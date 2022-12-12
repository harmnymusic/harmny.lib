package io.harmny.model.scale

import io.harmny.model.Note

class MajorScale(root: Note): HeptatonicScale(ScaleType.IONIAN, root) {

    fun getParallelMinor() = MinorMelodicScale(getNotes()[5])
}
