package ro.danielaoprea.cancerdiagnosticapp.fragments;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.activities.RadiographyDetailsActivity;
import ro.danielaoprea.cancerdiagnosticapp.utils.Constants;
import ro.danielaoprea.cancerdiagnosticapp.utils.Helper;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FullRadiographyFragment extends Fragment implements
		OnClickListener {

	private static final String ARG_PATH = "arg_path";
	private static final String ARG_DIAGNOSTIC = "arg_diagnostic";
	private static final String ARG_DETAILS = "arg_details";
	private static final String ARG_DATE = "arg_date";
	private ImageView radioImageView;
	private TextView detailsTextView;
	private TextView diagnosticTextView;
	private LinearLayout detailsLinearLayout;
	private TextView seeMoreTextView;

	public static FullRadiographyFragment newInstance(String path,
			String diagnostic, String details, long date) {
		FullRadiographyFragment fragment = new FullRadiographyFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PATH, path);
		args.putString(ARG_DIAGNOSTIC, diagnostic);
		args.putString(ARG_DETAILS, details);
		args.putLong(ARG_DATE, date);
		fragment.setArguments(args);
		return fragment;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.full_radiography_item_layout,
				container, false);
		radioImageView = (ImageView) v
				.findViewById(R.id.full_radiography_imagine_view);
		detailsTextView = (TextView) v
				.findViewById(R.id.full_radiography_details);
		diagnosticTextView = (TextView) v
				.findViewById(R.id.full_radiography_diagnostic);
		detailsLinearLayout = (LinearLayout) v
				.findViewById(R.id.radiography_details_layout);
		seeMoreTextView = (TextView) v.findViewById(R.id.full_radiography_more);
		Point size = getDisplaySize();
		int width = size.x;
		int height = size.y;
		radioImageView.setImageBitmap(Helper.Picture.getBitmapFromPath(
				getArguments().getString(ARG_PATH), width, height));
		detailsTextView.setText(getString(R.string.details) + " : "
				+ getArguments().getString(ARG_DETAILS));
		diagnosticTextView.setText(getString(R.string.diagnostic) + " : "
				+ getArguments().getString(ARG_DIAGNOSTIC));
		radioImageView.setOnClickListener(this);
		seeMoreTextView.setOnClickListener(this);
		return v;
	}

	private Point getDisplaySize() {
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}

	private void setDetailsVisibility() {

		if (detailsLinearLayout.isShown()) {
			detailsLinearLayout.setVisibility(View.GONE);
			getActivity().getActionBar().hide();
		} else {
			detailsLinearLayout.setVisibility(View.VISIBLE);
			getActivity().getActionBar().show();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.full_radiography_imagine_view:
			setDetailsVisibility();
			break;

		case R.id.full_radiography_more:
			Intent intent = new Intent(getActivity(),
					RadiographyDetailsActivity.class);
			intent.putExtra(Constants.IntentExtras.EXTRA_RADIOGRAPHY_DATE,
					getArguments().getLong(ARG_DATE));
			intent.putExtra(Constants.IntentExtras.EXTRA_RADIOGRAPHY_DETAILS,
					getArguments().getString(ARG_DETAILS));
			intent.putExtra(
					Constants.IntentExtras.EXTRA_RADIOGRAPHY_DIAGNOSTIC,
					getArguments().getString(ARG_DIAGNOSTIC));
			startActivity(intent);
			break;
		default:
			break;
		}

	}
}
