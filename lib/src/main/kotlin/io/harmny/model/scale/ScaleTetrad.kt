package io.harmny.model.scale

import io.harmny.model.Note
import io.harmny.model.Tetrad

data class ScaleTetrad(
    override val degree: Int,
    val tetrad: Tetrad,
) : ScaleDegree {

    override fun getNotes(): List<Note> = tetrad.getNotes()
}
