package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.scheduler.SwitchCommandGroup;

public class Switch<E extends Enum<E>> implements Command {

    private final SwitchCommandGroup<E> switchCommandGroup;
    private final E name;

    public Switch(SwitchCommandGroup<E> switchCommandGroup){
        this.switchCommandGroup = switchCommandGroup;
        name = null;
    }

    public Switch(SwitchCommandGroup<E> switchCommandGroup, E name) {
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
