package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.component.Arm
import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.component.Drive
import org.firstinspires.ftc.teamcode.component.Drone
import org.firstinspires.ftc.teamcode.component.Slider
import org.firstinspires.ftc.teamcode.state.State
import org.firstinspires.ftc.teamcode.subClass.Util
import org.firstinspires.ftc.teamcode.subClass.Const


@Autonomous(name = "Main Autonomous", group = "Main")
class Autonomous : LinearOpMode() {
    private val runtime = ElapsedTime()
    private val components = ArrayList<Component>()
    private val state = State()


    override fun runOpMode() {
        telemetry.addData("Status", "Initializing")
        components.add(Slider(hardwareMap))
        components.add(Drive(hardwareMap))
        components.add(Arm(hardwareMap))
        components.add(Drone(hardwareMap))
        state.stateInit()
        telemetry.addData("Status", "Initialized")
        waitForStart()
        val startTime = System.currentTimeMillis()

        val targetTime = Const.Autonomous.lateralMovement
        val targetTime2 = targetTime + Const.Autonomous.verticalMovement
        val targetTime3 = targetTime2 + Const.Autonomous.lateralMovement - Const.Autonomous.shortenDistance
        val targetTime4 = targetTime3 + Const.Autonomous.backTime
        val targetTime5 = targetTime4 + Const.Autonomous.slider
        val targetTime6 = targetTime5 + Const.Autonomous.holder
        val targetTime7 = targetTime6 + Const.Autonomous.slider
        //ちょっと後ろに進む
        val targetTime8 = targetTime7 + Const.Autonomous.holder
        //ちょっと前に進む
        val targetTime9 = targetTime8 + Const.Autonomous.slider
        val targetTime10 = targetTime9 + Const.Autonomous.holder
        val targetTime11 = targetTime10 + Const.Autonomous.slider
        val targetTime12 = targetTime11 + Const.Autonomous.enterTime
        val targetTime13 = targetTime12 + Const.Autonomous.enterTime
        state.stateReset()
        //センサーの値の取得
        components.forEach { component ->
            component.readSensors(state)
        }
        //数値を出力
        Util.sendLog(state, telemetry)

        runtime.reset()
        state.stateInit()
        gamepad1.rumble(1)
        gamepad2.rumble(1)

        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: $runtime")
            state.stateReset()
            //センサーの値の取得
            components.forEach { component ->
                component.readSensors(state)
            }
            if ((System.currentTimeMillis() - startTime) < targetTime){
                state.holderIsOpen = false
                state.leftStickX = - 0.5
                state.leftStickY = 0.0
            } else if ((System.currentTimeMillis() - startTime) < targetTime2){
                state.leftStickX = 0.0
                state.leftStickY = -0.5
            } else if ((System.currentTimeMillis() - startTime) < targetTime3){
                state.leftStickX = 0.5
                state.leftStickY = 0.0
            } else if ((System.currentTimeMillis() - startTime) < targetTime4){
                state.leftStickX = 0.0
                state.leftStickY = -0.5
            } else if ((System.currentTimeMillis() - startTime) < targetTime5){
                state.leftStickX = 0.0
                state.leftStickY = 0.0
                state.sliderPower = Const.Slider.Speed.targetToPosition

                state.leftSliderTargetPosition = Const.Slider.Position.top
                state.rightSliderTargetPosition = Const.Slider.Position.top

                state.liftIsUp = true

                state.flipIsUpward = true
            }else if ((System.currentTimeMillis() - startTime) < targetTime6){
                state.holderIsOpen = true

                state.leftStickX = 0.0
                state.leftStickY = 0.0
            }else if ((System.currentTimeMillis() - startTime) < targetTime7){
                state.leftStickX = 0.0
                state.leftStickY = 0.0

                state.leftSliderTargetPosition = 0
                state.rightSliderTargetPosition = 0

                state.liftIsUp = false

                state.flipIsUpward = false
            }else if ((System.currentTimeMillis() - startTime) < targetTime8){
                state.holderIsOpen = false
            }
            else if ((System.currentTimeMillis() - startTime) < targetTime9){
                state.leftStickX = 0.0
                state.leftStickY = 0.0

                state.leftSliderTargetPosition = Const.Slider.Position.top
                state.rightSliderTargetPosition = Const.Slider.Position.top

                state.liftIsUp = true

                state.flipIsUpward = true
            }else if ((System.currentTimeMillis() - startTime) < targetTime10){

                state.holderIsOpen = true

                state.leftStickX = 0.0
                state.leftStickY = 0.0
            }else if((System.currentTimeMillis() - startTime) < targetTime11){
                state.leftSliderTargetPosition = 0
                state.rightSliderTargetPosition = 0

                state.liftIsUp = false

                state.flipIsUpward = false
            }else if((System.currentTimeMillis() - startTime) < targetTime12){
                state.leftStickX = 0.5
                state.leftStickY = 0.0
            }else if((System.currentTimeMillis() - startTime) < targetTime13){
                state.leftStickX = 0.0
                state.leftStickY = -0.5
            }else {
                state.leftStickX = 0.0
                state.leftStickY = 0.0
            }

            //Stateを適用
            components.forEach { component ->
                component.applyState(state)
            }
            //数値を出力
            Util.sendLog(state, telemetry)
        }
        telemetry.update();
    }
}

