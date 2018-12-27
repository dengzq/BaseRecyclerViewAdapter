package com.dengzq.demo.ui.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.interfaces.ItemViewDelegate
import com.dengzq.baservadapter.listener.OnLoadMoreListener
import com.dengzq.baservadapter.multi.MultiItemTypeAdapter
import com.dengzq.demo.R
import com.dengzq.demo.delegate.MultiModelDelegate
import com.dengzq.demo.model.ModelBean
import com.dengzq.demo.service.ModelService
import com.dengzq.demo.widget.LoaderView
import com.dengzq.demo.widget.SimpleRefresh
import com.dengzq.simplerefreshlayout.SimpleRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*

/**
 * <p>author    dengzq</P>
 * <p>date      2018/11/5 下午11:32</p>
 * <p>package   com.dengzq.demo.ui.fragment</p>
 * <p>readMe    StaggeredGridFragment</p>
 */
class StaggeredGridFragment : Fragment() {

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
        simple_refresh.headerView = SimpleRefresh(activity!!)

        recycler_view.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        val list = ModelService.getModelBeanList(20)
        val adapter = StaggeredGridAdapter(context!!, list)
        recycler_view.adapter = adapter

        val header1 = layoutInflater.inflate(R.layout.layout_header, recycler_view, false)
        adapter.addHeaderView("header1", header1)

        val header2 = layoutInflater.inflate(R.layout.layout_head2, recycler_view, false)
        adapter.addHeaderView("header2", header2)

        val footer = layoutInflater.inflate(R.layout.layout_footer, recycler_view, false)
        adapter.addFooterView("footer1", footer)

        adapter.addLoaderView(LoaderView())
        adapter.onLoadMoreListener = object : OnLoadMoreListener {
            override fun onLoadMore() {
                Handler().postDelayed({
                    list.addAll(ModelService.getModelBeanList(10))
                    adapter.loadMoreSuccess()

                    if (list.size > 50)
                        adapter.isHasMore(false)
                }, 1000)
            }
        }

        simple_refresh.setOnSimpleRefreshListener(object : SimpleRefreshLayout.OnSimpleRefreshListener {
            override fun onLoadMore() {}

            override fun onRefresh() {
                Handler().postDelayed({
                    list.clear()
                    list.addAll(ModelService.getModelBeanList(10))

                    adapter.isHasMore(true)
                    adapter.notifyDataSetChanged()

                    simple_refresh.onRefreshComplete()
                }, 1000)
            }
        })
    }
}

class StaggeredGridAdapter(context: Context, list: List<ModelBean>) : MultiItemTypeAdapter<ModelBean>(context, list) {
    init {
        addItemViewDelegate(StaggeredGridDelegate())
        addItemViewDelegate(MultiModelDelegate())
    }
}

class StaggeredGridDelegate : ItemViewDelegate<ModelBean>() {
    override fun getItemViewLayoutId(): Int = R.layout.item_deleagte_grid_span

    override fun isForViewType(t: ModelBean, position: Int): Boolean = t.type == 1

    override fun convert(holder: BaseViewHolder, t: ModelBean, position: Int) {
        val iv_img = holder.getView<ImageView>(R.id.iv_img)
        when (position % 7) {
            0 -> iv_img.setImageResource(R.mipmap.icon_card03)
            1, 2 -> iv_img.setImageResource(R.mipmap.icon_card04)
            else -> iv_img.setImageResource(R.mipmap.icon_card05)
        }
    }
}

