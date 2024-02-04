package org.firstinspires.ftc.teamcode.component

import android.util.Size
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.state.State
import org.firstinspires.ftc.teamcode.subClass.Const
import org.firstinspires.ftc.vision.VisionPortal
import org.firstinspires.ftc.vision.tfod.TfodProcessor



class Camera(hardwareMap: HardwareMap) : Component {
    private var myVisionPortal: VisionPortal
    private var tfod: TfodProcessor

    init {
        //
        tfod = TfodProcessor.easyCreateWithDefaults()
        val myVisionPortalBuilder = VisionPortal.Builder()
        myVisionPortalBuilder.setCamera(hardwareMap.get(WebcamName::class.java, "Webcam 1"))
        myVisionPortalBuilder.addProcessor(tfod)
        myVisionPortalBuilder.setCameraResolution(Size(Const.Camera.Size.width, Const.Camera.Size.height))
        myVisionPortalBuilder.setStreamFormat(VisionPortal.StreamFormat.YUY2)
        myVisionPortalBuilder.enableLiveView(true)
        myVisionPortalBuilder.setAutoStopLiveView(true)
        myVisionPortal = myVisionPortalBuilder.build()
    }

    override fun autonomousInit() {
    }

    override fun teleopInit() {
    }

    override fun disabledInit() {
    }

    override fun testInit() {
    }

    override fun readSensors(state: State) {
        val currentRecognitions = tfod.recognitions
        for (recognition in currentRecognitions) {
            state.detectedObject = recognition.label

            state.left = recognition.left.toDouble()
            state.right = recognition.right.toDouble()
            val position = when {
                recognition.left < Const.Camera.Position.left -> state.pixelPosition = "Left"
                recognition.left < Const.Camera.Position.center -> state.pixelPosition = "Center"
                else -> state.pixelPosition = "Right"
            }
        }
    }

    override fun applyState(state: State) {

    }
}