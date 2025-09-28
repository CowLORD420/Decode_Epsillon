package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.subsystems.Pedro;

import java.util.function.Supplier;

public class Paths {

    private final Pedro pedro;
    private final Follower follower;
    private Pose currentPose;

    public Paths(Follower follower, Pedro pedro){
        this.follower = follower;
        this.pedro = pedro;
    }

    Pose startPose = new Pose(0,0,0);
    Pose endPose = new Pose(50, 50, Math.toRadians(90));

    public void registerPaths() {

        pedro.addPrebuiltPath("auto1", prebuiltPath(startPose, endPose));

        pedro.addLazyPath("score1", lazyPath(45, 98, 45));
        pedro.addLazyPath("score2", lazyPath(80, 120, 90));
    }

    private Supplier<PathChain> lazyPath(double x, double y, double headingDeg){
        return () -> {
            Pose start = currentPose;
            Pose end = new Pose(x, y, Math.toRadians(headingDeg));

            return follower.pathBuilder()
                    .addPath(new Path(new BezierLine(start, end)))
                    .setLinearHeadingInterpolation(start.getHeading(), end.getHeading())
                    .build();
        };
    }

    private PathChain prebuiltPath(Pose start, Pose end){
        return follower.pathBuilder()
                .addPath(new Path(new BezierLine(start, end)))
                .setLinearHeadingInterpolation(start.getHeading(), end.getHeading())
                .build();
    }

    public void updatePose(Pose currentPose){
        this.currentPose = currentPose;
    }
}
