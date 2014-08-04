package com.darkrockstudios.apps.setlocale.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.darkrockstudios.apps.setlocale.BusProvider;
import com.darkrockstudios.apps.setlocale.Favorites;
import com.darkrockstudios.apps.setlocale.FavoritesUpdateEvent;
import com.darkrockstudios.apps.setlocale.LocaleUtil;
import com.darkrockstudios.apps.setlocale.R;
import com.darkrockstudios.apps.setlocale.adapters.FavoritesAdapter;
import com.squareup.otto.Subscribe;

import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * Created by Adam on 8/3/2014.
 */
public class FavoriteLocalesFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
	private FavoritesAdapter m_adapter;

	@InjectView(R.id.FAVORITE_locale_list)
	ListView m_localeListView;

	public static FavoriteLocalesFragment newInstance()
	{
		return new FavoriteLocalesFragment();
	}

	@Override
	public void onCreate( final Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		final List<Locale> favorites = Favorites.getFavorites( getActivity() );
		m_adapter = new FavoritesAdapter( getActivity(), favorites.toArray( new Locale[ favorites.size() ] ) );
	}

	@Override
	public void onResume()
	{
		super.onResume();

		BusProvider.bus.register( this );
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

		m_localeListView.setAdapter( m_adapter );
		m_localeListView.setOnItemClickListener( this );
		m_localeListView.setOnItemLongClickListener( this );

		return rootView;
	}

	@Override
	public void onItemClick( final AdapterView<?> adapterView, final View view, final int position, final long id )
	{
		Locale locale = m_adapter.getItem( position );
		if( LocaleUtil.setLocale( locale, getActivity() ) )
		{
			Crouton.makeText( getActivity(), R.string.toast_locale_set, Style.CONFIRM ).show();
		}
	}

	@Override
	public boolean onItemLongClick( final AdapterView<?> adapterView, final View view, final int position, final long id )
	{
		final Locale locale = m_adapter.getItem( position );
		Favorites.removeFavorite( locale, getActivity() );
		Crouton.makeText( getActivity(), R.string.toast_locale_favorite_removed, Style.ALERT ).show();

		m_adapter.setLocales( Favorites.getFavorites( getActivity() ) );

		return true;
	}

	@Subscribe
	public void onFavoritesUpdate( final FavoritesUpdateEvent event )
	{
		m_adapter.setLocales( Favorites.getFavorites( getActivity() ) );
		m_adapter.notifyDataSetChanged();
	}
}
