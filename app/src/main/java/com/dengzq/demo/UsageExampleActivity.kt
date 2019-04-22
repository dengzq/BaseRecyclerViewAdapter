@file:Suppress("UnnecessaryVariable")

package com.dengzq.demo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.dengzq.demo.ui.fragment.*
import kotlinx.android.synthetic.main.activity_normal.*

/**
 * <p>author    dengzq</P>
 * <p>date      2018/6/7 下午5:35</p>
 * <p>package   com.dengzq.demo</p>
 * <p>readMe    基本使用示例</p>
 */
class UsageExampleActivity : AppCompatActivity() {

    val fragments = getFragmentList()
    val titles = getTitleList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal)

        view_pager.adapter = MyFragmentAdapter(supportFragmentManager)
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun getFragmentList(): ArrayList<Fragment> {
        val fragments = ArrayList<Fragment>()
        fragments.add(EmptyFragment())
        fragments.add(HeaderAndFooterFragment()) //header和footer示例
        fragments.add(LoadAndBottomFragment())   //加载更多和no more示例
        fragments.add(SingleFragment())          //普通用法示例
        fragments.add(MultiItemFragment())       //多类型item示例
        fragments.add(MultiClassifyFragment())   //多类型分类示例
        fragments.add(GridSpanFragment())        //多类型span size示例
        fragments.add(StaggeredGridFragment())
        return fragments
    }

    private fun getTitleList(): ArrayList<String> {
        val titles = arrayListOf("Empty","header and footer", "loader more", "single", "multi item", "multi classify", "multi span"
                , "StaggeredGrid")
        return titles
    }

    inner class MyFragmentAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

        override fun getPageTitle(position: Int): CharSequence? = titles[position]
    }

}