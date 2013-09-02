StructureGenerationTool
=======================

This is a tool I created to allow you to rotate and offset your custom structures as they are generated in the world.

The only 2 files you need are WorldGenStructure and StructureArrays as a demo / template. The rest of the files is
simply a sort of demo mod I put together so you can try the tool out without having to set up a bunch of stuff.

If you use the StructureGen mod Item StructureSpawner, these are the controls:

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

Detailed instructions for creating your own structure are included in the StructureArrays file.

Read them carefully.

Currently, I don't have tile entity data incorporated. I hope to add that in the future.

Let me know if you find any bugs, but if it has to do with metadata blocks, please, before you submit a bug report,
double and triple-check that you are setting metadata correctly. Each block has it's own way of determining facing,
so you MUST read each entry carefully every time you are setting metadata. Trust me, even looking at the reference
I still set metadata blocks backwards half the time.

Anyways, hope this is useful!

Possible Future Features:

- Ability to set default x/y/z offsets for your structure
- Tile Entity Data??? If you can get me the NBT data, I can rotate it.

Known Bugs:

- Currently none.
