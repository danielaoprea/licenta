package ro.danielaoprea.cancerdiagnosticapp.imageprocessing;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;

import ro.danielaoprea.cancerdiagnosticapp.R;
import ro.danielaoprea.cancerdiagnosticapp.utils.Helper;
import ro.danielaoprea.cancerdiagnosticapp.utils.Logger;
import android.content.Context;
import android.graphics.Bitmap;

public class ComputeHog {

	private Context context;

	public ComputeHog(Context context) {

		this.context = context;
	}

	public Bitmap extractFeatures(String path) {
		Bitmap bitmap = Helper.Picture.getBitmapFromPath(
				path,
				context.getResources().getDimensionPixelSize(
						R.dimen.radiography_grid_view_dimes),
				context.getResources().getDimensionPixelSize(
						R.dimen.radiography_grid_view_dimes));

		Mat img = new Mat();
		Utils.bitmapToMat(bitmap, img);

		MatOfFloat descriptors = new MatOfFloat(0);
		Size winStrinde = new Size(1, 1);
		Size padding = new Size(0, 0);
		MatOfPoint locations = new MatOfPoint();
		HOGDescriptor hog = new HOGDescriptor();

		Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2GRAY);
		// hog.compute(img, descriptors, winStrinde, padding, locations);
		Utils.matToBitmap(img, bitmap);
		return bitmap;
	}
}
