package com.darkrockstudios.apps.setlocale;

import android.os.Build;

/**
 * Created by Adam on 8/3/2014.
 */
public class OsUtils
{
	public static boolean hasJellyBeanMr1()
	{
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
	}
}
