package ro.softvision.cancerdiagnosticapp.fragments;

import ro.softvision.cancerdiagnosticapp.R;
import ro.softvision.cancerdiagnosticapp.adapters.PacientListAdapter;
import ro.softvision.cancerdiagnosticapp.database.tables.PacientTableUtils;
import ro.softvision.cancerdiagnosticapp.providers.CancerDiagnosticContentProvider;
import android.app.Fragment;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PacientListFragment extends Fragment implements
		LoaderCallbacks<Cursor> {

	private static final int ID_LOADER_PACIENTS = 2;
	private static final String ARG_DOCTOR_ID = "arg_doctor_id";
	private ListView pacientListView;
	private PacientListAdapter pacientAdapater;

	public static PacientListFragment newInstance(long doctorId) {
		PacientListFragment fragment = new PacientListFragment();
		Bundle args = new Bundle();
		args.putLong(ARG_DOCTOR_ID, doctorId);
		fragment.setArguments(args);
		return fragment;
	}

	public static PacientListFragment newInstance() {
		PacientListFragment fragment = new PacientListFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.pacient_list_fragment_layout,
				container, false);

		pacientListView = (ListView) v.findViewById(R.id.pacient_list_view);
		pacientAdapater = new PacientListAdapter(getActivity(), null);
		pacientListView.setAdapter(pacientAdapater);

		getLoaderManager().initLoader(ID_LOADER_PACIENTS, getArguments(), this);
		return v;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case ID_LOADER_PACIENTS:
			CursorLoader pacientsCursor = new CursorLoader(getActivity(),
					CancerDiagnosticContentProvider.PACIENT_URI, null,
					PacientTableUtils.ID_DOCTOR + "=?",
					new String[] { String.valueOf(args.get(ARG_DOCTOR_ID)) },
					null);

			return pacientsCursor;

		default:
			break;
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		pacientAdapater.swapCursor(data);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		pacientAdapater.swapCursor(null);

	}

}
