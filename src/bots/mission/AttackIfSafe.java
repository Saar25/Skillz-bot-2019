package bots.mission;

import bots.*;
import bots.wrapper.*;

import java.util.function.*;
import java.util.*;


public class AttackIfSafe implements Mission<MyElf> {
    
    private static final int EPS = 5;
    
    private final Function<MyElf, MyGameObject> destination;
    
    public AttackIfSafe(Supplier<MyGameObject> destination) {
        this.destination = e -> destination.get();
    }
    
    public AttackIfSafe(Function<MyElf, MyGameObject> destination) {
        this.destination = destination;
    }
    
    @Override
    public State act(MyElf e) {
        MyGameObject destination = this.destination.apply(e);
        if (destination == null) return State.PASS;
        
        List<MyGameObject> attackers = Utils.getInTurnsToAttack(e, MyGame.getEnemyKillers(), 0);
        if (Utils.getStrength(attackers) < e.getStrength()) {
            if(e.attack(destination)){
                return State.FINISHED;
            }
        }
        return State.PASS;
    }
    
    
}