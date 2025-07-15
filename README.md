# Uniapp CSY-FB插件使用参考

1. 从github （https://github.com/FaceBeauty/FBLiveUni#）拉取

2. 将FB模块打包成arr包

3. 在uniapp插件项目中创建`nativeplugins`目录，将本地的插件导入

4. 在`nativeplugins`目录中的package.json

   ```
   {
     "name": "CSY-FB",
     "id": "CSY-FB",
     "version": "2.0.0",
     "description": "RTC对接FaceBeauty",
     "_dp_type": "nativeplugin",
     "_dp_nativeplugin": {
       "android": {
         "plugins": [
          {
               "type": "module",
               "name": "CSY-FB",
               "class": "com.nimo.csyfb.Fb"
           }
         ],
         "integrateType": "aar",
         "compileOptions": {
           "sourceCompatibility": "1.8",
           "targetCompatibility": "1.8"
         },
         "dependencies": [
           "com.facebook.fresco:fresco:1.13.0",
           "com.google.code.gson:gson:2.8.7"
         ],
         "useAndroidX": true
       }
     }
   }
   
   ```

5. 在manifest.json中的原生插件配置选择本地插件

6. 融云集成参考：https://doc.rongcloud.cn/call/uni-app/5.X/prepare

   ```
   前往 DCloud 插件市场，购买下列融云 uni-app 插件，或将插件下载到本地
   融云即时通讯 SDK uni 原生插件 RCUniIMV2:
   https://ext.dcloud.net.cn/plugin?id=9227
   融云实时音视频 SDK uni 原生插件RCUniCall:
   https://ext.dcloud.net.cn/plugin?id=6372
   融云即时通讯 SDK uni TS 插件RongCloud-IMWrapper-V2:
   https://ext.dcloud.net.cn/plugin?id=9225
   融云实时音视频SDK uni TS 插件 RongCloud-CallWrapper:
   https://ext.dcloud.net.cn/plugin?id=7136
   ```

   腾讯云集成参考：https://uniapp.helpyougo.com/web/userpage/documents?id=30

   ```
   前往 DCloud 插件市场，购买下列腾讯云 uni-app 插件，或将插件下载到本地
   腾讯云视频互动直播:https://ext.dcloud.net.cn/plugin?id=3371
   ```

7. 美颜原生插件

   ```
   <script>
   
       //美颜原生插件
      const CSY_FB = uni.requireNativePlugin('CSY-FB');
   
       export default {
       }
   
   </script>
   ```

8. 初始化美颜

   ```
    在onLoad中初始化
    /**
        * 初始化美颜
        * @param YOUR_APPID 密钥
        * @param type 类型   rongcloud ：融云；trtc：腾讯云
        * @param callback 回调
        */
    CSY_FB.initFB("YOUR_APPID","type",(res)=>{
    	console.log(res.data)
    })
   ```

9. 开启美颜

   ```
   processRender((res)=>{
   	console.log(res.data)
   })
   ```

   

10. 是否开启美颜极值

   ```
   /**
        * 美颜极值
        * @param limitEnable默认关闭  true 开启、false关闭
        */
   setExtremeLimitEnable(limitEnable)
   ```

11. 使用美颜方法

    ```
    /**
     * 美肤
     * @param type 美肤类型； number
     * @param value 美颜程度，范围 0-100；number
     * CSY_FB.setBeauty(4,88)
    */
    CSY_FB.setBeauty(type, value)
    
    ```

    ```
    /**
     * 滤镜
     *0 ---滤镜类型 ,目前只有风格滤镜
     * @param type 滤镜类型； string
     * @param value 美颜程度，范围 0-100；number
     * CSY_FB.setFilter(0,"yuese",88)
    */
    CSY_FB.setFilter(type, value)
    
    ```

    ```
    /**
     * 美型
     * @param type 美型类型； number
     * @param value 美型程度，范围 0-100；number
     * CSY_FB.setReshape(21, 88)
    */
    CSY_FB.setReshape(type,value)
    ```

    ```
    /**
     * 贴纸
     * @param type  贴纸类型； number
     * @param value  贴纸名字；string
     * CSY_FB.setARItem(1,"fb_mask_purple")
    */
    CSY_FB.setARItem(type,value)
    ```

## 美肤类型（setBeauty）

```
0-------------------- -------------------- 美白，0~100，0为无效果
1-------------------- -------------------- 精细磨皮，0~100，0为无效果
2-------------------- -------------------- 红润，0~100，0为无效果
3-------------------- -------------------- 锐化（原清晰），0~100，0为无效果
4-------------------- -------------------- 亮度，0~100，0为最暗
5-------------------- -------------------- 去黑眼圈，0~100，0为无效果
6-------------------- -------------------- 去法令纹，0~100，0为无效果
7-------------------- -------------------- 美牙，0～100，0为无效果
8-------------------- -------------------- 亮眼，0～100，0为无效果
9--------------------  -------------------- 白平衡，-50-50，0为无效果
10-------------------- -------------------- 清晰，0-100， 0为无效果
11 -------------------- 五官立体，0-100， 0为无效果
```

## 美型类型（setReshape）

```
10-------------------- -------------------- 大眼，0-100，0为无效果
11-------------------- -------------------- 圆眼，0-100，0为无效果
12-------------------- -------------------- 眼间距，-50-50， 0为无效果
13-------------------- -------------------- 眼睛角度，-50-50， 0为无效果
14-------------------- -------------------- 开眼角，0-100， 0为无效果
20-------------------- -------------------- 瘦脸，0-100，0为无效果
21-------------------- -------------------- V脸，0-100，0为无效果
22-------------------- -------------------- 窄脸，0-100，0为无效果
 23-------------------- -------------------- 瘦颧骨，0-100，0为无效果
24-------------------- -------------------- 瘦下颌骨，0-100，0为无效果
25-------------------- -------------------- 丰太阳穴，0-100，0为无效果
26-------------------- -------------------- 小头，0-100，0为无效果
27-------------------- -------------------- 小脸，0-100，0为无效果
28-------------------- -------------------- 短脸，0-100，0为无效果
30-------------------- -------------------- 长鼻
31-------------------- -------------------- 瘦鼻，0-100，0为无效果
32-------------------- -------------------- 鼻头，0-100，0为无效果
33-------------------- -------------------- 山根，0-100，0为无效果
40-------------------- -------------------- 嘴型，-50-50， 0为无效果
41-------------------- -------------------- 微笑嘴角，0-100，0为无效果
0--------------------  -------------------- 下巴，-50-50， 0为无效果
1--------------------  -------------------- 发际线，-50-50， 0为无效果
2   -------------------- 缩人中，-50-50， 0为无效果
```

## AR道具类型（setARItem）

### type

```
0-------------------- -------------------- 2D贴纸
1-------------------- -------------------- 面具
```

### 贴纸名

```
fb_sticker_effect_zhu -----猪鼻子
fb_sticker_effect_apple----苹果
//更多详见资源文件fbui->src->main->assets->fbeffect->sticker->fb_sticker_config.json--->"name"
```

### 面具名

```
fb_mask_purple
//更多详见资源文件fbui->src->main->assets->fbeffect->mask->fb_mask_config.json--->"name"
```

## 滤镜类型(setFilter)

### type

```
滤镜名字：ziran1、yuese
//更多详见资源文件fbui->src->main->assets->fbeffect->filter->fb_style_filter_config.json-->"name"
```

