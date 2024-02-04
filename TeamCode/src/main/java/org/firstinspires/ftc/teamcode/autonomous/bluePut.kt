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

@Autonomous(name = "bluePut", group = "put")
class bluePut: LinearOpMode() {
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
                500,
                //2
                Const.Autonomous.verticalMovement,
                // ...
                1575,
                1000,
        )

        // ターゲット時間ごとの開始時間
        val startTimes = targetTimes.scan(0L) { acc, targetTime -> acc + targetTime }

        // ステートの初期化
        state.stateInit()

        // ゲーム開始待機
        waitForStart()

        // ゲーム開始時刻
        val startTime = System.currentTimeMillis()
        var beforeTime = System.currentTimeMillis()
        var phaseCount = 0
        while (opModeIsActive()) {
            telemetry.addData("Status", "Run Time: $runtime")
            telemetry.addData("phase",phaseCount)
            state.stateReset()
            //現在時刻から前回phaseが切り替わったときの時間を引いて、それが設定した時間以上だったら、phaseを次に進める
            if (System.currentTimeMillis() - beforeTime >= targetTimes[phaseCount] && phaseCount < 4) {
                phaseCount++
                beforeTime = System.currentTimeMillis()
            }
            // センサーの値の取得
            components.forEach { component ->
                component.readSensors(state)
            }
            when (phaseCount) {
                0 -> {
                    // ちょっと後ろに進む
                    state.holderIsOpen = false
                    state.leftStickX = 0.5
                    state.leftStickY = 0.02
                }

                1 -> {
                    state.sliderPower = Const.Slider.Speed.targetToPosition
                    state.leftSliderTargetPosition = Const.Slider.Position.medium
                    state.rightSliderTargetPosition = Const.Slider.Position.medium
                    // ちょっと前に進む
                    state.leftStickX = 0.0
                    state.leftStickY = 0.0
                }

                2 -> {
                    state.leftStickX = 0.0
                    state.leftStickY = 0.3
                }

                3 -> {
                    // Sliderの動作と各種設定
                    state.leftStickX = 0.5
                    state.leftStickY = 0.0
                }

                4 -> {
                    // Sliderの動作と各種設定
                    state.leftStickX = 0.0
                    state.leftStickY = 0.0
                    state.leftSliderTargetPosition = 0
                    state.rightSliderTargetPosition = 0
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
    }
}