package bots.mission;

import bots.wrapper.*;


public class BuildManaFountain implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        if (e.buildManaFountain()) {
            return State.FINISHED;
        } else {
            return State.PASS;
        }
    }
    
}