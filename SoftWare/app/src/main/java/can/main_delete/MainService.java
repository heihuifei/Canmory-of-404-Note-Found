package can.main_delete;

import android.annotation.SuppressLint;
import can.aboutsqlite.DBManager;
import can.aboutsqlite.Memo;
import can.memorycan.R;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainService extends Service {

    private static final String TAG = "MainService";
    int wstate = 1;
    ConstraintLayout toucherLayout;
    WindowManager.LayoutParams params;
    WindowManager windowManager;
    ImageButton imageButton1;
    private DBManager mgr;
    //状态栏高度.
    int statusBarHeight = -1;

    //不与Activity进行绑定.
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        if(wstate == 1){
            Log.i(TAG,"MainService Created");
            createToucher();
            Toast.makeText(MainService.this,"现已开启Toucher",Toast.LENGTH_SHORT).show();
        }
        else{
            Log.i(TAG,"MainService Ended");
            endService();
            Toast.makeText(MainService.this,"现已关闭Toucher",Toast.LENGTH_SHORT).show();
        }
        //mgr = new DBManager(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void createToucher() {
        //赋值WindowManager&LayoutParam.
        params = new WindowManager.LayoutParams();
        windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        //设置type.系统提示型窗口，一般都在应用程序窗口之上.
        //Android8.0行为变更，对8.0进行适配https://developer.android.google.cn/about/versions/oreo/android-8.0-changes#o-apps
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        //Android 8.0+
        //params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        params.format = PixelFormat.RGBA_8888;//设置效果为背景透明.
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//设置flags.不可聚焦及不可使用按钮对悬浮窗进行操控.
        params.gravity = Gravity.LEFT | Gravity.TOP;//设置窗口初始停靠位置.
        params.x = 0;
        params.y = 0;
        params.width = 200;//设置悬浮窗口长宽数据.
        params.height = 200;
        LayoutInflater inflater = LayoutInflater.from(getApplication());
        toucherLayout = (ConstraintLayout) inflater.inflate(R.layout.toucherlayout,null);//获取浮动窗口视图所在布局.
        windowManager.addView(toucherLayout,params);//添加toucherlayout
        Log.i(TAG,"toucherlayout-->left:" + toucherLayout.getLeft());
        Log.i(TAG,"toucherlayout-->right:" + toucherLayout.getRight());
        Log.i(TAG,"toucherlayout-->top:" + toucherLayout.getTop());
        Log.i(TAG,"toucherlayout-->bottom:" + toucherLayout.getBottom());
        toucherLayout.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);//主动计算出当前View的宽高信息.
        //用于检测状态栏高度.
        int resourceId = getResources().getIdentifier("status_bar_height","dimen","android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        Log.i(TAG,"状态栏高度为:" + statusBarHeight);
        imageButton1 = (ImageButton) toucherLayout.findViewById(R.id.imageButton1);//浮动窗口按钮.
        imageButton1.setOnClickListener(new View.OnClickListener() {
            long[] hints = new long[2];
            @Override
            public void onClick(View v) {
                Log.i(TAG,"点击了");
                System.arraycopy(hints,1,hints,0,hints.length -1);
                hints[hints.length -1] = SystemClock.uptimeMillis();
                if (SystemClock.uptimeMillis() - hints[0] >= 700) {
                    Log.i(TAG,"要执行");
                    Toast.makeText(MainService.this,"连续点击两次以退出",Toast.LENGTH_SHORT).show();
                }
                else {
                    endService();
                }
            }
        });
        imageButton1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                params.x = (int) event.getRawX() - 100;
                params.y = (int) event.getRawY() - 100 - statusBarHeight;
                windowManager.updateViewLayout(toucherLayout,params);
                return false;
            }
        });
    }

    public void endService() {
        Log.i(TAG,"即将关闭");
        wstate = 0;
        stopSelf();
    }

    @Override
    public void onDestroy() {
        if (imageButton1 != null) {
            windowManager.removeView(toucherLayout);
        }
        super.onDestroy();
    }
}