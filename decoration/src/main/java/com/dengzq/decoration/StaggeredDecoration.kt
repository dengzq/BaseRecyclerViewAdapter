package com.dengzq.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * <p>author    dengzq</P>
 * <p>date      2018/11/9 下午3:29</p>
 * <p>package   com.dengzq.decoration</p>
 * <p>readMe    StaggeredDecoration</p>
 */
class StaggeredDecoration : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)
    }

    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDrawOver(c, parent, state)
    }
}