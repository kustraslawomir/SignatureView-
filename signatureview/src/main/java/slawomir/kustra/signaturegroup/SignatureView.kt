package slawomir.kustra.signaturegroup

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import slawomir.kustra.signatureview.SignatureDrawView
import slawomir.kustra.signatureview.R
import slawomir.kustra.utils.Utils

class SignatureView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val signatureView: SignatureDrawView = SignatureDrawView(context, attrs)

    private val eraserView = getEraser(context)

    init {
        addView(signatureView)
        addView(eraserView)

        setEraserClickListener()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    private fun setEraserClickListener() {
        eraserView.setOnClickListener {
            signatureView.clearSignature()
        }
    }

    private fun getEraser(context: Context): ImageView {
        val eraser = ImageView(context)
        eraser.setImageResource(R.drawable.ic_eraser)
        val params = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.setMargins(0, Utils.pxFromDp(context, 20f).toInt(), Utils.pxFromDp(context, 20f).toInt(), 0)
        params.gravity = Gravity.END
        eraser.layoutParams = params
        eraser.scaleType = ImageView.ScaleType.CENTER
        return eraser
    }
}