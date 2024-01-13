package org.firstinspires.ftc.teamcode

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.IMU
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class Mecanum2 {
    @TeleOp(name = "fieldOriented Mecanaum")
    class FieldCentricMecanumTeleOp : LinearOpMode() {
        @Throws(InterruptedException::class)
        override fun runOpMode() {

            // モーターの初期化
            val leftFront: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_0")
            leftFront.direction = DcMotorSimple.Direction.FORWARD
            val leftRear: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_2")
            leftRear.direction = DcMotorSimple.Direction.FORWARD
            val rightFront: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_0")
            rightFront.direction = DcMotorSimple.Direction.REVERSE
            val rightRear: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_1")
            rightRear.direction = DcMotorSimple.Direction.REVERSE

            // IMU（慣性計測装置）の初期化
            val imu = hardwareMap.get(IMU::class.java, "imu")

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

                // ゲームパッドの入力を取得
                val y = gamepad1.left_stick_y.toDouble()
                val x = gamepad1.left_stick_x.toDouble()
                val rx = gamepad1.right_stick_x.toDouble()

                // スタートボタンが押されたらIMUの角度をリセット
                if (gamepad1.start) {
                    imu.resetYaw()
                }
                // ロボットの方向を取得
                val botHeading = imu.robotYawPitchRollAngles.getYaw(AngleUnit.RADIANS)

                // Field Centric座標系に変換
                var rotX = x * cos(-botHeading) - y * sin(-botHeading)
                val rotY = x * sin(-botHeading) + y * cos(-botHeading)
                rotX *= 1.1 // 不完全な横移動を補正

                // パワーの正規化
                val denominator =
                    (abs(rotY) + abs(rotX) + abs(rx)).coerceAtLeast(1.0)
                val frontLeftPower = (rotY + rotX + rx) / denominator
                val backLeftPower = (rotY - rotX + rx) / denominator
                val frontRightPower = (rotY - rotX - rx) / denominator
                val backRightPower = (rotY + rotX - rx) / denominator

                // モーターパワーをセット
                leftFront.power = frontLeftPower
                leftRear.power = backLeftPower
                rightFront.power = frontRightPower
                rightRear.power = backRightPower

                telemetry.addData("Mecanum2", "running")
                telemetry.update()
            }
        }
    }
}
