package ro.danielaoprea.cancerdiagnosticapp.activities;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.fragments.PacientDetailsFragment;
import ro.danielaoprea.cancerdiagnosticapp.fragments.PacientRadiographysFragment;
import ro.danielaoprea.cancerdiagnosticapp.utils.Constants;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class PacientDetailsActivity extends Activity {

	private String cnp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pacient_details_activity_layout);

		FragmentTransaction transaction;
		cnp = getIntent().getExtras().getString(Constants.IntentExtras.EXTRA_CNP);
		PacientDetailsFragment detailsFragment = PacientDetailsFragment
				.newInstance(cnp);
		transaction = getFragmentManager().beginTransaction();
		transaction.add(R.id.pacient_details_layout, detailsFragment)
				.commit();

		PacientRadiographysFragment radiographysFragment = PacientRadiographysFragment
				.newInstance(cnp);
		transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.pacient_radiograpys_layout,
				radiographysFragment).commit();
	}
}
