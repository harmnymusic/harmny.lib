package io.harmny.model.scale

import io.harmny.model.Note

class MinorMelodicScale(root: Note): HeptatonicScale(ScaleType.AEOLIAN, root) {

    fun getParallelMajor(): MajorScale = MajorScale(getNotes()[2])
}
