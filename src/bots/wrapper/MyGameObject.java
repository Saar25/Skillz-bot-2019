package bots.wrapper;

import elf_kingdom.*;
import bots.*;

import java.util.*;

public class MyGameObject extends MyMapObject {
    
    public final GameObject gameObject;
    
    public boolean alreadyActed = false;
    
    public int currentHealth;
    
    public int id;
    
    public int uniqueId;
    
    public Location initialLocation;
    
    public int maxHealth;
    
    public Player owner;
    
    private String name;
    
    public MyGameObject(GameObject gameObject) {
        super(gameObject);
        this.gameObject = gameObject;
        this.alreadyActed = gameObject.alreadyActed;
        this.currentHealth = gameObject.currentHealth;
        this.id = gameObject.id;
        this.uniqueId = gameObject.uniqueId;
        this.initialLocation = gameObject.initialLocation;
        this.maxHealth = gameObject.maxHealth;
        this.owner = gameObject.owner;
        this.name = randName();
    }
    
    private String randName() {
        List<String> names = Arrays.asList(
            "Gilad Shalit", "General Rabiin",
            "President Obamba", "Sergent Pizza", "colonel yafa runkel"
            );
        return names.get(id % names.size());
    }
    
    public boolean isEnemy() {
        return owner == MyGame.getEnemy();
    }
    
    public int getStrength() {
        return currentHealth * getAttackMultiplier();
    }
    
    public int potentialDamageBy(MyGameObject enemy) {
        int health = enemy.currentHealth;
        if (enemy instanceof MyCreature) {
            MyCreature e = (MyCreature) enemy;
            health -= e.suffocationPerTurn * e.turnsToAttack(this);
        }
        return (health / getAttackMultiplier() + 1) * enemy.getAttackMultiplier();
    }
    
    public int potentialDamageTo(MyGameObject enemy) {
        return (currentHealth / enemy.getAttackMultiplier() + 1) * getAttackMultiplier();
    }
    
    public boolean canKill(MyGameObject... enemies) {
        int damageBy = 0, damageTo = 0;
        for (MyGameObject enemy : enemies) {
            damageBy += potentialDamageBy(enemy);
            damageTo += potentialDamageTo(enemy);
        }
        return damageTo >= damageBy;
    }
    
    /**
     * Override this method where neccecery
     */
    public int getAttackRange() {
        return 0;
    }
    
    /**
     * Returns whether the game object is in attack range of the given map object
     * Much faster than api's method that uses RefLEcTiOn
     *
     * @param mapObject the map object to check
     * @return true if in attack range false otherwise
     */
    public boolean inAttackRange(MyMapObject mapObject) {
        int size = mapObject instanceof MyBuilding ? ((MyBuilding) mapObject).size : 0;
        return inRange(mapObject, getAttackRange() + size);
    }
    
    /**
     * Returns how many turns would it take for the object to get
     * close enough to the map object so it can attack the map object
     *
     * @param mapObject the map object to check
     * @return the amount of turns before attack
     */
    public int turnsToAttack(MyMapObject mapObject) {
        if (inAttackRange(mapObject)) {
            return 0;
        }
        int size = mapObject.getSize();
        int distance = distance(mapObject) - getAttackRange() - size;
        return Math.max(distance / getMaxSpeed(), 1);
    }
    
    /**
     * Returns the amount of turns takes to reach the destination
     *
     * @param destination the destination
     * @return the amount of turns reach
     */
    public int turnsToReach(MyMapObject destination) {
        int distance = distance(destination);
        return distance / getMaxSpeed();
    }
    
    /**
     * Override this method where neccecery
     */
    public int getMaxSpeed() {
        return 0;
    }
    
    /**
     * Override this method where neccecery
     */
    public int getAttackMultiplier() {
        return 0;
    }
    
    @Override
    public String toString() {
        //return "[" + getClass().getSimpleName() + ": Id= " + id + "," + " Name= " + name + "]";
        return "[" + getClass().getSimpleName() + ": Id= " + id + "]";
    }
}