package com.dengzq.demo.ui.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dengzq.baservadapter.interfaces.ItemNullDelegate
import com.dengzq.baservadapter.multi.MultiItemTypeAdapter
import com.dengzq.decoration.LinearDecoration
import com.dengzq.demo.R
import com.dengzq.demo.delegate.MultiModelDelegate
import com.dengzq.demo.delegate.MultiTextDelegate
import com.dengzq.demo.model.ModelBean
import com.dengzq.demo.service.ModelService
import com.dengzq.demo.widget.SimpleRefresh
import com.dengzq.simplerefreshlayout.SimpleRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*

/**
 * <p>author    dengzq</P>
 * <p>date      2018/6/7 下午6:02</p>
 * <p>package   com.dengzq.demo.ui.fragment</p>
 * <p>readMe    MultiItemTypeAdapter 示例;
 *
 * 适用于同一类实体类型，但显示类型不同的需求;
 * 此部分参考hongyangAndroid,原github地址:https://github.com/hongyangAndroid/baseAdapter;
 * 请多多支持原库!非常感谢
 *
 * </p>
 */
class MultiItemFragment : Fragment() {


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

        val list = ModelService.getModelBeanList(30)
        recycler_view.layoutManager = LinearLayoutManager(activity)
        val multiItemAdapter = MultiItemAdapter(activity!!, list)
        recycler_view.adapter = multiItemAdapter

        recycler_view.addItemDecoration(LinearDecoration(10.0f, RecyclerView.VERTICAL))

        simple_refresh.setPullUpEnable(false)
        simple_refresh.headerView = SimpleRefresh(activity!!)
        simple_refresh.setOnSimpleRefreshListener(object : SimpleRefreshLayout.OnSimpleRefreshListener {
            override fun onLoadMore() {
                //nothing
            }

            override fun onRefresh() {
                Handler().postDelayed({
                    list.clear()
                    list.addAll(ModelService.getModelBeanList(10))
                    recycler_view.adapter!!.notifyDataSetChanged()
                    simple_refresh.onRefreshComplete()
                }, 1000)
            }
        })
    }
}

class MultiItemAdapter(context: Context, list: List<ModelBean>) : MultiItemTypeAdapter<ModelBean>(context, list) {
    init {
        addItemViewDelegate(MultiModelDelegate())
        addItemViewDelegate(MultiTextDelegate())
        addItemViewDelegate(ItemNullDelegate())
    }
}