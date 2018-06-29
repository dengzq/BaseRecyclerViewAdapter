@file:Suppress("LocalVariableName")

package com.dengzq.demo.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.dengzq.baservadapter.listener.OnLoadMoreListener
import com.dengzq.demo.R
import com.dengzq.demo.model.ModelBean
import com.dengzq.demo.service.ModelService
import com.dengzq.demo.widget.LoaderView
import com.dengzq.demo.widget.SimpleBottom
import com.dengzq.demo.widget.SimpleFooter
import com.dengzq.demo.widget.SimpleRefresh
import com.dengzq.simplerefreshlayout.SimpleRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*

/**
 * <p>author    dengzq</P>
 * <p>date      2018/6/10 上午12:20</p>
 * <p>package   com.dengzq.demo.ui.fragment</p>
 * <p>readMe    loader and bottom 示例
 *
 * loader and bottom 一般有以下情况：
 * 1.loader属于父控件(即刷新控件),bottom也属于父布局;
 * 2.loader属于adapter，bottom也属于adapter;
 * 3.loader属于父控件(即刷新控件),bottom属于adapter;
 * 4.loader属于adapter,bottom属于父控件(即刷新控件)
 *
 * 选择满足你的需求的即可;
 * 示例中使用的刷新控件，地址:https://github.com/dengzq/SimpleRefreshLayout;
 * </p>
 */
class LoadAndBottomFragment : Fragment() {

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


        val list = ArrayList<ModelBean>()
        val adapter = MultiItemAdapter(activity!!, list)
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.adapter = adapter

        simple_refresh.setPullUpEnable(false)
        simple_refresh.setEffectivePullUpRange(200)
        simple_refresh.headerView = SimpleRefresh(activity!!)

        //set adapter loader
        val loader = LoaderView()
        adapter.addLoaderView(loader)

        //adapter bottom
        val adapterBottom = layoutInflater.inflate(R.layout.layout_bottom, recycler_view, false)

        val header = layoutInflater.inflate(R.layout.layout_loader_header, recycler_view, false)
        adapter.addHeaderView(header)
        val refresh_load = header.findViewById<CheckBox>(R.id.cb_refresh_load)
        val refresh_bottom = header.findViewById<CheckBox>(R.id.cb_refresh_bottom)
        val adapter_load = header.findViewById<CheckBox>(R.id.cb_adapter_load)
        val adapter_bottom = header.findViewById<CheckBox>(R.id.cb_adapter_bottom)

        refresh_load.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                simple_refresh.footerView = SimpleFooter(activity!!)
            } else {
                simple_refresh.removeFooterView()
            }

            if (simple_refresh.footerView != null || simple_refresh.bottomView != null) simple_refresh.setPullUpEnable(true)
            else simple_refresh.setPullUpEnable(false)
        }
        refresh_bottom.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                simple_refresh.bottomView = SimpleBottom(activity!!)
            } else {
                simple_refresh.removeBottomView()
            }

            if (simple_refresh.footerView != null || simple_refresh.bottomView != null) simple_refresh.setPullUpEnable(true)
            else simple_refresh.setPullUpEnable(false)
        }
        adapter_load.setOnCheckedChangeListener { buttonView, isChecked ->
            adapter.openLoadMore(isChecked)
        }
        adapter_bottom.setOnCheckedChangeListener { buttonView, isChecked ->
            //set adapter bottom
            adapter.addBottomView(if (isChecked) adapterBottom else null)
        }


        //设置simple_refresh load more
        simple_refresh.setOnSimpleRefreshListener(object : SimpleRefreshLayout.OnSimpleRefreshListener {
            override fun onLoadMore() {
                Handler().postDelayed({
                    list.addAll(ModelService.getModelBeanList(5))
                    recycler_view.adapter!!.notifyDataSetChanged()

                    simple_refresh.onLoadMoreComplete()

                    if (adapter.itemCount > 14) {
                        adapter.isHasMore(false)

                        simple_refresh.showNoMore(true)
                    }
                }, 1000)
            }

            override fun onRefresh() {
                Handler().postDelayed({
                    list.clear()
                    list.addAll(ModelService.getModelBeanList(10))
                    recycler_view.adapter!!.notifyDataSetChanged()

                    simple_refresh.onRefreshComplete()
                    adapter.isHasMore(true)

                }, 1000)
            }
        })
        //设置adapter load more
        adapter.onLoadMoreListener = object : OnLoadMoreListener {
            override fun onLoadMore() {
                Handler().postDelayed({

                    list.addAll(ModelService.getModelBeanList(5))
                    recycler_view.adapter!!.notifyDataSetChanged()

                    adapter.loadMoreSuccess()

                    if (adapter.itemCount > 14) {
                        adapter.isHasMore(false)

                        simple_refresh.showNoMore(true)
                    }
                }, 1000)
            }
        }


        //init config
        adapter_load.isChecked = true
        adapter_bottom.isChecked = true


        list.addAll(ModelService.getModelBeanList(10))
    }
}