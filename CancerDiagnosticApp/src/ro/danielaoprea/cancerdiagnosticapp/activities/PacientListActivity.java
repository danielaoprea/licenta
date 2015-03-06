package ro.danielaoprea.cancerdiagnosticapp.activities;

import java.util.ArrayList;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.adapters.PacientListAdapter;
import ro.danielaoprea.cancerdiagnosticapp.beans.Categorizable;
import ro.danielaoprea.cancerdiagnosticapp.beans.Pacient;
import ro.danielaoprea.cancerdiagnosticapp.database.tables.PacientTableUtils;
import ro.danielaoprea.cancerdiagnosticapp.database.tables.UserTableUtils;
import ro.danielaoprea.cancerdiagnosticapp.fragments.dialogs.AbstractEditTextDialogFragment.OnEditTextDialogListener;
import ro.danielaoprea.cancerdiagnosticapp.fragments.dialogs.ChangePasswordDialogFragment;
import ro.danielaoprea.cancerdiagnosticapp.fragments.dialogs.ChangePasswordDialogFragment.OnChangePasswordDialogListener;
import ro.danielaoprea.cancerdiagnosticapp.fragments.dialogs.PacientEditTextDialogFragment;
import ro.danielaoprea.cancerdiagnosticapp.providers.CancerDiagnosticContentProvider;
import ro.danielaoprea.cancerdiagnosticapp.utils.Constants;
import ro.danielaoprea.cancerdiagnosticapp.utils.Helper;
import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;
import android.widget.Toast;

public class PacientListActivity extends ListActivity implements
		LoaderCallbacks<Cursor>, OnEditTextDialogListener,
		OnChangePasswordDialogListener, MultiChoiceModeListener {

	private static final int ID_LOADER_PACIENTS = 1;
	private static final String INSERT_PACIENT_DIALOG = "insert_pacient_dialog";
	private static final String CHANGE_PASSWORD_DIALOG = "change_password_dialog";
	private PacientListAdapter pacientAdapter;
	private long doctorId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pacient_list_activity_layout);

		doctorId = getIntent().getExtras().getLong(
				Constants.IntentExtras.EXTRA_DOCTOR_ID);
		getActionBar().setTitle(
				getIntent().getExtras().getString(
						Constants.IntentExtras.EXTRA_USERNAME));
		pacientAdapter = new PacientListAdapter(this, null);
		setListAdapter(pacientAdapter);
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		getListView().setMultiChoiceModeListener(this);
		getLoaderManager().initLoader(ID_LOADER_PACIENTS, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.pacient_list_activity_menu, menu);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_pacient_action:
			showInsertDialog();
			return true;
		case R.id.change_password_action:
			showChangePasswordDialog();
			return true;
		case R.id.log_out_action:
			logout();
			return true;

		default:
			return false;
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getApplicationContext(),
				PacientDetailsActivity.class);
		Cursor c = (Cursor) pacientAdapter.getItem(position);
		intent.putExtra(Constants.IntentExtras.EXTRA_CNP,
				c.getString(c.getColumnIndex(PacientTableUtils.CNP)));
		startActivity(intent);
	}

	private void logout() {

		Intent intent = new Intent(getApplicationContext(),
				WelcomeActivity.class);
		startActivity(intent);
		SharedPreferences userPrefs = getSharedPreferences(
				Constants.Prefs.USER_PREFERENCES_FILE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = userPrefs.edit();
		editor.putLong(Constants.Prefs.LOGGED_DOCTOR_ID,
				Constants.Prefs.NO_DOCTOR_LOGGED);
		editor.commit();
		finish();
	}

	private void showChangePasswordDialog() {
		ChangePasswordDialogFragment fragment = ChangePasswordDialogFragment
				.newInstance(getString(R.string.new_password));
		fragment.show(getFragmentManager(), CHANGE_PASSWORD_DIALOG);
	}

	private void showInsertDialog() {

		PacientEditTextDialogFragment fragment = PacientEditTextDialogFragment
				.newInstance();
		fragment.show(getFragmentManager(), INSERT_PACIENT_DIALOG);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case ID_LOADER_PACIENTS:
			CursorLoader pacientsCursor = new CursorLoader(
					getApplicationContext(),
					CancerDiagnosticContentProvider.PACIENT_URI, null,
					PacientTableUtils.ID_DOCTOR + "=?",
					new String[] { String.valueOf(doctorId) }, null);

			return pacientsCursor;

		default:
			break;
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		((PacientListAdapter) getListAdapter()).swapCursor(data);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		((PacientListAdapter) getListAdapter()).swapCursor(null);

	}

	@Override
	public void onChangePasswordPositiveButtonClicked(String currentPassword,
			String newPassword, String confirmPassword) {

		new UpdateDoctorTask().execute(String.valueOf(doctorId),
				currentPassword, newPassword, confirmPassword);
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.pacient_list_activity_cab_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete_pacient_action:
			new DetelePacientsTask().execute();
			return true;

		default:
			return false;
		}
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		pacientAdapter.setSelectedItemsIds(new ArrayList<Integer>());

	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position,
			long id, boolean checked) {
		final int checkedCount = getListView().getCheckedItemCount();
		mode.setTitle(getString(R.string.selection, checkedCount));
		pacientAdapter.toggleSelection(position);

	}

	@Override
	public void onPositiveButtonClicked(Categorizable category, String tag) {
		Pacient pacient = (Pacient) category;
		pacient.setIdDoctor(doctorId);
		switch (tag) {
		case INSERT_PACIENT_DIALOG:
			new InsertPacientTask().execute(pacient);
			break;
		default:
			break;
		}
	}

	private class InsertPacientTask extends AsyncTask<Pacient, Void, Void> {

		@Override
		protected Void doInBackground(Pacient... params) {

			getContentResolver().insert(
					CancerDiagnosticContentProvider.PACIENT_URI,
					PacientTableUtils.createContentValues(params[0]));
			return null;
		}

	}

	private class UpdateDoctorTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			Cursor cursor = getContentResolver().query(
					CancerDiagnosticContentProvider.USER_URI, null,
					UserTableUtils.ROW_ID + " =? ", new String[] { params[0] },
					null);
			cursor.moveToFirst();
			String password = cursor.getString(cursor
					.getColumnIndex(UserTableUtils.PASSWORD));
			if (password.equals(Helper.SecurityUtils.sha1(params[1]))) {
				if (params[2].equals(params[3])) {
					ContentValues values = new ContentValues();
					values.put(UserTableUtils.PASSWORD,
							Helper.SecurityUtils.sha1(params[2]));
					getContentResolver().update(
							CancerDiagnosticContentProvider.USER_URI, values,
							UserTableUtils.ROW_ID + " =? ",
							new String[] { params[0] });

				} else {
					return Constants.Password.WRONG_CONFIRMED_PASSWORD;
				}
			} else {

				return Constants.Password.WRONG_CURRENT_PASSWORD;
			}
			return Constants.Password.PASSWORD_CHANGED;
		}

		@Override
		protected void onPostExecute(Integer result) {
			switch (result) {
			case Constants.Password.PASSWORD_CHANGED:
				Toast.makeText(getApplicationContext(),
						getString(R.string.password_changed), Toast.LENGTH_LONG)
						.show();
				break;
			case Constants.Password.WRONG_CONFIRMED_PASSWORD:
				Toast.makeText(getApplicationContext(),
						getString(R.string.wrong_confirmed_password),
						Toast.LENGTH_LONG).show();
				break;
			case Constants.Password.WRONG_CURRENT_PASSWORD:
				Toast.makeText(getApplicationContext(),
						getString(R.string.wrong_current_password),
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
	}

	private class DetelePacientsTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<Integer> pacientsIds = pacientAdapter.getSelectedIds();
			StringBuilder condition = new StringBuilder();
			for (int i = 0; i < pacientAdapter.getSelectedCount(); i++) {

				Cursor cursor = (Cursor) pacientAdapter.getItem(pacientsIds
						.get(i));
				long id = cursor.getLong(cursor
						.getColumnIndex(PacientTableUtils.ROW_ID));
				condition
						.append(PacientTableUtils.ROW_ID)
						.append("=")
						.append(id)
						.append((i == (pacientAdapter.getSelectedCount() - 1) ? ""
								: " OR "));
			}
			getContentResolver().delete(
					CancerDiagnosticContentProvider.PACIENT_URI,
					condition.toString(), null);
			return null;
		}

	}

}
