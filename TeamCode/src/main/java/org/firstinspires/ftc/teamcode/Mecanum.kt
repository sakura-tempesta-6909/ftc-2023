package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

class Mecanum {

    companion object {
        fun driveMecanum(
                leftFront: DcMotor,
                leftRear: DcMotor,
                rightFront: DcMotor,
                rightRear: DcMotor,
                x: Float,
                y: Float,
                rx: Float
        ) {
            val rotX = x
            val rotY = y

            // メカナムホイールの制御ロジック
            val frontLeftPower = rotY - rotX - rx
            val frontRightPower = rotY + rotX + rx
            val rearLeftPower = rotY + rotX - rx
            val rearRightPower = rotY - rotX + rx

            // モーターの速度を設定
            leftFront.power = frontLeftPower.toDouble()
            leftRear.power = rearLeftPower.toDouble()
            rightFront.power = frontRightPower.toDouble()
            rightRear.power = rearRightPower.toDouble()
        }
    }

    @TeleOp(name = "Mecanum")
    class LinearTeleop : LinearOpMode() {
        override fun runOpMode() {
            val leftFront: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_0")
            leftFront.direction = DcMotorSimple.Direction.FORWARD
            val leftRear: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_2")
            leftRear.direction = DcMotorSimple.Direction.FORWARD
            val rightFront: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_1")
            rightFront.direction = DcMotorSimple.Direction.REVERSE
            val rightRear: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_3")
            rightRear.direction = DcMotorSimple.Direction.REVERSE

            waitForStart()

            telemetry.addData("Mode", "running")
            telemetry.update()

            while (opModeIsActive()) {

                // ジョイスティックの値を取得
                val x = gamepad1.left_stick_x // 左右反転する場合は - を削除
                val y = gamepad1.left_stick_y // 上下反転する場合は - を削除
                val rx = gamepad1.right_stick_x // 左右反転する場合は - を削除

                // メカナムの制御関数を呼び出し
                driveMecanum(leftFront, leftRear, rightFront, rightRear, x, y, rx)

                telemetry.addData("leftStickX", x)
                telemetry.addData("leftStickY", y)
                telemetry.addData("rightStickX", rx)

                telemetry.update()
            }
        }
    }
}
