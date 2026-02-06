package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.commands.FreeRide;
import org.firstinspires.ftc.teamcode.commands.IntakeNext;
import org.firstinspires.ftc.teamcode.commands.OuttakeNext;
import org.firstinspires.ftc.teamcode.commands.Push;
import org.firstinspires.ftc.teamcode.commands.SetColorOrder;
import org.firstinspires.ftc.teamcode.commands.Shoot;
import org.firstinspires.ftc.teamcode.commands.StartIntake;
import org.firstinspires.ftc.teamcode.commands.StopIntake;
import org.firstinspires.ftc.teamcode.commands.StopShooter;
import org.firstinspires.ftc.teamcode.commands.SwitchTo;
import org.firstinspires.ftc.teamcode.hardware.Robot;
import org.firstinspires.ftc.teamcode.scheduler.Command;
import org.firstinspires.ftc.teamcode.scheduler.RaceParallelCommand;
import org.firstinspires.ftc.teamcode.scheduler.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.scheduler.SwitchCommandGroup;
import org.firstinspires.ftc.teamcode.scheduler.Wait;
import org.firstinspires.ftc.teamcode.subsystems.CameraLocalization;
import org.firstinspires.ftc.teamcode.subsystems.GamepadWrapper;
import org.firstinspires.ftc.teamcode.subsystems.Pinpoint;

import java.util.function.Supplier;

@TeleOp
public class Teleop extends OpMode {
    private Robot robot;

    enum drive {
        FAST,
        SLOW,
    }

    StartIntake startIntake;

    FreeRide fast;

    FreeRide slow;

    SwitchCommandGroup<drive> speed;

    SwitchTo<drive> fastDrive;
    SwitchTo<drive> slowDrive;

    Shoot startShooter;
    StopShooter stopShooter;

    RaceParallelCommand intake;

    enum intakeState {
        RUN,
        STOP
    }

    SwitchCommandGroup<intakeState> intakeSwitch;

    SwitchTo<intakeState> intakeStart;
    SwitchTo<intakeState> intakeStop;
    IntakeNext intakeNext;

    Push pushBall;
    OuttakeNext outtakeNext;

    SequentialCommandGroup moveAndShoot;

    SetColorOrder setColorOrder;

    GamepadWrapper gamepad;

    @Override
    public void init() {
        robot = new Robot(hardwareMap, gamepad1);
        robot.init();

        outtakeNext = new OuttakeNext(robot.colorSensor, robot.spindex);
        robot.scheduler.schedule(setColorOrder);

        fast = new FreeRide(
                robot.mecanumDrive,
                robot.input,
                robot.pinpoint,
                1
        );

        slow  = new FreeRide(
                robot.mecanumDrive,
                robot.input,
                robot.pinpoint,
                0.3
        );

        speed  = new SwitchCommandGroup.Builder<>(drive.class)
                .setDefault(() -> fast)
                .add(drive.FAST, () -> fast)
                .add(drive.SLOW, () -> slow)
                .build();

        fastDrive = new SwitchTo<>(speed, drive.FAST);
        slowDrive = new SwitchTo<>(speed, drive.SLOW);

        startShooter = new Shoot(robot.shooter, robot.pinpoint, Pinpoint.Color.BLUE);
        stopShooter = new StopShooter(robot.shooter);

        intakeNext = new IntakeNext(robot.colorSensor, robot.spindex);

        startIntake = new StartIntake(robot.intake);

        Supplier<Command> intake =  () -> new RaceParallelCommand(
                () -> startIntake,
                () -> intakeNext
        );

        intakeSwitch = new SwitchCommandGroup.Builder<>(intakeState.class)
                .setDefault(() -> new StopIntake(robot.intake))
                .add(intakeState.RUN, intake)
                .add(intakeState.STOP, () -> new StopIntake(robot.intake))
                .build();

        intakeStart  = new SwitchTo<>(intakeSwitch, intakeState.RUN);

        intakeStop = new SwitchTo<>(intakeSwitch, intakeState.STOP);

        pushBall = new Push(robot.picior);

        moveAndShoot = new SequentialCommandGroup(
                () -> outtakeNext,
                () -> new Wait(1),
                () -> pushBall
        );

        setColorOrder = new SetColorOrder(robot.colorSensor, robot.cameraLocalization);

        gamepad = new GamepadWrapper.Builder(robot.wrapper)
                .onPress(GamepadWrapper.Button.A, () -> intakeStart)
                .onPress(GamepadWrapper.Button.B, () -> startShooter, () -> stopShooter)
                .onPress(GamepadWrapper.Button.X, () -> outtakeNext, () -> pushBall)
                .onPress(GamepadWrapper.Button.LEFT_BUMPER, () -> fastDrive, () -> slowDrive)
                .build();

        robot.scheduler.schedule(intakeSwitch);
        robot.scheduler.schedule(speed);
    }

    @Override
    public void loop() {
        gamepad.update();
        robot.update();
    }
}
