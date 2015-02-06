package ro.softvision.cancerdiagnosticapp.activities;

import ro.softvision.cancerdiagnosticapp.R;
import ro.softvision.cancerdiagnosticapp.beans.Doctor;
import ro.softvision.cancerdiagnosticapp.database.tables.DoctorTableUtils;
import ro.softvision.cancerdiagnosticapp.providers.CancerDiagnosticContentProvider;
import ro.softvision.cancerdiagnosticapp.utils.Constants;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends Activity implements OnClickListener {

	private EditText codeEditText;
	private EditText passwordEditText;
	private Button logInButton;
	private TextView authenticationErrorTextView;
	private long doctorId;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_in_layout);
		codeEditText = (EditText) findViewById(R.id.doctor_cod_edit_text);
		passwordEditText = (EditText) findViewById(R.id.doctor_password_edit_text);
		logInButton = (Button) findViewById(R.id.login_button);
		logInButton.setOnClickListener(this);

		authenticationErrorTextView = (TextView) findViewById(R.id.authentication_problem_text_view);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			doctorId = extras
					.getLong(Constants.IntentExtras.EXTRA_AUTHENTICATION_DOCTOR_ID);
			codeEditText.setText(String.valueOf(doctorId));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_button:
			Toast.makeText(getApplicationContext(), "onClick",
					Toast.LENGTH_SHORT).show();
			logIn();
			break;

		default:
			break;
		}

	}

	private void logIn() {

		doctorId = Long.parseLong(codeEditText.getText().toString());
		password = passwordEditText.getText().toString();
		Cursor c = getContentResolver().query(
				CancerDiagnosticContentProvider.DOCTOR_URI, null,
				DoctorTableUtils.ROW_ID + "=?",
				new String[] { String.valueOf(doctorId) }, null);
		if (c.getCount() != 0) {
			c.moveToFirst();
			if (password.equals(c.getString(c
					.getColumnIndex(DoctorTableUtils.PASSWORD)))) {

				new UpdateTask().execute(doctorId);
				Intent intent = new Intent(getApplicationContext(),
						MainActivity.class);
				intent.putExtra(Constants.IntentExtras.EXTRA_DOCTOR_ID,
						doctorId);
				startActivity(intent);
			} else {
				authenticationErrorTextView.setVisibility(View.VISIBLE);
				authenticationErrorTextView.setText("Wrong password");
			}
		} else {
			authenticationErrorTextView.setVisibility(View.VISIBLE);
			authenticationErrorTextView.setText("Wrong user");
		}

	}

	private class UpdateTask extends AsyncTask<Long, Void, Void> {

		@Override
		protected Void doInBackground(Long... params) {
			ContentValues cv = new ContentValues();
			cv.put(DoctorTableUtils.AUTHENTICATION_STATUS, Doctor.STATUS_LOGGED);
			getContentResolver().update(
					CancerDiagnosticContentProvider.DOCTOR_URI, cv,
					DoctorTableUtils.ROW_ID + "=?",
					new String[] { String.valueOf(params[0]) });
			return null;
		}

	}
}
