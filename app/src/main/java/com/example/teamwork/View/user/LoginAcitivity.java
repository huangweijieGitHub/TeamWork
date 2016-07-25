package com.example.teamwork.View.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teamwork.MainActivity;
import com.example.teamwork.R;
import com.example.teamwork.Utils.ToastUtils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 伟捷。 on 2016/5/23.
 */
public class LoginAcitivity extends Activity implements View.OnClickListener {
    /**
     * 用于返回上一层按钮的ImageView图片
     */
    private ImageView img_back;
    /**
     * 用于登录按钮点击的Button
     */
    private Button btn_login;
    /**
     * 用于输入帐号和密码的EditText文本框
     */
    private EditText et_number,et_pwd;
    /**
     * 用于登录字体、切换忘记密码页面和微信登录的TextView文本框
     */
    private TextView tv_login,tv_forgetPwd,tv_weChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化 Bmob SDK，第一个参数为上下文，第二个参数为Application
        Bmob.initialize(this,"2c7a5a0b519dcca2c69dae802bfc6e2e");
        // 初始化布局元素
        initViews();
    }

    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void initViews() {
        img_back=(ImageView)findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        tv_forgetPwd=(TextView)findViewById(R.id.tv_forgetPwd);
        tv_forgetPwd.setOnClickListener(this);
        tv_weChat=(TextView)findViewById(R.id.tv_weChat);
        tv_weChat.setOnClickListener(this);
        tv_login=(TextView)findViewById(R.id.tv_login);
        et_number=(EditText)findViewById(R.id.et_number);
        et_pwd=(EditText)findViewById(R.id.et_pwd);
    }

    /**
     * 控件触发事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                startActivity(new Intent(this,BufferActivity.class));
                break;
            /**
             * 登录功能主要代码，在这里。
             */
            case R.id.btn_login:
                //1.获取文本框内容
                  String username=et_number.getText().toString().trim();
                  String password=et_pwd.getText().toString().trim();
                //2.判断文本框是否为空
                if (username.isEmpty()||password.isEmpty()){
                    ToastUtils.toast(LoginAcitivity.this,"账号密码不能为空");
                    return;
                }
                //3.寻找服务器，与储存信息匹配
                BmobUser bu = new BmobUser();
                bu.setUsername(username);
                bu.setPassword(password);
                //4.寻找数据信息与Bmob服务器信息匹配
                bu.login(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        //进入主页
                        startActivity(new Intent(LoginAcitivity.this,MainActivity.class));
                        //实现淡入淡出的效果
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        ToastUtils.toast(LoginAcitivity.this,"登录成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        ToastUtils.toast(LoginAcitivity.this,"登录失败:"+s);
                    }
                });

                break;
            case R.id.tv_forgetPwd:
                startActivity(new Intent(this,ForgetAcitity.class));
                //实现淡入淡出的效果
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.tv_weChat:
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
            ToastUtils.toast(LoginAcitivity.this,"再按一次退出程序");
        }
        fristTime=System.currentTimeMillis();
    }
}
