package com.namle197.radial_action_button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MultiContentMeasurePolicy
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrainWidth
import kotlin.math.cos
import kotlin.math.sin

internal fun circularMeasurePolicy(
    density: Density,
    overrideRadius: (Int) -> Dp,
    span: CircularSpan = CircularSpan.Circle.create(),
    startAngle: Float = 0f,
    clockwise: CircularDirection,
    moveUpEnabled: Boolean = true,
) = MultiContentMeasurePolicy { (centerMeasurables: List<Measurable>,
                                    contentMeasurables: List<Measurable>),
                                constraints: Constraints ->
    require(centerMeasurables.size == 1) { "Center measurables should only have one child!" }
    require(contentMeasurables.isNotEmpty()) { "Content Measurables should not be empty!" }

    // Measure children with modified constraints
    val modifiedConstraints = constraints.copy(
        minWidth = 0,
        minHeight = 0,
    )

    val centerPlaceable: Placeable = centerMeasurables.first().measure(modifiedConstraints)
    val contentPlaceable = contentMeasurables.map { measurable ->
        measurable.measure(modifiedConstraints)
    }

    val maxRadius =
        contentPlaceable.mapIndexed { index, _ -> overrideRadius(index).toPx() }.maxOrNull() ?: 0f

    // Measurements for children/contents
    val maxChildWidth = contentPlaceable.maxOf { it.width }
    val maxChildHeight = contentPlaceable.maxOf { it.height }

    val (desiredWidth, desiredHeight) = if (moveUpEnabled) {
        (maxRadius * 2 + maxChildWidth + centerPlaceable.width).toInt() to (maxRadius * 2 + maxChildHeight + centerPlaceable.height).toInt()
    } else {
        centerPlaceable.width to centerPlaceable.height
    }

    val layoutWidth = constraints.constrainWidth(desiredWidth)
    val layoutHeight = constraints.constrainWidth(desiredHeight)

    val centerX = layoutWidth / 2f
    val centerY = layoutHeight / 2f

    val angleIncrement = when (span) {
        is CircularSpan.Circle -> {
            if (contentPlaceable.size > 1) {
                360f / contentPlaceable.size
            } else {
                0f
            }
        }

        is CircularSpan.Angle -> {
            if (contentPlaceable.size > 1) {
                span.value / (contentPlaceable.size - 1)
            } else {
                0f
            }
        }
    } * if (clockwise == CircularDirection.Clockwise) 1 else -1

    layout(width = layoutWidth, height = layoutHeight) {

        // children
        contentPlaceable.forEachIndexed { index, placeable ->
            val angle = startAngle + (angleIncrement * index)
            val radians = Math.toRadians(angle.toDouble())
            val radius = with(density) { overrideRadius(index).toPx() }

            val x = centerX + (radius * cos(radians)).toFloat() - placeable.width / 2
            val y = centerY + (radius * sin(radians)).toFloat() - placeable.height / 2

            placeable.placeRelative(x.toInt(), y.toInt())
        }

        centerPlaceable.placeRelative(
            (centerX - centerPlaceable.width / 2).toInt(),
            (centerY - centerPlaceable.height / 2).toInt()
        )
    }
}

@Composable
fun RadialLayout(
    modifier: Modifier = Modifier,
    overrideRadius: (Int) -> Dp,
    startAngle: Float = 0f,
    spanAngle: CircularSpan,
    clockwise: CircularDirection = CircularDirection.Clockwise,
    moveUpEnabled: Boolean = true,
    center: @Composable () -> Unit,
    contents: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    Layout(
        measurePolicy = circularMeasurePolicy(
            density = density,
            overrideRadius = overrideRadius,
            span = spanAngle,
            startAngle = startAngle,
            clockwise = clockwise,
            moveUpEnabled = moveUpEnabled
        ),
        modifier = modifier,
        contents = listOf(center, contents),
    )
}
