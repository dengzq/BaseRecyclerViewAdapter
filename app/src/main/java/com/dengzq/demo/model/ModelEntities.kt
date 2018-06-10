package com.dengzq.demo.model

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/17 下午4:35</p>
 * <p>package   com.dengzq.demo.model</p>
 * <p>readMe    TODO</p>
 */
data class ModelBean(var nick: String, var avatar: Int, var type: Int)

data class NormalBean(var title: String, var imgRes: Int)

data class ClassifyBanner(var banners: ArrayList<Int>)

data class ClassifyTab(var tabs: ArrayList<NormalBean>)

data class ClassifyNews(var news: String)

