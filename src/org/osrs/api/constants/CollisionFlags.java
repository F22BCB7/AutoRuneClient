package org.osrs.api.constants;

public class CollisionFlags {
	//NORMAL/'STEP' FLAG BLOCKS FROM ACTUALLY ENTERING THE TILE
    //HIGH is an optional flag indicating it blocks projectiles
    //PATH if not set it lets you path through it. Sort of like an invisble wall, used for all traps and a few things like bank booths, border guards, ... lets you path 'through' the booth by clicking inside the bank, instead of running to the wall outside of the bank etc

    int NORTH_WEST_PILLAR = 0x1;
    int NORTH_WEST_PILLAR_HIGH = 0x200;
    int NORTH_WEST_PILLAR_PATH = 0x400000;

    /**
     * Tile has a wall on the north side
     */
    int NORTH_WALL = 0x2;

    /**
     * Tile has a wall on the north side that is high enough to block projectiles
     */
    int NORTH_WALL_HIGH = 0x400;

    /**
     * Tile has a wall on the north side that doesn't allow interactions through it
     */
    int NORTH_WALL_PATH = 0x800000;

    int NORTH_EAST_PILLAR = 0x4;
    int NORTH_EAST_PILLAR_HIGH = 0x800;
    int NORTH_EAST_PILLAR_PATH = 0x1000000;

    int EAST_WALL = 0b1000;
    int EAST_WALL_HIGH = 0b1000000000000;
    int EAST_WALL_PATH = 0b10000000000000000000000000;

    int SOUTH_EAST_PILLAR = 0b10000;
    int SOUTH_EAST_PILLAR_HIGH = 0b10000000000000;
    int SOUTH_EAST_PILLAR_PATH = 0b100000000000000000000000000;

    int SOUTH_WALL = 0b100000;
    int SOUTH_WALL_HIGH = 0b100000000000000;
    int SOUTH_WALL_PATH = 0b1000000000000000000000000000;

    int SOUTH_WEST_PILLAR = 0b1000000;
    int SOUTH_WEST_PILLAR_HIGH = 0b1000000000000000;
    int SOUTH_WEST_PILLAR_PATH = 0b10000000000000000000000000000;

    int WEST_WALL = 0b10000000;
    int WEST_WALL_HIGH = 0b10000000000000000;
    int WEST_WALL_PATH = 0b100000000000000000000000000000;

    /**
     * Tile is blocked by an object.
     */
    int OBSTACLE = 0b100000000;

    /**
     * Tile has an object high enough to block all projectiles.
     */
    int OBSTACLE_HIGH = 0b100000000000000000;

    /**
     * Tile is blocking interaction
     */
    int OBSTACLE_PATH = 0b1000000000000000000000000000000;

    /**
     * Tile is blocked due to a floor decoration object
     */
    int FLOOR_DECORATION_BLOCKED = 0b1000000000000000000;

    /**
     * If this flag is set you can interact (in the destination reachability check) with wall objects from the sides
     * <p>
     * Example with this flag
     *
     * <pre>
     * x x
     * x|x
     * x x
     * </pre>
     * <p>
     * Without this flag it would be
     *
     * <pre>
     * x|x
     * </pre>
     * <p>
     * NOTE: There is seems to be no code that actually sets this flag.
     */
    int INTERACT_SIDE_UNUSED = 0b10000000000000000000;

    /**
     * Tile is blocked from entering by render rule CLIPPED (0x1) (water...)
     */
    int TILE_BLOCKED = 0x200000;

    /**
     * Unused/reserved bit
     */
    //public static final int UNUSEDx100000 = 0x100000;

    /**
     * Unused/reserved bit
     */
    //public static final int UNUSEDx80000000 = 0x80000000;
    //Computed flags to make pathfinding more readable

    /**
     * Combination of flags to check if a tile can be entered at all
     */
    int BLOCK_ENTER = TILE_BLOCKED | FLOOR_DECORATION_BLOCKED | OBSTACLE;

    /**
     * Combination of flags to check there are no extra constraints that would block entering a north western tile
     */
    int BLOCK_ENTER_NORTH_WEST = SOUTH_EAST_PILLAR | SOUTH_WALL | EAST_WALL | BLOCK_ENTER;

    /**
     * Combination of flags to check there are no extra constraints that would block entering a northern tile
     */
    int BLOCK_ENTER_NORTH = SOUTH_WALL | BLOCK_ENTER;

    /**
     * Combination of flags to check there are no extra constraints that would block entering a north eastern tile
     */
    int BLOCK_ENTER_NORTH_EAST = SOUTH_WEST_PILLAR | SOUTH_WALL | WEST_WALL | BLOCK_ENTER;

    /**
     * Combination of flags to check there are no extra constraints that would block entering a eastern tile
     */
    int BLOCK_ENTER_EAST = WEST_WALL | BLOCK_ENTER;

    /**
     * Combination of flags to check there are no extra constraints that would block entering a south eastern tile
     */
    int BLOCK_ENTER_SOUTH_EAST = NORTH_WEST_PILLAR | NORTH_WALL | WEST_WALL | BLOCK_ENTER;

    /**
     * Combination of flags to check there are no extra constraints that would block entering a southern tile
     */
    int BLOCK_ENTER_SOUTH = NORTH_WALL | BLOCK_ENTER;

    /**
     * Combination of flags to check there are no extra constraints that would block entering a south western tile
     */
    int BLOCK_ENTER_SOUTH_WEST = NORTH_EAST_PILLAR | NORTH_WALL | EAST_WALL | BLOCK_ENTER;

    /**
     * Combination of flags to check there are no extra constraints that would block entering a western tile
     */
    int BLOCK_ENTER_WEST = EAST_WALL | BLOCK_ENTER;

    /**
     * Combination of flags to check if a projectile can fly over this tile at all
     */
    int BLOCK_PROJECTILE = /*TILE_BLOCKED | FLOOR_DECORATION_BLOCKED | */OBSTACLE_HIGH;

    /**
     * Combination of flags to check if a projectile can enter a north western tile
     */
    int BLOCK_PROJECTILE_NORTH_WEST = SOUTH_EAST_PILLAR_HIGH | SOUTH_WALL_HIGH | EAST_WALL_HIGH | BLOCK_PROJECTILE;
    int BLOCK_PROJECTILE_NORTH = SOUTH_WALL_HIGH | BLOCK_PROJECTILE;
    int BLOCK_PROJECTILE_NORTH_EAST = SOUTH_WEST_PILLAR_HIGH | SOUTH_WALL_HIGH | WEST_WALL_HIGH | BLOCK_PROJECTILE;
    int BLOCK_PROJECTILE_EAST = WEST_WALL_HIGH | BLOCK_PROJECTILE;
    int BLOCK_PROJECTILE_SOUTH_EAST = NORTH_WEST_PILLAR_HIGH | NORTH_WALL_HIGH | WEST_WALL_HIGH | BLOCK_PROJECTILE;
    int BLOCK_PROJECTILE_SOUTH = NORTH_WALL_HIGH | BLOCK_PROJECTILE;
    int BLOCK_PROJECTILE_SOUTH_WEST = NORTH_EAST_PILLAR_HIGH | NORTH_WALL_HIGH | EAST_WALL_HIGH | BLOCK_PROJECTILE;
    int BLOCK_PROJECTILE_WEST = EAST_WALL_HIGH | BLOCK_PROJECTILE;

    /**
     * Combination of flags to check if a tile allows interaction through it (bankers behind bank booths, door behind key doors, ...)
     */
    int BLOCK_PATH = TILE_BLOCKED | FLOOR_DECORATION_BLOCKED | OBSTACLE_PATH;

    int BLOCK_PATH_NORTH_WEST = SOUTH_EAST_PILLAR_PATH | SOUTH_WALL_PATH | EAST_WALL_PATH | BLOCK_PATH;
    int BLOCK_PATH_NORTH = SOUTH_WALL_PATH | BLOCK_PATH;
    int BLOCK_PATH_NORTH_EAST = SOUTH_WEST_PILLAR_PATH | SOUTH_WALL_PATH | WEST_WALL_PATH | BLOCK_PATH;
    int BLOCK_PATH_EAST = WEST_WALL_PATH | BLOCK_PATH;
    int BLOCK_PATH_SOUTH_EAST = NORTH_WEST_PILLAR_PATH | NORTH_WALL_PATH | WEST_WALL_PATH | BLOCK_PATH;
    int BLOCK_PATH_SOUT = NORTH_WALL_PATH | BLOCK_PATH;
    int BLOCK_PATH_SOUTH_WEST = NORTH_EAST_PILLAR_PATH | NORTH_WALL_PATH | EAST_WALL_PATH | BLOCK_PATH;
    int BLOCK_PATH_WEST = EAST_WALL_PATH | BLOCK_PATH;

    /**
     * Combination of flags to check there are no extra constraints that would block INTERACT_SIDE_UNUSED when interacting with a southern tile
     */
    int BLOCK_PATH_SOUTH_SIDE = NORTH_WALL | INTERACT_SIDE_UNUSED | BLOCK_ENTER;

    /**
     * Combination of flags to check there are no extra constraints that would block INTERACT_SIDE_UNUSED when interacting with a western tile
     */
    int BLOCK_PATH_WEST_SIDE = EAST_WALL | INTERACT_SIDE_UNUSED | BLOCK_ENTER;

    /**
     * Combination of flags to check there are no extra constraints that would block INTERACT_SIDE_UNUSED when interacting with a northern tile
     */
    int BLOCK_PATH_NORTH_SIDE = SOUTH_WALL | INTERACT_SIDE_UNUSED | BLOCK_ENTER;

    /**
     * Combination of flags to check there are no extra constraints that would block INTERACT_SIDE_UNUSED when interacting with a eastern tile
     */
    int BLOCK_PATH_EAST_SIDE = WEST_WALL | INTERACT_SIDE_UNUSED | BLOCK_ENTER;

    /**
     * Combination of flags to check there are no extra constraints that would block entering a northern tile for non corners of a big unit
     * For example any of these walls (or pillars) would block the unit, on the corners it doesn't need to check one wall
     *
     * <pre>
     *  |_|
     * x x x
     * x x x
     * x x x
     * </pre>
     */
    int BLOCK_PATH_NORTH_BIG = WEST_WALL_PATH | SOUTH_WEST_PILLAR_PATH | SOUTH_WALL_PATH | SOUTH_EAST_PILLAR_PATH | EAST_WALL_PATH | BLOCK_PATH;
    int BLOCK_PATH_EAST_BIG = NORTH_WALL_PATH | NORTH_WEST_PILLAR_PATH | WEST_WALL_PATH | SOUTH_WEST_PILLAR_PATH | SOUTH_WALL_PATH | BLOCK_PATH;
    int BLOCK_PATH_SOUTH_BIG = WEST_WALL_PATH | NORTH_WEST_PILLAR_PATH | NORTH_WALL_PATH | NORTH_EAST_PILLAR_PATH | EAST_WALL_PATH | BLOCK_PATH;
    int BLOCK_PATH_WEST_BIG = NORTH_WALL_PATH | NORTH_EAST_PILLAR_PATH | EAST_WALL_PATH | SOUTH_EAST_PILLAR_PATH | SOUTH_WALL_PATH | BLOCK_PATH;

    int BLOCK_ENTER_NORTH_BIG = WEST_WALL | SOUTH_WEST_PILLAR | SOUTH_WALL | SOUTH_EAST_PILLAR | EAST_WALL | BLOCK_ENTER;
    int BLOCK_ENTER_EAST_BIG = NORTH_WALL | NORTH_WEST_PILLAR | WEST_WALL | SOUTH_WEST_PILLAR | SOUTH_WALL | BLOCK_ENTER;
    int BLOCK_ENTER_SOUTH_BIG = WEST_WALL | NORTH_WEST_PILLAR | NORTH_WALL | NORTH_EAST_PILLAR | EAST_WALL | BLOCK_ENTER;
    int BLOCK_ENTER_WEST_BIG = NORTH_WALL | NORTH_EAST_PILLAR | EAST_WALL | SOUTH_EAST_PILLAR | SOUTH_WALL | BLOCK_ENTER;
}
