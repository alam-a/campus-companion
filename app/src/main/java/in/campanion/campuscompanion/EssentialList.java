package in.campanion.campuscompanion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.KeyEvent.getMaxKeyCode;

public class EssentialList extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<SetData> arrayList = new ArrayList<>();
    Cursor cursor;
    String Table;
    EditText searchBar;
    EssentialDatabase ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essential_list);
        Bundle bundle;
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);

        if(getIntent().getExtras() == null){
            Table = sharedPreferences.getString("Table","Shopping");
        }else {
            bundle = getIntent().getExtras();
            Table = bundle.getString("Table",null);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Table",Table);
            editor.apply();
        }
        recyclerView = (RecyclerView)findViewById(R.id.essential_list_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ed = new EssentialDatabase(this);
        //cursor = ed.getForList("",Table);
        new GetAndSet().execute("",Table);
//        cursor.moveToFirst();
//        do{
//            SetData setData = new SetData(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
//            arrayList.add(setData);
//        }while (cursor.moveToNext());
//        adapter = new EssentialAdapter(arrayList);
//        recyclerView.setAdapter(adapter);
        searchBar = (EditText)findViewById(R.id.essential_list_search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dataSetChanged(searchBar.getText().toString());
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(),new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(),e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)){
                    int position = rv.getChildAdapterPosition(child);
                    int s = -1;
                    if(cursor.moveToFirst()){
                        cursor.move(position);
                        s = cursor.getInt(0);
                    }
                    long pos = rv.getChildItemId(child);
                    Integer ps = (Integer) s;
                    if(pos == -1){

                        child.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.recycler_view_anim));
                        Toast.makeText(getApplicationContext(),ps.toString(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),detail.class);
                        intent.putExtra("Table",Table);
                        intent.putExtra("Id",ps.toString());
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(),"No Stable Ids",Toast.LENGTH_SHORT).show();
                    }
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void dataSetChanged(String s){
            cursor = ed.getForList(s,Table);
            if(cursor.moveToFirst()){
                arrayList.clear();
                cursor.moveToFirst();
                do{
                    SetData setData = new SetData(cursor.getString(0),cursor.getString(1),cursor.getString(3));
                    arrayList.add(setData);
                }while (cursor.moveToNext());
                adapter = new EssentialAdapter(arrayList);
                recyclerView.swapAdapter(adapter,false);
            }else {
                recyclerView.swapAdapter(null,false);
            }
        }

    @Override
    protected void onDestroy() {
        ed.close();
        //cursor.close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(!searchBar.getText().toString().equals("")){
            searchBar.setText("");
            dataSetChanged("");
        }else
        super.onBackPressed();
    }

    public class GetAndSet extends AsyncTask<String,Void,Cursor>{

        @Override
        protected Cursor doInBackground(String... params) {
            cursor = ed.getForList(params[0],Table);
            if(cursor.moveToFirst())
            return cursor;
            else
                return null;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if(cursor.moveToFirst()){
                do{
                    SetData setData = new SetData(cursor.getString(0),cursor.getString(1),cursor.getString(3));
                    arrayList.add(setData);
                }while (cursor.moveToNext());
                adapter = new EssentialAdapter(arrayList);
                recyclerView.setAdapter(adapter);
            }
        }
    }
}
