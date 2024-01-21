package org.firstinspires.ftc.teamcode.state

class State() {
    var sliderState: SliderStates = SliderStates.Disable
    var leftSliderTargetPosition = 0
    var leftSliderCurrentPosition = 0
    var rightSliderTargetPosition = 0
    var rightSliderCurrentPosition = 0
    var sliderPower = 0.0
    var holderIsOpen = false
    var liftIsUp = false
    var liftCurrentPosition = 0
    var flipIsUpward = false

    fun stateInit() {
        sliderState = SliderStates.Disable
        leftSliderTargetPosition = 0
        leftSliderCurrentPosition = 0
        rightSliderTargetPosition = 0
        rightSliderCurrentPosition = 0
        sliderPower = 0.0
        holderIsOpen = false
        liftIsUp = false
        liftCurrentPosition = 0
        flipIsUpward = false
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