package com.nimo.csyfb;

public class FBInfo {

    private static final FBInfo instance = new FBInfo();
    private FBRenderConfig renderConfig = new FBRenderConfig();

    private FBInfo() {}

    public static FBInfo getInstance() {
        return instance;
    }

    public FBRenderConfig getRenderConfig() {
        return renderConfig;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

}
