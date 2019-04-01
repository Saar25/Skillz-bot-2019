package bots.wrapper;

import bots.*;
import elf_kingdom.*;

import java.util.*;

public class MyManaFountain extends MyBuilding {
    
    public static int SIZE;
    public static int COST;
    public static int BUILDING_DURATION;
    public static int MANA_PER_TURN;
    public static int MAX_HEALTH;
    
    public static void update(Game game) {
        SIZE = game.manaFountainSize; //       
        COST = game.manaFountainCost;
        BUILDING_DURATION = game.manaFountainBuildingDuration;
        MANA_PER_TURN = game.manaFountainManaPerTurn;
        MAX_HEALTH = game.manaFountainMaxHealth;
    }
    
    private final ManaFountain manaFountain;
    
    public final int cost;
    public final int manaPerTurn;
    
    public MyManaFountain(ManaFountain manaFountain) {
        super(manaFountain);
        this.manaFountain = manaFountain;
        
        this.cost = manaFountain.cost;
        this.manaPerTurn = manaFountain.manaPerTurn;
    }
    
    @Override
    public List<? extends MyMapObject> getMineOfThisType() {
        return MyGame.getMyManaFountains();
    }
    
}