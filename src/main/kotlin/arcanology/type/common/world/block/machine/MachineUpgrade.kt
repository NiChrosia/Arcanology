package arcanology.type.common.world.block.machine

interface MachineUpgrade {
    val speedMultiplier: Long

    fun apply(machine: Machine): Boolean {
        if (!machine.compatible(this)) return false

        machine.recipeProgressor.incrementAmount *= speedMultiplier

        return true
    }
}