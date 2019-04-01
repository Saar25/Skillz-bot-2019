package bots.mission;

import bots.wrapper.*;

import java.util.*;

/**
 * Cast speed up to the elf
 */
public class CastSpeedUpInDanger implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        int enemies = count(e, MyGame.getEnemyKillers());
        int mine = count(e, MyGame.getMyKillers());
        if (enemies > mine) {
            if (e.turnsForSpeedUp() <= 1 && e.castSpeedUp()) {
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