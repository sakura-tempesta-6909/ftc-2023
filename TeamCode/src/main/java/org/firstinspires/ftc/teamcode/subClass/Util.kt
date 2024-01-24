package org.firstinspires.ftc.teamcode.subClass

import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.state.State
import kotlin.math.abs

class Util {
    companion object {
        fun sendLog(state: State, telemetry: Telemetry) {
            telemetry.addData("dronelauncher",state.dronelauncherPosition)
            telemetry.addData("leftSlider.currentPosition < Const.Slider.tolerance",state.leftSliderCurrentPosition < Const.Slider.tolerance)
            telemetry.addData("rightSlider.currentPosition < Const.Slider.tolerance",state.rightSliderCurrentPosition<Const.Slider.tolerance)
            telemetry.addData("leftSliderTarget == 0",state.leftSliderTargetPosition == 0)
            telemetry.addData("rightSliderTarget == 0",state.rightSliderTargetPosition == 0)
            telemetry.addData("isDown",state.rightSliderTargetPosition == 0 && state.leftSliderTargetPosition == 0 && abs(state.rightSliderCurrentPosition) < Const.Slider.tolerance && abs(state.leftSliderCurrentPosition) < Const.Slider.tolerance)
            telemetry.addData("target", state.rightSliderTargetPosition)
            telemetry.addData("power", state.leftRearPower)
            telemetry.addData("current", state.rightSliderCurrentPosition)
            telemetry.addData("holderIsOpen", state.holderIsOpen.toString())
            telemetry.addData("liftIsUp",state.liftIsUp)
            telemetry.addData("liftCurrentPosition",state.liftCurrentPosition)
            telemetry.addData("drone",state.droneIsShot)
        }
    }
}