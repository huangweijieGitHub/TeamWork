package com.example.teamwork.View.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.teamwork.R;
import com.example.teamwork.Utils.ToastUtils;

/**
 * Created by 伟捷。 on 2016/5/23.
 */
public class BufferActivity extends Activity implements View.OnClickListener {
    /**
     * 用于登录按钮和注册按钮的TextView文本
     */
    private TextView tv_login,tv_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer);
        // 初始化布局元素
        initViews();
    }

    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void initViews() {
        tv_login=(TextView)findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);
        tv_register=(TextView)findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);
    }

    /**
     * 控件触发事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_login:
                startActivity(new Intent(BufferActivity.this,LoginAcitivity.class));
                //实现淡入淡出的效果
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.tv_register:
                startActivity(new Intent(this,RegisterActivity.class));
                //实现淡入淡出的效果
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }

    /**
     * 连续按两次返回键就退出
     */
    private static long  fristTime;
    public void onBackPressed(){
        if (fristTime+2000>System.currentTimeMillis()){
            System.exit(0);
            super.onBackPressed();
        }
        else{
            ToastUtils.toast(BufferActivity.this,"再按一次退出程序");
        }
        fristTime=System.currentTimeMillis();
    }
}
