
#### 2.3主app的gradle配置如下
```
defaultConfig{
 multiDexEnabled true//开启分包
 packagingOptions {
            exclude 'META-INF/rxjava.properties' //解决不同版本的rxjava
        }
 ndk {
            abiFilters 'armeabi-v7a', 'armeabi', 'x86' //x86虚拟机测试用，发版可去掉
        }
}
```
#### 2.4 Application配置
  >1.Application继承BaseApplication
      2.实现getRootPackAge（）方法（*模块类的根目录）
     3.onCreate中初始化
```
//APP配置
List<Integer> httpCodeOff = new ArrayList<>();
        httpCodeOff.add(201);
        AppConfig appConfig = new AppConfig.Bulider()
                .setMaxPage(10)
                .setHttpCodeOff(httpCodeOff)
                .bulider();
        AppConfigManager.newInstance().setAppConfig(appConfig);
      //网络请求配置

        HttpConfig.HttpConfigBuilder httpConfig =
                new HttpConfig.HttpConfigBuilder()
                .setmBaseUrl("http://test-px.huatu.com")
                .setmIsUseLog(true);
        HttpConfig.newInstanceBuild(httpConfig);
        hApp.newInstance().initVideoPlay(this);
//初始化视频播放器
        hApp.newInstance().initVideoPlay(this);
  ```

 

### AppConfig属性列表

| Property        | Format           | Des  |
| ------------- |:-------------:| -----:|
| mBaseUrl    | String | 网络请求的域名 (http://www.baidu.com)|
| mConnectTimeout      |int      |  连接请求超时时间  |
| mIsUseLog | boolean     |   是否开启请求日志 |
| mIsUseCache      | boolean | 是否开启缓存 |
| mCacheFolder      | File      |   缓存文件地址  |
| mCacheSize |int      |    缓存大小 |
| mCacheTimeWithNet      | int | 缓存连接时间 |
| mCacheTimeWithoutNet      | int      |   读取缓存超时时间 |
| mMapHeader | Map<String, String>      |    报头 |
|isUseCustGson      | boolean      |   是否开启指定gson解析器  |
| isReshConfig | boolean     |    是否重新刷新配置信息 |
在此给出AppConfig配置详情 戳这里☞[传送门](https://github.com/w513209188/BaseLib/blob/master/common_base/src/main/java/com/wb/baselib/appconfig/AppConfig.java)
### HttpConfig属性列表

| Property        | Format           | Des  |
| ------------- |:-------------:| -----:|
| glideSize    | int | 图片缓存大小|
| glidePath      |String      |  图片缓存大小 |
| maxPage | int     |   列表最大显示页数 |
| httpCodeSuccess      | int | 网络请求成功code码 |
| httpCodeOff      | List<Integer>      |   下线code码  |
| appCarshPath |String      |    app奔溃日志存放路径 |
| isSendCarshLog      | boolean | 是否将奔溃日志发送至钉钉群组 |
| isCatshLog      | boolean      |   是否开启奔溃日志收集 |
| rootPackAge | String    |    根目录 |
|ddAccount      | String      |   钉钉账号  |
| ddToken | String     |    钉钉token |
| flgs | String     |    扩展字段 |
 在此给出HttpConfig配置详情 戳这里☞[传送门](https://github.com/w513209188/BaseLib/blob/master/common_base/src/main/java/com/wb/baselib/http/HttpConfig.java)
>因为属于正常调起故此正常调起方法如下:
```
  hApp.newInstance().toMainActivity(MainActivity.this, "1", "dfsfsfds", new LoginStatusCall() {
                    @Override
                    public void LoginError(String msg, int code) {
                        Log.e("---->>",msg+code);
                    }
                });

```
其他协议形式后续补齐


