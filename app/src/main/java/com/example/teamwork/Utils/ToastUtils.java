package com.example.teamwork.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 伟捷。 on 2016/5/27.
 */

public class ToastUtils {

    public  static void toast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static void toast(Context context,int msgId){
        Toast.makeText(context,msgId,Toast.LENGTH_SHORT).show();
    }
}
