package in.campanion.campuscompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowTable extends AppCompatActivity {
    Integer dayInt;
    DatabaseHelper db;
    Cursor c1;
    String period,setPeriodData;
    TextView t, t1, t2, t3, t4, t5, t6, t7, t8, t9,t10;
    LinearLayout nine;
    Boolean status1,status2,status3,status4,status5,status6,status7,status8,status9,status10;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_table);
        Bundle bundle = getIntent().getExtras();
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        if(sharedPreferences.getBoolean("FirstUse",true) && dayInt == null){
            showDialog();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("FirstUse",false);
            editor.apply();
        }
        if(bundle != null){
            dayInt = bundle.getInt("dayInt");
        }else
            dayInt = 1;
        db = new DatabaseHelper(this);
        t = (TextView) findViewById(R.id.dayOfWeek);
        t1 = (TextView) findViewById(R.id.firstHour);
        t2 = (TextView) findViewById(R.id.secondHour);
        t3 = (TextView) findViewById(R.id.thirdHour);
        t4 = (TextView) findViewById(R.id.fourthHour);
        t5 = (TextView) findViewById(R.id.fifthHour);
        t6 = (TextView) findViewById(R.id.sixthHour);
        t7 = (TextView) findViewById(R.id.seventhHour);
        t8 = (TextView) findViewById(R.id.eightthHour);
        t9 = (TextView) findViewById(R.id.ninethHour);
        t10 = (TextView) findViewById(R.id.tenthHour);
        nine = (LinearLayout) findViewById(R.id.ninethGroup);
        scrollView = (ScrollView)findViewById(R.id.showTableScrollView);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            int downX,upX;
            int upY,downY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    downX = (int) event.getX();
                    downY = (int) event.getY();
                    return true;
                } else if(event.getAction() == MotionEvent.ACTION_UP){
                    upX = (int) event.getX();
                    upY = (int) event.getY();
                    if(upX - downX > 400){
                       dayBefore(v);
                    }else if(upX - downX < -400){
                        nextDay(v);
                    }
                    return true;
                }
                return false;
            }
        });

        firstStep(dayInt.toString());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        firstStep(dayInt.toString());
    }

    @Override
    protected void onDestroy() {
        c1.close();
        db.close();
        super.onDestroy();
    }

    private void initialiseStatus() {
        status1 = status2 = status3 = status4 = status5 = status6 = status7 = status8 = status9 = status10 = false;
    }

    public void firstStep(String s){
        initialiseStatus();
//        nine.setVisibility(View.GONE);
        t.setText("Day Order "+dayInt);
        c1 = db.getTimeTablePeriod(s);
        if (c1.getCount() != 0) {
            //Toast.makeText(this,c1.getCount()+" Rows and "+c1.getColumnCount()+" Columns",Toast.LENGTH_SHORT).show();
            initialise(c1);
        } else{
           // Toast.makeText(this, "First set TimeTable by clicking on \"SET/EDIT TIME TABLE\" button on the bottom of the screen.", Toast.LENGTH_LONG).show();
            setFree();
        }
        checkStatus();
    }

    private void checkStatus() {
        if(!status1){
            t1.setText("-----\n-----\n-----");
        }
        if(!status2){
            t2.setText("-----\n-----\n-----");
        }
        if(!status3){
            t3.setText("-----\n-----\n-----");
        }
        if(!status4){
            t4.setText("-----\n-----\n-----");
        }
        if(!status5){
            t5.setText("-----\n-----\n-----");
        }
        if(!status6){
            t6.setText("-----\n-----\n-----");
        }
        if(!status7){
            t7.setText("-----\n-----\n-----");
        }
        if(!status8){
            t8.setText("-----\n-----\n-----");
        }
        if(!status9){
            t9.setText("-----\n-----\n-----");
        }
        if(!status10){
            t10.setText("-----\n-----\n-----");
        }
    }

    private void setFree() {
    t1.setText("-----\n-----\n-----");
    t2.setText("-----\n-----\n-----");
    t3.setText("-----\n-----\n-----");
    t4.setText("-----\n-----\n-----");
    t5.setText("-----\n-----\n-----");
    t6.setText("-----\n-----\n-----");
    t7.setText("-----\n-----\n-----");
    t8.setText("-----\n-----\n-----");
    t9.setText("-----\n-----\n-----");
    t10.setText("-----\n-----\n-----");
    }

    public void initialise(Cursor c) {
        c.moveToFirst();
        setPeriodData = c.getString(1)+"\n"+c.getString(2)+"\n"+c.getString(3);
        period = c.getString(0);
        setData(setPeriodData);
        while (c.moveToNext()){
            setPeriodData =c.getString(1)+"\n"+c.getString(2)+"\n"+c.getString(3);
            period = c.getString(0);
            setData(setPeriodData);
        }
    }

    public void setData(String s){
        switch (period) {
            case "1":
                t1.setText(s);
                status1 = true;
                break;
            case "2":
                t2.setText(s);
                status2 = true;
                break;
            case "3":
                t3.setText(s);
                status3 = true;
                break;
            case "4":
                t4.setText(s);
                status4 = true;
                break;
            case "5":
                t5.setText(s);
                status5 = true;
                break;
            case "6":
                t6.setText(s);
                status6 = true;
                break;
            case "7":
                t7.setText(s);
                status7 = true;
                break;
            case "8":
                t8.setText(s);
                status8 = true;
                break;
            case "9":
                //nine.setVisibility(View.VISIBLE);
                t9.setText(s);
                status9 = true;
                break;
            case "10":
                //nine.setVisibility(View.VISIBLE);
                t10.setText(s);
                status10 = true;
                break;
        }
    }

    public void dayBefore(View view) {
        if(dayInt-- == 1)
            dayInt = 5;
        firstStep(dayInt.toString());
    }

    public void nextDay(View view) {
        if(dayInt++ == 5)
            dayInt = 1;
        firstStep(dayInt.toString());
    }

    public void eddEdit(View view) {
        startActivity(new Intent(this, AddEdit.class));
    }

    private void showDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("It seems that either you are using the app for first time or have not set the time table. If you want to set time table then click on the 'SET/EDIT TIME TABLE' button on the bottom of the screen.");
        alert.setTitle("First Use");
        alert.setNegativeButton("I understood", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.setCancelable(false);
        alert.create().show();
    }
}
