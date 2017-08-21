# EasyArchitechure
一个适用于快速MVP项目开发的框架
##项目包含的内容
* Retrolambda
* Butterknife
* Retrofit
* RxJava
* Okhttp
* Logger
* Dagger2
##编译JAVA版本
JAVA_1.8
##项目特点
* 对服务器返回的字段进行了统一处理
* 对于网络请求封装了相应的对话框网络监听器,用户不用关心其等待对话框的显示和隐藏
* 项目整体采用mvp构架,适合中型项目的开发
## 使用要点
1. 对于新开发的项目,直接改包名进行引用
2. 新建的activity直接继承BaseActivity即可
3. 对于需要网络请求的activity,需要重写setUpComponent(AppComponent appComponent)方法
5. 使用方式
    *  需要添加公共请求参数时候,可以在OkhhtpFactory中的requestInterceptor中添加公共请求参数
    *  对于服务器返回的数据需要处理的可以在responseInterceptor进行统一处理
    *  新增网络请求
        * 在RequestService添加请求方法
        * 在RequestUtil中新增方法,确定请求参数和返回值
        * 采用mvp方式组织代码时,新建对应activity的presenter继承自BasePresnter,在presenter中进行网络请求
        *  新建对应activity的MVP的Component和对应的Module,并在activity中注入
    