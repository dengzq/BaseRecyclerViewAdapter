package com.dengzq.demo.ui.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dengzq.baservadapter.listener.OnLoadMoreListener
import com.dengzq.baservadapter.multi.MultiItemTypeAdapter
import com.dengzq.decoration.GridDecoration
import com.dengzq.demo.R
import com.dengzq.demo.delegate.GridSpanDelegate
import com.dengzq.demo.model.ModelBean
import com.dengzq.demo.service.ModelService
import com.dengzq.demo.widget.LoaderView
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
        //ModelService.getModelBeanList(6)
        val adapter = GridSpanAdapter(activity!!, ModelService.getModelBeanList(1))
        adapter.addLoaderView(LoaderView())
        adapter.onLoadMoreListener = object : OnLoadMoreListener {
            override fun onLoadMore() {
                Handler().postDelayed({
                    (adapter.list as MutableList).addAll(ModelService.getModelBeanList(6))
                    adapter.notifyDataSetChanged()
                    adapter.loadMoreSuccess()
                }, 1000)
            }
        }
        recycler_view.adapter = adapter
        recycler_view.addItemDecoration(GridDecoration(10.0f))
    }
}

class GridSpanAdapter(context: Context, list: List<ModelBean>) : MultiItemTypeAdapter<ModelBean>(context, list) {
    init {
        addItemViewDelegate(GridSpanDelegate())
    }
}