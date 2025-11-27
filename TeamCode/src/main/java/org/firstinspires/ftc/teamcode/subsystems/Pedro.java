package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Pedro {
    private final Follower follower;
    private Pose currentPose;

    private final Map<String, Supplier<PathChain>> lazyPaths = new HashMap<>();

    private final Map<String, PathChain> prebuiltPaths = new HashMap<>();

    public Pedro(Follower follower){
        this.follower = follower;
    }

    public void addLazyPath(String name, Supplier<PathChain> pathSupplier){
        lazyPaths.put(name, pathSupplier);
    }

    public void removeLazyPath(String name){
        lazyPaths.remove(name);
    }

    public void followLazyPath(String name){
        Supplier<PathChain> supplier = lazyPaths.get(name);
        if(supplier != null){
            PathChain path = supplier.get();
            follower.followPath(path, true);
        }
    }

    public void addPrebuiltPath(String name, PathChain path){
        prebuiltPaths.put(name, path);
    }

    public void removePrebuiltPath(String name){
        prebuiltPaths.remove(name);
    }

    public void followPath(String name){
        PathChain path = prebuiltPaths.get(name);
        if(path != null){
            follower.followPath(path, true);
        }
    }

    public void updatePose(Pose currentPose){
        this.currentPose = currentPose;
    }

    public boolean finished(){ return !follower.isBusy(); }

    public void update(){
        follower.update();
    }

    public void stop(){
        follower.breakFollowing();
    }
}
