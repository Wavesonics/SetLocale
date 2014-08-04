package com.darkrockstudios.apps.setlocale.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.darkrockstudios.apps.setlocale.R;

import java.util.Locale;

/**
 * Created by Adam on 8/3/2014.
 */
public class FavoritesAdapter extends LocaleAdapter
{
	public FavoritesAdapter( final Context context, final Locale[] locales )
	{
		super( context, locales );
	}

	@Override
	public int getCount()
	{
		final int numLocales = super.getCount();
		return (numLocales > 0 ? numLocales : 1);
	}

	@Override
	public View getView( final int position, final View convertView, final ViewGroup parent )
	{
		final View view;
		if( super.getCount() > 0 )
		{
			view = super.getView( position, convertView, parent );
		}
		else
		{
			view = m_inflater.inflate( R.layout.empty_favorites_listitem, parent, false );
		}

		return view;
	}

	@Override
	public boolean areAllItemsEnabled()
	{
		return false;
	}

	@Override
	public boolean isEnabled( final int position )
	{
		return super.getCount() > 0;
	}

	@Override
	public int getViewTypeCount()
	{
		return 2;
	}

	@Override
	public int getItemViewType( final int position )
	{
		return super.getCount() > 0 ? 0 : 1;
	}
}
