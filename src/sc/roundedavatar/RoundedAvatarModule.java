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
	 * Returns a rounded bitmap from a bitmap at location url
	 * @param url		The location of the original bitmap.
	 * @param width		The requested width of the returned bitmap (which is also the height).
	 * @return			Bitmap as a Blob.
	 */
	@Kroll.method
	public TiBlob getRoundedAvatar(String url, int width) { //, int height) {
		debugMsg("getRoundedAvatar(" + url + ", " + Integer.toString(width) + ")");
		if (width <= 0) {
			throw new IllegalArgumentException("Parameter 'width' has to be greater than zero!");
		}
		Bitmap bitmap = getBitmapFromURL(url);
		if (width != bitmap.getWidth()) {
			// Requested a different size than the bitmap's size
			return roundBitmap(resizeBitmap(bitmap, width, width));
		} else {
			return roundBitmap(bitmap);
		}
	}
	
	/**
	 * Returns a bitmap with rounded corners as a Blob. 
	 * The bitmap's height and width are assumed to be equal.
	 * @param bitmap	The bitmap to modify.
	 * @return			Bitmap as a Blob.
	 */
	private TiBlob roundBitmap(Bitmap bitmap) {
		if (bitmap != null) {
			RoundedAvatarDrawable avatar = new RoundedAvatarDrawable(bitmap);
	
		    Bitmap out = Bitmap.createBitmap(avatar.getIntrinsicWidth(), avatar.getIntrinsicHeight(), Config.ARGB_8888);
		    Canvas canvas = new Canvas(out); 
		    avatar.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		    avatar.draw(canvas);
		    avatar = null;
		    
		    return TiBlob.blobFromImage(out);
		} else {
			return null;
		}
	}

	/**
	 * Returns a Bitmap from a URL
	 * @param src	The source URL
	 * @return	the fetched Bitmap
	 */
	private static Bitmap getBitmapFromURL(String src) {
	    try {
	        URL url = new URL(src);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	/**
	 * Resizes a bitmap to a specified size.
	 * @param src		The bitmap to resize.
	 * @param width		new width.
	 * @param height	new height.
	 * @return			a new Bitmap with of the specified size.
	 */
	private static Bitmap resizeBitmap(Bitmap src, int width, int height) {
		return Bitmap.createScaledBitmap(src, width, height, false);
	}

}
