package org.firstinspires.ftc.teamcode.armSlider

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

@TeleOp(name = "Slider")

class Slider : LinearOpMode() {

    private val sliderLimitMin: Int = 500
    private val sliderLimitMax: Int = 3500

    override fun runOpMode() {
        val leftSlider: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_1")
        leftSlider.direction = DcMotorSimple.Direction.REVERSE

        val rightSlider: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_2")
        rightSlider.direction = DcMotorSimple.Direction.FORWARD

        leftSlider.power = 0.0
        rightSlider.power = 0.0

        waitForStart()

        leftSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        leftSlider.mode = DcMotor.RunMode.RUN_USING_ENCODER
        rightSlider.mode = DcMotor.RunMode.RUN_USING_ENCODER

        telemetry.addData("Mode", "running")
        telemetry.update()

        // 初期位置を2000に設定
        while ((leftSlider.currentPosition < 2000 || rightSlider.currentPosition < 2000)) {
            leftSlider.targetPosition = 2000
            rightSlider.targetPosition = 2000

            leftSlider.power = 0.5
            rightSlider.power = 0.5
        }


        leftSlider.mode = DcMotor.RunMode.RUN_TO_POSITION
        rightSlider.mode = DcMotor.RunMode.RUN_TO_POSITION

        while (opModeIsActive()) {
            if (gamepad1.a){

                val newLeftTarget = 500
                val newRightTarget = 500

                leftSlider.targetPosition = newLeftTarget.coerceIn(sliderLimitMin, sliderLimitMax)
                rightSlider.targetPosition = newRightTarget.coerceIn(sliderLimitMin, sliderLimitMax)

                leftSlider.power = 0.5
                rightSlider.power = 0.5

            }else if (gamepad1.b){

                val newLeftTarget = 2000
                val newRightTarget = 2000

                leftSlider.targetPosition = newLeftTarget.coerceIn(sliderLimitMin, sliderLimitMax)
                rightSlider.targetPosition = newRightTarget.coerceIn(sliderLimitMin, sliderLimitMax)

                leftSlider.power = 0.5
                rightSlider.power = 0.5

            }

            // 現在の位置をテレメトリに表示
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
