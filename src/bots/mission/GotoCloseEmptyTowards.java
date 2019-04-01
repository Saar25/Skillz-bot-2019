package bots.mission;

import bots.*;
import bots.wrapper.*;

import java.util.function.*;


public class GotoCloseEmptyTowards implements Mission<MyElf> {
    
    private final Function<MyElf, MyMapObject> destination;
    private final int size;
    
    public GotoCloseEmptyTowards(Supplier<MyMapObject> destination, int size) {
        this.destination = e -> destination.get();
        this.size = size;
    }
    
    public GotoCloseEmptyTowards(Function<MyElf, MyMapObject> destination, int size) {
        this.destination = destination;
        this.size = size;
    }
    
    @Override
    public State act(MyElf e) {
        final double delta = 5;
        Vector2 dest = e.getLocation();
        Vector2 destination = this.destination.apply(e).getLocation();
        Vector2 direction = destination.sub(dest).normalize(delta);
        
        int distance2 = e.distance2(destination);
        Vector2 finalDest = dest;
        while (dest.inMap()) {
            if (MyGame.canBuildAt(dest, size)) {
                int newDistance = e.distance2(dest) + destination.distance2(dest);
                if (distance2 > newDistance) {
                    distance2 = newDistance;
                    finalDest = dest;
                }
            }
            dest = dest.add(direction);
        }
        if (!finalDest.inMap()) {
            return State.PASS;
        }
        if (e.moveTo(finalDest)) {
            return State.FINISHED;
        }
        return State.PASS;
    }
}