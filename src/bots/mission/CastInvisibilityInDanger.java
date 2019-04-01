package bots.mission;

import bots.wrapper.*;

import java.util.*;

/**
 * Cast speed up to the elf
 */
public class CastInvisibilityInDanger implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        int enemies = count(e, MyGame.getEnemyKillers());
        int mine = count(e, MyGame.getMyKillers());
        
        if (enemies > mine || (enemies >= 1 && e.currentHealth * 3 <= e.maxHealth)) {
            if (e.turnsForInvisibility() <= 1 && e.castInvisibility()) {
                return State.FINISHED;
            }
        }
        return State.PASS;
    }
    
    private int count(MyElf e, List<MyGameObject> killers) {
        return (int) killers.stream()
                .filter(k -> e.inRange(k, k.getMaxSpeed() + e.getMaxSpeed() + k.getAttackRange()))
                .count();
    } 
}