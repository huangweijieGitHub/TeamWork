package com.example.teamwork.View.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.teamwork.R;
import com.example.teamwork.Utils.ToastUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;

/**
 * Created by 伟捷。 on 2016/5/23.
 */
public class ForgetAcitity extends Activity implements View.OnClickListener {
    /**
     * 用于返回上一层按钮的ImageView图片
     */
    private ImageView img_back;
    /**
     * 用于忘记密码按钮点击的Button
     */
    private Button  btn_forget;
    /**
     * 用于输入帐号的EditText文本框
     */
    private EditText et_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        // 初始化布局元素
        initViews();
    }

    /**
     * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void initViews() {
     img_back=(ImageView)findViewById(R.id.img_back);
     img_back.setOnClickListener(this);
     btn_forget=(Button)findViewById(R.id.btn_forget);
     btn_forget.setOnClickListener(this);
     et_number=(EditText)findViewById(R.id.et_number);

    }

    /***
     * 控件触发事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                 startActivity(new Intent(this,LoginAcitivity.class));
                //实现淡入淡出的效果
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                break;
            /**
             * 忘记密码功能主要代码，在这里。
             */
            case R.id.btn_forget:
                //1.获取文本框内容
                final String number=et_number.getText().toString().trim();
                //2.判断文本框是否为空
                if (number.isEmpty()){
                    ToastUtils.toast(ForgetAcitity.this,"邮箱不能为空");
                     return;
                }
                //3.寻找服务器，与储存信息匹配
                BmobUser  bu = new BmobUser();
                bu.setEmail(number);
                bu.resetPasswordByEmail(this, number,
                        new ResetPasswordByEmailListener() {
                    @Override
                    public void onSuccess() {
                        ToastUtils.toast(ForgetAcitity.this,"重置密码请求成功，" +
                                "请到" + number+ "邮箱进行密码重置操作");
                    }
                    @Override
                    public void onFailure(int i, String s) {
                        ToastUtils.toast(ForgetAcitity.this,"重置密码失败:" +s);
                    }
                });
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
            ToastUtils.toast(ForgetAcitity.this,"再按一次退出程序");
        }
        fristTime=System.currentTimeMillis();
    }
}
