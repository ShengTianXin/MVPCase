package com.feicui.mvpcase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Home页面相关视图实现
 * 视图一般是"消极的", 视图的变化基本都由于数据的变化，或用户的操作而变化的
 * 视图一般是不会自己来调用的
 */
public class MainActivity extends AppCompatActivity implements HomeView {

    // 展现数据的列表控件
    @BindView(R.id.listView)
    ListView listView;
    // 触发获取数据的按钮，默认显示，loading后将gone
    @BindView(R.id.btn_refresh)
    Button btnRefresh;
    // 数据获取中的loading控件，默认gone
    @BindView(R.id.prg_loading)
    ProgressBar prgLoading;

    private ArrayAdapter<String> arrayAdapter;

    private HomePresenter mHomePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**Presenter*/
        mHomePresenter = new HomePresenter();
        mHomePresenter.onCreate();
        mHomePresenter.attachView(this);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        // 用ArrayAdapter设置listView的适配器
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomePresenter.onDestroy();
        mHomePresenter.detachView();
    }

    @OnClick(R.id.btn_refresh)
    public void refreshData() {
        mHomePresenter.loadData();
    }

    // start view interface ----------------------------------
    // 视图上的工作如下
    @Override
    public void showLoading() {
        prgLoading.setVisibility(View.VISIBLE);
        btnRefresh.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        prgLoading.setVisibility(View.GONE);
        btnRefresh.setVisibility(View.VISIBLE);
    }

    @Override
    public void refreshListView(List<String> datas) {
        arrayAdapter.clear();
        arrayAdapter.addAll(datas);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    // end view interface ----------------------------------
}
