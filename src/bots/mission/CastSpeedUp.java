package bots.mission;

import bots.wrapper.*;

/**
 * Cast speed up to the elf
 */
public class CastSpeedUp implements Mission<MyElf> {
    
    @Override
    public State act(MyElf e) {
        if (!e.isSpeedUp() && e.canCastSpeedUp()) {
            e.castSpeedUp();
            return State.FINISHED;
        } else {
            return State.PASS;
        }
    }
}