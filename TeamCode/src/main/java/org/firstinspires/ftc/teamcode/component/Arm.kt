package org.firstinspires.ftc.teamcode.component

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.state.State
import org.firstinspires.ftc.teamcode.subClass.Const

class Arm(hardwareMap: HardwareMap) : Component {
    private lateinit var holder:Servo
    init {
        holder = hardwareMap.get(Servo::class.java,Const.Arm.Motor.Name.holder)

        holder.direction = Const.Arm.Motor.Direction.holder

    }
    override fun autonomousInit() {
        TODO("Not yet implemented")
    }

    override fun teleopInit() {
        TODO("Not yet implemented")
    }

    override fun disabledInit() {
        TODO("Not yet implemented")
    }

    override fun testInit() {
        TODO("Not yet implemented")
    }

    override fun readSensors(state: State) {
        TODO("Not yet implemented")
    }

    override fun applyState(state: State) {
        if (state.holderIsOpen){
            holder.position = 0.0
        }else{
            holder.position = 0.7
        }
    }
}