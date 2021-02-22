package com.ibashkimi.appinfo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.ibashkimi.appinfo.ui.MyApp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }

    override fun onBackPressed() {
        if (!Navigation.pop())
            super.onBackPressed()
    }
}
