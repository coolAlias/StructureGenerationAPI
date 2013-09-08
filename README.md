STRUCTURE GENERATION TOOL
=========================
This is a tool I created for those of you who don't want to use schematics, for whatever reason, to generate structures in your world. It allows you to design and generate custom structures as well as rotate and offset your custom structures as they are generated in the world. Additionally, there are methods that allow you to set custom tile entity data, spawn entities or whatever else you can imagine.

FEATURES
========
- Generate custom structures in the world with the click of a button
- Auto-rotation of structures ensures it will always face the player
- Default offset will place the structure so it never spawns on top of the player
- Ability to manually change a structure's rotation or offset before spawning
- Full metadata block rotation compatibility
- Compatibility with custom blocks; just use addCustomBlockRotation to define its rotation type
- Set any block as a 'buffer' so your structure spawns more naturally in the environment
- Custom 'hooks' allow you to define specific behavior for any block set, such as setting tile entity data
  or spawning entities at that location
- Place blocks where they can't otherwise be placed, such as trapdoors on glass or torches in air

CUSTOM 'HOOKS'
==============
- Add items to any tile entity inventory with a single method
- Spawn entities without fear of spawning in a wall
- Add hanging entities such as item frames or paintings
- Set items in placed item frames
- Add text to signs, with color example
- Place any kind of mob head
 
POSSIBLE FUTURE FEATURES
========================
- Reading structures from a file to avoid compile-time size limitations (big maybe)
 
KNOWN BUGS
==========
- Large entities such as horses have problems spawning in confined spaces

LIMITATIONS
===========
Due to the maximum byte limit of static initializers, the structure array size cannot exceed 65535 bytes. What this means for you is that a single horizontal layer of your structure cannot exceed roughly 3400 blocks in area, so about a 68x50 rectangular base, judging by my tests so far.

You would then build your structure by adding layers to the generator list. See StructureArrays.java for complete details on how to go about this.

If your structure is very large (more than 3 or so maxed static arrays), you will probably need to store them in separate files or you will get a compile time error.

DEMO MOD INCLUDED
=================
A ready-to-go mod that will help familiarize you with the capabilities and functionality of the Structure Generation
Tool. There is currently no recipe for the item that spawns structures, so try it in Creative.

Two structures are included:
- blockArrayNPCBlackSmith - Vanilla blacksmith shop. Thanks to Microjunk for this one!
- blockArrayNPCHut - A highly modified village hut. Enter at your own risk.

These are the controls:

'O' - changes structure's default orientation by 90 degrees. This will have the effect of changing which side spawns facing the player.

'I' - toggles between increment and decrement offset values below

'X' - offsets the structure's spawn location towards (+) or away (-) from the player

'Y' - increments or decrements y value to offset spawn location

'Z' - offsets the structure's spawn location to the right (+) or left (-) of the player

'U' - reset x/y/z offsets to 0

'V' - toggles between generate / remove structure - when removing, be sure to click the EXACT position you clicked when spawning it

Right click - spawn / remove structure at tile location clicked

Note that if you want to change what structure is spawned, you must do that manually in ItemStructureSpawner's
onItemUse method. Sorry for the inconvenience.

INSTALLATION
============
To test out the mod:
Download the pre-compiled and zipped mod file and place it in your minecraft/mods folder. You're good to go!

For creating your own structures:

1. Place the StructureGeneratorBase.java file in your mod package.
2. Either use the included WorldGenStructure.java file or create your own class that extends StructureGeneratorBase
3. Build your own structure arrays by following the guidelines in StructureArrays.java
4. Use your WorldGenStructure class to generate your structures from whatever location you choose, such as a block or item

Let me know if you find any bugs, but if it has to do with metadata blocks, please, before you submit a bug report,
double and triple-check that you are setting metadata correctly. Each block has it's own way of determining facing,
so you MUST read each entry carefully every time you are setting metadata. Trust me, even looking at the reference
I still set metadata blocks backwards half the time.

Anyways, hope this is useful!

SCREEN SHOTS
============
Check them out on the forums: http://www.minecraftforum.net/topic/1963371-structure-generation-and-rotation-tool/
