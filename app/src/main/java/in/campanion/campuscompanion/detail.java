package in.campanion.campuscompanion;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class detail extends AppCompatActivity {
    TextView name_text,detail_text,website_text,phone_text,email_text;
    String _id,table;
    String nameSet,detailSet,websiteSet,phoneSet,emailSet;
    double longtitude,lattitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        name_text = (TextView)findViewById(R.id.dt_name);
        detail_text = (TextView)findViewById(R.id.dt_detail);
        website_text = (TextView)findViewById(R.id.dt_website);
        phone_text = (TextView)findViewById(R.id.dt_mobile);
        Bundle b = getIntent().getExtras();
        _id = b.getString("Id");
        table = b.getString("Table");
        EssentialDatabase ed = new EssentialDatabase(this);
        Cursor cursor = ed.getDetail(table,_id);
        if(cursor.moveToFirst()){
            nameSet = cursor.getString(1);
            detailSet = cursor.getString(3);
            longtitude = cursor.getDouble(4);
            lattitude = cursor.getDouble(5);
            phoneSet = cursor.getString(7);
            websiteSet = cursor.getString(9);
            setDetail();
        }else {
            nameSet="Name";
            detailSet = "Detail";
            longtitude = 77.241619;
            lattitude = 28.656389;
            phoneSet = "8603833882";
            websiteSet = "campanion.in";
            setDetail();
        }
//        t1.setText(b.getString("name"));
//        t2.setText(b.getString("detail"));
    }

    private void setDetail() {
        name_text.setText(nameSet);
        detail_text.setText(detailSet);
        phone_text.setText(phoneSet);
        website_text.setText(websiteSet);
    }

    public void takeMeThere(View view){

        Uri location = Uri.parse("geo:0,0?q="+longtitude+","+lattitude+"?z=21"+"("+nameSet+")");
        Intent i = new Intent(Intent.ACTION_VIEW,location);
        startActivity(i);
    }
}
