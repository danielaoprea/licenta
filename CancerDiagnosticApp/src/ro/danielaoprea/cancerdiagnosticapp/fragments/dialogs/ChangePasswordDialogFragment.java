package ro.danielaoprea.cancerdiagnosticapp.fragments.dialogs;

import ro.danielaoprea.cancerdiagnosticapp.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordDialogFragment extends DialogFragment {

	private static final String ARG_TITLE = "title";
	private OnChangePasswordDialogListener onChangePasswordDialogListener;

	public static ChangePasswordDialogFragment newInstance(String title) {
		ChangePasswordDialogFragment fragment = new ChangePasswordDialogFragment();
		Bundle args = new Bundle();
		args.putString(ARG_TITLE, title);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View dialogLayout = inflater.inflate(
				R.layout.change_password_dialog_layout, null);
		final EditText currentPasswordEditText = (EditText) dialogLayout
				.findViewById(R.id.current_password);
		final EditText newPasswordEditText = (EditText) dialogLayout
				.findViewById(R.id.new_password);
		final EditText confirmPasswordEditText = (EditText) dialogLayout
				.findViewById(R.id.confirm_password);

		return new AlertDialog.Builder(getActivity())
				.setTitle(getArguments().getString(ARG_TITLE))
				.setPositiveButton(R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								String currentPassword = currentPasswordEditText
										.getText().toString();
								String newPassword = newPasswordEditText
										.getText().toString();
								String confirmPassword = confirmPasswordEditText
										.getText().toString();
								if (!TextUtils.isEmpty(currentPassword)
										&& !TextUtils.isEmpty(newPassword)
										&& !TextUtils.isEmpty(confirmPassword)) {

									onChangePasswordDialogListener
											.onChangePasswordPositiveButtonClicked(
													currentPassword,
													newPassword,
													confirmPassword);
								} else {
									Toast.makeText(
											getActivity(),
											getString(R.string.insert_information),
											Toast.LENGTH_SHORT).show();
								}
								dialog.dismiss();

							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();

							}
						}).setView(dialogLayout).create();
	}

	public interface OnChangePasswordDialogListener {
		public void onChangePasswordPositiveButtonClicked(String currentPassword,
				String newPassword, String confirmPassword);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		onChangePasswordDialogListener = (OnChangePasswordDialogListener) activity;
	}
}
