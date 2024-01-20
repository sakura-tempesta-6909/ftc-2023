package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.IMU
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

@TeleOp(name = "FieldCentricMecanumTeleOp", group = "TeleOp")
class FieldCentricMecanumTeleOp : LinearOpMode() {

    private lateinit var imu: IMU

    override fun runOpMode() {
        val leftFront: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_0")
        leftFront.direction = DcMotorSimple.Direction.FORWARD
        val leftRear: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_2")
        leftRear.direction = DcMotorSimple.Direction.FORWARD
        val rightFront: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_1")
        rightFront.direction = DcMotorSimple.Direction.REVERSE
        val rightRear: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_3")
        rightRear.direction = DcMotorSimple.Direction.REVERSE


        // IMU（慣性計測装置）の初期化
        val imu = hardwareMap.get(IMU::class.java, "imu")
        imu.resetYaw()

        val parameters = IMU.Parameters(
                RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        )
        imu.initialize(parameters)

        waitForStart()

        if (isStopRequested) return

        while (opModeIsActive()) {
            val y = gamepad1.left_stick_y
            val x = gamepad1.left_stick_x
            val rx = gamepad1.right_stick_x

            if (gamepad1.start) {
                imu.resetYaw()
            }

            val botHeading = imu.robotYawPitchRollAngles.getYaw(AngleUnit.RADIANS)

            val rotX = x * cos(botHeading) - y * sin(botHeading)
            val rotY = - x * sin(botHeading) - y * cos(botHeading)

            val rotXAdjusted = rotX * 1.1

            val denominator = Math.max(abs(rotY) + abs(rotXAdjusted) + abs(rx), 1.0)
            val frontLeftPower = (rotY + rotXAdjusted + rx) / denominator
            val backLeftPower = (rotY - rotXAdjusted + rx) / denominator
            val frontRightPower = (rotY - rotXAdjusted - rx) / denominator
            val backRightPower = (rotY + rotXAdjusted - rx) / denominator

            leftFront.power = frontLeftPower
            leftRear.power = backLeftPower
            rightFront.power = frontRightPower
            rightRear.power = backRightPower
        }
    }
}
