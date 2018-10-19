@file:Suppress("MemberVisibilityCanPrivate", "PropertyName", "PrivatePropertyName")

package com.dengzq.baservadapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewGroup
import com.dengzq.baservadapter.constants.LoadState
import com.dengzq.baservadapter.interfaces.ILoaderView
import com.dengzq.baservadapter.listener.*
import com.dengzq.baservadapter.multi.wrapper.BottomWrapper
import com.dengzq.baservadapter.multi.wrapper.HeaderAndFooterWrapper
import com.dengzq.baservadapter.multi.wrapper.LoaderWrapper

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/17 下午12:06</p>
 * <p>package   com.dengzq.baservadapter</p>
 * <p>readMe    RecyclerViewAdapter</p>
 */
@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseRvAdapter(val context: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    internal companion object StaticObj {
        /**
         * Range of ViewType;
         * HEADER: [0~100000)
         * FOOTER: [100000~200000)
         * LOADER: 200001
         * BOTTOM: 200002
         * VIEWER: [300000~ )
         */
        internal const val FOOTER_INDEX = 100000
        internal const val LOADER_INDEX = 200001
        internal const val BOTTOM_INDEX = 200002
        internal const val REALER_INDEX = 300000
    }

    private val hfHelper = HeaderAndFooterWrapper()
    private val loadHelper = LoaderWrapper(context)
    private val btmHelper = BottomWrapper()

    private lateinit var recyclerView: RecyclerView

    var onItemClickListener: OnItemClickListener? = null
    var onItemLongClickListener: OnItemLongClickListener? = null
    var onHeaderClickListener: OnHeaderClickListener? = null
    var onFooterClickListener: OnFooterClickListener? = null
    var onLoaderClickListener: OnLoaderClickListener? = null
    var onBottomClickListener: OnBottomClickListener? = null
    var onLoadMoreListener: OnLoadMoreListener? = null

    protected abstract fun getRealSpanSize(position: Int): Int
    protected abstract fun getRealItemCount(): Int
    protected abstract fun getRealViewType(position: Int): Int
    protected abstract fun createRealHolder(parent: ViewGroup, viewType: Int): BaseViewHolder
    protected abstract fun bindRealHolder(holder: BaseViewHolder, position: Int)

    override fun getItemViewType(position: Int): Int {
        return when {
            isHeaderPosition(position) ->
                hfHelper.getHeaderType(position)
            isFooterPosition(position) ->
                hfHelper.getFooterType(position - hfHelper.getHeaderCount() - getRealItemCount())
            isLoaderPosition(position) -> loadHelper.getLoadMoreViewType()
            isBottomPosition(position) -> btmHelper.getBottomViewType()
            else ->
                getRealViewType(position - hfHelper.getHeaderCount())
        }
    }

    override fun getItemCount(): Int = hfHelper.getHeaderCount() + hfHelper.getFooterCount() + getRealItemCount() +
            getBottomCount() + getLoaderCount()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val holder: BaseViewHolder?

        when {
            hfHelper.getHeaderView(viewType) != null -> {
                holder = BaseViewHolder.createViewHolder(context, hfHelper.getHeaderView(viewType)!!)
                setOnHeaderClickListener(holder, viewType)
            }
            hfHelper.getFooterView(viewType) != null -> {
                holder = BaseViewHolder.createViewHolder(context, hfHelper.getFooterView(viewType)!!)
                setOnFooterClickListener(holder, viewType)
            }
            loadHelper.getLoadMoreViewType() == viewType -> {
                holder = BaseViewHolder.createViewHolder(context, loadHelper.getLoadMoreView())
                setOnLoaderClickListener(holder)
            }
            btmHelper.getBottomViewType() == viewType -> {
                holder = BaseViewHolder.createViewHolder(context, btmHelper.getBottomView()!!)
                setOnBottomClickListener(holder)
            }
            else -> {
                holder = createRealHolder(parent, viewType)
                setOnItemClickListener(holder)
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

        when {
            isHeaderPosition(position) || isFooterPosition(position)
                    || isBottomPosition(position) -> return

            isLoaderPosition(position) -> autoLoadMore()

            else -> bindRealHolder(holder, position - hfHelper.getHeaderCount())
        }

    }

    private fun setOnBottomClickListener(holder: BaseViewHolder) {
        holder.getConvertView().setOnClickListener {
            onBottomClickListener?.onItemClick(it, holder, holder.adapterPosition)
        }
    }

    private fun setOnLoaderClickListener(holder: BaseViewHolder) {
        holder.getConvertView().setOnClickListener {
            onLoaderClickListener?.onItemClick(it, holder, loadHelper.state)
        }
    }

    private fun setOnHeaderClickListener(holder: BaseViewHolder, viewType: Int) {
        holder.getConvertView().setOnClickListener {
            onHeaderClickListener?.onHeaderClick(it, holder, hfHelper.getHeaderKey(viewType))
        }
    }

    private fun setOnFooterClickListener(holder: BaseViewHolder, viewType: Int) {
        holder.getConvertView().setOnClickListener {
            onFooterClickListener?.onFooterClick(it, holder, hfHelper.getFooterKey(viewType))
        }
    }

    private fun setOnItemClickListener(holder: BaseViewHolder) {
        holder.getConvertView().setOnClickListener {
            onItemClickListener?.onItemClick(it, holder, holder.adapterPosition - hfHelper.getHeaderCount())
        }
        holder.getConvertView().setOnLongClickListener {
            onItemLongClickListener?.onItemLongClick(it, holder, holder.adapterPosition - hfHelper.getHeaderCount())
            onItemLongClickListener != null
        }
    }

    private fun isHeaderPosition(position: Int): Boolean = hfHelper.getHeaderCount() > 0 && position < hfHelper.getHeaderCount()

    private fun isFooterPosition(position: Int): Boolean = hfHelper.getFooterCount() > 0 && position >= hfHelper.getHeaderCount() + getRealItemCount()
            && position < hfHelper.getHeaderCount() + hfHelper.getFooterCount() + getRealItemCount()

    private fun isLoaderPosition(position: Int): Boolean = getLoaderCount() > 0 && position == hfHelper.getHeaderCount() + hfHelper.getFooterCount() + getRealItemCount()

    private fun isBottomPosition(position: Int): Boolean = getBottomCount() > 0 && position == hfHelper.getHeaderCount() + hfHelper.getFooterCount() +
            getRealItemCount()

    private fun getLoaderCount(): Int = if (canShowBottom()) 0 else loadHelper.getLoaderCount()

    private fun getBottomCount(): Int = if (canShowBottom()) btmHelper.getBottomCount() else 0

    private fun canShowBottom(): Boolean = !loadHelper.hasMore

    /**
     * Auto load more data;
     * If you already set onLoadMoreListener, remember to invoke loadSuccess or
     * loadFailed to end loadMore event;
     */
    private fun autoLoadMore() {
        //No content, hide loader;
        if (getHeaderCount() + getFooterCount() + getRealItemCount() <= 0) {
            loadHelper.notifyStateChanged(LoadState.NORMAL)
            return
        }
        //Loading ,return;
        if (loadHelper.state == LoadState.LOADING) return

        //Auto load More;
        if (loadHelper.hasMore && isContentMoreThanOnePage()) {
            loadHelper.notifyStateChanged(LoadState.LOADING)

            //Auto load by loadMoreLis, or click to load by loadClickLis;
            if (loadHelper.autoLoad) {
                recyclerView.post({ onLoadMoreListener?.onLoadMore() })
            }
        } else {
            //Not match recyclerView's totalHeight, hide loader;
            loadHelper.hasMore = false
            loadHelper.notifyStateChanged(LoadState.NORMAL)
        }
    }

    /**
     * whether recyclerView already shows all content;
     * @return true if rv's content is more than one page,means that we can show loader if hasMore;
     * false otherwise;
     */
    private fun isContentMoreThanOnePage(): Boolean {

        val moreThanOnePage = false

        val layoutManager = recyclerView.layoutManager

        if (layoutManager is LinearLayoutManager) {

            val lastCompletePos = layoutManager.findLastCompletelyVisibleItemPosition()
            moreThanOnePage != lastCompletePos >= (layoutManager.childCount - 1)
                    && layoutManager.childCount == (layoutManager.itemCount - getLoaderCount() - getBottomCount())

        } else if (layoutManager is StaggeredGridLayoutManager) {

            val intArray = layoutManager.findLastCompletelyVisibleItemPositions(null)
            moreThanOnePage != intArray[intArray.size - 1] >= (layoutManager.childCount - 1)
                    && layoutManager.childCount == (layoutManager.itemCount - getLoaderCount() - getBottomCount())
        }

        return (moreThanOnePage && recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE) ||
                recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE
    }

    /**
     * Reset span size for GridLayoutManager;
     * Bind target recyclerView;
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val oldLookup = layoutManager.spanSizeLookup
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when {
                        isHeaderPosition(position) || isFooterPosition(position)
                                || isLoaderPosition(position)
                                || isBottomPosition(position)
                        -> layoutManager.spanCount
                        else -> {
                            val size = getRealSpanSize(position - hfHelper.getHeaderCount())
                            if (size != -1) {
                                if (size > layoutManager.spanCount || size <= 0) {
                                    throw IllegalArgumentException("Wrong span size parameter, span size : $size ")
                                }
                                size
                            } else
                                oldLookup.getSpanSize(position)
                        }
                    }
                }
            }
        }
    }

    /**
     * FullSpan for StaggeredGridLayoutManager;
     */
    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        val position = holder.adapterPosition
        if (position != RecyclerView.NO_POSITION && (isHeaderPosition(position) || isFooterPosition(position)
                        || isLoaderPosition(position) || isBottomPosition(position))) {
            val lp = holder.itemView.layoutParams
            if (lp is StaggeredGridLayoutManager.LayoutParams) {
                lp.isFullSpan = true
            }
        }
    }

    fun addHeaderView(key: String, view: View) {
        hfHelper.addHeaderView(key, view)
        notifyDataSetChanged()
    }

    fun addHeaderView(view: View) {
        hfHelper.addHeaderView(view)
        notifyDataSetChanged()
    }

    fun removeHeader(key: String) {
        hfHelper.removeHeader(key)
        notifyDataSetChanged()
    }

    fun removeAllHeaders() {
        hfHelper.removeAllHeaders()
        notifyDataSetChanged()
    }

    fun addFooterView(view: View) {
        hfHelper.addFooter(view)
        notifyDataSetChanged()
    }

    fun addFooterView(key: String, view: View) {
        hfHelper.addFooter(key, view)
        notifyDataSetChanged()
    }

    fun removeFooter(key: String) {
        hfHelper.removeFooter(key)
        notifyDataSetChanged()
    }

    fun removeAllFooters() {
        hfHelper.removeAllFooters()
        notifyDataSetChanged()
    }

    fun addLoaderView(iLoaderView: ILoaderView) {
        loadHelper.initializerLoader(iLoaderView)
    }

    fun removeLoaderView() {
        loadHelper.removeLoaderView()
    }

    fun getLoadingLayout(): View? = loadHelper.getLoadingView()

    fun getLoadErrLayout(): View? = loadHelper.getLoadErrView()

    fun autoLoadMore(auto: Boolean) {
        loadHelper.autoLoad = auto
    }

    fun openLoadMore(open: Boolean) {
        loadHelper.openLoad = open
        notifyDataSetChanged()
    }

    fun loadMoreFail() {
        loadHelper.notifyStateChanged(LoadState.ERROR)
    }

    fun loadMoreSuccess() {
        loadHelper.notifyStateChanged(LoadState.NORMAL)
    }

    fun goReloading() {
        loadHelper.notifyStateChanged(LoadState.LOADING)
        onLoadMoreListener?.onLoadMore()
    }

    fun isHasMore(hasMore: Boolean) {
        loadHelper.isHasMore(hasMore)
        notifyDataSetChanged()
    }

    fun addBottomView(view: View?) {
        btmHelper.addBottomView(view)
        notifyDataSetChanged()
    }

    fun getHeaderCount(): Int = hfHelper.getHeaderCount()
    fun getFooterCount(): Int = hfHelper.getFooterCount()
}
