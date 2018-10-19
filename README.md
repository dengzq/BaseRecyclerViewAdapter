# BaseRecyclerViewAdapter

* 1.支持header和footer
* 2.支持loader和 no more layout
* 3.支持带泛型的单类型，多类型布局
* 4.支持不带泛型的多类型模块用法
* 5.支持不同delegate不同span size

### demo演示

<img width="282" height="471" src="https://github.com/dengzq/BaseRecyclerViewAdapter/blob/master/image/img_header.gif" alt="HeaderAndFooter"/><img width="282" height="471" src="https://github.com/dengzq/BaseRecyclerViewAdapter/blob/master/image/img_loader.gif" alt="Loader"/><img width="282" height="471" src="https://github.com/dengzq/BaseRecyclerViewAdapter/blob/master/image/img_single.gif" alt="SingleItem"/>

<img width="282" height="471" src="https://github.com/dengzq/BaseRecyclerViewAdapter/blob/master/image/img_multi_item.gif" alt="MultiItem"/><img width="282" height="471" src="https://github.com/dengzq/BaseRecyclerViewAdapter/blob/master/image/img_multi_classify.gif" alt="MultiClassify"/><img width="282" height="471" src="https://github.com/dengzq/BaseRecyclerViewAdapter/blob/master/image/img_span_size.gif" alt="SpanSize"/>

### 使用

#### Gradle
```
compile 'com.dengzq.widget:baservadapter:0.7.9'
```
#### Maven
```
<dependency>
  <groupId>com.dengzq.widget</groupId>
  <artifactId>baservadapter</artifactId>
  <version>0.7.9</version>
  <type>pom</type>
</dependency>
```

#### 基本使用

##### 一. HeaderAndFooter

```
val header = layoutInflater.inflate(R.layout.layout_header, recycler_view, false)
adapter.addHeaderView(header)

如果需要在某些情况下移除header，请在添加时绑定一个key
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
               //do what you want to add data here;
            }
        }
```

当loadMore请求结束，请回调

```
adapter.loadMoreSuccess() //loadMore success
adapter.loadMoreFail()    //loadMore fail, show load error layout if it's exist;
```

###### 4. 关于autoLoad,加载失败

1)默认情况下，加载更多使用`自动加载`方式，如果需要`点击加载`的方式，请通过loader的点击事件实现;</br>
2)加载失败调用`adapter.loadMorefail()`会展示加载失败视图，可以通过loader点击事件实现重新请求;</br>
特别的，`adapter.goReloading()`会重新回调`onLoadMoreListener`. 如果你的请求逻辑没有写在`onLoadMoreListener`内,
请自行在 `state==LoadState.ERROR` 时实现重新请求逻辑.
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

###### 5.No More展示

```
1.添加BottomView
adapter.addBottomView(view)

2.无更多时，请调用
adapter.isHasMore(false)

3.刷新数据时，请重置isHasMore状态
adapter.isHasMore(true)
```


</p>
</p>

##### 三. 单类型，多类型列表

###### 1. 单类型使用
```
class SingleTypeAdapter(context: Context, list: ArrayList<T>) : CommonAdapter<T>(context, layout, list) {
    override fun convert(holder: BaseViewHolder, t: T, position: Int) {
        //do what you want when bindViewHolder
    }
}
```

###### 2. 多类型 带泛型 
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

###### 3. 多类型 不带泛型 
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
//重写getItemSpanSize(position: Int)方法;

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


### Todo list

1.预加载布局</br>
2.拖拽、侧滑、分级</br>
3.动画</br>
etc.

### 更丰富的刷新、加载效果 
请结合[SimpleRefreshLayout](https://github.com/dengzq/SimpleRefreshLayout)实现或查看[demo](https://www.pgyer.com/Eot4);

### 特别感谢

[hongyangAndroid](https://github.com/hongyangAndroid/baseAdapter)</br>
[我的小伙伴小太阳](https://github.com/juicy-zx)

#### 如果你喜欢本项目，请点个✨哦~
