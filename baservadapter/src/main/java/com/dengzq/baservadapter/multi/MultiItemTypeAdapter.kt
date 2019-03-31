@file:Suppress("MemberVisibilityCanPrivate")

package com.dengzq.baservadapter.multi

import android.content.Context
import android.view.ViewGroup
import com.dengzq.baservadapter.BaseRvAdapter
import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.interfaces.ItemViewDelegate
import com.dengzq.baservadapter.manager.ItemViewDelegateManager

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/13 下午3:28</p>
 * <p>package   com.dengzq.baservadapter.multi</p>
 * <p>readMe    MultiItemTypeAdapter</p>
 */
abstract class MultiItemTypeAdapter<T>(context: Context, val list: List<T>) : BaseRvAdapter(context) {
    private val delegateManager: ItemViewDelegateManager<T> = ItemViewDelegateManager()

    private fun checkValid(position: Int): Boolean = position < list.size

    override fun getRealItemCount(): Int = list.size

    override fun getRealViewType(position: Int): Int {
        if (checkValid(position))
            return delegateManager.getItemViewType(list[position], position)
        return -1
    }

    override fun createRealHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val delegate = delegateManager.getItemViewDelegate(viewType)
        return BaseViewHolder.createViewHolder(context, parent, delegate.getItemViewLayoutId())
    }

    override fun bindRealHolder(holder: BaseViewHolder, position: Int) {
        if (checkValid(position)) {
            delegateManager.convert(holder, list[position], position)
        }
    }

    fun addItemViewDelegate(delegate: ItemViewDelegate<T>) {
        delegateManager.addDelegate(delegate)
    }

    private fun isUseDelegateManager(): Boolean = delegateManager.getDelegateCount() > 0

    override fun getRealSpanSize(position: Int, spanCount: Int): Int {
        val delegate = delegateManager.getItemViewDelegate(getRealViewType(position))
        return delegate.getItemSpanSize(position, spanCount)
    }

    override fun onDelegateViewAttachedToWindow(position: Int, holder: BaseViewHolder) {
        if (checkValid(position)) {
            delegateManager.onDelegateViewAttachedToWindow(position, list[position], holder)
        }
    }

    override fun onDelegateViewDetachedFromWindow(position: Int, holder: BaseViewHolder) {
        if (checkValid(position)) {
            delegateManager.onDelegateViewDetachedFromWindow(position, list[position], holder)
        }
    }

    override fun onDelegateFailedToRecycleView(position: Int, holder: BaseViewHolder): Boolean {
        if (checkValid(position)) {
            return delegateManager.onDelegateFailedToRecycleView(position, list[position], holder)
        }
        return false
    }

    override fun onDelegateViewRecycled(position: Int, holder: BaseViewHolder) {
        if (checkValid(position)) {
            delegateManager.onDelegateViewRecycled(position, list[position], holder)
        }
    }
}

