package org.firstinspires.ftc.teamcode.Autonomous

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.component.Arm
import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.component.Drone
import org.firstinspires.ftc.teamcode.component.Slider
import org.firstinspires.ftc.teamcode.state.SliderStates
import org.firstinspires.ftc.teamcode.state.State
import org.firstinspires.ftc.teamcode.subClass.Const
import org.firstinspires.ftc.teamcode.subClass.Util
import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder


@Autonomous(name = "Advancing")
class Advancing : OpMode() {
    private val runtime = ElapsedTime()
    private val components = ArrayList<Component>()
    private val state = State()
    lateinit var drive: SampleMecanumDrive
    lateinit var traj1 : TrajectorySequence
    override fun init() {
        telemetry.addData("Status", "Initializing")
        components.add(Slider(hardwareMap))
        components.add(Arm(hardwareMap))
        components.add(Drone(hardwareMap))
        state.stateInit()
        drive = SampleMecanumDrive(hardwareMap)
        val startPose = Pose2d(0.0,0.0,Math.toRadians(0.0))
        drive.poseEstimate = startPose
        traj1 = drive.trajectorySequenceBuilder(startPose)
            .lineToLinearHeading(Pose2d(0.0,10.0,Math.toRadians(0.0)))
            .build()
        drive.followTrajectorySequenceAsync(traj1)
        telemetry.addData("Status", "Initialized")
    }

    /*
     * ドライバーがINITを押した後、PLAYを押す前に繰り返し実行するコード
     */
    override fun init_loop() {
        state.stateReset()
        //センサーの値の取得
        components.forEach { component ->
            component.readSensors(state)
        }
        //数値を出力
        Util.sendLog(state, telemetry)
    }

    /*
     * 開始時に一度だけ実行される
     */
    override fun start() {
        runtime.reset()
        state.stateInit()

    }

    /*
     * Enableの間ずっと実行される
     * while (opModeIsActive()) と同じ
     */
    override fun loop() {
        telemetry.addData("Status", "Run Time: $runtime")
        state.stateReset()
        //センサーの値の取得
        components.forEach { component ->
            component.readSensors(state)
        }

        drive.update()
        //Stateを適用
        components.forEach { component ->
            component.applyState(state)
        }
        //数値を出力
        Util.sendLog(state, telemetry)

    }

}
