package bots.mission;

import bots.wrapper.*;

import java.util.*;


public class AttackWeakElves implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        List<MyElf> enemies = MyGame.getEnemyLivingElves();
        MyElf enemy = e.getClosest(enemies);
        if (enemy == null) {
            return State.PASS;
        }
        if (enemy.currentHealth <= e.attackMultiplier) {
            if (e.attack(enemy)) {
                return State.FINISHED;
            }
        }
        return State.PASS;
    }
}