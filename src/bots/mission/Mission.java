package bots.mission;

import elf_kingdom.*;
import bots.wrapper.*;

public interface Mission<T> {
    
    enum State {
        FINISHED, // mission finished and acted
        CONTINUE, // mission acted but not finished
        PASS,     // mission could not act, skip to other mission
        ;
    }
    
    enum Priority {
        HIGH,    // mission is highly useful and should probably act
        USEFUL,  // mission is useful but might not be the best 
        USELESS, // mission is currenly useless
        ;
    }
    
    /**
     * Returns the name of the mission
     * 
     * @return the name of the mission
     */
     default String getName() {
        String[] split = getClass().getName().split("\\.");
        return split.length == 0 ? getClass().getName() : split[split.length - 1];
    }
    
    /**
     * Returns the priority level of the mission
     * return USEFUL by default
     * 
     * @param the elf check
     * @return the priority level
     */
    default Priority getPriority(T t) {
        return Priority.USEFUL;
    }
    
    /**
     * Perform the mission
     * 
     * @param the elf to act
     * @return the mission state
     */
    State act(T t);
    
}