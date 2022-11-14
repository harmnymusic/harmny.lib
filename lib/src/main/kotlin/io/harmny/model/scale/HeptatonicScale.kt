package io.harmny.model.scale

import io.harmny.model.Alteration
import io.harmny.model.Note
import io.harmny.model.NoteName
import io.harmny.model.Tetrad
import io.harmny.model.Triad
import kotlin.math.abs

abstract class HeptatonicScale(
    protected val root: Note,
    private val offsets: List<Int>,
) : Scale {

    init {
        require(offsets.size == 7) { "Heptatonic scale must have 7 intervals." }
        require(root.alterations.size < 2) { "Root note must have no more than one alteration." }
    }

    private val cachedNotes: List<Note> by lazy { findNotes() }

    override fun getNotes() = cachedNotes

    override fun getTriadAtDegree(number: Int): Triad {
        val index = getDegreeIndex(number)

        val note1 = cachedNotes[index]
        val note2 = cachedNotes[(index + 2) % 7]
        val note3 = cachedNotes[(index + 4) % 7]
        return Triad(note1, note2, note3)
    }

    override fun getTetradAtDegree(number: Int): Tetrad {
        val index = getDegreeIndex(number)

        val note1 = cachedNotes[index]
        val note2 = cachedNotes[(index + 2) % 7]
        val note3 = cachedNotes[(index + 4) % 7]
        val note4 = cachedNotes[(index + 6) % 7]
        return Tetrad(note1, note2, note3, note4)
    }

    private fun getDegreeIndex(number: Int): Int {
        require(number in 1..7) { "Degree number must be in range 1..7." }
        return number - 1
    }

    private fun findNotes(): List<Note> {
        // For instance: E, F, G, A, B, C, D
        val noteNames = generateSequence(root.noteName) { it.next() }.take(7).toList()
        val scaleNoteNumberValues = offsets.map { it + root.getOffset() }
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
}
