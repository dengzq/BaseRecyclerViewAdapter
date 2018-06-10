package com.dengzq.demo.ui.view

import com.dengzq.demo.model.ClassifyBanner
import com.dengzq.demo.model.ClassifyNews
import com.dengzq.demo.model.ClassifyTab
import com.dengzq.demo.model.ModelBean

/**
 * <p>author    dengzq</P>
 * <p>date      2018/6/7 下午6:32</p>
 * <p>package   com.dengzq.demo.ui.view</p>
 * <p>readMe    TODO</p>
 */
interface IClassifyView {
    fun notifyDataSetChanged()
}

interface IClassifyPresenter {

    fun getClassifyBanner(): List<ClassifyBanner>

    fun getClassifyTab(): List<ClassifyTab>

    fun getClassifyMenu(): List<ModelBean>

    fun getClassifyNews(): List<ClassifyNews>

    fun requestNetMenu()

    fun requestNetBanner()

    fun requestNetTab()

    fun requestNetNews()

}