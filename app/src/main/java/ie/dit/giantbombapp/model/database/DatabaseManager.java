package ie.dit.giantbombapp.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Graham on 17-Nov-16.
 */

public class DatabaseManager {

    private static final String PROMO_TABLE 	    = "Promo";
    private static final String PROMO_ROWID 	    = "_id";
    private static final String PROMO_APIURL        = "api_url";
    private static final String PROMO_DATEADDED 	= "date_added";
    private static final String PROMO_DECK 	        = "deck";
    private static final String PROMO_PROMOID	    = "promo_id";
    private static final String PROMO_SITELINK      = "site_link";
    private static final String PROMO_PROMONAME     = "promo_name";
    private static final String PROMO_RESTYPE       = "resource_type";
    private static final String PROMO_AUTHOR        = "author";
    private static final String PROMO_CREATE =
            "create table " + PROMO_TABLE + " (" +
                    PROMO_ROWID + " integer primary key autoincrement," +
                    PROMO_APIURL + " text," +
                    PROMO_DATEADDED + " text," +
                    PROMO_DECK + " text," +
                    PROMO_PROMOID + " integer," +
                    PROMO_SITELINK + " text," +
                    PROMO_PROMONAME + " text," +
                    PROMO_RESTYPE + " text," +
                    PROMO_AUTHOR + " text); ";
    private static final String PROMO_DROP = "drop table promo;";

    private static final String REVIEW_TABLE        = "Review";
    private static final String REVIEW_ROWID        = "_id";
    private static final String REVIEW_DECK         = "deck";
    private static final String REVIEW_GAMENAME     = "game_name";
    private static final String REVIEW_PUBLISHDATE  = "publish_date";
    private static final String REVIEW_RELEASENAME  = "release_date";
    private static final String REVIEW_AUTHOR       = "author";
    private static final String REVIEW_SCORE        = "score";
    private static final String REVIEW_PLATFORMS    = "platforms";
    private static final String REVIEW_CREATE =
            "create table " + REVIEW_TABLE + " (" +
                    REVIEW_ROWID + " integer primary key autoincrement," +
                    REVIEW_DECK + " text," +
                    REVIEW_GAMENAME + " text," +
                    REVIEW_PUBLISHDATE + " text," +
                    REVIEW_RELEASENAME + " text," +
                    REVIEW_AUTHOR + " text," +
                    REVIEW_SCORE + " integer," +
                    REVIEW_PLATFORMS + " text);";
    private static final String REVIEW_DROP = "drop table review;";

    private static final String DATABASE_NAME 	= "Giantbomb";
    private static final int DATABASE_VERSION 	= 4;
    private static final String DATABASE_CREATE =
            PROMO_CREATE + REVIEW_CREATE;
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    //
    public DatabaseManager(Context ctx)
    {
        //
        this.context 	= ctx;
        DBHelper 		= new DatabaseHelper(context);
    }


    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        //
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        //
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(PROMO_CREATE);
            db.execSQL(REVIEW_CREATE);
        }

        @Override
        //
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            db.execSQL(PROMO_DROP);
            db.execSQL(REVIEW_DROP);
            db.execSQL(PROMO_CREATE);
            db.execSQL(REVIEW_CREATE);
        }
    }   //

    public DatabaseManager open() throws SQLException
    {
        db     = DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        DBHelper.close();
    }

    public long insertPromo(String dateAdded, String apiUrl, String deck, int promoId, String siteLink, String promoName, String resType, String author) throws SQLException
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PROMO_DATEADDED, dateAdded);
        initialValues.put(PROMO_APIURL, apiUrl);
        initialValues.put(PROMO_DECK, deck);
        initialValues.put(PROMO_PROMOID, promoId);
        initialValues.put(PROMO_SITELINK, siteLink);
        initialValues.put(PROMO_PROMONAME, promoName);
        initialValues.put(PROMO_RESTYPE, resType);
        initialValues.put(PROMO_AUTHOR, author);
        return db.insert(PROMO_TABLE, null, initialValues);
    }

    public boolean deletePromo(long rowId) throws SQLException
    {
        //
        return db.delete(PROMO_TABLE, PROMO_ROWID +
                "=" + rowId, null) > 0;
    }

    public Cursor getAllPromos() throws SQLException
    {
        return db.query(PROMO_TABLE, new String[]
                        {
                                PROMO_DATEADDED,
                                PROMO_DECK,
                                PROMO_PROMOID,
                                PROMO_PROMONAME,
                                PROMO_RESTYPE,
                                PROMO_AUTHOR
                        },
                null, null, null, null, null);
    }

    public Cursor getPromoByRowId(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, PROMO_TABLE, new String[]
                                {
                                        PROMO_DATEADDED,
                                        PROMO_APIURL,
                                        PROMO_DECK,
                                        PROMO_PROMOID,
                                        PROMO_SITELINK,
                                        PROMO_PROMONAME,
                                        PROMO_RESTYPE,
                                        PROMO_AUTHOR
                                },
                        PROMO_ROWID + "=" + rowId,  null, null, null, null, null);

        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getPromoByPromoId(int promoId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, PROMO_TABLE, new String[]
                                {
                                        PROMO_DATEADDED,
                                        PROMO_APIURL,
                                        PROMO_DECK,
                                        PROMO_PROMOID,
                                        PROMO_SITELINK,
                                        PROMO_PROMONAME,
                                        PROMO_RESTYPE,
                                        PROMO_AUTHOR
                                },
                        PROMO_PROMOID + "=" + promoId,  null, null, null, null, null);

        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //
    public boolean updatePromo(long rowId, String dateAdded, String apiUrl, String deck, int promoId, String siteLink, String promoName, String resType, String author) throws SQLException
    {
        ContentValues args = new ContentValues();
        args.put(PROMO_DATEADDED, dateAdded);
        args.put(PROMO_APIURL, apiUrl);
        args.put(PROMO_DECK, deck);
        args.put(PROMO_PROMOID, promoId);
        args.put(PROMO_SITELINK, siteLink);
        args.put(PROMO_PROMONAME, promoName);
        args.put(PROMO_RESTYPE, resType);
        args.put(PROMO_AUTHOR, author);
        return db.update(PROMO_TABLE, args,
                PROMO_ROWID + "=" + rowId, null) > 0;
    }
}
