package bots.mission;

import bots.wrapper.*;

/**
 * Kill close elf to the castle
 */
public class BuildPortal implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        if (e.buildPortal()) {
            return State.FINISHED;
        } else {
            return State.PASS;
        }
    }
    
}