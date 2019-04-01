package bots.mission;

import bots.wrapper.*;

/**
 * Move to the emeny castle while escaping enemies
 */
public class GoToTheClosestEnemy implements Mission<MyElf> {
    
    private static final int castleDistance = 1000;
    
    @Override
    public State act(MyElf e) {
        MyGameObject closestEnemy = e.getClosest(MyGame.getEnemyCreatures());
        if (e.moveTo(closestEnemy)) {
            return State.PASS;
        } else {
            return State.FINISHED;
        }
    }
}
