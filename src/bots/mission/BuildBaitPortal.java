package bots.mission;

import bots.wrapper.*;

/**
 * Build a  portal when the douche
 */
public class BuildBaitPortal implements Mission<MyElf> {
    
    private static final int portalRange = 100;
    
    @Override
    public State act(MyElf e) {
        final MyPortal closestPortal = e.getClosest(MyGame.getMyPortals());
        if (closestPortal != null && closestPortal.inRange(e, portalRange)) {
            return State.PASS;
        }
        
        final MyElf enemyElf = e.getClosest(MyGame.getEnemyLivingElves());
        if (enemyElf != null && enemyElf.turnsToAttack(e) >= MyPortal.BUILDING_DURATION) {
            if (e.buildPortal()) {
                return State.FINISHED;
            }
        }
        return State.PASS;
    }
    
}