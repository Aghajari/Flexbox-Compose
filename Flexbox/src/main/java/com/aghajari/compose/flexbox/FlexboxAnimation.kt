package com.aghajari.compose.flexbox

import android.animation.TypeEvaluator
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.IntOffset

/**
 * A [FlexboxAnimation] customizes the animation of each item movements in [Flexbox].
 * Whenever a new item is added or an item is removed that causes the position of
 * the items placed in the [Flexbox] to change, this change will be accompanied by
 * an animation that you can customize with [FlexboxAnimation].
 * You can disable it by passing null to the [Flexbox] animation parameter.
 */
@Stable
interface FlexboxAnimation {

    /**
     * Threshold at which the animation may round off to its target value.
     */
    val visibilityThreshold: Float

    /**
     * The animation will use the provided animationSpec to animate the value towards the targetValue.
     */
    val animationSpec: AnimationSpec<Float>

    /**
     * This evaluator calculates the result of interpolating the start and end values, with
     * fraction representing the proportion between the start and end values.
     * Used [IntOffsetEvaluator] by default to return the result of linearly interpolating.
     */
    val positionEvaluator: TypeEvaluator<IntOffset>
        get() = IntOffsetEvaluator
}

/**
 * [DefaultFlexboxAnimation] enables the default movement animation on [Flexbox].
 * A [spring] will be used for [animationSpec] and [Spring.DefaultDisplacementThreshold]
 * will be set as [visibilityThreshold], the default cutoff for rounding off physics based animations.
 */
@Stable
internal class DefaultFlexboxAnimation : FlexboxAnimation {

    override val visibilityThreshold =
        Spring.DefaultDisplacementThreshold

    override val animationSpec =
        SpringSpec(visibilityThreshold = visibilityThreshold)
}

/**
 * This evaluator can be used to perform type interpolation between [IntOffset] values.
 */
@Stable
object IntOffsetEvaluator : TypeEvaluator<IntOffset> {

    override fun evaluate(
        fraction: Float,
        startValue: IntOffset,
        endValue: IntOffset
    ) = IntOffset(
        x = (startValue.x + fraction * (endValue.x - startValue.x)).toInt(),
        y = (startValue.y + fraction * (endValue.y - startValue.y)).toInt()
    )
}

internal fun FlexboxAnimation.evaluate(
    fraction: Float,
    startValue: IntOffset,
    endValue: IntOffset
) = positionEvaluator.evaluate(fraction, startValue, endValue)