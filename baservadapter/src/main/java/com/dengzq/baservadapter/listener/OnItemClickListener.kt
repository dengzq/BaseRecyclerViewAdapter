package com.dengzq.baservadapter.listener

import android.view.View
import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.constants.LoadState

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/21 下午7:33</p>
 * <p>package   com.dengzq.baservadapter.listener</p>
 * <p>readMe    Click Event</p>
 */
interface OnFooterClickListener {
    fun onFooterClick(v: View, holder: BaseViewHolder, key: String)
}

interface OnItemClickListener {
    fun onItemClick(v: View, holder: BaseViewHolder, position: Int)
}

interface OnItemLongClickListener {
    fun onItemLongClick(v: View, holder: BaseViewHolder, position: Int): Boolean
}

interface OnHeaderClickListener {
    fun onHeaderClick(v: View, holder: BaseViewHolder, key: String)
}

interface OnLoaderClickListener {
    fun onItemClick(v: View, holder: BaseViewHolder, state: LoadState)
}

interface OnBottomClickListener{
    fun onItemClick(v: View, holder: BaseViewHolder, position: Int)
}