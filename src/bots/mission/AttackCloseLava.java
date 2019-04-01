package bots.mission;

import bots.*;
import bots.wrapper.*;

public class AttackCloseLava implements Mission<MyElf> {
    
   @Override
    public State act(MyElf e) {
        MyLavaGiant enemy = Utils.getClosest(MyGame.getEnemyLavaGiants(), e);
        if (e.attack(enemy)) {
            return State.FINISHED;
        }
        return State.PASS;
    }
    
}