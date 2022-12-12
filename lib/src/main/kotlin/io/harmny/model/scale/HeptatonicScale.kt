package io.harmny.model.scale

import io.harmny.model.Alteration
import io.harmny.model.Note
import io.harmny.model.NoteName
import io.harmny.model.Tetrad
import io.harmny.model.Triad
import kotlin.math.abs

abstract class HeptatonicScale(
    private val scaleType: ScaleType,
    private val root: Note,
) : Scale {

    init {
        require(scaleType.offsets.size == 7) { "Heptatonic scale must have 7 intervals." }
        require(root.alterations.size < 2) { "Root note must have no more than one alteration." }
    }

    private val _notes: List<Note> by lazy { calculateNotes() }

    override fun getNotes() = _notes

    fun getTriadAt(degree: Int): ScaleTriad {
        val index = getDegreeIndex(degree)

        val note1 = _notes[index]
        val note2 = _notes[(index + 2) % 7]
        val note3 = _notes[(index + 4) % 7]

        val triad = Triad(note1, note2, note3)
        return ScaleTriad(degree, triad)
    }

    fun getTetradAt(degree: Int): ScaleTetrad {
        val index = getDegreeIndex(degree)

        val note1 = _notes[index]
        val note2 = _notes[(index + 2) % 7]
        val note3 = _notes[(index + 4) % 7]
        val note4 = _notes[(index + 6) % 7]

        val tetrad = Tetrad(note1, note2, note3, note4)
        return ScaleTetrad(degree, tetrad)
    }

    private fun getDegreeIndex(number: Int): Int {
        require(number in 1..7) { "Degree number must be in range 1..7." }
        return number - 1
    }

    private fun calculateNotes(): List<Note> {
        // For instance: E, F, G, A, B, C, D
        val noteNames = generateSequence(root.noteName) { it.next() }.take(7).toList()
        val scaleNoteNumberValues = scaleType.offsets.map { it + root.getOffset() }
        return noteNames.zip(scaleNoteNumberValues).map { (noteName, noteNumberValue) ->
            getApplicableNote(noteName, noteNumberValue)
        }
    }

    private fun getApplicableNote(noteName: NoteName, noteNumberValue: Int): Note {
        val referenceNoteValue = noteName.offset
        val (normalizedReferenceNote, normalizedRealNoteValue) = if (abs(noteNumberValue - noteName.offset) > 2) {
            // put one note octave up to compare
            if (noteNumberValue > referenceNoteValue) {
                referenceNoteValue + 12 to noteNumberValue
            } else {
                referenceNoteValue to noteNumberValue + 12
            }
        } else {
            referenceNoteValue to noteNumberValue
        }
        val (alteration, alterationsCount) = determineAlterations(normalizedReferenceNote, normalizedRealNoteValue)
        return Note(
            noteName = noteName,
            alterations = List(alterationsCount) { alteration },
        )
    }

    private fun determineAlterations(referenceNoteValue: Int, realNoteValue: Int): Pair<Alteration, Int> {
        return if (realNoteValue == referenceNoteValue) {
            Alteration.NATURAL to 1
        } else if (realNoteValue > referenceNoteValue) {
            Alteration.SHARP to (realNoteValue - referenceNoteValue)
        } else {
            Alteration.FLAT to (referenceNoteValue - realNoteValue)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HeptatonicScale) return false

        if (scaleType != other.scaleType) return false
        if (root != other.root) return false
        return true
    }

    override fun hashCode(): Int {
        var result = scaleType.hashCode()
        result = 31 * result + root.hashCode()
        return result
    }
}
