package org.firstinspires.ftc.teamcode
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
class Mecanum {


    @TeleOp(name = "Mecanum")
    class LinearTeleop : LinearOpMode() {
        override fun runOpMode() {
            val leftFront: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_0")
            leftFront.direction = DcMotorSimple.Direction.FORWARD
            val leftRear: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_1")
            leftRear.direction = DcMotorSimple.Direction.FORWARD
            val rightFront: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_2")
            rightFront.direction = DcMotorSimple.Direction.REVERSE
            val rightRear: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_3")
            rightRear.direction = DcMotorSimple.Direction.REVERSE


            waitForStart()

            telemetry.addData("Mode", "running")
            telemetry.update()

            while (opModeIsActive()) {
                // ジョイスティックの値を取得
                val leftStickX = -gamepad1.left_stick_x // 左右反転する場合は - を削除
                val leftStickY = -gamepad1.left_stick_y // 上下反転する場合は - を削除
                val rightStickX = -gamepad1.right_stick_x // 左右反転する場合は - を削除

                // メカナムホイールの制御ロジック
                val frontLeftPower = -leftStickY + leftStickX + rightStickX
                val frontRightPower = -leftStickY - leftStickX - rightStickX
                val rearLeftPower = -leftStickY - leftStickX + rightStickX
                val rearRightPower = -leftStickY + leftStickX - rightStickX

                // モーターの速度を設定
                leftFront.power = frontLeftPower.toDouble()
                leftRear.power = rearLeftPower.toDouble()
                rightFront.power = frontRightPower.toDouble()
                rightRear.power = rearRightPower.toDouble()


                telemetry.addData("leftStickX", leftStickX)
                telemetry.addData("leftStickY", leftStickY)
                telemetry.addData("rightStickX", rightStickX)


                telemetry.update()


            }
        }
    }

}