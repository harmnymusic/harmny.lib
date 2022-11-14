package io.harmny.model.scale

import io.harmny.model.NotesSequence
import io.harmny.model.Tetrad
import io.harmny.model.Triad

interface Scale : NotesSequence {

    fun getTriadAtDegree(number: Int): Triad

    fun getTetradAtDegree(number: Int): Tetrad
}
