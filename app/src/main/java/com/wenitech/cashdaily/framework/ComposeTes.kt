package com.wenitech.cashdaily.framework

import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class ComposeTes : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {

        }
    }

    @Composable
    fun MainContent() {
        Scaffold(
            topBar = { MyTopBar() },

        ) {
            Content()
        }
    }

    @Composable
    fun MyTopBar() {
        TopAppBar(title = { Text(text = "Home Activity") })
    }

    @Composable
    fun Content(){
        Text(text = "Hello word")
    }

    @Preview(uiMode = UI_MODE_NIGHT_MASK)
    @Composable
    fun PreviewText() {
        MainContent()
    }
}