package ie.dit.giantbombapp.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ie.dit.giantbombapp.model.pojos.Promo;
import ie.dit.giantbombapp.model.pojos.Review;

/**
 * Created by Graham on 17-Nov-16.
 */

public class DatabaseManager {

    private static final String PROMO_TABLE 	    = "Promo";
    private static final String PROMO_ROWID 	    = "_id";
    private static final String PROMO_APIURL        = "api_url";
    private static final String PROMO_DATEADDED 	= "date_added";
    private static final String PROMO_DECK 	        = "deck";
    private static final String PROMO_IMAGE         = "thumbnail";
    private static final String PROMO_PROMOID	    = "promo_id";
    private static final String PROMO_SITELINK      = "site_link";
    private static final String PROMO_PROMOTITLE    = "promo_title";
    private static final String PROMO_RESTYPE       = "resource_type";
    private static final String PROMO_AUTHOR        = "author";
    private static final String PROMO_CREATE =
            "create table " + PROMO_TABLE + " (" +
                    PROMO_ROWID + " integer primary key autoincrement," +
                    PROMO_APIURL + " text," +
                    PROMO_DATEADDED + " text," +
                    PROMO_DECK + " text," +
                    PROMO_IMAGE + " text," +
                    PROMO_PROMOID + " integer," +
                    PROMO_SITELINK + " text," +
                    PROMO_PROMOTITLE + " text," +
                    PROMO_RESTYPE + " text," +
                    PROMO_AUTHOR + " text); ";
    private static final String PROMO_DROP = "drop table promo;";

    private static final String REVIEW_TABLE        = "Review";
    private static final String REVIEW_ROWID        = "_id";
    private static final String REVIEW_APIURL       = "api_url";
    private static final String REVIEW_DECK         = "deck";
    private static final String REVIEW_GAMENAME     = "game_name";
    private static final String REVIEW_GAMEID       = "game_id";
    private static final String REVIEW_PUBLISHDATE  = "publish_date";
    private static final String REVIEW_RELEASENAME  = "release_name";
    private static final String REVIEW_AUTHOR       = "author";
    private static final String REVIEW_SCORE        = "score";
    private static final String REVIEW_SITEURL      = "site_url";
    private static final String REVIEW_PLATFORMS    = "platforms";
    private static final String REVIEW_CREATE =
            "create table " + REVIEW_TABLE + " (" +
                    REVIEW_ROWID + " integer primary key autoincrement," +
                    REVIEW_APIURL + " text," +
                    REVIEW_DECK + " text," +
                    REVIEW_GAMENAME + " text," +
                    REVIEW_GAMEID + " integer," +
                    REVIEW_PUBLISHDATE + " text," +
                    REVIEW_RELEASENAME + " text," +
                    REVIEW_AUTHOR + " text," +
                    REVIEW_SCORE + " integer," +
                    REVIEW_SITEURL + " text," +
                    REVIEW_PLATFORMS + " text);";
    private static final String REVIEW_DROP = "drop table review;";

    private static final String DATABASE_NAME 	= "Giantbomb";
    private static final int DATABASE_VERSION 	= 7;
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

    public long insertPromo(Promo promo) throws SQLException
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PROMO_DATEADDED, promo.getDateAdded());
        initialValues.put(PROMO_APIURL, promo.getApiDetailUrl());
        initialValues.put(PROMO_DECK, promo.getDeck());
        initialValues.put(PROMO_IMAGE, promo.getImage().getThumbUrl());
        initialValues.put(PROMO_PROMOID, promo.getId());
        initialValues.put(PROMO_SITELINK, promo.getLink());
        initialValues.put(PROMO_PROMOTITLE, promo.getName());
        initialValues.put(PROMO_RESTYPE, promo.getResourceType());
        initialValues.put(PROMO_AUTHOR, promo.getUser());
        return db.insert(PROMO_TABLE, null, initialValues);
    }

    public boolean deletePromoByPromoId(int promoId) throws SQLException
    {
        //
        return db.delete(PROMO_TABLE, PROMO_PROMOID +
                "=" + promoId, null) > 0;
    }

    public boolean wipePromos()
    {
        return db.delete(PROMO_TABLE, "1", null) > 0;
    }

    public Cursor getAllPromos() throws SQLException
    {
        return db.query(PROMO_TABLE, new String[]
                        {
                                PROMO_ROWID,
                                PROMO_DATEADDED,
                                PROMO_DECK,
                                PROMO_IMAGE,
                                PROMO_PROMOID,
                                PROMO_PROMOTITLE,
                                PROMO_RESTYPE,
                                PROMO_AUTHOR
                        },
                null, null, null, null, null);
    }

    public Cursor getPromoByPromoId(int promoId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, PROMO_TABLE, new String[]
                                {
                                        PROMO_ROWID,
                                        PROMO_DATEADDED,
                                        PROMO_APIURL,
                                        PROMO_DECK,
                                        PROMO_IMAGE,
                                        PROMO_PROMOID,
                                        PROMO_SITELINK,
                                        PROMO_PROMOTITLE,
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
    public boolean updatePromoByPromoId(int promoId, Promo promo) throws SQLException
    {
        ContentValues args = new ContentValues();
        args.put(PROMO_DATEADDED, promo.getDateAdded());
        args.put(PROMO_APIURL, promo.getApiDetailUrl());
        args.put(PROMO_DECK, promo.getDeck());
        args.put(PROMO_IMAGE, promo.getImage().getThumbUrl());
        args.put(PROMO_PROMOID, promo.getId());
        args.put(PROMO_SITELINK, promo.getLink());
        args.put(PROMO_PROMOTITLE, promo.getName());
        args.put(PROMO_RESTYPE, promo.getResourceType());
        args.put(PROMO_AUTHOR, promo.getUser());
        return db.update(PROMO_TABLE, args,
                PROMO_PROMOID + "=" + promoId, null) > 0;
    }

    public long insertReview(Review review) throws SQLException
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(REVIEW_DECK, review.getDeck());
        initialValues.put(REVIEW_APIURL, review.getApiDetailUrl());
        initialValues.put(REVIEW_GAMENAME, review.getGame().getName());
        initialValues.put(REVIEW_GAMEID, review.getGame().getId());
        initialValues.put(REVIEW_PUBLISHDATE, review.getPublishDate());
        if(review.getRelease() != null)
            initialValues.put(REVIEW_RELEASENAME, review.getRelease().getName());
        initialValues.put(REVIEW_AUTHOR, review.getReviewer());
        initialValues.put(REVIEW_SCORE, review.getScore());
        initialValues.put(REVIEW_SITEURL, review.getSiteDetailUrl());
        initialValues.put(REVIEW_PLATFORMS, review.getPlatforms());
        return db.insert(REVIEW_TABLE, null, initialValues);
    }

    public boolean deleteReviewByGameId(int gameId) throws SQLException
    {
        //
        return db.delete(REVIEW_TABLE, REVIEW_GAMEID +
                "=" + gameId, null) > 0;
    }

    public boolean wipeReviews()
    {
        return db.delete(REVIEW_TABLE, "1", null) > 0;
    }

    public Cursor getAllReviews() throws SQLException
    {
        return db.query(REVIEW_TABLE, new String[]
                        {
                                REVIEW_ROWID,
                                REVIEW_APIURL,
                                REVIEW_DECK,
                                REVIEW_GAMENAME,
                                REVIEW_GAMEID,
                                REVIEW_PUBLISHDATE,
                                REVIEW_RELEASENAME,
                                REVIEW_AUTHOR,
                                REVIEW_SCORE,
                                REVIEW_SITEURL,
                                REVIEW_PLATFORMS
                        },
                null, null, null, null, null);
    }

    public Cursor getReviewByGameId(int gameId) throws SQLException
    {
        Cursor mCursor = db.query(true, REVIEW_TABLE, new String[]
                        {
                                REVIEW_ROWID,
                                REVIEW_APIURL,
                                REVIEW_DECK,
                                REVIEW_GAMENAME,
                                REVIEW_GAMEID,
                                REVIEW_PUBLISHDATE,
                                REVIEW_RELEASENAME,
                                REVIEW_AUTHOR,
                                REVIEW_SCORE,
                                REVIEW_SITEURL,
                                REVIEW_PLATFORMS
                        },
                REVIEW_GAMEID + "=" + gameId, null, null, null, null, null);

        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateReviewByGameId(int gameId, Review review) throws SQLException
    {
        ContentValues args = new ContentValues();
        args.put(REVIEW_DECK, review.getDeck());
        args.put(REVIEW_APIURL, review.getApiDetailUrl());
        args.put(REVIEW_GAMENAME, review.getGame().getName());
        args.put(REVIEW_GAMEID, review.getGame().getId());
        args.put(REVIEW_PUBLISHDATE, review.getPublishDate());
        args.put(REVIEW_RELEASENAME, review.getRelease().getName());
        args.put(REVIEW_AUTHOR, review.getReviewer());
        args.put(REVIEW_SCORE, review.getScore());
        args.put(REVIEW_SITEURL, review.getSiteDetailUrl());
        args.put(REVIEW_PLATFORMS, review.getPlatforms());
        return db.update(REVIEW_TABLE, args,
                REVIEW_GAMEID + "=" + gameId, null) > 0;
    }
}
