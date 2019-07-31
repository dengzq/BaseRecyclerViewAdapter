package com.dengzq.baservadapter.listener

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/26 下午1:48</p>
 * <p>package   com.dengzq.baservadapter.listener</p>
 * <p>readMe    Load More Listener</p>
 */

/**
 * OnLoadMoreListener will only be invoked while loading attach;
 */
interface OnLoadMoreListener {
    fun onLoadMore()
}

/**
 * OnPreloadListener will only be invoked while preLoad;
 */
interface OnPreloadListener {
    fun onPreload()
}

/**
 * OnAdapterLoadListener will be invoked when preLoad or loading;
 */
interface OnAdapterLoadListener {
    fun onLoadMore()
}