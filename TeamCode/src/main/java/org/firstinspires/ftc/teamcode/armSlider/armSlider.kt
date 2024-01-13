package org.firstinspires.ftc.teamcode.armSlider

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo

class armSlider {
    @TeleOp(name = "armSlider")
    class ArmTeleOp : LinearOpMode() {
        //DCモーターの可動領域の設定
        private val sliderLimitMin: Int = 500
        private val sliderLimitMax: Int = 3500

        private val armLimitMin = 10
        private val armLimitMax = 800

        @Throws(InterruptedException::class)
        override fun runOpMode() {
            //モーターの初期設定
            //左スライダー
            val leftSlider: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_1")
            leftSlider.direction = DcMotorSimple.Direction.REVERSE

            //右スライダー
            val rightSlider: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_2")
            rightSlider.direction = DcMotorSimple.Direction.FORWARD

            //アームのDCモーター
            val armMotor: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_3")
            armMotor.direction = DcMotorSimple.Direction.FORWARD

            //アームのサーボ
            val armEndServo: Servo = hardwareMap.get(Servo::class.java, "servo_2")
            val holderServo: Servo = hardwareMap.get(Servo::class.java, "servo_3")

            //アームのパワー
            armMotor.power = 0.0
            leftSlider.power = 0.0
            rightSlider.power = 0.0

            waitForStart()

            //エンコーダーの初期化
            leftSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            rightSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            armMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            armMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER
            leftSlider.mode = DcMotor.RunMode.RUN_USING_ENCODER
            rightSlider.mode = DcMotor.RunMode.RUN_USING_ENCODER

            telemetry.addData("Mode", "running")
            telemetry.update()

            armMotor.targetPosition = 0
            armEndServo.position = 0.0
            holderServo.position = 0.0

            // 初期位置を2000に設定
            while ((leftSlider.currentPosition < 2000 || rightSlider.currentPosition < 2000)) {
                leftSlider.targetPosition = 2000
                rightSlider.targetPosition = 2000

                leftSlider.power = 0.5
                rightSlider.power = 0.5
            }


            leftSlider.mode = DcMotor.RunMode.RUN_TO_POSITION
            rightSlider.mode = DcMotor.RunMode.RUN_TO_POSITION
            armMotor.mode = DcMotor.RunMode.RUN_TO_POSITION

            while (opModeIsActive()) {
                // ゲームピースを持つ
                if (gamepad1.dpad_up) {

                    //アームを上に動かす
                    val newArmTarget = 100
                    armMotor.targetPosition = newArmTarget.coerceIn(armLimitMin, armLimitMax)
                    armMotor.power = 0.5

                    // アームの目標位置に到達するまで待機
                    while (opModeIsActive() && armMotor.currentPosition < armMotor.targetPosition) {
                        sleep(50)
                    }

                    //スライダーキットを上に動かす
                    val newLeftTarget = 3500
                    val newRightTarget = 3500

                    leftSlider.targetPosition = newLeftTarget.coerceIn(sliderLimitMin, sliderLimitMax)
                    rightSlider.targetPosition = newRightTarget.coerceIn(sliderLimitMin, sliderLimitMax)

                    leftSlider.power = 0.5
                    rightSlider.power = 0.5

                    // スライダーの目標位置に到達するまで待機
                    while (opModeIsActive() && (leftSlider.currentPosition < leftSlider.targetPosition || rightSlider.currentPosition < rightSlider.targetPosition)) {
                        sleep(50)
                    }

                    // アームとスライダーの動きが完了した後、モーターを停止
                    leftSlider.power = 0.0
                    rightSlider.power = 0.0

                }else if (gamepad1.dpad_down) {

                    //アームを下に動かす
                    val newArmTarget = 100
                    armMotor.targetPosition = newArmTarget.coerceIn(armLimitMin, armLimitMax)
                    armMotor.power = 0.5

                    // アームの目標位置に到達するまで待機
                    while (opModeIsActive() && armMotor.currentPosition > armMotor.targetPosition) {
                        sleep(50)
                    }

                    //スライダーキットを下に動かす
                    val newLeftTarget = 500
                    val newRightTarget = 500

                    leftSlider.targetPosition = newLeftTarget.coerceIn(sliderLimitMin, sliderLimitMax)
                    rightSlider.targetPosition = newRightTarget.coerceIn(sliderLimitMin, sliderLimitMax)

                    leftSlider.power = 0.5
                    rightSlider.power = 0.5

                    // スライダーの目標位置に到達するまで待機
                    while (opModeIsActive() && (leftSlider.currentPosition < leftSlider.targetPosition || rightSlider.currentPosition < rightSlider.targetPosition)) {
                        sleep(50)
                    }

                    // アームとスライダーの動きが完了した後、モーターを停止
                    leftSlider.power = 0.0
                    rightSlider.power = 0.0

                }else {
                    leftSlider.power = 0.0
                    rightSlider.power = 0.0
                }


                // サーボの回転制御
                if (gamepad1.dpad_left) {
                    armEndServo.position = 0.3.coerceIn(0.0, 1.0)
                } else if (gamepad1.dpad_right) {
                    armEndServo.position = 0.7.coerceIn(0.0, 1.0)
                }

                // サーボの増減量の制御
                if (gamepad1.x) {
                    holderServo.position = 0.3.coerceIn(0.0, 1.0)
                }else if (gamepad1.y) {
                    holderServo.position = 0.7.coerceIn(0.0, 1.0)
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
