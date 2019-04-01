package bots.mission;

import bots.*;
import bots.wrapper.*;

import java.util.*;

/**
 * Attack the closest enemy elf
 */
public class AttackElves implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        List<MyElf> elves = MyGame.getEnemyLivingElves();
        MyElf enemy = Utils.getClosest(elves, e);
        if (enemy == null) return State.PASS;
        if (!e.attackOrMove(enemy)) {
            return State.PASS;
        }
        return State.CONTINUE;
    }
    
}