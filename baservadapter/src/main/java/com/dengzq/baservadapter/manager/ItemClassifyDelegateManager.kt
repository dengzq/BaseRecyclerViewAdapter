package com.dengzq.baservadapter.manager

import android.support.v4.util.SparseArrayCompat
import com.dengzq.baservadapter.BaseRvAdapter
import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.interfaces.ItemClassifyDelegate

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/17 下午12:34</p>
 * <p>package   com.dengzq.baservadapter.manager</p>
 * <p>readMe    ItemClassifyDelegateManager</p>
 */
internal class ItemClassifyDelegateManager {

    private val delegates: SparseArrayCompat<ItemClassifyDelegate> = SparseArrayCompat()

    fun addDelegate(itemClassifyDelegate: ItemClassifyDelegate) {
        addDelegate(delegates.size() + BaseRvAdapter.REALER_INDEX, itemClassifyDelegate)
    }

    private fun addDelegate(viewType: Int, itemClassifyDelegate: ItemClassifyDelegate) {
        if (delegates.get(viewType) != null) {
            throw IllegalArgumentException("An ItemClassifyDelegate is registered for viewType:$viewType")
        }
        delegates.put(viewType, itemClassifyDelegate)
    }

    fun removeDelegate(viewType: Int) {
        if (delegates.get(viewType) != null) delegates.remove(viewType)
    }

    fun getItemClassifyDelegate(viewType: Int): ItemClassifyDelegate {
        return delegates.get(viewType)
    }

    fun getItemViewType(position: Int): Int {
        var p = position
        for (i in 0..delegates.size()) {
            val delegate = delegates.valueAt(i)
            if (delegate.needShow()) {
                if (p < delegate.getItemSize())
                    return i + BaseRvAdapter.REALER_INDEX
                else
                    p -= delegate.getItemSize()
            }
        }
        return 0
    }

    fun convert(holder: BaseViewHolder, position: Int) {
        var p = position
        (0 until delegates.size())
                .asSequence()
                .map { delegates.valueAt(it) }
                .filter { it.needShow() }
                .forEach {
                    if (p < it.getItemSize()) {
                        it.convert(holder, p)
                        return
                    } else
                        p -= it.getItemSize()
                }
    }

    fun getDelegateLayoutId(viewType: Int): Int = delegates.get(viewType).getItemViewLayoutId()

    fun getItemCount(): Int = (0 until delegates.size())
            .map { delegates.valueAt(it) }
            .filter { it.needShow() }
            .sumBy { it.getItemSize() }

    fun getDelegateCount(): Int = delegates.size()
}