package com.arnold.spaceinvaders.utils;

import com.arnold.spaceinvaders.model.Entity;

import java.util.ArrayList;
import java.util.List;

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

    public void addEntity(Entity entity){
        addAfterUpdate.add(entity);
    }

    public void removeEntity(Entity entity){
        removeAfterUpdate.add(entity);
    }

    public void updateEntities(){
        entities.stream().forEach(entity -> entity.update());
        entities.addAll(addAfterUpdate);
        entities.removeAll(removeAfterUpdate);
        addAfterUpdate.clear();
    }

    public void renderEntities(){
        entities.stream().forEach(entity -> entity.render());
    }

    public List<Entity> getEntities(){
        return entities;
    }

    public Entity getEntityById(String id) {
        for (Entity e: entities){
            if(id.equals(e.id)) {
                return e;
            }
        }
        return null;
    }
}
