package org.firstinspires.ftc.teamcode.autonomous

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.acmerobotics.roadrunner.trajectory.Trajectory
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.component.Arm
import org.firstinspires.ftc.teamcode.component.Component
import org.firstinspires.ftc.teamcode.component.Slider
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.state.SliderStates
import org.firstinspires.ftc.teamcode.state.State
import org.firstinspires.ftc.teamcode.subClass.Const
import org.firstinspires.ftc.teamcode.subClass.Util
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder

@Autonomous(name = "PutPixel")
class PutPixel : OpMode() {

    private val runtime = ElapsedTime()
    private val components = ArrayList<Component>()
    private val state = State()

    lateinit var drive: SampleMecanumDrive
    lateinit var traj1: TrajectorySequence
    lateinit var traj2: TrajectorySequence
    lateinit var traj3: Trajectory
    lateinit var traj4: Trajectory
    lateinit var traj5: TrajectorySequence

    override fun init() {
        telemetry.addData("Status", "Initializing")
        components.add(Slider(hardwareMap))
        components.add(Arm(hardwareMap))
        state.stateInit()
        drive = SampleMecanumDrive(hardwareMap)
        val startPose = Pose2d(-36.0, 60.0, Math.toRadians(90.0))
        drive.poseEstimate = startPose
        traj1 = drive.trajectorySequenceBuilder(startPose)
                //ピクセルをつかむ
                .addTemporalMarker{
                    state.holderIsOpen = false
                }
                //Spikeのセンターへ移動
                .lineToLinearHeading(Pose2d(-36.0, 32.0, Math.toRadians(90.0)))
                //ピクセルを設置
                .addTemporalMarker{
                    state.holderIsOpen = true
                }
                .waitSeconds(0.2)
                //少し後退
                .lineToLinearHeading(Pose2d(-36.0, 36.0, Math.toRadians(90.0)))
                //手前によける&回転
                .lineToLinearHeading(Pose2d(-65.0, 36.0, Math.toRadians(0.0)))
                //ピクセルをつかむ
                .addTemporalMarker{
                    state.holderIsOpen = false
                }
                .waitSeconds(0.2)
                //中央へ移動
                .lineToConstantHeading(Vector2d(-50.0, 12.0))
                //バックステージへ向かう
                .lineToLinearHeading(Pose2d(36.0, 12.0, Math.toRadians(0.0)))
                .lineToLinearHeading(Pose2d(36.0, 36.0, Math.toRadians(0.0)))
                .lineToLinearHeading(Pose2d(46.0, 36.0, Math.toRadians(0.0)))
                .addTemporalMarker{
                    state.holderIsOpen = false
                }
                .waitSeconds(0.3)
                //スライダーを上げる
                .addTemporalMarker{
                    state.sliderState = SliderStates.MoveSliderToPosition
                    state.leftSliderTargetPosition = Const.Slider.Position.auttoMedium
                    state.rightSliderTargetPosition = Const.Slider.Position.auttoMedium
                    state.sliderPower = Const.Slider.Speed.targetToPosition
                    state.flipIsUpward = true
                    state.liftIsUp = true
                }
                .waitSeconds(1.0)
                //ホルダーを開ける
                .addTemporalMarker{
                    state.holderIsOpen = true
                }
                .waitSeconds(1.0)
                .addTemporalMarker{
                    state.flipIsUpward = false
                }
                .waitSeconds(0.3)
                //スライダーを下げる
                .addTemporalMarker{
                    state.sliderState = SliderStates.MoveSliderToPosition
                    state.leftSliderTargetPosition = 0
                    state.rightSliderTargetPosition = 0
                    state.sliderPower = Const.Slider.Speed.targetToPosition
                    state.flipIsUpward = false
                    state.liftIsUp = false
                }
                .waitSeconds(1.0)
                //ピクセルをつかむ
                .addTemporalMarker{
                    state.holderIsOpen = false
                }
                .waitSeconds(0.3)
                //スライダーを上げる
                .addTemporalMarker{
                    state.sliderState = SliderStates.MoveSliderToPosition
                    state.leftSliderTargetPosition = Const.Slider.Position.medium
                    state.rightSliderTargetPosition = Const.Slider.Position.medium
                    state.sliderPower = Const.Slider.Speed.targetToPosition
                    state.flipIsUpward = true
                    state.liftIsUp = true
                }
                .waitSeconds(1.0)
                //ホルダーを開ける
                .addTemporalMarker{
                    state.holderIsOpen = true
                }
                .waitSeconds(0.3)
                .addTemporalMarker{
                    state.flipIsUpward = false
                }
                .waitSeconds(0.3)
                //スライダーを下げる
                .addTemporalMarker{
                    state.sliderState = SliderStates.MoveSliderToPosition
                    state.leftSliderTargetPosition = 0
                    state.rightSliderTargetPosition = 0
                    state.sliderPower = Const.Slider.Speed.targetToPosition
                    state.flipIsUpward = false
                    state.liftIsUp = false
                }
                .waitSeconds(1.0)
                .lineToLinearHeading(Pose2d(36.0, 36.0, Math.toRadians(0.0)))
                .lineToLinearHeading(Pose2d(36.0, 12.0, Math.toRadians(0.0)))
                .lineToLinearHeading(Pose2d(42.0, 12.0, Math.toRadians(0.0)))
                .waitSeconds(4.0)
                .lineToLinearHeading(Pose2d(-36.0, 60.0, Math.toRadians(90.0)))
                .build()
        drive.followTrajectorySequenceAsync(traj1)
        telemetry.addData("Status", "Initialized")
    }

    /*
      * ドライバーがINITを押した後、PLAYを押す前に繰り返し実行するコード
      */
    override fun init_loop() {
        state.stateReset()
        //センサーの値の取得
        components.forEach { component ->
            component.readSensors(state)
        }
        //数値を出力
        Util.sendLog(state, telemetry)
    }

    /*
  * 開始時に一度だけ実行される
  */
    override fun start() {
        runtime.reset()
        state.stateInit()
        state.holderIsOpen = false
    }

    /*
       * Enableの間ずっと実行される
       * while (opModeIsActive()) と同じ
       */
    override fun loop() {
        telemetry.addData("Status", "Run Time: $runtime")
        state.stateReset()
        //センサーの値の取得
        components.forEach { component ->
            component.readSensors(state)
        }
        drive.update()
        //Stateを適用
        components.forEach { component ->
            component.applyState(state)
        }
        telemetry.addData("time", runtime.time())
        //数値を出力
        Util.sendLog(state, telemetry)
    }
}