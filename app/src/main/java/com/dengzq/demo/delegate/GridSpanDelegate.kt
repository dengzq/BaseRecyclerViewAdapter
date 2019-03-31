@file:Suppress("LocalVariableName")

package com.dengzq.demo.delegate

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.interfaces.ItemViewDelegate
import com.dengzq.demo.R
import com.dengzq.demo.UsageExampleActivity
import com.dengzq.demo.model.ModelBean

/**
 * <p>author    dengzq</P>
 * <p>date      2018/6/9 下午3:07</p>
 * <p>package   com.dengzq.demo.delegate</p>
 * <p>readMe    TODO</p>
 */
class GridSpanDelegate(val context: Context) : ItemViewDelegate<ModelBean>() {

    override fun getItemViewLayoutId(): Int = R.layout.item_deleagte_grid_span

    override fun isForViewType(t: ModelBean, position: Int): Boolean = true

    override fun convert(holder: BaseViewHolder, t: ModelBean, position: Int) {
        val iv_img = holder.getView<ImageView>(R.id.iv_img)
        when (position % 7) {
            0 -> iv_img.setImageResource(R.mipmap.icon_card03)
            1, 2 -> iv_img.setImageResource(R.mipmap.icon_card04)
            else -> iv_img.setImageResource(R.mipmap.icon_card05)
        }
        iv_img.setOnClickListener {
            context.startActivity(Intent(context,UsageExampleActivity::class.java))
        }
    }

    override fun getItemSpanSize(position: Int, spanCount: Int): Int {
        return when (position % 7) {
            0 -> 4
            1, 2 -> 2
            else -> 1
        }
    }
}