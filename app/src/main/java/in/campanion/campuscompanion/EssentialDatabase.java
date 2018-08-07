package in.campanion.campuscompanion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by aftab on 17-12-2016.
 */

public class EssentialDatabase extends SQLiteAssetHelper{
    private static final String DATABASE_NAME = "detail.db";
    private static final String TABLE1 = "Shopping";
    private static final String TABLE2 = "Restaurant";
    private static final String TABLE3= "ATM";
    private static final String TABLE4= "DrugStore";
    private static final int DATABASE_VERSION = 9;

    public EssentialDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }

    public Cursor getForList(String s,String table) {

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String [] sqlSelect = {"_id","Name","Brief","Locality"};
        String s2 = '%'+s+'%';
        qb.setTables(table);
        Cursor c = qb.query(db, sqlSelect,"Name LIKE ? OR Locality LIKE ?",new String[]{s2,s2},
                null, null,"Name");

        c.moveToFirst();
        return c;

    }

    public Cursor getDetail(String table,String _id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(table,null,"_id = ?",new String[]{_id},null,null,null);
        return c;
    }


}
