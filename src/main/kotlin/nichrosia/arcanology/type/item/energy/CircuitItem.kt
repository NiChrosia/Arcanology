package nichrosia.arcanology.type.item.energy

import net.minecraft.item.Item
import nichrosia.arcanology.energy.EnergyTier

/** A purely decorational class for all circuits, as they are simply crafting ingredients.
 * Each are comprised of an energy source, a conductor, a switch, and the load.
 *
 * The quality and efficiency of each circuit depends on the material is it made of.
 * For example, a silver wire is far more effective than a copper or aluminum one.
 *
 * Circuits:
 *
 * Rudimentary Circuit ([EnergyTier.T1]): A simple but bulky circuit crafted by hand from aluminum wiring.
 *
 * Basic Circuit ([EnergyTier.T2]): A simple machine made circuit comprised of copper wiring and nickel-zinc batteries.
 *
 * Advanced Circuit (([EnergyTier.T3])): A more advanced machine-crafted circuit made up of gold wires and a lithium-sulfur battery.
 *
 * Supercircuit ([EnergyTier.T4]): An incredibly powerful circuit meticulously crafted by machinery, made up of pure silver wiring and
 * solid state batteries.
 *
 * Superconductor circuit ([EnergyTier.T5]): An extremely powerful circuit, made up of niobium-titanium alloy, with no
 * energy loss over distance, incredibly fast transfer rates, and supercapacitor batteries. */
open class CircuitItem(settings: Settings) : Item(settings)