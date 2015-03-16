package ro.danielaoprea.cancerdiagnosticapp.activities;

import java.util.ArrayList;
import java.util.List;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.adapters.WelcomePageAdapter;
import ro.danielaoprea.cancerdiagnosticapp.beans.User;
import ro.danielaoprea.cancerdiagnosticapp.database.tables.UserTableUtils;
import ro.danielaoprea.cancerdiagnosticapp.fragments.WelcomeFragment;
import ro.danielaoprea.cancerdiagnosticapp.providers.CancerDiagnosticContentProvider;
import ro.danielaoprea.cancerdiagnosticapp.utils.Constants;
import ro.danielaoprea.cancerdiagnosticapp.utils.Helper;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WelcomeActivity extends FragmentActivity implements
		OnClickListener {

	private Button loginButton;
	private WelcomePageAdapter welcomePageAdapter;
	private ViewPager viewPager;
	private String role;
	private String username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Constants.Prefs.NO_DOCTOR_LOGGED != getLoggedIdFromSharedPref()) {
			new QueryUserTask().execute(getLoggedIdFromSharedPref());
			finish();
		} else {
			setContentView(R.layout.welcome_activity_layout);
			loginButton = (Button) findViewById(R.id.log_in_button);
			loginButton.setOnClickListener(this);

			List<Fragment> fragments = getFragments();
			welcomePageAdapter = new WelcomePageAdapter(
					getSupportFragmentManager(), fragments);
			viewPager = (ViewPager) findViewById(R.id.viewpager);
			viewPager.setAdapter(welcomePageAdapter);
		}
		
	}

	private long getLoggedIdFromSharedPref() {
		SharedPreferences userPref = getSharedPreferences(
				Constants.Prefs.USER_PREFERENCES_FILE, Context.MODE_PRIVATE);
		return userPref.getLong(Constants.Prefs.LOGGED_DOCTOR_ID,
				Constants.Prefs.NO_DOCTOR_LOGGED);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.log_in_button:
			Intent intent = new Intent(getApplicationContext(),
					LogInActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	private List<Fragment> getFragments() {
		List<Fragment> fragmentList = new ArrayList<>();
		fragmentList.add(WelcomeFragment.newInstance(R.drawable.rsz_image1));
		fragmentList.add(WelcomeFragment.newInstance(R.drawable.rsz_image2));
		fragmentList.add(WelcomeFragment.newInstance(R.drawable.image4));
		return fragmentList;
	}

	private void insertDoctor() {
		User d = new User(1, "Oprea Daniela",
				Helper.SecurityUtils.sha1("daniela"), "daniela", "daniela",
				"0264 555000", User.DOCTOR);
		getContentResolver().insert(CancerDiagnosticContentProvider.USER_URI,
				UserTableUtils.createContentValues(d));

		User a = new User(2, "Pop Gigel", Helper.SecurityUtils.sha1("admin"),
				"admin", "gigi", "111", User.ADMIN);
		getContentResolver().insert(CancerDiagnosticContentProvider.USER_URI,
				UserTableUtils.createContentValues(a));
	}

	private class QueryUserTask extends AsyncTask<Long, Void, User> {

		@Override
		protected User doInBackground(Long... params) {
			Cursor cursor = getContentResolver().query(
					CancerDiagnosticContentProvider.USER_URI, null,
					UserTableUtils.ROW_ID + " =? ",
					new String[] { String.valueOf(params[0]) }, null);
			cursor.moveToFirst();
			User user = new User();
			user.setUsername(cursor.getString(cursor
					.getColumnIndex(UserTableUtils.NAME)));
			user.setRole(cursor.getString(cursor
					.getColumnIndex(UserTableUtils.ROLE)));
			cursor.close();
			return user;
		}

		@Override
		protected void onPostExecute(User result) {
			role = result.getRole();
			username = result.getUsername();
			if (role.equals(User.ADMIN)) {
				Intent adminIntent = new Intent(getApplicationContext(),
						DoctorListActivity.class);
				startActivity(adminIntent);
			} else {
				Intent intent = new Intent(getApplicationContext(),
						PacientListActivity.class);
				intent.putExtra(Constants.IntentExtras.EXTRA_DOCTOR_ID,
						getLoggedIdFromSharedPref());
				intent.putExtra(Constants.IntentExtras.EXTRA_USERNAME, username);
				startActivity(intent);
			}
		}
	}
}
