# DataBindingSample

DataBinding的使用集合

## 关于我

| 公众号   | 掘金     |  知乎    |  CSDN   |   简书   |   思否  |   哔哩哔哩  |   今日头条
|---------|---------|--------- |---------|---------|---------|---------|---------|
| [我的Android开源之旅](https://t.1yb.co/Irse)  |  [点我](https://juejin.im/user/598feef55188257d592e56ed/posts)    |   [点我](https://www.zhihu.com/people/xuexiangjys/posts)       |   [点我](https://xuexiangjys.blog.csdn.net/)  |   [点我](https://www.jianshu.com/u/6bf605575337)  |   [点我](https://segmentfault.com/u/xuexiangjys)  |   [点我](https://space.bilibili.com/483850585)  |   [点我](https://img.rruu.net/image/5ff34ff7b02dd)


## 准备工作

### 启用

1.DataBinding启用

```groovy
android {
    dataBinding {
        enabled = true
    }
}
```

2.ViewBinding启用

```groovy
android {
    buildFeatures {
        viewBinding true
    } 
}
```

### 快捷方式

在你的布局中找到最外层的布局，将光标放在如图位置。
* Windows请按快捷键 Alt + 回车
* Mac请按快捷键 option + 回车

![](https://s1.ax1x.com/2023/04/22/p9Z9cgP.png)

![](https://s1.ax1x.com/2023/04/22/p9Z963t.png)

## 基础使用

### 1.点击事件绑定

1.无参响应函数：

```kotlin
fun onIncrement() {
    // 方法体
}
```

```
android:onClick="@{() -> state.onIncrement()}"
```

2.接口变量响应函数

注意，这里变量的类型应该是`View.OnClickListener`接口。

```kotlin
val onClickDecrement = View.OnClickListener {
    // 方法体
}
```

```
android:onClick="@{state.onClickDecrement}"
```

3.有参响应函数

```kotlin
fun onReset(view: View) {
    // 方法体
}
```

```
// 第一种写法
android:onClick="@{(view) -> state.onReset(view)}" 

// 第二种写法
android:onClick="@{state::onReset}"
```

### 2.@BindingAdapter自定义属性

> 所有注解的功能都是基于XML属性值为DataBinding表达式才生效(即@{})

使用@BindingAdapter进行控件自定义属性绑定的时候，一定要使用 "@{}" 进行赋值，这一点非常重要！！！

1. 顶级函数实现

```kotlin
// Kotlin拓展函数式写法, 推荐使用
@BindingAdapter("customTitle")
fun TextView.setCustomTitle(title: String) {
    text = "标题1: $title"
}

// 第一个参数必须是view的子类
@BindingAdapter("customTitle1")
fun setCustomTitle1(view: TextView, title: String) {
    view.text = "标题2: $title"
}

// 多个参数进行绑定，requireAll=true，代表两个参数都设置了才生效，默认是true.
// 如果requireAll为false, 你没有填写的属性值将为null. 所以需要做非空判断.
@BindingAdapter(value = ["customTitle", "customSize"], requireAll = true)
fun TextView.setTextContent(title: String, size: Int) {
    text = "标题3: $title"
    textSize = size.toFloat()
}
```


**【特别注意事项⚠️】**

很多时候，很多新手在写DataBinding的时候，经常会漏掉`"@{}"`，尤其是用数字和Boolean类型的值时。就比如我上面设置的`customSize`属性，类型值是Int型，正确的写法应该是下面这样：

* 正确的写法

```xml
<TextView
    style="@style/TextStyle.Title"
    android:layout_marginTop="16dp"
    app:customSize="@{25}"
    app:customTitle="@{state.title}" />
```

* 常见错误的写法

```xml
<TextView
    style="@style/TextStyle.Title"
    android:layout_marginTop="16dp"
    app:customSize="25"
    app:customTitle="@{state.title}" />
```

上述错误的写法，运行后编译器会报错`AAPT: error: attribute customSize (aka com.xuexiang.databindingsample:customSize) not found.`。

所以当我们写DataBinding的时候，如果出现`AAPT: error: attribute xxx (aka com.aa.bb:xxx) not found.`，十有八九是你赋值漏掉了`"@{}"`。

2. 单例类+@JvmStatic注解

```kotlin
object TitleAdapter {
    @JvmStatic
    @BindingAdapter("customTitle2")
    fun setCustomTitle2(view: TextView, title: String) {
        view.text = "标题4: $title"
    }
}
```

### 3.@BindingConversion自定义类型转换

作用：在使用DataBinding的时候，对属性值进行转换，以匹配对应的属性。
定义：方法必须为公共静态（public static）方法，且有且只能有1个参数。

下面我给一个简单的例子：

1.对于User类，`age`的类型是Int。

```kotlin
data class User(
    val name: String,
    val gender: String? = "男",
    val age: Int = 10,
    val phone: String? = "13124765438",
    val address: String? = null
)
```

2.使用`@BindingAdapter`定义了`age`的类型却是String。

```kotlin
@BindingAdapter(value = ["name", "age"], requireAll = true)
fun TextView.setUserInfo(name: String, age: String) {
    text = "${name}今年${age}岁"
}
```

3.这时候使用DataBinding的时候，👇的`app:age="@{state.user.age}"`会编译报错，提示类型不匹配。

```xml
<TextView
    style="@style/TextStyle.Title"
    android:layout_marginTop="16dp"
    app:name="@{state.user.name}"
    app:age="@{state.user.age}"/>
```

4.这个时候，我们就可以使用`@BindingConversion`自定义类型转换: Int -> String, 这样👆的代码就不会编译出错了。

```kotlin
@BindingConversion
fun int2string(integer: Int) = integer.toString()
```

### 4.@{}中表达式使用

1. 常用运算符

* 算术 + - / * %
* 字符串合并 +
* 逻辑 && ||
* 二元 & | ^
* 一元 + - ! ~
* 移位 >> >>> <<
* 比较 == > < >= <=
* 三元 ?:
* Array 访问 \[\]

```xml
<TextView
    android:text="@{@string/app_name +  @string/app_name}"/>
```

```xml
<TextView 
    android:visibility="@{!state.user.phone.empty ? View.VISIBLE : View.GONE}"/>
```

2. 常用转义字符

* 空格:  \&nbsp;
* <小于号:  \&lt;
* \>大于号:  \&gt;
* &与号:	 \&amp;

```xml
<TextView 
    android:visibility="@{!state.user.phone.empty &amp;&amp; state.user.age > 5 ? View.VISIBLE : View.GONE}"/>
```

3. 资源使用

@string @color @drawable @dimen @array

```xml
<TextView
    style="@style/TextStyle.Content"
    android:text="@{@string/user_format(state.user.name, state.user.gender)}"
    android:textColor="@{@color/toast_error_color}"
    android:textSize="@{@dimen/xui_config_size_content_text_phone}" />
```

4. 集合

集合不属于`java.lang*`下, 需要导入全路径。集合使用\[\]进行访问。

```xml
<data>
    <import type="java.util.List"/>
    <import type="android.util.SparseArray"/>
    <import type="java.util.Map"/>
    <variable name="list" type="List&lt;String>"/>
    <variable name="sparse" type="SparseArray&lt;String>"/>
    <variable name="map" type="Map&lt;String, String>"/>
</data>
```
```xml
<TextView
    android:text="@{`key: key1, value:` + map[`key1`]}" />
```

5. 引用类的静态方法

kotlin中定义静态方法，一定要在方法上加上`@JvmStatic`，否则将无法成功引用。

(1) 定义方法
```kotlin
object AppUtils {

    @JvmStatic
    fun getAppInfo(context: Context?) =
        context?.let {
            "packageName: ${it.packageName}, \nversionName: ${
                it.packageManager.getPackageInfo(
                    it.packageName,
                    0
                ).versionName
            }"
        }
}
```
(2) 导入方法所在类路径

```xml
<import type="com.xuexiang.databindingsample.utils.AppUtils"/>
```

(3) 引用方法

```xml
<TextView
    android:text="@{AppUtils.getAppInfo(context)}"/>
```

6. 空值合并运算符

空值合并运算符 ?? 会取第一个不为 null 的值作为返回值。

```xml
<TextView
    android:text="@{`地址：` + (state.user.address ?? `默认地址`)}"/>
```
等价于

```xml
<TextView
    android:text="@{state.user.address != null ?  state.user.address : `默认地址`)}"/>
```

### 5.include 和 ViewStub

在主布局文件中将相应的变量传递给 include 布局，需使用自定义的 bind 命名空间将变量传递给 （include/ViewStub）， 从而使两个布局文件之间共享同一个变量。

例如，在include中定义的变量id是：<variable name="user" type="...User"/>, 那么就使用 app:user="@{state.user}" 来绑定数据，与variable定义的name保持一致。

1. include

```xml
<include
    layout="@layout/include_user_info"
    app:user="@{state.user}" />
```

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable
            name="user"
            type="com.xuexiang.databindingsample.fragment.basic.model.User" />

    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginVertical="16dp"
        android:orientation="vertical">

        <TextView
            style="@style/TextStyle.Content"
            android:userInfo="@{user}" />

    </LinearLayout>
</layout>
```

**【⚠️特别注意事项⚠️️】**

这里需要注意的是，include标签，如果设置了`layout_width`和`layout_height`这两个属性，那么布局就是由include外层设置的layout属性生效，内层属性不生效。

如果include标签没有设置`layout_width`和`layout_height`这两个属性，那么就是由include引用的布局内层设置的layout属性生效。

举个例子，如果把👆设置的include改成下面这样：

```xml
<include
    layout="@layout/include_user_info"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="24dp"
    app:user="@{state.user}" />
```

那么`@layout/include_user_info`加载的布局，距离上部的距离就是24dp，而不是16dp。

2. ViewStub

```xml
<ViewStub
    android:id="@+id/user_info"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="16dp"
    android:layout="@layout/viewstub_user_info"
    app:info="@{state.user}" />
```

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable
            name="info"
            type="com.xuexiang.databindingsample.fragment.basic.model.User" />

    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginVertical="16dp"
        android:orientation="vertical">

        <TextView
            style="@style/TextStyle.Content"
            android:userInfo="@{info}" />

    </LinearLayout>
</layout>
```

## 进阶使用

### 简化RecycleView的使用

1.定义一个供绑定的ViewHolder

```kotlin
class BindingViewHolder<T>(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root), LifecycleOwner {

    private val mLifecycle = LifecycleRegistry(this)

    fun bindingData(data: T?, variableId: Int = BR.item) {
        binding.setVariable(variableId, data)
    }

    init {
        mLifecycle.currentState = Lifecycle.State.INITIALIZED
    }

    fun onAttached() {
        mLifecycle.currentState = Lifecycle.State.STARTED
    }

    fun onDetached() {
        mLifecycle.currentState = Lifecycle.State.RESUMED
    }

    override fun getLifecycle(): Lifecycle = mLifecycle

}
```

2.定义一个供绑定的RecyclerView.Adapter

```kotlin

class BindingRecyclerViewAdapter<T>(
    @LayoutRes val layoutId: Int,
    var dataSource: MutableList<T>
) : RecyclerView.Adapter<BindingViewHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            layoutId,
            parent,
            false
        )
        val holder = BindingViewHolder<T>(binding)
        binding.lifecycleOwner = holder
        return holder
    }


    override fun onBindViewHolder(holder: BindingViewHolder<T>, position: Int) {
        holder.setDataBindingVariables(dataSource.getOrNull(position))
        holder.itemView.tag = position
        if (holder.binding.hasPendingBindings()) holder.binding.executePendingBindings()
    }

    override fun getItemCount() = dataSource.size

    @SuppressLint("NotifyDataSetChanged")
    fun refresh(data: List<T>) {
        if (data.isNotEmpty()) {
            dataSource = data.toMutableList()
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadMore(data: List<T>) {
        if (data.isNotEmpty()) {
            dataSource.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onViewAttachedToWindow(holder: BindingViewHolder<T>) {
        holder.onAttached()
    }

    override fun onViewDetachedFromWindow(holder: BindingViewHolder<T>) {
        holder.onDetached()
    }

    override fun onViewRecycled(holder: BindingViewHolder<T>) {
        holder.binding.lifecycleOwner = null
        super.onViewRecycled(holder)
    }
}
```

3.使用@BindingAdapter自定义绑定方法

```kotlin
@BindingAdapter(
    value = ["data", "itemLayout", "loadState"],
    requireAll = false
)
fun <T> RecyclerView.setBindingRecyclerViewAdapter(
    data: List<T>?,
    @LayoutRes layoutId: Int?,
    loadState: LoadState? = LoadState.DEFAULT,
) {
    requireNotNull(data) { "app:data argument cannot be null!" }
    requireNotNull(layoutId) { "app:itemLayout argument cannot be null!" }

    if (adapter == null) {
        adapter = BindingRecyclerViewAdapter(layoutId, data.toMutableList())
        layoutManager = XLinearLayoutManager(context)
    } else {
        @Suppress("UNCHECKED_CAST")
        (adapter as? BindingRecyclerViewAdapter<T>)?.run {
            when (loadState) {
                LoadState.REFRESH -> refresh(data, selectedPosition)
                LoadState.LOAD_MORE -> loadMore(data)
                else -> {}
            }
        }
    }
}
```

4.在xml中进行数据绑定

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>

        <variable
            name="state"
            type="com.xuexiang.databindingsample.fragment.advanced.model.RecyclerViewBasicState" />
    </data>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@android:color/white"
        android:overScrollMode="never"
        app:data="@{state.sampleData}"
        app:itemLayout="@{@layout/databinding_item_simple_list_2}" />

</layout>
```

5.在ViewModel中设置数据

```kotlin

class RecyclerViewBasicState(application: Application) : DataBindingPageState(application) {

    override fun initTitle() = "RecycleView的基础使用演示"

    val sampleData = MutableLiveData(getDemoData(application))

    fun getDemoData(context: Context, from: Int = 1, to: Int = 40): List<SimpleItem> {
        // 模拟数据加载
        val list = mutableListOf<SimpleItem>()
        for (index in from..to) {
            list.add(
                SimpleItem(
                    context.getString(R.string.item_example_number_title, index),
                    context.getString(R.string.item_example_number_subtitle, index)
                )
            )
        }
        return list
    }
}
```

这样，有了这样一套绑定体系后，后面我们再需要使用到RecyclerView的时候，就只需要4和5步就行了，1-3步都是可重复使用的。

### RecycleView的进阶使用

我们除了可以简单地使用DataBinding去加载RecyclerView的数据，我们还可以拓展其他一些操作来增强对RecyclerView的使用。

1.分割线的颜色和高度

```kotlin
@BindingAdapter(
    value = ["data", "itemLayout", "loadState", "dividerHeight", "dividerColor"],
    requireAll = false
)
fun <T> RecyclerView.setBindingRecyclerViewAdapter(
    data: List<T>?,
    @LayoutRes layoutId: Int?,
    loadState: LoadState? = LoadState.DEFAULT,
    dividerHeight: Float? = null,
    @ColorInt dividerColor: Int? = null,
) {
    requireNotNull(data) { "app:data argument cannot be null!" }
    requireNotNull(layoutId) { "app:itemLayout argument cannot be null!" }

    if (adapter == null) {
        adapter = BindingRecyclerViewAdapter(layoutId, data.toMutableList())
        layoutManager = XLinearLayoutManager(context)
        setDividerStyle(dividerHeight, dividerColor)
    } else {
        @Suppress("UNCHECKED_CAST")
        (adapter as? BindingRecyclerViewAdapter<T>)?.run {
            when (loadState) {
                LoadState.REFRESH -> refresh(data, selectedPosition)
                LoadState.LOAD_MORE -> loadMore(data)
                else -> {}
            }
        }
    }
}

fun RecyclerView.setDividerStyle(
    dividerHeight: Float? = null,
    @ColorInt dividerColor: Int? = null
) {
    val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
    dividerHeight?.let {
        divider.dividerHeight = it.roundToInt()
    }
    dividerColor?.let {
        divider.dividerColor = it
    }
    addItemDecoration(divider)
}

```

2.事件监听

(1) 定义监听接口

```kotlin
/**
 * 列表条目点击监听
 */
interface OnItemClickListener<T> {
    /**
     * 条目点击
     *
     * @param itemView 条目
     * @param item     数据
     * @param position 索引
     */
    fun onItemClick(itemView: View?, item: T?, position: Int)
}

/**
 * 列表条目长按监听
 */
interface OnItemLongClickListener<T> {
    /**
     * 条目长按
     *
     * @param itemView 条目
     * @param item     数据
     * @param position 索引
     */
    fun onItemLongClick(itemView: View?, item: T?, position: Int) : Boolean = true
}
```

(2) Adapter设置监听

```kotlin
class BindingRecyclerViewAdapter<T>(
    @LayoutRes val layoutId: Int,
    var dataSource: MutableList<T>,
    var onItemClickListener: OnItemClickListener<T>?,
    var onItemLongClickListener: OnItemLongClickListener<T>?,
) : RecyclerView.Adapter<BindingViewHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val holder = createViewHolder(layoutInflater, parent, viewType)
        initViewHolder(holder)
        return holder
    }

    private fun initViewHolder(holder: BindingViewHolder<T>) {
        onItemClickListener?.run {
            holder.itemView.setOnClickListener {
                val position = holder.itemView.tag as Int
                onItemClick(it, dataSource.getOrNull(position), position)
            }
        }
        onItemLongClickListener?.run {
            holder.itemView.setOnLongClickListener {
                val position = holder.itemView.tag as Int
                onItemLongClick(it, dataSource.getOrNull(position), position)
            }
        }
    }

    override fun onBindViewHolder(holder: BindingViewHolder<T>, position: Int) {
        holder.bindingData(dataSource.getOrNull(position))
        holder.itemView.tag = position
        if (holder.binding.hasPendingBindings()) holder.binding.executePendingBindings()
    }
}
```

(3) 使用@BindingAdapter自定义绑定方法

```kotlin
@BindingAdapter(
    value = ["data", "itemLayout", "loadState", "dividerHeight", "dividerColor", "itemClick", "itemLongClick"],
    requireAll = false
)
fun <T> RecyclerView.setBindingRecyclerViewAdapter(
    data: List<T>?,
    @LayoutRes layoutId: Int?,
    loadState: LoadState? = LoadState.DEFAULT,
    dividerHeight: Float? = null,
    @ColorInt dividerColor: Int? = null,
    onItemClickListener: OnItemClickListener<T>? = null,
    onItemLongClickListener: OnItemLongClickListener<T>? = null,
) {
    requireNotNull(data) { "app:data argument cannot be null!" }
    require(layoutId != null || itemViewParser != null) { "app:itemLayout and app:itemViewParser argument need a parameter that is not null!" }

    if (adapter == null) {
        adapter = BindingRecyclerViewAdapter(
            layoutId,
            data.toMutableList(),
            onItemClickListener,
            onItemLongClickListener
        )
        layoutManager = XLinearLayoutManager(context)
        setDividerStyle(dividerHeight, dividerColor)
    } else {
        @Suppress("UNCHECKED_CAST")
        (adapter as? BindingRecyclerViewAdapter<T>)?.run {
            when (loadState) {
                LoadState.REFRESH -> refresh(data, selectedPosition)
                LoadState.LOAD_MORE -> loadMore(data)
                else -> {}
            }
        }
    }
}
```

3.多布局类型加载

(1) 定义布局解析器接口

```kotlin

interface ItemViewParser {

    fun getItemViewType(position: Int): Int

    fun getItemLayoutId(viewType: Int): Int
}
```

(2) 增加布局解析器默认实现

```kotlin
class DefaultItemViewParser(@LayoutRes val layoutId: Int): ItemViewParser {

    override fun getItemViewType(position: Int) = 0

    override fun getItemLayoutId(viewType: Int) = layoutId

}
```

(3) 重写Adapter的`onCreateViewHolder`方法和`getItemViewType`方法

```kotlin
class BindingRecyclerViewAdapter<T>(
    private val itemViewParser: ItemViewParser,
    var dataSource: MutableList<T>,
    var onItemClickListener: OnItemClickListener<T>?,
    var onItemLongClickListener: OnItemLongClickListener<T>?,
) : RecyclerView.Adapter<BindingViewHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val holder = createViewHolder(layoutInflater, parent, viewType)
        initViewHolder(holder)
        return holder
    }

    private fun createViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<T> {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            itemViewParser.getItemLayoutId(viewType),
            parent,
            false
        )
        val holder = BindingViewHolder<T>(binding)
        binding.lifecycleOwner = holder
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return itemViewParser.getItemViewType(position)
    }
}
```

(4) 使用@BindingAdapter自定义绑定方法

```kotlin
@BindingAdapter(
    value = ["data", "itemLayout", "itemViewParser", "loadState", "dividerHeight", "dividerColor", "itemClick", "itemLongClick"],
    requireAll = false
)
fun <T> RecyclerView.setBindingRecyclerViewAdapter(
    data: List<T>?,
    @LayoutRes layoutId: Int?,
    itemViewParser: ItemViewParser?,
    loadState: LoadState? = LoadState.DEFAULT,
    dividerHeight: Float? = null,
    @ColorInt dividerColor: Int? = null,
    onItemClickListener: OnItemClickListener<T>? = null,
    onItemLongClickListener: OnItemLongClickListener<T>? = null,
) {
    requireNotNull(data) { "app:data argument cannot be null!" }
    require(layoutId != null || itemViewParser != null) { "app:itemLayout and app:itemViewParser argument need a parameter that is not null!" }

    if (adapter == null) {
        adapter = BindingRecyclerViewAdapter(
            itemViewParser ?: DefaultItemViewParser(layoutId!!),
            data.toMutableList(),
            onItemClickListener,
            onItemLongClickListener
        )
        layoutManager = XLinearLayoutManager(context)
        setDividerStyle(dividerHeight, dividerColor)
    } else {
        @Suppress("UNCHECKED_CAST")
        (adapter as? BindingRecyclerViewAdapter<T>)?.run {
            when (loadState) {
                LoadState.REFRESH -> refresh(data, selectedPosition)
                LoadState.LOAD_MORE -> loadMore(data)
                else -> {}
            }
        }
    }
}
```

4.刷新和加载更多

这里为了简单，我使用了开源的SmartRefreshLayout组件实现上拉刷新，下拉加载。

（1）使用@BindingAdapter自定义绑定方法

```kotlin

@BindingAdapter(
    value = ["refreshListener", "loadMoreListener"],
    requireAll = false
)
fun SmartRefreshLayout.setRefreshLayoutListener(
    refreshListener: OnRefreshListener?,
    loadMoreListener: OnLoadMoreListener?
) {
    setOnRefreshListener(refreshListener)
    setOnLoadMoreListener(loadMoreListener)
}
```

（2）在xml中进行数据绑定

```xml

<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <variable
            name="state"
            type="com.xuexiang.databindingsample.fragment.advanced.model.RecyclerViewRefreshState" />
    </data>

    <com.scwang.smart.refresh.layout.SmartRefreshLayout
        android:id="@+id/refreshLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:loadMoreListener="@{state.loadMoreListener}"
        app:refreshListener="@{state.refreshListener}">

        <com.scwang.smart.refresh.header.ClassicsHeader
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@android:color/white"
            android:overScrollMode="never"
            app:data="@{state.sampleData}"
            app:itemLayout="@{@layout/databinding_item_simple_list_2}"
            app:loadState="@{state.loadState}"
            tools:listitem="@layout/databinding_item_simple_list_2" />

        <com.scwang.smart.refresh.footer.ClassicsFooter
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

    </com.scwang.smart.refresh.layout.SmartRefreshLayout>

</layout>
```

（3）在ViewModel中设置数据

```kotlin

class RecyclerViewRefreshState(application: Application) : DataBindingPageState(application) {

    val sampleData = MutableLiveData<List<SimpleItem>>(arrayListOf())

    val loadState = MutableLiveData(LoadState.DEFAULT)

    var pageIndex = 0

    val refreshListener = OnRefreshListener { refreshLayout ->
        // 延迟1000ms模拟网络请求延时
        refreshLayout.layout.postDelayed({
            pageIndex = 0
            loadState.value = LoadState.REFRESH
            sampleData.value = sampleGetData(application)
            refreshLayout.finishRefresh()
            refreshLayout.resetNoMoreData()
        }, 1000)
    }

    val loadMoreListener = OnLoadMoreListener { refreshLayout ->
        refreshLayout.layout.postDelayed({
            pageIndex += 1
            loadState.value = LoadState.LOAD_MORE
            sampleData.value = sampleGetData(application)
            if (pageIndex >= 3) { // 模拟只能加载更多3页，即总共4页的数据
                refreshLayout.finishLoadMoreWithNoMoreData()
            } else {
                refreshLayout.finishLoadMore()
            }
        }, 1000)
    }

    /**
     * 模拟获取数据
     */
    private fun sampleGetData(context: Context) =
        getDemoData(context, pageIndex * PAGE_SIZE + 1, PAGE_SIZE * (pageIndex + 1))
}
```

## 特别感谢

* [Android 安卓DataBinding详解(超详细)](https://blog.csdn.net/qq_40881680/article/details/102240892)
* [DataBinding最全使用说明](https://juejin.cn/post/6844903549223059463)
* [Android DataBinding 从入门到进阶](https://juejin.cn/post/6844903609079971854)

## 如果觉得项目还不错，可以考虑打赏一波

> 你的打赏是我维护的动力，我将会列出所有打赏人员的清单在下方作为凭证，打赏前请留下打赏项目的备注！

![pay.png](https://raw.githubusercontent.com/xuexiangjys/Resource/master/img/pay/pay.png)

## 联系方式

> 更多资讯内容，欢迎扫描关注我的个人微信公众号:【我的Android开源之旅】

![](https://s1.ax1x.com/2022/04/27/LbGMJH.jpg)