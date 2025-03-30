package com.namle197.radial_action_button

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CircularMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    menuItemCount: Int,
    span: CircularSpan,
    startAngle: Float = 0f,
    radius: Float = 100f,
    clockwise: CircularDirection = CircularDirection.Clockwise,
    moveUpEnabled: Boolean = true,
    animation: CircularAnimation = CircularAnimation.StaggerAnimation(),
    centerContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val animatedRadiusValues = remember {
        List(menuItemCount) { Animatable(0f) }
    }

    LaunchedEffect(expanded) {
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
                        delay(index * animation.betweenAnimation)
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