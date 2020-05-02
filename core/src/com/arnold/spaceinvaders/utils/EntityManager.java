package com.arnold.spaceinvaders.utils;

import com.arnold.spaceinvaders.model.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing the EntityManager.
 *
 * author: Marc Arnold
 */
public class EntityManager {

    private static EntityManager entityManager;
    private List<Entity> entities;
    private List<Entity> addAfterUpdate;
    private List<Entity> removeAfterUpdate;

    private EntityManager(){}

    public static EntityManager getEntityManager(){
        if(entityManager == null){
            entityManager = new EntityManager();
            entityManager.entities = new ArrayList<Entity>();
            entityManager.addAfterUpdate = new ArrayList<Entity>();
            entityManager.removeAfterUpdate = new ArrayList<Entity>();
        }
        return entityManager;
    }

    /**
     * Adds an entity to the entities list.
     *
     * @param entity which will be added to the entities list
     */
    public void addEntity(Entity entity){
        addAfterUpdate.add(entity);
    }

    /**
     * Removes an entity from the entities list.
     *
     * @param entity which will be added from the entities list
     */
    public void removeEntity(Entity entity){
        removeAfterUpdate.add(entity);
    }

    /**
     * Removes all entities from the entityManager.
     */
    public void removeAllEntities() {
        entities.clear();
        addAfterUpdate.clear();
        removeAfterUpdate.clear();
    }

    /**
     * Calls the update method of all entities within the
     * entities list.
     */
    public void updateEntities(){
        entities.stream().forEach(entity -> entity.update());
        entities.addAll(addAfterUpdate);
        entities.removeAll(removeAfterUpdate);
        addAfterUpdate.clear();
    }

    /**
     * Renders all entities within the entities list to the screen.
     */
    public void renderEntities(){
        entities.stream().forEach(entity -> entity.render());
    }

    /**
     *  Returns a list of all entities.
     *
     * @return list of all entities
     */
    public List<Entity> getEntities(){
        return entities;
    }

    /**
     * Returns an entity depending on the given id.
     *
     * @param id which will be searched in the entities list
     * @return entity
     */
    public Entity getEntityById(String id) {
        for (Entity e: entities){
            if(id.equals(e.id)) {
                return e;
            }
        }
        return null;
    }
}
