package com.example.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView text1,text2,text3,text4;
    ListView list1;
    String [] data1 = {"항목1","항목2","항목3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView) findViewById(R.id.textview1);
        text2 = (TextView) findViewById(R.id.textview2);
        text3 = (TextView) findViewById(R.id.textview3);
        list1 = (ListView) findViewById(R.id.list1);
        text4 = (TextView) findViewById(R.id.textview4);

        registerForContextMenu(text2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data1);
        list1.setAdapter(adapter);
        list1.setOnItemClickListener(new ListListener());
        registerForContextMenu(list1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 1. XML을 사용해서 메뉴 구성
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        // 2. JAVA를 사용해서 메뉴 구성
        //menu.add(Menu.NONE,Menu.FIRST,Menu.NONE, "코드메뉴1");
        //Menu sub = menu.addSubMenu("코드메뉴2");
        //sub.add(Menu.NONE, Menu.FIRST + 10, Menu.NONE, "코드 서브메뉴1");

        MenuItem search_item = menu.findItem(R.id.item2);
        SearchView search_view = (SearchView) search_item.getActionView();
        search_view.setQueryHint("검색어 입력 바란다");
        search_view.setOnQueryTextListener(new SearchViewListener());

        return true;
    }

    // 옵션 메뉴의 항목을 터치하면 호출된다 item 값을 받아서 처리
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.item1:
                text1.setText("메뉴1을 눌렀습니다");
                break;
            case R.id.item2:
                text1.setText("메뉴2를 눌렀습니다");
                break;
            case R.id.item3:
                text1.setText("메뉴3을 눌렀습니다");
                break;
            case R.id.item4:
                text1.setText("메뉴4을 눌렀습니다");
                break;
        }

        switch (id) {
            case Menu.FIRST:
                text1.setText("메뉴1을 눌렀습니다");
                break;
            case Menu.FIRST+10:
                text1.setText("서브메뉴1를 눌렀습니다");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        int view_id = v.getId();

        switch (view_id) {
            case R.id.textview2:
                menu.setHeaderIcon(R.mipmap.ic_launcher);
                menu.setHeaderTitle("텍스트 뷰의 컨택스트 메뉴");
                inflater.inflate(R.menu.context_menu, menu);
                break;
            case R.id.list1:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Log.d("listview","선택된 항목 - " + info.position);

                menu.setHeaderTitle("리스트 뷰의 컨택스트 메뉴");
                inflater.inflate(R.menu.listview_menu, menu);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        int position = 0;
        ContextMenu.ContextMenuInfo info = item.getMenuInfo();
        if(info != null && info instanceof AdapterView.AdapterContextMenuInfo) {
            AdapterView.AdapterContextMenuInfo info2 = (AdapterView.AdapterContextMenuInfo) info;
            position = info2.position;
        }

        switch (id) {
            case R.id.context1:
                text2.setText("텍스트뷰의 메뉴1을 선택하였습니다");
                break;
            case R.id.context2:
                text2.setText("텍스트뷰의 메뉴2을 선택하였습니다");
                break;
            case R.id.list_item1:
                text3.setText("리스트뷰 메뉴1 포지션 - " + position);
                break;
            case R.id.list_item2:
                text3.setText("리스트뷰 메뉴2 포지션 - " + position);
                break;
        }

        return super.onContextItemSelected(item);
    }

    class ListListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            text3.setText("ListView Menu - " + data1[position] + " 번 선택");
        }
    }

    public void buttonMethod(View view) {
        PopupMenu pop = new PopupMenu(this, text4);
        Menu menu = pop.getMenu();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popup_menu,menu);
        pop.setOnMenuItemClickListener(new PopupListener());
        pop.show();
    }

    class PopupListener implements PopupMenu.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int id = item.getItemId();
            switch (id) {
                case R.id.popup_item1:
                    text4.setText("팝업 1을 선택하였습니다");
                    break;
                case R.id.popup_item2:
                    text4.setText("팝업 2을 선택하였습니다");
                    break;
                case R.id.popup_item3:
                    text4.setText("팝업 3을 선택하였습니다");
                    break;
            }
            return false;
        }
    }

    class SearchViewListener implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            text1.setText(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            text1.setText(newText);
            return false;
        }
    }
}