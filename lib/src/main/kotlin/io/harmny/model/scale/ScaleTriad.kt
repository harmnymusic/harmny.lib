package io.harmny.model.scale

import io.harmny.model.Note
import io.harmny.model.Triad

data class ScaleTriad(
    override val degree: Int,
    val triad: Triad,
) : ScaleDegree {

    override fun getNotes(): List<Note> = triad.getNotes()
}
