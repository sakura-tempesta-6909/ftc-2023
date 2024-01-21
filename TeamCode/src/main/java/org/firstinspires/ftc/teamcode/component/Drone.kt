package org.firstinspires.ftc.teamcode.component

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.state.State
import org.firstinspires.ftc.teamcode.subClass.Const

class Drone(hardwareMap: HardwareMap) : Component {
    private var droneLauncher: Servo
    private var initialPosition:Double = 0.0

    init {
        droneLauncher = hardwareMap.get(Servo::class.java,Const.Drone.Name.droneLauncher)
        var initialPosition = droneLauncher.position
        droneLauncher.direction = Servo.Direction.FORWARD
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

    }

    override fun applyState(state: State) {
        if (state.droneIsShot){
            droneLauncher.position = initialPosition+Const.Drone.Position.shot
        }else{
            droneLauncher.position = initialPosition
        }
    }
}