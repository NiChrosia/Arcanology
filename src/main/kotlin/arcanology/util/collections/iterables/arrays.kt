package arcanology.util.collections.iterables

inline fun <reified T> T.repeat(times: Int, noinline modifier: (T) -> T = { it }) = Array(times) { modifier(this) }