package bots.mission;

import bots.*;
import bots.wrapper.*;

import java.util.*;

/**
 * Escape enemies if the elf is weaker
 */
public class EscapeIfWeaker implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        //if (e.isInvisible()) {
            //return State.PASS;
        //}
        
        final MyGameObject enemy = e.getClosest(MyGame.getEnemyKillers());
        
        if (enemy == null) {
            return State.PASS;
        }
        
        final int maxRange = e.getAttackRange() + e.getMaxSpeed() + enemy.getAttackRange();
        final int range = e.distance(enemy);
        if (range > maxRange) {
            return State.PASS;
        }
        
        final List<MyGameObject> friends = Utils.getInTurnsToAttack(e, MyGame.getMyKillers(), 5);
        final List<MyGameObject> enemies = Utils.getInTurnsToAttack(e, MyGame.getEnemyKillers(), 5);
        
        int enemyStrength = 0;
        for (MyGameObject obj : enemies) {
            enemyStrength += obj.getStrength();
        }
        int myStrength = 0;
        for (MyGameObject obj : friends) {
            myStrength += obj.getStrength();
        }
        if (enemyStrength > myStrength) {
            e.escape(e.getClosest(enemies));
            return State.FINISHED;
        } else {
            return State.PASS;
        }
        
    }
}