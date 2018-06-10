package com.dengzq.demo.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.dengzq.baservadapter.BaseViewHolder
import com.dengzq.baservadapter.listener.OnFooterClickListener
import com.dengzq.baservadapter.listener.OnHeaderClickListener
import com.dengzq.baservadapter.listener.OnItemClickListener
import com.dengzq.demo.R
import com.dengzq.demo.service.ModelService
import kotlinx.android.synthetic.main.activity_main.*

/**
 * <p>author    dengzq</P>
 * <p>date      2018/6/8 上午12:30</p>
 * <p>package   com.dengzq.demo.ui.fragment</p>
 * <p>readMe    Header and footer 示例
 *
 * 对于header和footer，只需inflate之后添加到adapter即可;
 *
 * 注意：如果需要在某些情况下移除header或者footer,请在添加时绑定对应的key,
 * 通过adapter.removeHeader(key)、adapter.removeFooter(key)进行移除;
 *
 * </p>
 */
class HeaderAndFooterFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root: View
        if (view == null) {
            root = inflater.inflate(R.layout.activity_main, container, false)
            return root
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        simple_refresh.setPullDownEnable(false)
        simple_refresh.setPullUpEnable(false)

        val adapter = SingleTypeAdapter(activity!!, ModelService.getNormalBeanList(2))
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.adapter = adapter

        val header1 = layoutInflater.inflate(R.layout.layout_header, recycler_view, false)
        adapter.addHeaderView("header1", header1)

        val header2 = layoutInflater.inflate(R.layout.layout_head2, recycler_view, false)
        adapter.addHeaderView("header2", header2)

        val footer = layoutInflater.inflate(R.layout.layout_footer, recycler_view, false)
        adapter.addFooterView("footer1", footer)

        adapter.itemClickListener = object : OnItemClickListener {
            override fun onItemClick(v: View, holder: BaseViewHolder, position: Int) {
                Toast.makeText(activity, "Click item $position", Toast.LENGTH_SHORT).show()
            }
        }
        adapter.headerClickListener = object : OnHeaderClickListener {
            override fun onHeaderClick(v: View, holder: BaseViewHolder, key: String) {

                if (key != "header2" && key != "header1") {
                    adapter.removeHeader(key)
                    adapter.notifyDataSetChanged()
                    return
                }

                if (key == "header2") {
                    val header = layoutInflater.inflate(R.layout.layout_head2, recycler_view, false)
                    header.findViewById<TextView>(R.id.tv_head).text = "hello sub header "
                    header.findViewById<TextView>(R.id.tv_tip).text = "(Click to remove)"
                    adapter.addHeaderView(header)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(activity, "Click header-> $key ", Toast.LENGTH_SHORT).show()
                }
            }
        }

        adapter.footerClickListener = object : OnFooterClickListener {
            override fun onFooterClick(v: View, holder: BaseViewHolder, key: String) {

                if (key == "footer1") {
                    val footer2 = layoutInflater.inflate(R.layout.layout_footer, recycler_view, false)
                    footer2.findViewById<TextView>(R.id.tv_tip).text = "(Click to remove)"
                    footer2.findViewById<TextView>(R.id.tv_footer).text = "hello sub footer"
                    adapter.addFooterView(footer2)
                } else {
                    adapter.removeFooter(key)
                }
            }
        }
    }
}