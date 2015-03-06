package ro.danielaoprea.cancerdiagnosticapp.fragments;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.beans.Pacient;
import ro.danielaoprea.cancerdiagnosticapp.database.tables.PacientTableUtils;
import ro.danielaoprea.cancerdiagnosticapp.providers.CancerDiagnosticContentProvider;
import android.app.Fragment;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PacientDetailsFragment extends Fragment {

	private static final String ARG_CNP = "arg_cnp";
	private TextView nameTextView;
	private TextView cnpTextView;
	private TextView addressTextView;
	private TextView phoneTextView;
	private TextView emailTextView;

	public static PacientDetailsFragment newInstance(String cnp) {

		PacientDetailsFragment fragment = new PacientDetailsFragment();
		Bundle args = new Bundle();
		args.putString(ARG_CNP, cnp);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.pacient_details_fragment_layout,
				container, false);
		nameTextView = (TextView) v
				.findViewById(R.id.pacient_details_name_text_view);
		cnpTextView = (TextView) v
				.findViewById(R.id.pacient_details_cnp_text_view);
		addressTextView = (TextView) v
				.findViewById(R.id.pacient_details_address_text_view);
		phoneTextView = (TextView) v
				.findViewById(R.id.pacient_details_phone_text_view);
		emailTextView = (TextView) v
				.findViewById(R.id.pacient_details_email_text_view);
		new QueryPacientTask().execute(getArguments().getString(ARG_CNP));
		return v;
	}

	private class QueryPacientTask extends AsyncTask<String, Void, Pacient> {

		@Override
		protected Pacient doInBackground(String... params) {
			Cursor cursor = getActivity().getContentResolver().query(
					CancerDiagnosticContentProvider.PACIENT_URI, null,
					PacientTableUtils.CNP + " =?", new String[] { params[0] },
					null);
			if (cursor != null) {
				cursor.moveToFirst();
				Pacient pacient = new Pacient(params[0],
						cursor.getString(cursor
								.getColumnIndex(PacientTableUtils.NAME)),
						cursor.getString(cursor
								.getColumnIndex(PacientTableUtils.ADDRESS)),
						cursor.getString(cursor
								.getColumnIndex(PacientTableUtils.PHONE)),
						cursor.getString(cursor
								.getColumnIndex(PacientTableUtils.EMAIL)),
						cursor.getLong(cursor
								.getColumnIndex(PacientTableUtils.ID_DOCTOR)));
				return pacient;
			} else {
				return null;
			}
		}

		protected void onPostExecute(Pacient result) {
			if (result != null) {
				getActivity().getActionBar().setTitle(result.getName());
				nameTextView.setText(getString(R.string.name_) + " "
						+ result.getName());
				cnpTextView.setText(getString(R.string.cnp_) + " "
						+ String.valueOf(result.getCnp()));
				addressTextView.setText(getString(R.string.address_) + " "
						+ result.getAddress());
				phoneTextView.setText(getString(R.string.phone_) + " "
						+ result.getPhoneNumber());
				emailTextView.setText(getString(R.string.email_) + " "
						+ result.getEmail());
				Linkify.addLinks(emailTextView, Linkify.EMAIL_ADDRESSES);
			}
		}
	}

}
