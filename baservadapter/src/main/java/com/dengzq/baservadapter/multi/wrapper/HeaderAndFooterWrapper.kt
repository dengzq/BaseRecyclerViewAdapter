package com.dengzq.baservadapter.multi.wrapper

import android.view.View
import com.dengzq.baservadapter.BaseRvAdapter

/**
 * <p>author    dengzq</P>
 * <p>date      2018/5/21 下午5:10</p>
 * <p>package   com.dengzq.baservadapter.multi</p>
 * <p>readMe    Wrapper for header and footer</p>
 */
internal class HeaderAndFooterWrapper {

    private val headerObjs = ArrayList<InfoObj>()
    private val footerObjs = ArrayList<InfoObj>()

    fun getHeaderView(viewType: Int): View? {

        for (headerObj in headerObjs) {
            if (headerObj.viewType == viewType)
                return headerObj.view
        }
        return null
    }

    fun getHeaderType(position: Int): Int = headerObjs[position].viewType

    fun getHeaderKey(viewType: Int): String {

        for (headerObj in headerObjs) {
            if (headerObj.viewType == viewType)
                return headerObj.key
        }
        return "no such header-key for viewType: $viewType"
    }

    fun getFooterView(viewType: Int): View? {

        for (footerObj in footerObjs) {
            if (footerObj.viewType == viewType)
                return footerObj.view
        }
        return null
    }

    fun getFooterType(position: Int): Int = footerObjs[position].viewType

    fun getFooterKey(viewType: Int): String {

        for (footerObj in footerObjs) {
            if (footerObj.viewType == viewType)
                return footerObj.key
        }

        return "no such footer-key for viewType:$viewType"
    }

    fun getHeaderCount(): Int = headerObjs.size
    fun getFooterCount(): Int = footerObjs.size

    private fun buildKey(source: ArrayList<InfoObj>): String {
        val key: String = (Math.random() * BaseRvAdapter.FOOTER_INDEX).toInt().toString()

        if (checkKeyExist(key, source)) {
            return buildKey(source)
        }
        return key
    }

    private fun buildViewType(source: ArrayList<InfoObj>): Int {
        val viewType = if (source == headerObjs) {
            (Math.random() * BaseRvAdapter.FOOTER_INDEX).toInt()
        } else {
            (Math.random() * BaseRvAdapter.FOOTER_INDEX).toInt() + BaseRvAdapter.FOOTER_INDEX
        }
        if (checkViewTypeExist(viewType, source)) {
            return buildViewType(source)
        }
        return viewType
    }

    private fun checkViewTypeExist(viewType: Int, source: ArrayList<InfoObj>): Boolean {

        if (source.isEmpty()) return false

        for (infoObj in source) {
            if (infoObj.viewType == viewType)
                return true
        }
        return false
    }

    private fun checkKeyExist(key: String, source: ArrayList<InfoObj>): Boolean {

        if (source.isEmpty() || key.isEmpty()) return false

        for (infoObj in source) {
            if (infoObj.key == key)
                return true
        }

        return false
    }

    fun addHeaderView(view: View) {
        addHeaderView(buildKey(headerObjs), buildViewType(headerObjs), view)
    }

    fun addHeaderView(key: String, view: View) {
        addHeaderView(key, buildViewType(headerObjs), view)
    }

    private fun addHeaderView(key: String, viewType: Int, view: View) {

        if (key.isEmpty()) throw IllegalArgumentException("Invalid header view key: $key")


        for (headerObj in headerObjs) {
            if (headerObj.key == key)
                throw IllegalArgumentException("HeaderView with key=$key is already added to the recyclerView !")
        }

        headerObjs.add(InfoObj(viewType, key, view))
    }

    fun removeHeader(key: String) {

        val iterator = headerObjs.iterator()

        while (iterator.hasNext()) {
            val infoObj = iterator.next()
            if (key == infoObj.key) {
                iterator.remove()
                break
            }
        }
    }

    fun removeAllHeaders() {
        if (headerObjs.isEmpty()) return
        headerObjs.clear()
    }

    fun addFooter(view: View) {
        addFooter(buildKey(footerObjs), buildViewType(footerObjs), view)
    }

    fun addFooter(key: String, view: View) {
        addFooter(key, buildViewType(footerObjs), view)
    }

    private fun addFooter(key: String, viewType: Int, view: View) {

        if (key.isEmpty()) throw IllegalArgumentException("Invalid footer view key: $key")

        for (footerObj in footerObjs) {
            if (footerObj.key == key)
                throw IllegalArgumentException("FooterView with key=$key is already added to the recyclerView !")
        }

        footerObjs.add(InfoObj(viewType, key, view))
    }

    fun removeFooter(key: String) {

        val iterator = footerObjs.iterator()

        while (iterator.hasNext()) {
            val infoObj = iterator.next()
            if (key == infoObj.key) {
                iterator.remove()
                break
            }
        }
    }

    fun removeAllFooters() {
        if (footerObjs.isEmpty()) return
        footerObjs.clear()
    }

    inner class InfoObj(var viewType: Int, var key: String, var view: View)
}