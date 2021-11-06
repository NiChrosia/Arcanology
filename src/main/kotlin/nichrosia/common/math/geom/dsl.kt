package nichrosia.common.math.geom

infix fun Int.sized(height: Int): Size2 {
    return Size2(this, height)
}