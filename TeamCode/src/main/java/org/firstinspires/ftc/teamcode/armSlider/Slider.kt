package org.firstinspires.ftc.teamcode.armSlider

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

@TeleOp(name = "Slider")

class Slider : LinearOpMode() {

    override fun runOpMode() {
        val leftSlider: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_0")
        leftSlider.direction = DcMotorSimple.Direction.REVERSE

        val rightSlider: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_1")
        rightSlider.direction = DcMotorSimple.Direction.FORWARD

        waitForStart()

        leftSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        leftSlider.mode = DcMotor.RunMode.RUN_USING_ENCODER
        rightSlider.mode = DcMotor.RunMode.RUN_USING_ENCODER

        telemetry.addData("Mode", "running")
        telemetry.update()

        while (opModeIsActive()) {

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
