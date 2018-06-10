@file:Suppress("MemberVisibilityCanPrivate")

package com.dengzq.baservadapter.multi

import android.content.Context
import android.view.ViewGroup
import com.dengzq.baservadapter.BaseRvAdapter
import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.interfaces.ItemClassifyDelegate
import com.dengzq.baservadapter.manager.ItemClassifyDelegateManager

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/17 下午12:36</p>
 * <p>package   com.dengzq.baservadapter.multi</p>
 * <p>readMe    MultiItemClassifyAdapter</p>
 */
abstract class MultiItemClassifyAdapter(context: Context) : BaseRvAdapter(context) {
    private val delegateManager = ItemClassifyDelegateManager()

    override fun getRealItemCount(): Int = delegateManager.getItemCount()

    override fun getRealViewType(position: Int): Int = delegateManager.getItemViewType(position)

    override fun createRealHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutId = delegateManager.getItemClassifyDelegate(viewType).getItemViewLayoutId()
        return BaseViewHolder.createViewHolder(context, parent, layoutId)
    }

    override fun bindRealHolder(holder: BaseViewHolder, position: Int) {
        delegateManager.convert(holder, position)
    }

    private fun isUseItemClassifyManager(): Boolean = delegateManager.getDelegateCount() > 0

    fun addItemClassifyDelegate(delegate: ItemClassifyDelegate) {
        delegateManager.addDelegate(delegate)
    }

    override fun getRealSpanSize(position: Int): Int {
        val delegate = delegateManager.getItemClassifyDelegate(getRealViewType(position))
        return delegate.getItemSpanSize(position)
    }
}