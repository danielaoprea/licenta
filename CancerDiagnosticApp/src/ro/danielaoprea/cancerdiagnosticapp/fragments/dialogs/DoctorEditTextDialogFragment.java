package ro.danielaoprea.cancerdiagnosticapp.fragments.dialogs;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.beans.Categorizable;
import ro.danielaoprea.cancerdiagnosticapp.beans.User;
import ro.danielaoprea.cancerdiagnosticapp.utils.Helper;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class DoctorEditTextDialogFragment extends
		AbstractEditTextDialogFragment {

	protected EditText usernameEditText;
	protected EditText passwordEditText;
	protected String username;
	protected String password;

	public static DoctorEditTextDialogFragment newInstance() {
		DoctorEditTextDialogFragment fragment = new DoctorEditTextDialogFragment();
		return fragment;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		passwordEditText = (EditText) dialogLayout
				.findViewById(R.id.password_edit_text);
		passwordEditText.setVisibility(View.VISIBLE);

		usernameEditText = (EditText) dialogLayout
				.findViewById(R.id.username_edit_text);
		usernameEditText.setVisibility(View.VISIBLE);
		return createDialog();
	}

	@Override
	public void setStringsFromEditText() {
		super.setStringsFromEditText();
		username = usernameEditText.getText().toString();
		password = passwordEditText.getText().toString();

	}

	@Override
	public boolean areStringsValid() {
		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
			return true;
		}
		return false;
	}

	@Override
	public int getPositiveButtonTextId() {
		return R.string.ok;
	}

	@Override
	public String getTitle() {
		return getString(R.string.insert_new_doctor);
	}

	@Override
	public Categorizable createObjectFromFields() {
		User user = new User(-1, name, Helper.SecurityUtils.sha1(password),
				username, email, phone, User.DOCTOR);
		return user;
	}

}
