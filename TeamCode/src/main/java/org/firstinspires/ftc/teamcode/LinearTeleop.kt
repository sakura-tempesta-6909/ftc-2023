package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

@TeleOp
class LinearTeleop : LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {

        waitForStart()
        telemetry.addData("Mode", "running")
        telemetry.update()
        while (opModeIsActive()) {
            

            }
        }
    }
