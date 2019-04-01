package bots.mission;

import bots.*;
import bots.wrapper.*;

import java.util.*;


public class SummonDefendPortal implements Mission<MyPortal> {
    
    private static ManaManager.Request request;
    
    @Override
    public State act(MyPortal p) {
        final List<MyGameObject> closeFriends = filter(p, MyGame.getMyKillers());
        final List<MyGameObject> closeEnemies = filter(p, MyGame.getEnemyDestroyers());
        
        int friendsStrength = Utils.getStrength(closeFriends);
        int enemiesStrength = Utils.getStrength(closeEnemies);
        
        if (friendsStrength < enemiesStrength) {
            if (p.summonIceTroll(request)) {
                return State.FINISHED;
            } else if (request == null) {
                request = ManaManager.request(MyIceTroll.COST);
            } else {
                request.validate();
            }
        }
        return State.PASS;
    }
    
    private List<MyGameObject> filter(MyPortal p, List<MyGameObject> list) {
        List<MyGameObject> close = new ArrayList<>();
        int turns = MyIceTroll.SUMMONING_DURATION;
        for (MyGameObject object : list) {
            if (object.turnsToAttack(p) <= turns) {
                if (!object.isEnemy() || !(object instanceof MyCreature) || ((MyCreature) object).getDestination() == p) {
                    close.add(object);
                }
            }
        }
        return close;
    }
    
}