package org.bean.jandan.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.bean.jandan.R;
import org.bean.jandan.fragment.BaseRecycleViewNetFragment;

public class MainActivity extends BaseColorActivity implements AdapterView.OnItemClickListener {

    private DrawerLayout mDrawerLayout;
    private ListView mListView;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    protected void findViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerLayout.setScrimColor(getResources().getColor(R.color.drawer_layout_scrim_color));
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
    }

    @Override
    protected DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initMenu() {
        MenuAdapter adapter = new MenuAdapter();
        mListView.setAdapter(adapter);
        mListView.performItemClick(mListView.getChildAt(0), 0, 0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MainMenu menu = (MainMenu) parent.getAdapter().getItem(position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, menu.getFragment());
        transaction.commit();
        mDrawerLayout.closeDrawers();
        getToolbar().setTitle(menu.mLabelRes);
    }

    @Override
    public void onDoubleClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.main_tool_bar) {
            BaseRecycleViewNetFragment fragment = (BaseRecycleViewNetFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            fragment.goToTop();
        }
    }

    class MenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return MainMenu.values().length;
        }

        @Override
        public Object getItem(int position) {
            return MainMenu.values()[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.menu_item_layout, parent, false);
            }
            MainMenu menuItem = (MainMenu) getItem(position);
            ((TextView) convertView).setText(getString(menuItem.mLabelRes));
            return convertView;
        }
    }
}
