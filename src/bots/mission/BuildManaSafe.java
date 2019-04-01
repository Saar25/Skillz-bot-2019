package bots.mission;

import bots.*;
import bots.wrapper.*;


public class BuildManaSafe implements Mission<MyElf> {

    private final Build bulidSafe = new Build();
    
    @Override
    public State act(MyElf e) {
        State state = bulidSafe.act(e);
        if (state == State.PASS) {
            return bulidSafe.act(e);
        }
        return State.PASS;
    }
    public static class Build implements Mission<MyElf> {
        
        private static ManaManager.Request request = null;
    
        @Override
        public State act(MyElf e) {
            //Vector2 elfLocation = e.getLocation();
            if (e.canBuildManaFountain()) {
                    if (request == null) {
                        request = ManaManager.request(MyManaFountain.COST);
                    }
                    request.validate();
                    if (e.buildManaFountain()) {
                        request = null;
                        return State.FINISHED;
                    } else if (request == null || !request.isValid()) {
                        request = ManaManager.request(MyManaFountain.COST);
                    } else {
                        request.validate();
                    }
                    return State.PASS;
                }
            ManaManager.invalidate(request);
            return State.PASS;
            }
        }
    /**
    private static Vector2 getMiddle(MyElf e) {
        return new Vector2(MyGame.getEnemyCastle().getLocation())
                .sub(e.getLocation()).normalize(R)
                .add(e.getLocation());
    }
    
    public static class GoTo implements Mission<MyElf> {
        @Override
        public State act(MyElf e) {
            Vector2 middle = getMiddle(e);
            
            if (MyGame.canBuildManaFountainAt(middle)) {
                if (e.inRange(middle, 500)) {
                    return State.PASS;
                } //else {
                    //e.moveTo(middle);
                    //return State.FINISHED;
                //}
            }
            return State.FINISHED;
        }
    }
    
    public static class Build implements Mission<MyElf> {
        
        private static ManaManager.Request request = null;
    
        @Override
        public State act(MyElf e) {
            Vector2 middle = getMiddle(e);
            if (MyGame.canBuildManaFountainAt(middle)) {
                if (e.inRange(middle, 500)) {
                    if (request == null) {
                        request = ManaManager.request(MyManaFountain.COST);
                    }
                    request.validate();
                    if (e.buildManaFountain(request)) {
                        request = null;
                        return State.FINISHED;
                    } else if (request == null || !request.isValid()) {
                        request = ManaManager.request(MyManaFountain.COST);
                    } else {
                        request.validate();
                    }
                    return State.PASS;
                }
            } else {
                ManaManager.invalidate(request);
            }
            
            return State.PASS;
        }
    }*/
    }

    

    
