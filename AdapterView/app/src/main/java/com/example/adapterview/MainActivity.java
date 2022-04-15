package com.example.adapterview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView list1;
    Spinner spin1;
    ViewPager pager1;
    TextView text2, text3;


    String [] data1 = {"data1","data2","data3","data4","data5","data6","data7","data8","data9","data10"};
    String [] data2 = {"top1","top2","top3","top4","top5","top6","top7","top8","top9","top10"};
    int [] images1 = {R.drawable.kakaobear,R.drawable.linebear,R.drawable.kakaobear,R.drawable.linebear,R.drawable.kakaobear,R.drawable.linebear,R.drawable.kakaobear,R.drawable.linebear,R.drawable.kakaobear,R.drawable.linebear};
    String [] spinnerItem = {"apple","banana","orange"};
    ArrayList<View> viewList = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list1 = (ListView) findViewById(R.id.list1);
        spin1 = (Spinner) findViewById(R.id.spinner);
        text2 = (TextView) findViewById(R.id.textView2);
        pager1 = (ViewPager) findViewById(R.id.pager);
        text3 = (TextView) findViewById(R.id.textView3);

        // android.R.layout에서 제공하는 기본 뷰를 사용한 List View
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, data1);

        // 뷰는 직접 디자인 하지만 항목에 들어갈 데이터가 1가지인 경우
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.row, R.id.rowdata1, data1);
        /*
        ArrayList<HashMap<String, Object>> data_list = new ArrayList<HashMap<String, Object>>();
        for(int i=0; i<images1.length; i++) {
            HashMap<String,Object> map = new HashMap<String, Object>();
            map.put("image", images1[i]);
            map.put("data1", data1[i]);
            map.put("data2", data2[i]);
            data_list.add(map);
        }
        //해시 맵에 저장된 데이터의 이름 배열, 데이터를 넣을 뷰의 아이디 배열
        String [] keys = {"image","data1","data2"};
        int [] ids = {R.id.rowimage1,R.id.rowdata1,R.id.rowdata2};
        // 데이터가 2개 이상이면 Simple Adapter
        SimpleAdapter adapter = new SimpleAdapter(this,data_list,R.layout.row,keys,ids);*/

        ListAdapter adapter = new ListAdapter(); // BaseAdapter

        list1.setAdapter(adapter);
        list1.setOnItemClickListener(new ListListener());

        ArrayAdapter<String> sadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinnerItem);
        sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(sadapter);
        spin1.setOnItemSelectedListener(new SpinnerListener());

        LayoutInflater inflater = getLayoutInflater();
        View page1 = inflater.inflate(R.layout.page1,null);
        View page2 = inflater.inflate(R.layout.page2,null);
        View page3 = inflater.inflate(R.layout.page3,null);
        viewList.add(page1);
        viewList.add(page2);
        viewList.add(page3);
        pager1.setAdapter(new ViewPagerAdapter());
        pager1.setOnPageChangeListener(new PageChangeListener());
    }

    class ListListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("listview","List 1:" + data1[position]);
        }
    }

    class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data1.length;
        }

        // row가 화면에 보일 때 호출, 화면에서 사라지면 convertView에 넣어
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 재사용 가능한 뷰가 없다면 뷰를 만들어준다
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.row, null);
            }

            // .setTag(position)으로 태그를 달아 UI 요소를 구분할 수 있다
            ImageView imageview = (ImageView) convertView.findViewById(R.id.rowimage1);
            TextView textview1 = (TextView) convertView.findViewById(R.id.rowdata1);
            TextView textview2 = (TextView) convertView.findViewById(R.id.rowdata2);
            imageview.setImageResource(images1[position]);
            textview1.setText(data1[position]);
            textview2.setText(data2[position]);

            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

    class SpinnerListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // position = .getSelectedItemPosition();
            text2.setText("Spinner - " + spinnerItem[position]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            pager1.addView(viewList.get(position));
            return viewList.get(position);
        }

        // Android 운영체제가 public instantiateItem()을 자동으로 부르는 경우 고려해서 추가한 뷰와 오브젝트가 같을 때만 화면에 보이도록 반환
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            pager1.removeView((View)object);
        }
    }

    class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            text3.setText("View Pager - page " + position);
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}