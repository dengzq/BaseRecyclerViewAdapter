package com.dengzq.demo.ui.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.listener.OnItemClickListener
import com.dengzq.baservadapter.listener.OnLoadMoreListener
import com.dengzq.baservadapter.multi.CommonAdapter
import com.dengzq.demo.R
import com.dengzq.demo.model.NormalBean
import com.dengzq.demo.service.ModelService
import com.dengzq.demo.widget.LoaderView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * <p>author    dengzq</P>
 * <p>date      2018/6/7 下午5:54</p>
 * <p>package   com.dengzq.demo.ui.fragment</p>
 * <p>readMe    CommonAdapter 示例
 *
 * 基本使用示例;
 * </p>
 */
class SingleFragment : Fragment() {

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

        simple_refresh.setPullDownEnable(false)
        simple_refresh.setPullUpEnable(false)

        val list = ModelService.getNormalBeanList(3)
        val adapter = SingleTypeAdapter(activity!!, list)
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.adapter = adapter


        //set load more view;
        val loader = LoaderView()
        adapter.addLoaderView(loader)

        //set bottom view;
        val bottom = layoutInflater.inflate(R.layout.layout_bottom, recycler_view, false)
        adapter.addBottomView(bottom)

        //set itemClickListener;
        adapter.itemClickListener = object : OnItemClickListener {
            override fun onItemClick(v: View, holder: BaseViewHolder, position: Int) {
                Toast.makeText(context, "click item $position !!", Toast.LENGTH_SHORT).show()
            }
        }
        //set loadMoreListener;
        adapter.loadMoreListener = object : OnLoadMoreListener {
            override fun onLoadMore() {
                Handler().postDelayed({

                    list.addAll(ModelService.getNormalBeanList(3))
                    adapter.notifyDataSetChanged()
                    adapter.loadMoreSuccess()

                    if (adapter.list.size > 8) {
                        adapter.isHasMore(false)
                    }

                }, 1000)
            }
        }
    }
}

class SingleTypeAdapter(context: Context, list: ArrayList<NormalBean>) : CommonAdapter<NormalBean>(context, R.layout.item_main_adapter, list) {
    override fun convert(holder: BaseViewHolder, bean: NormalBean, position: Int) {
        holder.setText(R.id.tv_title, bean.title)
        holder.setImageResource(R.id.iv_img, bean.imgRes)
    }
}