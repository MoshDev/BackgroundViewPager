package com.infusionapps.widget;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infusionapps.backgroundviewpager.R;

public class TestActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_test);

		BackgroundViewPager viewPager = (BackgroundViewPager) findViewById(R.id.flowViewPager1);
		viewPager.setAdapter(new FragmentStatePagerAdapter(
				getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return 3;
			}

			@Override
			public Fragment getItem(int arg0) {
				return MyFragment.newInstance(arg0 + 1);
			}
		});
	}

	public static class MyFragment extends Fragment {

		public static MyFragment newInstance(int index) {
			Bundle bundle = new Bundle();
			bundle.putInt("index", index);

			MyFragment fragment = new MyFragment();
			fragment.setArguments(bundle);
			return fragment;
		}

		int index;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			index = getArguments().getInt("index");
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View v = inflater.inflate(R.layout.fragment_test, container, false);

			TextView textView = (TextView) v.findViewById(R.id.textView1);
			textView.setText("" + index);

			return v;
		}

	}
}
