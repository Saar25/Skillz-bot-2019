package bots.wrapper;

import elf_kingdom.*;
import bots.*;

import java.util.*;

public class MyCreature extends MyGameObject {
    
    private final Creature creature;
    
    public int attackMultiplier;
    
    public int attackRange;
    
    public int maxSpeed;
    
    public Portal summoner;
    
    public int summoningDuration;
    
    public int suffocationPerTurn;
    
    public MyCreature(Creature creature) {
        super(creature);
        this.creature = creature;
        this.attackMultiplier = creature.attackMultiplier;
        this.attackRange = creature.attackRange;
        this.maxSpeed = creature.maxSpeed;
        this.summoner = creature.summoner;
        this.summoningDuration = creature.summoningDuration;
        this.suffocationPerTurn = creature instanceof IceTroll 
                ? MyGame.iceTrollSuffocationPerTurn
                : MyGame.lavaGiantSuffocationPerTurn;
    }
    
    @Override
    public int getAttackRange() {
        return attackRange;
    }
    
    @Override
    public int getMaxSpeed() {
        return maxSpeed;
    }
    
    @Override
    public int getAttackMultiplier() {
        return attackMultiplier;
    }
    
    @Override
    public List<? extends MyMapObject> getMineOfThisType() {
        return MyGame.getMyCreatures();
    }
    
    /**
     * Returns the creature destination, this is something we can predict
     * 
     * OVERRIDE THIS METHOD IN MyLavaGiant AND MyIceTroll CLASSES
     * 
     * @return the creature destination
     */
    public MyMapObject getDestination() {
        return null;
    }
    
    /**
     * Returns the creature direction, this is something we can predict
     * 
     * @return the creature direction
     */
    public Vector2 getDirection() {
        return getDestination().getLocation().towards(getLocation(), getMaxSpeed());
    }
    
    /**
     * Returns whether the creature can reach the given destination if
     * it would move to it in straight line
     * 
     * @return true if the creature can reach false otherwise
     */
    public boolean canReach(MyMapObject destination) {
        return healthWhenReach(destination) > 0;
    }
    
    /**
     * Returns the health of the creature at the time it will
     * reach the given destination if it would move to it in
     * straight line
     * 
     * @return the predicted health
     */
    public int healthWhenReach(MyMapObject destination) {
        final int turns = distance(destination) / maxSpeed;
        final int suffocation = turns * suffocationPerTurn;
        return currentHealth - suffocation;
    }
    
    /**
     * Returns the health of the creature at the time it will
     * be able to attack the given destination if it would move
     * to it in straight line
     * 
     * @return the predicted health
     */
    public int healthWhenAttack(MyMapObject destination) {
        final int turns = turnsToReach(destination);
        final int suffocation = turns * suffocationPerTurn;
        return currentHealth - suffocation;
    }
    
    /**
     * Returns the amount of turns left for this creature to
     * stay alive, this function take into consideration only
     * the suffocation per turn of the creature
     * 
     * @return the ampunt of turns left to live
     */
    public int turnsToLive() {
        return currentHealth / suffocationPerTurn;
    }
    
    /**
     * Returns the predicted location if the future turns
     * 
     * @return the predicted location
     */
    public Vector2 predictLocation(int turns) {
        Vector2 direction = getDirection();
        Vector2 destination = getDestination().getLocation();
        int distance = getLocation().distance(destination);
        if (distance < direction.mul(turns).length()) {
            return destination;
        }
        return direction.mul(turns).add(getLocation());
    }

}