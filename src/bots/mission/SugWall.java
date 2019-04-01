package bots.mission;

import bots.*;
import bots.wrapper.*;

/**
 * Keep those immigrants out
 * 
 * SEGEV IMPLEMENTATION
 */
public class SugWall implements Mission<MyElf> {
    
    private static final double R = 900;
    
    private final GoTo gotoSug = new GoTo();
    private final Build buildSug = new Build();
    
    @Override
    public State act(MyElf e) {
        State state = gotoSug.act(e);
        if (state == State.PASS) {
            state = buildSug.act(e);
        }
        return state;
    }
    
    private static Vector2 getMiddle() {
        return MyGame.getEnemyCastle().getLocation()
                .sub(MyGame.getMyCastle().getLocation()).normalize(R)
                .add(MyGame.getMyCastle().getLocation());
    }
    
    public static boolean isNeeded() {
        return MyGame.canBuildPortalAt(getMiddle());
    }
    
    public static class GoTo implements Mission<MyElf> {
        @Override
        public State act(MyElf e) {
            Vector2 middle = getMiddle();
            
            if (MyGame.canBuildPortalAt(middle)) {
                if (e.inRange(middle, 50)) {
                    return State.PASS;
                } else {
                    e.moveTo(middle);
                    return State.FINISHED;
                }
            }
            return State.PASS;
        }
    }
    
    public static class Build implements Mission<MyElf> {
        
        @Override
        public State act(MyElf e) {
            Vector2 middle = getMiddle();
            
            ManaManager.Request request = ManaManager.getRequest(e);
            if (MyGame.canBuildPortalAt(middle)) {
                if (request == null) {
                    e.request(MyPortal.COST);
                    request = ManaManager.getRequest(e);
                }
                if (e.inRange(middle, 50)) {
                    if (e.buildPortal(request)) {
                        return State.FINISHED;
                    } else {
                        request.validate();
                    }
                    return State.CONTINUE;
                }
            } else {
                ManaManager.invalidate(request);
            }
            
            return State.PASS;
        }
    }
    
}