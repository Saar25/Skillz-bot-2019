package bots.mission;

import bots.wrapper.*;


public class SummonAttackWhenRich implements Mission<MyPortal> {
    
    private static final int mana = 3000;
    private static boolean rich = false;
    
    @Override
    public State act(MyPortal p) {
        rich = rich || MyGame.getMyself().mana > mana;
        
        if (rich && p.summonLavaGiant()) {
            return State.FINISHED;
        }
        
        return State.PASS;
    }
    
}