package com.dengzq.baservadapter.interfaces

import com.dengzq.baservadapter.BaseViewHolder

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/17 下午12:32</p>
 * <p>package   com.dengzq.baservadapter.interfaces</p>
 * <p>readMe    ItemClassifyDelegate</p>
 */
abstract class ItemClassifyDelegate : IDelegate {

    abstract fun getItemSize(): Int

    abstract fun needShow(): Boolean

    abstract fun getItemViewLayoutId(): Int

    abstract fun convert(holder: BaseViewHolder, position: Int)

    override fun getItemSpanSize(position: Int,spanCount:Int): Int = -1
}