package ro.danielaoprea.cancerdiagnosticapp.imageprocessing;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.utils.Helper;
import android.content.Context;
import android.graphics.Bitmap;

public class CalculateHog {

	private Context context;

	public CalculateHog(Context context) {
		this.context = context;
	}

	public Bitmap computeHog(String path) {

		Bitmap bitmap = Helper.Picture.getBitmapFromPath(
				path,
				context.getResources().getDimensionPixelSize(
						R.dimen.radiography_grid_view_dimes),
				context.getResources().getDimensionPixelSize(
						R.dimen.radiography_grid_view_dimes));

		Mat img = new Mat();
		Utils.bitmapToMat(bitmap, img);
		Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
		Utils.matToBitmap(img, bitmap);
		return bitmap;

	}
}
