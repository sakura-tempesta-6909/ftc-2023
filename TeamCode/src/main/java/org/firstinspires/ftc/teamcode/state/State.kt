package org.firstinspires.ftc.teamcode.state

import com.qualcomm.robotcore.hardware.Gamepad

class State() {
    var sliderState: SliderStates = SliderStates.Disable
    var leftSliderTargetPosition = 0
    var leftSliderCurrentPosition = 0
    var rightSliderTargetPosition = 0
    var rightSliderCurrentPosition = 0
    var sliderPower = 0.0

    fun stateInit() {
        sliderState = SliderStates.Disable
        leftSliderTargetPosition = 0
        leftSliderCurrentPosition = 0
        rightSliderTargetPosition = 0
        rightSliderCurrentPosition = 0
        sliderPower = 0.0
    }

    fun stateReset() {
        leftSliderCurrentPosition = 0
        rightSliderCurrentPosition = 0
    }
}

enum class SliderStates() {
    MoveSliderToPosition(),
    Disable(),
}