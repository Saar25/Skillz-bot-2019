package bots.mission;

import bots.*;
import bots.wrapper.*;

import java.util.*;

/**
 * Move to the emeny castle while escaping enemies
 */
public class GoNearEnemyCastle implements Mission<MyElf> {
    
    private static final int castleDistance = 1000;
    
    @Override
    public State act(MyElf e) {
        MyCastle enemyCastle = MyGame.getEnemyCastle();
        if (!e.inRange(enemyCastle, castleDistance)) {
            List<MyGameObject> enemies = MyGame.getEnemyKillers();
            MyGameObject enemy = Utils.getClosest(enemies, e);
            Vector2 dest = MyGame.getMyCastle().getLocation()
                .sub(enemyCastle.getLocation())
                .normalize(castleDistance)
                .rotate(Math.PI / 4)
                .add(enemyCastle.getLocation());
            int count = 0;
            while (!MyGame.canBuildPortalAt(dest)) {
                dest = dest.sub(enemyCastle.getLocation());
                dest = dest.rotate(-Math.PI / 16);
                if (count++ > 100) {
                    dest = enemyCastle.getLocation();
                    break;
                }
                dest = dest.add(enemyCastle.getLocation());
            }
            e.moveToAround(dest, enemy);
            return State.FINISHED;
        } else {
            return State.PASS;
        }
    }
    
}