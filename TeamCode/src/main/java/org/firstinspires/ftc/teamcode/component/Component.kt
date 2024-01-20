package org.firstinspires.ftc.teamcode.component

import org.firstinspires.ftc.teamcode.state.State

interface Component {
    /**
     * autonomous時の初期化
     */
    fun autonomousInit()

    /**
     * teleop時の初期化
     */
    fun teleopInit()

    /**
     * disable時の初期化
     */
    fun disabledInit()

    /**
     * test時の初期化
     */
    fun testInit()

    /**
     * センサーの値を読む。
     */
    fun readSensors(state: State)

    /**
     * stateを適用する
     */
    fun applyState(state:State)
}
