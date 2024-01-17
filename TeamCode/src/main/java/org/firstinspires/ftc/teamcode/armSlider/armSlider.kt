package org.firstinspires.ftc.teamcode.robot

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo

@TeleOp(name = "armSlider")
class armSlider : LinearOpMode() {

    private val armLimitMin = 0
    private val armLimitMax = 700

    private val sliderLimitMin: Int = 0
    private val sliderLimitMax: Int = 2100

    override fun runOpMode() {
        // アーム関連のハードウェア初期化
        val armMotor: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_3")
        armMotor.direction = DcMotorSimple.Direction.FORWARD
        armMotor.power = 0.0

        val armEndServo: Servo = hardwareMap.get(Servo::class.java, "servo_0")
        val holderServo: Servo = hardwareMap.get(Servo::class.java, "servo_1")

        // スライダー関連のハードウェア初期化
        val leftSlider: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_0")
        leftSlider.direction = DcMotorSimple.Direction.REVERSE

        val rightSlider: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_1")
        rightSlider.direction = DcMotorSimple.Direction.FORWARD

        leftSlider.power = 0.0
        rightSlider.power = 0.0

        waitForStart()

        // アームの初期設定
        armMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        armMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER

        telemetry.addData("Mode", "running")
        telemetry.update()

        armMotor.targetPosition = 0
        armEndServo.position = 0.0
        holderServo.position = 0.0

        armMotor.targetPosition = 0
        armMotor.power = 0.5

        armMotor.mode = DcMotor.RunMode.RUN_TO_POSITION

        // スライダーの初期設定
        leftSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        leftSlider.mode = DcMotor.RunMode.RUN_USING_ENCODER
        rightSlider.mode = DcMotor.RunMode.RUN_USING_ENCODER

        telemetry.addData("Mode", "running")
        telemetry.update()

        // 初期位置にスライダーを移動

            leftSlider.targetPosition = 0
            rightSlider.targetPosition = 0

            leftSlider.power = 0.5
            rightSlider.power = 0.5


        leftSlider.mode = DcMotor.RunMode.RUN_TO_POSITION
        rightSlider.mode = DcMotor.RunMode.RUN_TO_POSITION

        while (opModeIsActive()) {
            // アーム制御
            if (gamepad1.dpad_up) {
                val armTarget = 680
                armMotor.targetPosition = armTarget.coerceIn(armLimitMin, armLimitMax)
                armMotor.power = 0.25

                if (armMotor.currentPosition > 300) {
                    armEndServo.position = 0.7.coerceIn(0.0, 1.0)
                }
            }else if (gamepad1.dpad_down){
                val armTarget = 0
                armMotor.targetPosition = armTarget.coerceIn(armLimitMin, armLimitMax)
                armMotor.power = 0.25

                if (armMotor.currentPosition > 300) {
                    armEndServo.position = 0.0.coerceIn(0.0, 1.0)
                }
            }

            // スライダー制御
            if (gamepad1.dpad_up) {
                val sliderTarget = 2000
                leftSlider.targetPosition = sliderTarget.coerceIn(sliderLimitMin, sliderLimitMax)
                rightSlider.targetPosition = sliderTarget.coerceIn(sliderLimitMin, sliderLimitMax)

                leftSlider.power = 0.5
                rightSlider.power = 0.5
            }else if (gamepad1.dpad_down){
                val sliderTarget = 0
                leftSlider.targetPosition = sliderTarget.coerceIn(sliderLimitMin, sliderLimitMax)
                rightSlider.targetPosition = sliderTarget.coerceIn(sliderLimitMin, sliderLimitMax)

                leftSlider.power = 0.5
                rightSlider.power = 0.5
            }

            // ホルダー制御
            if (gamepad1.x) {
                holderServo.position = 0.7.coerceIn(0.0, 1.0)
            }else if (gamepad1.y){
                holderServo.position = 0.0.coerceIn(0.0, 1.0)
            }

            // テレメトリ
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
