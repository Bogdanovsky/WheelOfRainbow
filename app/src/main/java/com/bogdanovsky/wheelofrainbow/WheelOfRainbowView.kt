package com.bogdanovsky.wheelofrainbow

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.SeekBar

class WheelOfRainbowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr), SeekBar.OnSeekBarChangeListener {

    private val n = 7 // кол-во цветов
    private var padding = 0
    private var centreX = 0f
    private var centreY = 0f
    private var minDimension = 0f
    private var radius = 0f
    private val rect = RectF()
    private var scale = 0.5f
     var pivot = 0f
    private val paint = Paint()


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        centreX = width / 2f
        centreY = height / 2f
        minDimension = centreX.coerceAtMost(centreY)

        padding = (minDimension / 6).toInt()
        radius = (minDimension - padding) * (0.6f + scale / 2)

        rect.top = centreY - radius
        rect.left = centreX - radius
        rect.right = centreX + radius
        rect.bottom = centreY + radius
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return
        //drawCircle300(canvas)
        paint.reset()
        paint.apply {
            strokeWidth = 3f
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        var degree = 360f / n
        var currentAngle = pivot
        for (i in 0 until 7) {
            if (i == 0)
                paint.color = Color.RED
            if (i == 1)
                paint.color = Color.rgb(255, 127, 0)
            if (i == 2)
                paint.color = Color.YELLOW
            if (i == 3)
                paint.color = Color.GREEN
            if (i == 4)
                paint.color = Color.BLUE
            if (i == 5)
                paint.color = Color.rgb(75, 0, 130)
            if (i == 6)
                paint.color = Color.rgb(148, 0, 211)

            canvas.drawArc(rect, currentAngle, degree, true, paint)
            currentAngle += degree
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {
        scale = (seekBar?.progress ?: 50) / 100f
        requestLayout()
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }
//
//    fun setPivot(value: Float) {
//        pivot = value
//    }

//    fun setScale(value: Float) {
//        scale = value
//    }
}