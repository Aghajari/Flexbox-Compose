package com.aghajari.compose.flexbox

import androidx.compose.runtime.Stable
import androidx.compose.ui.layout.Placeable
import kotlin.math.max

/**
 * [FlexboxLine] holds all [Placeable]s that fit on a line,
 * relative to the [Flexbox] constraints.
 */
@Stable
class FlexboxLine {

    /**
     * List of all [Placeable]s on this line.
     */
    internal val placeables: MutableList<Placeable> = mutableListOf()

    /**
     * The maximum space that the [Placeable]s on this line occupy.
     */
    internal var usedSpace: Int = 0
        private set

    /**
     * Adds new [Placeable]s to this line.
     *
     * @return True if all of the [placeables] can fit on this line.
     *  False otherwise.
     */
    internal fun addPlaceables(
        placeables: List<Placeable>,
        flexSpace: Int,
        contentSpace: Int,
        padding: Int
    ): Boolean {
        return if (flexSpace >= usedSpace + contentSpace) {
            usedSpace += contentSpace + padding
            this.placeables.addAll(placeables)
            true
        } else {
            false
        }
    }

    /**
     * Notifies that no more [Placeable] can fit on this line.
     */
    internal fun close(padding: Int) {
        usedSpace = max(usedSpace - padding, 0)
    }
}