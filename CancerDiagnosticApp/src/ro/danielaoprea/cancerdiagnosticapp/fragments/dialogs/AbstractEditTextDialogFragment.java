package ro.danielaoprea.cancerdiagnosticapp.fragments.dialogs;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.beans.Categorizable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public abstract class AbstractEditTextDialogFragment extends DialogFragment {

	protected OnEditTextDialogListener onEditTextDialogListener;
	protected Categorizable categoty;
	protected EditText nameEditText;
	protected EditText phoneEditText;
	protected EditText emailDetailsEditText;
	protected View dialogLayout;
	protected String name;
	protected String phone;
	protected String email;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		dialogLayout = inflater.inflate(R.layout.inser_dialog_layout, null);
		nameEditText = (EditText) dialogLayout
				.findViewById(R.id.name_edit_text);

		phoneEditText = (EditText) dialogLayout
				.findViewById(R.id.phone_edit_text);

		emailDetailsEditText = (EditText) dialogLayout
				.findViewById(R.id.email_edit_text);

		return createDialog();
	}

	public Dialog createDialog() {
		return new AlertDialog.Builder(getActivity())
				.setTitle(getTitle())
				.setPositiveButton(getPositiveButtonTextId(),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								setStringsFromEditText();
								if (areStringsValid()) {
									onEditTextDialogListener
											.onPositiveButtonClicked(
													createObjectFromFields(),
													getTag());
									dialog.dismiss();
								} else {
									Toast.makeText(
											getActivity(),
											getString(R.string.insert_information),
											Toast.LENGTH_SHORT).show();
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

	public void setStringsFromEditText() {
		name = nameEditText.getText().toString();
		phone = phoneEditText.getText().toString();
		email = emailDetailsEditText.getText().toString();
		if (!isValidEmail(email)) {
			Toast.makeText(getActivity(), getString(R.string.invalid_email),
					Toast.LENGTH_SHORT).show();
			return;
		}
	}

	public abstract Categorizable createObjectFromFields();

	public boolean areStringsValid() {

		if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)
				&& !TextUtils.isEmpty(email) && areStringsValid()) {
			return true;
		}
		return false;
	}

	public abstract int getPositiveButtonTextId();

	public abstract String getTitle();

	public interface OnEditTextDialogListener {
		public void onPositiveButtonClicked(Categorizable category, String tag);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		onEditTextDialogListener = (OnEditTextDialogListener) activity;
	}

	public final static boolean isValidEmail(CharSequence target) {
		if (TextUtils.isEmpty(target)) {
			return false;
		} else {
			return Patterns.EMAIL_ADDRESS.matcher(target).matches();
		}
	}
}
