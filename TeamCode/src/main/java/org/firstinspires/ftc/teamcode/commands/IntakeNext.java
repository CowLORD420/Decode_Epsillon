package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensor;
import org.firstinspires.ftc.teamcode.subsystems.Spindex;

public class IntakeNext implements Command {
    private final ColorSensor colorSensor;
    private final Spindex spindex;
    private int currentPosition = 0;

    public IntakeNext(ColorSensor colorSensor, Spindex spindex){
        this.colorSensor = colorSensor;
        this.spindex = spindex;
    }

    @Override
    public void start() {
        spindex.setMode(Spindex.Mode.INTAKE);
        currentPosition = spindex.getCurrentPosition();
        spindex.goToPosition(currentPosition);
    }

    @Override
    public void update() {
        currentPosition = spindex.getCurrentPosition();
        if (colorSensor.detected()){
            colorSensor.updateColorAtIndex(currentPosition);
            spindex.setBoxFull(currentPosition);
            spindex.goToNextPosition();
        }
    }

    @Override
    public boolean isFinished() {
        return spindex.isFull();
    }

    @Override
    public void end() {

    }
}
