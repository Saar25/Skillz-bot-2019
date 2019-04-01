package bots;

import bots.wrapper.*;

import java.util.*;

/**
 * Manage mana here
 * if you need to save some mana add request here
 */
public final class ManaManager {
    
    // All the requests created are added to this list
    private static final List<Request> requests = new LinkedList<>();
    
    // You can create requests and give them to a game object by his uniqueId 
    private static final Map<Integer, Request> savedRequests = new HashMap<>();
    
    private ManaManager() {
        
    }
    
    public static void update() {
        List<Request> toDelete = new ArrayList<>();
        requests.removeIf(r -> !r.valid);
        
        for (Map.Entry<Integer, Request> entry : savedRequests.entrySet()) {
            Request request = entry.getValue();
            int uniqueId = entry.getKey();
            boolean found = false;
            for (MyElf elf : MyGame.getMyLivingElves()) {
                if (elf.uniqueId == uniqueId) {
                    found = true;
                    break;
                }
            }
            for (MyPortal portal : MyGame.getMyPortals()) {
                if (portal.uniqueId == uniqueId) {
                    found = true;
                    break;
                }
            }
            if (!found || !request.isValid() || !requests.contains(request)) {
                toDelete.add(request);
            }
        }
        toDelete.forEach(ManaManager::delete);
        requests.forEach(Request::invalidate);
        
        MyGame.debug("Mana Requests: ");
        MyGame.debug(requests);
        MyGame.debug();
    }
    
    public static Request getRequest(MyGameObject mgo) {
        for (Map.Entry<Integer, Request> entry : savedRequests.entrySet()) {
            if (entry.getKey() == mgo.uniqueId) {
                return entry.getValue();
            }
        }
        return null;
    }
    
    public static void giveRequest(MyElf elf, int mana) {
        giveRequest0(elf, mana);
    }
    
    public static void giveRequest(MyPortal portal, int mana) {
        giveRequest0(portal, mana);
    }
    
    public static void giveRequest(MyElf elf, int mana, String consumer) {
        giveRequest0(elf, mana, consumer);
    }
    
    public static void giveRequest(MyPortal portal, int mana, String consumer) {
        giveRequest0(portal, mana, consumer);
    }
    
    private static void giveRequest0(MyGameObject gameObject, int mana) {
        String consumer = Thread.currentThread().getStackTrace()[3].getClassName();
        String[] parts = consumer.split("\\.");
        consumer = parts[parts.length - 1];
        giveRequest0(gameObject, mana, consumer);
    }
    
    private static void giveRequest0(MyGameObject gameObject, int mana, String consumer) {
        savedRequests.put(gameObject.uniqueId, request(mana, consumer));
    }
    
    public static Request request(int mana) {
        String consumer = Thread.currentThread().getStackTrace()[2].getClassName();
        String[] parts = consumer.split("\\.");
        consumer = parts[parts.length - 1];
        return request(mana, consumer);
    }
    
    public static Request request(int mana, String consumer) {
        Request request = new Request(mana, consumer);
        requests.add(request);
        return request;
    }
    
    public static boolean canUse(Request request) {
        int mana = MyGame.getMyself().mana;
        for (Request current : requests) {
            if (current == request) {
                return mana >= request.mana;
            }
            mana -= current.mana;
        }
        return mana >= request.mana;
    }
    
    public static boolean canUse(int needed, Request request) {
        if (request != null && needed <= request.mana) {
            return canUse(request);
        }
        int mana = MyGame.getMyself().mana;
        for (Request current : requests) {
            if (current == request) {
                needed -= request.mana;
            }
            mana -= current.mana;
        }
        return mana >= needed;
    }
    
    public static void use(Request request) {
        request.use();
    }
    
    public static boolean canUse(int request) {
        int mana = MyGame.getMyself().mana;
        for (Request current : requests) {
            mana -= current.mana;
        }
        return mana >= request;
    }
    
    private static void delete(Request request) {
        savedRequests.values().remove(request);
        requests.remove(request);
    }
    
        
    /**
     * Call this method if you no longer need this request
     * it will remove the request automaticly next turn
     * 
     * this method takes care of null argument
     */
    public static void invalidate(Request request) {
        if (request != null) {
            request.invalidate();
        }
    }
    
    public static class Request {
        
        private static int ids = 0;
        
        public final int mana;
        private final String consumer;
        private final int id = ids++;
        
        private boolean valid = true;
        
        public Request(int mana, String consumer) {
            this.mana = mana;
            this.consumer = consumer;
        }
        
        public boolean canUse() {
            return ManaManager.canUse(this);
        }
        
        public void use() {
            requests.remove(this);
        }
        
        /**
         * If you want to keep this request active you must validate
         * it at least once every turn or else it would be removed
         */
        public void validate() {
            this.valid = true;
        }
        
        /**
         * Call this method if you no longer need this request
         * it will remove the request automaticly next turn
         */
        public void invalidate() {
            this.valid = false;
        }
        
        /**
         * Returns whether the request is valid
         * 
         * @return true if request is valid false otherwise
         */
        public boolean isValid() {
            return requests.contains(this);
        }
        
        @Override
        public String toString() {
            return "[Request: id = " + id + "; consumer = " + consumer + "; mana = " + mana + "]";
        }
    }
    
}