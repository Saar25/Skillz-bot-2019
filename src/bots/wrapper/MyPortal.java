package bots.wrapper;

import elf_kingdom.*;
import bots.*;

import java.util.*;

public class MyPortal extends MyBuilding {
    
    public static int SIZE;
    public static int COST;
    public static int MAX_HEALTH;
    public static int BUILDING_DURATION;
    
    public static void update(Game game) {
        MyPortal.SIZE = game.portalSize;
        MyPortal.COST = game.portalCost;
        MyPortal.MAX_HEALTH = game.portalMaxHealth;
        MyPortal.BUILDING_DURATION = game.portalBuildingDuration;
    }
    
    public final Portal portal;
    
    public int cost;

    public String currentlySummoning;

    public boolean isSummoning;

    public int turnsToSummon;
    
    public MyPortal(Portal portal) {
        super(portal);
        this.portal = portal;
        this.cost = portal.cost;
        this.currentlySummoning = portal.currentlySummoning;
        this.isSummoning = portal.isSummoning;
        this.turnsToSummon = portal.turnsToSummon;
    }
    
    @Override
    public List<? extends MyMapObject> getMineOfThisType() {
        return MyGame.getMyPortals();
    }
    
    /*
    *
    * Mana managment
    *
     */
    
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
    
    /*
    *
    * Summoning
    *
     */
    
    public boolean canSummonIceTroll() {
        return canSummonIceTroll(ManaManager.getRequest(this));
    }

    public boolean canSummonLavaGiant() {
        return canSummonLavaGiant(ManaManager.getRequest(this));
    }

    public boolean canSummonTornado() {
        return canSummonTornado(ManaManager.getRequest(this));
    }
    
    public boolean canSummonIceTroll(ManaManager.Request request) {
        boolean mana = ManaManager.canUse(MyIceTroll.COST, request);
        return mana && portal.canSummonIceTroll();
    }

    public boolean canSummonLavaGiant(ManaManager.Request request) {
        boolean mana = ManaManager.canUse(MyLavaGiant.COST, request);
        return mana && portal.canSummonLavaGiant();
    }
    
    public boolean canSummonTornado(ManaManager.Request request) {
        boolean mana = ManaManager.canUse(MyTornado.COST, request);
        return mana && portal.canSummonTornado();
    }
    
    public boolean summonLavaGiant() {
        return summonLavaGiant(null);
    }
    
    public boolean summonIceTroll() {
        return summonIceTroll(null);
    }
    
    public boolean summonTornado() {
        return summonTornado(null);
    }
    
    public boolean summonLavaGiant(ManaManager.Request request) {
        if (canSummonLavaGiant(request)) {
            portal.summonLavaGiant();
            return true;
        }
        return false;
    }
    
    public boolean summonIceTroll(ManaManager.Request request) {
        if (canSummonIceTroll(request)) {
            portal.summonIceTroll();
            return true;
        }
        return false;
    }
    
    public boolean summonTornado(ManaManager.Request request) {
        if (canSummonTornado(request)) {
            portal.summonTornado();
            return true;
        }
        return false;
    }

}