package nichrosia.arcanology.util

import java.util.*

/** Converts this [Optional] to a nullable type. */
val <T> Optional<T>.asNullable: T?
    get() = if (isPresent) get() else null

/** Transforms the given [Optional] to the new type. */
@Suppress("NULLABLE_TYPE_PARAMETER_AGAINST_NOT_NULL_TYPE_PARAMETER")
fun <T, R> Optional<T>.transform(transformer: (T) -> R): Optional<R> {
    return if (isPresent) Optional.of(transformer(get())) else Optional.empty()
}