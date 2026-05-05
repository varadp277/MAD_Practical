package com.example.turfbooking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TurfBooking.db";
    private static final int DATABASE_VERSION = 6;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_TURFS = "turfs";
    private static final String TABLE_BOOKINGS = "bookings";

    // Users Table Columns
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role"; // "admin" or "user"
    private static final String COLUMN_FAV_SPORT = "favorite_sport";
    private static final String COLUMN_SKILL_LEVEL = "skill_level";

    // Reviews Table Name
    private static final String TABLE_REVIEWS = "reviews";
    // Reviews Table Columns
    private static final String COLUMN_REVIEW_ID = "id";
    private static final String COLUMN_R_USER_ID = "user_id";
    private static final String COLUMN_R_TURF_ID = "turf_id";
    private static final String COLUMN_R_RATING = "rating";
    private static final String COLUMN_R_TEXT = "review_text";

    // Turfs Table Columns
    private static final String COLUMN_TURF_ID = "id";
    private static final String COLUMN_TURF_NAME = "name";
    private static final String COLUMN_TURF_LOCATION = "location";
    private static final String COLUMN_TURF_PRICE = "price_per_hour";
    private static final String COLUMN_TURF_IMAGE = "image_resource_name";
    private static final String COLUMN_TURF_CATEGORY = "category";

    // Bookings Table Columns
    private static final String COLUMN_BOOKING_ID = "id";
    private static final String COLUMN_B_USER_ID = "user_id";
    private static final String COLUMN_B_TURF_ID = "turf_id";
    private static final String COLUMN_BOOKING_DATE = "booking_date";
    private static final String COLUMN_BOOKING_SLOT = "time_slot";
    private static final String COLUMN_BOOKING_STATUS = "status"; // "confirmed", "cancelled"

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_ROLE + " TEXT,"
                + COLUMN_FAV_SPORT + " TEXT,"
                + COLUMN_SKILL_LEVEL + " TEXT" + ")";
        db.execSQL(createUsersTable);

        // Create Turfs Table
        String createTurfsTable = "CREATE TABLE " + TABLE_TURFS + "("
                + COLUMN_TURF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TURF_NAME + " TEXT,"
                + COLUMN_TURF_LOCATION + " TEXT,"
                + COLUMN_TURF_PRICE + " REAL,"
                + COLUMN_TURF_IMAGE + " TEXT,"
                + COLUMN_TURF_CATEGORY + " TEXT" + ")";
        db.execSQL(createTurfsTable);

        // Create Bookings Table
        String createBookingsTable = "CREATE TABLE " + TABLE_BOOKINGS + "("
                + COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_B_USER_ID + " INTEGER,"
                + COLUMN_B_TURF_ID + " INTEGER,"
                + COLUMN_BOOKING_DATE + " TEXT,"
                + COLUMN_BOOKING_SLOT + " TEXT,"
                + COLUMN_BOOKING_STATUS + " TEXT" + ")";
        db.execSQL(createBookingsTable);

        // Create Reviews Table
        String createReviewsTable = "CREATE TABLE " + TABLE_REVIEWS + "("
                + COLUMN_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_R_USER_ID + " INTEGER,"
                + COLUMN_R_TURF_ID + " INTEGER,"
                + COLUMN_R_RATING + " REAL,"
                + COLUMN_R_TEXT + " TEXT" + ")";
        db.execSQL(createReviewsTable);
        
        // Insert default admin
        db.execSQL("INSERT INTO " + TABLE_USERS + " (" + COLUMN_USERNAME + ", " + COLUMN_PASSWORD + ", " + COLUMN_ROLE + ") VALUES ('admin', 'admin123', 'admin')");
        
        // Insert sample turfs with categories for Football, Cricket, Tennis
        db.execSQL("INSERT INTO " + TABLE_TURFS + " (" + COLUMN_TURF_NAME + ", " + COLUMN_TURF_LOCATION + ", " + COLUMN_TURF_PRICE + ", " + COLUMN_TURF_IMAGE + ", " + COLUMN_TURF_CATEGORY + ") VALUES ('Greenfield Arena', 'Downtown', 50.0, 'turf_placeholder', 'Football')");
        db.execSQL("INSERT INTO " + TABLE_TURFS + " (" + COLUMN_TURF_NAME + ", " + COLUMN_TURF_LOCATION + ", " + COLUMN_TURF_PRICE + ", " + COLUMN_TURF_IMAGE + ", " + COLUMN_TURF_CATEGORY + ") VALUES ('Strikers Hub', 'Westside', 65.0, 'turf_2', 'Football')");
        db.execSQL("INSERT INTO " + TABLE_TURFS + " (" + COLUMN_TURF_NAME + ", " + COLUMN_TURF_LOCATION + ", " + COLUMN_TURF_PRICE + ", " + COLUMN_TURF_IMAGE + ", " + COLUMN_TURF_CATEGORY + ") VALUES ('Neon Turf 5v5', 'North Stadium', 40.0, 'turf_3', 'Football')");
        db.execSQL("INSERT INTO " + TABLE_TURFS + " (" + COLUMN_TURF_NAME + ", " + COLUMN_TURF_LOCATION + ", " + COLUMN_TURF_PRICE + ", " + COLUMN_TURF_IMAGE + ", " + COLUMN_TURF_CATEGORY + ") VALUES ('Blue Sky Pitch', 'East End Park', 45.0, 'turf_4', 'Cricket')");
        db.execSQL("INSERT INTO " + TABLE_TURFS + " (" + COLUMN_TURF_NAME + ", " + COLUMN_TURF_LOCATION + ", " + COLUMN_TURF_PRICE + ", " + COLUMN_TURF_IMAGE + ", " + COLUMN_TURF_CATEGORY + ") VALUES ('City View Rooftop', 'Financial District', 80.0, 'turf_5', 'Tennis')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 5) {
            db.execSQL("ALTER TABLE " + TABLE_TURFS + " ADD COLUMN " + COLUMN_TURF_CATEGORY + " TEXT DEFAULT 'Football'");
            db.execSQL("UPDATE " + TABLE_TURFS + " SET " + COLUMN_TURF_CATEGORY + "='Cricket' WHERE id=4");
            db.execSQL("UPDATE " + TABLE_TURFS + " SET " + COLUMN_TURF_CATEGORY + "='Tennis' WHERE id=5");
        } else {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TURFS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
            onCreate(db);
        }
    }

    // --- User Operations ---

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null,
                COLUMN_USER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)));
            user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)));
            user.setRole(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE)));
            if (cursor.getColumnIndex(COLUMN_FAV_SPORT) != -1) {
                user.setFavoriteSport(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FAV_SPORT)));
                user.setSkillLevel(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SKILL_LEVEL)));
            }
            cursor.close();
            return user;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ROLE, "user");
        values.put(COLUMN_FAV_SPORT, "Not specified");
        values.put(COLUMN_SKILL_LEVEL, "Beginner");

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean updateUserProfile(int id, String favoriteSport, String skillLevel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FAV_SPORT, favoriteSport);
        values.put(COLUMN_SKILL_LEVEL, skillLevel);

        int result = db.update(TABLE_USERS, values, COLUMN_USER_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    public User authenicateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USER_ID, COLUMN_USERNAME, COLUMN_ROLE},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)));
            user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)));
            user.setRole(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE)));
            cursor.close();
            return user;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    // --- Turf Operations ---

    public boolean addTurf(String name, String location, double price, String imageName, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TURF_NAME, name);
        values.put(COLUMN_TURF_LOCATION, location);
        values.put(COLUMN_TURF_PRICE, price);
        values.put(COLUMN_TURF_IMAGE, imageName);
        values.put(COLUMN_TURF_CATEGORY, category);

        long result = db.insert(TABLE_TURFS, null, values);
        return result != -1;
    }
    
    public Turf getTurf(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TURFS, null, COLUMN_TURF_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
                
        if (cursor != null && cursor.moveToFirst()) {
            Turf turf = new Turf();
            turf.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TURF_ID)));
            turf.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TURF_NAME)));
            turf.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TURF_LOCATION)));
            turf.setPricePerHour(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TURF_PRICE)));
            turf.setImageName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TURF_IMAGE)));
            turf.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TURF_CATEGORY)));
            cursor.close();
            return turf;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    public List<Turf> getAllTurfs() {
        List<Turf> turfList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TURFS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Turf turf = new Turf();
                turf.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TURF_ID)));
                turf.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TURF_NAME)));
                turf.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TURF_LOCATION)));
                turf.setPricePerHour(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TURF_PRICE)));
                turf.setImageName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TURF_IMAGE)));
                if (cursor.getColumnIndex(COLUMN_TURF_CATEGORY) != -1) {
                    turf.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TURF_CATEGORY)));
                }
                turfList.add(turf);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return turfList;
    }

    // --- Booking Operations ---

    public boolean bookTurf(int userId, int turfId, String date, String timeSlot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_B_USER_ID, userId);
        values.put(COLUMN_B_TURF_ID, turfId);
        values.put(COLUMN_BOOKING_DATE, date);
        values.put(COLUMN_BOOKING_SLOT, timeSlot);
        values.put(COLUMN_BOOKING_STATUS, "confirmed");

        long result = db.insert(TABLE_BOOKINGS, null, values);
        return result != -1;
    }
    
    public boolean isSlotBooked(int turfId, String date, String timeSlot) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKINGS, new String[]{COLUMN_BOOKING_ID},
                COLUMN_B_TURF_ID + "=? AND " + COLUMN_BOOKING_DATE + "=? AND " + COLUMN_BOOKING_SLOT + "=? AND " + COLUMN_BOOKING_STATUS + "=?",
                new String[]{String.valueOf(turfId), date, timeSlot, "confirmed"}, null, null, null);
                
        boolean isBooked = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        return isBooked;
    }

    public List<Booking> getBookingsForUser(int userId) {
        List<Booking> bookingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKINGS, null, COLUMN_B_USER_ID + "=?",
                new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Booking booking = new Booking();
                booking.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_ID)));
                booking.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_B_USER_ID)));
                booking.setTurfId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_B_TURF_ID)));
                booking.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_DATE)));
                booking.setTimeSlot(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_SLOT)));
                booking.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_STATUS)));
                bookingList.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bookingList;
    }
    
    public List<Booking> getAllBookings() {
        List<Booking> bookingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKINGS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Booking booking = new Booking();
                booking.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_ID)));
                booking.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_B_USER_ID)));
                booking.setTurfId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_B_TURF_ID)));
                booking.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_DATE)));
                booking.setTimeSlot(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_SLOT)));
                booking.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOKING_STATUS)));
                bookingList.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bookingList;
    }

    // --- Review Operations ---

    public boolean addReview(int userId, int turfId, float rating, String reviewText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_R_USER_ID, userId);
        values.put(COLUMN_R_TURF_ID, turfId);
        values.put(COLUMN_R_RATING, rating);
        values.put(COLUMN_R_TEXT, reviewText);

        long result = db.insert(TABLE_REVIEWS, null, values);
        return result != -1;
    }

    public List<Review> getReviewsForTurf(int turfId) {
        List<Review> reviewList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT r.*, u." + COLUMN_USERNAME + " FROM " + TABLE_REVIEWS + " r " +
                       "JOIN " + TABLE_USERS + " u ON r." + COLUMN_R_USER_ID + " = u." + COLUMN_USER_ID + " " +
                       "WHERE r." + COLUMN_R_TURF_ID + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(turfId)});

        if (cursor.moveToFirst()) {
            do {
                Review review = new Review();
                review.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID)));
                review.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_R_USER_ID)));
                review.setTurfId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_R_TURF_ID)));
                review.setRating(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_R_RATING)));
                review.setReviewText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_R_TEXT)));
                review.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)));
                reviewList.add(review);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return reviewList;
    }

    public float getAverageRatingForTurf(int turfId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT AVG(" + COLUMN_R_RATING + ") FROM " + TABLE_REVIEWS + " WHERE " + COLUMN_R_TURF_ID + "=?", new String[]{String.valueOf(turfId)});
        float avgRating = 0;
        if (cursor.moveToFirst()) {
            avgRating = cursor.getFloat(0);
        }
        cursor.close();
        return avgRating;
    }

    // --- Analytics Operations ---

    public int getTotalUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_USERS + " WHERE " + COLUMN_ROLE + "='user'", null);
        int count = 0;
        if (cursor.moveToFirst()) count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public int getTotalTurfs() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_TURFS, null);
        int count = 0;
        if (cursor.moveToFirst()) count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public int getTotalBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_BOOKINGS, null);
        int count = 0;
        if (cursor.moveToFirst()) count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public double getTotalRevenue() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(t." + COLUMN_TURF_PRICE + ") FROM " + TABLE_BOOKINGS + " b " +
                       "JOIN " + TABLE_TURFS + " t ON b." + COLUMN_B_TURF_ID + " = t." + COLUMN_TURF_ID + " " +
                       "WHERE b." + COLUMN_BOOKING_STATUS + "='confirmed'";
        Cursor cursor = db.rawQuery(query, null);
        double total = 0;
        if (cursor.moveToFirst()) total = cursor.getDouble(0);
        cursor.close();
        return total;
    }
}
