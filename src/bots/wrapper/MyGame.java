package bots.wrapper;

import bots.*;
import elf_kingdom.*;

import java.util.*;
import java.util.function.*;

public class MyGame {
    
    public static final boolean debugOn = true;
    
    public static Game game;
    
    // Mana properties
    public static int defaultManaPerTurn;

    // Ice troll properties
    public static int iceTrollAttackRange;
    public static int iceTrollAttackMultiplier;
    public static int iceTrollMaxHealth;
    public static int iceTrollMaxSpeed;
    public static int iceTrollSuffocationPerTurn;
    public static int iceTrollCost;

    // Lava giant properties
    public static int lavaGiantAttackRange;
    public static int lavaGiantAttackMultiplier;
    public static int lavaGiantMaxHealth;
    public static int lavaGiantMaxSpeed;
    public static int lavaGiantSuffocationPerTurn;
    public static int lavaGiantCost;

    // Tonado properties
    public static int tornadoAttackRange;
    public static int tornadoAttackMultiplier;
    public static int tornadoMaxHealth;
    public static int tornadoMaxSpeed;
    public static int tornadoSuffocationPerTurn;
    public static int tornadoCost;

    // Elf properties
    public static int elfAttackRange;
    public static int elfAttackMultiplier;
    public static int elfMaxHealth;
    public static int elfMaxSpeed;

    // Portal properties
    public static int portalMaxHealth;
    public static int portalSize;
    public static int portalCost;
    public static int portalBuildingDuration;

    // Mana fountain properties
    public static int manaFountainCost;
    public static int manaFountainBuildingDuration;
    public static int manaFountainManaPerTurn;
    public static int manaFountainMaxHealth;

    // Spells properties
    public static int invisibilityCost;
    public static int speedUpCost;
    public static int speedUpMultiplier; 
    public static int invisibilityExpirationTurns;
    public static int speedUpExpirationTurns;

    // Castle properties
    public static int castleMaxHealth;
    public static int castleSize;
    
    // Volcano properties
    public static int lavaGiantsFromVolcano;
    public static int volcanoInactiveTurns;
    public static int volcanoMaxHealth;
    public static int volcanoSize;
    
    // Map properties
    public static int rows;
    public static int cols;
    public static int turn;
    public static Vector2[] edges = new Vector2[4];
    
    // Our stuff
    private static final List<MyElf> myLivingElves = new ArrayList<>();
    private static final List<MyIceTroll> myIceTrolls = new ArrayList<>();
    private static final List<MyLavaGiant> myLavaGiants = new ArrayList<>();
    private static final List<MyTornado> myTornadoes = new ArrayList<>();
    
    private static final List<MyElf> enemyLivingElves = new ArrayList<>();
    private static final List<MyIceTroll> enemyIceTrolls = new ArrayList<>();
    private static final List<MyLavaGiant> enemyLavaGiants = new ArrayList<>();
    private static final List<MyTornado> enemyTornadoes = new ArrayList<>();
    
    private static final List<MyGameObject> myEntities = new ArrayList<>();
    private static final List<MyGameObject> myKillers = new ArrayList<>();
    private static final List<MyCreature>   myCreatures = new ArrayList<>();
    private static final List<MyGameObject> myCastleAttackers = new ArrayList<>();
    private static final List<MyGameObject> myDestroyers = new ArrayList<>();
    
    private static final List<MyGameObject> enemies = new ArrayList<>();
    private static final List<MyGameObject> enemyKillers = new ArrayList<>();
    private static final List<MyCreature>   enemyCreatures = new ArrayList<>();
    private static final List<MyGameObject> enemyCastleAttackers = new ArrayList<>();
    private static final List<MyGameObject> enemyDestroyers = new ArrayList<>();
    
    private static final List<MyPortal> myPortals = new ArrayList<>();
    private static final List<MyPortal> enemyPortals = new ArrayList<>();
    
    private static final List<MyManaFountain> myManaFountains = new ArrayList<>();
    private static final List<MyManaFountain> enemyManaFountains = new ArrayList<>();
    
    private static final List<MyBuilding> myBuildings = new ArrayList<>();
    private static final List<MyBuilding> enemyBuildings = new ArrayList<>();
    private static final List<MyBuilding> allBuildings = new ArrayList<>();
    
    private static final List<MyBuilding> myActiveBuildings = new ArrayList<>();
    private static final List<MyBuilding> enemyActiveBuildings = new ArrayList<>();
    private static final List<MyBuilding> allActiveBuildings = new ArrayList<>();
    
    private static final List<MyGameObject> allGameObjects = new ArrayList<>();
    
    private static MyCastle myCastle;
    private static MyCastle enemyCastle;
    private static MyVolcano volcano;
    
    /**
     * Initialize the class, call this method once on the start of the game
     * 
     * @param game the current game state
     */
    public static void init(Game game) {
        MyGame.game = game;
        edges[0] = new Vector2(0,0);
        edges[1] = new Vector2(MyGame.rows, 0);
        edges[2] = new Vector2(0, MyGame.cols);
        edges[3] = new Vector2(MyGame.rows, MyGame.cols);
    }

    /**
     * Update the class, call this method on the start of every turn
     * 
     * @param game the current game state
     */
    public static void update(Game game) {
        MyGame.game = game;
        
        MyGame.defaultManaPerTurn = game.defaultManaPerTurn;
        
        MyGame.iceTrollMaxSpeed = game.iceTrollMaxSpeed;
        MyGame.iceTrollMaxHealth = game.iceTrollMaxHealth;
        MyGame.iceTrollAttackRange = game.iceTrollAttackRange;
        MyGame.iceTrollAttackMultiplier = game.iceTrollAttackMultiplier;
        MyGame.iceTrollSuffocationPerTurn = game.iceTrollSuffocationPerTurn;
        MyGame.iceTrollCost = game.iceTrollCost;
        MyIceTroll.update(game);

        MyGame.lavaGiantMaxSpeed = game.lavaGiantMaxSpeed;
        MyGame.lavaGiantMaxHealth = game.lavaGiantMaxHealth;
        MyGame.lavaGiantAttackRange = game.lavaGiantAttackRange;
        MyGame.lavaGiantAttackMultiplier = game.lavaGiantAttackMultiplier;
        MyGame.lavaGiantSuffocationPerTurn = game.lavaGiantSuffocationPerTurn;
        MyGame.lavaGiantCost = game.lavaGiantCost;
        MyLavaGiant.update(game);

        MyGame.tornadoMaxSpeed = game.tornadoMaxSpeed;
        MyGame.tornadoMaxHealth = game.tornadoMaxHealth;
        MyGame.tornadoAttackRange = game.tornadoAttackRange;
        MyGame.tornadoAttackMultiplier = game.tornadoAttackMultiplier;
        MyGame.tornadoSuffocationPerTurn = game.tornadoSuffocationPerTurn;
        MyGame.tornadoCost = game.tornadoCost;
        MyTornado.update(game);

        MyGame.elfMaxSpeed = game.elfMaxSpeed;
        MyGame.elfMaxHealth = game.elfMaxHealth;
        MyGame.elfAttackRange = game.elfAttackRange;
        MyGame.elfAttackMultiplier = game.elfAttackMultiplier;
        MyElf.update(game);

        MyGame.portalMaxHealth = game.portalMaxHealth;
        MyGame.portalSize = game.portalSize;
        MyGame.portalCost = game.portalCost;
        MyGame.portalBuildingDuration = game.portalBuildingDuration;
        MyPortal.update(game);

        MyGame.manaFountainManaPerTurn = game.manaFountainManaPerTurn;
        MyGame.manaFountainBuildingDuration = game.manaFountainBuildingDuration;
        MyGame.manaFountainMaxHealth = game.manaFountainMaxHealth;
        MyGame.manaFountainCost = game.manaFountainCost;
        MyManaFountain.update(game);
        
        MyGame.invisibilityCost = game.invisibilityCost;
        MyGame.speedUpCost = game.speedUpCost;
        MyGame.speedUpMultiplier = game.speedUpMultiplier;
        MyGame.invisibilityExpirationTurns = game.invisibilityExpirationTurns;
        MyGame.speedUpExpirationTurns = game.speedUpExpirationTurns;
        MySpell.update(game);
        
        MyGame.castleSize = game.castleSize;
        MyGame.castleMaxHealth = game.castleMaxHealth;
        MyCastle.update(game);

        MyGame.cols = game.cols - 1;
        MyGame.rows = game.rows - 1;
        MyGame.turn = game.turn;
        
        // Elves
        toList(game.getMyLivingElves()   , myLivingElves   , MyElf::new);
        toList(game.getEnemyLivingElves(), enemyLivingElves, MyElf::new);
        
        // Creatures
        myCreatures.clear();
        enemyCreatures.clear();
        
        // Ice trolls
        toList(game.getMyIceTrolls()   , myIceTrolls   , MyIceTroll::new);
        toList(game.getEnemyIceTrolls(), enemyIceTrolls, MyIceTroll::new);
        enemyCreatures.addAll(enemyIceTrolls);
        myCreatures.addAll(myIceTrolls);
        
        // Lava giants
        toList(game.getMyLavaGiants()   , myLavaGiants   , MyLavaGiant::new);
        toList(game.getEnemyLavaGiants(), enemyLavaGiants, MyLavaGiant::new);
        enemyCreatures.addAll(enemyLavaGiants);
        myCreatures.addAll(myLavaGiants);
        
        // Tornadoes
        toList(game.getMyTornadoes()   , myTornadoes   , MyTornado::new);
        toList(game.getEnemyTornadoes(), enemyTornadoes, MyTornado::new);
        enemyCreatures.addAll(enemyTornadoes);
        myCreatures.addAll(myTornadoes);
        
        // Portals
        toList(game.getMyPortals()   , myPortals   , MyPortal::new);
        toList(game.getEnemyPortals(), enemyPortals, MyPortal::new);
        
        // Mana fountains
        toList(game.getMyManaFountains()   , myManaFountains   , MyManaFountain::new);
        toList(game.getEnemyManaFountains(), enemyManaFountains, MyManaFountain::new);
        
        // Castles
        myCastle = new MyCastle(game.getMyCastle());
        enemyCastle = new MyCastle(game.getEnemyCastle());
        
        // Volcano
        lavaGiantsFromVolcano = game.lavaGiantsFromVolcano;
        volcanoInactiveTurns = game.volcanoInactiveTurns;
        volcanoMaxHealth = game.volcanoMaxHealth;
        volcanoSize = game.volcanoSize;
        volcano = new MyVolcano(game.getVolcano());
        
        // Buildings
        myBuildings.clear();
        myBuildings.add(myCastle);
        myBuildings.addAll(myPortals);
        myBuildings.addAll(myManaFountains);
        enemyBuildings.clear();
        enemyBuildings.add(enemyCastle);
        enemyBuildings.addAll(enemyPortals);
        enemyBuildings.addAll(enemyManaFountains);
        allBuildings.clear();
        allBuildings.addAll(myBuildings);
        allBuildings.addAll(enemyBuildings);
        allBuildings.add(volcano);
        
        myActiveBuildings.clear();
        enemyActiveBuildings.clear();
        allActiveBuildings.clear();
        for (MyBuilding b : allBuildings) {
            if (!(b instanceof MyCastle) && b != volcano) {
                if (b.owner == getMyself()) {
                    myActiveBuildings.add(b);
                }
                else {
                    enemyActiveBuildings.add(b);
                }
                allActiveBuildings.add(b);
            }
        }
        
        // Others
        enemies.clear();
        enemies.addAll(enemyLivingElves);
        enemies.addAll(enemyLavaGiants);
        enemies.addAll(enemyIceTrolls);
        enemies.addAll(enemyTornadoes);
        
        enemyKillers.clear();
        enemyKillers.addAll(enemyLivingElves);
        enemyKillers.addAll(enemyIceTrolls);
        
        enemyCastleAttackers.clear();
        enemyCastleAttackers.addAll(enemyLivingElves);
        enemyCastleAttackers.addAll(enemyLavaGiants);
        
        enemyDestroyers.clear();
        enemyDestroyers.addAll(enemyTornadoes);
        enemyDestroyers.addAll(enemyLivingElves);
        
        myEntities.clear();
        myEntities.addAll(myLivingElves);
        myEntities.addAll(myLavaGiants);
        myEntities.addAll(myIceTrolls);
        myEntities.addAll(myTornadoes);
        
        myKillers.clear();
        myKillers.addAll(myLivingElves);
        myKillers.addAll(myIceTrolls);
        
        myCastleAttackers.clear();
        myCastleAttackers.addAll(myLivingElves);
        myCastleAttackers.addAll(myLavaGiants);
        
        myDestroyers.clear();
        myDestroyers.addAll(myTornadoes);
        myDestroyers.addAll(myLivingElves);
        
        ManaManager.update();
    }
    
    private static <T, R> void toList(R[] array, List<T> list, Function<R, T> convertor) {
        list.clear();
        Arrays.asList(array).stream()
            .map(r -> convertor.apply(r))
            .forEach(list::add);
        if (!list.isEmpty() && list.get(0) instanceof MyGameObject) {
            allGameObjects.addAll((List<? extends MyGameObject>) list);
        }
    }
    
    public static void debug(Object... objects) {
        if (debugOn) {
            game.debug(Arrays.asList(objects).toString());
        }
    }
    
    public static void debug(Object object) {
        if (debugOn) {
            game.debug(object != null ? object.toString() : "null");
        }
    }
    
    public static void debug() {
        if (debugOn) {
            game.debug("\n");
        }
    }
    
    public static boolean canBuildPortalAt(Vector2 location) {
        return canBuildAt(location, MyPortal.SIZE);
    }
    
    public static boolean canBuildManaFountainAt(Vector2 location) {
        return canBuildAt(location, MyManaFountain.SIZE);
    }
    
    public static boolean canBuildAt(Vector2 location, int size) {
        if (!location.isBetween(size, size, MyGame.rows - size, MyGame.cols - size)) {
            return false;
        }
        for (MyBuilding building : allBuildings) {
            if (building.inRange(location, building.size + size)) {
                return false;
            }
        }
        return true;
    }
    
    public static <T extends MyGameObject> T wrap(GameObject object) {
        if (object == null) return null;
        for (MyGameObject mgo : allGameObjects) {
            if (mgo.uniqueId == object.uniqueId) return (T) mgo;
        }
        return null;
    }
    
    public static int getMyMana() {return game.getMyMana();}
    public static int getEnemyMana() {return game.getEnemyMana();}
    
    public static Player getMyself() {return game.getMyself();}
    public static Player getEnemy()  {return game.getEnemy();}
    
    public static MyVolcano getVolcano() {return volcano;}
    
    public static MyCastle getMyCastle()     {return myCastle;}
    public static MyCastle getEnemyCastle()  {return enemyCastle;}
    
    public static List<MyPortal> getMyPortals()    {return myPortals;}
    public static List<MyPortal> getEnemyPortals() {return enemyPortals;}
    
    public static List<MyManaFountain> getMyManaFountains()     {return myManaFountains;}
    public static List<MyManaFountain> getEnemyManaFountains()  {return enemyManaFountains;}
    
    public static List<MyElf>       getMyLivingElves()    {return myLivingElves;}
    public static List<MyIceTroll>  getMyIceTrolls()      {return myIceTrolls;}
    public static List<MyLavaGiant> getMyLavaGiants()     {return myLavaGiants;}
    public static List<MyTornado>   getMyTornadoes()      {return myTornadoes;}
    public static List<MyElf>       getEnemyLivingElves() {return enemyLivingElves;}
    public static List<MyIceTroll>  getEnemyIceTrolls()   {return enemyIceTrolls;}
    public static List<MyLavaGiant> getEnemyLavaGiants()  {return enemyLavaGiants;}
    public static List<MyTornado>   getEnemyTornadoes()   {return enemyTornadoes;}
    
    public static List<MyGameObject> getEnemies()              {return enemies;}
    public static List<MyGameObject> getEnemyKillers()         {return enemyKillers;}
    public static List<MyCreature>   getEnemyCreatures()       {return enemyCreatures;}
    public static List<MyGameObject> getEnemyCastleAttackers() {return enemyCastleAttackers;}
    public static List<MyGameObject> getEnemyDestroyers()         {return enemyDestroyers;}
    
    public static List<MyGameObject> getMyEntities()        {return myEntities;}
    public static List<MyGameObject> getMyKillers()         {return myKillers;}
    public static List<MyCreature>   getMyCreatures()       {return myCreatures;}
    public static List<MyGameObject> getMyCastleAttackers() {return myCastleAttackers;}
    public static List<MyGameObject> getMyDestroyers()      {return myDestroyers;}
    
    public static List<MyBuilding> getAllBuildings()   {return allBuildings;}
    public static List<MyBuilding> getMyBuildings()    {return myBuildings;}
    public static List<MyBuilding> getEnemyBuildings() {return enemyBuildings;}
    
    public static List<MyBuilding> getAllActiveBuildings()   {return allActiveBuildings;}
    public static List<MyBuilding> getMyActiveBuildings()    {return myActiveBuildings;}
    public static List<MyBuilding> getEnemyActiveBuildings() {return enemyActiveBuildings;}
    
    
}