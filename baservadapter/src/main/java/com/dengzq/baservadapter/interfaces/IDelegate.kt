package com.dengzq.baservadapter.interfaces

import com.dengzq.baservadapter.BaseViewHolder

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/30 下午11:19</p>
 * <p>package   com.dengzq.baservadapter.interfaces</p>
 * <p>readMe    IDelegate</p>
 */
interface IDelegate {

    /**
     * Return span size for gridLayoutManager when
     * -1: default spanSize(usually 1);
     * -2: set spanCount for this item's size
     * else: set size you want;
     *
     * @param spanCount spanCount of gridLayoutManager
     * @link [com.dengzq.baservadapter.BaseRvAdapter.onAttachedToRecyclerView]
     */
    fun getItemSpanSize(position: Int, spanCount: Int): Int

    /**
     * Called when real item attached to window
     */
    fun onDelegateViewAttachedToWindow(position: Int, holder: BaseViewHolder)

    /**
     * Called when real item detached from window
     */
    fun onDelegateViewDetachedFromWindow(position: Int, holder: BaseViewHolder)

    /**
     * Called when real item fail to RecyclerView;
     * @return True if the View should be recycled, false otherwise. Note that if this method
     * returns <code>true</code>, RecyclerView <em>will ignore</em> the transient state of
     * the View and recycle it regardless. If this method returns <code>false</code>,
     * RecyclerView will check the View's transient state again before giving a final decision.
     * Default implementation returns false.
     */
    fun onDelegateFailedToRecycleView(position: Int, holder: BaseViewHolder): Boolean = false

    /**
     * Called when real item was recycled by RecyclerView;
     */
    fun onDelegateViewRecycled(position: Int, holder: BaseViewHolder)
}