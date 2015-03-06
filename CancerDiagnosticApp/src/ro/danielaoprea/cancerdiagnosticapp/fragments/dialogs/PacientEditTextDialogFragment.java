package ro.danielaoprea.cancerdiagnosticapp.fragments.dialogs;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.beans.Categorizable;
import ro.danielaoprea.cancerdiagnosticapp.beans.Pacient;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class PacientEditTextDialogFragment extends
		AbstractEditTextDialogFragment {

	private EditText cnpEditText;
	private EditText addressEditText;
	private String cnp;
	private String address;

	public static PacientEditTextDialogFragment newInstance() {
		PacientEditTextDialogFragment fragment = new PacientEditTextDialogFragment();
		return fragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		cnpEditText = (EditText) dialogLayout.findViewById(R.id.cnp_edit_text);
		cnpEditText.setVisibility(View.VISIBLE);
		addressEditText = (EditText) dialogLayout
				.findViewById(R.id.address_edit_text);
		addressEditText.setVisibility(View.VISIBLE);
		return createDialog();
	}

	@Override
	public void setStringsFromEditText() {
		super.setStringsFromEditText();
		cnp = cnpEditText.getText().toString();
		address = addressEditText.getText().toString();

	}

	@Override
	public boolean areStringsValid() {
		if (!TextUtils.isEmpty(cnp) && !TextUtils.isEmpty(address)) {
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
		return getString(R.string.insert_new_pacient);
	}

	@Override
	public Categorizable createObjectFromFields() {
		Pacient pacient = new Pacient(cnp, name, address,
				phone, email, -1);
		return pacient;
	}

}
