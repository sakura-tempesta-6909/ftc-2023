import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
@TeleOp(name = "Linear Encoder Test2")
class Encoder2 : LinearOpMode() {
    private lateinit var motor: DcMotor
    private var ticks = -2000
    private var newTarget: Double = 0.0

    @Throws(InterruptedException::class)
    override fun runOpMode() {
        motor = hardwareMap.get(DcMotor::class.java, "motor_3")
        telemetry.addData("Hardware: ", "Initialized")
        motor.mode = DcMotor.RunMode.RUN_USING_ENCODER

        waitForStart()
        motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor.mode = DcMotor.RunMode.RUN_USING_ENCODER
        newTarget = (ticks / 2).toDouble()
        motor.targetPosition = newTarget.toInt()
        motor.power = 0.8
        motor.mode = DcMotor.RunMode.RUN_TO_POSITION

        fun tracker() {
            motor.targetPosition = -1000
            motor.power = 0.8
            motor.mode = DcMotor.RunMode.RUN_TO_POSITION
        }

        while (opModeIsActive()) {

            telemetry.addData("Motor Ticks: ", motor.currentPosition)
            if (gamepad1.x && gamepad1.y) {
                motor.power = 0.0
                telemetry.addData("Status", "X&Y Button Pressed")
            } else if (gamepad1.x && !gamepad1.y) {
                motor.power = 0.5
                motor.targetPosition = 0
                telemetry.addData("Status", "X Button Pressed")
            } else if (!gamepad1.x && gamepad1.y) {
                motor.power = 0.5
                motor.targetPosition = -3200
                telemetry.addData("Status", "Y Button Pressed")
            }
            if (gamepad1.dpad_down && gamepad1.dpad_up) {
                motor.power = 0.0
                telemetry.addData("Status", "X&Y Button Pressed")
            } else if (gamepad1.dpad_down && !gamepad1.dpad_up) {
                motor.power = 0.5
                motor.targetPosition = minOf(0, motor.targetPosition + 10)
                telemetry.addData("Status", "X Button Pressed")
            } else if (!gamepad1.dpad_down && gamepad1.dpad_up) {
                motor.power = 0.5
                motor.targetPosition = maxOf(-4000, motor.targetPosition - 10)
                telemetry.addData("Status", "Y Button Pressed")
            }

            telemetry.update()
            if (gamepad1.b) {
                tracker()
            }

            if (10 > motor.currentPosition && motor.currentPosition > -10) {
                telemetry.addData("Status", "Arm can't go down")
            }
            if (-3990 > motor.currentPosition && motor.currentPosition > -4010) {
                telemetry.addData("Status", "Arm can't go up")
            }
        }
    }
}
