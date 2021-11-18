package arcanology.registrar.impl

import net.minecraft.entity.effect.StatusEffect
import net.minecraft.util.registry.Registry
import arcanology.type.common.registar.lang.VanillaLangRegistrar

open class StatusEffectContentRegistrar : VanillaLangRegistrar.Basic<StatusEffect>(Registry.STATUS_EFFECT, "status_effect")