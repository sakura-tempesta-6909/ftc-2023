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

@Autonomous(name = "BlueFar", group = "Blue")
class AutonomousBlueFar : LinearOpMode() {
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
                //0
                Const.Autonomous.lateralMovement,
                //1
                Const.Autonomous.verticalMovement,
                //2
                Const.Autonomous.lateralMovement /2,
                //3
                Const.Autonomous.backTime,
                //4
                Const.Autonomous.slider,
                //5
                Const.Autonomous.holder,
                //6
                Const.Autonomous.slider,
                //7
                Const.Autonomous.minimumMove,
                //8
                Const.Autonomous.holder,
                //9
                Const.Autonomous.minimumMove,
                //10
                Const.Autonomous.slider,
                //11
                Const.Autonomous.holder,
                //12
                Const.Autonomous.slider,
                //13
                Const.Autonomous.enterTime,
                //14
                Const.Autonomous.enterTime,
                //15
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
//            telemetry.addData("Status", "Run Time: $runtime")
            state.stateReset()

            // センサーの値の取得
            components.forEach { component ->
                component.readSensors(state)
            }

            // ターゲット時間ごとにステートを設定
            startTimes.forEachIndexed { index, start ->
                val elapsedTime = System.currentTimeMillis() - startTime
                if (elapsedTime in start until start + targetTimes[index]) {
                    telemetry.addData("mode",index)
                    telemetry.update()
                    when (index) {
                        0 -> {
                            // ちょっと後ろに進む
                            state.holderIsOpen = false
                            state.leftStickX = 0.5
                            state.leftStickY = 0.0
                            telemetry.addData("mode",index)
                            telemetry.update()
                        }
                        1 -> {
                            // ちょっと前に進む
                            state.leftStickX = 0.0
                            state.leftStickY = -0.5
                            telemetry.addData("mode",index)
                            telemetry.update()
                        }
                        2 -> {
                            // また横に進む
                            state.leftStickX = - 0.5
                            state.leftStickY = 0.0
                            telemetry.addData("mode",index)
                            telemetry.update()
                        }
                        3 -> {
                            // さらに前に進む
                            state.leftStickX = 0.0
                            state.leftStickY = -0.5
                            telemetry.addData("mode",index)
                            telemetry.update()
                        }
                        4 -> {
                            // Sliderの動作と各種設定
                            state.leftStickX = 0.0
                            state.leftStickY = 0.0
                            state.sliderPower = Const.Slider.Speed.targetToPosition
                            state.leftSliderTargetPosition = Const.Slider.Position.top
                            state.rightSliderTargetPosition = Const.Slider.Position.top
                            state.liftIsUp = true
                            state.flipIsUpward = true
                            telemetry.addData("mode",index)
                            telemetry.update()
                        }
                        5 -> {
                            // Holderを開く
                            state.holderIsOpen = true
                            state.leftStickX = 0.0
                            state.leftStickY = 0.0
                            telemetry.addData("mode",index)
                            telemetry.update()
                        }
                        6 -> {
                            // Sliderを戻す
                            state.leftStickX = 0.0
                            state.leftStickY = 0.0
                            state.leftSliderTargetPosition = 0
                            state.rightSliderTargetPosition = 0
                            state.liftIsUp = false
                            state.flipIsUpward = false
                            telemetry.addData("mode",index)
                            telemetry.update()
                        }
                        7 ->{
                            state.leftStickY =  0.5
                            telemetry.addData("mode",index)
                            telemetry.update()
                        }
                        8 -> {
                            // Holderを閉じる
                            state.holderIsOpen = false
                            telemetry.addData("mode",index)
                            telemetry.update()
                        }
                        9 ->{
                            state.leftStickY = -0.5
                            telemetry.addData("mode",index)
                            telemetry.update()
                        }
                        10 -> {
                            // Sliderを再び上げる
                            state.leftStickX = 0.0
                            state.leftStickY = 0.0
                            state.leftSliderTargetPosition = Const.Slider.Position.top
                            state.rightSliderTargetPosition = Const.Slider.Position.top
                            state.liftIsUp = true
                            state.flipIsUpward = true
                        }
                        11 -> {
                            // Holderを開く
                            state.holderIsOpen = true
                            state.leftStickX = 0.0
                            state.leftStickY = 0.0
                        }
                        12 -> {
                            // Sliderを戻す
                            state.leftSliderTargetPosition = 0
                            state.rightSliderTargetPosition = 0
                            state.liftIsUp = false
                            state.flipIsUpward = false
                        }
                        13 -> {
                            // 500ミリ秒横に進む
                            state.leftStickX = 0.5
                            state.leftStickY = 0.0
                        }
                        14 -> {
                            // 500ミリ秒前に進む
                            state.leftStickX = 0.0
                            state.leftStickY = -0.5
                        }
                        15 -> {
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
