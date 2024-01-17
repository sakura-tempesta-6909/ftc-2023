package org.firstinspires.ftc.teamcode.armSlider

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo

@TeleOp(name = "CombinedTeleOp")
class CombinedTeleOp : LinearOpMode() {

    private val armLimitMin = 10
    private val armLimitMax = 700

    private val sliderLimitMin: Int = 500
    private val sliderLimitMax: Int = 2000

    override fun runOpMode() {
        // アームのセットアップ
        val armMotor: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_3")
        armMotor.direction = DcMotorSimple.Direction.FORWARD
        armMotor.power = 0.0

        val armEndServo: Servo = hardwareMap.get(Servo::class.java, "servo_0")
        val holderServo: Servo = hardwareMap.get(Servo::class.java, "servo_1")

        // スライダーのセットアップ
        val leftSlider: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_1")
        leftSlider.direction = DcMotorSimple.Direction.REVERSE

        val rightSlider: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_2")
        rightSlider.direction = DcMotorSimple.Direction.FORWARD

        leftSlider.power = 0.0
        rightSlider.power = 0.0

        // アームの初期化
        armMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        armMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER

        telemetry.addData("Mode", "running")
        telemetry.update()

        armMotor.targetPosition = 0
        armEndServo.position = 0.0
        holderServo.position = 0.0

        armMotor.mode = DcMotor.RunMode.RUN_TO_POSITION

        // スライダーの初期化
        leftSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        leftSlider.mode = DcMotor.RunMode.RUN_USING_ENCODER
        rightSlider.mode = DcMotor.RunMode.RUN_USING_ENCODER

        leftSlider.targetPosition = 0
        rightSlider.targetPosition = 0

        leftSlider.mode = DcMotor.RunMode.RUN_TO_POSITION
        rightSlider.mode = DcMotor.RunMode.RUN_TO_POSITION

        waitForStart()

        armMotor.targetPosition = 0
        armMotor.power = 0.5

        while (opModeIsActive()) {
            // アームの制御ロジック
            if (gamepad1.dpad_up) {
                // アームを上げる
                if (armMotor.currentPosition < 150) {
                    armMotor.targetPosition = 680.coerceIn(armLimitMin, armLimitMax)
                    armMotor.power = 0.2
                } else if (armMotor.currentPosition < 680) {
                    armMotor.targetPosition = 680.coerceIn(armLimitMin, armLimitMax)
                    armMotor.power = 0.2

                    armEndServo.position = 0.7.coerceIn(0.0, 1.0)
                }

                // スライダーを上げる
                val newLeftTarget = 2000
                val newRightTarget = 2000

                leftSlider.targetPosition = newLeftTarget.coerceIn(sliderLimitMin, sliderLimitMax)
                rightSlider.targetPosition = newRightTarget.coerceIn(sliderLimitMin, sliderLimitMax)

                leftSlider.power = 0.5
                rightSlider.power = 0.5

            } else if (gamepad1.dpad_down) {
                // アームを下げる
                if (armMotor.currentPosition > 10) {
                    armMotor.targetPosition = 10.coerceIn(armLimitMin, armLimitMax)
                    armMotor.power = 0.2
                }
                if (armEndServo.position > 0.0) {
                    armEndServo.position = 0.0.coerceIn(0.0, 1.0)
                }

                // スライダーを下げる
                val newLeftTarget = 10
                val newRightTarget = 10

                leftSlider.targetPosition = newLeftTarget.coerceIn(sliderLimitMin, sliderLimitMax)
                rightSlider.targetPosition = newRightTarget.coerceIn(sliderLimitMin, sliderLimitMax)

                leftSlider.power = 0.5
                rightSlider.power = 0.5

            } else {
                // 入力がない場合、スライダーのモーターを停止
                leftSlider.power = 0.5
                rightSlider.power = 0.5
            }


            // ホルダーの制御
            if (gamepad1.x) {
                //しまる
                holderServo.position = 0.7.coerceIn(0.0, 1.0)
            } else if (gamepad1.y) {
                //開く
                holderServo.position = 0.0.coerceIn(0.0, 1.0)
            }

            // テレメトリの更新
            telemetry.addData("アームの位置", armMotor.currentPosition)
            telemetry.addData("アームの目標", armMotor.targetPosition)
            telemetry.addData("回転サーボ", armEndServo.position)
            telemetry.addData("ホルダー", holderServo.position)

            telemetry.addData("左モーターの位置", leftSlider.currentPosition)
            telemetry.addData("右モーターの位置", rightSlider.currentPosition)
            telemetry.addData("左モーターのパワー", leftSlider.power)
            telemetry.addData("右モーターのパワー", rightSlider.power)
            telemetry.addData("左モーターの目標", leftSlider.targetPosition)
            telemetry.addData("右モーターの目標", rightSlider.targetPosition)

            telemetry.update()
        }
    }
}
