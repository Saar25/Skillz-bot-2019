package bots.mission;

import bots.wrapper.*;

/**
 * Destroy the closest enemy ManaFountain
 */
public class DestroyManaFountain implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        MyManaFountain mf = e.getClosest(MyGame.getEnemyManaFountains());
        if (mf != null) {
            e.attackOrMove(mf);
            return State.FINISHED;
        }
        return State.PASS;
    }
    
}