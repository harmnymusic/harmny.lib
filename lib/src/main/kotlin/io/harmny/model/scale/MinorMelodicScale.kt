package io.harmny.model.scale

import io.harmny.model.Note

private val minorMelodicScaleIntervals = listOf(0, 2, 3, 5, 7, 8, 10)

class MinorMelodicScale(root: Note) : HeptatonicScale(root, minorMelodicScaleIntervals) {

    override fun toString(): String {
        return "MinorMelodicScale(root=$root)"
    }
}
