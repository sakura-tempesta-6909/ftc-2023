package org.firstinspires.ftc.teamcode.component

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.IMU
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit
import org.firstinspires.ftc.teamcode.state.State
import org.firstinspires.ftc.teamcode.subClass.Const
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class Drive(hardwareMap: HardwareMap) : Component {
    private var leftFront:DcMotor
    private var leftRear:DcMotor
    private var rightFront:DcMotor
    private var rightRear:DcMotor
    private var imu:IMU
    init {
        leftFront = hardwareMap.get(DcMotor::class.java,Const.Drive.Name.leftFront)
        leftFront.direction = Const.Drive.Direction.leftFront
        rightFront = hardwareMap.get(DcMotor::class.java,Const.Drive.Name.rightFront)
        rightFront.direction = Const.Drive.Direction.rightFront
        leftRear = hardwareMap.get(DcMotor::class.java, Const.Drive.Name.leftRear)
        leftRear.direction = Const.Drive.Direction.leftRear
        rightRear = hardwareMap.get(DcMotor::class.java,Const.Drive.Name.rightRear)
        rightRear.direction = Const.Drive.Direction.rightRear

        // IMU（慣性計測装置）の初期化
        imu = hardwareMap.get(IMU::class.java,Const.Drive.Name.imu)
        imu.resetYaw()
        val parameters = IMU.Parameters(
                RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        )
        imu.initialize(parameters)
    }
    override fun autonomousInit() {
        // TODO("Not yet implemented")
    }

    override fun teleopInit() {
        // TODO("Not yet implemented")
    }

    override fun disabledInit() {
        // TODO("Not yet implemented")
    }

    override fun testInit() {
        // TODO("Not yet implemented")
    }

    override fun readSensors(state: State) {
        // TODO("Not yet implemented")
    }

    override fun applyState(state: State) {
        if (state.leftSliderCurrentPosition < Const.Slider.Position.medium){
            state.driveMagnification = Const.Drive.Speed.highGear
        }else{
            state.driveMagnification = Const.Drive.Speed.lowGear
        }

        // ロボットの方向を取得
        val botHeading = imu.robotYawPitchRollAngles.getYaw(AngleUnit.RADIANS)
        if (state.imuIsReset) {
            imu.resetYaw()
        }
        val x = state.leftStickX
        val y = state.leftStickY
        val rx = - state.rightStickX

        // Field Centric座標系に変換
        var rotX = x * cos(botHeading) - y * sin(botHeading)
        val rotY = - x * sin(botHeading) - y * cos(botHeading)

        rotX *= 1.1

        // パワーの正規化
        val denominator =
                (abs(rotY) + abs(rotX) + abs(rx)).coerceAtLeast(1.0)

        state.leftFrontPower = (rotY + rotX + rx) / denominator * state.driveMagnification
        state.leftRearPower = (rotY - rotX + rx) / denominator * state.driveMagnification
        state.rightFrontPower = (rotY - rotX - rx) / denominator * state.driveMagnification
        state.rightRearPower = (rotY + rotX - rx) / denominator * state.driveMagnification

        leftFront.power = state.leftFrontPower
        rightFront.power = state.rightFrontPower
        leftRear.power = state.leftRearPower
        rightRear.power = state.rightRearPower


    }
}