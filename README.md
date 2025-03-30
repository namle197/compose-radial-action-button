# Radial Action Button for Jetpack Compose

[![Maven Central](https://img.shields.io/maven-central/v/io.github.namle197/compose-radial-action-button)](https://search.maven.org/artifact/io.github.namle197/compose-radial-action-button)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

A customizable radial action button menu for Android Jetpack Compose.

## Installation

Add to your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.github.namle197:compose-radial-action-button:0.1.0")
}
```
## Features
- Circular layout.
- Customize the number of items / style.
- Change animations.
- Turn on/off stabilized center

## Showcases
<img src="docs/images/full_circle_move_up.gif"/>

```
expanded = expanded,
span = CircularSpan.Circle.create(),
startAngle = 180f,
clockwise = CircularDirection.Clockwise,
animation = CircularAnimation.StaggerAnimation(),
moveUpEnabled = true,
```
<img src="docs/images/half_circle_move_up.gif"/>

```
span = CircularSpan.Angle(180f),
startAngle = 180f,
clockwise = CircularDirection.CounterClockwise,
```

<img src="docs/images/half_expand_animation.gif"/>

```
span = CircularSpan.Angle(180f),
startAngle = 180f,
clockwise = CircularDirection.CounterClockwise,
animation = CircularAnimation.ExpandAnimation()
```

<img src="docs/images/half_stabilized.gif"/>

```
span = CircularSpan.Angle(180f),
startAngle = 180f,
clockwise = CircularDirection.CounterClockwise,
animation = CircularAnimation.ExpandAnimation(),
moveUpEnabled = false,
```

<img src="docs/images/quarter_stablized.gif"/>

```
span = CircularSpan.Angle(90f),
startAngle = 180f,
clockwise = CircularDirection.Clockwise,
animation = CircularAnimation.ExpandAnimation(),
moveUpEnabled = false,
```