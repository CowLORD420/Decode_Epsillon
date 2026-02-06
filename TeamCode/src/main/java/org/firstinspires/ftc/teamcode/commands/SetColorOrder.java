package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.CameraLocalization;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensor;

public class SetColorOrder implements Command {
    private final ColorSensor colorSensor;
    private final CameraLocalization cameraLocalization;

    public SetColorOrder(ColorSensor colorSensor, CameraLocalization cameraLocalization){
        this.colorSensor = colorSensor;
        this.cameraLocalization = cameraLocalization;
    }

    @Override
    public void start() {
        cameraLocalization.start();
        cameraLocalization.update();
        colorSensor.setColorOrder(cameraLocalization.getColor1(), cameraLocalization.getColor2(), cameraLocalization.getColor3());
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end() {
        cameraLocalization.stop();
    }
}
