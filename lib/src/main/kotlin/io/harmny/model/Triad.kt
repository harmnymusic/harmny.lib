package io.harmny.model

data class Triad(
    val note1: Note,
    val note2: Note,
    val note3: Note,
) : NotesSequence {

    fun invert(): Triad {
        return Triad(note2, note3, note1)
    }

    override fun getNotes(): List<Note> {
        return listOf(note1, note2, note3)
    }

    override fun toString(): String {
        return "Triad($note1, $note2, $note3)"
    }
}
