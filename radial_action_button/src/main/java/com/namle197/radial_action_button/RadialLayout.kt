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
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import kotlin.math.cos
import kotlin.math.sin

internal fun circularMeasurePolicy(
    density: Density,
    overrideRadius: (Int) -> Dp,
    span: CircularSpan = CircularSpan.Circle,
    startAngle: Float = 0f,
    clockwise: CircularDirection,
    moveUpEnabled: Boolean = true,
) = MultiContentMeasurePolicy { (centerMeasurables: List<Measurable>,
                                    contentMeasurables: List<Measurable>),
                                constraints: Constraints ->
    require(centerMeasurables.size == 1) { "Center measurables should only have one child!" }
    require(contentMeasurables.isNotEmpty()) { "Content Measurables should not be empty!" }

    // Measure children with relaxed constraints
    val modifiedConstraints = constraints.copy(
        minWidth = 0,
        minHeight = 0,
    )

    val centerPlaceable: Placeable = centerMeasurables.first().measure(modifiedConstraints)
    val contentPlaceables = contentMeasurables.map { it.measure(modifiedConstraints) }

    // Calculate maximum radius for any item
    val maxRadius = contentPlaceables.indices
        .maxOfOrNull { index -> overrideRadius(index).toPx() } ?: 0f

    // Cache max dimensions for efficiency
    val maxChildWidth = contentPlaceables.maxOfOrNull { it.width } ?: 0
    val maxChildHeight = contentPlaceables.maxOfOrNull { it.height } ?: 0

    // Determine layout size based on moveUpEnabled
    val (desiredWidth, desiredHeight) = if (moveUpEnabled) {
        // Expand to fit all items
        val width = (maxRadius * 2 + maxChildWidth + centerPlaceable.width).toInt()
        val height = (maxRadius * 2 + maxChildHeight + centerPlaceable.height).toInt()
        width to height
    } else {
        // Stay the size of center content
        centerPlaceable.width to centerPlaceable.height
    }

    val layoutWidth = constraints.constrainWidth(desiredWidth)
    val layoutHeight = constraints.constrainHeight(desiredHeight)

    val centerX = layoutWidth / 2f
    val centerY = layoutHeight / 2f

    // Calculate angle increment between items
    val angleIncrement = when (span) {
        is CircularSpan.Circle -> {
            if (contentPlaceables.size > 1) {
                360f / contentPlaceables.size
            } else {
                0f
            }
        }

        is CircularSpan.Angle -> {
            if (contentPlaceables.size > 1) {
                span.value / (contentPlaceables.size - 1)
            } else {
                0f
            }
        }
    } * if (clockwise == CircularDirection.Clockwise) 1 else -1

    layout(width = layoutWidth, height = layoutHeight) {
        // Place items in circular pattern
        contentPlaceables.forEachIndexed { index, placeable ->
            val angle = startAngle + (angleIncrement * index)
            val radians = Math.toRadians(angle.toDouble())
            val itemRadius = overrideRadius(index).toPx()

            val x = centerX + (itemRadius * cos(radians)).toFloat() - placeable.width / 2
            val y = centerY + (itemRadius * sin(radians)).toFloat() - placeable.height / 2

            placeable.placeRelative(x.toInt(), y.toInt())
        }

        // Place center content on top
        centerPlaceable.placeRelative(
            (centerX - centerPlaceable.width / 2).toInt(),
            (centerY - centerPlaceable.height / 2).toInt()
        )
    }
}

/**
 * Low-level composable for arranging items in a circular/radial pattern.
 * For most use cases, prefer using [CircularMenu] instead.
 *
 * @param modifier The modifier to be applied to the layout
 * @param overrideRadius Function providing the radius for each item by index
 * @param startAngle Starting angle in degrees (0° = right, 90° = bottom, etc.)
 * @param spanAngle How items are distributed around the circle
 * @param clockwise Direction of item placement
 * @param moveUpEnabled If true, layout expands to fit items
 * @param center Center content composable
 * @param contents Items composable
 */
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
