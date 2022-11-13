package io.harmny.model

enum class Alteration(val offset: Int) {
    FLAT(-1) {
        override fun toString() = "b"
    },
    NATURAL(0) {
        override fun toString() = ""
    },
    SHARP(1) {
        override fun toString() = "#"
    },
}
