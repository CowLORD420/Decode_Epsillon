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

    Pose startPose = new Pose(0,0,Math.toRadians(0));
    Pose endPose = new Pose(50, 50, Math.toRadians(90));

    public enum LazyPaths {
        SCORE1("score1", 0, 0, Math.toRadians(50)),
        SCORE2("score2", 10, 5, Math.toRadians(90));

        private final String name;
        private final Pose pose;

        LazyPaths(String name, double x, double y, double heading) {
            this.name = name;
            this.pose = new Pose(x, y, heading);
        }

        public String getName() {
            return name;
        }

        public Pose getPose() {
            return pose;
        }
    }

    public enum PrebuiltPaths {
        AUTO1("auto1");

        private final String name;

        PrebuiltPaths(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public Paths(Follower follower, Pedro pedro){
        this.follower = follower;
        this.pedro = pedro;
    }

    public void registerLazyPath(String pathName, Pose pose){
        pedro.addLazyPath(pathName, lazyPath(pose));
    }

    //function that builds all prebuilt paths at init of auto
    /** To do make it so it only builds the ones it needs based on start position and color and etc **/

    public void registerPrebuiltPaths() {
        pedro.addPrebuiltPath(PrebuiltPaths.AUTO1.getName(), prebuiltPath(startPose, endPose));
    }

    private Supplier<PathChain> lazyPath(Pose pose){
        return () -> {
            Pose start = currentPose;

            return follower.pathBuilder()
                    .addPath(new Path(new BezierLine(start, pose)))
                    .setLinearHeadingInterpolation(start.getHeading(), pose.getHeading())
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
