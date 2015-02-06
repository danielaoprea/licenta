package ro.softvision.cancerdiagnosticapp.fragments;

import ro.softvision.cancerdiagnosticapp.R;
import ro.softvision.cancerdiagnosticapp.adapters.DoctorListAdapter;
import ro.softvision.cancerdiagnosticapp.providers.CancerDiagnosticContentProvider;
import android.app.Activity;
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
import android.widget.Toast;

public class DoctorListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private static final String TAG = DoctorListFragment.class.getSimpleName();
	private static final int ID_LOADER_DOCTORS = 1;
	private OnDoctorSelectedListener onDoctorSelectedListener;

	public static DoctorListFragment newInstance() {
		DoctorListFragment fragment = new DoctorListFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.doctor_list_fragment_layout,
				container, false);

		DoctorListAdapter doctorAdapter = new DoctorListAdapter(getActivity(),
				null);
		setListAdapter(doctorAdapter);
		getLoaderManager().initLoader(ID_LOADER_DOCTORS, null, this);
		return view;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case ID_LOADER_DOCTORS:
			CursorLoader doctorsCursor = new CursorLoader(getActivity(),
					CancerDiagnosticContentProvider.DOCTOR_URI, null, null,
					null, null);
			return doctorsCursor;

		default:
			break;
		}
		return null;
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
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		onDoctorSelectedListener.onDoctorSelected(id);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		onDoctorSelectedListener = (OnDoctorSelectedListener) activity;
	}

	public interface OnDoctorSelectedListener {
		public void onDoctorSelected(long doctorId);
	}
}
