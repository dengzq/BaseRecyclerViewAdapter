package com.dengzq.baservadapter.multi.wrapper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.dengzq.baservadapter.BaseRvAdapter
import com.dengzq.baservadapter.constants.LoadState
import com.dengzq.baservadapter.interfaces.ILoaderView

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/26 下午12:49</p>
 * <p>package   com.dengzq.baservadapter.multi.wrapper</p>
 * <p>readMe    Wrapper for loadMore</p>
 */

internal class LoaderWrapper(private val context: Context) {

    private val frameContainer = FrameLayout(context)
    private var loadMoreView: ILoaderView? = null
    private var loadLayout: View? = null
    private var errorLayout: View? = null

    var state = LoadState.NORMAL
    var preloadCount: Int = 6//default preload
    var openLoad = true //whether open load;
    var autoLoad = true //whether auto load;
    var hasMore = true  //whether show load;

    fun getLoaderCount(): Int = if (loadMoreView == null || loadLayout == null || !openLoad) 0 else 1

    fun getLoadMoreViewType(): Int = BaseRvAdapter.LOADER_INDEX

    fun getLoadMoreView(): View = frameContainer

    fun getLoadingView(): View? = loadLayout

    fun getLoadErrView(): View? = errorLayout

    fun removeLoaderView() {
        loadMoreView = null
        loadLayout = null
        errorLayout = null
    }

    fun initializerLoader(iLoadView: ILoaderView) {
        this.loadMoreView = iLoadView

        frameContainer.layoutParams = ViewGroup.LayoutParams(-1, -2)

        loadMoreView?.let {

            //Set target loader height after inflating xml res;
            if (loadMoreView!!.getLoadingLayoutId() != -1) {
                val id1 = loadMoreView!!.getLoadingLayoutId()
                loadLayout = LayoutInflater.from(context).inflate(id1, frameContainer, false)
                val p1 = FrameLayout.LayoutParams(-1, loadLayout!!.layoutParams.height)
                frameContainer.addView(loadLayout, p1)
            }

            if (loadMoreView!!.getErrorLayoutId() != -1) {
                val id2 = loadMoreView!!.getErrorLayoutId()
                errorLayout = LayoutInflater.from(context).inflate(id2, frameContainer, false)
                val p2 = FrameLayout.LayoutParams(-1, errorLayout!!.layoutParams.height)
                frameContainer.addView(errorLayout, p2)
            }
        }

    }

    fun isHasMore(hasMore: Boolean) {
        this.hasMore = hasMore
        if (hasMore) notifyStateChanged(LoadState.NORMAL)
    }

    fun notifyStateChanged(status: LoadState) {
        this.state = status
        if (!hasMore)
            state = LoadState.NORMAL

        when (state) {
            LoadState.LOADING -> {
                loadLayout?.visibility = View.VISIBLE
                errorLayout?.visibility = View.GONE
            }
            LoadState.ERROR -> {
                loadLayout?.visibility = View.GONE
                errorLayout?.visibility = View.VISIBLE
            }
            else -> {
                loadLayout?.visibility = View.GONE
                errorLayout?.visibility = View.GONE
            }
        }
    }
}