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
    var leftFrontPower = 0.0
    var rightFrontPower = 0.0
    var leftRearPower = 0.0
    var rightRearPower = 0.0
    var imuIsReset = false
    var leftStickX = 0.0
    var leftStickY = 0.0
    var rightStickX = 0.0

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
        leftFrontPower = 0.0
        rightFrontPower = 0.0
        leftRearPower = 0.0
        rightRearPower = 0.0
        imuIsReset = false
        leftStickX = 0.0
        leftStickY = 0.0
        rightStickX = 0.0
    }

    fun stateReset() {
        leftSliderCurrentPosition = 0
        rightSliderCurrentPosition = 0
        liftCurrentPosition = 0
    }
}

enum class SliderStates() {
    MoveSliderToPosition(),
    Disable(),
}