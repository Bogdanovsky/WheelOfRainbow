package com.bogdanovsky.wheelofrainbow

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import coil.load
import coil.transform.RoundedCornersTransformation
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    lateinit var wheel: WheelOfRainbowView
    lateinit var colorTextView: TextView
    lateinit var imageView: ImageView
    lateinit var resetButton: Button
    lateinit var seekBar: VerticalSeekBar
    lateinit var animator: ValueAnimator
    private var angle = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wheel = findViewById(R.id.wheel)
        colorTextView = findViewById(R.id.color_textview)
        imageView = findViewById(R.id.imageView)
        resetButton = findViewById(R.id.reset)
        seekBar = findViewById(R.id.seekbar)

        wheel.setOnClickListener { view ->
            val initialAngle = angle % 360
            angle = Random.nextInt(360,3600)
            val newDuration = (angle - initialAngle) / 360 * 1000L // millis

            animator = ValueAnimator.ofFloat(initialAngle.toFloat(), angle.toFloat())
            animator.apply {
                duration = newDuration
                addUpdateListener {
                    view.rotation = it.animatedValue as Float
                }
                doOnEnd {
                    onWheelStopAction((angle % 360) / (360f / 7f).toInt())
                }
                start()
            }
        }

        seekBar.setOnSeekBarChangeListener(wheel)

        resetButton.setOnClickListener {
            wheel.clearAnimation()
            colorTextView.apply {
                visibility = View.VISIBLE
                text = "Let's go!"
                setTextColor(Color.BLACK)
            }
            imageView.visibility = View.INVISIBLE
            animator = ValueAnimator.ofFloat(wheel.rotation, 0f)
            animator.apply {
                duration = angle / 360 * 1000L / 5
                addUpdateListener {
                wheel.rotation = it.animatedValue as Float
                }
                animator.start()
            }
        }
    }

    private fun onWheelStopAction(sector: Int) {
        Log.i("SECTOR", sector.toString())
        when (sector) {
            0 -> {
                colorTextView.apply {
                    text = "Violet"
                    visibility = View.VISIBLE
                    setTextColor(Color.rgb(148, 0, 211))
                }
                imageView.visibility = View.INVISIBLE
            }
            1 -> {
                colorTextView.apply {
                    text = "Indigo"
                    visibility = View.VISIBLE
                    setTextColor(Color.rgb(75, 0, 130))
                }
                imageView.visibility = View.INVISIBLE
            }
            4 -> {
                colorTextView.apply {
                    text = "Yellow"
                    visibility = View.VISIBLE
                    setTextColor(Color.rgb(255, 255, 0))
                }
                imageView.visibility = View.INVISIBLE
            }
            6 -> {
                colorTextView.apply {
                    text = "Red"
                    visibility = View.VISIBLE
                    setTextColor(Color.rgb(255, 0 , 0))
                }
                imageView.visibility = View.INVISIBLE
            }
            else -> {
                colorTextView.visibility = View.INVISIBLE
                showImage()
            }
        }
    }

    private fun showImage() {
        try {
            imageView.load("https://loremflickr.com/320/240?" + (0..1_000_000).random()) {
                crossfade(true)
                placeholder(R.drawable.baseline_broken_image_24)
                transformations(
                    RoundedCornersTransformation(8F)
                )
                error(R.drawable.baseline_error_24)
//                transformations(CircleCropTransformation())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show()
        }
        imageView.visibility = View.VISIBLE
    }
}
