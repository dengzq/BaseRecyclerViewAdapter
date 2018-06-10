package com.dengzq.demo.service

import com.dengzq.demo.R
import com.dengzq.demo.model.*


/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/17 下午4:36</p>
 * <p>package   com.dengzq.demo.model</p>
 * <p>readMe    TODO</p>
 */
object ModelService {

    val SINGLE = "single type"
    val MULTIPLE = "multi item type "
    val LOAD = "load more "
    val BOTTOM = "show no more"
    val HEADER_AND_FOOTER = "header and footer"
    val MULTI_SPAN = "multi span size"

    private val imgs: IntArray = intArrayOf(R.mipmap.icon_card01, R.mipmap.icon_card02,
            R.mipmap.icon_card03, R.mipmap.icon_card04, R.mipmap.icon_card05)

    fun getModelBeanList(count: Int): MutableList<ModelBean> {
        val list = ArrayList<ModelBean>()
        (0 until count).mapTo(list) {
            ModelBean("Eco Uzi  $it", getImageRes(), (Math.random() * 2).toInt())
        }
        return list
    }

    fun getNormalBeanList(count: Int): ArrayList<NormalBean> {
        val list = ArrayList<NormalBean>()
        (0 until count).mapTo(list) {
            NormalBean("Eco Uzi $it", getImageRes())
        }
        return list
    }

    fun getMainBeanList(): List<NormalBean> {
        val list = ArrayList<NormalBean>()
        list.add(NormalBean(SINGLE, getImageRes()))
        list.add(NormalBean(MULTIPLE, getImageRes()))
        list.add(NormalBean(LOAD, getImageRes()))
        list.add(NormalBean(BOTTOM, getImageRes()))
        list.add(NormalBean(HEADER_AND_FOOTER, getImageRes()))
        list.add(NormalBean(MULTI_SPAN, getImageRes()))
        return list
    }

    fun getBanner(count: Int): ArrayList<ClassifyBanner> {
        val classifyBanner = ClassifyBanner(ArrayList())

        (0 until count).forEach {
            classifyBanner.banners.add(getImageRes())
        }
        return arrayListOf(classifyBanner)
    }

    fun getTab(count: Int): ArrayList<ClassifyTab> {

        val list = ArrayList<ClassifyTab>()
        for (i in 0 until count) {
            val normalList = ArrayList<NormalBean>()
            normalList.addAll(getNormalBeanList(10))
            val classifyTab = ClassifyTab(normalList)

            list.add(classifyTab)
        }
        return list
    }

    fun getNews(count: Int): ArrayList<ClassifyNews> {
        val list = ArrayList<ClassifyNews>()
        for (i in 0 until count) {
            val news = ClassifyNews("item $i")
            list.add(news)
        }
        return list
    }

    private fun getImageRes(): Int = imgs[(Math.random() * 5).toInt()]


}