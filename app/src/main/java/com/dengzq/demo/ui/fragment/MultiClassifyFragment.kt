package com.dengzq.demo.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.listener.OnItemClickListener
import com.dengzq.baservadapter.multi.MultiItemClassifyAdapter
import com.dengzq.demo.R
import com.dengzq.demo.delegate.ClassifyBannerDelegate
import com.dengzq.demo.delegate.ClassifyMenuDelegate
import com.dengzq.demo.delegate.ClassifyMsgDelegate
import com.dengzq.demo.delegate.ClassifyTabDelegate
import com.dengzq.demo.ui.presenter.ClassifyPresenter
import com.dengzq.demo.ui.view.IClassifyPresenter
import com.dengzq.demo.ui.view.IClassifyView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * <p>author    dengzq</P>
 * <p>date      2018/6/7 下午6:11</p>
 * <p>package   com.dengzq.demo.ui.fragment</p>
 * <p>readMe    MultiItemClassifyAdapter 示例;
 *
 * 注意: MultiItemClassifyAdapter显示的顺序就是addItemDelegate的顺序；因此，
 * 当第一个类型为banner，添加BannerDelegate;
 * 第二个类型为菜单，添加MenuDelegate;
 * 第三个类型为广告，添加TabDelegate...依次类推;
 *
 * 适用于类似app首页等 [ 数据顺序确定 ] 的场景；
 * 你只需要做的仅是:
 * 1.按需求顺序添加所需delegate;
 * 2.请求不同的接口，拿到相应数据集;
 * 3.刷新adapter;
 *
 * </p>
 */
class MultiClassifyFragment : Fragment(), IClassifyView {

    private lateinit var adapter: ClassifyAdapter
    private lateinit var presenter: IClassifyPresenter

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

        presenter = ClassifyPresenter(this)

        simple_refresh.setPullDownEnable(false)
        simple_refresh.setPullUpEnable(false)

        adapter = ClassifyAdapter(activity!!, presenter)
        recycler_view.layoutManager = GridLayoutManager(activity, 4)
        recycler_view.adapter = adapter
        adapter.itemClickListener = object : OnItemClickListener {
            override fun onItemClick(v: View, holder: BaseViewHolder, position: Int) {
                Toast.makeText(activity, "item $position is clicked !", Toast.LENGTH_SHORT).show()
            }
        }

        //初始化数据，模拟网络请求
        initData()
    }

    override fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }

    private fun initData() {
        presenter.requestNetBanner()
        presenter.requestNetTab()
        presenter.requestNetMenu()
        presenter.requestNetNews()
    }
}

class ClassifyAdapter(context: Context, presenter: IClassifyPresenter) : MultiItemClassifyAdapter(context) {
    init {
        addItemClassifyDelegate(ClassifyBannerDelegate(context, presenter))
        addItemClassifyDelegate(ClassifyMenuDelegate(presenter))
        addItemClassifyDelegate(ClassifyTabDelegate(context, presenter))
        addItemClassifyDelegate(ClassifyMsgDelegate(presenter))
    }
}