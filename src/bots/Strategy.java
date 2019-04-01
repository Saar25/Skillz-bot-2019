package bots;

import bots.mission.Mission;
import bots.wrapper.*;

import java.util.function.*;
import java.util.*;

public class Strategy<T> implements Mission<T> {
    
    private final Queue<Tuple<T>> missions;
    private final String name;
    
    public Strategy(String name) {
        this.missions = new LinkedList<>();
        this.name = name;
    }
    
    public Strategy() {
        this("");
    }
    
    public void addMission(Mission<T> mission) {
        Tuple<T> tuple = new Tuple<>(mission, e -> true);
        this.missions.add(tuple);
    }
    
    public void addMission(Mission<T> mission, Predicate<T> predicate) {
        Tuple<T> tuple = new Tuple<>(mission, predicate);
        this.missions.add(tuple);
    }
    
    @Override
    public State act(T t) {
        String message = getStrategyMessage(t);
        
        if (alreadyActed(t)) {
            MyGame.debug(message + " is in the middle of something");
            return State.PASS;
        }
        if (missions.isEmpty()) {
            MyGame.debug(message + " Could not found a mission");
            return State.PASS;
        }
        
        Tuple<T> tuple = missions.peek();
        Mission<T> mission = tuple.mission;
        Predicate<T> predicate = tuple.predicate;
        State state = State.PASS;
        if (predicate.test(t)) {
            state = mission.act(t);
        }
        
        message = getMessage(t, mission);
        
        switch (state) {
            case FINISHED:
                MyGame.debug(message);
                missions.remove();
                break;
            case CONTINUE:
                MyGame.debug(message);
                break;
            case PASS:
                missions.remove();
                state = act(t);
                break;
        }
        
        return state;
    }
    
    private String getMessage(T t, Mission<T> mission) {
        String message = getStrategyMessage(t);
        message += " does mission " + mission.getName();
        //message += ", priority level: " + mission.getPriority(t);
        return message;
    }
    
    private String getStrategyMessage(T t) {
        String message = t.toString();
        if (!name.equals("")) {
            message += " is running " + name + ",";
        }
        return message;
    }
    
    private boolean alreadyActed(T t) {
        if (t instanceof MyElf) {
            return ((MyElf) t).alreadyActed;
        }
        if (t instanceof MyPortal) {
            return ((MyPortal) t).alreadyActed;
        }
        return false;
    }
    
    private static class Tuple<T> {
        private final Mission<T> mission;
        private final Predicate<T> predicate;
        public Tuple(Mission<T> mission, Predicate<T> predicate) {
            this.mission = mission;
            this.predicate = predicate;
        }
    }
    
}