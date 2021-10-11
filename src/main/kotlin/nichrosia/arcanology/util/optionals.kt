package nichrosia.arcanology.util

import java.util.*

val <T> Optional<T>.asNullable: T?
    get() = if (isPresent) get() else null

fun <T, R> Optional<T>.transform(transformer: (T) -> R): Optional<R> {
    return if (isPresent) Optional.of(transformer(get())) else Optional.empty()
}