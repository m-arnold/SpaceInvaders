package com.arnold.spaceinvaders.model;

import com.arnold.spaceinvaders.utils.AnimationManager;
import com.arnold.spaceinvaders.utils.AssetManager;
import com.arnold.spaceinvaders.utils.EntityManager;
import com.arnold.spaceinvaders.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * The entity class. Contains methods which can be used by concrete
 * entities.
 *
 * author: Marc Arnold
 */
public abstract class Entity {

    protected EntityManager entityManager = EntityManager.getEntityManager();
    protected AssetManager assetManager = AssetManager.getAssetManager();
    protected AnimationManager animationManager = AnimationManager.getAnimationManager();

    protected SpriteBatch spriteBatch = Utils.spriteBatch;
    protected Texture texture;
    protected Rectangle boundingBox;

    public String id;

    public float posX;
    public float posY;

    public abstract void update();

    public abstract void destroy();

    /**
     * Called to render the entity to the screen.
     */
    public void render() {
        spriteBatch.begin();
        spriteBatch.draw(texture, posX, posY);
        spriteBatch.end();
    }

    /**
     * Called to determine the center of the texture.
     *
     * @return center of the texture
     */
    protected Vector2 getCenterOfTexture(){
        Vector2 vector = new Vector2();
        vector.x = posX + (texture.getWidth() / 2);
        vector.y = posY + (texture.getHeight() / 2);
        return vector;
    }

    public Rectangle getBoundingBox(){
        return boundingBox;
    }

    /**
     * Checks whether the entity left the screen.
     *
     * @return boolean
     */
    public boolean leftScreen(){
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        if(posX + texture.getWidth() < 0 || posX > width
        || posY + texture.getHeight() < 0 || posY > height){
            return true;
        }
        return false;
    }

    /**
     * Updates the bounding box to the current position of the
     * entity.
     */
    protected void updateBoundingBox(){
        boundingBox.x = posX;
        boundingBox.y = posY;
    }

    /**
     * Checks whether the entity collided with another entity and returns it.
     *
     * @return collider entity
     */
    public Entity collidedWith(){
        for(Entity e : entityManager.getEntities()){
            if(this != e &&  boundingBox.overlaps(e.getBoundingBox())){
                return e;
            }
        }
        return null;
    }
}