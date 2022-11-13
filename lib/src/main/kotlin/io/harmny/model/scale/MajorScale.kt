package io.harmny.model.scale

import io.harmny.model.Note

private val majorScaleIntervals = listOf(0, 2, 4, 5, 7, 9, 11)

class MajorScale(root: Note) : HeptatonicScale(root, majorScaleIntervals) {

    override fun toString(): String {
        return "MajorScale(root=$root)"
    }
}
