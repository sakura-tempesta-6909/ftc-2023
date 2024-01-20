package org.firstinspires.ftc.teamcode.armSlider

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo

@TeleOp(name = "armSlider")
class ArmSlider : LinearOpMode() {

    lateinit var armMotor: DcMotor
    lateinit var armEndServo: Servo
    lateinit var holderServo: Servo
    lateinit var leftSlider: DcMotor
    lateinit var rightSlider: DcMotor

    companion object {

        fun controlArmSlider(dpad_up: Boolean,
                             dpad_down: Boolean,
                             armMotor: DcMotor,
                             leftSlider: DcMotor,
                             rightSlider: DcMotor,
                             armEndServo:Servo) {
            if (dpad_up) {
                armMotor.targetPosition = 680.coerceIn(0, 700)
                armMotor.power = 0.2

                leftSlider.targetPosition = 2000.coerceIn(0, 2100)
                rightSlider.targetPosition = 2000.coerceIn(0, 2100)

                leftSlider.power = 0.7
                rightSlider.power = 0.7

                if (armMotor.currentPosition > 300) {
                    armEndServo.position = 0.7.coerceIn(0.0, 1.0)
                }
            } else if (dpad_down) {

                armMotor.targetPosition = 0.coerceIn(0, 700)
                armMotor.power = 0.2

                leftSlider.targetPosition = 0.coerceIn(0, 2100)
                rightSlider.targetPosition = 0.coerceIn(0, 2100)

                leftSlider.power = 0.7
                rightSlider.power = 0.7

                if (armMotor.currentPosition > 300) {
                    armEndServo.position = 0.0.coerceIn(0.0, 1.0)
                }

            }else if (leftSlider.currentPosition < 10){
                leftSlider.power = 0.0
                rightSlider.power = 0.0
            }
        }

        fun controlHolder(gamepad_x: Boolean,
                          gamepad_y: Boolean,
                          holderServo:Servo) {
            if (gamepad_x) {
                holderServo.position = 0.7.coerceIn(0.0,1.0)
            } else if (gamepad_y) {
                holderServo.position = 0.0.coerceIn(0.0, 1.0)
            }
        }
    }

    override fun runOpMode() {

        val armMotor: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_3")
        armMotor.direction = DcMotorSimple.Direction.FORWARD
        armMotor.power = 0.0

        val armEndServo: Servo = hardwareMap.get(Servo::class.java, "servo_0")
        val holderServo: Servo = hardwareMap.get(Servo::class.java, "servo_1")

        // スライダーのセットアップ
        val leftSlider: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_1")
        leftSlider.direction = DcMotorSimple.Direction.REVERSE

        val rightSlider: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_2")
        rightSlider.direction = DcMotorSimple.Direction.FORWARD

        leftSlider.power = 0.0
        rightSlider.power = 0.0

        // アームの初期化
        armMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        armMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER

        telemetry.addData("Mode", "running")
        telemetry.update()

        armMotor.targetPosition = 0
        armEndServo.position = 0.0
        holderServo.position = 0.0

        armMotor.mode = DcMotor.RunMode.RUN_TO_POSITION

        // スライダーの初期化
        leftSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        leftSlider.mode = DcMotor.RunMode.RUN_USING_ENCODER
        rightSlider.mode = DcMotor.RunMode.RUN_USING_ENCODER

        leftSlider.targetPosition = 0
        rightSlider.targetPosition = 0

        leftSlider.mode = DcMotor.RunMode.RUN_TO_POSITION
        rightSlider.mode = DcMotor.RunMode.RUN_TO_POSITION

        waitForStart()

        while (opModeIsActive()) {
            val dpad_up = gamepad1.dpad_up
            val dpad_down = gamepad1.dpad_down
            val gamepad_x = gamepad1.x
            val gamepad_y = gamepad1.y
            controlArmSlider(dpad_up,dpad_down,armMotor,leftSlider,rightSlider,armEndServo)
            controlHolder(gamepad_x,gamepad_y,holderServo)
        }
    }
}



