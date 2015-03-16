package ro.danielaoprea.cancerdiagnosticapp.database.tables;

import ro.danielaoprea.cancerdiagnosticapp.beans.User;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class UserTableUtils {

	public static final String TABLE_NAME = "users";
	public static final String ROW_ID = "_id";
	public static final String NAME = "name";
	public static final String PASSWORD = "password";
	public static final String PHONE = "phone_number";
	public static final String USERNAME = "username";
	public static final String EMAIL = "email";
	public static final String ROLE = "role";

	public static String createTable() {
		String query = "";

		query = String.format("CREATE TABLE %s ( ", TABLE_NAME);
		query += String.format(
				"%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ", ROW_ID);
		query += String.format("%s TEXT NOT NULL, ", NAME);
		query += String.format("%s TEXT NOT NULL, ", PASSWORD);
		query += String.format("%s TEXT NOT NULL, ", USERNAME);
		query += String.format("%s TEXT NOT NULL, ", EMAIL);
		query += String.format("%s TEXT NOT NULL, ", PHONE);
		query += String.format("%s TEXT NOT NULL ) ", ROLE);

		return query;
	}

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(createTable());
	}

	public static ContentValues createContentValues(User user) {
		ContentValues cv = new ContentValues();
		cv.put(NAME, user.getName());
		cv.put(PASSWORD, user.getPassword());
		cv.put(PHONE, user.getPhoneNumber());
		cv.put(USERNAME, user.getUsername());
		cv.put(EMAIL, user.getEmail());
		cv.put(ROLE, user.getRole());
		return cv;
	}
	
	public static ContentValues createContentValuesForUpdate(User user) {
		ContentValues cv = new ContentValues();
		cv.put(NAME, user.getName());
		cv.put(PHONE, user.getPhoneNumber());
		cv.put(USERNAME, user.getUsername());
		cv.put(EMAIL, user.getEmail());
		return cv;
	}
}
