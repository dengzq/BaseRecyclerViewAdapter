package com.dengzq.demo.widget

import com.dengzq.baservadapter.interfaces.ILoaderView
import com.dengzq.demo.R

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/27 下午12:30</p>
 * <p>package   com.dengzq.demo.model</p>
 * <p>readMe    TODO</p>
 */
class LoaderView : ILoaderView {
    override fun getErrorLayoutId(): Int = R.layout.layout_load_err

    override fun getLoadingLayoutId(): Int = R.layout.layout_loader
}