# Core Systems

## Pulse

A new transfer network system, utilizing sending *pulses* of data towards a point, as well as being one-way. Unlike redstone, it cannot be 'on' or 'off', instead having a data level that gets transferred to the next pipe at a rate depending on the pipe. The three tiers will be electric (utilizing copper), light (utilizing quartz glass), and ender (utilizing crystalline ender pearls). More might be added later. Additionally, variants of these pipes that are capable of transferring items & liquids as data could also be added.

### Network types

There are four network types: data, energy, item, & fluid. Data is a simple pulse system aimed to be a redstone alternative, whereas the other ones are meant for distributing their respective property.

### Network features

A pulse network has only one mode, which is to evenly distribute energy outwards, with bias towards empty cables. Extraction is done from the entire network's balance as a whole, and it extracts the maximum possible energy and divides it up between each output.

Another unique feature would be turning a specific color (e.g., red, as it's commonly recognized as bad) when the network structure is invalid, (e.g., when it loops back into the same block), and turning back into the normal color (aqua) when the network structure is valid again.

### Pulse blocks

Networking blocks are as listed:
 - Transmutator, a block that converts energy from one network tier to another.
 - Teleportator, an ender network tier block that allows for wireless connection between network nodes.

## Ores

For more advanced metals (or potentially even the base ones, with processing machines being made from vanilla materials), ores are mined as a raw form, such as bauxite, wolframite, chromite, sperrylite, etc. They are typically generated in varying sized stringy clumps, similarly to 1.18's large ore veins. The refining process would be mostly standard for all ores, being slightly more complicated for rarer ores, and would consist of 3-5 machines.

### Generation

The majority of ores would generate in large-ish stringy clumps in a mineral form, requiring processing to convert into a usable form, typically via melting & refinement.

### Refining

The refining process consists of melting the mineral, refining it into metals/gems, and optionally purifying it if the pure form is required.

 - Melting, converts the mineral into a usable molten state (1 ore block -> 1000 mB, 1 raw ore item -> 250 mB)
   - Refining, converts minerals (e.g., osmiridium) into separate fluids (e.g., raw osmium & raw iridium) (2000 mB osmiridium -> 1000 mB raw osmium & 1000 mB raw iridium) (due to this machine outputting two different fluids, additional configuration will have to be available to allow the user to choose what side each fluid should output/input to)
    
   - Purification, removes impurities from raw molten ore (250 mB -> 1/9 B)
   - Enrichment, enriches the molten ore into 100% pure ore, resulting in 25% (subject to change) of the original volume in pure ore (4/9 B -> 1/9 B)
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

Machines are blocks mainly used for either processing, construction, or fabrication of complex items. There are two types of machines, modular (one that is created as a module, and inputting into a machine), and chamber (used to create modules or other advanced items.) 

The planned machines are as listed:
 - Modular:
	 - Resource acquisition:
	   - Aeroponic Germinator, grows plants on a suspended rod and sprays it with water.
	   - Magmatic Extractor, extracts magma from extremely deep within the earth
	 - Ore processing:
	   - Magmatic Enrichment Chamber, filters the slag from magma
	   - Magmatic Refinery, separates enriched magma into usable molten ores

	   - Smelter, smelts raw minerals into molten minerals
	   - Refinery, converts molten minerals into separate molten metal
	   - Purifier, purifies molten raw ore into normal molten ore
	   - Enrichment Chamber, enriches normal molten ore into pure molten ore, reducing it into 1/4 of the original volume
	   - Forging Chamber, casts normal/pure molten ore into forms such as ingots, tool heads, armor plates, etc.
 - Chambers:
    - Item production:
       - Fabrication Chamber, constructs complicated objects such as high tier weaponry, various modules, etc.

### Machine types

#### Magmatic Processing

Magmatic processing consists of slowly extracting magma from the depths of the earth (it works more efficiency in the nether too, around a 250% increase, but the nether would have different ores), based on a 4D (fourth dimension would equate to type) noise map that would have 'peaks' of purity of specific ores

### Tiers

Machines will be split into four tiers, primitive, standard, industrial, and vanguard.

### Upgrades

Additionally, each (non-primitive) machine can have upgrades, such as
 - Overclocking (increasing the speed but increasing energy consumption)
 - Cryo-efficiency (further increasing the effects of temperature/coolant)
 - Mass upgrade (increasing the quantity of items that can be processed at once)
 - Spatial compression (increasing the number of modules that can be added to this chain module)
 - Energy capacity

### Coolant

(Concept) There could be either a coolant or temperature based system, allowing for significantly increased efficiency.

### Assembly Lines

The primary categorization of machines will be based on tiers & type. The two types will be first, standard machinery, used for manual production, as well as second, assembly lines, used to produce refined products from raw extracted resources.

### Modularization

(Concept) Instead of there being a block for every type of machine, there could be *modules*, that would be inserted into a machine frame of a matching tier, enabling significantly easier automation. This system would only be available to industrial & vanguard.

#### Virtualization

(Concept) Multiple modules could be combined inside a fabrication chamber (or possibly a dedicated block, e.g., *Spatial condensation chamber*), creating a virtual machine chain, allowing for significantly more complicated assembly lines. (This can only be performed once (may be increased in the future), to prevent crash-inducing recursion.) This system would only be available to vanguard.

## Chronomanipulation

(Concept) A fancy synonym for time manipulation, would be used for both standstill (machine) and mobile (handheld) time manipulation in a region around the player, allowing for interesting concepts such as accelerating block growth, machine speed, etc.

### Chronokinetics

A subconcept of chronomanipulation, that would utilize rewinding time to go back to previously marked locations, effectively creating teleportation.