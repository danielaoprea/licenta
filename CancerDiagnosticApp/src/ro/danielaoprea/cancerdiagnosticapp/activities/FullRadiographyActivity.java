package ro.danielaoprea.cancerdiagnosticapp.activities;

import java.util.ArrayList;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.adapters.FullRadiographyPagerAdapter;
import ro.danielaoprea.cancerdiagnosticapp.beans.Radiography;
import ro.danielaoprea.cancerdiagnosticapp.database.tables.RadiographyTableUtils;
import ro.danielaoprea.cancerdiagnosticapp.providers.CancerDiagnosticContentProvider;
import ro.danielaoprea.cancerdiagnosticapp.utils.Constants;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class FullRadiographyActivity extends Activity implements
		LoaderCallbacks<Cursor>, OnPageChangeListener {

	private static final int ID_LOADER_RADIOGRAPHY = 1;
	private static final String ARG_PACIENT_CNP = "arg_pacient_cnp";
	private ViewPager fullRadiographyViewPager;
	private FullRadiographyPagerAdapter fullRadiographyPagerAdapter;
	private long currentRadiographyId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_radiography_layout);
		Bundle args = new Bundle();
		args.putString(
				ARG_PACIENT_CNP,
				getIntent().getExtras().getString(
						Constants.IntentExtras.EXTRA_PACIENT_DETAILS_CNP));
		currentRadiographyId = getIntent().getExtras().getLong(
				Constants.IntentExtras.EXTRA_RADIOGRAPHY_ID);
		fullRadiographyViewPager = (ViewPager) findViewById(R.id.radiography_view_pager);
		fullRadiographyPagerAdapter = new FullRadiographyPagerAdapter(
				getFragmentManager());
		fullRadiographyViewPager.setAdapter(fullRadiographyPagerAdapter);
		fullRadiographyViewPager.setOnPageChangeListener(this);
		getLoaderManager().initLoader(ID_LOADER_RADIOGRAPHY, args, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		switch (id) {
		case ID_LOADER_RADIOGRAPHY:
			return new CursorLoader(getApplicationContext(),
					CancerDiagnosticContentProvider.RADIOGRAPHY_URI, null,
					RadiographyTableUtils.CNP_PACIENT + " =? ",
					new String[] { args.getString(ARG_PACIENT_CNP) }, null);

		default:
			return null;
		}

	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		switch (loader.getId()) {
		case ID_LOADER_RADIOGRAPHY:
			if (data.getCount() <= 0) {
				this.finish();
				return;
			}
			ArrayList<Radiography> radios = createRadiographyListFromCursor(data);
			fullRadiographyPagerAdapter.setRadiographyList(radios);
			fullRadiographyPagerAdapter.notifyDataSetChanged();
			int index = 0;
			for (int i = 0; i < radios.size(); i++) {
				if (currentRadiographyId == radios.get(i).getIdRadiography()) {
					index = i;
				}
			}
			fullRadiographyViewPager.setCurrentItem(index, true);
			break;

		default:
			break;
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		fullRadiographyPagerAdapter
				.setRadiographyList(new ArrayList<Radiography>());

	}

	private ArrayList<Radiography> createRadiographyListFromCursor(Cursor data) {
		ArrayList<Radiography> radios = new ArrayList<Radiography>();
		while (data.moveToNext()) {
			Radiography radio = new Radiography();
			radio.setIdRadiography(data.getLong(data
					.getColumnIndex(RadiographyTableUtils.ROW_ID)));
			radio.setPath(data.getString(data
					.getColumnIndex(RadiographyTableUtils.PATH)));
			radio.setDate(data.getLong(data
					.getColumnIndex(RadiographyTableUtils.DATE)));
			radio.setDiagnostic(data.getString(data
					.getColumnIndex(RadiographyTableUtils.DIAGNOSTIC)));
			radio.setDetails(data.getString(data
					.getColumnIndex(RadiographyTableUtils.DETAILS)));
			radio.setCnpPacient(data.getString(data
					.getColumnIndex(RadiographyTableUtils.CNP_PACIENT)));
			radios.add(radio);
		}
		return radios;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		currentRadiographyId = fullRadiographyPagerAdapter.getRadiographyList()
				.get(position).getIdRadiography();
	}

}
