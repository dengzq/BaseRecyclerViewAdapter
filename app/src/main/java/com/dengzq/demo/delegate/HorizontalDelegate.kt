package com.dengzq.demo.delegate

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.interfaces.ItemViewDelegate
import com.dengzq.baservadapter.multi.CommonAdapter
import com.dengzq.demo.R
import com.dengzq.demo.model.ModelBean
import com.dengzq.demo.model.NormalBean
import com.dengzq.demo.service.ModelService

/**
 * <p>author    dengzq</P>
 * <p>date      2019/3/26 23:13</p>
 * <p>package   com.dengzq.demo.delegate</p>
 * <p>readMe    doc.</p>
 */
class HorizontalDelegate : ItemViewDelegate<ModelBean>() {

    val sparseArray by lazy { HashMap<ModelBean, Int>() }

    override fun getItemViewLayoutId(): Int = R.layout.item_recycler_view

    override fun isForViewType(t: ModelBean, position: Int): Boolean = 3 == t.type

    override fun convert(holder: BaseViewHolder, bean: ModelBean, position: Int) {
        val recyclerView = holder.getView<RecyclerView>(R.id.recycler_view)

        val list = ModelService.getNormalBeanList(15)

        recyclerView?.layoutManager = LinearLayoutManager(recyclerView?.context, LinearLayout.HORIZONTAL, false)
        recyclerView?.adapter = HorizontalTypeAdapter(recyclerView?.context!!, list)

        recyclerView?.clearOnScrollListeners()
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                sparseArray[bean] = recyclerView!!.computeHorizontalScrollOffset()
            }
        })

        if (sparseArray[bean] != null) {
            recyclerView?.scrollBy(sparseArray[bean] ?: 0, 0)
        } else
            sparseArray[bean] = 0
    }
}

class HorizontalTypeAdapter(context: Context, list: ArrayList<NormalBean>) : CommonAdapter<NormalBean>(context, R.layout.item_recycler_child, list) {
    override fun convert(holder: BaseViewHolder, bean: NormalBean, position: Int) {
        holder.setText(R.id.tv_text, "$position")
    }
}