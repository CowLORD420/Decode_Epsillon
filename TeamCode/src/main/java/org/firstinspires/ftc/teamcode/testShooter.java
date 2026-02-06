package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.commands.Shoot;
import org.firstinspires.ftc.teamcode.commands.StopShooter;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.scheduler.CommandScheduler;
import org.firstinspires.ftc.teamcode.subsystems.GamepadWrapper;
import org.firstinspires.ftc.teamcode.subsystems.Pinpoint;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.GoBildaPinpointDriver;

import java.util.Comparator;

@TeleOp
public class testShooter extends OpMode {
    Shooter shooter;
    Shoot shoot;
    StopShooter stopShooter;
    Pinpoint pinpoint;

    CommandScheduler scheduler;
    GamepadWrapper wrapper;

    @Override
    public void init() {
        pinpoint = new Pinpoint(hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint"));
        shooter = new Shooter(
                hardwareMap.get(DcMotorEx.class, "shooter_left"),
                hardwareMap.get(DcMotorEx.class, "shooter_right")
        );
        shoot = new Shoot(shooter, pinpoint, Pinpoint.Color.RED);
        stopShooter = new StopShooter(shooter);
        scheduler = new CommandScheduler();
        wrapper = new GamepadWrapper.Builder(gamepad1, scheduler)
               .onPress(GamepadWrapper.Button.B, () -> shoot, () -> stopShooter)
               .build();
    }


    @Override
    public void loop() {
        shooter.update();
        pinpoint.update();
        wrapper.update();
        scheduler.update();
        telemetry.addData("yaw", pinpoint.getYaw());
        telemetry.addData("x", pinpoint.getX());
        telemetry.addData("y", pinpoint.getY());
        telemetry.addData("pose", pinpoint.getDistanceToTarget(Pinpoint.Color.RED));
        telemetry.addData("rpm", shooter.getRPM());
        telemetry.addData("ticks", shooter.getTicks());
        telemetry.update();
    }
}
