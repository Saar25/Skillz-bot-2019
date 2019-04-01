package bots.wrapper;

import elf_kingdom.*;
import bots.*;

import java.util.*;

public class MyLavaGiant extends MyCreature {
    
    public static int ATTACK_RANGE;
    public static int ATTACK_MULTIPLIER;
    public static int MAX_HEALTH;
    public static int MAX_SPEED;
    public static int SUFFOCATION_PER_TURN;
    public static int COST;
    
    public static void update(Game game) {
        MyLavaGiant.ATTACK_RANGE = game.lavaGiantAttackRange;
        MyLavaGiant.ATTACK_MULTIPLIER = game.lavaGiantAttackMultiplier;
        MyLavaGiant.MAX_HEALTH = game.lavaGiantMaxHealth;
        MyLavaGiant.MAX_SPEED = game.lavaGiantMaxSpeed;
        MyLavaGiant.SUFFOCATION_PER_TURN = game.lavaGiantSuffocationPerTurn;
        MyLavaGiant.COST = game.lavaGiantCost;
    }
    
    private final LavaGiant lavaGiant;
    
    public int cost;
    
    public int suffocationPerTurn;
    
    public MyLavaGiant(LavaGiant lavaGiant) {
        super(lavaGiant);
        this.lavaGiant = lavaGiant;
        this.cost = lavaGiant.cost;
        this.suffocationPerTurn = lavaGiant.suffocationPerTurn;
    }
    
    @Override
    public List<? extends MyMapObject> getMineOfThisType() {
        return MyGame.getMyLavaGiants();
    }
    
    /**
     * Lava giant always go to their enemy's castle
     */
    @Override
    public MyMapObject getDestination() {
        if (!isEnemy()) {
            return MyGame.getEnemyCastle();
        } else {
            return MyGame.getMyCastle();
        }
    }
}