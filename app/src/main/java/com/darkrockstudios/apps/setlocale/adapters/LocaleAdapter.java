package com.darkrockstudios.apps.setlocale.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.darkrockstudios.apps.setlocale.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Adam on 8/3/2014.
 */
public class LocaleAdapter extends ArrayAdapter<Locale> implements Filterable
{
	protected final LayoutInflater m_inflater;
	private         Locale[]       m_locales;

	public LocaleAdapter( final Context context, final Locale[] locales )
	{
		super( context, R.layout.locale_listitem );

		m_inflater = LayoutInflater.from( context );

		m_locales = locales;
		addAll( m_locales );
	}

	public void setLocales( final List<Locale> locales )
	{
		setLocales( locales.toArray( new Locale[ locales.size() ] ) );
	}

	public void setLocales( final Locale[] locales )
	{
		clear();
		m_locales = locales;
		addAll( m_locales );
	}

	@Override
	public View getView( final int position, final View convertView, final ViewGroup parent )
	{
		final View view;
		if( convertView == null )
		{
			view = m_inflater.inflate( R.layout.locale_listitem, parent, false );
		}
		else
		{
			view = convertView;
		}

		final Locale locale = getItem( position );

		final TextView text1 = (TextView) view.findViewById( R.id.textView1 );
		final TextView text2 = (TextView) view.findViewById( R.id.textView2 );

		text1.setText( locale.getDisplayName() );
		text2.setText( locale.toString() );

		return view;
	}

	@Override
	public Filter getFilter()
	{
		return new ExtensionFilter();
	}

	public class ExtensionFilter extends Filter
	{
		public ExtensionFilter()
		{

		}

		@Override
		protected Filter.FilterResults performFiltering( final CharSequence constraint )
		{
			FilterResults results = new FilterResults();
			ArrayList<Locale> filteredLocales = new ArrayList<>();

			final String lowerCaseConstraint = constraint.toString().toLowerCase();
			final String[] constraintComponents = lowerCaseConstraint.split( "\\s" );

			for( final Locale locale : m_locales )
			{
				if( locale.toString().toLowerCase().contains( lowerCaseConstraint ) ||
				    locale.getDisplayName().toLowerCase().contains( lowerCaseConstraint ) ||
				    matchComponents( locale, constraintComponents ) )
				{
					filteredLocales.add( locale );
				}
			}

			results.count = filteredLocales.size();
			results.values = filteredLocales;

			return results;
		}

		private boolean matchComponents( final Locale locale, final String[] constraintComponents )
		{
			final boolean isMatch;

			final String launguageCode = locale.getLanguage().toLowerCase();
			final String countryCode = locale.getCountry().toLowerCase();

			if( constraintComponents != null )
			{
				if( constraintComponents.length == 1 && !constraintComponents[ 0 ].isEmpty() )
				{
					isMatch = launguageCode.equals( constraintComponents[ 0 ] );
				}
				else if( constraintComponents.length == 2
				         && !constraintComponents[ 0 ].isEmpty()
				         && !constraintComponents[ 1 ].isEmpty() )
				{
					isMatch = launguageCode.equals( constraintComponents[ 0 ] )
					          && countryCode.equals( constraintComponents[ 1 ] );
				}
				else
				{
					isMatch = false;
				}
			}
			else
			{
				isMatch = false;
			}

			return isMatch;
		}

		@Override
		protected void publishResults( final CharSequence charSequence, final FilterResults filterResults )
		{
			clear();

			if( filterResults.count > 0 )
			{
				addAll( (List<Locale>) filterResults.values );
			}

			notifyDataSetChanged();
		}
	}
}
