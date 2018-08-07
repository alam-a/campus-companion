package in.campanion.campuscompanion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

/**
 * Created by aftab on 19-12-2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "note.db";
    public static final String TABLE_CODE = "codeTable";
    public static final String TABLE_PERIOD = "period_table";
    public static final String COL_1_CODE = "_id";
    public static final String COL_2_CODE = "CODE";
    public static final String COL_3_CODE = "SUBJECT";
    public static final String COL_4_CODE = "TEACHER";
    public static final String COL_5_CODE = "ROOM";
    public static final String COL_1_PERIOD = "_id";
    public static final String COL_2_PERIOD = "CODE";
    public static final String COL_3_PERIOD = "DAY";
    public static final String COL_4_PERIOD = "PERIOD";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_CODE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,CODE TEXT,SUBJECT TEXT,TEACHER TEXT,ROOM TEXT)");
        db.execSQL("create table "+TABLE_PERIOD + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,CODE TEXT,DAY TEXT,PERIOD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CODE);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PERIOD);
        onCreate(db);
    }
    public boolean createCode(String code,String name,String t_name,String r_no){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_CODE,code);
        contentValues.put(COL_3_CODE,name);
        contentValues.put(COL_4_CODE,t_name);
        contentValues.put(COL_5_CODE,r_no);
        long res = db.insert(TABLE_CODE,null,contentValues);
        if(res == -1){
            return false;
        }else {
            return true;
        }
    }

    public boolean createPeriod(String code,String day,String period){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2_PERIOD,code);
            contentValues.put(COL_3_PERIOD,day);
            contentValues.put(COL_4_PERIOD,period);
            long r = db.insert(TABLE_PERIOD,null,contentValues);
            if(r == -1){
                return false;
            }else {
                return true;
            }
    }
    public Cursor getCodeData(){
        SQLiteDatabase db1 = getReadableDatabase();
        Cursor c = db1.rawQuery("SELECT * FROM "+TABLE_CODE,null);
        return c;
    }
    public Cursor getPeriodData(){
        SQLiteDatabase db1 = getReadableDatabase();
        Cursor c = db1.rawQuery("SELECT * FROM "+TABLE_PERIOD,null);
        return c;
    }

    public boolean checkCodeAlreadyPresent(String s){
        SQLiteDatabase db1 = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String sqlTables = TABLE_CODE;
        qb.setTables(sqlTables);
        Cursor c = qb.query(db1,null,"CODE = ?",new String[]{s},null,null,null);
        if(c.getCount() != 0){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean updateCode(String code,String name,String t_name,String r_no){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_CODE,code);
        contentValues.put(COL_3_CODE,name);
        contentValues.put(COL_4_CODE,t_name);
        contentValues.put(COL_5_CODE,r_no);

        int result = db.update(TABLE_CODE,contentValues,"CODE = ?",new String[]{code});
        if(result < 1){
            return false;
        }
        else {
            return true;
        }
    }
    public boolean updatePeriod(String code,String day,String period){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_PERIOD,code);
        contentValues.put(COL_3_PERIOD,day);
        contentValues.put(COL_4_PERIOD,period);


        if(db.update(TABLE_PERIOD,contentValues,"DAY = ? AND PERIOD = ?",new String[]{day,period}) > 0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkPeriodAlreadyPresent(String s1,String s2){
        SQLiteDatabase db1 = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String sqlTables = TABLE_PERIOD;
        qb.setTables(sqlTables);
        Cursor c = qb.query(db1,null,"DAY = ? AND PERIOD = ?",new String[]{s1,s2},null,null,null);
        if(c.getCount() != 0){
            return true;
        }
        else{
            return false;
        }
    }
    public int deleteCode(String s){
        SQLiteDatabase db = getWritableDatabase();
        int affectedCode = db.delete(TABLE_CODE,"CODE = ?",new String[]{s});
        int affectedPeriod = db.delete(TABLE_PERIOD,"CODE = ?",new String[]{s});
        return affectedCode+affectedPeriod;
    }

    public Cursor getTimeTablePeriod(String s){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c;
//        c = db.query(TABLE_PERIOD,null,"DAY = ? ",new String[]{s},null,null,null);

        c = db.rawQuery("select period_table.PERIOD, codeTable.SUBJECT, codeTable.TEACHER, codeTable.ROOM from period_table, codeTable where period_table.CODE = codeTable.CODE and DAY = ?",new String[]{s});
        return c;
    }

    public int deletePeriodData(String s1, String s2){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_PERIOD,"DAY = ? AND PERIOD = ?",new String[]{s1,s2});
    }

    public int deleteCompleteDay(String s){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_PERIOD,"DAY = ?",new String[]{s});
    }


    public Cursor getTimeTableDetail(String s){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(TABLE_CODE,null,"CODE = ? ",new String[]{s},null,null,null);
        return c;
    }

    public Cursor checkDataIsPresent(){
        SQLiteDatabase db1 = getReadableDatabase();
        Cursor c = db1.rawQuery("SELECT _id FROM "+TABLE_PERIOD,null);
        return c;
    }
}
