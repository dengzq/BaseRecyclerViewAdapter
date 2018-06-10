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

    override fun getItemSpanSize(position: Int): Int=-1
}