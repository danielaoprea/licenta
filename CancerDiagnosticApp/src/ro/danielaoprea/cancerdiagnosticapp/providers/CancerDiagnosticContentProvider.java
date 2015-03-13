package ro.danielaoprea.cancerdiagnosticapp.providers;

import ro.danielaoprea.cancerdiagnosticapp.database.tables.PacientTableUtils;
import ro.danielaoprea.cancerdiagnosticapp.database.tables.RadiographyTableUtils;
import ro.danielaoprea.cancerdiagnosticapp.database.tables.UserTableUtils;
import ro.danielaoprea.cancerdiagnosticapp.db.CancerDiagnosticOpenHelper;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class CancerDiagnosticContentProvider extends ContentProvider {

	public static final String AUTHORITY = "ro.danielaoprea.cancerdiagnosticapp.providers.CancerDiagnosticContentProvider";

	private static final int USER = 1;
	private static final int PACIENT = 10;
	private static final int RADIOGRAPHY = 20;

	private static final String USER_TABLE = UserTableUtils.TABLE_NAME;
	private static final String PACIENT_TABLE = PacientTableUtils.TABLE_NAME;
	private static final String RADIOGRAPHY_TABLE = RadiographyTableUtils.TABLE_NAME;

	private CancerDiagnosticOpenHelper database;

	public static Uri USER_URI = Uri.parse("content://" + AUTHORITY + "/"
			+ USER_TABLE);
	public static Uri PACIENT_URI = Uri.parse("content://" + AUTHORITY + "/"
			+ PACIENT_TABLE);
	public static Uri RADIOGRAPHY_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + RADIOGRAPHY_TABLE);

	private static final UriMatcher uriMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);

	static {
		uriMatcher.addURI(AUTHORITY, USER_TABLE, USER);
		uriMatcher.addURI(AUTHORITY, PACIENT_TABLE, PACIENT);
		uriMatcher.addURI(AUTHORITY, RADIOGRAPHY_TABLE, RADIOGRAPHY);
	}

	@Override
	public boolean onCreate() {
		database = new CancerDiagnosticOpenHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = database.getWritableDatabase();
		int uriType = uriMatcher.match(uri);
		Cursor cursor;
		switch (uriType) {
		case USER:
			cursor = db.query(USER_TABLE, projection, selection, selectionArgs,
					null, null, sortOrder);
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
			return cursor;
		case PACIENT:
			cursor = db.query(PACIENT_TABLE, projection, selection,
					selectionArgs, null, null, sortOrder);
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
			return cursor;
		case RADIOGRAPHY:
			cursor = db.query(RADIOGRAPHY_TABLE, projection, selection,
					selectionArgs, null, null, sortOrder);
			cursor.setNotificationUri(getContext().getContentResolver(), uri);
			return cursor;
		default:
			throw new IllegalArgumentException("Unknown uri: " + uri);
		}

	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri result;
		int uriType = uriMatcher.match(uri);
		SQLiteDatabase db = database.getWritableDatabase();
		long id = 0;
		switch (uriType) {
		case USER:
			id = db.insert(USER_TABLE, null, values);
			result = Uri.parse(USER_TABLE + "/" + id);
			break;
		case PACIENT:
			id = db.insert(PACIENT_TABLE, null, values);
			result = Uri.parse(PACIENT_TABLE + "/" + id);
			break;
		case RADIOGRAPHY:
			id = db.insert(RADIOGRAPHY_TABLE, null, values);
			result = Uri.parse(RADIOGRAPHY_TABLE + "/" + id);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI:" + uri);
		}
		getContext().getContentResolver().notifyChange(result, null);
		return result;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = uriMatcher.match(uri);
		SQLiteDatabase db = database.getWritableDatabase();
		int itemsDeleted;
		switch (uriType) {
		case USER:
			itemsDeleted = db.delete(USER_TABLE, selection, selectionArgs);
			break;
		case PACIENT:
			itemsDeleted = db.delete(PACIENT_TABLE, selection, selectionArgs);
			break;
		case RADIOGRAPHY:
			itemsDeleted = db.delete(RADIOGRAPHY_TABLE, selection, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI:" + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return itemsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int uriType = uriMatcher.match(uri);
		SQLiteDatabase db = database.getWritableDatabase();
		int itemsUpdated;

		switch (uriType) {
		case USER:
			itemsUpdated = db.update(USER_TABLE, values, selection,
					selectionArgs);
			getContext().getContentResolver().notifyChange(USER_URI, null);
			break;

		case RADIOGRAPHY:
			itemsUpdated = db.update(RADIOGRAPHY_TABLE, values, selection,
					selectionArgs);
			getContext().getContentResolver().notifyChange(RADIOGRAPHY_URI,
					null);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI:" + uri);
		}
		return itemsUpdated;
	}

}
