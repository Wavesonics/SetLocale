package com.darkrockstudios.apps.setlocale.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.darkrockstudios.apps.setlocale.BusProvider;
import com.darkrockstudios.apps.setlocale.Favorites;
import com.darkrockstudios.apps.setlocale.FavoritesUpdateEvent;
import com.darkrockstudios.apps.setlocale.LocaleUtil;
import com.darkrockstudios.apps.setlocale.R;
import com.darkrockstudios.apps.setlocale.adapters.LocaleAdapter;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Adam on 8/3/2014.
 */
public class LocaleListFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener
{
	private LocaleAdapter m_adapter;

	@InjectView(R.id.SELECT_locale_search)
	EditText m_searchView;

	@InjectView(R.id.SELECT_locale_list)
	ListView m_localeListView;

	public static LocaleListFragment newInstance()
	{
		return new LocaleListFragment();
	}

	@Override
	public void onCreate( final Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );

		m_adapter = new LocaleAdapter( getActivity(), Locale.getAvailableLocales() );
	}

	@Override
	public View onCreateView( final LayoutInflater inflater,
	                          final ViewGroup container,
	                          final Bundle savedInstanceState )
	{
		final View rootView = inflater.inflate( R.layout.fragment_locale_select, container, false );
		ButterKnife.inject( this, rootView );

		m_localeListView.setAdapter( m_adapter );
		m_localeListView.setOnItemClickListener( this );
		m_localeListView.setOnItemLongClickListener( this );

		m_searchView.addTextChangedListener( new SearchListener() );

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
		Favorites.addFavorite( locale, getActivity() );
		Toast.makeText( getActivity(), R.string.toast_locale_favorite_added, Toast.LENGTH_SHORT ).show();

		BusProvider.bus.post( new FavoritesUpdateEvent() );

		return true;
	}

	@OnClick(R.id.clear_button)
	public void onClearClick()
	{
		if( m_searchView != null )
		{
			m_searchView.setText( "" );
		}
	}

	private class SearchListener implements TextWatcher
	{
		@Override
		public void beforeTextChanged( final CharSequence charSequence, final int i, final int i2, final int i3 )
		{

		}

		@Override
		public void onTextChanged( final CharSequence charSequence, final int i, final int i2, final int i3 )
		{
			m_adapter.getFilter().filter( charSequence );
		}

		@Override
		public void afterTextChanged( final Editable editable )
		{

		}
	}
}
