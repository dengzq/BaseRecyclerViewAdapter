package com.dengzq.baservadapter.manager

import android.support.v4.util.SparseArrayCompat
import com.dengzq.baservadapter.BaseRvAdapter
import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.interfaces.ItemViewDelegate

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/13 下午3:20</p>
 * <p>package   com.dengzq.baservadapter.base</p>
 * <p>readMe    ItemViewDelegateManager</p>
 */
internal class ItemViewDelegateManager<T> {

    private val delegates: SparseArrayCompat<ItemViewDelegate<T>> = SparseArrayCompat()

    fun getItemViewType(t: T, position: Int): Int {
        for (i in 0 until delegates.size()) {
            val delegate = delegates.valueAt(i)
            if (delegate.isForViewType(t, position))
                return delegates.keyAt(i)
        }
        throw IllegalArgumentException("No ItemDelegate added that matches position==$position")
    }

    fun addDelegate(delegate: ItemViewDelegate<T>): ItemViewDelegateManager<T> {
        return addDelegate(delegates.size() + BaseRvAdapter.REALER_INDEX, delegate)
    }

    private fun addDelegate(viewType: Int, delegate: ItemViewDelegate<T>): ItemViewDelegateManager<T> {
        if (delegates.get(viewType) != null) {
            throw IllegalArgumentException("An ItemViewDelegate is already registered for viewType : $viewType")
        }

        delegates.put(viewType, delegate)
        return this
    }

    fun removeDelegate(itemType: Int) {
        if (delegates.get(itemType) != null) delegates.remove(itemType)
    }

    fun convert(holder: BaseViewHolder, t: T, position: Int) {
        for (i in 0..delegates.size()) {

            val delegate = delegates.valueAt(i)

            if (delegate.isForViewType(t, position)) {
                delegate.convert(holder, t, position)
                return
            }
        }
        throw IllegalArgumentException("No ItemViewDelegate added that matches position==$position")
    }

    fun getItemViewDelegate(viewType: Int): ItemViewDelegate<T> = delegates.get(viewType)

    fun getDelegateCount(): Int = delegates.size()

    fun getItemViewLayoutId(viewType: Int): Int = delegates.get(viewType).getItemViewLayoutId()

    fun getItemViewType(delegate: ItemViewDelegate<T>): Int = delegates.indexOfValue(delegate)
}