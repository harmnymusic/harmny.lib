package io.harmny.model.scale

import io.harmny.model.Alteration
import io.harmny.model.Note
import io.harmny.model.NoteName
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
