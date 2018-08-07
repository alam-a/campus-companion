package in.campanion.campuscompanion;

import android.database.Cursor;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AboutTeacher extends AppCompatActivity {
    TeacherDatabase db;
    TextView t1,t2,t3,t4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_teacher);
        Bundle bundle = getIntent().getExtras();
        Long id = bundle.getLong("id");
        db = new TeacherDatabase(this);
        Cursor cursor = db.getDetail(id.toString());
        t1 = (TextView)findViewById(R.id.ab_Id);
        t2 = (TextView)findViewById(R.id.ab_name);
        t3 = (TextView)findViewById(R.id.ab_email);
        t4 = (TextView)findViewById(R.id.ab_address);
        if(cursor.moveToFirst()){
            String s1,s2,s3,s4;
            s1 = cursor.getString(0);
            s2 = cursor.getString(1);
            s3 = cursor.getString(2);
            s4 = cursor.getString(3);
            t1.setText(s1);
            t2.setText(s2);
            t3.setText(s3);
            t4.setText(s4);
        }
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
}
