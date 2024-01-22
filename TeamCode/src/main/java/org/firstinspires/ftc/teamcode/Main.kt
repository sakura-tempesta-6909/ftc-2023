/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.component.Arm
import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.component.Drive
import org.firstinspires.ftc.teamcode.component.Drone
import org.firstinspires.ftc.teamcode.component.Slider
import org.firstinspires.ftc.teamcode.state.SliderStates
import org.firstinspires.ftc.teamcode.state.State
import org.firstinspires.ftc.teamcode.subClass.Const
import org.firstinspires.ftc.teamcode.subClass.Util


@TeleOp(name = "Main OpMode", group = "Main")
class Main : OpMode() {
    private val runtime = ElapsedTime()
    private val components = ArrayList<Component>()
    private val state = State()

    override fun init() {
        telemetry.addData("Status", "Initializing")
        components.add(Slider(hardwareMap))
        components.add(Drive(hardwareMap))
        components.add(Arm(hardwareMap))
        components.add(Drone(hardwareMap))
        state.stateInit()
        telemetry.addData("Status", "Initialized")
    }

    /*
     * ドライバーがINITを押した後、PLAYを押す前に繰り返し実行するコード
     */
    override fun init_loop() {
        state.stateReset()
        //センサーの値の取得
        components.forEach { component ->
            component.readSensors(state)
        }
        //数値を出力
        Util.sendLog(state, telemetry)
    }

    /*
     * 開始時に一度だけ実行される
     */
    override fun start() {
        runtime.reset()
        state.stateInit()
        gamepad1.rumble(1)
        gamepad2.rumble(1)
    }

    /*
     * Enableの間ずっと実行される
     * while (opModeIsActive()) と同じ
     */
    override fun loop() {
        telemetry.addData("Status", "Run Time: $runtime")
        state.stateReset()
        //センサーの値の取得
        components.forEach { component ->
            component.readSensors(state)
        }
        //操作関連コマンド
        if (gamepad2.dpad_up) {
            state.sliderState = SliderStates.MoveSliderToPosition
            state.leftSliderTargetPosition = Const.Slider.Position.top
            state.rightSliderTargetPosition = Const.Slider.Position.top
            state.sliderPower = Const.Slider.Speed.targetToPosition
        } else if (gamepad2.dpad_down) {
            state.sliderState = SliderStates.MoveSliderToPosition
            state.leftSliderTargetPosition = 0
            state.rightSliderTargetPosition = 0
            state.sliderPower = Const.Slider.Speed.targetToPosition
        }

        if (gamepad1.x) {
            state.holderIsOpen = true
        } else if (gamepad1.y) {
            state.holderIsOpen = false
        } else if (gamepad2.x) {
            state.holderIsOpen = true
        } else if (gamepad2.y) {
            state.holderIsOpen = false}
            if (gamepad2.dpad_up) {
                state.liftIsUp = true
            } else if (gamepad2.dpad_down) {
                state.liftIsUp = false
            }
            if (gamepad2.dpad_up) {
                state.flipIsUpward = true
            } else if (gamepad2.dpad_down) {
                state.flipIsUpward = false
            }
            if (gamepad2.a) {
                state.sliderState = SliderStates.MoveSliderToPosition
                state.leftSliderTargetPosition = Const.Slider.Position.climb
                state.rightSliderTargetPosition = Const.Slider.Position.climb
                state.sliderPower = Const.Slider.Speed.targetToPosition
                state.liftIsUp = false
                state.flipIsUpward = false
            }
        if (gamepad2.right_trigger > 0){
            state.leftSliderTargetPosition -= Const.Slider.Position.motor_adjustment_quantity
            state.rightSliderTargetPosition -= Const.Slider.Position.motor_adjustment_quantity
        }else if(gamepad2.left_trigger > 0){
            state.leftSliderTargetPosition += Const.Slider.Position.motor_adjustment_quantity
            state.rightSliderTargetPosition += Const.Slider.Position.motor_adjustment_quantity
        }

            state.droneIsShot = gamepad1.right_bumper && gamepad2.right_bumper
            state.imuIsReset = gamepad1.start
            state.leftStickX = gamepad1.left_stick_x.toDouble()
            state.leftStickY = gamepad1.left_stick_y.toDouble()
            state.rightStickX = gamepad1.right_stick_x.toDouble()

            //Stateを適用
            components.forEach { component ->
                component.applyState(state)
            }
            //数値を出力
            Util.sendLog(state, telemetry)

    }
    /*
   * コードが停止されるときに一度だけ実行される
   */
    override fun stop() {}
}
