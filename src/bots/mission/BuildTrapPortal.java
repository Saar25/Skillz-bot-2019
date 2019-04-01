package bots.mission;

import bots.*;
import bots.wrapper.*;

/**
 * Kill close elf to the castle
 */
public class BuildTrapPortal implements Mission<MyElf> {
    
    private static ManaManager.Request request = null;
    
    @Override
    public State act(MyElf e) {
        // Check if non of the enemies are close
        MyElf enemy = Utils.getClosest(MyGame.getEnemyLivingElves(), e);
        if (enemy == null || enemy.inRange(enemy.getClosest(MyGame.getMyPortals()), 500)) {
            ManaManager.invalidate(request);
            return State.PASS;
        }
        
        ManaManager.Request request = ManaManager.getRequest(e);
        if (request == null) {
            e.request(MyPortal.COST);
            request = ManaManager.getRequest(e);
        }
        
        if (enemy.turnsToAttack(e) > MyPortal.BUILDING_DURATION) {
            request.validate();
            if (e.buildPortal(request)) {
                return State.FINISHED;
            } else if (!e.inRange(e.getClosest(MyGame.getMyPortals()), 200)) {
                e.escape(enemy);
                return State.FINISHED;
            } else {
                return State.PASS;
            }
        }
        e.moveTo(enemy);
        return State.FINISHED;
    }
    
}