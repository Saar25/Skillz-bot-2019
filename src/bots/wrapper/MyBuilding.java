package bots.wrapper;

import elf_kingdom.*;
import bots.*;

import java.util.*;

public class MyBuilding extends MyGameObject {
    
    private final Building building;
    
    public int size;
    
    public MyBuilding(Building building) {
        super(building);
        this.building = building;
        this.size = building.size;
    }
    
    @Override
    public List<? extends MyMapObject> getMineOfThisType() {
        return MyGame.getMyBuildings();
    }
    
    @Override
    public int getSize() {
        return size;
    }
    
    public enum Type {
        ManaFountain,
        Portal,
        ;
        
        public static Type of(String s) {
            if (s == null) return null;
            switch (s) {
                case "ManaFountain":
                    return ManaFountain;
                case "Portal":
                    return Portal;
                default:
                    return null;
            }
        }   
    }
    
}