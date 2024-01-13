package org.firstinspires.ftc.teamcode.armSlider

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo

class Arm {
    @TeleOp(name = "Arm")
    class ArmTeleOp : LinearOpMode() {
        //DCモーターの可動領域の設定

        private val armLimitMin = 10
        private val armLimitMax = 800

        @Throws(InterruptedException::class)
        override fun runOpMode() {
        //モーターの初期設定

            val armMotor: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_3")
            armMotor.direction = DcMotorSimple.Direction.FORWARD
            armMotor.power = 0.0

            val armEndServo: Servo = hardwareMap.get(Servo::class.java, "servo_1")
            val holderServo: Servo = hardwareMap.get(Servo::class.java, "servo_0")

            waitForStart()

            armMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            armMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER

            telemetry.addData("Mode", "running")
            telemetry.update()

            armMotor.targetPosition = 0
            armEndServo.position = 0.5
            holderServo.position = 0.5

            armMotor.mode = DcMotor.RunMode.RUN_TO_POSITION

            while (opModeIsActive()) {
                // アームの上下動の制御
                if (gamepad1.dpad_up) {
                    val newArmTarget = armMotor.targetPosition + 10
                    armMotor.targetPosition = newArmTarget.coerceIn(armLimitMin, armLimitMax)
                    armMotor.power = 0.5
                } else if (gamepad1.dpad_down) {
                    val newArmTarget = armMotor.targetPosition - 10
                    armMotor.targetPosition = newArmTarget.coerceIn(armLimitMin, armLimitMax)
                    armMotor.power = 0.5
                }

                // サーボの回転制御
                if (gamepad1.dpad_left) {
                    armEndServo.position += 0.05.coerceIn(0.0, 1.0)
                } else if (gamepad1.dpad_right) {
                    armEndServo.position -= 0.05.coerceIn(0.0, 1.0)
                }

                // サーボの増減量の制御
                if (gamepad1.x) {
                    holderServo.position += 0.05.coerceIn(0.0, 1.0)
                }else if (gamepad1.y) {
                    holderServo.position -= 0.05.coerceIn(0.0, 1.0)
                }

                telemetry.addData("アームの位置",armMotor.currentPosition)
                telemetry.addData("アームの目標",armMotor.targetPosition)
                telemetry.addData("回転サーボ",armEndServo.position)
                telemetry.addData("ホルダー",holderServo.position)
                telemetry.update()

            }
        }
    }
}



