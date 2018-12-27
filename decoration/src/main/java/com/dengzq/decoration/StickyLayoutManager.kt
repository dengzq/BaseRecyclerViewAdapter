package com.dengzq.decoration

import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * <p>author    dengzq</P>
 * <p>date      2018/11/14 下午11:03</p>
 * <p>package   com.dengzq.decoration</p>
 * <p>readMe    StickyLayoutManager</p>
 */
class StickyLayoutManager : RecyclerView.LayoutManager() {


    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(-2, -2)
    }

    private var rvTotalHeight = 0//rv总高度

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {

        if (recycler == null) return

        var offsetY = 0

        for (i in 0 until itemCount) {
            val view = recycler.getViewForPosition(i)
            addView(view)

            measureChildWithMargins(view, 0, 0)

            val width = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)

            layoutDecorated(view, 0, offsetY, width, offsetY + height)
            offsetY += height
        }

        rvTotalHeight = offsetY
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    private var scrollDistance = 0

    /**
     * 当return的dy值与scrollVerticalBy传递进来的dy值不一致时，说明
     * 需要进行距离的修正，即惯性滑动;
     * 因此这时候将会出现灰色的阴影;
     */
    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {

        val rvVisibleHeight = height - paddingTop - paddingBottom
        var targetDy = dy

        if (scrollDistance + dy < 0) {//说明到达了上边界,计算距离顶部还有多少的距离;
            targetDy = -scrollDistance
        } else if (rvTotalHeight > rvVisibleHeight && rvVisibleHeight + scrollDistance + dy > rvTotalHeight) {//到达下边界,计算距离底部还有多少距离;
            targetDy = rvTotalHeight - rvVisibleHeight - scrollDistance
        } else {

        }
        scrollDistance += targetDy
        offsetChildrenVertical(-targetDy)

        Log.d(StickyLayoutManager::class.java.simpleName, "dy-->$dy  scrollDistance->$scrollDistance  targetDy->$targetDy")

        return targetDy
    }

}