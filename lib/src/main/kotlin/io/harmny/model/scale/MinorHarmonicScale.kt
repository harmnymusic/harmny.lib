package io.harmny.model.scale

import io.harmny.model.Note

private val minorHarmonicScaleIntervals = listOf(0, 2, 3, 5, 7, 8, 11)

class MinorHarmonicScale(root: Note) : HeptatonicScale(root, minorHarmonicScaleIntervals) {

    override fun toString(): String {
        return "MinorHarmonicScale(root=$root)"
    }
}
