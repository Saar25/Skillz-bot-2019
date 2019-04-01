package bots.mission;

import bots.*;
import bots.wrapper.*;

import java.util.*;


public class SummonTornado implements Mission<MyPortal> {
    
    @Override
    public State act(MyPortal p) {
        MyBuilding building = p.getClosest(MyGame.getEnemyActiveBuildings());
        MyGame.debug(building);
        
        if (building == null || !building.isClosest(p)) return State.PASS;
        
        if (!canDestroy(p, building) && potentialDamage(p, building) >= building.currentHealth) {
            if (p.summonTornado()) {
                return State.FINISHED;
            }
        }
        
        return State.PASS;
    }
    
    private int potentialDamage(MyMapObject p, MyBuilding building) {
        int turns = (p.distance(building) - building.size - MyTornado.ATTACK_RANGE) / MyTornado.MAX_SPEED;
        int healthWhenReach = MyTornado.MAX_HEALTH - turns * MyTornado.SUFFOCATION_PER_TURN;
        return healthWhenReach * MyTornado.SUFFOCATION_PER_TURN;
    }
    
    private boolean canDestroy(MyPortal p, MyBuilding building) {
        int pd = 0;
        List<MyGameObject> destroyers = Utils.getBetween(building, p, MyGame.getMyDestroyers());
        for (MyGameObject destroyer : destroyers) {
            pd += potentialDamage(destroyer, building);
        }
        return pd >= building.currentHealth;
    }
    
}