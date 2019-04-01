package bots.mission;

import bots.wrapper.*;


public class SummonClearOurBase implements Mission<MyPortal> {
    
    @Override
    public State act(MyPortal p) {
        final MyCastle myCastle = MyGame.getMyCastle();
        final MyPortal portal = myCastle.getClosest(MyGame.getEnemyPortals());
        if (portal == null) return State.PASS;
        
        // Check if portal not even close to our castle
        int range = MyCastle.SIZE + MyPortal.SIZE + MyLavaGiant.MAX_SPEED * 3;
        if (!portal.inRange(myCastle, range)) {
            return State.PASS;
        }
        
        final MyTornado tornado = portal.getClosest(MyGame.getMyTornadoes());
        if (tornado == null || tornado.healthWhenAttack(p) * tornado.getAttackMultiplier() < p.currentHealth) {
            if (p.summonTornado()) {
                return State.FINISHED;
            } else if (p.getRequest() == null) {
                p.request(MyTornado.COST);
            }
        }
        
        return State.PASS;
    }
    
}