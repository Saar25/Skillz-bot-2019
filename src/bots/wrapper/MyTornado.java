package bots.wrapper;

import elf_kingdom.*;
import bots.*;

import java.util.*;

public class MyTornado extends MyCreature {
    
    public static int ATTACK_RANGE;
    public static int ATTACK_MULTIPLIER;
    public static int MAX_HEALTH;
    public static int MAX_SPEED;
    public static int SUFFOCATION_PER_TURN;
    public static int COST;
    
    public static void update(Game game) {
        MyTornado.ATTACK_RANGE = game.tornadoAttackRange;
        MyTornado.ATTACK_MULTIPLIER = game.tornadoAttackMultiplier;
        MyTornado.MAX_HEALTH = game.tornadoMaxHealth;
        MyTornado.MAX_SPEED = game.tornadoMaxSpeed;
        MyTornado.SUFFOCATION_PER_TURN = game.tornadoSuffocationPerTurn;
        MyTornado.COST = game.tornadoCost;
    }
    
    private final Tornado tornado;
    
    public int cost;
    public int suffocationPerTurn;
    
    public MyTornado(Tornado tornado) {
        super(tornado);
        this.tornado = tornado;
        this.cost = tornado.cost;
        this.suffocationPerTurn = tornado.suffocationPerTurn;
    }
    
    @Override
    public List<? extends MyMapObject> getMineOfThisType() {
        return MyGame.getMyTornadoes();
    }
    
    /**
     * Lava giant always go to their enemy's castle
     */
    @Override
    public MyMapObject getDestination() {
        if (!isEnemy()) {
            return getClosest(MyGame.getEnemyActiveBuildings());
        } else {
            return getClosest(MyGame.getMyActiveBuildings());
        }
    }
}