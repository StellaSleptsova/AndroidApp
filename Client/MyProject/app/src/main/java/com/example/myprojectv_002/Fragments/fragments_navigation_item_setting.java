package com.example.myprojectv_002.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myprojectv_002.Activity_Navigation;
import com.example.myprojectv_002.R;
import com.example.myprojectv_002.ResourceAdapter.Setting_adapter;
import com.example.myprojectv_002.ResourceItem.Setting_item;

import java.util.ArrayList;
import java.util.List;

public class fragments_navigation_item_setting extends Fragment {

    ListView listView;
    List<Fragment> listFragment;
    List<Setting_item> listItem;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        v=inflater.inflate(R.layout.fragment_setting, container,false);

        Activity_Navigation.toolbar.setTitle("Настройки");
        listView = (ListView) v.findViewById(R.id.list_setting);
        listItem = new ArrayList<Setting_item>();

        listItem.add(new Setting_item("Внешний вид",R.mipmap.ic_palette,"Настройки интерфейса приложения"));
        listItem.add(new Setting_item("Профиль",R.mipmap.ic_account,"Настройки профиля"));
        listItem.add(new Setting_item("Language",R.mipmap.ic_web,"Настройки языка интерфейса"));
        listItem.add(new Setting_item("О программе",R.mipmap.ic_information_outline,""));


        Setting_adapter ListAdapter = new Setting_adapter(getActivity(), R.layout.item_list_setting, listItem);
        listView.setAdapter(ListAdapter);

        /*listFragment = new ArrayList<Fragment>();
        for (int i = 0; i < countGroups; i++) {
            listFragment.add(new fragment_list_student(groups[i].idGroup));
        }*/

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentManager fragmentManager = getFragmentManager();
                Activity_Navigation.toolbar.setTitle(groups[position].nameGroup);
                fragmentManager.beginTransaction().replace(R.id.main_content, listFragment.get(position)).commit();

            }
        });*/
        return  v;
    }
}
