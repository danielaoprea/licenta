package ro.softvision.cancerdiagnosticapp.adapters;

import ro.softvision.cancerdiagnosticapp.R;
import ro.softvision.cancerdiagnosticapp.database.tables.PacientTableUtils;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PacientListAdapter extends CursorAdapter {

	private LayoutInflater inflater;

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

		name.setText(cursor.getString(cursor
				.getColumnIndex(PacientTableUtils.NAME)));
		cnp.setText(String.valueOf(cursor.getLong(cursor
				.getColumnIndex(PacientTableUtils.CNP))));
		address.setText(cursor.getString(cursor
				.getColumnIndex(PacientTableUtils.ADDRESS)));
		phone.setText(cursor.getString(cursor
				.getColumnIndex(PacientTableUtils.PHONE)));

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		View v = inflater.inflate(R.layout.pacient_list_item, null);
		return v;
	}

}
