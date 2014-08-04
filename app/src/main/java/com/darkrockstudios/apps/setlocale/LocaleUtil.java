package com.darkrockstudios.apps.setlocale;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;

import java.util.Locale;

/**
 * Created by Adam on 8/3/2014.
 */
public class LocaleUtil
{
	public static boolean setLocale( final Locale locale, final Context context )
	{
		boolean success = false;
		try
		{
			Class<?> activityManagerNative = Class.forName( "android.app.ActivityManagerNative" );
			Object am = activityManagerNative.getMethod( "getDefault" ).invoke( activityManagerNative );
			Object config = am.getClass().getMethod( "getConfiguration" ).invoke( am );
			config.getClass().getDeclaredField( "locale" ).set( config, locale );
			config.getClass().getDeclaredField( "userSetLocale" ).setBoolean( config, true );

			am.getClass().getMethod( "updateConfiguration", android.content.res.Configuration.class ).invoke( am, config );
			success = true;
		}
		catch( final Exception e )
		{
			showJellyBeanDialog( context );
		}

		return success;
	}

	public static void showJellyBeanDialog( final Context context )
	{
		AlertDialog.Builder builder = new AlertDialog.Builder( context );
		builder.setTitle( context.getString( R.string.permission_dialog_title ) );
		builder.setMessage( Html.fromHtml( context.getString( R.string.permission_dialog_message ) ) );
		builder.setPositiveButton( R.string.permission_dialog_button, null );
		builder.create().show();
	}
}
