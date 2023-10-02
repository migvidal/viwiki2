package com.migvidal.viwiki2.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination(start = true)
fun TodayScreen() {
    Text(text = "Today")
}