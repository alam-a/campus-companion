package in.campanion.campuscompanion;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.util.LogWriter;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int checkBackStack;
    View t;
    SharedPreferences sharedPreferences;
    Button d1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        t = (View) findViewById(R.id.welcome);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        checkBackStack = 0;
        sharedPreferences = getSharedPreferences("Preference", MODE_PRIVATE);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            t.setVisibility(View.GONE);
        }
        reportFullyDrawn();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (checkBackStack == 0 && getFragmentManager().getBackStackEntryCount() == 0) {
            checkBackStack++;
            Toast.makeText(this, "Press back button again to exit", Toast.LENGTH_SHORT).show();
        } else if (getFragmentManager().getBackStackEntryCount() == 1) {
            t.setVisibility(View.VISIBLE);
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.nav_about_us) {
            checkBackStack = 0;
            t.setVisibility(View.GONE);
            getFragmentManager().beginTransaction().replace(R.id.content_main, new AboutUsFragment()).addToBackStack(null).commit();
        } else if(id == R.id.nav_contact_us){
            checkBackStack = 0;
            t.setVisibility(View.GONE);
            getFragmentManager().beginTransaction().replace(R.id.content_main, new ContactUsFragment()).addToBackStack(null).commit();
        }else if(id == R.id.nav_review_app){
            checkBackStack = 0;
            t.setVisibility(View.GONE);
            getFragmentManager().beginTransaction().replace(R.id.content_main, new ReviewAppFragment()).addToBackStack(null).commit();
        }else if(id == R.id.essential_fragment){
             checkBackStack = 0;
             t.setVisibility(View.GONE);
             getFragmentManager().beginTransaction().replace(R.id.content_main, new Essentials()).addToBackStack(null).commit();
         }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Mess", Mess);
                editor.apply();
                Toast.makeText(getApplicationContext(), item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, MessDetail.class));
                return true;
            }
        });
    }

    public void dayOrder(View view) {
        Intent i = new Intent(this, ShowTable.class);
        d1 = (Button)view;
        i.putExtra("dayInt", Integer.parseInt(d1.getText().toString()));
       // Toast.makeText(this,d1.getText(),Toast.LENGTH_SHORT).show();
        startActivity(i);
    }


    public void messMenu(final View view) {
        if (sharedPreferences.getString("Mess", null) == null) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setTitle("Default Mess not selected.");
            alertDialog.setMessage("Do you want to select a Mess and set it as your default.\nRemember you can always change this through a button provided below menu.");
            alertDialog.setPositiveButton("Select mess", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    changeMess(view);
                    dialog.cancel();
                }
            });
            alertDialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.create().show();
        } else
            startActivity(new Intent(MainActivity.this, MessDetail.class));
    }


    public void essential(View view) {
        Intent i = new Intent(this, EssentialList.class);
        Button button = (Button)view;
        i.putExtra("Table", button.getText());
        Toast.makeText(this,button.getText(),Toast.LENGTH_SHORT).show();
        startActivity(i);
    }




    public void trainTiming(View view) {
        startActivity(new Intent(MainActivity.this, TrainTiming.class));
    }

    public void terms_of_use(MenuItem item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(true);
        alert.setTitle("Terms Of Use");
        alert.setMessage(getResources().getString(R.string.terms_of_use));
        alert.show();
    }


}
