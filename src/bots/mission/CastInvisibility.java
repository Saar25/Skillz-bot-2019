package bots.mission;

import bots.wrapper.*;

/**
 * Cast invisibility to the elf
 */
public class CastInvisibility implements Mission<MyElf> {
    
    /*
    * You can just add this to flank attack strategy
    * instead of copying the FlankAttack class
    */
    
    @Override
    public State act(MyElf e) {
        if (!e.isInvisible() && e.canCastInvisibility()) {
            e.castInvisibility();
            return State.FINISHED;
        } else {
            return State.PASS;
        }
    }
}