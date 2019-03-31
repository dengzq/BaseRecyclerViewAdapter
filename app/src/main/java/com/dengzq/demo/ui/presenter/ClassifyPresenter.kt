package com.dengzq.demo.ui.presenter

import android.os.Handler
import com.dengzq.demo.model.ClassifyBanner
import com.dengzq.demo.model.ClassifyNews
import com.dengzq.demo.model.ClassifyTab
import com.dengzq.demo.model.ModelBean
import com.dengzq.demo.service.ModelService
import com.dengzq.demo.ui.view.IClassifyPresenter
import com.dengzq.demo.ui.view.IClassifyView

/**
 * <p>author    dengzq</P>
 * <p>date      2018/6/7 下午6:31</p>
 * <p>package   com.dengzq.demo.ui.presenter</p>
 * <p>readMe    TODO</p>
 */
class ClassifyPresenter(private val classifyView: IClassifyView) : IClassifyPresenter {
    override fun getClassifyNews(): List<ClassifyNews> = news

    private val banners = ArrayList<ClassifyBanner>()

    private val tabs = ArrayList<ClassifyTab>()
    private val menus = ArrayList<ModelBean>()
    private val news = ArrayList<ClassifyNews>()
    override fun getClassifyBanner(): List<ClassifyBanner> = banners

    override fun getClassifyTab(): List<ClassifyTab> = tabs

    override fun getClassifyMenu(): List<ModelBean> = menus

    //模拟网络请求轮播图
    override fun requestNetBanner() {
        Handler().postDelayed({

            banners.clear()
            banners.addAll(ModelService.getBanner(5))

            classifyView.notifyDataSetChanged()

        }, 200)
    }

    //模拟网络请求广告
    override fun requestNetTab() {
        Handler().postDelayed({

            tabs.clear()
            tabs.addAll(ModelService.getTab(1))

            classifyView.notifyDataSetChanged()

        }, 200)
    }

    //模拟网络请求菜单列表
    override fun requestNetMenu() {
        Handler().postDelayed({

            menus.clear()
            menus.addAll(ModelService.getModelBeanList(8))

            classifyView.notifyDataSetChanged()

        }, 200)
    }

    //模拟网络请求文本信息
    override fun requestNetNews() {
        Handler().postDelayed({

            news.clear()
            news.addAll(ModelService.getNews(40))

            classifyView.notifyDataSetChanged()

        }, 200)
    }
}