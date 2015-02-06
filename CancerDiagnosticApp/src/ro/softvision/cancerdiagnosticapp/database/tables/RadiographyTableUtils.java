package ro.softvision.cancerdiagnosticapp.database.tables;

import ro.softvision.cancerdiagnosticapp.beans.Radiography;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class RadiographyTableUtils {

	public static final String TABLE_NAME = "radiographys";
	public static final String ROW_ID = "_id";
	public static final String PATH = "path";
	public static final String DATE = "date";
	public static final String DIAGNOSTIC = "diagnostic";
	public static final String DETAILS = "details";
	public static final String CNP_PACIENT = "cnp_pacient";

	public static String createTable() {
		String query = "";

		query = String.format("CREATE TABLE %s (", TABLE_NAME);
		query += String.format(
				"%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,", ROW_ID);
		query += String.format("%s TEXT NOT NULL, ", PATH);
		query += String.format("%s INTEGER NOT NULL, ", DATE);
		query += String.format("%s TEXT,  ", DIAGNOSTIC);
		query += String.format("%s TEXT,  ", DETAILS);
		query += String.format("%s INTEGER NOT NULL) ", CNP_PACIENT);

		return query;
	}

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(createTable());
	}

	public static ContentValues createContentValues(Radiography radiography) {
		ContentValues cv = new ContentValues();
		cv.put(PATH, radiography.getPath());
		cv.put(DATE, radiography.getDate());
		cv.put(DIAGNOSTIC, radiography.getDiagnostic());
		cv.put(DETAILS, radiography.getDetails());
		cv.put(CNP_PACIENT, radiography.getCnpPacient());

		return cv;
	}
}
