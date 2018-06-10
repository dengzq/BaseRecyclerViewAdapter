package com.dengzq.baservadapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * <p>author    dengzq</p>
 * <p>date      2018/5/5 下午4:52</p>
 * <p>package   com.dengzq.baservadapter.base</p>
 * <p>readMe    ViewHolder 基类</p>
 */
class BaseViewHolder(val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mViews = SparseArray<View>()

    companion object {

        fun createViewHolder(context: Context, itemView: View): BaseViewHolder {
            return BaseViewHolder(context, itemView)
        }

        fun createViewHolder(context: Context, parent: ViewGroup, layoutId: Int): BaseViewHolder {
            val view = LayoutInflater.from(context).inflate(layoutId, parent, false)
            return BaseViewHolder(context, view)
        }

    }

    @Suppress("UNCHECKED_CAST")
    public fun <T : View> getView(resId: Int): T {
        var view = mViews.get(resId)
        if (view == null) {
            view = itemView.findViewById(resId)
            mViews.put(resId, view)
        }
        return view!! as T
    }

    fun getConvertView(): View = itemView

    /**
     * 以下是辅助方法
     */
    fun setText(viewId: Int, text: String): BaseViewHolder {
        val tv = getView<TextView>(viewId)
        tv.text = text
        return this
    }

    fun setTextColor(viewId: Int, color: Int): BaseViewHolder {
        val tv = getView<TextView>(viewId)
        tv.setTextColor(color)
        return this
    }

    fun setTextSize(viewId: Int, size: Float): BaseViewHolder {
        val tv = getView<TextView>(viewId)
        tv.textSize = size
        return this
    }

    fun setImageResource(viewId: Int, resId: Int): BaseViewHolder {
        val iv = getView<ImageView>(viewId)
        iv.setImageResource(resId)
        return this
    }

    fun setImageDrawable(viewId: Int, drawable: Drawable): BaseViewHolder {
        val iv = getView<ImageView>(viewId)
        iv.setImageDrawable(drawable)
        return this
    }

    fun setImageBitmap(viewId: Int, bitmap: Bitmap): BaseViewHolder {
        val iv = getView<ImageView>(viewId)
        iv.setImageBitmap(bitmap)
        return this
    }

    fun setBackgroundColor(viewId: Int, color: Int): BaseViewHolder {
        getView<View>(viewId).setBackgroundColor(color)
        return this
    }

    fun setBackgroundRes(viewId: Int, resId: Int): BaseViewHolder {
        getView<View>(viewId).setBackgroundResource(resId)
        return this
    }

    fun setVisibility(viewId: Int, visible: Boolean): BaseViewHolder {
        getView<View>(viewId).visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }
}