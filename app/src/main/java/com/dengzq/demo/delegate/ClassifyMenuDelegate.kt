package com.dengzq.demo.delegate

import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.interfaces.ItemClassifyDelegate
import com.dengzq.demo.R
import com.dengzq.demo.ui.view.IClassifyPresenter

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/18 下午4:26</p>
 * <p>package   com.dengzq.demo.model</p>
 * <p>readMe    TODO</p>
 */
class ClassifyMenuDelegate(private val presenter: IClassifyPresenter) : ItemClassifyDelegate() {


    override fun getItemSize(): Int = presenter.getClassifyMenu().size

    override fun needShow(): Boolean = getItemSize() > 0

    override fun getItemViewLayoutId(): Int = R.layout.item_delegate_menu

    override fun convert(holder: BaseViewHolder, position: Int) {

        val menu = presenter.getClassifyMenu()[position]
        holder.setText(R.id.tv_nick, menu.nick)
    }
}