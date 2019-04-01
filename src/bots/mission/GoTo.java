package bots.mission;

import bots.wrapper.*;

import java.util.function.*;

/**
 * Make the elf go to the given location
 * 
 * example:
 *      new GoTo(e -> MyGame.getEnemyCastle(), 5);
 * 
 * The destination is a function so it can be dynamic.
 * e.getClosest... will return the current closest, while 
 * e -> e.getClosest... will return the closest when the function invoked
 */
public class GoTo implements Mission<MyElf> {
    
    private final Function<MyElf, MyMapObject> dest;
    private final int eps;
    
    public GoTo(Function<MyElf, MyMapObject> dest) {
        this.dest = dest;
        this.eps = 5;
    }
    
    public GoTo(Function<MyElf, MyMapObject> dest, int eps) {
        this.dest = dest;
        this.eps = eps;
    }
    
    public GoTo(Supplier<MyMapObject> dest) {
        this.dest = e -> dest.get();
        this.eps = 5;
    }
    
    public GoTo(Supplier<MyMapObject> dest, int eps) {
        this.dest = e -> dest.get();
        this.eps = eps;
    }
    
    @Override
    public State act(MyElf e) {
        MyMapObject destination = dest.apply(e);
        if (!e.inRange(destination, eps)) {
            e.moveTo(destination);
            return State.FINISHED;
        } else {
            return State.PASS;
        }
    }
}