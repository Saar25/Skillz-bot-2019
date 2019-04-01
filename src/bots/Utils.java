package bots;

import bots.wrapper.*;

import java.util.*;

public final class Utils {

    private Utils() {

    }

    /**
     * Returns the closest MapObject from the given MapObjects
     *
     * @param objects the objects to check
     * @param center  the center location to check
     * @param <T>     the type of the objects
     * @return the closest T, null if no objects
     */
     public static <T extends MyMapObject> T getClosest(MyMapObject center, T... objects) {
        return getClosest(Arrays.asList(objects), center);
    }
    
    public static <T extends MyMapObject> T getClosest(List<T> objects, MyMapObject center) {
        if (objects == null || center == null) return null;
        T closest = null;
        for (T object : objects) {
            if (object != null) {
                closest = object;
                break;
            }
        }
        if(closest == null) return null;
        int closestDistance = closest.getLocation().distance2(center);
        for (T obj : objects) {
            if (obj == null || !obj.inMap()) continue;
            int objDistance = obj.distance2(center);
            if (closestDistance > objDistance) {
                closestDistance = objDistance;
                closest = obj;
            }
        }
        return closest;
    }

    /**
     * Returns the furthest MapObject from the given MapObjects
     *
     * @param objects the objects to check
     * @param center  the center location to check
     * @param <T>     the type of the objects
     * @return the furthest T, null if no objects
     */
     public static <T extends MyMapObject> T getFurthest(MyMapObject center, T... objects) {
        return getFurthest(Arrays.asList(objects), center);
    }
    
    public static <T extends MyMapObject> T getFurthest(List<T> objects, MyMapObject center) {
        if (objects == null || center == null) return null;
        T furthest = null;
        for (T object : objects) {
            if (object != null) {
                furthest = object;
                break;
            }
        }
        if(furthest == null) return null;
        int furthestDistance = furthest.getLocation().distance2(center);
        for (T obj : objects) {
            if (obj == null || !obj.inMap()) continue;
            int objDistance = obj.distance2(center);
            if (furthestDistance > objDistance) {
                furthestDistance = objDistance;
                furthest = obj;
            }
        }
        return furthest;
    }

    /**
     * Returns all the objects in the given range from the given location
     *
     * @param objects the objects to check
     * @param center  the center location to check
     * @param range   the maximum range from the center
     * @param <T>     the type of the objects
     * @return the closest T, null if no objects
     */
    public static <T extends MyMapObject> List<T> getInRange(List<T> objects, MyMapObject center, int range) {
        List<T> inRange = new ArrayList<>();
        for (T object : objects) {
            if (object.inRange(center, range)) {
                inRange.add(object);
            }
        }
        return inRange;
    }
    
    /**
     * Returns all the objects that can attack the center
     * This method uses the objects attack range
     *
     * @param objects the objects to check
     * @param center  the center location to check
     * @param <T>     the type of the objects
     * @return the closest T, null if no objects
     */
    public static <T extends MyGameObject> List<T> getPotentialAttackers(List<T> objects, MyMapObject center) {
        return getInTurnsToAttack(center, objects, 0);
    }
    
    public static <T extends MyGameObject> List<T> getInTurnsToAttack(MyMapObject center, List<T> objects, int turns) {
        List<T> attackers = new ArrayList<>();
        if (objects == null || center == null) return attackers;
        for (T object : objects) if (object != null) {
            if (object.turnsToAttack(center) <= turns) {
                attackers.add(object);
            }
        }
        return attackers;
    }
    
    public static <T extends MyGameObject> List<T> getInTurnsToReach(MyMapObject center, List<T> objects, int turns) {
        List<T> attackers = new ArrayList<>();
        if (objects == null || center == null) return attackers;
        for (T object : objects) if (object != null) {
            if (object.turnsToReach(center) <= turns) {
                attackers.add(object);
            }
        }
        return attackers;
    }
    
    /**
     * Returns the given list but sorted from the closest to the farthest  
     * 
     * @return the sorted list
     */
    public static <T extends MyMapObject> List<T> sortByDistance(MyMapObject center, List<T> objects) {
        List<T> sorted = new ArrayList<>(objects);
        Collections.sort(sorted, (o1, o2) -> 
            o1.distance2(center) - o2.distance2(center));
        return sorted;
    }
    
    /**
     * Returns the given list but sorted from the lowest health to the highest  
     * 
     * @return the sorted list
     */
    public static <T extends MyGameObject> List<T> sortByHealth(List<T> objects) {
        List<T> sorted = new ArrayList<>(objects);
        Collections.sort(sorted, (o1, o2) -> 
            o1.currentHealth - o2.currentHealth);
        return sorted;
    }
    
    /**
     * Returns the objects that are between the given objects
     * 
     * @param a the first object
     * @param b the second object
     * @param objects the objects to check
     * @param <T> the type of the objects
     * @return the objects
     */
    public static <T extends MyMapObject> List<T> getBetween(MyMapObject a, MyMapObject b, List<T> objects) {
        int distance = a.distance(b) / 2;
        Vector2 middle = a.getLocation().towards(b.getLocation(), distance);
        return middle.getInRange(objects, distance + 10);
    }

    /**
     * Returns the center of the map
     * 
     * @return the center of the map
     */
    public static Vector2 getCenter() {
        return new Vector2(MyGame.rows / 2, MyGame.cols / 2);
    }
    
    /**
     * yo tako go over this. its suppose to calculate the 
     * distance it takes to lava middgets to reach the enemy portal
     * 
     * Yes that is good
     * We can also make something like :
     *      public static int potentialDamage(MapObject attaker, Location target) {
     *          ...
     *      }
     * and calculate how much time it would take for the 
     * attacker to reach the target, then we can know how
     * much damage the attacker can cause = health * damage
     */
    public static int calculateDeath(MyMapObject atk) {
        int health = MyLavaGiant.MAX_HEALTH;
        int dmg = MyLavaGiant.SUFFOCATION_PER_TURN;
        int step = MyLavaGiant.MAX_SPEED;
        int a = health / dmg;
        int deathDist = step * a;
        return deathDist;
    }
    
    /**
     * Returns the tangent intersections with the circle
     * 
     * @param p the point that the tangent start from
     * @param c the center of the circle
     * @param r the radius of the circle
     * @return array of 2 Vector2 contains the tangent intersections with the circle
     * @see http://www.ambrsoft.com/TrigoCalc/Circles2/CirclePoint/CirclePointDistance.htm
     */
     public static Vector2[] getTangentIntersections(Vector2 p, Vector2 c, double r) {
        double dx = p.x - c.x;
        double dy = p.y - c.y;
        double r2 = Math.pow(r, 2);
        double div = Math.pow(dx, 2) + Math.pow(dy, 2);
        double sqrt = Math.sqrt(div - r2);
        double pmx = + r * dy * sqrt / div;
        double pmy = - r * dx * sqrt / div;
        double x = r2 * dx / div + c.x;
        double y = r2 * dy / div + c.y;
        Vector2 a = new Vector2(x + pmx, y + pmy);
        Vector2 b = new Vector2(x - pmx, y - pmy);
        return new Vector2[] {a, b};
     }
     
    public static Vector2[] getCirclesIntersections(Vector2 a, double r1, Vector2 b, double r2) {
        if (a.distance2(b) > Math.pow(r1 + r2, 2)) {
            return new Vector2[0];
        } else if (a.distance2(b) > Math.pow(r1 + r2, 2)) {
            return new Vector2[] { b.sub(a).normalize(r1).add(a) };
        }
        double d = a.distance(b);
        double x = (r1 * r1 - r2 * r2 + d * d) / (2 * d);
        double h = Math.sqrt(r1 * r1 - x * x);
        Vector2 m = b.sub(a).mul(x / d).add(a);
        double x1 = m.x + h * (b.y - a.y) / d;
        double y1 = m.y - h * (b.x - a.x) / d;
        double x2 = m.x - h * (b.y - a.y) / d;
        double y2 = m.y + h * (b.x - a.x) / d;
        Vector2 v1 = new Vector2(x1, y1);
        Vector2 v2 = new Vector2(x2, y2);
        return new Vector2[] {v1, v2};
    }
     
    /**
    * Returns the distance of the point from the given line
    * 
    * @param p the point
    * @param m the line's slope
    * @param b the line's constant
    * @return the distance
    * @see https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
    */
    public static double pointDistanceFromLine(Vector2 p, double m, double b) {
        double sqrt = Math.sqrt(Math.pow(m, 2) + 1);
        double top = m * p.x - p.y + b;
        return Math.abs(top / sqrt);
    }
     
    /**
    * Clamps a number between two numbers
    * 
    * @param num the number
    * @param min the minimum value
    * @param max the maximum value
    * @return the clamped value
    */
    public static double clamp(double num, double min, double max) {
        num = num > max ? max : num;
        num = num < min ? min : num;
        return num;
    }
     
    /**
    * Calculates the sum of the strengths of the given game objects
    * 
    * @param list the game object
    * @return their strength
    */
    public static int getStrength(List<? extends MyGameObject> list) {
        int strength = 0;
        for (MyGameObject object : list) {
            strength += object.getStrength();
        }
        return strength;
    }
}