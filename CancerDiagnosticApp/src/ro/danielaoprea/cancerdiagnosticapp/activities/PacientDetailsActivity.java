package ro.danielaoprea.cancerdiagnosticapp.activities;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.fragments.PacientDetailsFragment;
import ro.danielaoprea.cancerdiagnosticapp.fragments.PacientRadiographysFragment;
import ro.danielaoprea.cancerdiagnosticapp.utils.Constants;
import ro.danielaoprea.cancerdiagnosticapp.utils.Logger;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

public class PacientDetailsActivity extends Activity {

	private static final String TAG = PacientDetailsActivity.class
			.getSimpleName();
	private String cnp;
	private BaseLoaderCallback mOpenCVCallBack = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS: {
				Log.i("PacientDetailsActivity", "OpenCV loaded successfully");
				setContentView(R.layout.pacient_details_activity_layout);
				FragmentTransaction transaction;
				cnp = getIntent().getExtras().getString(
						Constants.IntentExtras.EXTRA_CNP);
				Logger.v(TAG, cnp);
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
				break;
			default: {
				super.onManagerConnected(status);
			}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Log.i(TAG, "Trying to load OpenCV library");
		if (!OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this,
				mOpenCVCallBack)) {
			Log.e(TAG, "Cannot connect to OpenCV Manager");
		} else {
			Log.i(TAG, "opencv successfully added");
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pacient_details_activity_layout);

	}
}
