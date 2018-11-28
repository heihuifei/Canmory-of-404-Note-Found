package can.wallpaper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import can.memorycan.R;

public class select_wallpaper extends AppCompatActivity {

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_wallpaper);
        button=(Button)findViewById(R.id.radioButton2);
        imageView1=(ImageView)findViewById(R.id.imageView1) ;
        imageView2=(ImageView)findViewById(R.id.imageView2) ;
        imageView3=(ImageView)findViewById(R.id.imageView3) ;
        imageView4=(ImageView)findViewById(R.id.imageView4) ;
        imageView5=(ImageView)findViewById(R.id.imageView5) ;
        imageView6=(ImageView)findViewById(R.id.imageView6) ;
        button.setOnClickListener(new WallGenerate());
        imageView1.setOnClickListener(new OnClickListener1());
        imageView2.setOnClickListener(new OnClickListener2());
        imageView3.setOnClickListener(new OnClickListener3());
        imageView4.setOnClickListener(new OnClickListener4());
        imageView5.setOnClickListener(new OnClickListener5());
        imageView6.setOnClickListener(new OnClickListener6());
    }
    private class WallGenerate implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(select_wallpaper.this,wallpaper_generate.class);
            Bundle bd=new Bundle();
            bd.putInt("id",0);
            intent.putExtras(bd);
            startActivity(intent);
            finish();
        }
    }
    private class OnClickListener1 implements View.OnClickListener{
        public void onClick(View v) {
            Intent intent = new Intent(select_wallpaper.this,wallpaper_generate.class);
            Bundle bd=new Bundle();
            bd.putInt("id",1);
            intent.putExtras(bd);
            startActivity(intent);

        }
    }
    private class OnClickListener2 implements View.OnClickListener{
        public void onClick(View v) {
            Intent intent = new Intent(select_wallpaper.this,wallpaper_generate.class);
            Bundle bd=new Bundle();
            bd.putInt("id",2);
            intent.putExtras(bd);
            startActivity(intent);

        }
    }
    private class OnClickListener3 implements View.OnClickListener{
        public void onClick(View v) {
            Intent intent = new Intent(select_wallpaper.this,wallpaper_generate.class);
            Bundle bd=new Bundle();
            bd.putInt("id",3);
            intent.putExtras(bd);
            startActivity(intent);

        }
    }
    private class OnClickListener4 implements View.OnClickListener{
        public void onClick(View v) {
            Intent intent = new Intent(select_wallpaper.this,wallpaper_generate.class);
            Bundle bd=new Bundle();
            bd.putInt("id",4);
            intent.putExtras(bd);
            startActivity(intent);
            finish();
        }
    }
    private class OnClickListener5 implements View.OnClickListener{
        public void onClick(View v) {
            Intent intent = new Intent(select_wallpaper.this,wallpaper_generate.class);
            Bundle bd=new Bundle();
            bd.putInt("id",5);
            intent.putExtras(bd);
            startActivity(intent);

        }
    }
    private class OnClickListener6 implements View.OnClickListener{
        public void onClick(View v) {
            Intent intent = new Intent(select_wallpaper.this,wallpaper_generate.class);
            Bundle bd=new Bundle();
            bd.putInt("id",6);
            intent.putExtras(bd);
            startActivity(intent);

        }
    }
}
