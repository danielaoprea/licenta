package ro.softvision.cancerdiagnosticapp.db;

import ro.softvision.cancerdiagnosticapp.database.tables.DoctorTableUtils;
import ro.softvision.cancerdiagnosticapp.database.tables.PacientTableUtils;
import ro.softvision.cancerdiagnosticapp.database.tables.RadiographyTableUtils;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CancerDiagnosticOpenHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "cancerdiagnostic.db";

	private static final int DB_VERSION = 1;

	public CancerDiagnosticOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		DoctorTableUtils.onCreate(db);
		PacientTableUtils.onCreate(db);
		RadiographyTableUtils.onCreate(db);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
