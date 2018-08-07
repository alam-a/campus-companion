package in.campanion.campuscompanion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
/**
 * Created by aftab on 17-12-2016.
 */

public class TeacherDatabase  extends SQLiteAssetHelper{
    private static final String DATABASE_NAME = "test.db";
    private static final String TABLE_NAME = "name";
    private static final int DATABASE_VERSION = 2;

    public TeacherDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

    public Cursor getEmployees(String s) {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String [] sqlSelect = {"_id", "Name"};
        String sqlTables = TABLE_NAME;
        String s2 = '%'+s+'%';
        qb.setTables(sqlTables);
        Cursor c = qb.query(db, sqlSelect,"Name LIKE ?",new String[]{s2},
                null, null,"Name");

        c.moveToFirst();
        return c;

    }

    public Cursor getDetail(String s){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("name",null,"_id = ?",new String[]{s},null,null,null);

        return c;
    }


}
