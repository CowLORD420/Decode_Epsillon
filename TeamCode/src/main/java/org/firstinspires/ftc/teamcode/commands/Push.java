package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.subsystems.Picior;

public class Push implements Command {
    Picior picior;
    ElapsedTime timer = new ElapsedTime();

    public Push(Picior picior){
        this.picior = picior;
    }

    @Override
    public void start() {
        timer.reset();
        picior.up();
    }

    @Override
    public void update() {
    }

    @Override
    public boolean isFinished() {
        return timer.seconds() > 0.5;
    }

    @Override
    public void end() {
        picior.down();
    }
}
