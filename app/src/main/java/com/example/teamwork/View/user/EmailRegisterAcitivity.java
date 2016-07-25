package com.example.teamwork.View.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teamwork.R;
import com.example.teamwork.Utils.ToastUtils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 伟捷。 on 2016/5/26.
 */

public class EmailRegisterAcitivity extends Activity implements View.OnClickListener {
    /**
     * 用于返回上一层按钮的ImageView图片
     */
    private ImageView img_back;
    /**
     * 用于注册按钮点击的Button
     */
    private Button btn_register;
    /**
     * 用于输入邮箱和名字和密码的EditText文本框
     */
    private EditText et_email,et_name,et_pwd;
    /**
     * 用于切换注册方式按钮的TextView文本：手机或微信。
     */
    private TextView tv_phone,tv_weChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);

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
        btn_register=(Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        tv_phone=(TextView)findViewById(R.id.tv_phone);
        tv_phone.setOnClickListener(this);
        tv_weChat=(TextView)findViewById(R.id.tv_weChat);
        tv_weChat.setOnClickListener(this);
        et_email=(EditText)findViewById(R.id.et_email);
        et_name=(EditText)findViewById(R.id.et_name);
        et_pwd=(EditText)findViewById(R.id.et_pwd);
    }

    /***
     * 控件触发事件
     * @param v
     */
    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.img_back:
             startActivity(new Intent(this,BufferActivity.class));
             //实现淡入淡出的效果
             overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
             break;
         /**
          * 邮箱注册功能主要代码，在这里。
          */
         case R.id.btn_register:
             //1.获取文本框内容
             String email= et_email.getText().toString().trim();
             String username = et_name.getText().toString().trim();
             String password= et_pwd.getText().toString().trim();
             //2.判断文本框是否为空
             if (email.isEmpty()||username.isEmpty()||password.isEmpty()){
                 ToastUtils.toast(EmailRegisterAcitivity.this,"信息不能为空");
                 return;
             }
             //3.寻找服务器，与储存信息匹配
             BmobUser bu = new BmobUser();
             bu.setEmail(email);
             bu.setUsername(username);
             bu.setPassword(password);
             //4.保存数据信息到Bmob服务器
             bu.signUp(this, new SaveListener() {
                 @Override
                 public void onSuccess() {
                     ToastUtils.toast(EmailRegisterAcitivity.this,"注册成功");
                 }
                 @Override
                 public void onFailure(int i, String s) {
                     ToastUtils.toast(EmailRegisterAcitivity.this,"注册失败:"+s);
                 }
             });
             break;
         case R.id.tv_phone:
             startActivity(new Intent(this,RegisterActivity.class));
             //实现淡入淡出的效果
             overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
             break;
         case  R.id.tv_weChat:
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
            ToastUtils.toast(EmailRegisterAcitivity.this,"再按一次退出程序");
        }
        fristTime=System.currentTimeMillis();
    }

}
