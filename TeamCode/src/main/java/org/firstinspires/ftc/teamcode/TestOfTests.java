package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.commands.FreeRide;
import org.firstinspires.ftc.teamcode.commands.Push;
import org.firstinspires.ftc.teamcode.commands.Shoot;
import org.firstinspires.ftc.teamcode.commands.StopShooter;
import org.firstinspires.ftc.teamcode.commands.SwitchTo;
import org.firstinspires.ftc.teamcode.scheduler.CommandScheduler;
import org.firstinspires.ftc.teamcode.scheduler.SwitchCommandGroup;
import org.firstinspires.ftc.teamcode.scheduler.Wait;
import org.firstinspires.ftc.teamcode.subsystems.DriveInput;
import org.firstinspires.ftc.teamcode.subsystems.GamepadWrapper;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Picior;
import org.firstinspires.ftc.teamcode.subsystems.Pinpoint;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.utils.GoBildaPinpointDriver;

@TeleOp
public class TestOfTests extends OpMode {
    Pinpoint pinpointer;
    GoBildaPinpointDriver pinpoint;
    MecanumDrive mecanumDrive;
    DriveInput input;
    FreeRide freeRide;
    GamepadWrapper gamepadWrapper;
    CommandScheduler commandScheduler;
    FreeRide exactRide;
    Picior picior;
    Push push;
    Shooter shooter;
    Shoot shoot;
    StopShooter stopShooter;


    enum drive {
        FREERIDE,
        EXACTRIDE,
        STOP
    }
    SwitchCommandGroup<drive> driveSwitch;
    Wait wait = new Wait(10);

    @Override
    public void init() {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpointer = new Pinpoint(pinpoint);
        picior = new Picior(hardwareMap.get(Servo.class, "picior"));
        push = new Push(picior);
        shooter = new Shooter(
                hardwareMap.get(DcMotorEx.class, "shooter_left"),
                hardwareMap.get(DcMotorEx.class, "shooter_right"));
        stopShooter = new StopShooter(shooter);

        DcMotorEx lf = hardwareMap.get(DcMotorEx.class, "lf");
        DcMotorEx lb = hardwareMap.get(DcMotorEx.class, "lb");
        DcMotorEx rf = hardwareMap.get(DcMotorEx.class, "rf");
        DcMotorEx rb = hardwareMap.get(DcMotorEx.class, "rb");

        mecanumDrive = new MecanumDrive(lf, lb, rf, rb);
        input = new DriveInput(gamepad1);
        freeRide = new FreeRide(mecanumDrive, input, pinpointer, 1);
        exactRide = new FreeRide(mecanumDrive, input, pinpointer, 0.3);
        commandScheduler = new CommandScheduler();
        driveSwitch = new SwitchCommandGroup.Builder<>(drive.class)
                .setDefault(() -> freeRide)
                .add(drive.EXACTRIDE,() -> exactRide)
                .add(drive.STOP, () -> wait)
                .build();

        SwitchTo<drive> stop = new SwitchTo<>(driveSwitch, drive.STOP);
        SwitchTo<drive> exact = new SwitchTo<>(driveSwitch, drive.EXACTRIDE);
        SwitchTo<drive> normal = new SwitchTo<>(driveSwitch);
        commandScheduler.schedule(driveSwitch);

        gamepadWrapper = new GamepadWrapper.Builder(gamepad1, commandScheduler)
                .onPress(GamepadWrapper.Button.A, () -> exact, () -> normal, ()-> stop)
                .onPress(GamepadWrapper.Button.B, () -> new Push(picior))
                .build();
    }


    @Override
    public void loop() {
        input.update();
        commandScheduler.update();
        gamepadWrapper.update();

        pinpointer.update();
        telemetry.addData("i", input.getX());
        telemetry.addData("i", input.getY());
        telemetry.addData("x", pinpointer.getX());
        telemetry.addData("y", pinpointer.getY());
        telemetry.addData("rot", pinpointer.getYaw());
    }
}
