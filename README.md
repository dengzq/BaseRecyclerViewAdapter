# BaseRecyclerViewAdapter

* 1.支持header和footer
* 2.支持loader和 no more layout
* 3.支持带泛型的单类型，多类型布局
* 4.支持不带泛型的多类型模块用法
* 5.支持不同delegate不同span size

### demo演示
![headerAndFooter.gif](https://github.com/dengzq/BaseRecyclerViewAdapter/tree/master/image/img_header.gif)
![loader.gif](https://github.com/dengzq/BaseRecyclerViewAdapter/tree/master/image/img_loader.gif)
![singleItem.gif](https://github.com/dengzq/BaseRecyclerViewAdapter/tree/master/image/img_single.gif)

![multiItem.gif](https://github.com/dengzq/BaseRecyclerViewAdapter/tree/master/image/img_multi_item.gif)
![multiClassify.gif](https://github.com/dengzq/BaseRecyclerViewAdapter/tree/master/image/img_multi_classify.gif)
![spanSize.gif](https://github.com/dengzq/BaseRecyclerViewAdapter/tree/master/image/img_span_size.gif)


### 使用

#### Gradle
```
compile 'com.dengzq.widget:baservadapter:0.7.0'
```
#### Maven
```
<dependency>
  <groupId>com.dengzq.widget</groupId>
  <artifactId>baservadapter</artifactId>
  <version>0.7.0</version>
  <type>pom</type>
</dependency>
```

#### 基本使用

##### 一. HeaderAndFooter

```
val header = layoutInflater.inflate(R.layout.layout_header, recycler_view, false)
adapter.addHeaderView(header)

如果需要在某些情况下移除header，请给在添加时绑定一个key
add : adapter.addHeaderView(key,header)
remove: adapter.removeHeader(key)

val footer = layoutInflater.inflate(R.layout.layout_footer, recycler_view, false)
adapter.addFooterView(footer)

//移除footer
adapter.addFooterView(key, footer)
adapter.removeFooter(key)
```

</p>

##### 二.loadMore And No more

###### 1. 设置loadMoreView,继承ILoaderView 接口
```
class LoaderView : ILoaderView {

    override fun getLoadingLayoutId(): Int = R.layout.layout_loader

    override fun getErrorLayoutId(): Int = R.layout.layout_load_err
}
```

###### 2. 添加loaderView
```
val loader = LoaderView()
adapter.addLoaderView(loader)

//默认情况下，添加loader则开启加载更多功能，如果需要关闭：
//adapter.removeLoaderView() or adapter.openLoadMore(false)
```

###### 3. 加载更多回调
```
adapter.loadMoreListener = object : OnLoadMoreListener {
            override fun onLoadMore() {
               //do what you what to add data here;
            }
        }
```

当loadMore请求结束，请回调

```
adapter.loadMoreSuccess() //loadMore success
adapter.loadMoreFail()    //loadMore fail, show load error layout if it's exist;
```

###### 4. 关于autoLoad,加载失败

默认情况下，开启的是loader autoLoad方式，如果需要点击加载ClickLoad
```
adapter.autoLoadMore(false)
adapter.loaderClickListener=object :OnLoaderClickListener{
            override fun onItemClick(v: View, holder: BaseViewHolder, state: LoadState) {
                if (state==LoadState.LOADING){
                    //Click to loadMore data here;
                }
                else if (state==LoadState.ERROR){
                    //error layout was clicked, reload data;
                    adapter.goReloading()
                }
            }
        }
```


</p>
</p>

##### 三. 单类型，多类型功能

###### 1. 单类型使用
```
class SingleTypeAdapter(context: Context, list: ArrayList<T>) : CommonAdapter<T>(context, layout, list) {
    override fun convert(holder: BaseViewHolder, t: T, position: Int) {
        //do what you want when bindViewHolder
    }
}
```

###### 2. 多类型 [item位置不确定,类似聊天页面]
```
1).继承ItemViewDelegate<T>,实现当前类型item的视图功能
class MultiModelDelegate:ItemViewDelegate<ModelBean>(){

    override fun isForViewType(t: ModelBean, position: Int): Boolean=t.type==0

    override fun getItemViewLayoutId(): Int= R.layout.item_adapter_common

    override fun convert(holder: BaseViewHolder, t: ModelBean, position: Int) {
        holder.setText(R.id.tv_nick, t.nick)
    }
}

2).继承MultiItemTypeAdapter<T>,将delegate添加到adapter
class MultiItemAdapter(context: Context, list: List<ModelBean>) : MultiItemTypeAdapter<ModelBean>(context, list) {
    init {
        addItemViewDelegate(MultiModelDelegate())
        addItemViewDelegate(MultiTextDelegate())
    }
}

3).设置adapter
recycler_view.adapter = MultiItemAdapter(context, list)
```

###### 3. 多类型 [item位置确定，并且页面由几个接口组成，类似app首页]
```
1).继承ItemClassifyDelegate,实现当前类型item的视图功能
class ClassifyMsgDelegate(private val presenter: IClassifyPresenter) : ItemClassifyDelegate() {
    override fun getItemSize(): Int = presenter.getClassifyNews().size

    override fun needShow(): Boolean = getItemSize() > 0

    override fun getItemViewLayoutId(): Int = R.layout.item_adapter_text

    override fun convert(holder: BaseViewHolder, position: Int) {
    }
}

2).继承MultiItemClassifyAdapter,添加delegate
class ClassifyAdapter(context: Context, presenter: IClassifyPresenter) : MultiItemClassifyAdapter(context) {
    init {
        addItemClassifyDelegate(ClassifyBannerDelegate(context, presenter))
        addItemClassifyDelegate(ClassifyMenuDelegate(presenter))
    }
}

3).设置adapter
recycler_view.adapter = ClassifyAdapter(activity!!, presenter)
```

##### 四.Item间不同的spanSize
```
//只需要让delegate重写getItemSpanSize(position: Int)方法，GridLayoutManager会获取到该
//span size改变布局;

class GridSpanDelegate : ItemViewDelegate<ModelBean>() {

    override fun getItemSpanSize(position: Int): Int {
        return when (position % 7) {
            0 -> 4
            1, 2 -> 2
            else -> 1
        }
    }
}
```

</p>

更丰富的 刷新、load more和 no more 效果 请结合</br>
[SimpleRefreshLayout](https://github.com/dengzq/SimpleRefreshLayout)实现或查看demo;


### Todo list

1.预加载布局</br>
2.拖拽、侧滑、分级</br>
3.动画</br>
etc.

### 特别感谢

[hongyangAndroid大大](https://github.com/hongyangAndroid/baseAdapter)</br>
我的小伙伴[小太阳](https://github.com/juicy-zx)

#### 如果你喜欢本项目，请点个✨哦~
