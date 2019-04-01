package bots.wrapper;

import elf_kingdom.*;
import bots.*;

import java.util.*;

public class MyMapObject {
    
    public final MapObject mapObject;
    
    public Location apiLocation = null;
    public Vector2 location = null;
    
    public MyMapObject(MapObject mapObject) {
        this.mapObject = mapObject;
        this.apiLocation = mapObject.getLocation();
        this.location = vector2();
    }
    
    /**
     * Override this method to return all of our objects of this type
     */
    public List<? extends MyMapObject> getMineOfThisType() {
        return Arrays.asList(this);
    }
    
    private Vector2 vector2() {
        return this instanceof Vector2 ? (Vector2) this : new Vector2(apiLocation);
    }
    
    public int distance(MyMapObject other) {
        return (int) Math.sqrt(distance2(other));
    }
    
    public int distance2(MyMapObject other) {
        return location.distance2(other);
    }
    
    public boolean inRange(MyMapObject other, int range) {
        return distance2(other) <= range * range;
    }
    
    public boolean inRange(Vector2 other, int range) {
        return distance2(other) <= range * range;
    }
    
    public boolean isClosest(MyMapObject object) {
        return object.getClosest(getMineOfThisType()) == object;
    }
    
    public <T extends MyMapObject> T getClosest(T... objects) {
        return Utils.getClosest(this, objects);
    }
    
    public <T extends MyMapObject> T getClosest(List<T> objects) {
        return Utils.getClosest(objects, this);
    }
    
    public boolean isFurthest(MyMapObject object) {
        return object.getFurthest(getMineOfThisType()) == object;
    }
    
    public <T extends MyMapObject> T getFurthest(T... objects) {
        return Utils.getFurthest(this, objects);
    }
    
    public <T extends MyMapObject> T getFurthest(List<T> objects) {
        return Utils.getFurthest(objects, this);
    }
    
    public <T extends MyMapObject> List<T> getInRange(List<T> objects, int range) {
        return Utils.getInRange(objects, this, range);
    }
    
    public Vector2[] getBorders() {
        return new Vector2[] {
            new Vector2(0, location.y),
            new Vector2(location.x, 0),
            new Vector2(MyGame.rows, location.y),
            new Vector2(location.x, MyGame.cols)
        };
    }
    
    public Vector2 getClosestBorder() {
        return getClosest(getBorders());
    }
    
    public Location getApiLocation() {
        return this.apiLocation;
    }
    
    public Vector2 getLocation() {
        return this.location;
    }
    
    @Deprecated
    public Vector2 getLocationVec2() {
        return this.location;
    }
    
    public boolean inMap() {
        return mapObject.inMap();
    }
    
    /**
     * OVERRIDE THIS IN BUILDING CLASS
     */
    public int getSize() {
        return 0;
    }
}