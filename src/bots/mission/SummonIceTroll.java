package bots.mission;

import bots.wrapper.*;

/**
 * Kill close elf to the castle
 */
public class SummonIceTroll implements Mission<MyPortal> {
    
    @Override
    public State act(MyPortal p) {
        // Check if the portal is the one that will summon the closest ice troll to the enemy
        MyGameObject enemy = p.getClosest(MyGame.getEnemyCastleAttackers());
        if (enemy == null || !enemy.isClosest(p)) {
            return State.PASS;
        }
        
        int turnsToAttackPortal = enemy.turnsToAttack(p);
        int turnsToAttackCastle = enemy.turnsToAttack(MyGame.getMyCastle());
        if (turnsToAttackPortal <= MyIceTroll.SUMMONING_DURATION + 1 || turnsToAttackCastle <= MyIceTroll.SUMMONING_DURATION + 1) {
            if (p.summonIceTroll()) {
                return State.FINISHED;
            }
        }
        return State.PASS;
    }
    
}