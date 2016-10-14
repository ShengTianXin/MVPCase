package com.feicui.mvpcase;

import android.support.annotation.UiThread;

import com.feicui.mvpcase.basemvp.MvpPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * </p>
 * <li/>1 做了业务处理  : doInBackground里面的模拟（3秒）获取加载数据
 * <li/>2 做了业务和视图的协调处理: 调用了视图上的一些“方法”（进行了视图的刷新）
 * </p>
 * 他太累了,他的工作太多了，在工作太多时，它就容易出错 -- 不方便修改,不明确工作职责,容易出BUG，出了BUG还不容易找
 * </p>
 * 我们希望它的工作再拆分一下,拆分成为,业务和协调,也就是Model和Presenter,View就是HomeView也就是Activity或Fragmnet,View,ViewGroup)
 * </p>
 * Created by Administrator on 2016/10/13 0013.
 */
public class HomePresenter extends MvpPresenter<HomeView> {

    // 视图接口对象,获取视图
    private HomeView mHomeView = super.getView();

    @UiThread
    public void loadData() {
        mHomeView.showLoading();
        new HomeModel().asyncLoadData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HomeEvent homeEvent){
        mHomeView.hideLoading();
        if (homeEvent.datas == null) {
            mHomeView.showMessage("未知错误,数据获取失败！");
            return;
        }
        mHomeView.refreshListView(homeEvent.datas);
    }

    /**
     * 一个HomeView接口(视图接口)空的实现
     * @return
     */
    protected final HomeView getNullObject() {
        HomeView homeView = new HomeView() {
            @Override
            public void showLoading() {}

            @Override
            public void hideLoading() {}

            @Override
            public void refreshListView(List<String> datas) {}

            @Override
            public void showMessage(String msg) {}
        };
        return homeView;
    }
}
