package bots;

import elf_kingdom.*;
import bots.wrapper.*;

public class Vector2  extends MyMapObject {

    public final int x;
    public final int y;

    public Vector2(double x, double y) {
        super(new Location((int) x, (int) y));
        this.x = (int) x;
        this.y = (int) y;
    }

    public Vector2(Location location) {
        this(location.row, location.col);
    }

    public Vector2(double d) {
        this(d, d);
    }

    public Vector2() {
        this(0, 0);
    }

    public Location toLocation() {
        return new Location(x, y);
    }
    
    public boolean inMap() {
        return toLocation().inMap();
    }
    
    public Vector2 clamp(double minX, double maxX, double minY, double maxY) {
        double x = Utils.clamp(this.x, minX, maxX);
        double y = Utils.clamp(this.y, minY, maxY);
        return new Vector2(x, y);
    }
    
    public boolean isBetween(double minX, double minY, double maxX, double maxY) {
        return isBetween(x, minX, maxX) && isBetween(y, minY, maxY);
    }
    
    private boolean isBetween(double a, double min, double max) {
        return a >= min && a <= max;
    }
    
    //
    // DIRECTION
    //
    
    public Vector2 perpendicularLeft() {
        return new Vector2(-y, x);
    }
    
    public Vector2 perpendicularRight() {
        return new Vector2(y, -x);
    }
    
    public Vector2 rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = this.x * cos - this.y * sin;
        double y = this.x * sin + this.y * cos;
        return new Vector2(x, y);
    }
    
    public Vector2 towards(Vector2 other, double range) {
        return other.sub(this).normalize(range).add(this);
    }
    
    public double angle() {
        if (this.x == 0) {
            return Math.PI / 2;
        } else if (this.x > 0) {
            return Math.atan(this.y / this.x);
        } else {
            return 2 * Math.PI - Math.atan(this.y / this.x);
        }
    }

    //
    // DISTANCE AND LENGTH
    //

    public Vector2 normalize() {
        double length = length();
        double x = this.x * length;
        double y = this.y * length;
        return new Vector2(x, y);
    }

    public Vector2 normalize(double length) {
        length /= length();
        double x = this.x * length;
        double y = this.y * length;
        return new Vector2(x, y);
    }

    public int length() {
        return (int) Math.sqrt(x * x + y * y);
    }

    public int length2() {
        return x * x + y * y;
    }

    public int distance(double x, double y) {
        return (int) Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
    }

    public int distance(Vector2 vector2) {
        return (int) Math.sqrt(Math.pow(this.x - vector2.x, 2) + Math.pow(this.y - vector2.y, 2));
    }

    public int distance(Location location) {
        return (int) Math.sqrt(Math.pow(this.x - location.row, 2) + Math.pow(this.y - location.col, 2));
    }

    public int distance2(double x, double y) {
        return (int) (Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
    }

    public int distance2(Vector2 vector2) {
        return (int) (Math.pow(this.x - vector2.x, 2) + Math.pow(this.y - vector2.y, 2));
    }

    public int distance2(Location location) {
        return (int) (Math.pow(this.x - location.row, 2) + Math.pow(this.y - location.col, 2));
    }

    public int distance2(MyMapObject l) {
        return (int) (Math.pow(this.x - l.location.x, 2) + Math.pow(this.y - l.location.y, 2));
    }
    
    public boolean inRange(Vector2 vector2, double r) {
        return distance2(vector2) <= r * r;
    }

    //
    // ADDITION
    //

    public Vector2 add(double d) {
        return new Vector2(this.x + d, this.y + d);
    }

    public Vector2 add(double x, double y) {
        return new Vector2(this.x + x, this.y + y);
    }

    public Vector2 add(Vector2 vector2) {
        return new Vector2(this.x + vector2.x, this.y + vector2.y);
    }

    public Vector2 add(Location location) {
        return new Vector2(this.x + location.row, this.y + location.col);
    }

    //
    // SUBTRACTION
    //

    public Vector2 sub(double d) {
        return new Vector2(this.x - d, this.y - d);
    }

    public Vector2 sub(double x, double y) {
        return new Vector2(this.x - x, this.y - y);
    }

    public Vector2 sub(Vector2 vector2) {
        return new Vector2(this.x - vector2.x, this.y - vector2.y);
    }

    public Vector2 sub(Location location) {
        return new Vector2(this.x - location.row, this.y - location.col);
    }

    //
    // MULTIPLICATION
    //

    public Vector2 mul(double d) {
        return new Vector2(this.x * d, this.y * d);
    }

    public Vector2 mul(double x, double y) {
        return new Vector2(this.x * x, this.y * y);
    }

    public Vector2 mul(Vector2 vector2) {
        return new Vector2(x * vector2.x, y * vector2.y);
    }

    public Vector2 mul(Location location) {
        return new Vector2(x * location.row, y * location.col);
    }

    //
    // DIVISION
    //

    public Vector2 div(double d) {
        return new Vector2(this.x / d, this.y / d);
    }

    public Vector2 div(double x, double y) {
        return new Vector2(this.x / x, this.y / y);
    }

    public Vector2 div(Vector2 vector2) {
        return new Vector2(x / vector2.x, y / vector2.y);
    }

    public Vector2 div(Location location) {
        return new Vector2(x / location.row, y / location.col);
    }
    
    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

}