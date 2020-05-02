package com.arnold.spaceinvaders.utils;

import com.arnold.spaceinvaders.model.animations.Animatable;

import java.util.ArrayList;
import java.util.List;

public class AnimationManager {

    private static AnimationManager animationManager;
    private List<Animatable> animations;
    private List<Animatable> addAfterUpdate;
    private List<Animatable> removeAfterUpdate;

    public static AnimationManager getAnimationManager(){
        if(animationManager == null){
            animationManager = new AnimationManager();
            animationManager.animations = new ArrayList<>();
            animationManager.addAfterUpdate = new ArrayList<>();
            animationManager.removeAfterUpdate = new ArrayList<>();
        }
        return animationManager;
    }

    public void addAnimation(Animatable animation){
        addAfterUpdate.add(animation);
    }
    public void removeAnimation(Animatable animation) { removeAfterUpdate.add(animation);}

    public void removeAllAnimations() {
        animations.clear();
        addAfterUpdate.clear();
        removeAfterUpdate.clear();
    }

    public void renderAnimations(){
        animations.addAll(addAfterUpdate);
        addAfterUpdate.clear();
        animations.stream().forEach(animation -> animation.render());
        animations.removeAll(removeAfterUpdate);
    }
}
