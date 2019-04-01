package bots;

import elf_kingdom.*;
import bots.wrapper.*;

public class MyBot implements SkillzBot {

    /**
     * Makes the bot run a single turn.
     *
     * @param game the current game state.
     */
    @Override
    public void doTurn(Game game) {
        if (game.turn == 1) {
            MyGame.init(game);
        }
        
        final long start = System.currentTimeMillis();
        
        printMessages();
        MyGame.update(game);
        handleElves();
        handlePortals();
        printActions();
        
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Turn took (in millis) : " + elapsed);
    }
    
    private void printMessages() {
        System.out.println("Da LuZeRz.");
        System.out.println("Somebody once told me the world is gonna roll me");
        System.out.println("Hamahapeha :}, Haxball forever");
        System.out.println();
        if (MyGame.debugOn) {
            return;
        }
        System.out.println("According to all known laws");
        System.out.println("of aviation,");
        System.out.println("there is no way a bee");
        System.out.println("should be able to fly.");
        System.out.println("Its wings are too small to get");
        System.out.println("its fat little body off the ground.");
        System.out.println("The bee, of course, flies anyway");
        System.out.println("because bees don't care");
        System.out.println("what humans think is impossible.");
        System.out.println("Yellow, black. Yellow, black.");
        System.out.println("Yellow, black. Yellow, black.");
        System.out.println("Ooh, black and yellow!");
        System.out.println("Let's shake it up a little.");
        System.out.println("Barry! Breakfast is ready!");
        System.out.println("Ooming!");
        System.out.println("Hang on a second.");
        System.out.println("Hello?");
        System.out.println("- Barry?");
        System.out.println("- Adam?");
        System.out.println("- Oan you believe this is happening?");
        System.out.println("- I can't. I'll pick you up.");
        
    }

    /**
     * Gives orders to my elves.
     */
    private void handleElves() {
        try {
            MyGame.debug("Elves Missions: ");
            for (MyElf elf : MyGame.getMyLivingElves()) {
                Strategy<MyElf> strategy = MissionManager.createElfMissions(elf);
                strategy.act(elf);
            } 
            MyGame.debug();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gives orders to my portals.
     */
    private void handlePortals() {
        try {
            MyGame.debug("Portals Missions: ");
            for (MyPortal portal : MyGame.getMyPortals()) {
                Strategy<MyPortal> strategy = MissionManager.createPortalMissions(portal);
                strategy.act(portal);
            }
            MyGame.debug();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Print the action of the elves... and the portals?
     */
    private void printActions() {
        MyGame.debug("Elves actions: ");
        for (MyElf elf : MyGame.getMyLivingElves()) {
            MyGame.debug(elf.getAction());
        }
    }
}
