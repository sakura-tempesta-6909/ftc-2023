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

@Autonomous(name = "BlueClose", group = "Blue")
class AutonomousBlueClose : LinearOpMode() {
    private val runtime = ElapsedTime()
    private val components = ArrayList<Component>()
    private val state = State()

    override fun runOpMode() {
        // 初期化処理
        telemetry.addData("Status", "Initializing")
        components.add(Slider(hardwareMap))
        components.add(Drive(hardwareMap))
        components.add(Arm(hardwareMap))
        components.add(Drone(hardwareMap))
        state.stateInit()
        telemetry.addData("Status", "Initialized")
        waitForStart()

        // ターゲット時間の定義
        val targetTimes = listOf(
                - Const.Autonomous.shortenDistance,
                Const.Autonomous.backTime,
                Const.Autonomous.slider,
                Const.Autonomous.holder,
                Const.Autonomous.slider,
                //8
                Const.Autonomous.minimumMove,
                Const.Autonomous.holder,
                //10
                Const.Autonomous.minimumMove,
                Const.Autonomous.slider,
                Const.Autonomous.holder,
                Const.Autonomous.slider,
                Const.Autonomous.enterTime,
                Const.Autonomous.enterTime,
                10,
                // 以下同様に追加
                // ...
        )


// ターゲット時間の合計時間
        val totalTargetTime = targetTimes.sum()

// ターゲット時間ごとの開始時間
        val startTimes = targetTimes.scan(0L) { acc, targetTime -> acc + targetTime }

// ステートの初期化
        state.stateInit()

// ゲーム開始待機
        waitForStart()

// ゲーム開始時刻
        val startTime = System.currentTimeMillis()

        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: $runtime")
            state.stateReset()

            // センサーの値の取得
            components.forEach { component ->
                component.readSensors(state)
            }

            // ターゲット時間ごとにステートを設定
            startTimes.forEachIndexed { index, start ->
                val elapsedTime = System.currentTimeMillis() - startTime
                if (elapsedTime in start until start + targetTimes[index]) {
                    when (index) {
                        0 -> {
                            // ちょっと後ろに進む
                            state.holderIsOpen = false
                            state.leftStickX = 0.5
                            state.leftStickY = 0.0
                        }
                        1 -> {
                        // さらに前に進む
                        state.leftStickX = 0.0
                        state.leftStickY = - 0.5
                        }
                        2 -> {
                            // Sliderの動作と各種設定
                            state.leftStickX = 0.0
                            state.leftStickY = 0.0
                            state.sliderPower = Const.Slider.Speed.targetToPosition
                            state.leftSliderTargetPosition = Const.Slider.Position.top
                            state.rightSliderTargetPosition = Const.Slider.Position.top
                            state.liftIsUp = true
                            state.flipIsUpward = true
                        }
                        3 -> {
                            // Holderを開く
                            state.holderIsOpen = true
                            state.leftStickX = 0.0
                            state.leftStickY = 0.0
                        }
                        4 -> {
                            // Sliderを戻す
                            state.leftStickX = 0.0
                            state.leftStickY = 0.0
                            state.leftSliderTargetPosition = 0
                            state.rightSliderTargetPosition = 0
                            state.liftIsUp = false
                            state.flipIsUpward = false
                        }
                        5 ->{
                            state.leftStickY = 0.5
                        }
                        6 -> {
                            state.leftStickY = 0.0
                            // Holderを閉じる
                            state.holderIsOpen = false
                        }
                        7 ->{
                            state.leftStickY = - 0.5
                        }
                        8 -> {
                            // Sliderを再び上げる
                            state.leftStickX = 0.0
                            state.leftStickY = 0.0
                            state.leftSliderTargetPosition = Const.Slider.Position.top
                            state.rightSliderTargetPosition = Const.Slider.Position.top
                            state.liftIsUp = true
                            state.flipIsUpward = true
                        }
                        9 -> {
                            // Holderを開く
                            state.holderIsOpen = true
                            state.leftStickX = 0.0
                            state.leftStickY = 0.0
                        }
                        10 -> {
                            // Sliderを戻す
                            state.leftSliderTargetPosition = 0
                            state.rightSliderTargetPosition = 0
                            state.liftIsUp = false
                            state.flipIsUpward = false
                        }
                        11 -> {
                            // 500ミリ秒横に進む
                            state.leftStickX = 0.5
                            state.leftStickY = 0.0
                        }
                        12 -> {
                            // 500ミリ秒前に進む
                            state.leftStickX = 0.0
                            state.leftStickY = -0.5
                        }
                        13 -> {
                            // 停止
                            state.leftStickX = 0.0
                            state.leftStickY = 0.0
                        }
                        // 以下同様に追加
                        // ...
                    }
                    return@forEachIndexed
                }
            }

            // Stateを適用
            components.forEach { component ->
                component.applyState(state)
            }

            // 数値を出力
            Util.sendLog(state, telemetry)
            telemetry.update()
        }

        telemetry.update()
    }
}