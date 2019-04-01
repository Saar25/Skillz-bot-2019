package bots.wrapper;

import elf_kingdom.*;
import bots.*;

import java.util.*;

public class MyElf extends MyGameObject {
    
    public static int ATTACK_RANGE;
    public static int ATTACK_MULTIPLIER;
    public static int MAX_HEALTH;
    public static int MAX_SPEED;
    
    public static void update(Game game) {
        MyElf.ATTACK_RANGE = game.elfAttackRange;
        MyElf.ATTACK_MULTIPLIER = game.elfAttackMultiplier;
        MyElf.MAX_HEALTH = game.elfMaxHealth;
        MyElf.MAX_SPEED = game.elfMaxSpeed;
    }
    
    public final Elf elf;
    
    public int attackMultiplier;
    public int attackRange;
    public boolean isBuilding;
    public int maxSpeed;
    public int spawnTurns;
    public int turnsToBuild;
    public int turnsToRevive;
    public final MyBuilding.Type currentlyBuilding;
    
    public List<MySpell> currentSpells = new ArrayList<>();

    private String action = "~~~~~nothing~~~~~";

    public MyElf(Elf elf) {
        super(elf);
        this.elf = elf;
        this.attackMultiplier = elf.attackMultiplier;
        this.attackRange = elf.attackRange;
        this.isBuilding = elf.isBuilding;
        this.maxSpeed = elf.maxSpeed;
        this.spawnTurns = elf.spawnTurns;
        this.turnsToBuild = elf.turnsToBuild;
        this.turnsToRevive = elf.turnsToRevive;
        this.currentlyBuilding = MyBuilding.Type.of(elf.currentlyBuilding);
        
        if (currentlyBuilding != null) {
            alreadyActed = true;
            action = "Building " + currentlyBuilding;
        }
        
        Arrays.asList(elf.currentSpells).stream()
                .map(s -> new MySpell(s, this))
                .forEach(currentSpells::add);
    }
    
    @Override
    public int getAttackRange() {
        return attackRange;
    }
    
    @Override
    public int getMaxSpeed() {
        return !isSpeedUp() ? maxSpeed : maxSpeed * MySpell.speedUpMultiplier;
    }
    
    @Override
    public int getAttackMultiplier() {
        return attackMultiplier;
    }
    
    @Override
    public List<? extends MyMapObject> getMineOfThisType() {
        return MyGame.getMyLivingElves();
    }
    
    public void request(int mana) {
        ManaManager.giveRequest(this, mana);
    }
    
    public ManaManager.Request getRequest() {
        return ManaManager.getRequest(this);
    }
    
    public boolean hasRequest(int mana) {
        ManaManager.Request request = ManaManager.getRequest(this);
        return request != null && request.mana >= mana;
    }
    
    /**
     * Makes the elf attack the given  target
     *
     * @return true if mission succeeded false otherwise
     */
    public boolean attack(MyGameObject target) {
        if (isInvisible() && !(target instanceof MyBuilding)) {
            return false;
        }
        if (target != null && !alreadyActed && inAttackRange(target)) {
            elf.attack(target.gameObject);
            action = "Attack " + target;
            alreadyActed = true;
            return true;
        }
        return false;
    }

    /**
     * Makes the elf move to the given destination
     *
     * @param destination the destination of the elf
     * @return true if mission succeeded false otherwise
     */
    public boolean moveTo(MyMapObject destination) {
        if (!alreadyActed && destination != null && destination.inMap()) {
            elf.moveTo(destination.getApiLocation());
            action = "Move To " + destination;
            if (!(destination instanceof Vector2)) {
                action += ", " + destination.getLocation();
            }
            alreadyActed = true;
            return true;
        }
        return false;
    }

    /**
     * Returns weather the elf can build portal
     *
     * @return true if can build portal false otherwise
     */
    public boolean canBuildPortal() {
        return canBuildPortal(ManaManager.getRequest(this));
    }
    
    public boolean canBuildPortal(ManaManager.Request request) {
        return elf.canBuildPortal() && ManaManager.canUse(MyGame.portalCost, request);
    }

    /**
     * Makes the elf build a portal in his location
     *
     * @return true if mission succeeded false otherwise
     */
    public boolean buildPortal() {
        return buildPortal(ManaManager.getRequest(this));
    }
    
    public boolean buildPortal(ManaManager.Request request) {
        if (!alreadyActed && canBuildPortal(request)) {
            if (request != null) request.use();
            elf.buildPortal();
            action = "Build Portal";
            alreadyActed = true;
            return true;
        }
        return false;
    }

    /**
     * Returns weather the elf can build ManaFountain
     *
     * @return true if can build ManaFountain false otherwise
     */
    public boolean canBuildManaFountain() {
        return canBuildManaFountain(ManaManager.getRequest(this));
    }
    
    public boolean canBuildManaFountain(ManaManager.Request request) {
        return elf.canBuildManaFountain() && ManaManager.canUse(MyGame.manaFountainCost, request);
    }

    /**
     * Makes the elf build a mana fountain in his location
     *
     * @return true if mission succeeded false otherwise
     */
    public boolean buildManaFountain() {
        return buildManaFountain(ManaManager.getRequest(this));
    }
    
    public boolean buildManaFountain(ManaManager.Request request) {
        if (!alreadyActed && canBuildManaFountain(request)) {
            if (request != null) request.use();
            elf.buildManaFountain();
            action = "Build Mana Fountain";
            alreadyActed = true;
            return true;
        }
        return false;
    }
    
    /**
     * Returns weather the elf is alive
     *
     * @return true if alive false otherwise
     */
    public boolean isAlive() {
        return elf.isAlive();
    }

    
    /**
     * Returns weather the elf can cast invisibility
     * 
     * @return true if can cast invisibility false otherwise
     */
    public boolean canCastInvisibility() {
        return canCastInvisibility(ManaManager.getRequest(this));
    }
    
    public boolean canCastInvisibility(ManaManager.Request request) {
        return elf.canCastInvisibility() && ManaManager.canUse(MyGame.invisibilityCost, request);
    }
    
    /**
     * Cast invisibility to the elf
     */
    public boolean castInvisibility() {
        return castInvisibility(ManaManager.getRequest(this));
    }
    
    public boolean castInvisibility(ManaManager.Request request) {
        if (!alreadyActed && canCastInvisibility(request)) {
            if (request != null) request.use();
            elf.castInvisibility();
            action = "Cast Invisibility";
            alreadyActed = true;
            return true;
        }
        return false;
    }
    
    /**
     * Returns weather the elf has invisibility spell
     * 
     * @return true if invisibley false otherwise
     */
    public boolean isInvisible() {
        for (MySpell spell : currentSpells) {
            if (spell.isInvisibility()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns the turns left for the elf to stay invisible
     * 
     * @return the amount of turns
     */
    public int turnsForInvisibility() {
        int max = 0;
        for (MySpell spell : currentSpells) {
            if (spell.isInvisibility()) {
                max = Math.max(max, spell.expirationTurns);
            }
        }
        return max;
    }
    
    /**
     * Returns weather the elf can cast speed up
     * 
     * @return true if can cast speed up false otherwise
     */
    public boolean canCastSpeedUp() {
        return canCastSpeedUp(ManaManager.getRequest(this));
    }
    
    public boolean canCastSpeedUp(ManaManager.Request request) {
        return elf.canCastSpeedUp() && ManaManager.canUse(MyGame.speedUpCost, request);
    }
    
    /**
     * Cast speed up to the elf
     */
    public boolean castSpeedUp() {
        return castSpeedUp(ManaManager.getRequest(this));
    }
    
    public boolean castSpeedUp(ManaManager.Request request) {
        if (!alreadyActed && canCastSpeedUp(request)) {
            if (request != null) request.use();
            elf.castSpeedUp();
            action = "Cast Speed Up";
            alreadyActed = true;
            return true;
        }
        return false;
    }
    
    /**
     * Returns weather the elf has invisibility spell
     * 
     * @return true if invisibley false otherwise
     */
    public boolean isSpeedUp() {
        for (MySpell spell : currentSpells) {
            if (spell.isSpeedUp()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Returns the turns left for the elf to stay speed up
     * 
     * @return the amount of turns
     */
    public int turnsForSpeedUp() {
        int max = 0;
        for (MySpell spell : currentSpells) {
            if (spell.isSpeedUp()) {
                max = Math.max(max, spell.expirationTurns);
            }
        }
        return max;
    }
    
    /**
     * Returns weather the elf has enough mana to build
     *
     * @param newBuildingCost the cost of the new building
     * @return true if enough mana to build false otherwise
     */
    public boolean enoughManaToBuild(int newBuildingCost) {
        return elf.enoughManaToBuild(newBuildingCost);
    }
    
    /**
     * Returns the action of the elf what the elf have done in this turn
     * 
     * @return the action of the elf as String
     */
    public String getAction() {
        return toString() + " " + action;
    }
    
    //
    // OUR METHODSSSSSSSSSS
    //
    
    /**
     * Move to the to the given destination while not entering the given circle
     * 
     * @param destination the destination
     * @param c           the center of the circle
     * @param radius      the radius of the circle
     * @return true if mission succeeded false otherwise
     */
    public boolean moveToAround(Vector2 destination, Vector2 c, double radius) {
        Vector2 realDest = location.towards(destination, getMaxSpeed());
        
        if (!realDest.inRange(c, radius)) {
            return moveTo(destination);
        } else {
            Vector2 dest;
            Vector2[] destinations = 
                Utils.getCirclesIntersections(getLocation(), getMaxSpeed(), c, radius);
            if (!destinations[0].inMap()) {
                dest = destinations[1];
            } else if (!destinations[1].inMap()) {
                dest = destinations[0];
            } else {
                dest = destination.getClosest(destinations);
            }
            if (!dest.inMap()) dest = destination;
            dest = dest.sub(location)
                    .normalize(maxSpeed)
                    .add(location);
            return moveTo(dest);
        }
    }
    
    /**
     * Move to the to the given destination while not entering the given circle
     * 
     * @param destination the destination
     * @param c           the center of the circle
     * @param radius      the radius of the circle
     * @return true if mission succeeded false otherwise
     */
    public boolean moveToAround(MyMapObject destination, MyMapObject c, double radius) {
        if (destination == null) return false;
        if (c == null) return moveTo(destination);
        return moveToAround(destination.getLocation(), c.getLocation(), radius);
    }
    
    /**
     * Move to the to the given destination while not entering the
     * attack zone of the enemy in this turn and the next
     * 
     * @param destination the destination
     * @param enemy       the enemy to avoid
     * @return true if mission succeeded false otherwise
     */
    public boolean moveToAround(MyMapObject destination, MyGameObject enemy) {
        if (destination == null) return false;
        if (enemy == null) return moveTo(destination);
        int radius = maxSpeed + enemy.getMaxSpeed() + enemy.getAttackRange();
        return moveToAround(destination, enemy, radius);
    }
    
    /**
     * Move to the to the given destination while not entering the
     * attack zone of the closest enemy killer
     * 
     * @param destination the destination
     * @return true if mission succeeded false otherwise
     */
    public boolean moveToAroundEnemy(MyMapObject destination) {
        return moveToAround(destination, getClosest(MyGame.getEnemyKillers()));
    }
    
    /**
     * Tries to attack the given object, if can't, moves to the given object
     * if can't do that either, does nothing
     * 
     * @param dbject the object to attack
     * @return true if acted false otherwise
     */
    public boolean attackOrMove(MyGameObject object) {
        return attack(object) || moveTo(object);
    }
    
    /**
     * Move to the opposite direction, away from the enemy
     */
    public void escape(MyMapObject enemy) {
        //
        //  DOESNT WORK PROPERLY
        //
        Vector2 destination = getLocation().towards(enemy.getLocation(), -getMaxSpeed());
        if (!destination.inMap()) {
            Vector2 border = enemy.getClosestBorder();
            int d = distance(border) + getMaxSpeed();
            Vector2 vector = enemy.getLocation().sub(border).normalize(d);
            Vector2 d1 = vector.perpendicularRight().add(border);
            Vector2 d2 = vector.perpendicularLeft().add(border);
            destination = getClosest(d1, d2);
            if (!d1.inMap()) {
                destination = d2;
            } else if (!d2.inMap()) {
                destination = d1;
            }
        }
        MyGame.debug(destination);
        moveTo(destination);
    }
    
    public int turnsForSafety() {
        MyGameObject enemy = getClosest(MyGame.getEnemyKillers());
        return enemy.turnsToAttack(this);
    }
}