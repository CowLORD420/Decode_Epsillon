package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensor;
import org.firstinspires.ftc.teamcode.subsystems.Spindex;

public class OuttakeNext implements Command {
    private final Spindex spindex;
    private final ColorSensor colorSensor;

    public OuttakeNext(ColorSensor colorSensor, Spindex spindex){
        this.spindex = spindex;
        this.colorSensor = colorSensor;
    }

    @Override
    public void start() {
        int index = colorSensor.getNextBallIndex();
        spindex.setMode(Spindex.Mode.OUTTAKE);
        spindex.goToPosition(index);
        spindex.setBoxEmpty(index);
        if (spindex.isEmpty()) colorSensor.resetTaken();
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

    }
}
