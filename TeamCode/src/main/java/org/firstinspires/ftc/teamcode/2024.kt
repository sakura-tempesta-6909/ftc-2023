package org.firstinspires.ftc.teamcode

import org.firstinspires.ftc.teamcode.Mecanum.LinearTeleop
import org.firstinspires.ftc.teamcode.armSlider.ArmSlider
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo

@TeleOp(name = "all")
class all : LinearOpMode() {

    // アームおよびスライダーの制御に関する定数
//    private val armLimitMin = 0
//    private val armLimitMax = 700
//    private val sliderLimitMin: Int = 0
//    private val sliderLimitMax: Int = 2100

    // ハードウェアの宣言
    private lateinit var armMotor: DcMotor
    private lateinit var armEndServo: Servo
    private lateinit var holderServo: Servo
    private lateinit var leftSlider: DcMotor
    private lateinit var rightSlider: DcMotor
    private lateinit var leftFront: DcMotor
    private lateinit var leftRear: DcMotor
    private lateinit var rightFront: DcMotor
    private lateinit var rightRear: DcMotor

    private fun initializeHardware() {
        // アーム関連のハードウェア初期化
        armMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_3")
        armMotor.direction = DcMotorSimple.Direction.FORWARD
        armMotor.power = 0.0

        armEndServo = hardwareMap.get(Servo::class.java, "servo_0")
        holderServo = hardwareMap.get(Servo::class.java, "servo_1")

        // スライダー関連のハードウェア初期化
        leftSlider = hardwareMap.get(DcMotor::class.java, "ex-motor_0")
        leftSlider.direction = DcMotorSimple.Direction.REVERSE

        rightSlider = hardwareMap.get(DcMotor::class.java, "ex-motor_1")
        rightSlider.direction = DcMotorSimple.Direction.FORWARD

        leftSlider.power = 0.0
        rightSlider.power = 0.0

        // Mecanum ロボットのハードウェア初期化
        leftFront = hardwareMap.get(DcMotor::class.java, "motor_0")
        leftFront.direction = DcMotorSimple.Direction.FORWARD
        leftRear = hardwareMap.get(DcMotor::class.java, "motor_2")
        leftRear.direction = DcMotorSimple.Direction.FORWARD
        rightFront = hardwareMap.get(DcMotor::class.java, "motor_1")
        rightFront.direction = DcMotorSimple.Direction.REVERSE
        rightRear = hardwareMap.get(DcMotor::class.java, "motor_3")
        rightRear.direction = DcMotorSimple.Direction.REVERSE
    }

    fun initializeArm() {
        armMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        armMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER

        armMotor.targetPosition = 0
        armEndServo.position = 0.0
        holderServo.position = 0.0

        armMotor.targetPosition = 0
        armMotor.power = 0.5

        armMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
    }

    fun initializeSlider() {
        leftSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightSlider.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        leftSlider.mode = DcMotor.RunMode.RUN_USING_ENCODER
        rightSlider.mode = DcMotor.RunMode.RUN_USING_ENCODER

        leftSlider.targetPosition = 0
        rightSlider.targetPosition = 0

        leftSlider.power = 0.5
        rightSlider.power = 0.5

        leftSlider.mode = DcMotor.RunMode.RUN_TO_POSITION
        rightSlider.mode = DcMotor.RunMode.RUN_TO_POSITION
    }


    override fun runOpMode() {

        initializeHardware()
        initializeArm()
        initializeSlider()
        waitForStart()

        while (opModeIsActive()) {
            val x = gamepad1.left_stick_x // 左右反転する場合は - を削除
            val y = gamepad1.left_stick_y // 上下反転する場合は - を削除
            val rx = gamepad1.right_stick_x // 左右反転する場合は - を削除

            val dpad_up = gamepad1.dpad_up
            val dpad_down = gamepad1.dpad_down
            val gamepad_x = gamepad1.x
            val gamepad_y = gamepad1.y
            // 各機能の制御
            ArmSlider.controlArmSlider(dpad_up, dpad_down, armMotor, leftSlider, rightSlider, armEndServo)
            ArmSlider.controlHolder(gamepad_x, gamepad_y, holderServo)
            Mecanum.driveMecanum(leftFront, leftRear, rightFront, rightRear, x, y, rx)
            telemetry.addData("Mode", "running")
            telemetry.addData("ArmLeftSliderPosition", leftSlider.currentPosition)
            telemetry.addData("ArmLeftSliderTarget", leftSlider.targetPosition)
            telemetry.addData("ArmRightSliderPosition", rightSlider.currentPosition)
            telemetry.addData("ArmRightSliderTarget", rightSlider.targetPosition)
            telemetry.addData("RightSliderMotorPower",rightSlider.power)
            telemetry.addData("LeftSliderMotorPower",leftSlider.power)
            telemetry.addData("ArmMotorPower",armMotor.power)
            telemetry.update()
        }
    }
}

