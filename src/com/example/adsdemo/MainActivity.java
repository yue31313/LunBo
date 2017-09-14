package com.example.adsdemo;

import java.util.Timer;
import java.util.TimerTask;

import com.example.adsdemo.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	FragmentAdapter mAdapter;
	ViewPager mPager;

	TextView title;
	TextView text;
	
	Timer mTimer;
	TimerTask mTask;
	int pageIndex = 1;
	boolean isTaskRun;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("AdsDemo by QQ472950043");

		/* ���� */
		title = (TextView) findViewById(R.id.title);

		// ����ViewPager
		mPager = (ViewPager) findViewById(R.id.viewpager);
		mAdapter = new FragmentAdapter(getSupportFragmentManager());
		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			/* �����ֶ�����ʱ��λ�� */
			@Override
			public void onPageSelected(int position) {
				pageIndex = position;
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			/* state: 0���У�1�ǻ����У�2������� */
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				System.out.println("state:" + state);
				if (state == 0 && !isTaskRun) {
					setCurrentItem();
					startTask();
				} else if (state == 1 && isTaskRun)
					stopTask();
			}
		});

		/* �������� */
		text = (TextView) findViewById(R.id.text);
		text.setText("���������Լ�������");
	}
	
	/**
	 * ������ʱ����
	 */
	private void startTask() {
		// TODO Auto-generated method stub
		isTaskRun = true;
		mTimer = new Timer();
		mTask = new TimerTask() {
			@Override
			public void run() {
				pageIndex++;
				mHandler.sendEmptyMessage(0);
			}
		};
		mTimer.schedule(mTask, 2 * 1000, 2 * 1000);// ���������Զ��л���ʱ�䣬��λ�Ǻ��룬2*1000��ʾ2��
	}

	// ����EmptyMessage(0)
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			setCurrentItem();
		}
	};

	/**
	 * ����Page���л��߼�
	 */
	private void setCurrentItem() {
		if (pageIndex == 0) {
			pageIndex = 3;
		} else if (pageIndex == 4) {
			pageIndex = 1;
		}
		mPager.setCurrentItem(pageIndex, false);// ȡ������
	}

	/**
	 * ֹͣ��ʱ����
	 */
	private void stopTask() {
		// TODO Auto-generated method stub
		isTaskRun = false;
		mTimer.cancel();
	}

	public void onResume() {
		super.onResume();
		setCurrentItem();
		startTask();
	}
    
	@Override
	public void onPause() {
		super.onPause();
		stopTask();
	}
}
