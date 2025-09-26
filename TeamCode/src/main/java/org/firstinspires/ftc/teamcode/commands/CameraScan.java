package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.Pipeline;

public class CameraScan implements Command {
    private final Pipeline pipeline;

    public CameraScan (Pipeline pipeline){
        this.pipeline = pipeline;
    }
// caca
    @Override
    public void start() {
        pipeline.start();
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
        pipeline.stop();
    }
}
