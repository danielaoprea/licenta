package ro.danielaoprea.cancerdiagnosticapp.adapters;

import java.util.ArrayList;

import ro.danielaoprea.cancerdiagnosticapp.beans.Radiography;
import ro.danielaoprea.cancerdiagnosticapp.fragments.FullRadiographyFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

public class FullRadiographyPagerAdapter extends FragmentStatePagerAdapter {

	private ArrayList<Radiography> radios = new ArrayList<>();

	public FullRadiographyPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		Radiography radio = radios.get(position);
		return FullRadiographyFragment.newInstance(radio.getPath(),
				radio.getDiagnostic(), radio.getDetails(), radio.getDate());
	}

	@Override
	public int getCount() {
		return radios.size();
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	public ArrayList<Radiography> getRadiographyList() {
		return radios;
	}

	public void setRadiographyList(ArrayList<Radiography> radioList) {
		this.radios = radioList;
	}
}
