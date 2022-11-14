package io.harmny.model

data class Note(
    val noteName: NoteName,
    val alterations: List<Alteration> = listOf(Alteration.NATURAL),
) {

    fun halfUp(): Note {
        return if (alterations.isEmpty()) {
            Note(noteName, listOf(Alteration.SHARP))
        } else if (Alteration.FLAT in alterations) {
            Note(noteName, alterations - Alteration.FLAT)
        } else if (Alteration.SHARP in alterations) {
            Note(noteName, alterations + Alteration.SHARP)
        } else {
            Note(noteName, listOf(Alteration.SHARP))
        }
    }

    fun getOffset(): Int = noteName.offset + alterations.sumOf { it.offset }

    override fun toString(): String {
        return "${noteName.name}${alterations.joinToString("")}"
    }
}
