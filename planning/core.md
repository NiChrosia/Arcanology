# Core Systems

An explanation of the core systems, such as Arcanity, Pulse, etc.

## Arcanity

A designation given to *arcane* artifacts that represents the object being incredibly good with magic, to the degree 
of being as natural as a human breathing. (The only current objects with this status are magic hearts). Arcane blocks
also affect ore generation, increasing the purity of an ore the closer it generates to an arcane object, such as a
magic heart.

## Pulse

A new transfer network system, utilizing sending *pulses* of data towards a point, as well as being one-way. Unlike 
redstone, it cannot be 'on' or 'off', instead having a data level that gets transferred to the next pipe at a rate 
depending on the pipe. The three tiers will be electric (utilizing copper), light (utilizing quartz glass), and ender 
(utilizing crystalline ender pearls). More might be added later. Additionally, variants of these pipes
that are capable of transferring items & liquids as data could also be added.

## Ore Generation & Processing

For more advanced metals (or potentially even the base ones, with processing machines being made from vanilla
materials), ores are mined as a raw form, such as bauxite, wolframite, chromite, sperrylite, etc. They are typically
generated in varying sized stringy clumps, similarly to 1.18's large ore veins. The [refining process](#core-processing) 
would be mostly standard for all ores, being slightly more complicated for rarer ores, and would consist of 3-5 
machines.

### Core processing

 - Magnetic separation (exclusive to ores with ferromagnetic components), separates into raw ores
 - Hydrophobic/hydrophilic separation (exclusive to ores that have opposite reactions to water)
 - Electrostatic separation (exclusive to ores with an opposite charge)
    - Acidic purification (corrodes the ore medium, leaving only chunks of ore), purifies into a purified chunk of ore
       - Acidic pickling (removes an oxide coating, exclusive to non-noble metals), deoxidizes into an unrusted chunk of ore

### Smelting

 - Vanilla blast furnace
 - Industrial smelting into molten ore (at varying temperatures & machines used, depending on the metal and its melting point)
    - potential alloying with other metals
    - solidification into an ingot or other forms

## Machines

### Tiers

Machines will be split into (as of now) three tiers: standard, industrial, and vanguard. Each one will have a separate
palette and drawing style, such as metallic, shiny ender-esque, and shiny purple. Standard is mainly vanilla-like
speeds, such as 5-10 seconds per recipe. Industrial will be a lot faster, at 3-6 seconds per recipe, and Vanguard would
have 1-2 seconds per recipe.

### Upgrades

Additionally, each machine can have upgrades, a total of tier * 10, and a cap per upgrade of tier * 5. The available 
upgrades would be as listed (efficiency percentage formula variables are `l` for level, and `t` for tier):
 - Overclocking upgrade, increases speed by `50lt`%, at the cost of `30l / t`% increased power consumption, with a maximum of 5 levels.
 - Cryo-efficiency upgrade, increases the efficiency of low temperatures by `5(l ^ 2)`%, with a max level of 8.
 - Mass upgrade, increases the number of items/fluid that can be processed at once by `40l`%, with a max level of 10.
 - Energy capacity upgrade, increases the total energy capacity by `40l`%, with a max level of 5.

### Low temperatures

When coolant is provided to a machine, it is significantly faster than it would be otherwise. The following are 
formulas for additional efficiency based on the cryo-efficiency percent, `e`.
 - Power usage: `2e`% less power is consumed while coolant is provided.
 - Speed: Items & fluids are produced `2.75e`% faster while coolant is provided.