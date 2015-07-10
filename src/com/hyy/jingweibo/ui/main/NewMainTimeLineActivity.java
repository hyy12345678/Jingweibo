package com.hyy.jingweibo.ui.main;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.widget.Toast;

import com.hyy.jingweibo.R;
import com.hyy.jingweibo.support.application.GlobalContext;
import com.hyy.jingweibo.support.keeper.AccountInfoKeeper;
import com.hyy.jingweibo.ui.fragment.NavigationDrawerFragment;
import com.hyy.jingweibo.ui.fragment.NavigationDrawerFragment.NavigationDrawerCallbacks;
import com.hyy.jingweibo.ui.fragment.OldFrendsTimeLineFragment;
import com.hyy.jingweibo.ui.interfaces.AbstractAppActivity;

public class NewMainTimeLineActivity extends AbstractAppActivity implements
		NavigationDrawerCallbacks {

	private static final String TAG = "NewMainActivity";

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */

	public static Intent newIntent() {
		return new Intent(GlobalContext.getInstance(),
				NewMainTimeLineActivity.class);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintimeline);

		/* 初始化slideMenu相关事项 */
		initSlideMenuStuff();

		/* 加载微博消息Fragment */
		initMessageStuff();

	}

	private void initMessageStuff() {
		// TODO Auto-generated method stub

		Fragment fragment = OldFrendsTimeLineFragment.newInstance();
		getFragmentManager().beginTransaction().add(R.id.container, fragment)
				.commit();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	/***
	 * 初始化SlideMenu相关事项
	 */
	private void initSlideMenuStuff() {

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

	}

	/**
	 * 重载当侧边栏点击触发事件
	 */
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// TODO Auto-generated method stub
		// 后续添加根据侧边栏过滤内容

		switch (position) {
		case 0:
			Toast.makeText(GlobalContext.getInstance(), "press FriendsTimeLine line",
					Toast.LENGTH_SHORT).show();
			break;
		case 1:
			Toast.makeText(GlobalContext.getInstance(), "press logout line",
					Toast.LENGTH_SHORT).show();
			
			Intent start = AccountActivity.newIntent();
			start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(start);
			finish();
			
			break;
		case 2:
			Toast.makeText(GlobalContext.getInstance(), "press setting line",
					Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

	/***
	 * store the last action bar （include the screen title）
	 */
	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(AccountInfoKeeper.readAuthInfoToken(
				GlobalContext.getInstance()).getAutherName());
	}

	/***
	 * 重载actionBar菜单
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(
					R.menu.actionbar_menu_maintimelineactivity, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);

	}

}
