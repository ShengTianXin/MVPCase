package com.feicui.mvpcase;

import java.util.List;

/**
 * Created by Administrator on 2016/10/14 0014.
 */
public class HomeEvent {

    public final List<String> datas;

    public HomeEvent() {
        datas = null;
    }
    public HomeEvent(List<String> datas) {
        this.datas = datas;
    }
}
