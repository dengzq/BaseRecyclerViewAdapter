package com.dengzq.decoration

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * <p>author    dengzq</P>
 * <p>date      2018/11/9 上午10:31</p>
 * <p>package   com.dengzq.decoration</p>
 * <p>readMe    GridDecoration</p>
 */
class GridDecoration(private val size: Float) : RecyclerView.ItemDecoration() {

    companion object {
        const val ITEM_START = "item_start"
        const val ITEM_MIDDLE = "item_middle"
        const val ITEM_END = "item_end"
        const val ITEM_SINGLE = "item_single"//单个成行
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {

        val layoutManager = parent?.layoutManager
        val position = (view?.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition

        if (layoutManager != null && layoutManager is GridLayoutManager) {
            var totalSpanSize = 0

            for (i in 0 until position) {
                totalSpanSize += layoutManager.spanSizeLookup.getSpanSize(i)
            }

            val spanCount = layoutManager.spanCount
            val spanSize = layoutManager.spanSizeLookup.getSpanSize(position)

            val positionInRow = when {
                spanCount == spanSize -> ITEM_SINGLE
                totalSpanSize % spanCount == 0 -> ITEM_START
                (totalSpanSize + spanSize) % spanCount == 0 -> ITEM_END
                else -> ITEM_MIDDLE
            }

            val context = parent.context
            when (positionInRow) {
                ITEM_START ->
                    outRect?.set(dp2px(context, size), 0, dp2px(context, size / 2), dp2px(context, size))
                ITEM_MIDDLE ->
                    outRect?.set(dp2px(context, size / 2), 0, dp2px(context, size / 2), dp2px(context, size))
                ITEM_END ->
                    outRect?.set(dp2px(context, size / 2), 0, dp2px(context, size), dp2px(context, size))
                ITEM_SINGLE ->
                    outRect?.set(dp2px(context, size), 0, dp2px(context, size), dp2px(context, size))
            }
        }

    }
}
