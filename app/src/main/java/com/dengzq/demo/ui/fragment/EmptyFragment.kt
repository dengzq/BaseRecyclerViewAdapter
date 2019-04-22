package com.dengzq.demo.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.dengzq.demo.R
import com.dengzq.demo.service.ModelService
import com.dengzq.demo.widget.LoaderView
import kotlinx.android.synthetic.main.fragment_empty.*

/**
 * <p>author    dengzq</P>
 * <p>date      2018/11/14 下午11:13</p>
 * <p>package   com.dengzq.demo.ui.fragment</p>
 * <p>readMe    EmptyFragment</p>
 */
class EmptyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root: View
        if (view == null) {
            root = inflater.inflate(R.layout.fragment_empty, container, false)
            return root
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        simple_refresh.setPullDownEnable(false)
        simple_refresh.setPullUpEnable(false)

        val list = ModelService.getNormalBeanList(10)
        val adapter = SingleTypeAdapter(activity!!, list)

        adapter.addLoaderView(LoaderView())
        recycler_view.layoutManager = LinearLayoutManager(activity)
        val textView=TextView(activity).apply {
            text="EmptyView"
            textSize=30.0f
            setTextColor(Color.BLACK)
            setBackgroundColor(Color.parseColor("#5ccccc"))
        }
        adapter.setEmptyView(textView)

        val adapterBottom = layoutInflater.inflate(R.layout.layout_bottom, recycler_view, false)
        adapter.addBottomView(adapterBottom)

        recycler_view.adapter = adapter

        adapter.isHasMore(false)

        btn_clear.setOnClickListener {
            list.clear()
            adapter.notifyDataSetChanged()
        }

        btn_add.setOnClickListener {
            list.addAll(ModelService.getNormalBeanList(10))
            adapter.notifyDataSetChanged()
        }
    }
}