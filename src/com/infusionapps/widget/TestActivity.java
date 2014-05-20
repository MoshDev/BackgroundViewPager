package com.infusionapps.widget;

import android.graphics.Color;
import android.graphics.Typeface;
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

		test1();
		// test2();

	}

	private void test1() {
		setContentView(R.layout.activity_test_2);

		BackgroundViewPager2 viewPager = (BackgroundViewPager2) findViewById(R.id.viewPager2);
		viewPager.setViewPagerBackground(R.drawable.wide_bg);
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

	private void test2() {
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
			textView.setTextSize(200);
			textView.setTypeface(Typeface.MONOSPACE);
			textView.setTextColor(Color.BLACK);
			textView.setText("" + index);

			return v;
		}

	}
}
