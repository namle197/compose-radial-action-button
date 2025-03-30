package com.namle197.radialactionbutton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.namle197.radial_action_button.CircularAnimation
import com.namle197.radial_action_button.CircularMenu
import com.namle197.radial_action_button.CircularDirection
import com.namle197.radial_action_button.CircularSpan
import com.namle197.radialactionbutton.ui.theme.RadialactionbuttonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RadialactionbuttonTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        var expanded by remember { mutableStateOf(false) }

                        CircularMenu(
                            centerContent = {
                                GradientFloatingActionButton(
                                    modifier = Modifier.shadow(elevation = 6.dp,
                                        shape = CircleShape,
                                        clip = false),
                                    onClick = {
                                        expanded = !expanded
                                    }
                                )
                            },
                            radius = 150f,
                            expanded = expanded,
                            span = CircularSpan.Circle.create(),
                            startAngle = 270f,
                            clockwise = CircularDirection.CounterClockwise,
                            animation = CircularAnimation.StaggerAnimation(),
                            moveUpEnabled = true,
                            menuItemCount = 10,
                            content = {
                                repeat(10) {
                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .shadow(
                                                elevation = 1.dp,
                                                shape = CircleShape
                                            )
                                            .background(
                                                brush = Brush.linearGradient(
                                                    colors = listOf(Color(0xFF40E495), Color(0xFF2BB673)),
                                                    start = Offset(0f, 0f),
                                                    end = Offset.Infinite
                                                )
                                            )
                                            .clickable {
                                                expanded = !expanded
                                            }
                                        ,
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = (it + 1).toString())
                                    }
                                }
                            }
                        )
                    },
                    floatingActionButtonPosition = FabPosition.EndOverlay

                ) { innerPadding ->
                    FancyCreditText(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun FancyCreditText(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = Color(0xFF171515),
        targetValue = Color(0xFF00ACC1),
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Text(
        text = "This is from namle197. If you find it useful, don't hesitate to give me 1 star :D",
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xff4fc3f7), Color(0xff00b8d4))
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        style = TextStyle(
            color = color,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            shadow = Shadow(
                color = Color.Black.copy(alpha = 0.3f),
                offset = Offset(2f, 2f),
                blurRadius = 4f
            ),
            letterSpacing = 0.2.sp
        ),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun GradientFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFF89D8D3), // #89d8d3
                    Color(0xFF03C8A8)  // #03c8a8
                )
            ))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            tint = Color.White
        )
    }
}

