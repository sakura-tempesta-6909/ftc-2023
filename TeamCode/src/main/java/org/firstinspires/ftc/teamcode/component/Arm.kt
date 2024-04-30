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
        lift.targetPosition = 15
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
        state.liftCurrentPosition = lift.currentPosition
    }

    override fun applyState(state: State) {

        if (state.initialize) {
            lift.targetPosition = lift.targetPosition - 10
            lift.power = 0.6
        }else {
            if (state.liftIsUp) {
                if (abs((lift.currentPosition - Const.Arm.Motor.Position.liftUpperLimit)) < 50) {
                    lift.power = 0.0
                } else {
                    lift.power = 0.6
                }
                lift.targetPosition = Const.Arm.Motor.Position.liftUpperLimit
            } else {
                if (lift.currentPosition < 50) {
                    lift.power = 0.0
                } else {
                    lift.power = 0.6
                }
                lift.targetPosition = 15
            }
        }
        if (state.holderIsOpen) {
            holder.position = 0.0
        } else {
            holder.position = 0.6
        }
        if (state.flipIsUpward ){
            if (lift.currentPosition < 300) {
                flip.position = 0.65
            }
        }else{
            flip.position = 0.0
        }
    }
}