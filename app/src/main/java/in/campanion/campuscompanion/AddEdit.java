package in.campanion.campuscompanion;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class AddEdit extends AppCompatActivity {
    TextView t1,t2;
    LinearLayout f1,f2;
    EditText ed1,ed2,ed3,ed4,ep1,ep2,ep3;
    DatabaseHelper db;
    Button b1,b2;
    ScrollView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        t1 = (TextView)findViewById(R.id.add_code_Button);
        t2 = (TextView)findViewById(R.id.add_period_Button);
        f1 = (LinearLayout) findViewById(R.id.add_code_Part);
        f2 = (LinearLayout) findViewById(R.id.add_period_Part);
        f2.setVisibility(View.GONE);
        ed1 = (EditText)findViewById(R.id.add_ed_code);
        ed2 = (EditText)findViewById(R.id.add_ed_subject);
        ed3 = (EditText)findViewById(R.id.add_ed_teacher);
        ed4 = (EditText)findViewById(R.id.add_ed_room);
        ep1 = (EditText)findViewById(R.id.add_ep_code);
        ep2 = (EditText)findViewById(R.id.add_ep_day);
        ep3 = (EditText)findViewById(R.id.add_ep_period);
        b1 = (Button)findViewById(R.id.add_btn_submitCode);
        b2 = (Button)findViewById(R.id.add_btn_submitPeriod);
        home = (ScrollView) findViewById(R.id.add_edit_home);
        db = new DatabaseHelper(this);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f1.getVisibility() == View.GONE){
                    t1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.recycler_view_anim));

                    f2.setVisibility(View.GONE);
                    f1.setVisibility(View.VISIBLE);
                    t1.setBackgroundColor(Color.parseColor("#03A9F4"));
                    t2.setBackgroundColor(Color.WHITE);
                }         }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f2.getVisibility() == View.GONE){
                    t2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.recycler_view_anim));
                    f1.setVisibility(View.GONE);
                    f2.setVisibility(View.VISIBLE);
                    t2.setBackgroundColor(Color.parseColor("#03A9F4"));
                    t1.setBackgroundColor(Color.WHITE);
                }
            }
        });

        home.setOnTouchListener(new View.OnTouchListener() {
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
                        if (!(f1.getVisibility() == View.VISIBLE)){
                            f1.setVisibility(View.VISIBLE);
                            f2.setVisibility(View.GONE);
                            t1.setBackgroundColor(Color.parseColor("#03A9F4"));
                            t2.setBackgroundColor(Color.WHITE);
                        }
                    }else if(upX - downX < -400){
                        if(!(f2.getVisibility() ==  View.VISIBLE)){
                            f2.setVisibility(View.VISIBLE);
                            f1.setVisibility(View.GONE);
                            t2.setBackgroundColor(Color.parseColor("#03A9F4"));
                            t1.setBackgroundColor(Color.WHITE);
                        }
                    }
                    return true;
                }
                return false;
            }
        });
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
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    public void submitPeriod(View view){
        if(ep2.getText().toString().equals("")){
            Toast.makeText(this,"Enter data first",Toast.LENGTH_SHORT).show();
            return;
        }
        if(ep3.getText().toString().equals("")){
            Toast.makeText(this,"Enter data first",Toast.LENGTH_SHORT).show();
            return;
        }

        Integer i1 = Integer.parseInt(ep2.getText().toString());
        Integer i2 = Integer.parseInt(ep3.getText().toString());

        if(i1 > 0 && i1 < 6 && i2 > 0 && i2 < 11){

            if(!ep1.getText().toString().equals("") && !ep2.getText().toString().equals("") && !ep3.getText().toString().equals("")){

                if(db.checkPeriodAlreadyPresent(ep2.getText().toString(),ep3.getText().toString())){
                    AlertDialog.Builder alert = new AlertDialog.Builder(AddEdit.this);
                    alert.setMessage("A subject exists in the entered day order and period combination. Do you want to update the details?");
                    alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(db.checkCodeAlreadyPresent(ep1.getText().toString())){
                                if(db.updatePeriod(ep1.getText().toString(),ep2.getText().toString(),ep3.getText().toString()))
                                    Toast.makeText(getApplicationContext(),"Data Updated",Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getApplicationContext(),"Data Update Failed",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Wrong Code, Data insertion failed",Toast.LENGTH_SHORT).show();
                            }
                            dialog.cancel();
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ep1.setText("");
                            ep2.setText("");
                            ep3.setText("");
                            dialog.cancel();
                        }
                    });
                    AlertDialog makeAlert = alert.create();
                    makeAlert.show();
                }
                else {
                    if(db.checkCodeAlreadyPresent(ep1.getText().toString())){
                        boolean result = db.createPeriod(ep1.getText().toString(),ep2.getText().toString(),ep3.getText().toString());
                        if(result)
                            Toast.makeText(this,"Data entered successfully",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(this,"Data insertion failed",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(this,"Wrong Code. Failed to eneter data.",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this,"All field must be filled.",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"Wrong value for Day Order or Period. See Range.",Toast.LENGTH_SHORT).show();
        }
    }
    public void seeCodeData(View view){
        Cursor c = db.getCodeData();
        if(c.getCount() == 0){
            Toast.makeText(this,"Empty",Toast.LENGTH_SHORT).show();
        }
        else {
            StringBuffer stringBuffer = new StringBuffer();
            while (c.moveToNext()){
                stringBuffer.append("Code : "+c.getString(1)+"\n");
                stringBuffer.append("Subject : "+c.getString(2)+"\n");
                stringBuffer.append("Teacher : "+c.getString(3)+"\n");
                stringBuffer.append("Room : "+c.getString(4)+"\n\n");
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Subject Code Details ");
            builder.setMessage(stringBuffer);
            builder.show();
            c.close();
        }
    }

    public void seePeriodData(View view){
        Cursor c = db.getPeriodData();
        if(c.getCount() == 0){
            Toast.makeText(this,"Empty",Toast.LENGTH_SHORT).show();
        }
        else {
            StringBuffer stringBuffer = new StringBuffer();
            while (c.moveToNext()){
                stringBuffer.append("Code : "+c.getString(1)+"\n");
                stringBuffer.append("Day : "+c.getString(2)+"\n");
                stringBuffer.append("Period : "+c.getString(3)+"\n\n");
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Subject Code Details ");
            builder.setMessage(stringBuffer);
            builder.show();
            c.close();
        }
    }
    public void deleteCodeData(View view){
        if(ed1.getText().toString().equals("")){
            Toast.makeText(this,"Enter Code first",Toast.LENGTH_SHORT).show();
        }
        else{
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Go ahead with deletion?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(db.checkCodeAlreadyPresent(ed1.getText().toString())){
                        int affected = db.deleteCode(ed1.getText().toString());
                        if(affected > 0)
                            Toast.makeText(getApplicationContext(),affected + " Rows deleted",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(),"Deletion Failed",Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getApplicationContext(),"Wrong Code, Deletion failed.",Toast.LENGTH_SHORT).show();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.create().show();
        }

    }

    public void tutorial(View view) {
            startActivity(new Intent(this,AddEditHow.class));
    }

    public void deletePeriodData(View view) {
        if(!ep2.getText().toString().equals("") && !ep3.getText().toString().equals("")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Do you want to go ahead with deletion?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(db.deletePeriodData(ep2.getText().toString(),ep3.getText().toString()) > 0){
                        Toast.makeText(getApplicationContext(),"Period deleted",Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getApplicationContext(),"Deletion Failed",Toast.LENGTH_SHORT).show();
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.setCancelable(true);
            alertDialog.create().show();
        }else if(!ep2.getText().toString().equals("")){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Only Day Order value is inserted. Do you want to delete all period in a specific Day Order? Press Continue to delete all.");
            alertDialog.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),db.deleteCompleteDay(ep2.getText().toString())+" columns deleted",Toast.LENGTH_SHORT).show();
                }
            });
            alertDialog.setPositiveButton("Leave", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.setCancelable(true);
            alertDialog.create().show();
        }
    }

    public void submitCode(View view) {
        boolean res;
        res = db.checkCodeAlreadyPresent(ed1.getText().toString());
        if(res){
            AlertDialog.Builder alert = new AlertDialog.Builder(AddEdit.this);
            alert.setMessage("Code already present. Do you want to update the code?");
            alert.setCancelable(true);
            alert.setPositiveButton("Update Data", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(!ed1.getText().toString().equals("") && !ed2.getText().toString().equals("") && !ed3.getText().toString().equals("") && !ed4.getText().toString().equals("")){

                        boolean result = db.updateCode(ed1.getText().toString(),ed2.getText().toString(),ed3.getText().toString(),ed4.getText().toString());
                        if(result)
                            Toast.makeText(getApplicationContext(),"Code data updated",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(),"Failed to update",Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getApplicationContext(),"Enter All Details first.",Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ed1.setText("");
                    ed2.setText("");
                    ed3.setText("");
                    ed4.setText("");
                    dialog.cancel();
                }
            });
            AlertDialog makeAlert = alert.create();
            makeAlert.show();
        }
        else {
            if(!ed1.getText().toString().equals("") && !ed2.getText().toString().equals("") && !ed3.getText().toString().equals("") && !ed4.getText().toString().equals("")){

                boolean resi = db.createCode(ed1.getText().toString(),ed2.getText().toString(),ed3.getText().toString(),ed4.getText().toString());
                if(!resi){
                    Toast.makeText(getApplicationContext(),"Failed to save data. Please try again.",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Data successfully saved.",Toast.LENGTH_SHORT).show();
                }
            }
            else
                Toast.makeText(getApplicationContext(),"Enter all the details",Toast.LENGTH_SHORT).show();
        }
    }
}
