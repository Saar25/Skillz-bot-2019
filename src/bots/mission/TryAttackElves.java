package bots.mission;

import bots.wrapper.*;

import java.util.*;

/**
 * Kill close elf to the castle
 */
public class TryAttackElves implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        List<MyElf> enemies = MyGame.getEnemyLivingElves();
        MyElf enemy = e.getClosest(enemies);
        
        if (e.attack(enemy)) {
            return State.FINISHED;
        } else {
            return State.PASS;
        }
    }
    
}