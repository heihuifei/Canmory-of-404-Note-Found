package can.sliderbar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import can.main_delete.MainActivity;
import can.memorycan.R;

public class sliderbar extends AppCompatActivity {
    RadioButton rb1,rb2,rb3,rb4,rb5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliderbar);

        /*
        * 获取RadioButton的对象
        * */
        rb1=findViewById(R.id.radioButton4);
        rb2=findViewById(R.id.radioButton5);
        rb3=findViewById(R.id.radioButton7);
        rb4=findViewById(R.id.radioButton8);
        rb5=findViewById(R.id.radioButton9);

        /*
        * 对对象进行监听跳转
        * */

        rb1.setOnClickListener(new tomain());
        rb2.setOnClickListener(new toliveassitance());
        rb3.setOnClickListener(new towallpapergenerate());
        rb4.setOnClickListener(new tosetting());
        rb5.setOnClickListener(new tologin());
    }
    private class tomain implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(sliderbar.this,MainActivity.class);
            startActivity(intent);
        }
    }
    private class toliveassitance implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(sliderbar.this,can.live_assitcance.live_assitance.class);
            startActivity(intent);
        }
    }
    private class towallpapergenerate implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(sliderbar.this,can.wallpaper.wallpaper_generate.class);
            startActivity(intent);
        }
    }
    private class tosetting implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(sliderbar.this,can.memorycan.setting.setting.class);
            startActivity(intent);
        }
    }
    private class tologin implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(sliderbar.this,can.login.LoginActivity.class);
            startActivity(intent);
        }
    }

}
