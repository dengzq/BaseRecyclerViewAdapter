package com.dengzq.baservadapter.interfaces

import android.view.ViewGroup
import com.dengzq.baservadapter.BaseViewHolder

/**
 * <p>author    dengzq</P>
 * <p>date      2019/4/3 10:01</p>
 * <p>package   com.dengzq.baservadapter.interfaces</p>
 * <p>readMe    Adapter processor used to separate different view type</p>
 */
internal interface IProcessor {
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder
    fun getItemCount(): Int
    fun onBindViewHolder(holder: BaseViewHolder, position: Int)
    fun getItemViewType(position: Int): Int
    fun isProcess(): Boolean
}