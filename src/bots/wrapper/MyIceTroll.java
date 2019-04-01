package bots.wrapper;

import elf_kingdom.*;
import bots.*;

import java.util.*;

public class MyIceTroll extends MyCreature {
    
    public static int ATTACK_RANGE;
    public static int ATTACK_MULTIPLIER;
    public static int MAX_HEALTH;
    public static int MAX_SPEED;
    public static int SUFFOCATION_PER_TURN;
    public static int COST;
    public static int SUMMONING_DURATION;
    
    public static void update(Game game) {
        MyIceTroll.ATTACK_RANGE = game.iceTrollAttackRange;
        MyIceTroll.ATTACK_MULTIPLIER = game.iceTrollAttackMultiplier;
        MyIceTroll.MAX_HEALTH = game.iceTrollMaxHealth;
        MyIceTroll.MAX_SPEED = game.iceTrollMaxSpeed;
        MyIceTroll.SUFFOCATION_PER_TURN = game.iceTrollSuffocationPerTurn;
        MyIceTroll.COST = game.iceTrollCost;
        MyIceTroll.SUMMONING_DURATION = game.iceTrollSummoningDuration;
    }
    
    private final IceTroll iceTroll;
    
    public int cost;
    
    public int suffocationPerTurn;
    
    public MyIceTroll(IceTroll iceTroll) {
        super(iceTroll);
        this.iceTroll = iceTroll;
        this.cost = iceTroll.cost;
        this.suffocationPerTurn = iceTroll.suffocationPerTurn;
    }
    
    @Override
    public List<? extends MyMapObject> getMineOfThisType() {
        return MyGame.getMyIceTrolls();
    }
    
    /**
     * Ice trolls always go to their closest enemy
     */
    @Override
    public MyMapObject getDestination() {
        if (!isEnemy()) {
            return getClosest(MyGame.getEnemies());
        } else {
            return getClosest(MyGame.getMyEntities());
        }
    }
}