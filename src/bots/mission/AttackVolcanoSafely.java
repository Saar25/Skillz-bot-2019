package bots.mission;

import bots.*;
import bots.wrapper.*;

import java.util.*;


public class AttackVolcanoSafely implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        MyVolcano volcano = MyGame.getVolcano();
        if (!volcano.isActive() || !e.inAttackRange(volcano) 
            || volcano.damageByEnemy * 2 >= volcano.maxHealth) {
            return State.PASS;
        }
        
        List<MyGameObject> enemies = MyGame.getEnemyKillers();
        List<MyGameObject> myDefenders = MyGame.getMyKillers();
        int health = MyGame.volcanoMaxHealth / 2 - volcano.damageByMe;
        int turns = health / e.getAttackMultiplier();
        
        enemies = Utils.getInTurnsToAttack(e, enemies, turns);
        if (enemies.size() > myDefenders.size() || e.currentHealth * 2 <= e.maxHealth) {
            if (e.attack(volcano)) {
                return State.FINISHED;
            }
        }
        return State.PASS;
    }
}