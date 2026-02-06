package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class StartIntake implements Command {
    private final Intake intake;

    public StartIntake(Intake intake){
        this.intake = intake;
    }

    @Override
    public void start() {
        intake.start();
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end() {
    }
}
