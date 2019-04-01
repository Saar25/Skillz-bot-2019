package bots.wrapper;

import elf_kingdom.*;
import bots.*;

import java.util.*;

public class MyCastle extends MyBuilding {
    
    public static int SIZE;
    public static int MAX_HEALTH;
    
    public static void update(Game game) {
        MyCastle.SIZE = game.castleSize;
        MyCastle.MAX_HEALTH = game.castleMaxHealth;
    }
    
    private final Castle castle;
    
    public MyCastle(Castle castle) {
        super(castle);
        this.castle = castle;
    }
    
    @Override
    public List<? extends MyMapObject> getMineOfThisType() {
        return super.getMineOfThisType();
    }
    
}