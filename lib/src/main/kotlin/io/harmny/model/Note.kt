package io.harmny.model

data class Note(
    val noteName: NoteName,
    val alterations: List<Alteration> = listOf(Alteration.NATURAL),
) {

    fun getOffset(): Int = noteName.offset + alterations.sumOf { it.offset }

    override fun toString(): String {
        return "${noteName.name}${alterations.joinToString("")}"
    }
}
