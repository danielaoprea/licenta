package ro.danielaoprea.cancerdiagnosticapp.fragments;

import ro.danielaoprea.cancerdiagnosticapp.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class WelcomeFragment extends Fragment {

	public static final String EXTRA_IMAGE_ID = "image_id";
	private ImageView imageView;

	public static WelcomeFragment newInstance(int id) {
		WelcomeFragment fragment = new WelcomeFragment();
		Bundle args = new Bundle();
		args.putInt(EXTRA_IMAGE_ID, id);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.welcome_fragment_layout, container,
				false);
		imageView = (ImageView) v.findViewById(R.id.image);
		imageView.setImageResource(getArguments().getInt(EXTRA_IMAGE_ID));
		return v;
	}
}
