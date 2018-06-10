package com.dengzq.baservadapter.interfaces

import android.support.annotation.LayoutRes

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/27 下午12:19</p>
 * <p>package   com.dengzq.baservadapter.interfaces</p>
 * <p>readMe    LoadView
 * Implement this interface to set loading layout as well as load-error layout;
 * </p>
 */
interface ILoaderView {
    /**
     * set loading view for loading state
     * if you don't need loading layout,return -1; otherwise return layoutId
     */
    @LayoutRes
    fun getLoadingLayoutId(): Int


    /**
     * set load err view for load err state
     * if you don't need error layout,return -1; otherwise return layoutId
     */
    @LayoutRes
    fun getErrorLayoutId(): Int
}