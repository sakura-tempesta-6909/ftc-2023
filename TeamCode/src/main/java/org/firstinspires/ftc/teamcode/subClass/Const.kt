package org.firstinspires.ftc.teamcode.subClass

import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo

class Const {
    class Slider {
        class Motor {
            //モーターの名前
            class Name {
                companion object {
                    const val Right = "slider_right"
                    const val Left = "slider_left"
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
                const val targetToPosition = 0.8
            }
        }

        class Position {
            companion object {
                const val medium = 1000
                const val top = 2000
                const val climb = 2400
                const val motor_adjustment_quantity = 10
            }
        }
    }

    class Arm {
        class Motor {
            class Name {
                companion object {
                    const val holder = "holder_servo"
                    const val lift = "lift_motor"
                    const val flip = "flip_servo"
                }
            }

            class Direction {
                companion object {
                    val lift = DcMotorSimple.Direction.FORWARD
                    val holder = Servo.Direction.FORWARD
                    val flip = Servo.Direction.FORWARD
                }
            }

            class Position{
                companion object{
                    const val liftUpperLimit = 680
                    const val liftDownLimit = 15
                }
            }



        }
    }
    class Drive{
        class Name{
            companion object{
                const val leftFront = "left_front"
                const val rightFront = "right_front"
                const val leftRear = "left_back"
                const val rightRear = "right_back"
                const val imu = "imu"
            }
        }
        class Direction{
            companion object{

                val leftFront = DcMotorSimple.Direction.FORWARD

                val rightFront = DcMotorSimple.Direction.REVERSE

                val leftRear = DcMotorSimple.Direction.FORWARD

                val rightRear = DcMotorSimple.Direction.REVERSE
            }
        }
        class Speed{
            companion object{
                const val lowGear = 0.5
                const val highGear = 1.0
            }
        }
    }
    class Drone{
        class Name{
            companion object{
                const val droneLauncher = "drone_servo"
            }
        }

        class Position{
            companion object{
                const val shot = 0.0
            }
        }
        class Direction{
            companion object{
                val drone = Servo.Direction.FORWARD
            }
        }
    }
    class Autonomous{
        companion object{
            const val slider = 1500
            const val holder = 500
            const val lateralMovement = 1600
            const val verticalMovement = 500
            const val shortenDistance = 300
            const val backTime = 600
            const val enterTime = 3000
            const val minimumMove = 200
        }
    }
}