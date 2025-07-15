package com.nimo.csyfb;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.nimo.facebeauty.FBEffect;
import com.nimo.facebeauty.model.FBRotationEnum;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;

import cn.rongcloud.rtc.api.RCRTCConfig;
import cn.rongcloud.rtc.api.RCRTCEngine;
import cn.rongcloud.rtc.api.callback.IRCRTCVideoOutputFrameListener;
import cn.rongcloud.rtc.base.RCRTCVideoFrame;
import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;

public class Fb extends UniModule {
    private static final String TAG = "FB-------";
    private boolean isInitBuffer= false;
    private  TRTCCloud getTRTCCloudInstance;

    /**
     * 初始化美颜
     * @param APPID 密钥
     * @param type 类型   rongcloud ：融云；trtc：腾讯云
     * @param callback 回调
     */
    @UniJSMethod
    public void initFB(String APPID,String type,UniJSCallback callback){
        FBEffect.shareInstance().initFaceBeauty(mUniSDKInstance.getContext(), APPID, new FBEffect.InitCallback() {
            @Override public void onInitSuccess() {
                callback.invoke(new JSONObject(){
                    {
                        put("data","初始化成功");
                    }

                });
                Log.i(TAG, "onInitSuccess: 初始化成功");
            }
            @Override public void onInitFailure() {
                callback.invoke(new JSONObject(){
                    {
                        put("data","初始化失败");
                    }

                });
                Log.e(TAG, "onInitSuccess: 初始化失败");
            }
        });
        FBInfo.getInstance().setType(type);

    }

    /**
     * 开始渲染
     */
    @UniJSMethod
    public void processRender(UniJSCallback callback){
        switch ( FBInfo.getInstance().getType()){
            case RTCType.RC:
                //融云
                RCRTCEngine.getInstance().init(mUniSDKInstance.getContext(), RCRTCConfig.Builder.create().enableEncoderTexture(true).build());
                try {
                    RCRender();
                    callback.invoke(new JSONObject(){
                        {
                            put("data",true);
                            put("message","融云渲染成功");
                        }
                    });
                } catch (Exception e) {
                    callback.invoke(new JSONObject(){
                        {
                            put("data",false);
                            put("message","融云渲染错误");
                        }
                    });
                }

                break;
            case RTCType.TRTC:
                //腾讯云
                try {
                    TXRender();
                    callback.invoke(new JSONObject(){
                        {
                            put("data",true);
                            put("message","腾讯云渲染成功");
                        }
                    });
                } catch (Exception e) {
                    callback.invoke(new JSONObject(){
                        {
                            put("data",false);
                            put("message","腾讯云渲染错误");
                        }
                    });
                }

                break;
            default:
                callback.invoke(new JSONObject(){
                    {
                        put("data",false);
                        put("message","RTC类型错误");
                    }
                });
                break;
        }
    }

    /**
     * 腾讯云
     */
   private void TXRender(){
          TRTCCloud.sharedInstance(mUniSDKInstance.getContext()).setLocalVideoProcessListener(TRTCCloudDef.TRTC_VIDEO_PIXEL_FORMAT_Texture_2D,
                TRTCCloudDef.TRTC_VIDEO_BUFFER_TYPE_TEXTURE, new TRTCCloudListener.TRTCVideoFrameListener() {
                    @Override
                    public void onGLContextCreated() {

                    }
                    @Override
                    public int onProcessVideoFrame(TRTCCloudDef.TRTCVideoFrame srcFrame, TRTCCloudDef.TRTCVideoFrame dstFrame) {
                        //todo--start--FB 添加渲染
                        if (!isInitBuffer){
                            isInitBuffer =FBEffect.shareInstance().initTextureRenderer(
                                    srcFrame.width,
                                    srcFrame.height,
                                    FBRotationEnum.FBRotationClockwise0,
                                    false,
                                    5);
                            Log.i(TAG, "processVideoFrame:isInitBuffer "+isInitBuffer);
                        }
                        FBInfo.getInstance().getRenderConfig().applyAll(FBEffect.shareInstance());
                        dstFrame.texture.textureId = FBEffect.shareInstance().processTexture(srcFrame.texture.textureId);
                        return dstFrame.texture.textureId;
                    }
                    @Override
                    public void onGLContextDestory() {

                    }
                });
   }

    /**
     * 融云
     */
    private void RCRender(){
        //融云
        RCRTCEngine.getInstance().getDefaultVideoStream().setVideoFrameListener(new IRCRTCVideoOutputFrameListener() {
            @Override
            public RCRTCVideoFrame processVideoFrame(RCRTCVideoFrame rtcVideoFrame) {
                //todo--start--FB 添加渲染
                if (!isInitBuffer){
                    isInitBuffer =FBEffect.shareInstance().initTextureRenderer(
                            rtcVideoFrame.getWidth(),
                            rtcVideoFrame.getHeight(),
                            FBRotationEnum.FBRotationClockwise270,
                            false,
                            5);
                    Log.i(TAG, "processVideoFrame:isInitBuffer "+isInitBuffer);
                }
                //添加渲染
                FBInfo.getInstance().getRenderConfig().applyAll(FBEffect.shareInstance());
                int textureId =  FBEffect.shareInstance().processTexture(rtcVideoFrame.getTextureId());
                rtcVideoFrame.setTextureId(textureId);
                return rtcVideoFrame;

            }
        });
    }
    /**
     * 美颜极值
     * @param limitEnable
     */
    @UniJSMethod
    public void setExtremeLimitEnable(boolean limitEnable) {
        FBEffect.shareInstance().setExtremeLimitEnable(limitEnable);
    }
    /**
     * 美肤
     * @param type 类型
     * @param value 美颜程度 0-100
     */
    @UniJSMethod
    public void setBeauty(int type, int value) {
        FBInfo.getInstance().getRenderConfig().setBeauty(type, value);
    }
    /**
     * 美型
     * @param type 美型
     * @param value 美颜程度 0-100
     */
    @UniJSMethod
    public void setReshape(int type, int value) {
        FBInfo.getInstance().getRenderConfig().setReshape(type, value);
    }
    /**
     * 面具、贴纸
     * @param type 面具、贴纸
     * @param name 面具名、贴纸名
     */
    @UniJSMethod
    public void setARItem(int type, String name) {
        FBInfo.getInstance().getRenderConfig().setARItem(type, name);
    }
    /**
     * 滤镜
     * @param type 滤镜类型
     * @param name 滤镜名
     * @param value 美颜程度 0-100
     */
    @UniJSMethod
    public void setFilter(int type, String name,int value) {
        FBInfo.getInstance().getRenderConfig().setFilter(type, name,value);
    }
}
