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

### Network types

There are four network types: data, energy, item, & fluid. Data is a simple pulse system aimed to be a redstone
alternative, whereas the other ones are meant for distributing their respective property.

### Network features

A pulse network has only one mode, which is to evenly distribute energy outwards, with bias towards empty cables.
Extraction is done from the entire network's balance as a whole, and it extracts the maximum possible energy and 
divides it up between each output.

### Pulse blocks

Networking blocks are as listed:
 - Transmutator, a block that converts energy from one network tier to another.
 - Teleportator, an ender network tier block that allows for wireless connection between network nodes.

## Ore Generation & Processing

For more advanced metals (or potentially even the base ones, with processing machines being made from vanilla
materials), ores are mined as a raw form, such as bauxite, wolframite, chromite, sperrylite, etc. They are typically
generated in varying sized stringy clumps, similarly to 1.18's large ore veins. The refining process 
would be mostly standard for all ores, being slightly more complicated for rarer ores, and would consist of 2-4 
machines.

### Core processing

 - Separation (turn ores such as bauxite into aluminum & iron), separates it into different elements
    - Acidic purification (corrodes the ore medium, leaving only chunks of ore), purifies into a purified chunk of ore
    - Thermal purification (melts off the slag that has a lower melting point)

### Smelting

Smelting raw ores is done by the following processes:
 - Vanilla blast furnace
 - Industrial smelting into molten ore (at varying temperatures & machines used, depending on the metal and its melting point)
    - potential alloying with other metals
    - solidification into an ingot or other forms

## Machines

Machines are blocks mainly used for processing materials into other materials, or generating some other sort of object.

The planned machines are as listed:
 - Resource acquisition:
    - Aeroponic Germinator, grows plants on a suspended connector and sprays it with water.
    - [Magmatic Extractor](#magmatic-processing), extracts magma from extremely deep within the earth
 - Ore processing:
    - Magmatic Enrichment Chamber, filters the slag from magma
    - Magmatic Separator, separates enriched magma into usable molten ores
      
    - [Separator](#ore-separation), splits ferromagnetic ores into raw ores
    - [Acidic Purifier](#ore-purification), purifies raw ores into pure ore
      
    - [Smelter](#molten-ore-processing), melts down pure/raw ore into molten ore
    - Solidifier, solidifies molten ore into ingots
 - Miscellaneous: 
    - Sawmill, pulverizes wood logs into 6 planks
    - Metallic Welder, conjoins non-malleable materials into more intricate plates, for purposes of armor or tools
   
### Machine types

#### Magmatic Processing

Magmatic processing machines use large amounts of power to extract magma from the earth, and convert it into usable
molten ore.

#### Ore Separation

Ore separation splits multi-ores into separate raw ores.

#### Ore Purification

Ore purifiers purify raw ore items into a purified version of the item, which is required for making a pure ingot.
If you just smelt the raw ore, an impure ingot will be created.

#### Molten Ore Processing

Molten ore processing consists of melting down raw or purified ore into a molten form, and casting it into ingot form.

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

### Coolant[^temperature]

Coolant can be provided to a machine to make it significantly faster. Potential available coolants will be:
 - Water, valued at 20% efficiency
 - Liquid hydrogen, valued at 100%

[^temperature]: (Might potentially be replaced with a temperature based system)

#### Formulas

The following are formulas for additional efficiency based on the cryo-efficiency percent, `e`.
 - Power usage: `2e`% less power is consumed while coolant is provided.
 - Speed: Items & fluids are produced `2.75e`% faster while coolant is provided.

## Auto-translation

By default, the mod is automatically translated into English and added to the mod's RRP. Additionally, it should also
use an API like NLP to automatically translate the English translations into various other languages, preferably
on either every Gradle `build` task being run or whenever the GitHub action workflow runs.