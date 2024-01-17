package org.firstinspires.ftc.teamcode
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
class motor {


    @TeleOp(name = "motor")
    class LinearTeleop : LinearOpMode() {
        override fun runOpMode() {
            val leftFront: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_0")
            leftFront.direction = DcMotorSimple.Direction.REVERSE
            val leftRear: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_2")
            leftRear.direction = DcMotorSimple.Direction.REVERSE
            val rightFront: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_1")
            rightFront.direction = DcMotorSimple.Direction.FORWARD
            val rightRear: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_3")
            rightRear.direction = DcMotorSimple.Direction.FORWARD


            waitForStart()

            telemetry.addData("Mode", "running")
            telemetry.update()

            while (opModeIsActive()) {
                if (gamepad1.x){
                    leftFront.power = 0.5
                    leftRear.power = 0.0
                    rightFront.power = 0.0
                    rightRear.power = 0-.0
                }
                if (gamepad1.y){
                    leftFront.power = 0.0
                    leftRear.power = 0.5
                    rightFront.power = 0.0
                    rightRear.power = 0-.0
                }
                if (gamepad1.a){
                    leftFront.power = 0.0
                    leftRear.power = 0.0
                    rightFront.power = 0.5
                    rightRear.power = 0-.0
                }
                if (gamepad1.b){
                    leftFront.power = 0.0
                    leftRear.power = 0.0
                    rightFront.power = 0.0
                    rightRear.power = 0.5
                }

                telemetry.update()


            }
        }
    }

}