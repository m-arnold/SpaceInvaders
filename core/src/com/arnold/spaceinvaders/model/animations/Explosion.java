package com.arnold.spaceinvaders.model.animations;

import com.arnold.spaceinvaders.utils.AnimationManager;
import com.arnold.spaceinvaders.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion implements Animatable{

    private AnimationManager animationManager = AnimationManager.getAnimationManager();

    private SpriteBatch batch = Utils.spriteBatch;
    private static Animation<TextureRegion> animation;

    private static final int FRAME_COLS = 8, FRAME_ROWS = 8;

    private float statetime;
    private float posX;
    private float posY;

    /**
     * This function is called by the assetManager on application start and only needs to be done
     * once. This prevents loading times while playing, since the "same" animation is used for all
     * explosion objects
     */
    public static void preloadAnimation(){
        Texture explosionSheet = new Texture("textures/explosion_4.png");

        TextureRegion[][] tmp = TextureRegion.split(explosionSheet,
                explosionSheet.getWidth() / FRAME_COLS,
                explosionSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] explosionFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                explosionFrames[index++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(0.025f, explosionFrames);
    }

    public Explosion(float posX, float posY){
        this.posX = posX;
        this.posY = posY;

        statetime = 0;
    }

    public void render(){
        statetime += Gdx.graphics.getDeltaTime();
        if(!animation.isAnimationFinished(statetime)){
            TextureRegion currentframe = animation.getKeyFrame(statetime,false);

            batch.begin();
            batch.draw(currentframe, posX - (currentframe.getRegionWidth() / 2) , posY - (currentframe.getRegionHeight() /2) );
            batch.end();
        } else{
            animationManager.removeAnimation(this);
        }
    }
}
