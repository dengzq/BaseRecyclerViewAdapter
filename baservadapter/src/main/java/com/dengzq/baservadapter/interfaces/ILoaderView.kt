package com.dengzq.baservadapter.interfaces

import android.support.annotation.LayoutRes

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/27 下午12:19</p>
 * <p>package   com.dengzq.baservadapter.interfaces</p>
 * <p>readMe    LoadView</p>
 */
interface ILoaderView {
    /**
     * Set loading view for loading state;
     * @return -1 if you do not need loading layout else return layout id;
     */
    @LayoutRes
    fun getLoadingLayoutId(): Int


    /**
     * Set load err view for load error state;
     * @return -1 if you do not need error layout else return layout id;
     */
    @LayoutRes
    fun getErrorLayoutId(): Int
}