package bots.wrapper;

import bots.*;
import bots.wrapper.*;
import elf_kingdom.*;

import java.util.*;

public class MyVolcano extends MyBuilding {
    
    private final Volcano volcano;
    
    public int damageByMe;
    public int damageByEnemy;
    public int maxTurnsToActive;
    
    public MyVolcano(Volcano volcano) {
        super(volcano);
        this.volcano = volcano;
        this.damageByMe = volcano.damageByMe;
        this.damageByEnemy = volcano.damageByEnemy;
        this.maxTurnsToActive = volcano.maxTurnsToActive;
    }
    
    public boolean isActive() {
        return volcano.isActive();
    }
    
}