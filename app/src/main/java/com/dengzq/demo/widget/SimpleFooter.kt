@file:Suppress("PrivatePropertyName")

package com.dengzq.demo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.dengzq.demo.R
import com.dengzq.simplerefreshlayout.IFooterWrapper

/**
 * <p>author    dengzq</P>
 * <p>date      2018/6/5 下午5:59</p>
 * <p>package   com.dengzq.demo.load</p>
 * <p>readMe    TODO</p>
 */
class SimpleFooter : LinearLayout, IFooterWrapper {

    private val tv_content: TextView

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr)

    init {
        val layout = LayoutInflater.from(context).inflate(R.layout.layout_refresh_footer, this, false)
        tv_content = layout.findViewById(R.id.tv_refresh_footer)
        addView(layout)
    }

    override fun pullUpRelease() {
        tv_content.text = "<系啦 ! 拼命load>"
    }

    override fun pullUp() {
        tv_content.text = "<hello !  靓仔>"
    }

    override fun getFooterView(): View = this

    override fun pullUpFinish() {
        tv_content.text = "<ok! 搞掂晒>"
    }

    override fun pullUpReleasable() {
        tv_content.text = "<喂快松手啦 ！唔该>"
    }

}