package com.dengzq.demo.widget.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.dengzq.demo.R

/**
 * <p>author    dengzq</P>
 * <p>date      2018/10/24 上午10:10</p>
 * <p>package   com.dengzq.demo.widget.decoration</p>
 * <p>readMe    StickyDecoration</p>
 */
class StickyDecoration : RecyclerView.ItemDecoration() {

    var view: View? = null

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = (view?.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition

        if (position % 3 == 0) {
            outRect?.set(20, 20, 20, 20)
        }
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)

//        if (parent == null) return
//
//        val left = parent.paddingLeft
//        val right = parent.width - parent.paddingRight
//        for (i in 0 until parent.childCount) {
//            val child = parent.getChildAt(i)
//            val layoutPosition = (child.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
//            val bottom = child.top
//            if (layoutPosition % 3 == 0) {
//                val paint = Paint().apply {
//                    color = ContextCompat.getColor(child.context, R.color.background)
//                }
//                val textPaint = Paint().apply {
//                    color = ContextCompat.getColor(child.context, R.color.black)
//                    textSize=50f
//                }
//                c?.drawRect(left.toFloat(), bottom - 80f, right.toFloat(), bottom.toFloat(), paint)
//                val text = "这是第{$layoutPosition}个"
//
//                c?.drawText(text, 0, text.length,0f,bottom.toFloat(), textPaint)
//            }
//        }
    }

    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDrawOver(c, parent, state)

        if (parent == null) return

        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val layoutPosition = (child.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
            val bottom = child.top
            if (layoutPosition % 3 == 0) {
                val paint = Paint().apply {
                    color = ContextCompat.getColor(child.context, R.color.background)
                }
                val textPaint = Paint().apply {
                    color = ContextCompat.getColor(child.context, R.color.black)
                    textSize = 50f
                }
                c?.drawRect(left.toFloat(), bottom - 80f, right.toFloat(), bottom.toFloat(), paint)
                val text = "这是第{$layoutPosition}个"
                val rect = Rect()
                textPaint.getTextBounds(text, 0, text.length, rect)
                c?.drawRect(left.toFloat(), 0f, right.toFloat(), 80f, paint)
                c?.drawText(text, 0, text.length, 0f, rect.height().toFloat(), textPaint)

            }
        }
    }
}