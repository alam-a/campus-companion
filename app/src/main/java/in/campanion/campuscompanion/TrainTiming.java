package in.campanion.campuscompanion;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class TrainTiming extends AppCompatActivity {
    TextView sourceDest,mon_sat,sun;
    String mon_sat_pot_par;
    String mon_sat_par_pot;
    String sun_pot_par;
    String sun_par_pot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_timing);
        sourceDest = (TextView)findViewById(R.id.train_timing_source_dest_text);
        mon_sat = (TextView)findViewById(R.id.train_timing_mon_sat);
        sun = (TextView)findViewById(R.id.train_timing_sun);
        mon_sat_pot_par = getString(R.string.mon_sat_pot_par);
        mon_sat_par_pot = getString(R.string.mon_sat_par_pot);
        sun_pot_par = getString(R.string.sun_pot_par);
        sun_par_pot = getString(R.string.sun_par_pot);
        mon_sat.setText(mon_sat_pot_par);
        sun.setText(sun_pot_par);
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

    public void changeSourceDest(View view) {
        if(sourceDest.getText().toString().equals("Potheri to Chennai Park")){
            sourceDest.setText("Chennai Park to Potheri");
            mon_sat.setText(mon_sat_par_pot);
            sun.setText(sun_par_pot);
        }else {
            sourceDest.setText("Potheri to Chennai Park");
            mon_sat.setText(mon_sat_pot_par);
            sun.setText(sun_pot_par);
        }
    }
}
