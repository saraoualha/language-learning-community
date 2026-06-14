package com.example.tandem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tandem.ui.theme.LanguageLearningCommunityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LanguageLearningCommunityTheme {
                val viewModel: CommunityViewModel = viewModel(
                    factory = CommunityViewModelFactory(applicationContext)
                )
                CommunityScreen(viewModel = viewModel)
            }
        }
    }
}