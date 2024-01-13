import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo

//「Linear Encoder Test」という名前の TeleOpを定義
@TeleOp(name = "Linear Encoder Test")
class Encoder : LinearOpMode() {

    //motorという名前のDcMotor変数を宣言して、他の変数の数値を初期化
    private lateinit var motor: DcMotor
    private lateinit var servo1: Servo
    private lateinit var servo2: Servo
    private var ticks = -2000
    private var newTarget: Double = 0.0

    //
    @Throws(InterruptedException::class)
    override fun runOpMode() {

        //モーターを初期化
        motor = hardwareMap.get(DcMotor::class.java, "motor_3")
        servo1 = hardwareMap.get(Servo::class.java, "servo1")
        servo2 = hardwareMap.get(Servo::class.java, "servo2")

        //テレメトリに初期化メッセージを表示
        telemetry.addData("Hardware: ", "Initialized")


        //エンコーダを使用して実行するように設定
        motor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        //スタートボタンが押されてからの処理
        waitForStart()

        //もう一度エンコーダを使用して実行するように設定
        motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor.mode = DcMotor.RunMode.RUN_USING_ENCODER


        //ticksでモーターの新しいターゲット位置を設定
        newTarget = (ticks / 2).toDouble()


        //モーターのターゲット位置とパワーを設定し、モードをターゲット位置まで動かす
        motor.targetPosition = newTarget.toInt()
        motor.power = 0.8
        motor.mode = DcMotor.RunMode.RUN_TO_POSITION
        servo1.position = 0.5
        servo2.position = 0.5


        //trackerという名前の関数を定義
        //モーターのターゲット位置を初期位置に設定
        fun tracker() {
            motor.targetPosition = -1000
            motor.power = 0.8
            motor.mode = DcMotor.RunMode.RUN_TO_POSITION
        }

        //OpModeの間、以下のコードを実行
        while (opModeIsActive()) {
            telemetry.addData("servo1", servo1.position)
            telemetry.addData("servo2", servo2.position)
            //モーターの現在の位置をテレメトリに表示
            telemetry.addData("Motor Ticks: ", motor.currentPosition)
            //XボタンとYボタンの入力からモーターを制御
            //ゲームパッドのXボタンとYボタンが押されているときにモーターの速度を0にする
            if (gamepad1.x && gamepad1.y) {
                motor.power = 0.0
                telemetry.addData("Status", "X&Y Button Pressed")
                //ゲームパッドのXボタンが押されていて、Yボタンが押されていないときにモーターのターゲット位置を0にする
            } else if (gamepad1.x && !gamepad1.y) {
                motor.power = 0.5
                motor.targetPosition = 0
                telemetry.addData("Status", "X Button Pressed")
                //ゲームパッドのYボタンが押されていて、Xボタンが押されていないときにモーターのターゲット位置を-3200にする
            } else if (!gamepad1.x && gamepad1.y) {
                motor.power = 0.5
                motor.targetPosition = -3200
                telemetry.addData("Status", "Y Button Pressed")
            }
            //dpadの入力からモーターを制御
            //十字ボタンの下と十字ボタンの上が押されているときにモーターの速度を0にする
            if (gamepad1.dpad_down && gamepad1.dpad_up) {
                motor.power = 0.0
                telemetry.addData("Status", "X&Y Button Pressed")
                //十字ボタンの下が押されていて、十字ボタンの上が押されていないときにモーターのターゲット位置を10大きくする
            } else if (gamepad1.dpad_down && !gamepad1.dpad_up) {
                motor.power = 0.5
                motor.targetPosition = minOf(0, motor.targetPosition + 10)
                telemetry.addData("Status", "X Button Pressed")
                //十字ボタンの上が押されていて、十字ボタンの下が押されていないときにモーターのターゲット位置を10小さくする
            } else if (!gamepad1.dpad_down && gamepad1.dpad_up) {
                motor.power = 0.5
                motor.targetPosition = maxOf(-4000, motor.targetPosition - 10)
                telemetry.addData("Status", "Y Button Pressed")
            }


            //テレメトリの表示を更新
            telemetry.update()

            //Bボタンが押された場合にtracker関数を呼び出す
            if (gamepad1.b) {
                tracker()
            }

            // モーターの位置が下がらないか上がらない時に警告文を表示する
            if (10 > motor.currentPosition && motor.currentPosition > -10) {
                telemetry.addData("Status", "Arm can't go down")
            }
            if (-3990 > motor.currentPosition && motor.currentPosition > -4010) {
                telemetry.addData("Status", "Arm can't go up")
            }
            //左のトリガーが押された場合に閉じる
            if (gamepad1.left_trigger > 0) {
                servo1.position = 0.1
                servo2.position = 0.9
            //右のトリガーが押された場合に開く
            } else if (gamepad1.right_trigger > 0) {
                servo1.position = 0.6
                servo2.position = 0.4
            //右バンパーが押されている間,開き続ける
            } else if (gamepad1.right_bumper) {
                servo1.position = minOf(0.7,servo1.position+0.005)
                servo2.position = maxOf(0.3,servo2.position - 0.005)
            //左バンパーが押されている間,閉まり続ける
            } else if (gamepad1.left_bumper) {
                servo1.position -= 0.005
                servo2.position += 0.005
            }
        }
    }
}