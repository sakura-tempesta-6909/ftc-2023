package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

@Autonomous(name = "First_Autonomous")
class FirstAuto : LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        waitForStart()
    }
}