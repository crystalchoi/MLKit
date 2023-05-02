package com.crystal.androiddraw.draw

import android.annotation.SuppressLint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.viewbinding.ViewBinding
import com.crystal.androiddraw.R
import com.crystal.androiddraw.databinding.ConstraintlayoutDrawBinding
import com.crystal.androiddraw.databinding.DrawViewBinding
import com.crystal.androiddraw.tflite.Classifier
import com.divyanshu.draw.activity.DrawingActivity
import com.divyanshu.draw.widget.DrawView
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlin.jvm.internal.Intrinsics

@SuppressLint("ResourceAsColor")
@Preview(showBackground = true)
@Composable
fun AndroidDrawView(viewModel: DrawViewModel = DrawViewModel(Classifier(LocalContext.current.assets))) {

    lateinit var drawViewForDigit: DrawView

    ConstraintLayout(modifier = Modifier//.height(IntrinsicSize.Max)
        .wrapContentSize(Alignment.TopCenter)
    ) {
        val (drawViewRef, button, textResult) = createRefs()

        Column(modifier = Modifier.height(IntrinsicSize.Max)
            .constrainAs(drawViewRef) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(button.top, 16.dp)
        }
        ) {
            AndroidViewBinding(
                DrawViewBinding::inflate,
                modifier = Modifier.fillMaxSize().offset(x = 0.dp, y = 0.dp)
            ) {
                drawView.apply {
                    setBackgroundColor(R.color.black)
                    setStrokeWidth(50.0f)
                    setColor(R.color.white)
                }
                drawViewForDigit = drawView

//            classifyBtn.apply {
//                setOnClickListener {
//                    drawView.getBitmap()
//                }
//            }
//            clearBtn.apply {
//                setOnClickListener {
//                    drawView.clearCanvas();
//                }
//            }
            }
        }

        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().constrainAs(button) {
            top.linkTo(drawViewRef.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(textResult.top)
        }) {
            Button(onClick = {  drawViewForDigit.getBitmap() },
                modifier = Modifier) {
                Text("Classify")
            }
            Button(onClick = { drawViewForDigit.clearCanvas() },
                modifier = Modifier) {
                Text("Clear")
            }
        }

        Text(text = viewModel.result.value,
            modifier = Modifier.height(IntrinsicSize.Max).constrainAs(textResult) {
                top.linkTo(button.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            })
    }
}