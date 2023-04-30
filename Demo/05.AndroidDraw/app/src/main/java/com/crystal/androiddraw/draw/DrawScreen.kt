package com.crystal.androiddraw.draw

import android.annotation.SuppressLint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.viewbinding.ViewBinding
import com.crystal.androiddraw.R
import com.crystal.androiddraw.databinding.ConstraintlayoutDrawBinding
import com.divyanshu.draw.activity.DrawingActivity
import com.divyanshu.draw.widget.DrawView

@SuppressLint("ResourceAsColor")
@Composable
fun AndroidViewDraw() {
    Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.primaryContainer
    ) {


//        @Composable
//        fun <T : ViewBinding> AndroidViewBinding(
//            factory: (inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean) -> T,
//            modifier: Modifier = Modifier,
//            update: T.() -> Unit = {}
//        ): Unit

        AndroidViewBinding(ConstraintlayoutDrawBinding::inflate,
            modifier = Modifier.fillMaxSize()) {
            drawView.apply {
                setBackgroundColor(Color.Black.value.toInt())
                setStrokeWidth(50.0f)
                setColor(Color.White.value.toInt())
            }

            classifyBtn.apply {
                setOnClickListener {
                    drawView.getBitmap()
                }
            }

            clearBtn.apply {
                setOnClickListener {
                    drawView.clearCanvas();
                }
            }
        }
    }
}