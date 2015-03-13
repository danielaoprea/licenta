package ro.danielaoprea.cancerdiagnosticapp.fragments.dialogs;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.beans.Categorizable;
import ro.danielaoprea.cancerdiagnosticapp.beans.User;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

public class UpdateDoctorEditTextDialogFragment extends
		DoctorEditTextDialogFragment {

	private static final String ARG_DOCTOR_ID = "arg_doctor_id";
	private static final String ARG_NAME = "arg_name";
	private static final String ARG_USERNAME = "arg_username";
	private static final String ARG_PHONE = "arg_phone";
	private static final String ARG_EMAIL = "arg_email";

	public static UpdateDoctorEditTextDialogFragment newInstance(long doctorId,
			String name, String username, String phone, String email) {
		UpdateDoctorEditTextDialogFragment fragment = new UpdateDoctorEditTextDialogFragment();
		Bundle args = new Bundle();
		args.putLong(ARG_DOCTOR_ID, doctorId);
		args.putString(ARG_NAME, name);
		args.putString(ARG_USERNAME, username);
		args.putString(ARG_PHONE, phone);
		args.putString(ARG_EMAIL, email);
		fragment.setArguments(args);
		return fragment;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		passwordEditText.setVisibility(View.GONE);
		nameEditText.setText(getArguments().getString(ARG_NAME));
		phoneEditText.setText(getArguments().getString(ARG_PHONE));
		usernameEditText.setText(getArguments().getString(ARG_USERNAME));
		emailDetailsEditText.setText(getArguments().getString(ARG_EMAIL));
		return createDialog();
	}

	@Override
	public boolean areStringsValid() {
		if (!TextUtils.isEmpty(username)) {
			return true;

		}
		return false;
	}

	@Override
	public int getPositiveButtonTextId() {
		return R.string.update;
	}

	@Override
	public Categorizable createObjectFromFields() {
		User user = new User(getArguments().getLong(ARG_DOCTOR_ID), name, null,
				username, email, phone, User.DOCTOR);
		return user;
	}

	@Override
	public String getTitle() {
		return getString(R.string.update_doctor);
	}
}
