package bots.mission;

import bots.*;
import bots.wrapper.*;

/**
 * Distract enemies
 */
public class SummonDistraction implements Mission<MyPortal> {
    
    @Override
    public State act(MyPortal p) {
        int closeEnemies = Utils.getInRange(MyGame.getEnemyIceTrolls(), p, MyPortal.SIZE * 5).size();
        int closeDefenders = Utils.getInRange(MyGame.getMyIceTrolls(), p, MyPortal.SIZE * 5).size();
        if (closeEnemies > 2 && closeEnemies >= closeDefenders) {
            if (p.summonLavaGiant()) {
                return State.CONTINUE;
            }
        }
        return State.PASS;
    }
    
}