package bots.mission;

import bots.wrapper.*;

/**
 * Kill close elf to the castle
 */
public class SummonLavaGiant implements Mission<MyPortal> {
    
    @Override
    public State act(MyPortal p) {
        if (p.inRange(MyGame.getEnemyCastle(), MyCastle.SIZE + MyPortal.SIZE + MyLavaGiant.MAX_SPEED)) {
            if (p.summonLavaGiant()) {
                return State.FINISHED;
            }
        }
        
        if (MyGame.getEnemyCastle().isClosest(p)
            && p.distance2(MyGame.getMyCastle()) > p.distance2(MyGame.getEnemyCastle())) {
            if (p.summonLavaGiant()) {
                return State.FINISHED;
            }
        }
        return State.PASS;
    }
    
}