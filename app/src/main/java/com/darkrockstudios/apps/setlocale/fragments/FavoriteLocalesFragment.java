package com.darkrockstudios.apps.setlocale.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.colintmiller.simplenosql.NoSQLEntity;
import com.colintmiller.simplenosql.RetrievalCallback;
import com.darkrockstudios.apps.setlocale.BusProvider;
import com.darkrockstudios.apps.setlocale.Favorites;
import com.darkrockstudios.apps.setlocale.FavoritesUpdateEvent;
import com.darkrockstudios.apps.setlocale.LocaleUtil;
import com.darkrockstudios.apps.setlocale.R;
import com.darkrockstudios.apps.setlocale.adapters.FavoritesAdapter;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Adam on 8/3/2014.
 */
public class FavoriteLocalesFragment extends Fragment implements AdapterView.OnItemClickListener,
                                                                 AdapterView.OnItemLongClickListener,
                                                                 RetrievalCallback<Locale>
{
	private FavoritesAdapter m_adapter;

	@InjectView(R.id.FAVORITE_locale_list)
	ListView m_localeListView;

	public static FavoriteLocalesFragment newInstance()
	{
		return new FavoriteLocalesFragment();
	}

	@Override
	public void onResume()
	{
		super.onResume();

		BusProvider.bus.register( this );
		Favorites.getFavorites( getActivity(), this );
	}

	@Override
	public void onPause()
	{
		super.onPause();

		BusProvider.bus.unregister( this );
	}

	@Override
	public View onCreateView( final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState )
	{
		View rootView = inflater.inflate( R.layout.fragment_favorite_locales, container, false );
		ButterKnife.inject( this, rootView );

		m_localeListView.setOnItemClickListener( this );
		m_localeListView.setOnItemLongClickListener( this );

		if( m_adapter != null )
		{
			m_localeListView.setAdapter( m_adapter );
		}

		return rootView;
	}

	@Override
	public void onItemClick( final AdapterView<?> adapterView, final View view, final int position, final long id )
	{
		Locale locale = m_adapter.getItem( position );
		if( LocaleUtil.setLocale( locale, getActivity() ) )
		{
			Toast.makeText( getActivity(), R.string.toast_locale_set, Toast.LENGTH_SHORT ).show();
		}
	}

	@Override
	public boolean onItemLongClick( final AdapterView<?> adapterView, final View view, final int position, final long id )
	{
		final Locale locale = m_adapter.getItem( position );
		Favorites.removeFavorite( locale, getActivity() );
		Toast.makeText( getActivity(), R.string.toast_locale_favorite_removed, Toast.LENGTH_SHORT ).show();

		Favorites.getFavorites( getActivity(), this );

		return true;
	}

	@Subscribe
	public void onFavoritesUpdate( final FavoritesUpdateEvent event )
	{
		if( isAdded() )
		{
			Favorites.getFavorites( getActivity(), this );
		}
	}

	@Override
	public void retrievedResults( final List<NoSQLEntity<Locale>> noSQLEntities )
	{
		if( isAdded() )
		{
			final List<Locale> favorites = new ArrayList<>();
			for( final NoSQLEntity<Locale> favoriteEntry : noSQLEntities )
			{
				favorites.add( favoriteEntry.getData() );
			}
			setLocales( favorites );
		}
	}

	private void setLocales( final List<Locale> favorites )
	{
		if( m_adapter == null )
		{
			m_adapter = new FavoritesAdapter( getActivity(), favorites.toArray( new Locale[ favorites.size() ] ) );
			if( m_localeListView != null )
			{
				m_localeListView.setAdapter( m_adapter );
			}
		}
		else
		{
			m_adapter.setLocales( favorites );
		}
		m_adapter.notifyDataSetChanged();
	}
}
