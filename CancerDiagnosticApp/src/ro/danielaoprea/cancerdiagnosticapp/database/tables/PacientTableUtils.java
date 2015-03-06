package ro.danielaoprea.cancerdiagnosticapp.database.tables;

import ro.danielaoprea.cancerdiagnosticapp.beans.Pacient;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class PacientTableUtils {

	public static final String TABLE_NAME = "pacients";
	public static final String ROW_ID = "_id";
	public static final String NAME = "name";
	public static final String ADDRESS = "address";
	public static final String PHONE = "phone_number";
	public static final String CNP = "cnp";
	public static final String EMAIL = "email";
	public static final String ID_DOCTOR = "id_doctor";
	private static final String PACIENT_TRIGGER = "pacient_trigger";

	public static String createTable() {
		String query = "";

		query = String.format("CREATE TABLE %s ( ", TABLE_NAME);
		query += String.format(
				"%s INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,", ROW_ID);
		query += String.format("%s TEXT NOT NULL ,", CNP);
		query += String.format("%s TEXT NOT NULL ,", NAME);
		query += String.format("%s TEXT NOT NULL ,", ADDRESS);
		query += String.format("%s TEXT NOT NULL ,", PHONE);
		query += String.format("%s TEXT NOT NULL ,", EMAIL);
		query += String.format("%s INTEGER NOT NULL ) ", ID_DOCTOR);

		return query;
	}

	public static void triggerDeletePacients(SQLiteDatabase database) {
		database.execSQL("CREATE TRIGGER " + PACIENT_TRIGGER
				+ "AFTER DELETE ON " + UserTableUtils.TABLE_NAME
				+ " FOR EACH ROW BEGIN DELETE FROM "
				+ PacientTableUtils.TABLE_NAME + " WHERE "
				+ PacientTableUtils.ID_DOCTOR + " = OLD. "
				+ UserTableUtils.ROW_ID + " ; END");
	}

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(createTable());
	}

	public static ContentValues createContentValues(Pacient pacient) {
		ContentValues cv = new ContentValues();
		cv.put(NAME, pacient.getName());
		cv.put(CNP, pacient.getCnp());
		cv.put(ADDRESS, pacient.getAddress());
		cv.put(PHONE, pacient.getPhoneNumber());
		cv.put(EMAIL, pacient.getEmail());
		cv.put(ID_DOCTOR, pacient.getIdDoctor());

		return cv;
	}
}
