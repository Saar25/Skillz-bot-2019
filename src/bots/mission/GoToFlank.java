package bots.mission;

import bots.*;
import bots.wrapper.*;

/**
 * Build a portal near the enemy castle
 */
public class GoToFlank implements Mission<MyElf> {
    
    private static final int EPS = 0;
    
    private static final Mission<MyElf> gotoClose = 
                    new GotoCloseEmpty(MyGame::getEnemyCastle, MyPortal.SIZE);
    
    @Override
    public State act(MyElf e) {
        Vector2 dest1 = transform(MyGame.getMyCastle().getLocation()
                        .sub(MyGame.getEnemyCastle().getLocation())
                        .perpendicularLeft());
        
        Vector2 dest2 = transform(MyGame.getMyCastle().getLocation()
                        .sub(MyGame.getEnemyCastle().getLocation())
                        .perpendicularRight());
        
        Vector2 dest = null;
        
        boolean build1 = MyGame.canBuildPortalAt(dest1);
        boolean build2 = MyGame.canBuildPortalAt(dest2);
        if (build1 && !build2) {
            dest = dest1;
        } else if (build2 && !build1) {
            dest = dest2;
        } else {
            dest = e.getClosest(dest1, dest2);
        }
        
        if (!dest.inMap() || !MyGame.canBuildPortalAt(dest)) {
            return gotoClose.act(e);
        }
        
        if (e.inRange(dest, EPS)) {
            return State.PASS;
        } else {
            e.moveToAroundEnemy(dest);
            return State.FINISHED;
        }
    }
    
    private static Vector2 transform(Vector2 v) {
        return v.normalize(MyPortal.SIZE + MyCastle.SIZE + 2)
                .add(MyGame.getEnemyCastle().getLocation())
                .clamp(MyPortal.SIZE, MyGame.rows - MyPortal.SIZE, 
                    MyPortal.SIZE, MyGame.cols - MyPortal.SIZE);
    }
    
    private Vector2 getDestination() {
        final int range = MyCastle.SIZE + MyPortal.SIZE;
        Vector2 myCastle = MyGame.getMyCastle().getLocation();
        Vector2 enCastle = MyGame.getEnemyCastle().getLocation();
        Vector2 direction = enCastle.towards(myCastle, -range);
        Vector2 destination = direction.add(enCastle);
        int times = 0;
        int maxTimes = 36;
        while (!MyGame.canBuildPortalAt(destination)) {
            direction = direction.rotate(Math.PI / maxTimes);
            destination = direction.add(enCastle);
            times++;
            if (times > maxTimes) {
                destination = null;
                break;
            }
        }
        return destination;
    }
}