package org.firstinspires.ftc.teamcode.subClass

import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.state.State

class Util {
    companion object {
        fun sendLog(state: State, telemetry: Telemetry) {
            telemetry.addData("target", state.rightSliderTargetPosition)
            telemetry.addData("power", state.sliderPower)
            telemetry.addData("current", state.rightSliderCurrentPosition)
            telemetry.addData("holderIsOpen", state.holderIsOpen.toString())
            telemetry.addData("liftIsUp",state.liftIsUp)
            telemetry.addData("liftCurrentPosition",state.liftCurrentPosition)
        }
    }
}