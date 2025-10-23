package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.scheduler.SwitchCommandGroup;

public class Switch implements Command {
    SwitchCommandGroup switchCommandGroup;
    String name;
    public Switch(SwitchCommandGroup switchCommandGroup, String name){
        this.switchCommandGroup = switchCommandGroup;
        this.name = name;
    }

    @Override
    public void start() {
        switchCommandGroup.setCommand(name);
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
