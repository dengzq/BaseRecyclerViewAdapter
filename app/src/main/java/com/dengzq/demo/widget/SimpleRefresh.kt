package com.dengzq.demo.widget

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.dengzq.demo.R
import com.dengzq.simplerefreshlayout.IHeaderWrapper

/**
 * <p>author    dengzq</P>
 * <p>date      2018/6/9 下午5:17</p>
 * <p>package   com.dengzq.demo.widget</p>
 * <p>readMe    TODO</p>
 */
class SimpleRefresh : LinearLayout, IHeaderWrapper {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    init {
        val layout = LayoutInflater.from(context).inflate(R.layout.layout_refresh_header, this, false)
        val params = LinearLayout.LayoutParams(-1, -2)
        params.gravity = Gravity.BOTTOM
        addView(layout, params)
        setBackgroundColor(ContextCompat.getColor(context, R.color.background))
    }

    override fun pullDown() {
        //do nothing
    }

    override fun pullDownRelease() {
        //do nothing
    }

    override fun pullDownReleasable() {
        //do nothing
    }

    override fun getHeaderView(): View = this
}