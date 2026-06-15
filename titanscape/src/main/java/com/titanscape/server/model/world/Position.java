package com.titanscape.server.model.world;

import java.util.Objects;

/**
 * Represents a tile position in the game world.
 */
public final class Position {

    private final int x;
    private final int y;
    private final int z;

    public Position(int x, int y) {
        this(x, y, 0);
    }

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getRegionX() {
        return (x >> 3) - 6;
    }

    public int getRegionY() {
        return (y >> 3) - 6;
    }

    public int getLocalX() {
        return x - 8 * getRegionX();
    }

    public int getLocalY() {
        return y - 8 * getRegionY();
    }

    public int distanceTo(Position other) {
        int dx = Math.abs(x - other.x);
        int dy = Math.abs(y - other.y);
        return Math.max(dx, dy);
    }

    public boolean isWithinDistance(Position other, int distance) {
        return distanceTo(other) <= distance && z == other.z;
    }

    public Position translate(int dx, int dy) {
        return new Position(x + dx, y + dy, z);
    }

    public Position translate(int dx, int dy, int dz) {
        return new Position(x + dx, y + dy, z + dz);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position other)) return false;
        return x == other.x && y == other.y && z == other.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return String.format("Position[%d, %d, %d]", x, y, z);
    }
}
