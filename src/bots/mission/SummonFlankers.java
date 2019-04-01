package bots.mission;

import bots.*;
import bots.wrapper.*;

/**
 * Summon lava giants very close to the enenmy castle
 */
public class SummonFlankers implements Mission<MyPortal> {
    
    @Override
    public State act(MyPortal p) {
        final MyCastle enemyCastle = MyGame.getEnemyCastle();
        if (Utils.getBetween(p, enemyCastle, MyGame.getEnemyPortals()).size() != 0) {
            return State.PASS;
        }
        
        final int range = getMaxRange();
        if (p.inRange(enemyCastle, range)) {
            if (p.summonLavaGiant()) {
                return State.FINISHED;
            }
        }
        return State.PASS;
    }
    
    private int getMaxRange() {
        return MyPortal.SIZE + MyCastle.SIZE + MyLavaGiant.MAX_SPEED;
    }
}