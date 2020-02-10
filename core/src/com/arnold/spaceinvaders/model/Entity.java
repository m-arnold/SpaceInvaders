package com.arnold.spaceinvaders.model;

import com.arnold.spaceinvaders.utils.AnimationManager;
import com.arnold.spaceinvaders.utils.AssetManager;
import com.arnold.spaceinvaders.utils.EntityManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

    protected EntityManager entityManager = EntityManager.getEntityManager();
    protected AssetManager assetManager = AssetManager.getAssetManager();
    protected AnimationManager animationManager = AnimationManager.getAnimationManager();

    protected SpriteBatch spriteBatch = new SpriteBatch();
    protected Texture texture;
    protected Rectangle boundingBox;

    public String id;

    public float posX;
    public float posY;

    public abstract void update();

    public void render() {
        spriteBatch.begin();
        spriteBatch.draw(texture, posX, posY);
        spriteBatch.end();
    }

    protected Vector2 getCenterOfTexture(){
        Vector2 vector = new Vector2();
        vector.x = posX + (texture.getWidth() / 2);
        vector.y = posY + (texture.getHeight() / 2);
        return vector;
    }

    public Rectangle getBoundingBox(){
        return boundingBox;
    }

    public boolean leftScreen(){
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        if(posX + texture.getWidth() < 0 || posX > width
        || posY + texture.getHeight() < 0 || posY > height){
            return true;
        }
        return false;
    }

    protected void updateBoundingBox(){
        boundingBox.x = posX;
        boundingBox.y = posY;
    }

    public Entity collidedWith(){
        for(Entity e : entityManager.getEntities()){
            if(this != e &&  boundingBox.overlaps(e.getBoundingBox())){
                return e;
            }
        }
        return null;
    }
}