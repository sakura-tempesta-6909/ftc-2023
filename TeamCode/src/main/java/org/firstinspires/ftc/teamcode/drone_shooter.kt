package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
@TeleOp(name = "drone shooter")
class drone_shooter : LinearOpMode() {

    override fun runOpMode(){

        val droneShooter : Servo = hardwareMap.get(Servo::class.java,"servo_2")
        droneShooter.direction = Servo.Direction.REVERSE

        waitForStart()

        while (opModeIsActive()) {

            if(gamepad1.x){
             droneShooter.position = 0.2
             } else {
                 droneShooter.position = 0.0
             }

            telemetry.addData("servoPosition",droneShooter.position)
        }
    }
}