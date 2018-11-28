package can.memo_add_details;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import can.main_delete.MainActivity;
import can.memorycan.R;
import can.memorycan.memo_add.memo_add;

import static java.security.AccessController.getContext;

public class memo_add_details extends AppCompatActivity {
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private Bundle bundle;
    private Intent intent;

    //插入图片的Activity的返回的code
    static final int IMAGE_CODE = 99;
    //数据库帮助类
    DataBaseUtil dbUtil;

    //内容对象
    Flag flag;      //传进来的flag
    Boolean isChanged = false;      //判断内容是否被修改过


    //控件申明

    EditText content;           //内容
    EditText title;             //标题
    View inflate;               //dialog的容器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_add_details);
        /*
        * 获取传递来的信息
        * */
        intent=getIntent();
        bundle=intent.getExtras();


        ImageButton imageButton_back = (ImageButton) findViewById(R.id.imageButton_back);
        imageButton_back.setOnClickListener(new MemoAdd());
        EditText editText0 = (EditText) findViewById(R.id.editText0);
        content=editText0;

        content.setText(bundle.getString("m_content"));

        Button imageButton_preview = (Button) findViewById(R.id.imageButton_preview);
        imageButton_preview.setOnClickListener(new MemoAdd());

        ImageButton imageButton_pic = (ImageButton) findViewById(R.id.imageButton_pic);
        imageButton_pic.setOnClickListener(new MemoAdd_pic());


    }

    private class MemoAdd implements View.OnClickListener {
        @Override
        public void onClick(View v) {
           // Toast.makeText(memo_add_details.this,content.getText().toString() , Toast.LENGTH_SHORT).show();
            bundle.putString("final_content",content.getText().toString());
            intent.putExtras(bundle);
            setResult(0x717,intent);//通过别人传过来的意图反向回去
            finish();
        }

    }
    private class MemoAdd_pic implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            callGallery();
        }

    }

    public void camear(){
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},1001);
            }
        }else {
        }
        int permission = ActivityCompat.checkSelfPermission(memo_add_details.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(memo_add_details.this,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
        }
//        //调用系统图库
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");  //相片类型
//        startActivityForResult(intent,1);


        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, IMAGE_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bitmap bm = null;
        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();
        if(requestCode == IMAGE_CODE){
            try{
                // 获得图片的uri
                Uri originalUri = data.getData();
                bm = MediaStore.Images.Media.getBitmap(resolver,originalUri);
                String[] proj = {MediaStore.Images.Media.DATA};
                // 好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = managedQuery(originalUri,proj,null,null,null);
                // 按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                // 最后根据索引值获取图片路径
                String path = cursor.getString(column_index);
                insertImg(path);
                //Toast.makeText(AddFlagActivity.this,path,Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(memo_add_details.this,"图片插入失败",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private Uri uri;
    //String path;
    int mTargetW;
    int mTargetH;

    //region 插入图片
    private void insertImg(String path){
        String tagPath = "<img src=\""+path+"\"/>";//为图片路径加上<img>标签
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if(bitmap != null){
            SpannableString ss = getBitmapMime(path,tagPath);
            insertPhotoToEditText(ss);
            content.append("\n");
            Log.d("YYPT", content.getText().toString());
        }
    }
    //region 将图片插入到EditText中
    private void insertPhotoToEditText(SpannableString ss){
        Editable et = content.getText();
        int start = content.getSelectionStart();
        et.insert(start,ss);
        content.setText(et);
        content.setSelection(start+ss.length());
        content.setFocusableInTouchMode(true);
        content.setFocusable(true);
    }
    //endregion
    private SpannableString getBitmapMime(String path,String tagPath) {
        SpannableString ss = new SpannableString(tagPath);//这里使用加了<img>标签的图片路径

        int width = ScreenUtils.getScreenWidth(memo_add_details.this);
        int height = ScreenUtils.getScreenHeight(memo_add_details.this);




        Log.d("ScreenUtils", "高度:"+height+",宽度:"+width);

        Bitmap bitmap = ImageUtils.getimage(path);

        /*
        //高:754，宽1008
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap bitmap = BitmapFactory.decodeFile(path,options);
        */
        Log.d("YYPT_IMG_COMPRESS", "高度："+bitmap.getHeight()+",宽度:"+bitmap.getWidth());


        ImageSpan imageSpan = new ImageSpan(this, bitmap);
        ss.setSpan(imageSpan, 0, tagPath.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;


    }
    private void initContent(){
        //input是获取将被解析的字符串
        String input = flag.getContent().toString();
        //将图片那一串字符串解析出来,即<img src=="xxx" />
        Pattern p = Pattern.compile("\\<img src=\".*?\"\\/>");
        Matcher m = p.matcher(input);

        //使用SpannableString了，这个不会可以看这里哦：http://blog.sina.com.cn/s/blog_766aa3810100u8tx.html#cmt_523FF91E-7F000001-B8CB053C-7FA-8A0
        SpannableString spannable = new SpannableString(input);
        while(m.find()){
            //Log.d("YYPT_RGX", m.group());
            //这里s保存的是整个式子，即<img src="xxx"/>，start和end保存的是下标
            String s = m.group();
            int start = m.start();
            int end = m.end();
            //path是去掉<img src=""/>的中间的图片路径
            String path = s.replaceAll("\\<img src=\"|\"\\/>","").trim();
            //Log.d("YYPT_AFTER", path);

            //利用spannableString和ImageSpan来替换掉这些图片
            int width = ScreenUtils.getScreenWidth(memo_add_details.this);
            int height = ScreenUtils.getScreenHeight(memo_add_details.this);

            Bitmap bitmap = ImageUtils.getSmallBitmap(path,width,240);
            ImageSpan imageSpan = new ImageSpan(this, bitmap);
            spannable.setSpan(imageSpan,start,end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        content.setText(spannable);
        content.append("\n");
        //Log.d("YYPT_RGX_SUCCESS",content.getText().toString());
    }


}
