package com.darkrockstudios.apps.setlocale.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.darkrockstudios.apps.setlocale.R;
import com.darkrockstudios.apps.setlocale.fragments.FavoriteLocalesFragment;
import com.darkrockstudios.apps.setlocale.fragments.LocaleListFragment;

import java.util.Locale;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter
{
	private final Context m_context;

	public SectionsPagerAdapter( final FragmentManager fm, final Context context )
	{
		super( fm );

		m_context = context;
	}

	@Override
	public Fragment getItem( final int position )
	{
		final Fragment fragment;
		switch( position )
		{
			case 0:
				fragment = LocaleListFragment.newInstance();
				break;
			case 1:
				fragment = FavoriteLocalesFragment.newInstance();
				break;
			default:
				fragment = null;
				break;
		}
		return fragment;
	}

	@Override
	public int getCount()
	{
		return 2;
	}

	@Override
	public CharSequence getPageTitle( final int position )
	{
		final String title;

		final Locale l = Locale.getDefault();
		switch( position )
		{
			case 0:
				title = m_context.getString( R.string.title_section_locale_list ).toUpperCase( l );
				break;
			case 1:
				title = m_context.getString( R.string.title_section_favorite_locales ).toUpperCase( l );
				break;
			default:
				title = null;
				break;
		}
		return title;
	}
}
