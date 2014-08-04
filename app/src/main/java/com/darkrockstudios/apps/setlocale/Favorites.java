package com.darkrockstudios.apps.setlocale;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Adam on 8/3/2014.
 */
public class Favorites
{
	private static final String KEY_FAVORITES = "FAVORITES";

	private static SharedPreferences getStorage( final Context context )
	{
		return PreferenceManager.getDefaultSharedPreferences( context );
	}

	public static void addFavorite( final Locale locale, final Context context )
	{
		final SharedPreferences prefs = getStorage( context );

		final Set<String> favorites = prefs.getStringSet( KEY_FAVORITES, new HashSet<String>() );
		favorites.add( locale.toString().toLowerCase( Locale.US ) );
		prefs.edit().putStringSet( KEY_FAVORITES, favorites ).commit();
	}

	public static void removeFavorite( final Locale locale, final Context context )
	{
		final SharedPreferences prefs = getStorage( context );

		final Set<String> favorites = prefs.getStringSet( KEY_FAVORITES, new HashSet<String>() );
		favorites.remove( locale.toString().toLowerCase( Locale.US ) );
		prefs.edit().putStringSet( KEY_FAVORITES, favorites ).commit();
	}

	public static List<Locale> getFavorites( final Context context )
	{
		final SharedPreferences prefs = getStorage( context );

		final Set<String> favoritesStrings = prefs.getStringSet( KEY_FAVORITES, new HashSet<String>() );
		final List<Locale> favorites = new ArrayList<>( favoritesStrings.size() );

		for( final String favoritesString : favoritesStrings )
		{
			final Locale locale = new Locale( favoritesString );
			favorites.add( locale );
		}

		return favorites;
	}
}
