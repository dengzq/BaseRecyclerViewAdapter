package com.dengzq.baservadapter.multi.wrapper

import android.view.View
import com.dengzq.baservadapter.BaseRvAdapter

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/30 上午12:08</p>
 * <p>package   com.dengzq.baservadapter.multi.wrapper</p>
 * <p>readMe    Wrapper for no more view</p>
 */
internal class BottomWrapper {

    private var view: View? = null
    private var showBottom: Boolean = true

    fun getBottomCount(): Int = if (view == null || !showBottom) 0 else 1

    fun getBottomViewType(): Int = BaseRvAdapter.BOTTOM_INDEX

    fun getBottomView(): View? = view

    fun addBottomView(view: View?) {
        this.view = view
    }

    fun removeBottomView() {
        this.view = null
    }

    fun isShowBottom(): Boolean {
        return showBottom
    }

    fun showBottom(show: Boolean) {
        this.showBottom = show
    }
}