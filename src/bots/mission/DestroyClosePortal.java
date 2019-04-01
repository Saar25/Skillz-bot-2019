package bots.mission;

import bots.*;
import bots.wrapper.*;

/**
 * Destroy any portal that in range from the castle
 */
public class DestroyClosePortal implements Mission<MyElf> {
    
    private final int range;
    
    public DestroyClosePortal(int range) {
        this.range = range;
    }
    
    @Override
    public State act(MyElf e) {
        MyCastle myCastle = MyGame.getMyCastle();
        MyPortal closestPortal = Utils.getClosest(MyGame.getEnemyPortals(), myCastle);
        if (closestPortal == null) return State.PASS;
        if (myCastle.inRange(closestPortal, range)) {
            e.attackOrMove(closestPortal);
            return State.FINISHED;
        } else {
            return State.PASS;
        }
    }
    
}