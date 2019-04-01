package bots.mission;

import bots.wrapper.*;


public class SummonIceTroll2 implements Mission<MyPortal> {
    
    @Override
    public State act(MyPortal p) {
        if (p.summonIceTroll()) {
            return State.CONTINUE;
        }
        return State.PASS;
    }
    
}