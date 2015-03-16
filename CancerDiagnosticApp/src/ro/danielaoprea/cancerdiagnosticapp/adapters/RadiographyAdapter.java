package ro.danielaoprea.cancerdiagnosticapp.adapters;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.database.tables.RadiographyTableUtils;
import ro.danielaoprea.cancerdiagnosticapp.utils.Helper;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RadiographyAdapter extends CursorAdapter {

	private LayoutInflater inflater;

	public RadiographyAdapter(Context context, Cursor c) {
		super(context, c, false);
		inflater = LayoutInflater.from(context);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View v = inflater
				.inflate(R.layout.radiography_grid_item, parent, false);
		return v;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ImageView radiographyImage = (ImageView) view
				.findViewById(R.id.radiography_image_view);
		TextView date = (TextView) view
				.findViewById(R.id.radiography_date_text_view);
		date.setText(Helper.Date.getDateDayMonthyear(cursor.getLong(cursor
				.getColumnIndex(RadiographyTableUtils.DATE))));
		radiographyImage.setImageBitmap(Helper.Picture.getBitmapFromPath(
				cursor.getString(cursor
						.getColumnIndex(RadiographyTableUtils.PATH)),
				context.getResources().getDimensionPixelSize(
						R.dimen.radiography_grid_view_dimes),
				context.getResources().getDimensionPixelSize(
						R.dimen.radiography_grid_view_dimes)));
	}
}
