package bots.mission;

import bots.wrapper.*;

/**
 * Attack the enemy castle
 */
public class AttackCastle implements Mission<MyElf> {
    
    @Override
    public Mission.State act(MyElf e) {
        MyCastle enemyCastle = MyGame.getEnemyCastle();
        e.attackOrMove(enemyCastle);
        return Mission.State.CONTINUE;
    }
    
}