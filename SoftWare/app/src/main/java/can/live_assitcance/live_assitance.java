package can.live_assitcance;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;

import can.aboutsqlite.DBManager;
import can.aboutsqlite.User;
import can.memorycan.R;
import can.sms.HelloService;
import can.sms.TicketService;

public class live_assitance extends AppCompatActivity {
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Switch  sw1,sw2,sw3,sw4,sw5,sw6;
        ImageButton ig4;
        final Spinner sp1,sp2,sp3;
        final int[] percel={0,0,0};
        final int[] trip={0,0,0};
        final int[] weather={0,0,0};


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_assitance);
        /*
         * 获取各个权限的值
         * */

        ig4=findViewById(R.id.imageButton4);
        ig4.setOnClickListener(new tomain());

        final DBManager mgr = new DBManager(live_assitance.this);
        user=mgr.returnauser(1);

        trip[0]=user.getTrip_on();
        trip[1]=user.getTrip_priority();
        trip[2]=user.getParcel_paper();

        percel[0]=user.getTrip_on();
        percel[1]=user.getParcel_priority();
        percel[2]=user.getParcel_paper();

        weather[0]=user.getWeather_on();
        weather[1]=user.getParcel_priority();
        weather[2]=user.getParcel_paper();

        final String sdy[]={" "," "," "};
        /*
         * trip的spinner的设置
         * */
        if(trip[2]==0){
            sdy[0]="无";
        }
        else if(trip[2]==1){
            sdy[0]="低";
        }
        else if(trip[2]==2){
            sdy[0]="中";
        }
        else if(trip[2]==3){
            sdy[0]="高";
        }
        /*
         * parcel的spinner的设置
         * */
        if(percel[2]==0){
            sdy[1]="无";
        }
        else if(percel[2]==1){
            sdy[1]="低";
        }
        else if(percel[2]==2){
            sdy[1]="中";
        }
        else if(percel[2]==3){
            sdy[1]="高";
        }
        /*
         * weather的spinner的设置
         * */
        if(weather[2]==0){
            sdy[2]="无";
        }
        else if(weather[2]==1){
            sdy[2]="低";
        }
        else if(weather[2]==2){
            sdy[2]="中";
        }
        else if(weather[2]==3){
            sdy[2]="高";
        }

        /*
        * 对switch的对象获取
        * */
        sw1=findViewById(R.id.switch14);
        sw2=findViewById(R.id.switch4);
        sw3=findViewById(R.id.switch5);
        sw4=findViewById(R.id.switch6);
        sw5=findViewById(R.id.switch10);
        sw6=findViewById(R.id.switch11);

        /*
        * 对switch进行设置
        * */
        if(trip[0]>0){
            sw1.setChecked(true);
        }
        else{
            sw1.setChecked(false);
        }

        if(trip[2]>0){
            sw2.setChecked(true);
        }
        else{
            sw2.setChecked(false);
        }
        if(percel[0]>0){
            sw3.setChecked(true);
        }
        else{
            sw3.setChecked(false);
        }
        if(percel[2]>0){
            sw4.setChecked(true);
        }
        else{
            sw4.setChecked(false);
        }
        if(weather[0]>0){
            sw5.setChecked(true);
        }
        else{
            sw5.setChecked(false);
        }
        if(weather[2]>0){
            sw6.setChecked(true);
        }
        else{
            sw6.setChecked(false);
        }


        /*
        * 对Spinner进行对象获取
        * */
        sp1=findViewById(R.id.spinner4);
        sp2=findViewById(R.id.spinner5);
        sp3=findViewById(R.id.spinner9);

        /*
        * 对spinner进行设置
        *
        * */
        SpinnerAdapter apsAdapter= sp1.getAdapter();
        int k= apsAdapter.getCount();
        for(int i=0;i< k;i++){
            if(sdy[0].equals(apsAdapter.getItem(i).toString())){
                sp1.setSelection(i,true);
                break;
            }
        }

        SpinnerAdapter apsAdapter1= sp2.getAdapter();
        int k1= apsAdapter.getCount();
        for(int i=0;i< k;i++){
            if(sdy[1].equals(apsAdapter1.getItem(i).toString())){
                sp1.setSelection(i,true);
                break;
            }
        }

        SpinnerAdapter apsAdapter2= sp3.getAdapter();
        int k2= apsAdapter.getCount();
        for(int i=0;i< k;i++){
            if(sdy[2].equals(apsAdapter2.getItem(i).toString())){
                sp1.setSelection(i,true);
                break;
            }
        }


        /*
        * 对switch进行监听
        * */

        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent mIntent = new Intent(live_assitance.this, HelloService.class);
                if (sw1.isChecked() == true) {
                    getPermission(Manifest.permission.READ_SMS);
                    if (ActivityCompat.checkSelfPermission(live_assitance.this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
                        System.out.println("----------------------------------------------------------");
                        startService(mIntent);
                        trip[0]=1;
                        user.setTrip_on(trip[0]);
                        mgr.changeTrip_on(1, trip[0]);
                    } else {
                        System.out.println("***********************************************************");
                        sw1.setChecked(false);
                        stopService(mIntent);
                    }
                } else {
                    trip[0]=0;
                    user.setTrip_on(trip[0]);
                    mgr.changeTrip_on(1, trip[0]);
                    stopService(mIntent);
                }
            }
        });
        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    trip[2]=1;
                    user.setTrip_paper(trip[2]);
                    mgr.changeTrip_paper(1, trip[2]);

                }else{
                    trip[2]=0;
                    user.setTrip_paper(trip[2]);
                    mgr.changeTrip_paper(1, trip[2]);
                }
            }
        });
        sw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Intent mIntent = new Intent(live_assitance.this, TicketService.class);
                        if (sw1.isChecked() == true) {
                            getPermission(Manifest.permission.READ_SMS);
                            if (ActivityCompat.checkSelfPermission(live_assitance.this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
                                System.out.println("----------------------------------------------------------");
                                startService(mIntent);
                                percel[0]=1;
                                user.setParcel_on(percel[0]);
                            } else {
                                System.out.println("***********************************************************");
                                sw1.setChecked(false);
                                stopService(mIntent);
                            }
                        } else {
                            percel[0]=0;
                            user.setParcel_on(percel[0]);
                            stopService(mIntent);
                        }
                        mgr.changeParcel_on(1,percel[0]);
            }
        });
        sw4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    percel[2]=1;
                    user.setParcel_paper(percel[2]);


                }else{
                    percel[2]=0;
                    user.setParcel_paper(percel[2]);
                }
                mgr.changeParcel_paper(1, percel[2]);
            }
        });
        sw5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    weather[0]=1;
                    user.setWeather_on(weather[0]);


                }else{
                    weather[0]=0;
                    user.setWeather_on(weather[0]);
                }
                mgr.changeWeather_on(1, weather[0]);
            }
        });
        sw6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    weather[2]=1;
                    user.setWeather_paper(weather[2]);


                }else{
                    weather[2]=0;
                    user.setWeather_paper(weather[2]);
                }
                mgr.changeWallpaper_paper(1, weather[2]);
            }
        });


        /*
        * 对spinner进行监听
        * */

        sp1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            //选择item的选择点击监听事件

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                // 将所选mySpinner 的值带入myTextView 中

                String temp_s=sp1.getSelectedItem().toString();
                if(temp_s.equals("无")){
                    trip[1]=0;
                }
                else if(temp_s.equals("低")){
                    trip[1]=1;
                }
                else if(temp_s.equals("中")){
                    trip[1]=2;
                }
                else {
                    trip[1]=3;
                }
                mgr.changeTrip_priority(1, trip[1]);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        user.setTrip_priority(trip[1]);
        sp2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            //选择item的选择点击监听事件

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                // 将所选mySpinner 的值带入myTextView 中

                String temp_s=sp1.getSelectedItem().toString();
                if(temp_s.equals("无")){
                    percel[1]=0;
                }
                else if(temp_s.equals("低")){
                    percel[1]=1;
                }
                else if(temp_s.equals("中")){
                    percel[1]=2;
                }
                else {
                    percel[1]=3;
                }
                mgr.changeParcel_priority(1, percel[1]);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        user.setParcel_priority((percel[1]));
        sp3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            //选择item的选择点击监听事件

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                // 将所选mySpinner 的值带入myTextView 中

                String temp_s=sp1.getSelectedItem().toString();
                if(temp_s.equals("无")){
                    weather[1]=0;
                }
                else if(temp_s.equals("低")){
                    weather[1]=1;
                }
                else if(temp_s.equals("中")){
                    weather[1]=2;
                }
                else {
                    weather[1]=3;
                }
                mgr.changeWall_priority(1, weather[1]);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        user.setWeather_priority(weather[1]);

        /*
        * 将权限传入数据库
        * */
//        user.setTrip_on(trip[0]);
//        user.setTrip_priority(trip[1]);
//        user.setTrip_paper(trip[2]);
//
//        user.setParcel_on(percel[0]);
//        user.setParcel_priority((percel[1]));
//        user.setParcel_paper(percel[2]);
//
//        user.setWeather_on(weather[0]);
//        user.setWeather_priority(weather[1]);
//        user.setWeather_paper(weather[2]);

    }

    private class tomain implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            Intent intent=new Intent(live_assitance.this,can.main_delete.MainActivity.class);
            startActivity(intent);
        }
    }

    private void getPermission(String permissios) {
        if (ActivityCompat.checkSelfPermission(this,permissios) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {permissios}, 123);
        }
    }
}
