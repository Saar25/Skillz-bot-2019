package bots.mission;

import bots.*;
import bots.wrapper.*;

import java.util.*;

/**
 * Attack the portal closest to our castle
 * If not close enough move to the portal location
 */
public class DestroyPortals implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        MyCastle myCastle = MyGame.getMyCastle();
        List<MyPortal> portals = MyGame.getEnemyPortals();
        MyPortal portal = Utils.getClosest(portals, e);
        //MyPortal portal = Utils.getClosest(portals, mycastle);
        if (portal == null) return State.PASS;
        if (!e.attack(portal)) { //CanAttack?
            MyGameObject enemy = Utils.getClosest(MyGame.getEnemyKillers(), e);
            e.moveToAround(portal, enemy);
        }
        return State.CONTINUE;
    }
    
}