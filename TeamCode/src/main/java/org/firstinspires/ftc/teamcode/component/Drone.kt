package org.firstinspires.ftc.teamcode.component

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.state.State
import org.firstinspires.ftc.teamcode.subClass.Const

class Drone(hardwareMap: HardwareMap) : Component {
    private var droneLauncher: Servo
    init {
        droneLauncher = hardwareMap.get(Servo::class.java,Const.Drone.Name.droneLauncher)
        droneLauncher.direction = Const.Drone.Direction.drone
    }

    override fun autonomousInit() {
        // TODO("Not yet implemented")
    }

    override fun teleopInit() {

    }

    override fun disabledInit() {
        // TODO("Not yet implemented")
    }

    override fun testInit() {
        // TODO("Not yet implemented")
    }

    override fun readSensors(state: State) {
        state.dronelauncherPosition = droneLauncher.position
    }

    override fun applyState(state: State) {
        if (state.droneIsShot){
            droneLauncher.position = Const.Drone.Position.shot
        }else{
            droneLauncher.position = 1.0
        }
        state.droneDirection = droneLauncher.direction
    }
}