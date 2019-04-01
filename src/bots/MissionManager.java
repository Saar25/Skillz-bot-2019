package bots;

import bots.*;
import bots.mission.*;
import bots.wrapper.*;

import java.util.*;

/**
 * This class is in charge of creaing the mission group 
 * for our elves and our portals
 */
public final class MissionManager {
    
    public static Strategy<MyElf> createElfMissions(MyElf e) {
        switch (e.id) {
            case 0:
                final MyCastle enemyCastle = MyGame.getEnemyCastle();
                
                if(MyGame.getEnemyManaFountains().size() > 0){
                    return flankMana();
                }
                    
                final int range = MyPortal.SIZE + MyCastle.SIZE + MyElf.MAX_SPEED;
                final MyPortal closestPortal = enemyCastle.getClosest(MyGame.getMyPortals());
                if (closestPortal == null || !closestPortal.inRange(enemyCastle, range)
                    && (MyGame.rows - MyCastle.SIZE * 2) / 2 >= MyPortal.SIZE) {
                    return flankAttack();
                }
                
                return attack(e);
                //return destroyEnemyBase();
            default:
                MyGameObject enemy = MyGame.getMyCastle()
                    .getClosest(MyGame.getEnemyCastleAttackers());
                int myFountains = MyGame.getMyManaFountains().size();
                int enFountains = MyGame.getEnemyManaFountains().size();
                if (enemy == null || enemy.turnsToAttack(MyGame.getMyCastle()) <= 5) {
                    //return defend();
                    return defend();
                } else if (myFountains <= enFountains && myFountains < 2) {
                    return manaBase(e);
                } else {
                    return general(e);
                }
        }
    }
    
    public static Strategy<MyPortal> createPortalMissions(MyPortal portal) {
        Strategy<MyPortal> strategy = new Strategy<>();
        
        // If really rich spam lava giants
        strategy.addMission(new SummonAttackWhenRich(), e -> MyGame.getMyLivingElves().size() == 0);
        
        // Summon ice trolls to defend the portal
        strategy.addMission(new SummonDefendPortal());
        
        // If we have a close portal to enemy castle spam lava giants
        strategy.addMission(new SummonFlankers());
    
        // Spawn ice trolls if enemy army is getting close
        strategy.addMission(new DefendCastle());
        
        strategy.addMission(new SummonTornado());
        
        strategy.addMission(new SummonIceTroll());
        
        strategy.addMission(new SummonClearOurBase());
        
        strategy.addMission(new SummonDistraction());
        
        //strategy.addMission(new SummonLavaGiant());
        
        return strategy;
    }
    
    
    //flank mana - should go fast to the enemy mana fountains. and rip em.
    private static Strategy<MyElf> flankMana() {
        Strategy<MyElf> strategy = new Strategy<>("FlankMana");
        
        final MyCastle enemyCastle = MyGame.getEnemyCastle();
        final MyPortal closestPortal = enemyCastle.getClosest(MyGame.getMyPortals());
        final int range = MyCastle.SIZE + MyPortal.SIZE + MyElf.MAX_SPEED;
        
        strategy.addMission(new AttackVolcanoSafely());
        strategy.addMission(new CastSpeedUpInDanger());
        strategy.addMission(new AttackIfSafe(e -> e.getClosest(MyGame.getEnemyManaFountains())));
        strategy.addMission(new GoToBehind(e -> e.getClosest(MyGame.getEnemyManaFountains())));
        //strategy.addMission(new Attack(e -> e.getClosest(MyGame.getEnemyManaFountains())));
        
      
        return strategy;
    }
    
    
    private static Strategy<MyElf> flankAttack() {
        Strategy<MyElf> strategy = new Strategy<>("FlankAttack");
        
        final MyCastle enemyCastle = MyGame.getEnemyCastle();
        final MyPortal closestPortal = enemyCastle.getClosest(MyGame.getMyPortals());
        final int range = MyCastle.SIZE + MyPortal.SIZE + MyElf.MAX_SPEED;
        
        //strategy.addMission(new CastInvisibilityInDanger());
        strategy.addMission(new CastSpeedUp(), e -> !e.inRange(MyGame.getEnemyCastle(), range * 2));
        
        strategy.addMission(new SaveMana(MyPortal.COST));
        strategy.addMission(new AttackWeakElves());
        strategy.addMission(new AttackVolcanoSafely());

        strategy.addMission(new GoToFlank());
        strategy.addMission(new BuildPortal());
        
        strategy.addMission(new TryDestroyBuilding());
        
        strategy.addMission(new Attack.OrMove(e -> e.getClosest(MyGame.getEnemyLivingElves())));
        strategy.addMission(new AttackIfCan());
        strategy.addMission(new AttackCastle());
        
        return strategy;
    }
    
    private static Strategy<MyElf> general(MyElf elf) {
        Strategy<MyElf> strategy = new Strategy<>("General");
        
        strategy.addMission(new CastInvisibilityInDanger());
        
        if (MyElf.MAX_SPEED < 50 && SugWall.isNeeded()) {
            strategy.addMission(new SugWall.Build());
            strategy.addMission(new SpeedUpIfSlow(50));
            strategy.addMission(new SugWall.GoTo());
        } else {
            strategy.addMission(new SugWall());
        }
        
        strategy.addMission(new EscapeIfWeaker());
        if (MyGame.getMyPortals().size() < 2){
            strategy.addMission(new BuildPortal());
            strategy.addMission(new GotoCloseEmptyTowards(MyGame::getEnemyCastle, MyPortal.SIZE));
        }

        strategy.addMission(new Attack.Closest(MyGame::getEnemyLivingElves));
        strategy.addMission(new BuildAmazingPortal());
        //strategy.addMission(new BuildManaSafe());
        strategy.addMission(new BuildBaitPortal());
        strategy.addMission(new TryDestroyBuilding());
        strategy.addMission(new AttackIfCan());
        strategy.addMission(new BuildPortal());
        strategy.addMission(new AttackVolcanoSafely());
        strategy.addMission(new KillCloseElves(1500));
        strategy.addMission(new DestroyClosePortal(2000));
        
        strategy.addMission(new GoNearEnemyCastle());
        strategy.addMission(new Attack.Closest(MyGame::getEnemyLivingElves));
        strategy.addMission(new DestroyPortals());
        strategy.addMission(new AttackElves());
        strategy.addMission(new AttackCastle());
        
        return strategy;
    }
    
    /**
     * Defend our castle, dont even try to attack
     */
    private static Strategy<MyElf> defend() {
        Strategy<MyElf> strategy = new Strategy<>("Defend");
        
        
        strategy.addMission(new TryAttackElves());
        strategy.addMission(new SugWall());
        strategy.addMission(new DestroyPortals());
        strategy.addMission(new TryDestroyBuilding());
        strategy.addMission(new AttackIfCan());
        strategy.addMission(new AttackVolcanoSafely());

        
        return strategy;
    }
    
    /**
     * Only attack now, defence is useless
     */
    private static Strategy<MyElf> attack(MyElf elf) {
        Strategy<MyElf> strategy = new Strategy<>("Attack");
        
        //strategy.addMission(new SugWall());
        strategy.addMission(new Attack.Closest(MyGame::getEnemyLivingElves));
        strategy.addMission(new Attack.OrMove(e -> e.getClosest(MyGame.getEnemyLivingElves())));
        strategy.addMission(new CastInvisibilityInDanger());
        strategy.addMission(new EscapeIfWeaker());
        
        //strategy.addMission(new AttackIfCan());
        strategy.addMission(new BuildAmazingPortal());
        strategy.addMission(new BuildPortalInDanger());
        
        strategy.addMission(new TryAttackElves());
        //strategy.addMission(new TryDestroyBuilding());

        //strategy.addMission(new SaveMana(MyPortal.COST));
        strategy.addMission(new AttackIfCan());
        strategy.addMission(new GotoCloseEmpty(MyGame::getEnemyCastle, MyPortal.SIZE));
        strategy.addMission(new BuildPortal());
        
        strategy.addMission(new DestroyManaFountain());
        strategy.addMission(new AttackCastle());
        
        return strategy;
    }
    
    private static Strategy<MyElf> campAtEnemy() {
        Strategy<MyElf> strategy = new Strategy<>("CampAtEnemy");
        
        strategy.addMission(new Attack(MyGame::getEnemyCastle));
        strategy.addMission(new CastInvisibilityInDanger());
        strategy.addMission(new CastSpeedUp());
        strategy.addMission(new GoTo(MyGame::getEnemyCastle));
        return strategy;
    }
    
    private static Strategy<MyElf> portalRoad() {
        Strategy<MyElf> strategy = new Strategy<>("PortalRoad");
        
        strategy.addMission(new EscapeIfWeaker());
        strategy.addMission(new SaveMana(MyPortal.COST));
        strategy.addMission(new BuildPortal());
        strategy.addMission(new GotoCloseEmptyTowards(MyGame::getEnemyCastle, MyPortal.SIZE));
        strategy.addMission(new AttackVolcanoSafely());

        return strategy;
    }
    
    private static Strategy<MyElf> manaBase(MyElf e) {
        Strategy<MyElf> strategy = new Strategy<>("ManaBase");
        
        strategy.addMission(new SugWall());
        
        strategy.addMission(new SaveMana(MyManaFountain.COST));
        strategy.addMission(new GotoCloseEmpty(MyGame::getMyCastle, MyManaFountain.SIZE));
        strategy.addMission(new Attack.Closest(MyGame::getEnemyDestroyers));
        strategy.addMission(new BuildManaFountain());
        if (MyGame.getMyPortals().size() < 2){
            strategy.addMission(new BuildPortal());
            strategy.addMission(new GotoCloseEmptyTowards(MyGame::getEnemyCastle, MyPortal.SIZE));
        }

        return strategy;
    }
    
    private static Strategy<MyElf> destroyEnemyBase() {
        Strategy<MyElf> strategy = new Strategy<>("DestroyEnemyMana");
        
        strategy.addMission(new CastInvisibilityInDanger());
        strategy.addMission(new EscapeIfWeaker());
        strategy.addMission(new AttackVolcanoSafely());

        strategy.addMission(new GoToBehind(e -> e.getClosest(MyGame.getEnemyManaFountains())));
        strategy.addMission(new Attack(e -> e.getClosest(MyGame.getEnemyManaFountains())));
        
        strategy.addMission(new GoToBehind(e -> e.getClosest(MyGame.getEnemyPortals())));
        strategy.addMission(new Attack(e -> e.getClosest(MyGame.getEnemyPortals())));
        
        strategy.addMission(new GoToBehind(e -> MyGame.getEnemyCastle()));
        strategy.addMission(new Attack(e -> MyGame.getEnemyCastle()));
        //strategy.addMission(new AttackVolcanoSafely());

        return strategy;
    }
    
}