package in.campanion.campuscompanion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by aftab on 04-01-2017.
 */

public class MessDatabase extends SQLiteAssetHelper {
    private static final String DATABASE = "mess.db";
    public MessDatabase(Context context) {
        super(context, DATABASE, null, 6);
        setForcedUpgrade();
    }

    public Cursor getMessData(String Mess,String Day){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(Mess,new String[]{"Breakfast","Lunch","Snacks","Dinner"}," _id = ? ",new String[]{Day},null,null,null);

    }
}
