package io.harmny.model

enum class NoteName(val offset: Int) {
    C(0),
    D(2),
    E(4),
    F(5),
    G(7),
    A(9),
    B(11);

    fun next() = values()[(ordinal + 1) % values().size]
}
