package ro.danielaoprea.cancerdiagnosticapp.activities;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.beans.User;
import ro.danielaoprea.cancerdiagnosticapp.database.tables.UserTableUtils;
import ro.danielaoprea.cancerdiagnosticapp.providers.CancerDiagnosticContentProvider;
import ro.danielaoprea.cancerdiagnosticapp.utils.Constants;
import ro.danielaoprea.cancerdiagnosticapp.utils.Helper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends Activity implements OnClickListener {

	private EditText passwordEditText;
	private Button logInButton;
	private TextView userNameEditText;
	private String password;
	private String user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_in_layout);
		passwordEditText = (EditText) findViewById(R.id.doctor_password_edit_text);
		userNameEditText = (TextView) findViewById(R.id.doctor_user_edit_text);
		logInButton = (Button) findViewById(R.id.login_button);
		logInButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_button:
			logIn();
			break;

		default:
			break;
		}

	}

	private void logIn() {
		user = userNameEditText.getText().toString();
		password = passwordEditText.getText().toString();
		Cursor doctorCursor = getContentResolver().query(
				CancerDiagnosticContentProvider.USER_URI, null,
				UserTableUtils.USERNAME + "=?", new String[] { user }, null);
		if (doctorCursor.getCount() != 0) {
			doctorCursor.moveToFirst();
			if (Helper.SecurityUtils.sha1(password).equals(
					doctorCursor.getString(doctorCursor
							.getColumnIndex(UserTableUtils.PASSWORD)))) {

				if (User.ADMIN.equals(doctorCursor.getString(doctorCursor
						.getColumnIndex(UserTableUtils.ROLE)))) {
					Intent adminIntent = new Intent(getApplicationContext(),
							DoctorListActivity.class);
					startActivity(adminIntent);
				} else {
					Intent intent = new Intent(getApplicationContext(),
							PacientListActivity.class);
					intent.putExtra(Constants.IntentExtras.EXTRA_DOCTOR_ID,
							doctorCursor.getLong(doctorCursor
									.getColumnIndex(UserTableUtils.ROW_ID)));
					intent.putExtra(Constants.IntentExtras.EXTRA_USERNAME, user);
					startActivity(intent);
				}
				saveIdSharedPref(doctorCursor.getLong(doctorCursor
						.getColumnIndex(UserTableUtils.ROW_ID)));
				finishAffinity();
			} else {
				Toast.makeText(getApplicationContext(),
						getString(R.string.wrong_password), Toast.LENGTH_LONG)
						.show();
			}
		} else {
			Toast.makeText(getApplicationContext(),
					getString(R.string.invalid_user), Toast.LENGTH_LONG).show();
		}

		doctorCursor.close();
	}

	private void saveIdSharedPref(long id) {
		SharedPreferences userPrefs = getSharedPreferences(
				Constants.Prefs.USER_PREFERENCES_FILE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = userPrefs.edit();
		editor.putLong(Constants.Prefs.LOGGED_DOCTOR_ID, id);
		editor.commit();
	}

}
