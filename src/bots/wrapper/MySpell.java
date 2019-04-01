package bots.wrapper;

import bots.*;
import elf_kingdom.*;

import java.util.*;

public class MySpell {
    
    public static int invisibilityCost;
    public static int invisibilityExpirationTurns;
    public static int speedUpCost;
    public static int speedUpMultiplier;
    public static int speedUpExpirationTurns;
    
    public static void update(Game game) {
        MySpell.speedUpCost = game.speedUpCost;
        MySpell.invisibilityCost = game.invisibilityCost;
        MySpell.speedUpMultiplier = game.speedUpMultiplier;
        MySpell.speedUpExpirationTurns = game.speedUpExpirationTurns;
        MySpell.invisibilityExpirationTurns = game.invisibilityExpirationTurns;
    }
    
    private final Spell spell;
    private final Type type;
    
    public final MyElf caster;
    public final int expirationTurns;
    
    public enum Type {
        INVISIBILITY,
        SPEEDUP;
    }
    
    public MySpell(Spell spell, MyElf caster) {
        this.spell = spell;
        this.type = spell instanceof Invisibility ? Type.INVISIBILITY
                  : spell instanceof SpeedUp      ? Type.SPEEDUP
                  : null;
        this.caster = caster;
        this.expirationTurns = spell.expirationTurns;
    }
    
    public boolean isInvisibility() {
        return type == Type.INVISIBILITY;
    }
    
    public boolean isSpeedUp() {
        return type == Type.SPEEDUP;
    }
    
}