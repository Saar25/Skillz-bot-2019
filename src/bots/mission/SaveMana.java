package bots.mission;

import bots.*;
import bots.wrapper.*;

/**
 * Save Mana for the elf
 */
public class SaveMana implements Mission<MyElf> {
    
    private final int mana;
    
    public SaveMana(int mana) {
        this.mana = mana;
    }
    
    @Override
    public State act(MyElf e) {
        ManaManager.Request request = ManaManager.getRequest(e);
        if (request == null) {
            ManaManager.giveRequest(e, mana, e.toString());
            request = ManaManager.getRequest(e);
        } else {
            request.validate();
        }
        return State.PASS;
    }
}