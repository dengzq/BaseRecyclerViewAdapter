package com.dengzq.demo.delegate

import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.interfaces.ItemClassifyDelegate
import com.dengzq.demo.R
import com.dengzq.demo.ui.view.IClassifyPresenter

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/18 下午4:31</p>
 * <p>package   com.dengzq.demo.model</p>
 * <p>readMe    TODO</p>
 */
class ClassifyMsgDelegate(private val presenter: IClassifyPresenter) : ItemClassifyDelegate() {
    override fun getItemSize(): Int = presenter.getClassifyNews().size

    override fun needShow(): Boolean = getItemSize() > 0

    override fun getItemViewLayoutId(): Int = R.layout.item_adapter_text

    override fun convert(holder: BaseViewHolder, position: Int) {
        val news = presenter.getClassifyNews()[position]
        holder.setText(R.id.tv_text, news.news + " || Most parents genuinely do their best to provide their children with a happy and healthy upbringing, but even these individuals can accidentally make mistakes that may result in future therapy appointments.")
    }

    override fun getItemSpanSize(position: Int): Int = 4
}