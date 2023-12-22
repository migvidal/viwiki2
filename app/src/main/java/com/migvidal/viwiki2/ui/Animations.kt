package com.migvidal.viwiki2.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.spec.DestinationStyle

object CustomTransitions : DestinationStyle.Animated {
    private val slideOutExitAnimation = slideOutHorizontally(
        targetOffsetX = { 1000 },
        animationSpec = tween(200)
    )
    private val slideInEnterAnimation = slideInHorizontally(
        initialOffsetX = { 1000 },
        animationSpec = tween(200)
    )
    override fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransition(): EnterTransition? {
        return slideInEnterAnimation
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransition(): ExitTransition {
        return slideOutExitAnimation
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransition(): EnterTransition? {
        return slideInEnterAnimation
    }

    override fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransition(): ExitTransition? {
        return slideOutExitAnimation
    }
}