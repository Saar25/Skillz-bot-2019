package bots.mission;

import bots.*;
import bots.wrapper.*;
import java.util.*;

/**
 * Keep those immigrants out 
 * 
 * WARNING : DOESNT WORK WHEN WE ON THE ORANGE SIDE
 */
public class TrumpWall implements Mission<MyElf> {
    
    private static final double R = 1500;
    private static final int count = 4;
    private static final double angleRange = Math.PI / 3;
    
    private static ManaManager.Request request = null;
    
    @Override
    public State act(MyElf e) {
        Vector2 toEnemy = MyGame.getEnemyCastle().getLocation()
            .sub(MyGame.getMyCastle().getLocation())
            .normalize(R);
        
        List<Vector2> locations = new ArrayList<>();
        for (int i = 0; i < count / 2; i++) {
            double angle = i * angleRange / count;
            Vector2 rotated = toEnemy.rotate(angle).add(MyGame.getMyCastle().getLocation());
            if (MyGame.canBuildPortalAt(rotated)) {
                locations.add(rotated);
            }
            
            angle = -i * angleRange / count;
            rotated = toEnemy.rotate(angle).add(MyGame.getMyCastle().getLocation());
            if (MyGame.canBuildPortalAt(rotated)) {
                locations.add(rotated);
            }
        }
        
        if (request == null) {
            request = ManaManager.request(MyPortal.COST);
        }
        
        Vector2 portalLocation = Utils.getClosest(locations, e);
        if (portalLocation == null) {
            request.invalidate();
            return State.PASS;
        }
        
        request.validate();
        if (e.inRange(portalLocation, 100) && e.canBuildPortal(request)) {
            e.buildPortal(request);
        } else {
            e.moveTo(portalLocation);
        }
        return State.FINISHED;
    }
    
}