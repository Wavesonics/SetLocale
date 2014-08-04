package com.darkrockstudios.apps.setlocale;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

import com.darkrockstudios.apps.setlocale.adapters.SectionsPagerAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LocaleSelectActivity extends Activity
{
	private SectionsPagerAdapter m_sectionsPagerAdapter;

	@InjectView(R.id.pager)
	ViewPager m_viewPager;

	@InjectView(R.id.pager_titles)
	PagerTabStrip m_pagerTitles;

	@Override
	protected void onCreate( final Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_locale_select );
		ButterKnife.inject( this );

		m_sectionsPagerAdapter = new SectionsPagerAdapter( getFragmentManager(), this );

		m_viewPager.setAdapter( m_sectionsPagerAdapter );
	}
}
