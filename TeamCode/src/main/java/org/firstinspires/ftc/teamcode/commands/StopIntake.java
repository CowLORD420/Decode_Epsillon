package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class StopIntake implements Command {
    private final Intake intake;

    public StopIntake(Intake intake){
        this.intake = intake;
    }

    @Override
    public void start() {
        intake.stop();
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
