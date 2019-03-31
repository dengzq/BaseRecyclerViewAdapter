@file:Suppress("MemberVisibilityCanPrivate", "PropertyName", "PrivatePropertyName")

package com.dengzq.baservadapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
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
         *  Range of ViewType
         * |------------------------|
         * |   Header[0~100000)     |
         * |------------------------|
         * |   Real Item[300000~ )  |
         * |------------------------|
         * |  Footer[100000~200000) |
         * |------------------------|
         * |     Loader(200001)     |
         * |------------------------|
         * |     Bottom(200002)     |
         * |------------------------|
         */
        internal const val FOOTER_INDEX = 100000
        internal const val LOADER_INDEX = 200001
        internal const val BOTTOM_INDEX = 200002
        internal const val REALER_INDEX = 300000
    }

    private val hfHelper = HeaderAndFooterWrapper()
    private val loadHelper = LoaderWrapper(context)
    private val btmHelper = BottomWrapper()

    private var recyclerView: RecyclerView? = null

    var onItemClickListener: OnItemClickListener? = null
    var onItemLongClickListener: OnItemLongClickListener? = null
    var onHeaderClickListener: OnHeaderClickListener? = null
    var onFooterClickListener: OnFooterClickListener? = null
    var onLoaderClickListener: OnLoaderClickListener? = null
    var onBottomClickListener: OnBottomClickListener? = null
    var onLoadMoreListener: OnLoadMoreListener? = null

    protected abstract fun getRealSpanSize(position: Int, spanCount: Int): Int
    protected abstract fun getRealItemCount(): Int
    protected abstract fun getRealViewType(position: Int): Int
    protected abstract fun createRealHolder(parent: ViewGroup, viewType: Int): BaseViewHolder
    protected abstract fun bindRealHolder(holder: BaseViewHolder, position: Int)
    protected open fun onDelegateViewAttachedToWindow(position: Int, holder: BaseViewHolder) {}
    protected open fun onDelegateViewDetachedFromWindow(position: Int, holder: BaseViewHolder) {}
    protected open fun onDelegateFailedToRecycleView(position: Int, holder: BaseViewHolder): Boolean = false
    protected open fun onDelegateViewRecycled(position: Int, holder: BaseViewHolder) {}
    protected open fun convertHeader(holder: BaseViewHolder, position: Int, key: String) {}
    protected open fun convertFooter(holder: BaseViewHolder, position: Int, key: String) {}
    protected open fun convertLoader(holder: BaseViewHolder, position: Int) {}
    protected open fun convertBottom(holder: BaseViewHolder, position: Int) {}

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
            isHeaderPosition(position) -> convertHeader(holder, position, hfHelper.getHeaderKey(getItemViewType(position)))
            isFooterPosition(position) -> convertFooter(holder, position, hfHelper.getFooterKey(getItemViewType(position)))
            isBottomPosition(position) -> convertBottom(holder, position)
            isLoaderPosition(position) -> {
                convertLoader(holder, position)
                autoLoadMore()
            }
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
                    ?: false
        }
    }

    private fun isHasContent(): Boolean = getHeaderCount() + getRealItemCount() + getFooterCount() > 0

    private fun isHeaderPosition(position: Int): Boolean = hfHelper.getHeaderCount() > 0 && position < hfHelper.getHeaderCount()

    private fun isFooterPosition(position: Int): Boolean = hfHelper.getFooterCount() > 0 && position >= hfHelper.getHeaderCount() + getRealItemCount()
            && position < hfHelper.getHeaderCount() + hfHelper.getFooterCount() + getRealItemCount()

    private fun isLoaderPosition(position: Int): Boolean = getLoaderCount() > 0 && position == hfHelper.getHeaderCount() + hfHelper.getFooterCount() + getRealItemCount()

    private fun isBottomPosition(position: Int): Boolean = getBottomCount() > 0 && position == hfHelper.getHeaderCount() + hfHelper.getFooterCount() +
            getRealItemCount()

    private fun isRealPosition(position: Int): Boolean = (!isHeaderPosition(position)) && (!isFooterPosition(position)) &&
            (!isLoaderPosition(position)) && (!isBottomPosition(position))

    private fun getLoaderCount(): Int = if (loadHelper.hasMore && isHasContent()) loadHelper.getLoaderCount() else 0

    private fun getBottomCount(): Int = if (!loadHelper.hasMore && isHasContent()) btmHelper.getBottomCount() else 0

    private fun getRealItemPosition(position: Int) = position - hfHelper.getHeaderCount()

    /**
     * Auto load more data;
     * If you already set [OnLoadMoreListener], remember to invoke [loadMoreSuccess] or
     * [loadMoreFail] to finish load more action. Otherwise, try to notify adapter if there is
     * no data by [isHasMore];
     */
    private fun autoLoadMore() {
        //No content, hide loader;
        if (getHeaderCount() + getFooterCount() + getRealItemCount() <= 0) {
            loadHelper.notifyStateChanged(LoadState.NORMAL)
            return
        }
        //Loading ,return;
        if (loadHelper.state == LoadState.LOADING) return

        //Don't has more,return;
        if (!loadHelper.hasMore) {
            loadHelper.notifyStateChanged(LoadState.NORMAL)
            return
        }

        loadHelper.notifyStateChanged(LoadState.LOADING)

        //Auto load by loadMoreLis, or click to load by loadClickLis;
        if (loadHelper.autoLoad) {
            recyclerView?.post { onLoadMoreListener?.onLoadMore() }
        }
    }

    /**
     * Set Span size for gridLayoutManager;
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
                            val size = getRealSpanSize(position - hfHelper.getHeaderCount(), layoutManager.spanCount)
                            when (size) {
                                -2 -> return layoutManager.spanCount  //spanCount for default value -2
                                -1 -> return oldLookup.getSpanSize(position) //default spanSize which is 1
                                else -> {
                                    if (size > layoutManager.spanCount || size < 0) {
                                        throw IllegalArgumentException("Wrong span size parameter, span size : $size ")
                                    }
                                    size
                                }
                            }
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

        if (position != RecyclerView.NO_POSITION && isRealPosition(position)) {
            onDelegateViewAttachedToWindow(getRealItemPosition(position), holder)
        }
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        val position = holder.adapterPosition
        if (position != RecyclerView.NO_POSITION && isRealPosition(position)) {
            onDelegateViewDetachedFromWindow(getRealItemPosition(position), holder)
        }
    }

    override fun onFailedToRecycleView(holder: BaseViewHolder): Boolean {
        val position = holder.adapterPosition
        if (position != RecyclerView.NO_POSITION && isRealPosition(position)) {
            return onDelegateFailedToRecycleView(getRealItemPosition(position), holder)
        }
        return super.onFailedToRecycleView(holder)
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        val position = holder.adapterPosition
        if (position != RecyclerView.NO_POSITION && isRealPosition(position)) {
            onDelegateViewRecycled(getRealItemPosition(position), holder)
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
        if (loadHelper.openLoad != open) {
            loadHelper.openLoad = open
            notifyDataSetChanged()
        }
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

    fun goEmptyReloading() {
        loadHelper.notifyStateChanged(LoadState.LOADING)
    }

    fun isHasMore(hasMore: Boolean) {
        if (loadHelper.hasMore != hasMore) {
            loadHelper.isHasMore(hasMore)
            notifyDataSetChanged()
        }
    }

    fun addBottomView(view: View?) {
        btmHelper.addBottomView(view)
        notifyDataSetChanged()
    }

    fun removeBottomView() {
        btmHelper.removeBottomView()
        notifyDataSetChanged()
    }

    fun getHeaderCount(): Int = hfHelper.getHeaderCount()
    fun getFooterCount(): Int = hfHelper.getFooterCount()
}
