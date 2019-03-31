package com.dengzq.baservadapter.interfaces

import android.util.Log
import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.R

/**
 * <p>author    dengzq</P>
 * <p>date      2018/12/28 13:46</p>
 * <p>package   com.dengzq.baservadapter.interfaces</p>
 * <p>readMe    DefaultDelegates</p>
 */

class ItemNullDelegate : ItemViewDelegate<Any>() {
    override fun getItemViewLayoutId(): Int = R.layout.layout_delegate_default
    override fun isForViewType(t: Any, position: Int): Boolean = true
    override fun getItemSpanSize(position: Int, spanCount: Int): Int = -2
    override fun convert(holder: BaseViewHolder, t: Any, position: Int) {
        Log.e("ItemNullDelegate", "ItemNullDelegate has been call which means " +
                "there is itemType that can't be match, position : $position")
    }
}