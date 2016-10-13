package com.feicui.mvpcase;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public class HomeModel {

    /**业务层接口*/
    public interface Model {
        void setData(List<String> datas);
    }

    private Thread thread;
    private Model mModel;
    private Handler handler;

    public HomeModel(Model model){
        mModel = model;
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    List<String> datas = (List<String>) msg.obj;
                    mModel.setData(datas);
                }
            }
        };
    }

    public void asyncLoadData(){
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<String> datas = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    datas.add("我是第 " + i + " 条数据");
                }
                /**反馈Model层数据*/
                Message message = Message.obtain();
                message.what = 1;
                message.obj = datas;
                handler.sendMessage(message);
            }
        });
        thread.start();
    }

}
