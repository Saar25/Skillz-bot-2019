package bots.mission;

import bots.*;
import bots.wrapper.*;

/**
 * Kill any elf that in range from the castle
 */
public class KillCloseElves implements Mission<MyElf> {
    
    private final int range;
    
    public KillCloseElves(int range) {
        this.range = range;
    }
    
    @Override
    public State act(MyElf e) {
        //MyCastle myCastle = MyGame.getMyCastle();
        MyElf closestElf = Utils.getClosest(MyGame.getEnemyLivingElves(), e);
        // e instead of my castle
        if (closestElf == null) return State.PASS;
        if (e.inRange(closestElf, range)) {
            e.attackOrMove(closestElf);
            return State.FINISHED;
        } else {
            return State.PASS;
        }
    }
    
}