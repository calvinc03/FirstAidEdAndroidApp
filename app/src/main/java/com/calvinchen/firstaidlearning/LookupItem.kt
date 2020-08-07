package com.calvinchen.firstaidlearning

import android.graphics.drawable.Drawable

data class LookupItem(
    val imageResource: Drawable,
    val title: String,
    val category: String
)