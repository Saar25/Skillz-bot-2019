package bots.mission;

import bots.wrapper.*;

/**
 * Kill close elf to the castle
 */
public class BuildAmazingPortal implements Mission<MyElf> {
    
    private static final int R = 2000;
    
    @Override
    public State act(MyElf e) {
        if (e.getInRange(MyGame.getEnemyKillers(), 500).size() > 3) {
            return State.PASS;
        }
        if (MyGame.getEnemyCastle().getInRange(MyGame.getMyPortals(), R).size() < 3 
                && e.inRange(MyGame.getEnemyCastle(), R) && e.canBuildPortal()) {
            e.buildPortal();
            return State.FINISHED;
        }
        return State.PASS;
    }
    
}