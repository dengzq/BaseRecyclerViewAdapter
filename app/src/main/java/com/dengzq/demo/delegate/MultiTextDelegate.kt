package com.dengzq.demo.delegate

import android.util.Log
import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.interfaces.ItemViewDelegate
import com.dengzq.demo.R
import com.dengzq.demo.model.ModelBean

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/18 下午4:04</p>
 * <p>package   com.dengzq.demo.model</p>
 * <p>readMe    TODO</p>
 */
class MultiTextDelegate : ItemViewDelegate<ModelBean>() {
    override fun getItemViewLayoutId(): Int = R.layout.item_adapter_text

    override fun isForViewType(t: ModelBean, position: Int): Boolean = t.type == 1

    override fun convert(holder: BaseViewHolder, t: ModelBean, position: Int) {
        holder.setText(R.id.tv_text, t.nick + " || Most parents genuinely do their best to provide their children with a happy and healthy upbringing, but even these individuals can accidentally make mistakes that may result in future therapy appointments.")
    }

    override fun getItemSpanSize(position: Int, spanCount: Int): Int = 2

    override fun onDelegateFailedToRecycleView(position: Int, holder: BaseViewHolder): Boolean {
        Log.d("MultiTextDelegate", "MultiTextDelegate onDelegateFailedToRecycleView => $position")
        return super.onDelegateFailedToRecycleView(position, holder)
    }

    override fun onDelegateViewAttachedToWindow(position: Int, holder: BaseViewHolder) {
        super.onDelegateViewAttachedToWindow(position, holder)
        Log.d("MultiTextDelegate", "MultiTextDelegate onDelegateViewAttachedToWindow => $position")
    }

    override fun onDelegateViewDetachedFromWindow(position: Int, holder: BaseViewHolder) {
        super.onDelegateViewDetachedFromWindow(position, holder)
        Log.d("MultiTextDelegate", "MultiTextDelegate onDelegateViewDetachedFromWindow => $position")
    }

    override fun onDelegateViewRecycled(position: Int, holder: BaseViewHolder) {
        super.onDelegateViewRecycled(position, holder)
        Log.d("MultiTextDelegate", "MultiTextDelegate onDelegateViewRecycled => $position")
    }
}