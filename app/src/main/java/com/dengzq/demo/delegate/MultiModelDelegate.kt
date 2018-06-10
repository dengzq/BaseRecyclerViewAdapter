package com.dengzq.demo.delegate

import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.interfaces.ItemViewDelegate
import com.dengzq.demo.R
import com.dengzq.demo.model.ModelBean

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/18 下午3:50</p>
 * <p>package   com.dengzq.demo.model</p>
 * <p>readMe    TODO</p>
 */
class MultiModelDelegate:ItemViewDelegate<ModelBean>(){

    override fun isForViewType(t: ModelBean, position: Int): Boolean=t.type==0

    override fun getItemViewLayoutId(): Int= R.layout.item_adapter_common


    override fun convert(holder: BaseViewHolder, t: ModelBean, position: Int) {
        holder.setText(R.id.tv_nick, t.nick)
    }
}