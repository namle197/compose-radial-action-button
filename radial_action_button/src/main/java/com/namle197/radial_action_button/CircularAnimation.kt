package com.namle197.radial_action_button

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween

sealed class CircularAnimation(
    open val animationSpec: AnimationSpec<Float>, // Defines how animation progresses
    open val betweenAnimation: Long // Delay between each item animation
) {
    data class StaggerAnimation(
        override val animationSpec: AnimationSpec<Float> = tween(500),
        override val betweenAnimation: Long = 100L // 100ms delay between each item
    ) : CircularAnimation(animationSpec, betweenAnimation)

    data class ExpandAnimation(
        override val animationSpec: AnimationSpec<Float> = tween(
            700,
            easing = LinearOutSlowInEasing
        ),
        override val betweenAnimation: Long = 50L // Smaller delay
    ) : CircularAnimation(animationSpec, betweenAnimation)
}
