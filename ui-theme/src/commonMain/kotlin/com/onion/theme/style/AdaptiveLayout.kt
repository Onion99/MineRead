/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT
 */

package com.onion.theme.style

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.window.core.layout.WindowWidthSizeClass
import com.onion.theme.state.AdaptiveLayoutType
import com.onion.theme.state.ContentType

@Composable
fun AdaptiveLayout(
    adaptiveLayoutType: MutableState<AdaptiveLayoutType>,
    contentType: MutableState<ContentType>
) {
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val windowSizeClass = adaptiveInfo.windowSizeClass.windowWidthSizeClass
    when (windowSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            adaptiveLayoutType.value = AdaptiveLayoutType.Compact
            contentType.value = ContentType.Single
        }

        WindowWidthSizeClass.MEDIUM -> {
            adaptiveLayoutType.value = AdaptiveLayoutType.Medium
            contentType.value = ContentType.Single
        }

        WindowWidthSizeClass.EXPANDED -> {
            adaptiveLayoutType.value = AdaptiveLayoutType.Expanded
            contentType.value = ContentType.Dual
        }

        else -> {
            adaptiveLayoutType.value = AdaptiveLayoutType.Compact
            contentType.value = ContentType.Single
        }
    }
}