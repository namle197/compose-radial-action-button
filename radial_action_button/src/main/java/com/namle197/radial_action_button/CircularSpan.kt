package com.namle197.radial_action_button

/**
 * Defines the angular span for positioning items in a circular layout.
 */
sealed class CircularSpan {
    abstract val value: Float

    /**
     * Positions items within a specific angle range.
     * @param value The angle in degrees (e.g., 180f for half circle, 90f for quarter)
     */
    data class Angle(override val value: Float) : CircularSpan()
    
    /**
     * Positions items evenly around a full circle (360 degrees).
     */
    data object Circle : CircularSpan() {
        override val value: Float = 360f
    }
}