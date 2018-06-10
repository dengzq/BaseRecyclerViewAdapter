package com.dengzq.demo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.dengzq.demo.R
import com.dengzq.simplerefreshlayout.IBottomWrapper

/**
 * <p>author    dengzq</P>
 * <p>date      2018/6/10 上午1:03</p>
 * <p>package   com.dengzq.demo.widget</p>
 * <p>readMe    TODO</p>
 */
class SimpleBottom : LinearLayout, IBottomWrapper {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    init {
        val layout = LayoutInflater.from(context).inflate(R.layout.layout_bottom, this, false)
        addView(layout)
    }

    override fun showBottom() {
    }

    override fun getBottomView(): View = this

}