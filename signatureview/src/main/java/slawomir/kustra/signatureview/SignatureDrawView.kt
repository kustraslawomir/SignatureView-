package slawomir.kustra.signatureview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

internal class SignatureDrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint()
    private val path = Path()

    init {
        setDefaultPaintStyle(paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setBackgroundColor(WHITE)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun setDefaultPaintStyle(paint: Paint) {
        with(paint) {
            isAntiAlias = true
            strokeWidth = 5f
            color = BLACK
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> path.moveTo(x, y)
            MotionEvent.ACTION_MOVE -> path.lineTo(x, y)
        }

        invalidate()
        return true
    }

    fun clearSignature() {
        path.reset()
        invalidate()
    }
}