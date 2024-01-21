package org.firstinspires.ftc.teamcode.subClass

import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo

class Const {
    class Slider {
        class Motor {
            //モーターの名前
            class Name {
                companion object {
                    const val Right = "ex-motor_1"
                    const val Left = "ex-motor_0"
                }
            }

            //モーターの向き
            class Direction {
                companion object {
                    val Right = DcMotorSimple.Direction.FORWARD
                    val Left = DcMotorSimple.Direction.REVERSE
                }
            }
        }

        //positionの誤差の許容値
        companion object {
            const val tolerance = 10
        }

        //モーターの速度
        class Speed {
            companion object {
                const val targetToPosition = 0.5
            }
        }

        class Position {
            companion object {
                const val top = 2000
            }
        }
    }

    class Arm {
        class Motor {
            class Name {
                companion object {
                    const val holder = "servo_1"
                }
            }

            class Direction {
                companion object {
                    val holder = Servo.Direction.FORWARD
                }
            }

            class State{
                companion object{
                    const val holderIsOpen = true
                }
            }

        }
    }
}