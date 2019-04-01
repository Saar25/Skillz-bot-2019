package bots.mission;

import bots.wrapper.*;

/**
 * Cast speed up to the elf
 */
public class SpeedUpIfSlow implements Mission<MyElf> {
    
    private final int minSpeed;
    
    public SpeedUpIfSlow(int minSpeed) {
        this.minSpeed = minSpeed;
    }
    
    @Override
    public State act(MyElf e) {
        if (e.maxSpeed < minSpeed && e.maxSpeed * MySpell.speedUpMultiplier >= minSpeed) {
            if (e.canCastSpeedUp()) {
                e.castSpeedUp();
                return State.FINISHED;
            }
        }
        return State.PASS;
    }
}