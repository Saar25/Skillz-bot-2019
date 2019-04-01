package bots.mission;

import bots.wrapper.*;

/**
 * Move to the emeny castle while escaping enemies
 */
public class BuildPortalInDanger implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        MyGameObject enemy = e.getClosest(MyGame.getEnemyKillers());
        if (enemy == null) return State.PASS;
        
        int range = (MyPortal.BUILDING_DURATION + 3) * enemy.getMaxSpeed() + enemy.getAttackRange();
        int minRange = e.getMaxSpeed() + enemy.getMaxSpeed() + enemy.getAttackRange();
        if (e.inRange(enemy, range) && !e.inRange(enemy, minRange) && e.buildPortal()) {
            return State.FINISHED;
        }
        return State.PASS;
    }
}
