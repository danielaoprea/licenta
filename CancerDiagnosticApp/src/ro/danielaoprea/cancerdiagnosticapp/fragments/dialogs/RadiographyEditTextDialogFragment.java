package ro.danielaoprea.cancerdiagnosticapp.fragments.dialogs;

import ro.danielaoprea.cancerdiagnosticapp.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RadiographyEditTextDialogFragment extends DialogFragment {

	public static RadiographyEditTextDialogFragment newInstance() {
		RadiographyEditTextDialogFragment fragment = new RadiographyEditTextDialogFragment();
		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View dialogLayout = inflater.inflate(
				R.layout.insert_radiography_dialog_layout, null);
		final ImageView radioImageView = (ImageView) dialogLayout
				.findViewById(R.id.chosed_radiography_image_view);
		final Button browseButton = (Button) dialogLayout
				.findViewById(R.id.browse_button);
		final EditText detailsEditText = (EditText) dialogLayout
				.findViewById(R.id.radiography_details);
		return new AlertDialog.Builder(getActivity())
				.setTitle(getString(R.string.insert_radiography))
				.setView(dialogLayout).create();
	}
}
