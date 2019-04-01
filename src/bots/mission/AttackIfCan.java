package bots.mission;

import bots.wrapper.*;

import java.util.*;

/**
 * DRY - Don't Repeat Yourself
 */
public class AttackIfCan implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        
        List<MyGameObject> enemies = new ArrayList<>();
        enemies.addAll(MyGame.getEnemyLivingElves());
        enemies.addAll(MyGame.getEnemyLavaGiants());
        enemies.addAll(MyGame.getEnemyTornadoes());
        enemies.addAll(MyGame.getEnemyIceTrolls());
        enemies.addAll(MyGame.getEnemyPortals());
        
        for (MyGameObject enemy : enemies) {
            if (e.attack(enemy)){
                return State.FINISHED;
            }
        }
        return State.PASS;
    }
}