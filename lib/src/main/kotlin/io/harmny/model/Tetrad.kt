package io.harmny.model

data class Tetrad(
    val note1: Note,
    val note2: Note,
    val note3: Note,
    val note4: Note,
) : NotesSequence {

    override fun getNotes(): List<Note> {
        return listOf(note1, note2, note3, note4)
    }

    override fun toString(): String {
        return "Tetrad($note1, $note2, $note3, $note4)"
    }
}
