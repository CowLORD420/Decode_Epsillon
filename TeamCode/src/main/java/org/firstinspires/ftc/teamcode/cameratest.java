package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.CameraLocalization;

@TeleOp
public class cameratest extends OpMode {
    CameraLocalization cameraLocalization;

    @Override
    public void init() {
        cameraLocalization = new CameraLocalization(hardwareMap.get(Limelight3A.class, "limelight"));
        cameraLocalization.start();
    }

    @Override
    public void loop() {
        cameraLocalization.update();
        telemetry.addData("color1", cameraLocalization.getColor1());
        telemetry.addData("color2", cameraLocalization.getColor2());
        telemetry.addData("color3", cameraLocalization.getColor3());
        telemetry.update();
    }
}
