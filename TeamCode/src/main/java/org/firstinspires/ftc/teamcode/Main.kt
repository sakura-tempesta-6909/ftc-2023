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
    lateinit var traj2:Trajectory
    lateinit var traj3:Trajectory
    lateinit var traj4:Trajectory

    //インチで記載する(base:24)
    override fun init() {
        drive = SampleMecanumDrive(hardwareMap)
        val startPose = Pose2d(-36.0, 60.0, Math.toRadians(0.0))
        drive.poseEstimate = startPose
        traj1 = drive.trajectoryBuilder(startPose)
                .splineTo(Vector2d(-36.0,12.0),0.0)
                .addDisplacementMarker{ drive.followTrajectoryAsync(traj2) }
                .build()
        traj2 = drive.trajectoryBuilder(traj1.end())
                .splineTo(Vector2d(36.0,12.0),90.0)
                .addDisplacementMarker{ drive.followTrajectoryAsync(traj3) }
                .build()
        traj3 = drive.trajectoryBuilder(traj2.end())
                .splineTo(Vector2d(-36.0,60.0),9.0)
                .build()
        drive.followTrajectoryAsync(traj1)
    }

    override fun loop() {
        drive.update()
    }

}