package ro.danielaoprea.cancerdiagnosticapp.fragments.dialogs;

import ro.danielaoprea.cancerdiagnosticapp.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditRadiographyDialogFragment extends DialogFragment {

	private static final String ARG_RADIO_ID = "radio_id";
	private static final String ARG_TITLE = "title";
	private static final String ARG_DATE = "date";
	private static final String ARG_DIAGNOSTIC = "diagnostic";
	private static final String ARG_DETAILS = "details";
	private OnEditRadiographyDialogListener onEditRadiographyDialogListener;

	public static EditRadiographyDialogFragment newInstance(String title,
			long radioId, String date, String diagnostic, String details) {
		EditRadiographyDialogFragment fragment = new EditRadiographyDialogFragment();

		Bundle args = new Bundle();
		args.putLong(ARG_RADIO_ID, radioId);
		args.putString(ARG_TITLE, title);
		args.putString(ARG_DATE, date);
		args.putString(ARG_DIAGNOSTIC, diagnostic);
		args.putString(ARG_DETAILS, details);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View dialogLayout = inflater.inflate(
				R.layout.edit_radiography_details_dialog_layout, null);

		final TextView dateTextView = (TextView) dialogLayout
				.findViewById(R.id.radiography_date_text_view);
		dateTextView.setText(getString(R.string.date) + " : "
				+ getArguments().getString(ARG_DATE));

		final TextView diagnosticTextView = (TextView) dialogLayout
				.findViewById(R.id.radiography_diagnostic_text_view);
		diagnosticTextView.setText(getString(R.string.diagnostic) + " : "
				+ getArguments().getString(ARG_DIAGNOSTIC));

		final EditText detailsEditText = (EditText) dialogLayout
				.findViewById(R.id.radiography_details_edit_text);
		detailsEditText.setText(getArguments().getString(ARG_DETAILS));
		return new AlertDialog.Builder(getActivity())
				.setTitle(getArguments().getString(ARG_TITLE))
				.setPositiveButton(getString(R.string.ok),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String details = detailsEditText.getText()
										.toString();
								if (!details.equals(getArguments().getString(
										ARG_DETAILS))) {
									onEditRadiographyDialogListener
											.onPositiveButtonClicked(
													getArguments().getLong(
															ARG_RADIO_ID),
													details);
								}

							}
						})
				.setNegativeButton(getString(R.string.cancel),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();

							}
						}).setView(dialogLayout).create();
	}

	public interface OnEditRadiographyDialogListener {
		public void onPositiveButtonClicked(long radioId, String details);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		onEditRadiographyDialogListener = (OnEditRadiographyDialogListener) activity;
	}
}
