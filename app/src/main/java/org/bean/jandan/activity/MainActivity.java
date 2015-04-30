package org.bean.jandan.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.bean.jandan.R;

public class MainActivity extends BaseColorActivity implements AdapterView.OnItemClickListener {

    private DrawerLayout mDrawerLayout;
    private ListView mListView;
    private FrameLayout mFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initMenu();
    }

    private void findViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mFragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        mDrawerLayout.setScrimColor(0x00000000);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, R.string.app_name, R.string.app_name);
        toggle.syncState();
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
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
        mListView.performItemClick(mListView, 0, 0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MainMenu menu = (MainMenu) parent.getAdapter().getItem(position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, menu.getFragment());
        transaction.commit();
        mDrawerLayout.closeDrawers();
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
