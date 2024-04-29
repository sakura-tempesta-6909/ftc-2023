package org.firstinspires.ftc.teamcode

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive


@Autonomous(name = "AutoTest")
class Main : OpMode() {
    lateinit var drive: SampleMecanumDrive
    lateinit var traj1:Trajectory
    override fun init() {
        drive = SampleMecanumDrive(hardwareMap)
        val startPose = Pose2d(0.0, 0.0, Math.toRadians(0.0))
        drive.poseEstimate = startPose
        traj1 = drive.trajectoryBuilder(startPose)
                .forward(10.0)
                .build()
        drive.followTrajectoryAsync(traj1);
    }

    override fun loop() {
        drive.update()
    }


}