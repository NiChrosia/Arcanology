package nichrosia.arcanology.util

import java.util.*

val <T> Optional<T>.asNullable: T?
    get() = if (isPresent) get() else null