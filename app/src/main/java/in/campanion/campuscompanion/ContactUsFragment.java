package in.campanion.campuscompanion;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by aftab on 13-12-2016.
 */

public class ContactUsFragment extends Fragment {
    View myView;
    Button send;
    EditText text,subject;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.contact_us_fragment,container, false);

        send = (Button) myView.findViewById(R.id.contactFragmentSend);
        text = (EditText) myView.findViewById(R.id.contactFragmentEdit);
        subject = (EditText) myView.findViewById(R.id.contactFragmentEditSubject);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"aftab.alam991@live.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT,subject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT,text.getText().toString());
                if (intent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivity(Intent.createChooser(intent,"Semd Email"));
                }
            }
        });
        return myView;    }

}
