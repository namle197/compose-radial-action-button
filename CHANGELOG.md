# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased] - Optimized Branch

### Added
- Comprehensive KDoc documentation for all public APIs
- Improved README with clearer feature descriptions
- Better error messages in require() statements

### Changed
- **BREAKING**: `CircularSpan.Circle.create()` is now `CircularSpan.Circle` (data object instead of factory method)
- **BREAKING**: `CircularMenu` no longer requires `menuItemCount` parameter - it's automatically calculated from content
- **BREAKING**: `CircularAnimation.betweenAnimation` renamed to `staggerDelay` for better clarity
- `ExpandAnimation` now has `staggerDelay = 0L` (was 50L) since it doesn't use staggering
- Improved performance by using `maxOfOrNull` instead of `maxOf` with null fallback
- Fixed bug in `RadialLayout` where height was constrained using `constrainWidth` instead of `constrainHeight`

### Performance Improvements
- Optimized layout calculations by caching max dimensions
- Reduced unnecessary allocations in measure policy
- More efficient radius calculations

### Developer Experience
- Clearer parameter names (`staggerDelay` instead of `betweenAnimation`)
- Comprehensive documentation with usage examples
- Better code organization and readability

## Migration Guide

### CircularSpan.Circle
**Before:**
```kotlin
span = CircularSpan.Circle.create()
```

**After:**
```kotlin
span = CircularSpan.Circle
```

### CircularMenu
**Before:**
```kotlin
CircularMenu(
    menuItemCount = 10,
    // ... other parameters
    content = {
        repeat(10) { /* item */ }
    }
)
```

**After:**
```kotlin
CircularMenu(
    // menuItemCount removed - automatically calculated
    // ... other parameters
    content = {
        repeat(10) { /* item */ }
    }
)
```

### CircularAnimation (if using custom animations)
**Before:**
```kotlin
CircularAnimation.StaggerAnimation(
    betweenAnimation = 100L
)
```

**After:**
```kotlin
CircularAnimation.StaggerAnimation(
    staggerDelay = 100L
)
```
