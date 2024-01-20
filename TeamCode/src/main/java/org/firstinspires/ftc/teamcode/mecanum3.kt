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

    companion object {
        fun driveMecanum(
                leftFront: DcMotor,
                leftRear: DcMotor,
                rightFront: DcMotor,
                rightRear: DcMotor,
                imu: IMU,
                x: Float,
                y: Float,
                rx: Float,
                start:Boolean
        ) {
            // ロボットの方向を取得
            val botHeading = imu.robotYawPitchRollAngles.getYaw(AngleUnit.RADIANS)

            // スタートボタンが押されたらIMUの角度をリセット
            if (start) {
                imu.resetYaw()
            }

            // Field Centric座標系に変換
            var rotX = x * cos(botHeading) - y * sin(botHeading)
            val rotY = - x * sin(botHeading) - y * cos(botHeading)

            rotX *= 1.1

            // パワーの正規化
            val denominator =
                    (abs(rotY) + abs(rotX) + abs(rx)).coerceAtLeast(1.0)
            val frontLeftPower = (rotY + rotX + rx) / denominator
            val rearLeftPower = (rotY - rotX + rx) / denominator
            val frontRightPower = (rotY - rotX - rx) / denominator
            val rearRightPower = (rotY + rotX - rx) / denominator

            leftFront.power = frontLeftPower
            leftRear.power = rearLeftPower
            rightFront.power = frontRightPower
            rightRear.power = rearRightPower
        }
    }


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
            val start = gamepad1.start

            driveMecanum(leftFront,leftRear,rightFront,rightRear,imu,x,y,rx,start)

            telemetry.addData("Mecanum2", "running")
            telemetry.update()

        }
    }
}
