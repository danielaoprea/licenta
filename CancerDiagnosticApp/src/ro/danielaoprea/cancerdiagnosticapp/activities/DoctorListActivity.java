package ro.danielaoprea.cancerdiagnosticapp.activities;

import java.util.ArrayList;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.adapters.DoctorListAdapter;
import ro.danielaoprea.cancerdiagnosticapp.beans.Categorizable;
import ro.danielaoprea.cancerdiagnosticapp.beans.User;
import ro.danielaoprea.cancerdiagnosticapp.database.tables.UserTableUtils;
import ro.danielaoprea.cancerdiagnosticapp.fragments.dialogs.AbstractEditTextDialogFragment.OnEditTextDialogListener;
import ro.danielaoprea.cancerdiagnosticapp.fragments.dialogs.DoctorEditTextDialogFragment;
import ro.danielaoprea.cancerdiagnosticapp.fragments.dialogs.UpdateDoctorEditTextDialogFragment;
import ro.danielaoprea.cancerdiagnosticapp.providers.CancerDiagnosticContentProvider;
import ro.danielaoprea.cancerdiagnosticapp.utils.Constants;
import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
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

public class DoctorListActivity extends ListActivity implements
		LoaderCallbacks<Cursor>, OnEditTextDialogListener,
		MultiChoiceModeListener {

	private static final int ID_LOADER_DOCTORS = 2;
	private static final String INSERT_DOCTOR_DIALOG = "insert_doctor_dialog";
	private static final String UPDATE_DOCTOR_DIALOG = "update_doctor_dialog";
	private DoctorListAdapter doctorAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctor_list_activity_layout);
		doctorAdapter = new DoctorListAdapter(this, null);
		setListAdapter(doctorAdapter);
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		getListView().setMultiChoiceModeListener(this);
		getLoaderManager().initLoader(ID_LOADER_DOCTORS, null, this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.doctor_list_activity_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_doctor_action:
			showInsertDialog();
			return true;
		case R.id.log_out_action:
			logout();

		default:
			return false;
		}
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

	private void showInsertDialog() {
		DoctorEditTextDialogFragment fragment = DoctorEditTextDialogFragment
				.newInstance();
		fragment.show(getFragmentManager(), INSERT_DOCTOR_DIALOG);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Cursor cursor = (Cursor) doctorAdapter.getItem(position);
		String name = cursor.getString(cursor
				.getColumnIndex(UserTableUtils.NAME));
		String username = cursor.getString(cursor
				.getColumnIndex(UserTableUtils.USERNAME));
		String phone = cursor.getString(cursor
				.getColumnIndex(UserTableUtils.PHONE));
		String email = cursor.getString(cursor
				.getColumnIndex(UserTableUtils.EMAIL));
		UpdateDoctorEditTextDialogFragment fragment = UpdateDoctorEditTextDialogFragment
				.newInstance(id, name, username, phone, email);
		fragment.show(getFragmentManager(), UPDATE_DOCTOR_DIALOG);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case ID_LOADER_DOCTORS:

			return new CursorLoader(getApplicationContext(),
					CancerDiagnosticContentProvider.USER_URI, null,
					UserTableUtils.ROLE + " =? ", new String[] { User.DOCTOR },
					UserTableUtils.NAME);

		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		((DoctorListAdapter) getListAdapter()).swapCursor(data);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		((DoctorListAdapter) getListAdapter()).swapCursor(null);

	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.doctor_list_activity_cab_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete_doctor_action:
			new DeteleDoctorsTask().execute();
			return true;

		default:
			return false;
		}
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		doctorAdapter.setSelectedItemsIds(new ArrayList<Integer>());

	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position,
			long id, boolean checked) {
		final int checkedCount = getListView().getCheckedItemCount();
		mode.setTitle(getString(R.string.selection, checkedCount));
		doctorAdapter.toggleSelection(position);

	}

	private class InsertDoctorTask extends AsyncTask<User, Void, Void> {

		@Override
		protected Void doInBackground(User... params) {
			getContentResolver().insert(
					CancerDiagnosticContentProvider.USER_URI,
					UserTableUtils.createContentValues(params[0]));
			return null;
		}

	}

	private class UpdateDoctorTask extends AsyncTask<User, Void, Void> {

		@Override
		protected Void doInBackground(User... params) {
			getContentResolver().update(
					CancerDiagnosticContentProvider.USER_URI,
					UserTableUtils.createContentValuesForUpdate(params[0]),
					UserTableUtils.ROW_ID + " =? ",
					new String[] { String.valueOf(params[0].getId()) });
			return null;
		}

	}

	private class DeteleDoctorsTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<Integer> doctorsIds = doctorAdapter.getSelectedIds();
			StringBuilder condition = new StringBuilder();
			for (int i = 0; i < doctorAdapter.getSelectedCount(); i++) {

				Cursor cursor = (Cursor) doctorAdapter.getItem(doctorsIds
						.get(i));
				long id = cursor.getLong(cursor
						.getColumnIndex(UserTableUtils.ROW_ID));
				condition
						.append(UserTableUtils.ROW_ID)
						.append("=")
						.append(id)
						.append((i == (doctorAdapter.getSelectedCount() - 1) ? ""
								: " OR "));
			}
			getContentResolver().delete(
					CancerDiagnosticContentProvider.USER_URI,
					condition.toString(), null);
			return null;
		}

	}

	@Override
	public void onPositiveButtonClicked(Categorizable category, String tag) {
		User user = (User) category;
		switch (tag) {
		case INSERT_DOCTOR_DIALOG:
			new InsertDoctorTask().execute(user);
			break;
		case UPDATE_DOCTOR_DIALOG:
			new UpdateDoctorTask().execute(user);
			break;
		default:
			break;
		}

	}

}
