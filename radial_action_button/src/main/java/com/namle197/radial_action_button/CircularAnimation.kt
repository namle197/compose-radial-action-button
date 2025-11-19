package com.namle197.radial_action_button

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween

/**
 * Defines animation styles for the circular menu items.
 */
sealed class CircularAnimation {
    abstract val animationSpec: AnimationSpec<Float>
    abstract val staggerDelay: Long

    /**
     * Animates items with a staggered delay between each item.
     * @param animationSpec Controls the animation timing
     * @param staggerDelay Delay in milliseconds between each item animation
     */
    data class StaggerAnimation(
        override val animationSpec: AnimationSpec<Float> = tween(500),
        override val staggerDelay: Long = 100L
    ) : CircularAnimation()

    /**
     * Expands all items simultaneously with a smooth easing function.
     * @param animationSpec Controls the animation timing
     */
    data class ExpandAnimation(
        override val animationSpec: AnimationSpec<Float> = tween(
            700,
            easing = LinearOutSlowInEasing
        )
    ) : CircularAnimation() {
        override val staggerDelay: Long = 0L
    }
}
