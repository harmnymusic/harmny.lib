package io.harmny.model.scale

enum class ScaleType(val offsets: List<Int>) {

    IONIAN(listOf(0, 2, 4, 5, 7, 9, 11)),
    // DORIAN(listOf()),
    // PHRYGIAN(listOf()),
    // LYDIAN(listOf()),
    // MIXOLYDIAN(listOf()),
    AEOLIAN(listOf(0, 2, 3, 5, 7, 8, 11)),
    // LOCRIAN(listOf()),
    // MAJOR_PENTATONIC(listOf()),
    // MINOR_PENTATONIC(listOf())
    ;

    companion object {
        fun getAllHeptatonic(): List<ScaleType> {
            return values().filter { it.isHeptatonic() }
        }
    }

    fun isHeptatonic() = offsets.size == 7

    fun isPentatonic() = offsets.size == 5
}
