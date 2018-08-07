package in.campanion.campuscompanion;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MessDetail extends AppCompatActivity {

    TextView messtime1,messtime2,messtime3,messtime4,day;
    Integer setDay;
    Cursor cursor;
    MessDatabase messDatabase;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_detail);
        messDatabase = new MessDatabase(this);
        messtime1 =(TextView)findViewById(R.id.breakfast);
        messtime2 =(TextView)findViewById(R.id.lunch);
        messtime3 =(TextView)findViewById(R.id.snacks);
        messtime4 =(TextView)findViewById(R.id.dinner);
        scrollView = (ScrollView)findViewById(R.id.activity_mess_detail_scrollview);
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
        day =(TextView)findViewById(R.id.dayOfWeek);
            Calendar calendar = Calendar.getInstance();
            setDay = calendar.get(Calendar.DAY_OF_WEEK);
            initialise(setDay);
    }

    @Override
    protected void onDestroy() {
        messDatabase.close();
        cursor.close();
        super.onDestroy();
    }


    public void initialise(Integer Day){
        SharedPreferences sharedPreferences = getSharedPreferences("Preference",MODE_PRIVATE);

        cursor = messDatabase.getMessData(sharedPreferences.getString("Mess","TheRoyalCafe"),Day.toString());
        if(cursor.moveToFirst()){
            String s1,s2,s3,s4;
            s1 = cursor.getString(0);
            s2 = cursor.getString(1);
            s3 = cursor.getString(2);
            s4 = cursor.getString(3);
            day.setText(ToString(Day));
            messtime1.setText(s1);
            messtime2.setText(s2);
            messtime3.setText(s3);
            messtime4.setText(s4);
        }
    }

    public String ToString(Integer integer){
        if(integer == 1){
            return "Sunday";
        }
        else if(integer == 2){
            return "Monday";
        }else if(integer == 3){
            return "Tuesday";
        }else if(integer == 4){
            return "Wednesday";
        }else if(integer == 5){
            return "Thursday";
        }else if(integer == 6){
            return "Friday";
        }else{
            return "Saturday";
        }


    }
    public void dayBefore(View view){
        if(setDay == 1)
            setDay = 7;
        else
            setDay = setDay-1;
        initialise(setDay);
    }
    public void nextDay(View view){

        if(setDay == 7)
            setDay = 1;
        else
            setDay = setDay+1;
        initialise(setDay);
    }

    public void changeMess(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.mess_menu, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String Mess;
                if(item.getItemId() == R.id.Mess1){
                    Mess = "TheRoyalCafe";
                }else {
                    Mess = "SamhitaHospitality";
                }
                SharedPreferences sharedPreferences = getSharedPreferences("Preference",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Mess", Mess);
                editor.apply();
                initialise(setDay);
                return true;

            }
        });
    }
}
