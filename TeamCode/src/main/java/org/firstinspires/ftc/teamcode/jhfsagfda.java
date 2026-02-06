package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.sun.tools.javac.code.Attribute;

import org.firstinspires.ftc.teamcode.commands.GoTo;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.scheduler.CommandScheduler;
import org.firstinspires.ftc.teamcode.subsystems.Pedro;
import org.firstinspires.ftc.teamcode.utils.Paths;

@TeleOp
public class jhfsagfda extends OpMode {
    Pedro pedro;
    Paths paths;
    Follower follower;
    CommandScheduler scheduler;
    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        pedro = new Pedro(follower);
        paths = new Paths(follower, pedro);
        scheduler = new CommandScheduler();
    }

    @Override
    public void loop() {
        //scheduler.schedule(new GoTo(pedro, Paths.PrebuiltPaths.AUTO1));
        //scheduler.update();
        follower.followPath(new Path(new BezierLine(new Pose(0,0,Math.toRadians(0)), new Pose(50,50,Math.toRadians(90)))));
        follower.update();
    }
}
