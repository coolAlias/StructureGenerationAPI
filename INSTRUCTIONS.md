THE STRUCTURE GENERATION API
============================
Include the structuregen.api folder directly into the project's source folder or as a
separate project that is included in the build path. For best results, files located
in api.util should NOT be modified, but modifications to the StructureGenerator class
will be needed if using custom hooks.

There can be multiple StructureGenerator classes, allowing for the same hook id to be
used differently depending on which generator is referenced; this prevents potential
id conflicts from multiple mods using the api.

See below for complete instructions or check out the tutorial series to get started.

GENERATING CUSTOM STRUCTURES
============================
In order to use the API, a class extending StructureGeneratorBase must first be created. The auto-
generated methods are sufficient for most cases.

The examples used in the following steps assume the use of the StructureGenerator class included
with the API, but the steps will be the same for any class extending StructureGeneratorBase.

Step 1: Choose a method from which to generate the structure
    
    "onItemUse", "onBlockActivated", and classes extending IWorldGenerator are all suitable choices, but
    structures can be generated from any class or method with access to a World object and the server.

Step 2: Create a new StructureGenerator object within the chosen method
    
    Creating a new instance is only necessary for multi-player compatibility; otherwise, a static
    instance of the StructureGenerator could be created once and used for all generation.
    
    StructureGeneratorBase gen = new StructureGenerator();

Step 3: Set the Structure to be generated
    
    An object of the Structure class (see below) can be set directly or with optional rotation:
        "setStructure(Structure)"
        "setStructureWithRotation(Structure, int)" - sets and rotates structure off it's default facing
    
    Alternatively, int[][][][] blockArray(s) (see below) can be added directly to the Generator;
    note that with this approach, the structure's facing must be set manually.
        "addBlockArray(int[][][][])" - adds a blockArray to current list of arrays
        "setBlockArray(int[][][][])" - overwrites current list with provided blockArray
        "addBlockArrayList(List<int[][][][]>)" - adds a list of blockArrays to current list
        "setBlockArrayList(List<int[][][][]>)" - overwrites current list with provided list
   
    gen.setStructure(structure); // See below for how to set up a Structure class object

Step 4: (Optional) Set the structure's facing and rotation
    
    FACING
    If the Structure class was used, a facing should have already been set; otherwise, a facing should
    be set with the method "setStructureFacing(StructureGeneratorBase.DIRECTION)":
    
        gen.setStructureFacing(StructureGeneratorBase.NORTH);
 
    Valid DIRECTION values are NORTH, SOUTH, EAST, WEST, enumerated in StructureGeneratorBase. If no
    facing is set, EAST will be used as the default value.
    
    See below for more information on choosing a default facing for the structure.
    
    ROTATION
    Additionally, a structure can be rotated manually off of it's default facing. Each rotation rotates the
    structure 90 degrees clockwise, so rotating the above structure once will cause it to face EAST. This
    is useful when generating the same structure multiple times with different facings each time, but where
    the default facing needs to be maintained for future use.
    
        gen.rotateStructureFacing(); // rotates the structure 90 degrees clockwise once only
        gen.rotateStructureFacing(3); // rotates the structure 3 more times, 90 degrees each time

Step 5: (Optional) Account for the player's facing
    
    If it's important to have the structure's front side facing the player, then the player's facing must be
    set in the StructureGenerator. This requires access to an EntityPlayer object, of course.
    
        gen.setPlayerFacing(par2EntityPlayer); // using par2EntityPlayer from the method "onItemUse"
    
    Manual rotations will now cause the structure to rotate with respect to the player as well as with respect
    to the structure's default facing.

Step 6: (Optional) Set structure's offset
    
    Usually while generating a structure, the x/y/z coordinates at which to generate it are retrieved from the
    method parameters from which it will generate. It's position can be changed by setting x/y/z offsets.
    
    If the structure is generating in relation to a player, then the following methods are recommended:
        "setDefaultOffset()" - sets offset so the structure generates completely in front of the player
        "setDefaultOffset(int x, int y, int z)" - as above, but with additional offsets added in
    
    Additionally, the offsets can be set with complete manual control, but this is generally not recommended:
        "setOffset(int offX, int offY, int offZ)"
    
    Increasing offset X moves the structure further away from the player, while decreasing offset X moves it
    closer to or even behind the player. Increasing offset Z moves the structure to the player's right, while
    decreasing Z moves it to the player's right. Offset Y moves up and down, respectively.
    
    During world generation, the player is not a factor and setting offsets should not be necessary unless
    using the LinkedStructureGenerator (see below).
    
Step 7: Generate the structure
    
    The final step is to call the generate method of the StructureGenerator class.
    
        "generate(World world, Random random, int x, int y, int z)"
    
    gen.generate(par3World, par3World.rand, par4, par5, par6); // using the default parameters from "onItemUse"

LINKED STRUCTURE GENERATION
===========================

(instructions pending)

THE STRUCTURE CLASS
===================
The Structure class is a simple utility class that stores all information needed to properly
generate a structure. Its use is not required, but convenient for several reasons:

- The structure can be named, providing a means to display what structure is selected
- Set a specific offset for the structure independent of any other offset
- Easily switch between entire structures by using a List<Structure>

To setup a Structure object, see the following steps:

Step 1: Declare a new Structure object, including the Structure's name, if applicable.
     
    Structure structure = new Structure("Hut");
    Structure unnamed = new Structure();
        
    NOTE: A Structure's name is final, so if it has a name it must be set from the constructor.

Step 2: Add all necessary blockArrays (see below) to the structure
        
    An example for a structure with only a single layer:
    structure.addBlockArray(StructureArrays.blockArrayNPCHut);
        
    An example for a structure with multiple layers, some of which are the same:
        structure.addBlockArray(StructureArrays.blockArrayOffsetTest1);
        structure.addBlockArray(StructureArrays.blockArrayOffsetTest2);
        structure.addBlockArray(StructureArrays.blockArrayOffsetTest2);
        structure.addBlockArray(StructureArrays.blockArrayOffsetTest2);
        structure.addBlockArray(StructureArrays.blockArrayOffsetTest1);
        
    If a List<int[][][][]> of block arrays is available, the entire list can be added:
        structure.addBlockArrayList(anotherStructure.blockArrayList());
        
    This could be used to easily copy a structure or add a duplicate multi-layer.
        
    If the structure has more than one layer, they should be added from the bottom up, so first
    the base, then the layer on top of the base, and so on until the final topmost layer.
            
Step 3: Set the default direction the structure faces:
    
    structure.setFacing(StructureGeneratorBase.EAST);

Step 4: (Optional) Set structure-specific offsets
    
    See "Generating Custom Structures" Step 6 for more information on how setting offsets affects
    a structure's generated position.
    
    This structure has a buffer layer that should only generate if there is empty space underneath,
    so the default offset is set to lower the Y position by one so the structure still generates at
    ground level.
        
        structure.setStructureOffset(0, -1, 0);

Step 5: (Optional) Add the completed structure to a List<Structure> for easy access
        
    /** A good place for this list would be in the StructureGenerator class */
    List<Structure> structures = new LinkedList();
    
    structures.add(structure); // adds the "Hut" that was set up above

To generate a completed structure, see "Generating Custom Structures".

SETTING UP A STRUCTURE'S BLOCK ARRAY
====================================
TIP: Using MCEdit first and converting the 'generate' methods from that to a block
array is much easier than building one from scratch. This has the added benefit of
providing all the correct metadata values for block facing - it is therefore highly
recommended to use MCEdit first and use it as a reference for building the array. 

STRUCTURE FACING

Structure facing determines which 'side' of the array faces the player when generated.

Facing      Array Setup
NORTH       Front: min z, Back: max z, Left (east): max x, Right (west): min x
SOUTH       Front: max z, Back: min z, Left (west): min z, Right (east): max x
EAST        Front: max x, Back: min x, Left (south): max z, Right (north): min z
WEST        Front: min x, Back: max x, Left (north): min z, Right (south): max z

Choose a default structure facing that is easy for you to visualize while making the array;
you can always rotate the structure later to get any facing you desire. I recommend building
your a simple structure array first without an orientation in mind, then after you generate
it change the default facing to match your personal style. Now you know how you visualize
arrays and can probably use the same default facing for all your future structures (if you
build them from scratch, that is).

Personally, I like WEST because then the front of the structure is the first x array, which
is the topmost line, and the z array runs from left to right if you are looking in the same
direction your structure faces (so if you look directly at your structure, z=0 will actually
be on your right side, even though it's the left side of the structure).

Cardinal directions are determined by x and z regardless of structure facing:
NORTH = Min Z, SOUTH = Max Z, EAST = Max X, WEST = Min X

Left/right values given above are based on the player looking at the front face of the
structure in its default orientation.

All directional blocks should be set in relation to your structure's default facing, although
this is only an aid for you in setting the correct value. Once you have the correct value, you
can give your structure any default facing and the metadata will still be correct.

THE BLOCK ARRAY

The first array [] stores y values, so we're building a structure one flat layer at
a time because I personally feel it's easier to visualize that way.

The second array [] stores x, the third stores z, and the fourth individual block data.

Each block uses the following variables:
{blockID, metadata, customData1, customData2}

Note that you only need to fill this array up to the point you need, so if your block does
not use metadata, you could simply use {blockID} for the array, instead of {blockID,0,0,0};
conversely, if your block is directional, you should include a metadata value. If no meta
value is found, directional blocks default to a value of 0 which may or may not be correct
for the type of block you are setting.

{blockID}
If left blank, the structure generator will skip the coordinate of the missing block.

If you want any part of your structure to 'soft spawn', use the negative value of the
block ID. This will prevent your block from spawning in the world if another block
already exists at that location, allowing your structure to more naturally fit in with
the environment. To set an air block that doesn't overwrite pre-existing blocks, use
the value SET_NO_BLOCK instead of a block ID or simply leave the field blank.

A quick example: Block.cobblestone.blockID will only spawn a cobblestone block if its
spawn location is air or occupied by a block such as grass that doesn't block movement.

Values above 4095 are used to trigger a hook that allows custom manipulation of the block
via the method onCustomBlockAdded in WorldGenStructure. See below for details.

NOTE: If your mod has custom blocks, you can use YourMod.yourBlock.blockID to spawn it
in the structure. If your custom block is directional, you MUST register it with the
method "addCustomBlockRotation(int blockID, ROTATION type)", where ROTATION is an
enumerated type defining vanilla rotation styles. See the method for further details.

{metadata}
Stores the block's metadata. If metadata determines orientation, see below for details on
setting the value correctly.

{custom data 1}
This value is passed to getRealBlockID method where it can be used to subtype custom blocks, if desired.
Generally used just like {custom data 2}.
Also used to store color value for cloth blocks.

{custom data 2}
Passed along with customData1 to the onCustomBlockAdded method. One could use this to subtype a block
ID, such as CustomHooks.CUSTOM_CHEST with subtypes VILLAGE_BLACKSMITH, VILLAGE_LIBRARY, etc., to
set the number of random items to generate, to set villager type to spawn... you get
the idea. See below for more information on how to use custom data.

!!!IMPORTANT!!!
The first element [0] of each y / x array MUST contain an array that defines the maximum
area of your structure, such that blockArray[0] contains the max number of x arrays at
any given point and blockArray[0][0] contains the max number of z arrays at any given
point. After this, you can have fewer as needed.

This is because I use the first index to determine the structure's center, saving myself
the trouble and processing time of iterating through the entire structure array to find
the max length and width. If you don't follow this rule, be prepared for your structure
to generate off-center and possible on top of you.

As an example, consider a tower whose base is 6x6, but the roof is 8x8. The first array
stored at blockArray[0] must contain 8 elements (i.e. 8 arrays for the x axis) and the
first array stored at blockArray[0][0] must also contain 8 elements (i.e 8 arrays for the
z axis). blockArray[1]-[maxHeight] can all contain as few as 0 elements, but no more than
8; same goes for blockArray[n][1]-[n][maxWidth].

BLOCK ARRAY TEMPLATE
====================
/**
 * Here is an EMPTY template. Copy and paste as needed.
 * See 'StructureArrays.java' for samples.
 */
public static final int[][][][] blockArrayTemplate =
{   // Brackets to encompass entire array
    {   // Brackets for y values
        {   // Brackets for x values within a single y bracket
            // Brackets for z values within a single x bracket
            {}, // z = 0,
            {}, // z = 1,
            {}  // z = 2 etc. for as many z values as you need
            // Each z bracket contains up to four integers:
            // {blockID, metadata, customData1, customData2}
        }
    }
};

GENERATING LARGE STRUCTURES: USING MULTIPLE ARRAYS
==================================================
If you receive the error message "The code for the static initializer is exceeding
the 65535 bytes limit", then you will need to break your structure up into multiple
array 'layers'.

A 'layer' is defined here as a set of one or more complete 'y' arrays, such that all
blocks contained in the horizontal plane to be generated are defined. In other words,
you cannot end a 'layer' partway through generating blocks for a given 'y' without
achieving undesirable results.

Thus, if your structure is 25x40 blocks wide, its total planar area is 1000 blocks.
An array containing this 40x40 block area to a height of 3 would contain 3000 blocks;
trying to add another 'y' element at this point would exceed the static initializer
limit, and you wouldn't want to add only a partial 'y' layer as the next layer up
wouldn't finish generating at the same vertical coordinate.

If you wanted to generate a structure using this base that is 20 blocks tall, you'd
need to make 7 'layers' of height 3 each, except for the final layer which would only
have a height of 2.

TIP: If your structure has many layers that are the same, just create one array for
it and add it multiple times to the generator. For instance, if you're making a wizard
tower with 10 identical floor layouts, you only need to create an array for a single
floor and then addBlockArray(floorWizardTower) 10 times.


IMPORTANT: NOTES ON SETTING METADATA
====================================
Read this or your directional blocks WILL face the wrong direction!

Blocks that use metadata for things other than rotation, such as cloth for color, can
be set without regard for facing.

For directional blocks, however, you must pay attention to the default structure facing
and set the metadata accordingly. For example, the NPC Hut faces EAST, so to make stairs
ascending into the structure, the stairs must ascend to the WEST, which is a metadata value
of 1.

Note that if you fail to include a metadata value for a directional block, it will be
interpreted as a value of 0 for purposes of rotation.

(ROTATION) type is in parentheses, followed by metadata explanations for all blocks
that use that type. Use this as a guide for setting custom block rotation type.

[POST] Indicates that these block types are set post-generation, so can be used for custom
events you want to do after the structure has finished generating. Currently only the
type WALL_MOUNTED uses this.

TIP: It's worth stating one more time: using MCEdit will vastly reduce the headache of
trying to figure out metadata information for directional blocks. Please use it first,
then it's a simple (but still tedious) task to manually convert to an array format from
the code generated by MCEdit. The code it generates WILL give you the correct metadata
value based on how you placed the block in the structure, whereas calculating it yourself
tends to involve many backwards blocks.

A NOTE ABOUT REDSTONE: Redstone wires will set their direction automatically. Set your
redstone torches, repeaters, etc. to the correct on/off state and direction. When you
generate the structure, it will take a moment for the redstone device to update, at which
point it should work as expected. If you try to use it before it updates, the redstone
wire will not have power; manually setting redstone wire's power level can lead to strange
effects, so is not generally recommended.

ROTATION TYPES AND DESCRIPTIONS
===============================

(ANVIL) Anvils:
0 sets the anvil's length along the north-south axis, 1 along east-west
Add 4 for slightly damaged, add 8 for very damaged. DON'T add 12!!!

(DOOR) Doors:
Bottom block should have a value of 0,1,2,3 facing west, north, east, or south
Add 16 for hinges on right side, but this doesn't seem to work correctly - check
the wiki for more details: http://www.minecraftwiki.net/wiki/Data_values#Door
Top block's value should be bottom + 8

(GENERIC) Beds, Cocoas, Fence Gates, Pumpkins, Jack'o Lanterns, Tripwire Hooks, End Portal Frames:
0,1,2,3 facing south, west, north, east. For cocoa, it is placed on the block to that side.
Add 4 for opened (gates), no face (pumpkins & jack'o lanterns), connected (tripwire hooks)
medium size (cocoa) or inserted eye (end portal frame)
Add 8 for large size (cocoa) or to set as head (bed) - be sure to put the head on the correct side
of the base ;)

(STAIRS) Stairs:
0,1,2,3 ascending east, west, south, north, +4 for descending

(PISTON_CONTAINER)
Chests, Furnaces, Ladders and Wall Signs:
2,3,4,5 facing north, south, west, east
(ladders and signs placed on 'facing' side of the block: 2 will place on north side of block to the south)
RE: Chests - if you place two chests adjacent to each other but with different facings,
be prepared for weird things to happen as the code will try to enforce your chosen
facing. Don't say I didn't warn you.

Pistons, Piston Extensions, Dispensers, Droppers and Hoppers:
Same as above; 0,1 down/up

Activated/Detector/Powered Rails: (yes, they use PISTON_CONTAINER, not RAIL)
Same as Rails for values 0-5. No corner pieces.
Add 8 if powered (should be set automatically, but maybe not)

(RAIL)
Rails:
0,1 flat track along north-south/east-west axis
2,3,4,5 track ascending to the east, west, north, south
6,7,8,9 corner track: NW, NE, SE, SW corner
Corner pieces connect the opposite directions, so 6 (NW corner) connects to the south and east

(REPEATER)
Redstone Repeater and Comparator:
0,1,2,3 facing north, east, south, west. Add 4 per tick delay beyond the first.
Example: Repeater facing south with 1 tick = 2; 3 ticks = 2 + 4 + 4 = 10.

(SIGNPOST)
Sign Posts:
16 directions, 0 being due south and working clockwise towards south-southeast at 15
Keep in mind that this is the direction in which the writing will show.
See http://www.minecraftwiki.net/wiki/Data_values#Sign_Posts for exact details

(TRAPDOOR)
Trapdoors:
0,1,2,3 attached to the wall on the south, north, east or west. For example, if you
are facing west and want the trapdoor to open away from you, attach it to the wall
to the west (3), NOT east.
Add 4 if you want the trapdoor opened, add 8 if you want it attached to the top
half of the block (these are additive, so east wall open on upper half is 2+4+8 = 14)

Note that placing active redstone signals near trapdoors will cause them to detach IF
in a location not otherwise allowed (e.g. attached to glass of any kind)

(VINE)
Vines:
1,2,4,8 anchored to the south, west, north or east side of the vine block, NOT
neighboring block (so the position may be opposite what you think)

(WALL_MOUNTED) [POST]
Buttons and Torches (normal and redstone):
1,2,3,4 pointing east, west, south, north.
Torches (both) only: 5 standing on floor

(LEVER) [POST]:
Same as WALL_MOUNTED with the following:
5,6 ground lever south or east when off;
7,0 ceiling lever, south or east when off, +8 for switched on

(WOOD)
Wood:
0,1,2,3 Oak/Spruce/Birch/Jungle placed vertically
Add 4 will face east/west, add 8 will face north/south, add 12 bark only

(SKULL)
Skull Blocks:
1 is on the floor, 2,3,4,5 on a wall and facing south, north, west, east.
Note that these are opposite from what the wiki claims, but it's what I saw in my tests.
For example, while looking WEST at a structure of facing EAST, no rotations would be
applied to metadata. A value of 4, which the wiki claims is facing east, in fact faces
west, AWAY from the player. Perhaps they meant 'attached to' instead of facing.
Associated tile entity will determine skull type, as well as rotation if on floor, so
you must make a CUSTOM_BLOCK case to get anything other than skeleton skulls and then
use one of the "setSkullData" methods below.

HOW TO USE CUSTOM HOOK METHOD
=============================
"onCustomBlockAdded(World world, int x, int y, int z, int fakeID, int customData)"

Step 1: Choose a block ID
Custom block hooks require block ids greater than 4095. If you want to your block to
soft-spawn, you can also use the negative value of your defined block id.

This value is what will be used by you to determine how to handle the case in the
onCustomBlockAdded method. It is the parameter 'fakeID'.

Best practice is to define it as "public static final int CUSTOM_BLOCK_NAME = value"

Step 2: Set the real block id
Since values > 4095 are not real blocks, you must use the special method

"getRealBlockID(int fakeID, int customData)"

to define which block will be set in the world when the generator encounters your custom
value. The parameter customData could also allow you to subtype a single fakeID.

For example, if you wanted to generate random holes in your structure, you could use a
single id CustomHooks.RANDOM_HOLE, each subtype of which would be a different block id in the structure.
It would then be simple to randomly remove blocks of this type from within the case
'CustomHooks.RANDOM_HOLE' in onCustomBlockAdded, even if the blocks were in fact different kinds.

If you forget this step, no block will be set and the hook will not trigger.

Step 3: Define your custom case in 'onCustomBlockAdded'
This is where you handle the custom id, at which point you can do pretty much anything.

See WorldGenStructure's demo implementation of this method for concrete examples.

SOME USEFUL METHODS
===================
The following are methods designed to make handling onCustomBlockAdded cases easier:

1. addItemToTileInventory(World world, ItemStack itemstack, int x, int y, int z)

    Use this method to conveniently add items to any TileEntity that has an inventory
    (i.e. implements either IInventory or ISidedInventory).
    
    Items are added to the first slot available and the method returns false if the
    stack was not able to be added entirely or if there was an error.

2.1 spawnEntityInStructure(World world, Entity entity, int x, int y, int z)

    Spawns the passed in entity within the structure such that it doesn't spawn inside of
    walls by using the method setEntityInStructure below. If no valid location was found,
    the entity will still spawn but the method will return false.
    
2.2 setEntityInStructure(World world, Entity entity, int x, int y, int z)

     Sets an entity's location so that it doesn't spawn inside of walls, but doesn't spawn
     the entity. Automatically removes placeholder block at coordinates x/y/z.
     Returns false if no suitable location found so user can decide whether to spawn or not.

3. setHangingEntity(World world, ItemStack hanging, int x, int y, int z)

    Places a hanging entity in the world based on the arguments provided; orientation
    will be determined automatically based on the dummy blocks data, so a WALL_MOUNTED
    block id (such as Block.torchWood.blockID) must be returned from getRealBlockID().
    
    This method returns the direction in which the entity faces for use with the methods
    'setItemFrameStack' and 'setPaintingArt'. It is not needed for wall signs.

4. setItemFrameStack(World world, ItemStack itemstack, int x, int y, int z, int direction,
    int itemRotation)

    Finds the correct ItemFrame in the world for the coordinates and direction given and
    places the itemstack inside with the rotation provided, or default if no itemRotation
    value is given. 'direction' parameter is value returned from setHangingEntity

5. setPaintingArt(World world, String name, int x, int y, int z, int direction)

    Sets the art for a painting at location x/y/z and sends a packet to update players.
    'direction' parameter is value returned from setHangingEntity
    Returns false if 'name' didn't match any EnumArt values.

6. setSignText(World world, String[] text, int x, int y, int z)

    Adds the provided text to a sign tile entity at the provided coordinates, or returns
    false if no TileEntitySign was found. String[] must be manually set for each sign, as
    there is currently no way to store this information within the block array.

7.1 setSkullData(World world, String name, int type, int x, int y, int z)

    Sets the skull type and player username (if you can get one) for the tile entity at
    the provided coordinates. Returns false if no TileEntitySkull was found.
    
7.2 setSkullData(World world, String name, int type, int rot, int x, int y, int z)

    As above but with additional rotation data (rot). This only applies to skulls sitting
    on the floor, not mounted to walls.

CONGRATULATIONS! YOU'VE REACHED THE END!
========================================
