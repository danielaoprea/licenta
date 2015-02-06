package ro.softvision.cancerdiagnosticapp.adapters;

import ro.softvision.cancerdiagnosticapp.R;
import ro.softvision.cancerdiagnosticapp.database.tables.DoctorTableUtils;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DoctorListAdapter extends CursorAdapter {

	private LayoutInflater inflater;

	public DoctorListAdapter(Context context, Cursor c) {
		super(context, c, false);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor c) {
		TextView name = (TextView) view.findViewById(R.id.doctor_name);
		name.setText(c.getString(c.getColumnIndex(DoctorTableUtils.NAME)));
		TextView code = (TextView) view.findViewById(R.id.doctor_cod_text_view);
		code.setText("Authentiocation code: "
				+ c.getLong(c.getColumnIndex(DoctorTableUtils.ROW_ID)));
		TextView phone = (TextView) view.findViewById(R.id.doctor_phone);
		phone.setText(c.getString(c.getColumnIndex(DoctorTableUtils.PHONE)));
	}

	@Override
	public View newView(Context context, Cursor c, ViewGroup view) {
		View v = inflater.inflate(R.layout.doctor_list_item, null);
		return v;
	}

}
