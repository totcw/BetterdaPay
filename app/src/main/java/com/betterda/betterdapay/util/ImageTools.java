package com.betterda.betterdapay.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片处理工具类 Tools for handler picture
 *
 * @author Ryan.Tang
 *
 */
public final class ImageTools {

	/**
	 * Transfer drawable to bitmap
	 *
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();

		Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
				: Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * Bitmap to drawable
	 *
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		return new BitmapDrawable(bitmap);
	}

	/**
	 * Input stream to bitmap
	 *
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static Bitmap inputStreamToBitmap(InputStream inputStream)
			throws Exception {
		return BitmapFactory.decodeStream(inputStream);
	}

	/**
	 * Byte transfer to bitmap
	 *
	 * @param byteArray
	 * @return
	 */
	public static Bitmap byteToBitmap(byte[] byteArray) {
		if (byteArray.length != 0) {
			return BitmapFactory
					.decodeByteArray(byteArray, 0, byteArray.length);
		} else {
			return null;
		}
	}

	/**
	 * Byte transfer to drawable
	 *
	 * @param byteArray
	 * @return
	 */
	public static Drawable byteToDrawable(byte[] byteArray) {
		ByteArrayInputStream ins = null;
		if (byteArray != null) {
			ins = new ByteArrayInputStream(byteArray);
		}
		return Drawable.createFromStream(ins, null);
	}

	/**
	 * Bitmap transfer to bytes
	 *
	 * @return
	 */
	public static byte[] bitmapToBytes(Bitmap bm) {
		byte[] bytes = null;
		if (bm != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
			bytes = baos.toByteArray();
		}
		return bytes;
	}

	/**
	 * Drawable transfer to bytes
	 *
	 * @param drawable
	 * @return
	 */
	public static byte[] drawableToBytes(Drawable drawable) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		Bitmap bitmap = bitmapDrawable.getBitmap();
		byte[] bytes = bitmapToBytes(bitmap);
		;
		return bytes;
	}

	/**
	 * Base64 to byte[] //
	 */
	// public static byte[] base64ToBytes(String base64) throws IOException {
	// byte[] bytes = Base64.decode(base64);
	// return bytes;
	// }
	//
	// /**
	// * Byte[] to base64
	// */
	// public static String bytesTobase64(byte[] bytes) {
	// String base64 = Base64.encode(bytes);
	// return base64;
	// }

	/**
	 * Create reflection images
	 *
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
				h / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
				Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	/**
	 * 获取圆角图片 Get rounded corner images
	 *
	 * @param bitmap
	 * @param roundPx
	 *            5 10
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, w, h);
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 利用画布重新画图 Resize the bitmap
	 *
	 * @param bitmap

	 *            的高
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	/**
	 * 改变图片的尺寸 Resize the drawable
	 *
	 * @param drawable
	 * @param w
	 * @param h
	 * @return
	 */
	public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap oldbmp = drawableToBitmap(drawable);
		Matrix matrix = new Matrix();
		float sx = ((float) w / width);
		float sy = ((float) h / height);
		matrix.postScale(sx, sy);
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
				matrix, true);
		return new BitmapDrawable(newbmp);
	}

	/**
	 * Get images from SD card by path and the name of image
	 *
	 * @param photoName
	 * @return
	 */
	public static Bitmap getPhotoFromSDCard(String path, String photoName) {
		Bitmap photoBitmap = BitmapFactory.decodeFile(path + "/" + photoName
				+ ".jpg");
		if (photoBitmap == null) {
			return null;
		} else {
			return photoBitmap;
		}
	}

	/**
	 * 检查sd卡是否存在 Check the SD card
	 *
	 * @return
	 */
	public static boolean checkSDCardAvailable() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 判断是否有图片 Get image from SD card by path and the name of image
	 *
	 * @return
	 */
	public static boolean findPhotoFromSDCard(String path, String photoName) {
		boolean flag = false;

		if (checkSDCardAvailable()) {
			File dir = new File(path);
			if (dir.exists()) {
				File folders = new File(path);
				File photoFile[] = folders.listFiles();
				for (int i = 0; i < photoFile.length; i++) {
					String fileName = photoFile[i].getName().split("\\.")[0];
					if (fileName.equals(photoName)) {
						flag = true;
					}
				}
			} else {
				flag = false;
			}
			// File file = new File(path + "/" + photoName + ".jpg" );
			// if (file.exists()) {
			// flag = true;
			// }else {
			// flag = false;
			// }

		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * Save image to the SD card
	 *
	 * @param photoBitmap
	 * @param photoName
	 * @param path
	 */
	public static void savePhotoToSDCard(Bitmap photoBitmap, String path,
										 String photoName) {
		if (checkSDCardAvailable()) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File photoFile = new File(path, photoName + ".png");
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					// png无损压缩 第二参数没影响
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
							fileOutputStream)) {

						fileOutputStream.flush();
					}
				}
			} catch (FileNotFoundException e) {
				photoFile.delete();
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				e.printStackTrace();
			} finally {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除路径下的所有图片 Delete the image from SD card
	 *
	 * @param path
	 *            file:///sdcard/temp.jpg
	 */
	public static void deleteAllPhoto(String path) {
		if (checkSDCardAvailable()) {
			File folder = new File(path);
			File[] files = folder.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}
	}

	/**
	 * 根据路径和名字删除图片
	 *
	 * @param path
	 * @param fileName
	 */
	public static void deletePhotoAtPathAndName(String path, String fileName) {
		if (checkSDCardAvailable()) {
			File folder = new File(path);
			File[] files = folder.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().split("\\.")[0].equals(fileName)) {
					files[i].delete();
				}
			}
		}
	}

	// 将URI转换为真实路径
	@SuppressWarnings("deprecation")
	public static String changeUriToPath(Uri uri, Activity activity) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor actualImageCursor = activity.managedQuery(uri, proj, null, null,
				null);

		int actual_image_column_index = actualImageCursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		actualImageCursor.moveToFirst();
		String currentImagePath = actualImageCursor
				.getString(actual_image_column_index);
		return currentImagePath;
	}

	/**
	 * 根据图片的宽高 缩放 图片  返回的一般在20Kb作用
	 * @param path 图片的路径
	 *
	 * @return
	 */
	public static Bitmap scacleToBitmap(String path, Activity activity
	) {
		int scale = 1;
		BitmapFactory.Options op = new BitmapFactory.Options();
		op.inJustDecodeBounds=true;//设置为true 不真正加载，不需要内存，只是为了获取图片的宽，高
		BitmapFactory.decodeFile(path, op);//此时加载不会暂用内存，只是获取op
		scale=computeSampleSize(op, 320, 320*480);
		op.inJustDecodeBounds = false;
		op.inSampleSize = scale;// 这个数字越大,图片大小越小. 宽高为原来的4分一，大小16分之1	
		return BitmapFactory.decodeFile(path, op);
	}


	/**
	 * 获取压缩的比例
	 * @param options
	 * @param minSideLength
	 * @param maxNumOfPixels
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
										int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
												int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math
				.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}




	/**
	 * 根据内容生成二维码bitmap
	 *
	 * @param content
	 * @return
	 */
	public  static Bitmap generateQRCode(String content,Context context) {
		if (TextUtils.isEmpty(content)) {
			return null;
		}
		try {
			QRCodeWriter writer = new QRCodeWriter();
			// MultiFormatWriter writer = new MultiFormatWriter();
			BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, UtilMethod.dip2px(context, 400), UtilMethod.dip2px(context, 400));
			return bitMatrix2Bitmap(matrix);
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成bitmap
	 *
	 * @param matrix
	 * @return
	 */
	public  static Bitmap bitMatrix2Bitmap(BitMatrix matrix) {
		int w = matrix.getWidth();
		int h = matrix.getHeight();
		int[] rawData = new int[w * h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int color = Color.WHITE;
				if (matrix.get(i, j)) {
					color = Color.BLACK;
				}
				rawData[i + (j * w)] = color;
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
		bitmap.setPixels(rawData, 0, w, 0, 0, w, h);
		return bitmap;
	}



	/**
	 * 裁剪
	 *
	 * @param uri 图片的uri
	 */
	public static void cropImg(Uri uri, Activity activity, int width, int height, int width2, int height2) {

		try {
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, "image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", width); //X方向上的比例
			intent.putExtra("aspectY", height);
			intent.putExtra("outputX", width2); //裁剪区的宽
			intent.putExtra("outputY", height2);
			intent.putExtra("scale", true); //是否保留比例
			intent.putExtra("return-data", false);
			intent.putExtra(MediaStore.EXTRA_OUTPUT,getUri(activity));
			activity.startActivityForResult(intent, 5);
		} catch (Exception e
				) {

		}

	}

	/**
	 * 拍照
	 */
	public static void paizhao(Activity activity, int requestcode) {
		try {
			if (!ImageTools.checkSDCardAvailable()) {
				UtilMethod.Toast(activity, "内存卡错误,请检查您的内存卡");
				return;
			}
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// 这样就将文件的存储方式和uri指定到了Camera应用中 指定了这个 data一般为空了
			intent.putExtra(MediaStore.EXTRA_OUTPUT,getUri(activity));

			activity.startActivityForResult(intent, requestcode);


		} catch (Exception e) {
			UtilMethod.Toast(activity, "没有拍照权限");
		}

	}


	/**
	 * 选取图片
	 */
	public static void choosePicture(Activity activity, int requestcode) {
		try {
			if (!ImageTools.checkSDCardAvailable()) {
				UtilMethod.Toast(activity, "内存卡错误,请检查您的内存卡");
				return;
			}
			Intent intentphoto = new Intent(Intent.ACTION_PICK);
			intentphoto.setDataAndType(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					Constants.IMAGE_UNSPECIFIED);
			activity.startActivityForResult(intentphoto, requestcode);

		} catch (Exception e) {
			UtilMethod.Toast(activity, "选取照片失败");
		}

	}

	/**
	 * 在7.0以上使用FileProvider获取Uri
	 */
	public static   Uri getUri(Activity activity) {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
			return FileProvider.getUriForFile(activity, activity.getPackageName(),new File(Constants.PHOTOPATHFORCROP));
		} else {
			return Uri.fromFile(new File(Constants.PHOTOPATHFORCROP));
		}
	}
}
