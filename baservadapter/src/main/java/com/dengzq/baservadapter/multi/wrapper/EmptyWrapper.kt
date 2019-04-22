package com.dengzq.baservadapter.multi.wrapper

import android.view.View
import android.view.ViewGroup
import com.dengzq.baservadapter.BaseRvAdapter

/**
 * <p>author    dengzq</P>
 * <p>date      2019/4/1 23:26</p>
 * <p>package   com.dengzq.baservadapter.multi.wrapper</p>
 * <p>readMe    EmptyWrapper.</p>
 */
internal class EmptyWrapper {

    private var emptyView: View? = null
    var showEmpty: Boolean = true

    fun setEmptyView(view: View?) {
        if (this.emptyView != view && view != null) {

            val params = view.layoutParams?.apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
            } ?: ViewGroup.LayoutParams(-1, -1)
            view.layoutParams = params

            emptyView = view
        }
    }

    fun removeEmptyView() {
        this.emptyView = null
    }

    fun getEmptyViewType(): Int = BaseRvAdapter.EMPTY_INDEX
    fun getEmptyView(): View? = emptyView
    fun getEmptyCount(): Int = if (emptyView != null && showEmpty) 1 else 0
}