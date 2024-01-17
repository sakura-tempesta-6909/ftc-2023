package org.firstinspires.ftc.teamcode.armSlider

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

@TeleOp(name = "SliderNew")
class Slidernew : LinearOpMode() {

    // スライダーの移動範囲の制限
    private val sliderLimitMin: Int = 500
    private val sliderLimitMax: Int = 2400

    // プログラムの状態を表すenum
    private var currentState = State.IDLE
    private enum class State {
        IDLE,
        MOVE_TO_POSITION_A,
        MOVE_TO_POSITION_B
    }

    override fun runOpMode() {
        // モーターの初期設定
        val leftSlider: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_1")
        leftSlider.direction = DcMotorSimple.Direction.REVERSE

        val rightSlider: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_2")
        rightSlider.direction = DcMotorSimple.Direction.FORWARD

        leftSlider.power = 0.0
        rightSlider.power = 0.0

        // プログラムの実行を待つ
        waitForStart()

        // モーターエンコーダーの設定
        leftSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        leftSlider.mode = DcMotor.RunMode.RUN_USING_ENCODER
        rightSlider.mode = DcMotor.RunMode.RUN_USING_ENCODER

        // テレメトリに実行中であることを表示
        telemetry.addData("Mode", "running")
        telemetry.update()

        leftSlider.mode = DcMotor.RunMode.RUN_TO_POSITION
        rightSlider.mode = DcMotor.RunMode.RUN_TO_POSITION

        // メインループ
        while (opModeIsActive()) {
            // 状態に基づいてボタン入力を処理
            when (currentState) {
                State.IDLE -> {
                    if (gamepad1.dpad_down) {
                        currentState = State.MOVE_TO_POSITION_A
                    } else if (gamepad1.dpad_up) {
                        currentState = State.MOVE_TO_POSITION_B
                    }
                }
                State.MOVE_TO_POSITION_A, State.MOVE_TO_POSITION_B -> {
                    // 現在の状態に基づいて目標位置を設定
                    val newLeftTarget = if (currentState == State.MOVE_TO_POSITION_A) 500 else 2000
                    val newRightTarget = if (currentState == State.MOVE_TO_POSITION_A) 500 else 2000

                    // 目標位置を制限範囲内に調整
                    leftSlider.targetPosition = newLeftTarget.coerceIn(sliderLimitMin, sliderLimitMax)
                    rightSlider.targetPosition = newRightTarget.coerceIn(sliderLimitMin, sliderLimitMax)

                    // モーターに動力を与える
                    leftSlider.power = 0.5
                    rightSlider.power = 0.5

                    // 状態をIDLEに戻す
                    currentState = State.IDLE
                }
            }

            // 現在の位置やパワー、目標位置をテレメトリに表示
            telemetry.addData("currentState",currentState)
            telemetry.addData("左モーターの位置", leftSlider.currentPosition)
            telemetry.addData("右モーターの位置", rightSlider.currentPosition)
            telemetry.addData("左モーターのパワー", leftSlider.power)
            telemetry.addData("右モーターのパワー", rightSlider.power)
            telemetry.addData("左モーターの目標", leftSlider.targetPosition)
            telemetry.addData("右モーターの目標", rightSlider.targetPosition)
            telemetry.update()
        }
    }
}
