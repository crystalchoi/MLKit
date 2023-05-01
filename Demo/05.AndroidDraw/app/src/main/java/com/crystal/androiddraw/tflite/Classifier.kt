package com.crystal.androiddraw.tflite

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.util.Log
import android.widget.Button
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.Tensor
import java.io.FileInputStream
import java.io.IOException
import java.lang.Exception
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel


private const val  TAG = "Classifier"

class Classifier(private val assetManager: AssetManager, private val modelFileName: String = "") {

    private lateinit var interpreter: Interpreter
    private var modelInputWidth:Int = 0
    private var modelInputHeight:Int = 0
    private var modelInputChannel:Int = 0

    init {
        val model = loadModelFile()
        model?.order(ByteOrder.nativeOrder())
        interpreter = Interpreter(model!!)

        initModelShape()
    }

    private fun initModelShape() {
        val inputTensor: Tensor = interpreter.getInputTensor(0)
        val inputShape = inputTensor.shape()
        modelInputChannel = inputShape[0]
        modelInputWidth = inputShape[1]
        modelInputHeight = inputShape[2]
    }


    private fun resizeBitmap(bitmap: Bitmap) : Bitmap {
        return Bitmap.createScaledBitmap(bitmap, modelInputWidth, modelInputHeight, false)
    }

    private fun loadModelFile() : ByteBuffer? {

        val assetFd  = try {
            val fileName  = modelFileName.ifEmpty { MODEL_NAME }
            assetManager.openFd(fileName )
        } catch (e: IOException) {
            return null
        }

        val fileInputStream = try {
            FileInputStream(assetFd.fileDescriptor)
        } catch (e: IOException) {
            return null
        }

        val fileChannel = try {
            fileInputStream.channel
        } catch  (e: IOException) {
            return null
        }

        val startOffset = assetFd.startOffset
        val declaredLength = assetFd.declaredLength

        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    companion object  {
        private const val  MODEL_NAME = "keras_model.tflite"

    }
}