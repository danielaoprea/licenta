package ro.danielaoprea.cancerdiagnosticapp.adapters;

import java.util.ArrayList;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.database.tables.UserTableUtils;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DoctorListAdapter extends CursorAdapter {

	private LayoutInflater inflater;
	private ArrayList<Integer> selectedItemsIds = new ArrayList<>();

	public DoctorListAdapter(Context context, Cursor c) {
		super(context, c, false);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View v, Context context, Cursor cursor) {
		TextView name = (TextView) v.findViewById(R.id.doctor_name);
		TextView username = (TextView) v.findViewById(R.id.doctor_username);
		TextView phone = (TextView) v.findViewById(R.id.doctor_phone);
		TextView email = (TextView) v.findViewById(R.id.doctor_email);

		name.setText(cursor.getString(cursor
				.getColumnIndex(UserTableUtils.NAME)));
		username.setText(context.getString(R.string.username)
				+ " : "
				+ cursor.getString(cursor
						.getColumnIndex(UserTableUtils.USERNAME)));
		phone.setText(context.getString(R.string.phone_) + " "
				+ cursor.getString(cursor.getColumnIndex(UserTableUtils.PHONE)));
		email.setText(context.getString(R.string.email_) + " "
				+ cursor.getString(cursor.getColumnIndex(UserTableUtils.EMAIL)));
		Linkify.addLinks(email, Linkify.EMAIL_ADDRESSES);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup view) {
		View v = inflater.inflate(R.layout.doctor_list_item, view, false);
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
