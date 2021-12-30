package arcanology.common.type.impl.world.storage.modular

import team.reborn.energy.api.base.SimpleEnergyStorage

open class ModularEnergyStorage(capacity: Long, maxInsert: Long, maxExtract: Long) : SimpleEnergyStorage(capacity, maxInsert, maxExtract), Toggleable {
    override var enabled = false

    override fun supportsInsertion(): Boolean {
        return super.supportsInsertion() && enabled
    }

    override fun supportsExtraction(): Boolean {
        return super.supportsExtraction() && enabled
    }
}