package com.vero.justmakefunwithui.components.weightpicker

sealed interface LineType {
    object Normal : LineType
    object FiveStep : LineType
    object TenStep : LineType
}