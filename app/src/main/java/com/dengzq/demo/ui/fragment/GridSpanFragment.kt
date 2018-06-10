package com.dengzq.demo.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dengzq.baservadapter.multi.MultiItemTypeAdapter
import com.dengzq.demo.R
import com.dengzq.demo.delegate.GridSpanDelegate
import com.dengzq.demo.model.ModelBean
import com.dengzq.demo.service.ModelService
import kotlinx.android.synthetic.main.activity_main.*

/**
 * <p>author    dengzq</P>
 * <p>date      2018/6/9 下午12:07</p>
 * <p>package   com.dengzq.demo.ui.fragment</p>
 * <p>readMe    Span size 示例
 *
 * 适用于一些类似菜单的列表页面；
 * 需要实现不同的span size时,只需让delegate覆写getItemSpanSize()方法;
 * </p>
 */
class GridSpanFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root: View
        if (view == null) {
            root = inflater.inflate(R.layout.activity_main, container, false)
            return root
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simple_refresh.setPullUpEnable(false)
        simple_refresh.setPullDownEnable(false)

        recycler_view.layoutManager = GridLayoutManager(activity, 4)
        recycler_view.adapter = GridSpanAdapter(activity!!, ModelService.getModelBeanList(14))
    }
}

class GridSpanAdapter(context: Context, list: List<ModelBean>) : MultiItemTypeAdapter<ModelBean>(context, list) {
    init {
        addItemViewDelegate(GridSpanDelegate())
    }
}