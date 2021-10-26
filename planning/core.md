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

Another unique feature would be turning a specific color (e.g., red, as it's commonly recognized as bad) when the
network structure is invalid, (e.g., when it loops back into the same block), and turning back into the normal color
(aqua) when the network structure is valid again.

### Pulse blocks

Networking blocks are as listed:
 - Transmutator, a block that converts energy from one network tier to another.
 - Teleportator, an ender network tier block that allows for wireless connection between network nodes.

## Ores

For more advanced metals (or potentially even the base ones, with processing machines being made from vanilla
materials), ores are mined as a raw form, such as bauxite, wolframite, chromite, sperrylite, etc. They are typically
generated in varying sized stringy clumps, similarly to 1.18's large ore veins. The refining process 
would be mostly standard for all ores, being slightly more complicated for rarer ores, and would consist of 3-5 
machines.

### Generation

The majority of ores would generate in large-ish stringy clumps in a mineral form, requiring processing to convert into 
a usable form, typically via melting & refinement.

### Refining

The refining process consists of melting the mineral, refining it into metals/gems, and optionally purifying it if
the pure form is required.

 - Melting, converts the mineral into a usable molten state
   - Refining, converts minerals (e.g. osmiridium) into separate fluids (e.g. raw osmium & raw iridium)
    
   - Purification, removes impurities from raw molten ore
   - Enrichment, enriches the molten ore into 100% pure ore, resulting in 20% (subject to change) of the original volume in pure ore
     - Casting into various forms, e.g., ingots, tool heads, armor plates, etc.

### Fluid types

 - Molten metal
   - Molten minerals
   - Molten metal
   - Pure molten metal

### Minerals

A list of the main minerals/ores that will be used in Arcanology.

 - Copper, the metal used for extremely crude machines, used as a crutch to get into the main tech
 - Titanium, the base metal, used for most low tier tech, like standard machines 
 - Iridium, the medium metal, used for medium tier tech, such as industrial machines
 - Aeonite (from the Greek word for eternal), the top tier metal, used for vanguard technologies, extremely rare
    - Whitish-violet
    - Crystalline
    - The entire structure is a massive fully connected grid of tetrahedrons

## Machines

Machines are blocks mainly used for processing materials into other materials, or generating some other sort of object.

The planned machines are as listed:
 - Resource acquisition:
   - Aeroponic Germinator, grows plants on a suspended connector and sprays it with water.
   - Magmatic Extractor, extracts magma from extremely deep within the earth
 - Ore processing:
   - Magmatic Enrichment Chamber, filters the slag from magma
   - Magmatic Refinery, separates enriched magma into usable molten ores

   - Ember Smelter, smelts raw minerals into molten minerals
   - Metallic Refinery, converts molten minerals into separate molten metal
   - Metallic Purifier, purifies molten raw ore into normal molten ore
   - Metallic Enrichment Chamber, enriches normal molten ore into pure molten ore, reducing it into 1/5 of the original volume
   - Forging Chamber, casts normal/pure molten ore into forms such as ingots, tool heads, armor plates, etc
   - Fabrication Chamber, constructs complicated objects such as laser weaponry, life-support modules, detonative weaponry, etc.

### Machine types

#### Magmatic Processing

Magmatic processing consists of slowly extracting magma from the depths of the earth (it works more efficiency in the 
nether too, around a 250% increase, but the nether would have different ores), based on a noise map that would have 
'peaks' of purity of specific ores

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

### Assembly Lines

The primary categorization of machines will be based on tiers & type. The two types will be first, standard machinery, 
used for manual production, as well as second, assembly lines, used to produce refined products from raw extracted
resources.

#### Formulas

The following are formulas for additional efficiency based on the cryo-efficiency percent, `e`.
 - Power usage: `2e`% less power is consumed while coolant is provided.
 - Speed: Items & fluids are produced `2.75e`% faster while coolant is provided.

## Auto-translation

By default, the mod is automatically translated into English and added to the mod's RRP. Additionally, it should also
use an API like NLP to automatically translate the English translations into various other languages, preferably
on either every Gradle `build` task being run or whenever the GitHub action workflow runs.