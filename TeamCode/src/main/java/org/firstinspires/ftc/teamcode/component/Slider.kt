package org.firstinspires.ftc.teamcode.component

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.state.SliderStates
import org.firstinspires.ftc.teamcode.state.State
import org.firstinspires.ftc.teamcode.subClass.Const

class Slider(hardwareMap: HardwareMap) : Component {
    private var leftSlider: DcMotor
    private var rightSlider: DcMotor

    init {
        leftSlider = hardwareMap.get(DcMotor::class.java, Const.Slider.Motor.Name.Left)
        rightSlider = hardwareMap.get(DcMotor::class.java, Const.Slider.Motor.Name.Right)

        leftSlider.direction = Const.Slider.Motor.Direction.Left
        rightSlider.direction = Const.Slider.Motor.Direction.Right
        leftSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

        leftSlider.mode = DcMotor.RunMode.RUN_TO_POSITION
        rightSlider.mode = DcMotor.RunMode.RUN_TO_POSITION


    }

    override fun autonomousInit() {
        TODO("Not yet implemented")
    }

    override fun teleopInit() {
        TODO("Not yet implemented")
    }

    override fun disabledInit() {
        TODO("Not yet implemented")
    }

    override fun testInit() {
        TODO("Not yet implemented")
    }

    override fun readSensors(state: State) {
        state.rightSliderCurrentPosition = rightSlider.currentPosition
        state.leftSliderCurrentPosition = leftSlider.currentPosition
    }

    private fun moveSliderToPosition(rightSliderTarget: Int, leftSliderTarget: Int, sliderPower: Double) {
        if (rightSliderTarget == 0 && leftSliderTarget == 0 && rightSlider.currentPosition < Const.Slider.tolerance && leftSlider.currentPosition < Const.Slider.tolerance) {
            rightSlider.power = 0.0
            leftSlider.power = 0.0
        } else {
            rightSlider.power = sliderPower
            leftSlider.power = sliderPower
        }
        rightSlider.targetPosition = rightSliderTarget
        leftSlider.targetPosition = leftSliderTarget
    }

    override fun applyState(state: State) {
        when (state.sliderState) {
            SliderStates.Disable -> {
                //Sliderがしまわれていたら、モーターのパワーを減らす
                if (rightSlider.currentPosition < Const.Slider.tolerance && leftSlider.currentPosition < Const.Slider.tolerance) {
                    rightSlider.power = 0.0
                    leftSlider.power = 0.0
                } else {
                    rightSlider.power = Const.Slider.Speed.targetToPosition
                    leftSlider.power = Const.Slider.Speed.targetToPosition
                }
                leftSlider.targetPosition = 0
                rightSlider.targetPosition = 0
            }

            SliderStates.MoveSliderToPosition -> {
                moveSliderToPosition(state.rightSliderTargetPosition, state.leftSliderTargetPosition, state.sliderPower)
            }
        }
    }
}