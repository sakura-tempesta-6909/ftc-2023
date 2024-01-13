package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

@Autonomous(name = "Autonomous")
class Autonomous : LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        //初期設定

        //DCモーターの初期設定
        val leftFront: DcMotor = hardwareMap.get(DcMotor::class.java, "ex-motor_0")
        leftFront.direction = DcMotorSimple.Direction.FORWARD
        val leftRear: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_2")
        leftRear.direction = DcMotorSimple.Direction.FORWARD
        val rightFront: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_0")
        rightFront.direction = DcMotorSimple.Direction.REVERSE
        val rightRear: DcMotor = hardwareMap.get(DcMotor::class.java, "motor_1")
        rightRear.direction = DcMotorSimple.Direction.REVERSE

        //servoモーターの初期設定

        waitForStart()
        //特定の場所に行く


        //ピクセルを置く

        //アームを下げる

        //アームを開く

    }
}