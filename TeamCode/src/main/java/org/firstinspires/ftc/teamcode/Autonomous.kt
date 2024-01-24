package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.component.Arm
import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.component.Drive
import org.firstinspires.ftc.teamcode.component.Drone
import org.firstinspires.ftc.teamcode.component.Slider
import org.firstinspires.ftc.teamcode.state.State
import org.firstinspires.ftc.teamcode.subClass.Util
import org.firstinspires.ftc.teamcode.state.SliderStates
import org.firstinspires.ftc.teamcode.subClass.Const
import java.util.Timer
import kotlin.concurrent.timer


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
        val targetTime = 500 // 1秒をミリ秒で表した値
        val targetTime2 = 1000
        val targetTime3 = 1500
        val targetTime4 = 2500
        val targetTime5 = 3000
        val targetTime6 = 4000
        val targetTime7 = 4500
        val targetTime8 = 5500
        var targetTime9 = 6000
        var targetTime10 = 7000
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
                state.leftStickX = -0.5
                state.leftStickY = 0.0
                telemetry.addData("Status", "${runtime.seconds()}秒間実行中")
                telemetry.update()
            } else if ((System.currentTimeMillis() - startTime) < targetTime2){
                state.leftStickX = 0.0
                state.leftStickY = -0.5
            } else if ((System.currentTimeMillis() - startTime) < targetTime3){
                state.leftStickX = 0.5
                state.leftStickY = 0.0
            } else if ((System.currentTimeMillis() - startTime) < targetTime4){
                state.leftStickX = 0.0
                state.leftStickY = 0.0

                state.sliderPower = Const.Slider.Speed.targetToPosition

                state.leftSliderTargetPosition = Const.Slider.Position.top
                state.rightSliderTargetPosition = Const.Slider.Position.top

                state.liftIsUp = true

                state.flipIsUpward = true
            }else if ((System.currentTimeMillis() - startTime) < targetTime5){
                state.holderIsOpen = true

                state.leftStickX = 0.0
                state.leftStickY = 0.0
            }else if ((System.currentTimeMillis() - startTime) < targetTime6){
                state.leftStickX = 0.0
                state.leftStickY = 0.0

                state.leftSliderTargetPosition = 0
                state.rightSliderTargetPosition = 0

                state.liftIsUp = false

                state.flipIsUpward = false
            }else if ((System.currentTimeMillis() - startTime) < targetTime7){
                state.holderIsOpen = false
            }
            else if ((System.currentTimeMillis() - startTime) < targetTime8){
                state.leftStickX = 0.0
                state.leftStickY = 0.0

                state.leftSliderTargetPosition = Const.Slider.Position.top
                state.rightSliderTargetPosition = Const.Slider.Position.top

                state.liftIsUp = true

                state.flipIsUpward = true
            }else if ((System.currentTimeMillis() - startTime) < targetTime9){

                state.holderIsOpen = true

                state.leftStickX = 0.0
                state.leftStickY = 0.0
            }else if((System.currentTimeMillis() - startTime) < targetTime10){
                state.leftSliderTargetPosition = 0
                state.rightSliderTargetPosition = 0

                state.liftIsUp = false

                state.flipIsUpward = false
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

