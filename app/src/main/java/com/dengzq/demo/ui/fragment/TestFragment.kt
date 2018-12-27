package com.dengzq.demo.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dengzq.decoration.StickyLayoutManager
import com.dengzq.demo.R
import com.dengzq.demo.service.ModelService
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

        val list = ModelService.getNormalBeanList(30)
        val adapter = SingleTypeAdapter(activity!!, list)
        recycler_view.layoutManager = StickyLayoutManager()
        recycler_view.adapter = adapter
    }
}