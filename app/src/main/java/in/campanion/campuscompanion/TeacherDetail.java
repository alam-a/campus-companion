package in.campanion.campuscompanion;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Color.rgb;

public class TeacherDetail extends AppCompatActivity {
    String hint;
    Cursor cursor;
    TeacherDatabase tdb;
    EditText editText;
    ListView listView;

    @Override
    protected void onDestroy() {
        cursor.close();tdb.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);
        Bundle extras = getIntent().getExtras();
        hint =  extras.getString("Branch");
        if(hint != null)
        Toast.makeText(this,hint,Toast.LENGTH_SHORT).show();
        listView = (ListView) findViewById(android.R.id.list);
        tdb = new TeacherDatabase(this);
        showLists("");
        editText = (EditText)findViewById(R.id.teacher_search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")){
                    showLists("");
                }
                else {
                    tdb.close();
                    showLists(s.toString());
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer integ = (Integer) position;
                Long idget = parent.getItemIdAtPosition(position);
                Toast.makeText(getApplicationContext(),integ.toString()+" "+ idget.toString(),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),AboutTeacher.class);
                i.putExtra("id",idget);
                startActivity(i);
            }
        });
    }
    public void showLists(String name){
        cursor = tdb.getEmployees(name);
        @SuppressWarnings("deprecation")
        ListAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[] {"Name"},
                new int[] {android.R.id.text1});
        listView.setAdapter(adapter);
    }
}
