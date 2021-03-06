package com.example.myprojectv_002;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myprojectv_002.ClassesObject.UserInfo;
import com.example.myprojectv_002.Fragments.fragments_navigation_item_groups;
import com.example.myprojectv_002.Fragments.fragments_navigation_item_tasks;
import com.example.myprojectv_002.Fragments.fragments_navigation_item_setting;
import com.example.myprojectv_002.Fragments.fragments_navigation_item_students;
import com.example.myprojectv_002.ResourceAdapter.NavigationListAdapter;
import com.example.myprojectv_002.ResourceItem.Navigation_Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_Navigation extends AppCompatActivity {

    public static Toolbar toolbar;
    public static FragmentManager fragmentManager;
    public static int i;
    public static ProgressDialog progressDialog;
    public static Map<String,String> mapTranslate;
    DrawerLayout drawerLayout;
    RelativeLayout drawerPane;
    ListView listViewNavigation;
    TextView textLogin;
    TextView textName;
    TextView textInstitution;
    List<Navigation_Item> listNavigationItems;
    List<Fragment> listFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        i=0;
        textLogin=(TextView)findViewById(R.id.profile_login);
        textName=(TextView)findViewById(R.id.profile_name);
        textInstitution=(TextView)findViewById(R.id.profile_institution);

        progressDialog=new ProgressDialog(Activity_Navigation.this);
        progressDialog.setMessage("Загрузка");
        progressDialog.setCanceledOnTouchOutside(false);

        mapTranslate=new HashMap<String,String>();
        mapTranslate.put("Student", "студента");
        mapTranslate.put("Group","группу");
        mapTranslate.put("Task","задачу");
        initToolbar();
        initNavigation();
    }
    private void initToolbar() {
        toolbar=(Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle(R.string.app_name);
    }

    private void initNavigation() {
        fragmentManager = getSupportFragmentManager();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.view_navigation_open, R.string.view_navigation_close);
        drawerLayout.setDrawerListener(toogle);
        toogle.syncState();

        drawerPane = (RelativeLayout) findViewById(R.id.drawer_pane);
        listViewNavigation = (ListView) findViewById(R.id.navigation_list);

        listNavigationItems = new ArrayList<Navigation_Item>();
        listNavigationItems.add(new Navigation_Item("Студенты", R.mipmap.ic_account));
        listNavigationItems.add(new Navigation_Item("Группы", R.mipmap.ic_account_multiple));
        listNavigationItems.add(new Navigation_Item("Задачи", R.mipmap.ic_book_multiple));
        listNavigationItems.add(new Navigation_Item("Настройки", R.mipmap.ic_settings));

        NavigationListAdapter navigationListAdapter = new NavigationListAdapter(getApplicationContext(), R.layout.item_navigation_list, listNavigationItems);
        listViewNavigation.setAdapter(navigationListAdapter);

        textLogin.setText(UserInfo.loginUser);
        textName.setText(UserInfo.nameUser);
        textInstitution.setText(UserInfo.institution);

        listFragments = new ArrayList<Fragment>();
        listFragments.add(new fragments_navigation_item_students());
        listFragments.add(new fragments_navigation_item_groups());
        listFragments.add(new fragments_navigation_item_tasks());
        listFragments.add(new fragments_navigation_item_setting());

        fragmentManager.beginTransaction().replace(R.id.main_content, listFragments.get(0)).addToBackStack("stack").commit();

        toolbar.setTitle(listNavigationItems.get(0).getTitle());
        listViewNavigation.setItemChecked(0, true);
        drawerLayout.closeDrawer(drawerPane);

        listViewNavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //deleteAllFragment();
                fragmentManager.beginTransaction().replace(R.id.main_content, listFragments.get(position)).addToBackStack("stack").commit();

                listViewNavigation.setItemChecked(position, true);
                drawerLayout.closeDrawer(drawerPane);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //if (fragmentManager.getBackStackEntryCount() == 1)
            openQuitDialog();
        /*else {
            fragmentManager.popBackStack();
        }*/
    }

    private  void openQuitDialog() {
        final AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                Activity_Navigation.this);
        quitDialog.setTitle("Закрыть приложение?");

        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quitDialog.getContext();
            }
        });

        quitDialog.show();
    }

    public static void deleteAllFragment(){
        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
    }
    public static void hideKeyBoard(Context context,View v) {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}

