package bots.mission;

import bots.*;
import bots.wrapper.*;

import java.util.function.*;

/**
 * Make the elf attack the given object
 */
public class GotoCloseEmpty implements Mission<MyElf> {
    
    private static final int EPS = 10;
    private static final int checks = 10;
    private static final int maxDistance = 
                (int) Math.pow(MyGame.rows + MyGame.cols, 2);
    
    private final int size;
    private final Function<MyElf, MyMapObject> destination;
    
    public GotoCloseEmpty(int size) {
        this.size = size;
        this.destination = e -> e;
    }
    
    public GotoCloseEmpty(Supplier<MyMapObject> destination, int size) {
        this.size = size;
        this.destination = e -> destination.get();
    }
    
    public GotoCloseEmpty(Function<MyElf, MyMapObject> destination, int size) {
        this.size = size;
        this.destination = destination;
    }
    
    @Override
    public State act(MyElf e) {
        MyMapObject center = destination.apply(e);
        int size = this.size; 
        if (center instanceof MyBuilding) {
            size += ((MyBuilding) center).size;
        }
        
        Vector2 v1 = center.getLocation().sub(size, size);
        Vector2 v2 = center.getLocation().add(size, size);
        Vector2 inc1 = v1.getClosest(MyGame.edges).sub(v1).div(checks);
        Vector2 inc2 = v2.getClosest(MyGame.edges).sub(v2).div(checks);
        
        Vector2 dest = null;
        for (int i = 0; dest == null && i < checks; i++) {
            dest = findEmpty(e, v1.x, v2.x, v1.y, v2.y);
            v1 = v1.add(inc1); v2 = v2.add(inc2);
        }
        if (dest != null && !e.inRange(dest, EPS / 2) && e.moveTo(dest)) {
            return State.FINISHED;
        } else {
            return State.PASS;
        }
    }
    
    private Vector2 findEmpty(MyElf e, int minX, int maxX, int minY, int maxY) {
        Vector2 dest = null;
        int distance = maxDistance;
        Vector2 center = destination.apply(e).getLocation();
        for (int x = minX; x < maxX; x += 10) {
            for (int y = minY; y < maxY; y += 10) {
                final Vector2 newDest = new Vector2(x, y);
                final int newDistance = e.distance2(newDest) + center.distance2(newDest);
                
                if (newDistance < distance && MyGame.canBuildAt(newDest, size + EPS)) {
                    distance = newDistance;
                    dest = newDest;
                }
            }
        }
        return dest;
    }
    
}