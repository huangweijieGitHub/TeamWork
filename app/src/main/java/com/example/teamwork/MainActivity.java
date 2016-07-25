package com.example.teamwork;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.teamwork.Utils.ToastUtils;

/**
 * 项目的主Activity,首页功能在这里实现。
 *
 * Created by 伟捷。 on 2016/5/23.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 用于展示定位城市文字的TextView文本
     */
    private TextView tv_location=null;

    /**
     * 用于展示底部导航中项目、我的、通知、聊天的TextView文本
     */
    private TextView tv_project,tv_me,tv_message,tv_chat;

    /**
     * 用于展示底部导航中项目、我的、通知、聊天的ImageView图片
     */
    private ImageView iv_project,iv_me,iv_message,iv_chat;

    /**
     * 用于底部导航点击跳转中项目、我的、通知、聊天的RelativeLayout选区
     */
    private RelativeLayout rl_project,rl_me,rl_message,rl_chat;

    /**
     * 用于底部导航点击变色定义status
     */
    private int status = 0;

    /**
     * 初始化LocationClient类
     */
    public LocationClient mLocationClient = null;

    /**
     * 定义MyLocationListener方法
     */
    public MyLocationListener myListener = new MyLocationListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化布局元素
        initViews();
    }

    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void initViews() {
        //导航文字
        tv_me=(TextView)findViewById(R.id.tv_me);
        tv_chat=(TextView)findViewById(R.id.tv_chat);
        tv_message=(TextView)findViewById(R.id.tv_message);
        tv_project=(TextView)findViewById(R.id.tv_project);
        //导航图片
        iv_me=(ImageView)findViewById(R.id.iv_me);
        iv_chat=(ImageView)findViewById(R.id.iv_chat);
        iv_message=(ImageView)findViewById(R.id.iv_message);
        iv_project=(ImageView)findViewById(R.id.iv_project);
        //导航选区
        rl_me=(RelativeLayout)findViewById(R.id.rl_me);
        rl_chat=(RelativeLayout)findViewById(R.id.rl_chat);
        rl_message=(RelativeLayout)findViewById(R.id.rl_message);
        rl_project=(RelativeLayout)findViewById(R.id.rl_project);
        rl_me.setOnClickListener(this);
        rl_chat.setOnClickListener(this);
        rl_message.setOnClickListener(this);
        rl_project.setOnClickListener(this);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener); //注册监听函数
        tv_location=(TextView)findViewById(R.id.tv_location);
        setLocationOption(); //定义setLocationOption()方法
        mLocationClient.start(); //执行定位
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_project:
                switch (status) {
                    //0代表点击时候，1代表没有点击时候
                    case 0:
                        iv_project.setImageResource(R.drawable.project_blue);
                        tv_project.setTextColor(Color.parseColor("#56abe4"));
                        status = 1;
                        break;
                    case 1:
                        status = 0;
                        break;
                }
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                //实现淡入淡出的效果
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
            case R.id.rl_me:
                switch (status){
                    //0代表点击时候，1代表没有点击时候
                    case 0:
                        iv_me.setImageResource(R.drawable.me_blue);
                        tv_me.setTextColor(Color.parseColor("#56abe4"));
                        status = 1;
                        break;
                    case 1:
                        status = 0;
                        break;
                }
                startActivity(new Intent(MainActivity.this,MeActivity.class));
                //实现淡入淡出的效果
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
            case R.id.rl_message:
                switch (status){
                    //0代表点击时候，1代表没有点击时候
                    case 0:
                        iv_message.setImageResource(R.drawable.message_blue);
                        tv_message.setTextColor(Color.parseColor("#56abe4"));
                        status=1;
                        break;
                    case 1:
                        status=0;
                        break;
                }
                startActivity(new Intent(MainActivity.this,MessageActivity.class));
                //实现淡入淡出的效果
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
                break;
            case R.id.rl_chat:
                switch (status){
                    //0代表点击时候，1代表没有点击时候
                    case 0:
                        iv_chat.setImageResource(R.drawable.chat_blue);
                        tv_chat.setTextColor(Color.parseColor("#56abe4"));
                        status=1;
                        break;
                    case 1:
                        status=0;
                        break;
                }
                startActivity(new Intent(MainActivity.this,ChatActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
                break;
        }
    }


    /**
     * 城市定位功能主要代码，在这里。
     */
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //将获取的City赋值给txt
            /**
             *1.国家:location.getCountry()
             * 2.城市:location.getCity()
             * 3.区域(例：天河区)：location.getDistrict()
             * 4.地点(例：风信路)：location.getStreet()
             * 5.详细地址：location.getAddrStr()
             */
            tv_location.setText(bdLocation.getDistrict());
           ToastUtils.toast(MainActivity.this,"网络定位成功"+
                    bdLocation.getDirection());
        }
        public void onReceivePoi(BDLocation arg0) {
        }
    }

    /**
     * 执行onDestroy()方法，停止定位
     */
    @Override
    public void onDestroy() {
        mLocationClient.stop();
        super.onDestroy();
    }

    /**
     * 设置相关参数
     */
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); //打开gps
        option.setAddrType("all");//返回定位结果包含地址信息
        option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先
        option.setPriority(LocationClientOption.GpsFirst);       //gps
        option.disableCache(true);//禁止启用缓存定位
        mLocationClient.setLocOption(option);
    }


}
