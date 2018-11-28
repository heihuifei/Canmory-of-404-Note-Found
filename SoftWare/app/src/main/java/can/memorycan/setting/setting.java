package can.memorycan.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import can.live_assitcance.live_assitance;
import can.memorycan.R;

public class setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageButton ig8;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ig8=findViewById(R.id.imageButton8);

        ig8.setOnClickListener(new tomain());
    }
    private class tomain implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(setting.this,can.main_delete.MainActivity.class);
            startActivity(intent);
        }
    }
}
