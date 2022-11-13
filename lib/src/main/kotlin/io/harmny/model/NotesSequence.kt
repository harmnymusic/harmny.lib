package io.harmny.model

interface NotesSequence {

    fun getNotes(): List<Note>

    fun getNotesOffsets(): List<Int> {
        val notes = getNotes()
        val notePairs = listOf(notes[0] to notes[0]) + notes.zipWithNext()
        var offset = 0

        return notePairs.map { (prevNote, note) ->
            val noteOffset = note.getOffset()
            if (noteOffset < prevNote.getOffset()) {
                offset += 12
            }
            noteOffset + offset
        }
    }
}
