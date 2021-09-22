package nichrosia.arcanology.type.element

@Suppress("unused")
enum class Element {
    /** The light element.
     *
     * Found in: the Overworld, in prismatic temples.
     *
     * Capable of manipulating light. */
    Light,

    /** The void element.
     *
     * Found in: the End, in void monoliths.
     *
     * Capable of manipulating space and time. */
    Void,

    /** The fire element.
     *
     * Found in: the Nether, in flare shrines.
     *
     * Capable of creating, and manipulating flames and forging metals. */
    Fire,

    /** The water element.
     *
     * Found in: oceans, in aqua pylons.
     *
     * Capable of manipulating fluids, and creating complex fluids. */
    Water,

    /** The earth element.
     *
     * Found in: the ground of the Overworld, in terrafortresses.
     *
     * Capable of manipulating ground and metals. */
    Earth,

    /** The air element.
     *
     * Found in: the sky of the Overworld, in floating islands.
     *
     * Capable of creating, and manipulating electricity, as well as the wind. */
    Air,

    /** The element of life, or experience.
     *
     * Found innately in all living things, as well as the full combination of all other [Element]s.
     *
     * Capable of enchanting tools to become far more powerful. */
    Mana;

    companion object {
        val elementalValues: Array<Element>
            get() = values().sliceArray(0..5)
    }
}