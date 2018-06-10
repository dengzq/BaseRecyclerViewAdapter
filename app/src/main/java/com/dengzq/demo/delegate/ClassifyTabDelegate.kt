package com.dengzq.demo.delegate

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.interfaces.ItemClassifyDelegate
import com.dengzq.demo.R
import com.dengzq.demo.ui.fragment.SingleTypeAdapter
import com.dengzq.demo.ui.view.IClassifyPresenter

/**
 * <p>author    dengzq</P>
 * <p>date      2018/6/7 下午11:14</p>
 * <p>package   com.dengzq.demo.delegate</p>
 * <p>readMe    TODO</p>
 */
class ClassifyTabDelegate(private val context: Context, private val presenter: IClassifyPresenter) : ItemClassifyDelegate() {

    override fun getItemSize(): Int = presenter.getClassifyTab().size

    override fun needShow(): Boolean = getItemSize() > 0

    override fun getItemViewLayoutId(): Int = R.layout.item_delegate_tab

    override fun convert(holder: BaseViewHolder, position: Int) {
        val classifyTab = presenter.getClassifyTab()[position]

        val recyclerView = holder.getView<RecyclerView>(R.id.recycler_view)
        if (recyclerView.adapter != null) {
            recyclerView.adapter.notifyDataSetChanged()
        } else {
            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            recyclerView.adapter = SingleTypeAdapter(context, classifyTab.tabs)
        }
    }

    override fun getItemSpanSize(position: Int): Int = 4
}