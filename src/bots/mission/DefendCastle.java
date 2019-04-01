package bots.mission;

import bots.*;
import bots.wrapper.*;

import java.util.*;

/**
 * Kill close elf to the castle
 */
public class DefendCastle implements Mission<MyPortal> {
    
    @Override
    public State act(MyPortal p) {
        MyCastle castle = MyGame.getMyCastle();
        List<MyGameObject> enemies = 
            Utils.getInTurnsToAttack(castle, MyGame.getEnemies(), MyIceTroll.SUMMONING_DURATION);
        List<MyGameObject> killers = 
            Utils.getInTurnsToReach(castle, MyGame.getMyKillers(), MyIceTroll.SUMMONING_DURATION);
        
        int enStrength = Utils.getStrength(enemies);
        int myStrength = Utils.getStrength(killers);
        
        if (castle.getClosest(p, castle.getClosest(enemies)) == p && enStrength > myStrength) {
            if (p.summonIceTroll()) {
                return State.CONTINUE;
            }
        }
        return State.PASS;
    }
    
}