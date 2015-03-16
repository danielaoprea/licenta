package ro.danielaoprea.cancerdiagnosticapp.activities;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.utils.Constants;
import ro.danielaoprea.cancerdiagnosticapp.utils.Helper;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class RadiographyDetailsActivity extends Activity {

	private TextView dateTextView;
	private TextView diagnosticTextView;
	private TextView detailsTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.radiography_details_activity_layout);
		dateTextView = (TextView) findViewById(R.id.radiography_details_date);
		diagnosticTextView = (TextView) findViewById(R.id.radiography_details_diagnostic);
		detailsTextView = (TextView) findViewById(R.id.radiography_details_details);
		dateTextView
				.setText(getString(R.string.date)
						+ " : "
						+ Helper.Date
								.getDateDayMonthyear(getIntent()
										.getExtras()
										.getLong(
												Constants.IntentExtras.EXTRA_RADIOGRAPHY_DATE)));
		diagnosticTextView.setText(getString(R.string.diagnostic)
				+ " : "
				+ getIntent().getExtras().getString(
						Constants.IntentExtras.EXTRA_RADIOGRAPHY_DIAGNOSTIC));
		detailsTextView.setText(getString(R.string.details)
				+ " : "
				+ getIntent().getExtras().getString(
						Constants.IntentExtras.EXTRA_RADIOGRAPHY_DETAILS));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.radiography_details_activity_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.edit_radiography_information_action:
			Toast.makeText(getApplicationContext(), "merge", Toast.LENGTH_SHORT)
					.show();
			return true;

		default:
			return false;
		}
	}
}
