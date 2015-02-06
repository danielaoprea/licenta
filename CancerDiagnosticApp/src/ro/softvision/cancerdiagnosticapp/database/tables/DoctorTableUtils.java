package ro.softvision.cancerdiagnosticapp.database.tables;

import ro.softvision.cancerdiagnosticapp.beans.Doctor;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class DoctorTableUtils {

	public static final String TABLE_NAME = "doctors";
	public static final String ROW_ID = "_id";
	public static final String NAME = "name";
	public static final String PASSWORD = "password";
	public static final String PHONE = "phone_number";
	public static final String AUTHENTICATION_STATUS = "authentication_status";

	public static String createTable() {
		String query = "";

		query = String.format("CREATE TABLE %s ( ", TABLE_NAME);
		query += String.format(
				"%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ", ROW_ID);
		query += String.format("%s TEXT NOT NULL, ", NAME);
		query += String.format("%s TEXT NOT NULL, ", PASSWORD);
		query += String.format("%s TEXT NOT NULL, ", PHONE);
		query += String.format(" %s INTEGER )", AUTHENTICATION_STATUS);

		return query;
	}

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(createTable());
	}

	public static ContentValues createContentValues(Doctor doctor) {
		ContentValues cv = new ContentValues();
		cv.put(NAME, doctor.getName());
		cv.put(PASSWORD, doctor.getPassword());
		cv.put(PHONE, doctor.getPhoneNumber());
		cv.put(AUTHENTICATION_STATUS, Doctor.STATUS_NOT_LOGGED);
		return cv;
	}
}
