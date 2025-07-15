package com.nimo.csyfb;

import com.nimo.facebeauty.FBEffect;

import java.util.HashMap;
import java.util.Map;

public class FBRenderConfig {
    private final Map<Integer, Integer> beautyParams = new HashMap<>();
    private final Map<Integer, Integer> reshapeParams = new HashMap<>();
    private final Map<Integer, String> arItems = new HashMap<>();
    private final Map<Integer, FilterItem> filterParams = new HashMap<>();
    public static class FilterItem {
        public String name;
        public int value;

        public FilterItem(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }
    public void applyAll(FBEffect effect) {
        for (Map.Entry<Integer, Integer> entry : beautyParams.entrySet()) {
            effect.setBeauty(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<Integer, Integer> entry : reshapeParams.entrySet()) {
            effect.setReshape(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<Integer, String> entry : arItems.entrySet()) {
            effect.setARItem(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Integer, FilterItem> entry : filterParams.entrySet()) {
            FilterItem item = entry.getValue();
            effect.setFilter(entry.getKey(), item.name, item.value);
        }
    }

    public void setBeauty(int type, int value) {
        beautyParams.put(type, value);
    }

    public void setReshape(int type, int value) {
        reshapeParams.put(type, value);
    }

    public void setARItem(int type, String name) {
        arItems.put(type, name);
    }
    public void setFilter(int type, String name,int value) {
        filterParams.put(type, new FilterItem(name, value));
    }

}

