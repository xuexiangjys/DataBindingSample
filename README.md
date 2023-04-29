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




## 特别感谢

* [Android 安卓DataBinding详解(超详细)](https://blog.csdn.net/qq_40881680/article/details/102240892)

## 如果觉得项目还不错，可以考虑打赏一波

> 你的打赏是我维护的动力，我将会列出所有打赏人员的清单在下方作为凭证，打赏前请留下打赏项目的备注！

![pay.png](https://raw.githubusercontent.com/xuexiangjys/Resource/master/img/pay/pay.png)

## 联系方式

> 更多资讯内容，欢迎扫描关注我的个人微信公众号:【我的Android开源之旅】

![](https://s1.ax1x.com/2022/04/27/LbGMJH.jpg)