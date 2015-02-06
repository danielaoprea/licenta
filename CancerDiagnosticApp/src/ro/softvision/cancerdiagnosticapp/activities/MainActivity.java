package ro.softvision.cancerdiagnosticapp.activities;

import ro.softvision.cancerdiagnosticapp.R;
import ro.softvision.cancerdiagnosticapp.beans.Doctor;
import ro.softvision.cancerdiagnosticapp.beans.Pacient;
import ro.softvision.cancerdiagnosticapp.database.tables.DoctorTableUtils;
import ro.softvision.cancerdiagnosticapp.database.tables.PacientTableUtils;
import ro.softvision.cancerdiagnosticapp.fragments.DoctorListFragment;
import ro.softvision.cancerdiagnosticapp.fragments.DoctorListFragment.OnDoctorSelectedListener;
import ro.softvision.cancerdiagnosticapp.fragments.PacientListFragment;
import ro.softvision.cancerdiagnosticapp.providers.CancerDiagnosticContentProvider;
import ro.softvision.cancerdiagnosticapp.utils.Constants;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnDoctorSelectedListener {

	private static final int INVALID_CODE = -1;
	private TextView queryTextView;
	private long doctorId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity_layout);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			doctorId = extras.getLong(Constants.IntentExtras.EXTRA_DOCTOR_ID);
			DoctorListFragment doctorFragment = DoctorListFragment
					.newInstance();
			getFragmentManager().beginTransaction()
					.add(R.id.doctor_list_layout, doctorFragment).commit();

			PacientListFragment pacientFragment = PacientListFragment
					.newInstance(extras
							.getLong(Constants.IntentExtras.EXTRA_DOCTOR_ID));
			getFragmentManager().beginTransaction()
					.replace(R.id.pacient_list_layout, pacientFragment)
					.commit();
		} else {
			doctorId = INVALID_CODE;
			DoctorListFragment doctorFragment = DoctorListFragment
					.newInstance();
			getFragmentManager().beginTransaction()
					.add(R.id.doctor_list_layout, doctorFragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {

		MenuItem itemLogIn = menu.findItem(R.id.action_log_in);
		MenuItem itemLogOut = menu.findItem(R.id.action_log_out);

		if (doctorId == INVALID_CODE) {
			itemLogIn.setVisible(true);
			itemLogOut.setVisible(false);
		} else {
			itemLogIn.setVisible(false);
			itemLogOut.setVisible(true);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_log_in:
			invalidateOptionsMenu();
			Intent intent = new Intent(getApplicationContext(),
					LogInActivity.class);
			startActivity(intent);
			return true;

		case R.id.action_log_out:
			logOut();
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void logOut() {

	}

	private void insertDoctors() {
		Doctor doctor;
		doctor = new Doctor(2, "Oltean Maria", "parola", "0756 123 456",
				Doctor.STATUS_NOT_LOGGED);
		getContentResolver().insert(CancerDiagnosticContentProvider.DOCTOR_URI,
				DoctorTableUtils.createContentValues(doctor));
		doctor = new Doctor(1, "Popescu Vlad", "vlad", "0756 123 987",
				Doctor.STATUS_NOT_LOGGED);
		getContentResolver().insert(CancerDiagnosticContentProvider.DOCTOR_URI,
				DoctorTableUtils.createContentValues(doctor));
	}

	private void insertPacients() {
		Pacient pacient = new Pacient(2324, "Puscas Ana", "Cluj-Napoca",
				"0743 896 557", 1);
		getContentResolver().insert(
				CancerDiagnosticContentProvider.PACIENT_URI,
				PacientTableUtils.createContentValues(pacient));
	}

	@Override
	public void onDoctorSelected(long id) {

		Cursor c = getContentResolver().query(
				CancerDiagnosticContentProvider.DOCTOR_URI, null,
				DoctorTableUtils.ROW_ID + "=?",
				new String[] { String.valueOf(id) }, null);
		c.moveToFirst();
		if (Doctor.STATUS_NOT_LOGGED == c.getInt(c
				.getColumnIndex(DoctorTableUtils.AUTHENTICATION_STATUS))) {
			Intent intent = new Intent(getApplicationContext(),
					LogInActivity.class);
			intent.putExtra(
					Constants.IntentExtras.EXTRA_AUTHENTICATION_DOCTOR_ID,
					id);
			startActivity(intent);
		}

	}

}
