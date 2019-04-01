package bots.mission;

import bots.wrapper.*;

/**
 * Try destroy the enmey building
 */
public class TryDestroyBuilding implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        MyBuilding enemyBuilding = e.getClosest(MyGame.getEnemyBuildings());
        
        if (e.attack(enemyBuilding)) {
            return State.FINISHED;
        } else {
            return State.PASS;
        }
    }
    
}