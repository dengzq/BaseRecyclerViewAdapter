package com.dengzq.decoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * <p>author    dengzq</P>
 * <p>date      2018/11/7 下午11:52</p>
 * <p>package   com.dengzq.decoration</p>
 * <p>readMe    LinearDecoration</p>
 */
class LinearDecoration @JvmOverloads constructor(private val size: Float, @RecyclerView.Orientation private val orientation: Int = RecyclerView.VERTICAL) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {

        val position = (view?.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        parent?.let {

            val itemCount = it.layoutManager.itemCount

            if (RecyclerView.VERTICAL == orientation) {

                if (position != itemCount - 1)
                    outRect?.set(0, 0, 0, dp2px(it.context, size))
            } else {
                if (position != itemCount - 1)
                    outRect?.set(0, 0, dp2px(it.context, size), 0)
            }
        }
    }
}