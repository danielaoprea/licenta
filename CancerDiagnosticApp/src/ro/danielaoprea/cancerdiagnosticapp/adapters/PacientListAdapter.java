package ro.danielaoprea.cancerdiagnosticapp.adapters;

import java.util.ArrayList;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.database.tables.PacientTableUtils;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PacientListAdapter extends CursorAdapter {

	private LayoutInflater inflater;
	private ArrayList<Integer> selectedItemsIds = new ArrayList<>();

	public PacientListAdapter(Context context, Cursor c) {
		super(context, c, false);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView name = (TextView) view.findViewById(R.id.pacient_name);
		TextView cnp = (TextView) view.findViewById(R.id.pacient_cnp);
		TextView address = (TextView) view.findViewById(R.id.pacient_address);
		TextView phone = (TextView) view.findViewById(R.id.pacient_phone);
		TextView email = (TextView) view.findViewById(R.id.pacient_email);

		name.setText(cursor.getString(cursor
				.getColumnIndex(PacientTableUtils.NAME)));
		cnp.setText("CNP: "
				+ String.valueOf(cursor.getLong(cursor
						.getColumnIndex(PacientTableUtils.CNP))));
		address.setText("Address: "
				+ cursor.getString(cursor
						.getColumnIndex(PacientTableUtils.ADDRESS)));
		phone.setText("Phone: "
				+ cursor.getString(cursor
						.getColumnIndex(PacientTableUtils.PHONE)));
		email.setText("Email: "
				+ cursor.getString(cursor
						.getColumnIndex(PacientTableUtils.EMAIL)));
		Linkify.addLinks(email, Linkify.EMAIL_ADDRESSES);

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		View v = inflater.inflate(R.layout.pacient_list_item, viewGroup, false);
		return v;
	}

	public void toggleSelection(Integer position) {
		if (!selectedItemsIds.contains(position)) {
			selectedItemsIds.add(position);
		} else {
			selectedItemsIds.remove(position);
		}
		notifyDataSetChanged();
	}

	public int getSelectedCount() {
		return selectedItemsIds.size();
	}

	public ArrayList<Integer> getSelectedIds() {
		return selectedItemsIds;
	}

	public void setSelectedItemsIds(ArrayList<Integer> selectedItemsIds) {
		this.selectedItemsIds = selectedItemsIds;
	}
}
