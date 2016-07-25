package com.example.teamwork.View.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teamwork.R;
import com.example.teamwork.Utils.ToastUtils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by 伟捷。 on 2016/5/23.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    /**
     * 用于返回上一层按钮的ImageView图片
     */
    private ImageView img_back;
    /**
     * 用于获取验证码按钮和注册按钮点击的Button
     */
    private Button btn_getAuthCode,btn_register;
    /**
     * 用于切换邮箱注册和微信登录的TextView文本
     */
    private TextView tv_email,tv_weChat;
    /**
     * 用于输入手机号码、验证码、帐号和密码的EditText文本框
     */
    private EditText et_phone,et_authCode,et_name,et_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
        btn_getAuthCode=(Button)findViewById(R.id.btn_getAuthCode);
        btn_getAuthCode.setOnClickListener(this);
        tv_email=(TextView)findViewById(R.id.tv_email);
        tv_email.setOnClickListener(this);
        tv_weChat=(TextView)findViewById(R.id.tv_weChat);
        tv_weChat.setOnClickListener(this);
        et_phone=(EditText)findViewById(R.id.et_phone);
        et_authCode=(EditText)findViewById(R.id.et_authCode);
        et_name=(EditText)findViewById(R.id.et_name);
        et_pwd=(EditText)findViewById(R.id.et_pwd);
    }

    /**
     * 控件触发事件
     * @param v
     */
    @Override
    public void onClick(View v) {
       switch (v.getId())
       {
           case R.id.img_back:
               startActivity(new Intent(this,BufferActivity.class));
               //实现淡入淡出的效果
               overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
               break;
           /**
            * 注册功能和验证码实现的主要代码，在这里。
            */
           case R.id.btn_getAuthCode:
               //1.获取手机号码
                    String phone=et_phone.getText().toString().trim();
               //2.判断文本框是否为空
               if (phone.isEmpty())
               {
                   ToastUtils.toast(RegisterActivity.this,"手机号码不能为空");
                   return;
               }
               //2.与服务器对接
               BmobSMS.requestSMSCode(this, phone, "TeamWork", new RequestSMSCodeListener() {
                   @Override
                   //integer用于查询本次短信发送详情
                   public void done(Integer integer, BmobException e) {
                     if (e==null){//验证码发送成功
                         ToastUtils.toast(RegisterActivity.this,"请查收验证码");
                     }
                   }
               });
               new TimeCount(60 * 1000,1000).start();
               break;
           case R.id.btn_register:
               //1.获取文本框内容
                  String mobilePhoneNumber= et_phone.getText().toString().trim();
                  String getAuthCode= et_authCode.getText().toString().trim();
                  String username= et_name.getText().toString().trim();
                  String password=et_pwd.getText().toString().trim();
               //2.判断文本框是否为空
               if (mobilePhoneNumber.isEmpty()||username.isEmpty()
                       ||password.isEmpty()||getAuthCode.isEmpty()){
                   ToastUtils.toast(RegisterActivity.this,"信息不能为空");
                   return;
               }
               //3.验证手机号码
               BmobSMS.verifySmsCode(RegisterActivity.this, mobilePhoneNumber,
                       getAuthCode, new VerifySMSCodeListener() {
                   @Override
                   public void done(BmobException e) {
                       if (e==null){//短信验证码已验证成功
                           ToastUtils.toast(RegisterActivity.this,"验证成功");
                       }else{
                           ToastUtils.toast(RegisterActivity.this,"验证失败:code="
                                   +e.getErrorCode()+"msg="+e.getLocalizedMessage());
                       }
                   }
               });
               //4.寻找服务器，与储存信息匹配
               BmobUser bu=new BmobUser();
               bu.setMobilePhoneNumber(mobilePhoneNumber);
               bu.setUsername(username);
               bu.setPassword(password);
               //5.保存数据信息到Bmob服务器
               bu.signUp(this, new SaveListener() {
                   @Override
                   public void onSuccess() {
                       ToastUtils.toast(RegisterActivity.this,"注册成功");
                   }
                   @Override
                   public void onFailure(int i, String s) {
                       ToastUtils.toast(RegisterActivity.this,"注册失败:"+s);
                   }
               });
               break;
           case R.id.tv_email:
               startActivity(new Intent(this,EmailRegisterAcitivity.class));
               //实现淡入淡出的效果
               overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
               break;
           case R.id.tv_weChat:
               break;
       }
    }

    /**
     * 倒计时
     */
    private class TimeCount extends CountDownTimer{
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            btn_getAuthCode.setClickable(false);
            btn_getAuthCode.setText(millisUntilFinished / 1000+"");
        }
        @Override
        public void onFinish() {
            btn_getAuthCode.setText("重新获取");
            btn_getAuthCode.setClickable(true);

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
            ToastUtils.toast(RegisterActivity.this,"再按一次退出程序");
        }
        fristTime=System.currentTimeMillis();
    }
}
