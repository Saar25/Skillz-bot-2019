package bots.mission;

import bots.wrapper.*;

import java.util.function.*;
import java.util.*;

/**
 * Make the elf attack the given object
 */
public class Attack implements Mission<MyElf> {
    
    private final Function<MyElf, MyGameObject> object;
    
    public Attack(Function<MyElf, MyGameObject> object) {
        this.object = object;
    }
    
    public Attack(Supplier<MyGameObject> object) {
        this.object = e -> object.get();
    }
    
    @Override
    public State act(MyElf e) {
        MyGameObject object = this.object.apply(e);
        if (e.attack(object)) {
            return State.FINISHED;
        } else {
            return State.PASS;
        }
    }
    
    public static class OrMove extends Attack implements Mission<MyElf> {
        
        public OrMove(Function<MyElf, MyGameObject> object) {
            super(object);
        }
        
        public OrMove(Supplier<MyGameObject> object) {
            super(object);
        }
        
        @Override
        public State act(MyElf e) {
            State state = super.act(e);
            if (state == State.PASS) {
                if (e.moveTo(super.object.apply(e))) {
                    return State.FINISHED;
                }
            }
            return state;
        }
        
    }
    
    public static class Closest extends Attack implements Mission<MyElf> {
        
        public Closest(Function<MyElf, List<? extends MyGameObject>> object) {
            super(e -> e.getClosest(object.apply(e)));
        }
        
        public Closest(Supplier<List<? extends MyGameObject>> object) {
            super(e -> e.getClosest(object.get()));
        }
    
        @Override
        public String getName() {
            return "Attack";
        }
        
    }
}