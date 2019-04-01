package bots.mission;

import bots.*;
import bots.wrapper.*;

/**
 * Escape douchebags
 */
public class EscapeEnemies implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        MyGameObject enemy = Utils.getClosest(MyGame.getEnemyKillers(), e);
        if (enemy == null) return State.PASS;
        
        final int range = enemy.getAttackRange() + enemy.getMaxSpeed() + e.getMaxSpeed();
        if (enemy.inAttackRange(e)) {
            return State.PASS;
        } else if (e.inRange(enemy, range)) {
            e.escape(enemy);
            return State.FINISHED;
        } else if (true) {
            return State.PASS;
        }
        
        int myArmy    = Utils.getInRange(MyGame.getMyKillers(), enemy, 500).size();
        int enemyArmy = Utils.getInRange(MyGame.getEnemyKillers(),  e, 500).size();
        if (enemyArmy < myArmy) return State.PASS;
        
        int radius = enemy.getAttackRange() + 
            enemy.getMaxSpeed() + e.getMaxSpeed();
        if (!enemy.inRange(e, radius)) {
            return State.PASS;
        }
        Vector2 destination = e.getLocation()
            .sub(enemy.getLocation())
            .normalize(e.maxSpeed * 2)
            .add(e.getLocation());
            
        if (!destination.inMap()) {
            destination = destination.sub(e.getLocation());
            double x = Utils.clamp(destination.x , 0, MyGame.cols - 1);
            double y = Utils.clamp(destination.y , 0, MyGame.rows - 1);
            destination = new Vector2(x, y);
        }
        e.moveTo(destination);
        return State.CONTINUE;
    }
    
}