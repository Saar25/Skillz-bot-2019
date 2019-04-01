package bots.mission;

import bots.*;
import bots.wrapper.*;

import java.util.function.*;


public class GoToBehind implements Mission<MyElf> {
    
    private static final int EPS = 5;
    
    private final Function<MyElf, MyMapObject> destination;
    
    public GoToBehind(Supplier<MyMapObject> destination) {
        this.destination = e -> destination.get();
    }
    
    public GoToBehind(Function<MyElf, MyMapObject> destination) {
        this.destination = destination;
    }
    
    @Override
    public State act(MyElf e) {
        MyMapObject destination = this.destination.apply(e);
        if (destination == null) return State.PASS;
        
        MyGameObject enemy = destination.getClosest(MyGame.getEnemyKillers());
        if (enemy == null) {
            Mission<MyElf> go = new GoTo(this.destination);
            return go.act(e);
        }
        
        int range = destination.getSize() + e.getAttackRange();
        Vector2 dest = destination.getLocation().towards(enemy.getLocation(), -range);
        dest = dest.clamp(0, MyGame.rows - 1, 0, MyGame.cols - 1);
        
        if (!e.inRange(dest, EPS)) {
            e.moveTo(dest);
            return State.FINISHED;
        } else {
            return State.PASS;
        }
    }
}