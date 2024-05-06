package org.firstinspires.ftc.teamcode.component

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.state.State
import org.firstinspires.ftc.teamcode.subClass.Const
import kotlin.math.abs

class Arm(hardwareMap: HardwareMap) : Component {
    private var lift: DcMotor
    private var holder: Servo
    private var flip:Servo


    init {
        lift = hardwareMap.get(DcMotor::class.java, Const.Arm.Motor.Name.lift)
        holder = hardwareMap.get(Servo::class.java, Const.Arm.Motor.Name.holder)
        flip = hardwareMap.get(Servo::class.java,Const.Arm.Motor.Name.flip)

        lift.direction = Const.Arm.Motor.Direction.lift
        lift.power = 0.0
        lift.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        lift.targetPosition = 0
        lift.mode = DcMotor.RunMode.RUN_TO_POSITION

        holder.direction = Const.Arm.Motor.Direction.holder
        flip.direction = Const.Arm.Motor.Direction.flip

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
        //Stateにリフトの現在位置を反映
        state.liftCurrentPosition = lift.currentPosition
    }

    override fun applyState(state: State) {

        if (state.liftIsUp) {
            //リフトを上げる際の処理
            if (abs((lift.currentPosition - Const.Arm.Motor.Position.liftUpperLimit)) < 50) {
                //リフトが既に上がっている場合モーターに力を加えない
                lift.power = 0.0
            }else{
                //リフトが上がっていない場合モーターに力を加える
                lift.power = Const.Arm.Motor.Power.liftMoving
            }
            //リフトの目標位置を上側に設定
            lift.targetPosition = Const.Arm.Motor.Position.liftUpperLimit
        } else {
            //リフトを下げる際の処理
            if (lift.currentPosition < Const.Arm.Motor.Position.liftdown){
                //リフトが既に下がっている場合モーターに力を加えない
                lift.power = 0.0
            }else{
                //リフトが上がっている場合モーターに力を加える
                lift.power = Const.Arm.Motor.Power.liftMoving
            }
            //リフトの目標位置を下に設定
            lift.targetPosition = 0
        }
        if (state.holderIsOpen) {
            //ホルダーを開ける場合サーボモータの位置を変更
            holder.position = 0.0
        } else {
            //ホルダーを閉める場合サーボモータの位置を変更
            holder.position = Const.Arm.Motor.Position.holderIsclosed
        }
        if (state.flipIsUpward ){
            if (lift.currentPosition < Const.Arm.Motor.Position.flipIsRotatable) {
                //フリップを上向きにするとき、リフトがある程度上がっていれば上を向かせる
                flip.position = Const.Arm.Motor.Position.flipIsUpper
            }
        }else{
            //フリップを下向きにする
            flip.position = 0.0
        }

    }
}