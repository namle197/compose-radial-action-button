package com.namle197.radial_action_button

sealed class CircularSpan {
    abstract val value: Float

    data class Angle(override val value: Float) : CircularSpan()
    class Circle private constructor() : CircularSpan() {
        override val value: Float = 360f

        companion object {
            fun create(): Circle = Circle()
        }
    }
}