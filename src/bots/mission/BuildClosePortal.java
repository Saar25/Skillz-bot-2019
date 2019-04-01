package bots.mission;

import bots.wrapper.*;

/**
 * Build a portal when in range from enemy castle
 */
public class BuildClosePortal implements Mission<MyElf> {
    
    private final int range;
    
    public BuildClosePortal(int range) {
        this.range = range;
    }
    
    @Override
    public State act(MyElf e) {
        MyCastle enemyCastle = MyGame.getEnemyCastle();
        if (e.inRange(enemyCastle, range)) {
            if (e.buildPortal()) {
                return State.FINISHED;
            }
        }
        return State.PASS;
    }
    
}