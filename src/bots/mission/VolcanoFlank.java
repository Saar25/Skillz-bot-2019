package bots.mission;

import bots.wrapper.*;

import java.util.*;


public class VolcanoFlank implements Mission<MyElf> {

    @Override
    public State act(MyElf e) {
        MyVolcano volcano = MyGame.getVolcano();
        int volacnoLife = MyGame.volcanoMaxHealth;
        if (volcano.damageByMe > volacnoLife / 2){
            if (e.attack(volcano) && !elfInDanger(e)) {
                return State.FINISHED;
            }
            else {
                return State.PASS;
            }
        }
        else{
            if (e.attack(volcano)) {
                return State.FINISHED;
            }
            else{
                return State.PASS;
            }
        }
    }
    
    public boolean elfInDanger(MyElf e){
        int enemies = count(e, MyGame.getEnemyKillers());
        int mine = count(e, MyGame.getMyKillers());
        if (enemies > mine) 
        {
                return true;
        }
        else{
            return false;
            }
        }
    
    private int count(MyElf e, List<MyGameObject> killers) {
        return (int) killers.stream()
                .filter(k -> e.inRange(k, k.getMaxSpeed() + e.getMaxSpeed() + k.getAttackRange()))
                .count();
    } 
}