package bots.mission;

import bots.*;
import bots.wrapper.*;

import java.util.*;

/**
 * Build a portal when in range from enemy castle
 */
public class BuildClosestPortal implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        MyCastle enemyCastle = MyGame.getEnemyCastle();
        List<MyPortal> myPortals = MyGame.getMyPortals();
        MyPortal closest = Utils.getClosest(myPortals, enemyCastle);
        if (Utils.getClosest(enemyCastle, closest, e) == e) {
            if (e.buildPortal()) {
                return State.FINISHED;
            }
        }
        return State.PASS;
    }
    
}