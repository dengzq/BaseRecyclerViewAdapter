package com.dengzq.baservadapter.interfaces

import com.dengzq.baservadapter.BaseViewHolder

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/13 下午3:16</p>
 * <p>package   com.dengzq.baservadapter.base</p>
 * <p>readMe    ItemViewDelegate</p>
 */
abstract class ItemViewDelegate<in T> : IDelegate {

    abstract fun getItemViewLayoutId(): Int

    abstract fun isForViewType(t: T, position: Int): Boolean

    abstract fun convert(holder: BaseViewHolder, t: T, position: Int)

    override fun getItemSpanSize(position: Int, spanCount: Int): Int = -1

    override fun onDelegateViewAttachedToWindow(position: Int, holder: BaseViewHolder) {}

    override fun onDelegateViewDetachedFromWindow(position: Int, holder: BaseViewHolder) {}

    override fun onDelegateFailedToRecycleView(position: Int, holder: BaseViewHolder): Boolean {
        return super.onDelegateFailedToRecycleView(position, holder)
    }

    override fun onDelegateViewRecycled(position: Int, holder: BaseViewHolder) {}
}