package ro.danielaoprea.cancerdiagnosticapp.fragments;

import java.util.ArrayList;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.activities.FullRadiographyActivity;
import ro.danielaoprea.cancerdiagnosticapp.activities.PacientDetailsActivity;
import ro.danielaoprea.cancerdiagnosticapp.adapters.RadiographyAdapter;
import ro.danielaoprea.cancerdiagnosticapp.beans.Radiography;
import ro.danielaoprea.cancerdiagnosticapp.database.tables.RadiographyTableUtils;
import ro.danielaoprea.cancerdiagnosticapp.imageprocessing.ComputeHog;
import ro.danielaoprea.cancerdiagnosticapp.providers.CancerDiagnosticContentProvider;
import ro.danielaoprea.cancerdiagnosticapp.utils.Constants;
import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class PacientRadiographysFragment extends Fragment implements
		LoaderCallbacks<Cursor>, OnItemClickListener, MultiChoiceModeListener {

	private static final String TAG = PacientRadiographysFragment.class
			.getSimpleName();
	private static final int ID_LOADER_RADIOGRAPHYS = 3;
	private static final int PICK_IMAGE = 1;
	private static final String ARG_CNP = "arg_cnp";

	private GridView radiographysGridView;
	private RadiographyAdapter radiographyAdapter;

	public static PacientRadiographysFragment newInstance(String cnp) {
		PacientRadiographysFragment fragment = new PacientRadiographysFragment();
		Bundle args = new Bundle();
		args.putString(ARG_CNP, cnp);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.pacient_radiography_fragment_layout,
				container, false);
		radiographysGridView = (GridView) v
				.findViewById(R.id.radiography_grid_view);
		radiographysGridView.setEmptyView(v.findViewById(R.id.empty));
		radiographysGridView.setOnItemClickListener(this);
		radiographyAdapter = new RadiographyAdapter(getActivity(), null);
		radiographysGridView.setAdapter(radiographyAdapter);
		radiographysGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
		radiographysGridView.setMultiChoiceModeListener(this);
		getActivity().getLoaderManager().initLoader(ID_LOADER_RADIOGRAPHYS,
				getArguments(), this);
		setHasOptionsMenu(true);
		return v;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.radiography_fragment_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_radiography_action:
			openGallery();
			return true;

		default:
			return false;
		}
	}

	private void openGallery() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(
				Intent.createChooser(intent, "Complete action using"),
				PICK_IMAGE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == PICK_IMAGE) {
				insertRadiography(getRealPathFromURI(data.getData()));
			}
		}
	}

	public String getRealPathFromURI(Uri contentUri) {

		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = getActivity().getContentResolver().query(contentUri,
				proj, null, null, null);
		int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();

		return cursor.getString(column_index);
	}

	private void insertRadiography(String seletedImage) {
		Radiography radio = new Radiography(0, seletedImage,
				System.currentTimeMillis(), String.valueOf(false), null,
				getArguments().getString(ARG_CNP));
		new InsertRadiographyTask().execute(radio);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case ID_LOADER_RADIOGRAPHYS:
			return new CursorLoader(getActivity(),
					CancerDiagnosticContentProvider.RADIOGRAPHY_URI, null,
					RadiographyTableUtils.CNP_PACIENT + " =? ",
					new String[] { args.getString(ARG_CNP) },
					RadiographyTableUtils.DATE + " desc ");
		default:
			return null;
		}
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		radiographyAdapter.swapCursor(data);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		radiographyAdapter.swapCursor(null);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Cursor cursor = (Cursor) radiographyAdapter.getItem(position);
		Intent intent = new Intent(getActivity(), FullRadiographyActivity.class);
		intent.putExtra(Constants.IntentExtras.EXTRA_PACIENT_DETAILS_CNP,
				cursor.getString(cursor
						.getColumnIndex(RadiographyTableUtils.CNP_PACIENT)));
		intent.putExtra(Constants.IntentExtras.EXTRA_RADIOGRAPHY_ID, cursor
				.getLong(cursor.getColumnIndex(RadiographyTableUtils.ROW_ID)));
		startActivity(intent);

	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.radiography_fragment_cab_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete_radiography_action:
			new DeleteRadiographyTask().execute();
			return true;

		default:
			return false;
		}

	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		radiographyAdapter.setSelectedItemsIds(new ArrayList<Integer>());
	}

	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position,
			long id, boolean checked) {
		final int checkedCount = radiographysGridView.getCheckedItemCount();
		mode.setTitle(getString(R.string.selection, checkedCount));
		radiographyAdapter.toggleSelection(position);

	}

	private class InsertRadiographyTask extends
			AsyncTask<Radiography, Void, Void> {

		@Override
		protected Void doInBackground(Radiography... params) {
			getActivity().getContentResolver().insert(
					CancerDiagnosticContentProvider.RADIOGRAPHY_URI,
					RadiographyTableUtils.createContentValues(params[0]));
			return null;
		}

	}

	private class DeleteRadiographyTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			ArrayList<Integer> radioIds = radiographyAdapter.getSelectedIds();
			StringBuilder condition = new StringBuilder();
			for (int i = 0; i < radiographyAdapter.getSelectedCount(); i++) {
				Cursor cursor = (Cursor) radiographyAdapter.getItem(radioIds
						.get(i));
				long id = cursor.getLong(cursor
						.getColumnIndex(RadiographyTableUtils.ROW_ID));
				condition
						.append(RadiographyTableUtils.ROW_ID)
						.append("=")
						.append(id)
						.append((i == (radiographyAdapter.getSelectedCount() - 1) ? ""
								: " OR "));
			}
			getActivity().getContentResolver().delete(
					CancerDiagnosticContentProvider.RADIOGRAPHY_URI,
					condition.toString(), null);
			return null;
		}

	}
}
