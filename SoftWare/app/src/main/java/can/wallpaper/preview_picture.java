package can.wallpaper;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import can.aboutsqlite.DBManager;
import can.aboutsqlite.Memo;
import can.memo_add_details.memo_add_details;
import can.memorycan.R;

import java.io.IOException;
import java.util.ArrayList;

public class preview_picture extends AppCompatActivity {
    private DBManager mgr;
    private Button buttom,preview_picture_generate;
    private ImageView imageView;
    private ArrayList<Memo> lData = null;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    public int judge_flag;
    public View view;
    public int width;
    public int  height;
    public Bitmap bitmap;

    int pictureId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_picture);
        buttom=findViewById(R.id.button1);
        imageView=findViewById(R.id.imageView);
        buttom.setOnClickListener(new Wall_generate());

        preview_picture_generate=findViewById(R.id.preview_picture_generate);
        preview_picture_generate.setOnClickListener(new generate());

        textView1=findViewById(R.id.textView1);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);
        textView5=findViewById(R.id.textView5);
        mgr=new DBManager(this);
        ArrayList<Memo> list=mgr.returnmemo3(1);
        /*需要数据库返回带优先级的列表*/

        /*添加文字信息*/
        String temp_s="            ";
        String use_0=list.get(0).getMemo_title()+temp_s+list.get(0).getmemo_dtimestring();
        String use_1=list.get(1).getMemo_title()+temp_s+list.get(1).getmemo_dtimestring();
        String use_2=list.get(2).getMemo_title()+temp_s+list.get(2).getmemo_dtimestring();
        String use_3=list.get(3).getMemo_title()+temp_s+list.get(3).getmemo_dtimestring();
        String use_4=list.get(4).getMemo_title()+temp_s+list.get(4).getmemo_dtimestring();
        textView1.setText(use_0);
        textView2.setText(use_1);
        textView3.setText(use_2);
        textView4.setText(use_3);
        textView5.setText(use_4);





        Intent intent = getIntent();
        Bundle bd=intent.getExtras();
        int[] temp_sp=bd.getIntArray("temp_sp");

        assert temp_sp != null;
        /*缺优先级的展示*/

        /*判断颜色*/
        if(temp_sp[1]==0) {
            textView1.setTextColor(Color.RED);
            textView2.setTextColor(Color.RED);
            textView3.setTextColor(Color.RED);
            textView4.setTextColor(Color.RED);
            textView5.setTextColor(Color.RED);
        }
        else if(temp_sp[1]==1){
            textView1.setTextColor(Color.GREEN);
            textView2.setTextColor(Color.GREEN);
            textView3.setTextColor(Color.GREEN);
            textView4.setTextColor(Color.GREEN);
            textView5.setTextColor(Color.GREEN);
        }
        else{
            textView1.setTextColor(Color.BLUE);
            textView2.setTextColor(Color.BLUE);
            textView3.setTextColor(Color.BLUE);
            textView4.setTextColor(Color.BLUE);
            textView5.setTextColor(Color.BLUE);
        }

        /*设置字体*/
        textView1.setTextSize(temp_sp[2]);
        textView2.setTextSize(temp_sp[2]);
        textView3.setTextSize(temp_sp[2]);
        textView4.setTextSize(temp_sp[2]);
        textView5.setTextSize(temp_sp[2]);

        pictureId = bd.getInt("id");
        if(pictureId==0||pictureId==1||pictureId==3||pictureId==5){
            imageView.setBackgroundResource(R.drawable.wallpaper1);
        }
        else{
            imageView.setBackgroundResource(R.drawable.wallpaper2);
        }


    }
    public class Wall_generate implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(preview_picture.this, wallpaper_generate.class);
            startActivity(intent);
        }

    }
    public class generate implements View.OnClickListener{
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {
            //获取当前屏幕的大小
            width = getWindow().getDecorView().getRootView().getWidth();
            height = getWindow().getDecorView().getRootView().getHeight();
            //生成相同大小的图片
            int height0=(int)height;
            bitmap = Bitmap.createBitmap( width, 300, Bitmap.Config.ARGB_8888 );
            //找到当前页面的跟布局
            view = getWindow().getDecorView().getRootView();
            //设置缓存
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache();
            //从缓存中获取当前屏幕的图片
            bitmap = view.getDrawingCache();
            imageView.setImageBitmap(bitmap);
            SetLockWallPaper();       //设置锁屏壁纸
//          SetWallPaper();         //设置桌面壁纸


        }
    }
    public void SetWallPaper() {
        WallpaperManager mWallManager = WallpaperManager.getInstance(this);
        try {
            Resources res = preview_picture.this.getResources();
            Bitmap bitmap;
            if(judge_flag==0){
                Bitmap bitmap1= BitmapFactory.decodeResource(res, R.drawable.wallpaper1);
                bitmap=bitmap1;
                Toast.makeText(preview_picture.this, "壁纸设置成功0", Toast.LENGTH_SHORT)
                        .show();
            }
            else{
                Bitmap bitmap1= BitmapFactory.decodeResource(res, R.drawable.wallpaper2);
                bitmap=bitmap1;
                Toast.makeText(preview_picture.this, "壁纸设置成功1", Toast.LENGTH_SHORT)
                        .show();
            }
            mWallManager.setBitmap(bitmap);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void SetLockWallPaper() {
        // TODO Auto-generated method stub
        WallpaperManager mWallManager = WallpaperManager.getInstance(this);

        try {

            mWallManager.setBitmap(bitmap,null,true,WallpaperManager.FLAG_LOCK);
            Toast.makeText(preview_picture.this,"壁纸生成成功",Toast.LENGTH_SHORT).show();
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        //应用的最后一个Activity关闭时应释放DB
        mgr.closeDB();
    }



}

