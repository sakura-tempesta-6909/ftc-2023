package org.firstinspires.ftc.teamcode.drive.opmode

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.kinematics.Kinematics.calculateMotorFeedforward
import com.acmerobotics.roadrunner.profile.MotionProfile
import com.acmerobotics.roadrunner.profile.MotionProfileGenerator.generateSimpleMotionProfile
import com.acmerobotics.roadrunner.profile.MotionState
import com.acmerobotics.roadrunner.util.NanoClock
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.RobotLog
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import java.util.Objects

@Config
@Autonomous(group = "drive")
class ManualFeedforwardTuner : LinearOpMode() {
    private val dashboard = FtcDashboard.getInstance()
    private lateinit var drive: SampleMecanumDrive

    internal enum class Mode {
        DRIVER_MODE,
        TUNING_MODE
    }

    private var mode: Mode? = null

    override fun runOpMode() {
        if (RUN_USING_ENCODER) {
            RobotLog.setGlobalErrorMsg(
                "Feedforward constants usually don't need to be tuned " +
                        "when using the built-in drive motor velocity PID."
            )
        }
        val telemetry: Telemetry = MultipleTelemetry(telemetry, dashboard.telemetry)
        drive = SampleMecanumDrive(hardwareMap)
        val voltageSensor = hardwareMap.voltageSensor.iterator().next()
        mode = Mode.TUNING_MODE
        val clock = NanoClock.system()
        telemetry.addLine("Ready!")
        telemetry.update()
        telemetry.clearAll()
        waitForStart()
        if (isStopRequested) return
        var movingForwards = true
        var activeProfile = generateProfile(true)
        var profileStart = clock.seconds()
        while (!isStopRequested) {
            telemetry.addData("mode", mode)
            when (mode) {
                Mode.TUNING_MODE -> {
                    if (gamepad1.y) {
                        mode = Mode.DRIVER_MODE
                    }

                    // calculate and set the motor power
                    val profileTime = clock.seconds() - profileStart
                    if (profileTime > activeProfile.duration()) {
                        // generate a new profile
                        movingForwards = !movingForwards
                        activeProfile = generateProfile(movingForwards)
                        profileStart = clock.seconds()
                    }
                    val motionState = activeProfile[profileTime]
                    val targetPower =
                        calculateMotorFeedforward(motionState.v, motionState.a, kV, kA, kStatic)
                    val NOMINAL_VOLTAGE = 12.0
                    val voltage = voltageSensor.voltage
                    drive.setDrivePower(Pose2d(NOMINAL_VOLTAGE / voltage * targetPower, 0.0, 0.0))
                    drive.updatePoseEstimate()

                    val currentVelo = drive.poseVelocity?.x

                    if (currentVelo != null) {
                        // update telemetry
                        telemetry.addData("targetVelocity", motionState.v)
                        telemetry.addData("measuredVelocity", currentVelo)
                        telemetry.addData("error", motionState.v - currentVelo)
                    } else {
                        telemetry.addLine("Warning: poseVelocity is null.")
                    }
                }

                Mode.DRIVER_MODE -> {
                    if (gamepad1.b) {
                        mode = Mode.TUNING_MODE
                        movingForwards = true
                        activeProfile = generateProfile(movingForwards)
                        profileStart = clock.seconds()
                    }
                    drive.setWeightedDrivePower(
                        Pose2d(
                            -gamepad1.left_stick_y.toDouble(),
                            -gamepad1.left_stick_x.toDouble(),
                            -gamepad1.right_stick_x.toDouble()
                        )
                    )
                }

                else -> {
                    // Add a handling for the null case or do nothing
                }
            }
            telemetry.update()
        }
    }

    companion object {
        var DISTANCE = 200.0 // in
        var MAX_VEL = 61.7 // adjust as needed
        var MAX_ACCEL = 61.7 // adjust as needed
        var kV = 0.0162022455 // adjust as needed
        var kA = 0.002 // adjust as needed
        var kStatic = 0.1// adjust as needed
        var RUN_USING_ENCODER = false // adjust as needed

        private fun generateProfile(movingForward: Boolean): MotionProfile {
            val start = MotionState(if (movingForward) 0.0 else DISTANCE, 0.0, 0.0, 0.0)
            val goal = MotionState(if (movingForward) DISTANCE else 0.0, 0.0, 0.0, 0.0)
            return generateSimpleMotionProfile(start, goal, MAX_VEL, MAX_ACCEL)
        }
    }
}
