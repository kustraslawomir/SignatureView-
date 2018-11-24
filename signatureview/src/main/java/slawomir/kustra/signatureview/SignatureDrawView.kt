package slawomir.kustra.signatureview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.graphics.RectF
import android.R.attr.bottom
import android.R.attr.top
import android.R.attr.right
import android.R.attr.left


internal class SignatureDrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint()
    private val path = Path()
    private val dirtyRect = RectF()
    private var lastTouchX: Float = 0.toFloat()
    private var lastTouchY: Float = 0.toFloat()

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
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                lastTouchX = x
                lastTouchY = y
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                resetDirtyRect(x, y)

                val historySize = event.historySize
                for (i in 0 until historySize) {
                    val historicalX = event.getHistoricalX(i)
                    val historicalY = event.getHistoricalY(i)
                    expandDirtyRect(historicalX, historicalY)
                    path.lineTo(historicalX, historicalY)
                }
                path.lineTo(x,y)
            }
        }

        invalidate()
        lastTouchX = x
        lastTouchY = y
        return true
    }

    private fun resetDirtyRect(x: Float, y: Float) {
        dirtyRect.left = Math.min(lastTouchX, x)
        dirtyRect.right = Math.max(lastTouchX, x)
        dirtyRect.top = Math.min(lastTouchY, y)
        dirtyRect.bottom = Math.max(lastTouchY, y)
    }

    fun clearSignature() {
        path.reset()
        invalidate()
    }

    private fun expandDirtyRect(historicalX: Float, historicalY: Float) {
        if (historicalX < dirtyRect.left) {
            dirtyRect.left = historicalX
        } else if (historicalX > dirtyRect.right) {
            dirtyRect.right = historicalX
        }
        if (historicalY < dirtyRect.top) {
            dirtyRect.top = historicalY
        } else if (historicalY > dirtyRect.bottom) {
            dirtyRect.bottom = historicalY
        }
    }

}