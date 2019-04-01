package bots.mission;

import bots.*;
import bots.wrapper.*;


public class BuildManaFountainAt implements Mission<MyElf> {
    
    private final Vector2 location;
    
    public BuildManaFountainAt(MyMapObject location) {
        this.location = location.getLocation();
    }
    
    @Override
    public State act(MyElf e) {
        if (location.inRange(e, MyElf.MAX_SPEED) && e.canBuildManaFountain()) {
             e.buildManaFountain();
             return State.FINISHED;
        } else if (e.moveTo(location)){
            return State.FINISHED;
        } else {
            return State.PASS;
        }
    }
    
}