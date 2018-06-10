package com.dengzq.baservadapter.multi

import android.content.Context
import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.interfaces.ItemViewDelegate

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/17 下午3:11</p>
 * <p>package   com.dengzq.baservadapter.multi</p>
 * <p>readMe    CommonAdapter</p>
 */
abstract class CommonAdapter<T>(context: Context, layoutId: Int, list: List<T>) : MultiItemTypeAdapter<T>(context, list) {

    init {
        addItemViewDelegate(object : ItemViewDelegate<T>() {
            override fun getItemViewLayoutId(): Int = layoutId

            override fun isForViewType(t: T, position: Int): Boolean = true

            override fun convert(holder: BaseViewHolder, t: T, position: Int) {
                this@CommonAdapter.convert(holder, t, position)
            }
        })
    }

    abstract fun convert(holder: BaseViewHolder, t: T, position: Int)

}