package com.darkrockstudios.apps.setlocale;

import android.content.Context;

import com.colintmiller.simplenosql.NoSQL;
import com.colintmiller.simplenosql.NoSQLEntity;
import com.colintmiller.simplenosql.RetrievalCallback;

import java.util.Locale;

/**
 * Created by Adam on 8/3/2014.
 */
public class Favorites
{
	private static final String BUCKET_FAVORITES = "FAVORITES";

	public static void addFavorite( final Locale locale, final Context context )
	{
		NoSQLEntity<Locale> entity = new NoSQLEntity<>( BUCKET_FAVORITES, locale.hashCode() + "" );
		entity.setData( locale );

		NoSQL.with( context ).using( Locale.class ).save( entity );
	}

	public static void removeFavorite( final Locale locale, final Context context )
	{
		NoSQL.with( context ).using( Locale.class )
		     .bucketId( BUCKET_FAVORITES )
		     .entityId( locale.hashCode() + "" )
		     .delete();
	}

	public static void getFavorites( final Context context, final RetrievalCallback<Locale> callback )
	{
		NoSQL.with( context ).using( Locale.class )
		     .bucketId( BUCKET_FAVORITES )
		     .retrieve( callback );
	}
}
