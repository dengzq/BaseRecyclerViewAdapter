package com.dengzq.demo.delegate

import android.content.Context
import android.view.View
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.interfaces.ItemClassifyDelegate
import com.dengzq.demo.R
import com.dengzq.demo.ui.view.IClassifyPresenter

/**
 * <p>author    dengzq</P>
 * <p>date      2018/6/7 下午10:42</p>
 * <p>package   com.dengzq.demo.delegate</p>
 * <p>readMe    TODO</p>
 */
class ClassifyBannerDelegate(private val context: Context, private val presenter: IClassifyPresenter) : ItemClassifyDelegate() {


    override fun getItemSize(): Int = presenter.getClassifyBanner().size

    override fun needShow(): Boolean = getItemSize() > 0

    override fun getItemViewLayoutId(): Int = R.layout.item_delegate_banner

    override fun convert(holder: BaseViewHolder, position: Int) {
        val classifyBanner = presenter.getClassifyBanner()[position]

        val banner = holder.getView<BGABanner>(R.id.banner)
        val views = ArrayList<View>()
        for (i in 0 until classifyBanner.banners.size) {
            val iv = ImageView(context)
            iv.scaleType = ImageView.ScaleType.CENTER_CROP
            iv.setImageResource(classifyBanner.banners[i])
            views.add(iv)
        }
        banner?.setData(views)
    }

    override fun getItemSpanSize(position: Int,spanCount:Int): Int = 4

}