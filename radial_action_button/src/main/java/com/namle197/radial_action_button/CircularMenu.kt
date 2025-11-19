package com.namle197.radial_action_button

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A circular menu that arranges items in a radial pattern around a center content.
 *
 * @param modifier The modifier to be applied to the layout
 * @param expanded Whether the menu is expanded or collapsed
 * @param itemCount Number of menu items to display. Should match the number of items in [content]
 * @param span Defines how items are distributed (full circle or partial arc)
 * @param startAngle Starting angle in degrees for the first item (0° = right, 90° = bottom, 180° = left, 270° = top)
 * @param radius Radius in pixels from center to items when expanded
 * @param clockwise Direction of item placement around the circle
 * @param moveUpEnabled If true, layout expands to accommodate items. If false, items overlay the center
 * @param animation Animation style for items appearing/disappearing
 * @param centerContent The content displayed at the center (typically a FAB)
 * @param content Items to be arranged in a circle. Should contain exactly [itemCount] composable calls
 */
@Composable
fun CircularMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    itemCount: Int,
    span: CircularSpan,
    startAngle: Float = 0f,
    radius: Float = 100f,
    clockwise: CircularDirection = CircularDirection.Clockwise,
    moveUpEnabled: Boolean = true,
    animation: CircularAnimation = CircularAnimation.StaggerAnimation(),
    centerContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val animatedRadiusValues = remember(itemCount) {
        List(itemCount) { Animatable(0f) }
    }

    LaunchedEffect(expanded, itemCount) {
        when (animation) {
            is CircularAnimation.ExpandAnimation -> {
                animatedRadiusValues.forEach { animatable ->
                    launch {
                        animatable.animateTo(
                            if (expanded) radius else 0f,
                            animationSpec = animation.animationSpec
                        )
                    }
                }
            }

            is CircularAnimation.StaggerAnimation -> {
                animatedRadiusValues.forEachIndexed { index, animatable ->
                    launch {
                        delay(index * animation.staggerDelay)
                        animatable.animateTo(
                            if (expanded) radius else 0f,
                            animationSpec = animation.animationSpec
                        )
                    }
                }
            }
        }
    }

    RadialLayout(
        modifier = modifier,
        overrideRadius = { index -> animatedRadiusValues[index].value.dp },
        spanAngle = span,
        startAngle = startAngle,
        clockwise = clockwise,
        moveUpEnabled = moveUpEnabled,
        center = centerContent,
        contents = content,
    )
}