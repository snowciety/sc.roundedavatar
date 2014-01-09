/*
 * Copyright 2013 Snowciety
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sc.roundedavatar;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.titanium.TiBlob;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

@Kroll.module(name="RoundedAvatar", id="sc.roundedavatar")
public class RoundedAvatarModule extends KrollModule {
	
	private final static String TAG = "RoundedAvatarModule";
	
	public RoundedAvatarModule(){
		super();
	}
	
	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app)
	{
		// put module init code that needs to run when the application is created
	}
	
	@Override
	public void onResume(Activity activity) {
		super.onResume(activity);
	}
	
	@Override
	public void onDestroy(Activity activity) {
		super.onDestroy(activity);
	}
	
	private static void debugMsg(String msg)	{
		Log.debug(TAG, msg);
	}
	
	
	/**
	 * Returns a rounded bitmap from a bitmap passed as a blob from Titanium.
	 * @param imageBlob	The image as a blob.
	 * @param width		The requested width of the returned bitmap (which is also the height).
	 * @return			THe rounded image as a blob.
	 */
	@Kroll.method
	public TiBlob getRoundedAvatar(TiBlob imageBlob, int width) {
		if (width <= 0) {
			throw new IllegalArgumentException("Parameter 'width' has to be greater than zero!");
		}
		debugMsg("Received blob with size "+Integer.toString(imageBlob.getBytes().length));
		Bitmap image = BitmapFactory.decodeByteArray(imageBlob.getBytes(), 0, imageBlob.getBytes().length);
		if (image != null) {
			return getRoundedBitmapAsBlob(resizeBitmap(image, width, width));
		} else {
			throw new IllegalArgumentException("The passed blob could not be converted to a bitmap!");
		}
	}
	
	private static TiBlob getRoundedBitmapAsBlob(Bitmap bitmap) {
	    final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
	    final Canvas canvas = new Canvas(output);
	 
	    final int color = Color.RED;
	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	    final RectF rectF = new RectF(rect);
	 
	    paint.setAntiAlias(true);
	    canvas.drawARGB(0, 0, 0, 0);
	    paint.setColor(color);
	    canvas.drawOval(rectF, paint);
	 
	    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);
	 
	    bitmap.recycle();
	 
	    return TiBlob.blobFromImage(output);
	  }
	
	/**
	 * Resizes a bitmap to a specified size.
	 * @param bitmap	The bitmap to resize.
	 * @param width		new width.
	 * @param height	new height.
	 * @return			a new Bitmap with of the specified size.
	 */
	private static Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
		return Bitmap.createScaledBitmap(bitmap, width, height, false);
	}

}
