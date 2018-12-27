package com.dengzq.demo.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dengzq.demo.R
import com.dengzq.demo.service.ModelService
import com.dengzq.demo.widget.LoaderView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * <p>author    dengzq</P>
 * <p>date      2018/11/14 下午11:13</p>
 * <p>package   com.dengzq.demo.ui.fragment</p>
 * <p>readMe    TestFragment</p>
 */
class TestFragment : Fragment() {

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

        val list = ModelService.getNormalBeanList(2)
        val adapter = SingleTypeAdapter(activity!!, list)
        adapter.addLoaderView(LoaderView())
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.adapter = adapter
    }
}