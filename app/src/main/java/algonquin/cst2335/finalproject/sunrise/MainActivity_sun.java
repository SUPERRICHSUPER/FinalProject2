package algonquin.cst2335.finalproject.sunrise;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.R;

public class MainActivity_sun extends AppCompatActivity {

    private LinearLayout linearLayout;
    private EditText etLng;
    private EditText etLat;
    private Button btnSearch;
    private ListView listInfo;
    private FloatingActionButton btnCollection;
    List<SunIfo> list=new ArrayList<>();
    ArrayAdapter<String> adapter;
    List<String> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sun);
        initView();
    }

    private void initView() {
        etLng = (EditText) findViewById(R.id.et_lng);
        etLat = (EditText) findViewById(R.id.et_lat);
        // 在需要获取保存的用户名和密码时读取 SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        String savedLat = sharedPreferences.getString("lat", "");
        String savedLng = sharedPreferences.getString("lng", "");
        if(!savedLng.isEmpty()&&!savedLat.isEmpty()){
            etLat.setText(savedLat);
            etLng.setText(savedLng);
        }

// 检查 savedUsername 和 savedPassword 是否为空，如果非空则填充到登录页面的用户名和密码字段中

        btnSearch = (Button) findViewById(R.id.btn_search);
        listInfo = (ListView) findViewById(R.id.list_info);
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        listInfo.setAdapter(adapter);
        btnCollection = (FloatingActionButton) findViewById(R.id.btn_collection);
        btnCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "start to collection!", Snackbar.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), CollectionActivity.class));
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lat=etLat.getText().toString();
                String lng=etLng.getText().toString();
                if(!lat.isEmpty()&!lng.isEmpty()){
                    list.clear();
                    serach(lat,lng);
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("lat" ,lat);
                    editor.putString("lng", lng);
                    editor.apply();


                }else {
                    Toast.makeText(getApplicationContext(),"please check your enter!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void serach(String lat,String lng){
        String url ="https://api.sunrisesunset.io/json?lat="+lat+"&lng="+lng+"&time=UTC&date=today";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());


                        try {
                            JSONObject responseJson = new JSONObject(response.toString());
                            JSONObject results = responseJson.getJSONObject("results");
                            // 迭代解析艺术家信息数组
//                                        for (int i = 0; i < info.length(); i++) {
//                                            JSONObject paraInfo = info.getJSONObject(i);
                            String date = results.getString("date");
                            String sunrise = results.getString("sunrise");
                            String sunset = results.getString("sunset");
                            String first_flight = results.getString("first_light");
                            String last_flight = results.getString("last_light");
                            String dawn = results.getString("dawn");
                            String dusk = results.getString("dusk");
                            String solar_moon = results.getString("solar_noon");
                            String golden_moon = results.getString("golden_hour");
                            String day_length = results.getString("day_length");
                            String timezone = results.getString("timezone");
                            String utc_offset = results.getString("utc_offset");
                            SunIfo sunIfo=new SunIfo(date,sunrise,sunset,first_flight,last_flight,dawn,dusk,solar_moon,golden_moon,day_length,timezone,utc_offset);

                            list.add(sunIfo);
                            arrayList.add("Date : "+date+"\n"+"Sunset : "+sunset+"\n"+"SunRise : "+sunrise);
                            Log.i(TAG, "onResponse: " + list.toString());
                            adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);

                            listInfo.setAdapter(adapter);

                            // 提取并使用艺术家信息
//                                        }
                            adapter.notifyDataSetChanged();
                            isAdd(lat,lng);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
        queue.add(jsonObjectRequest);
    }
    public void isAdd(String lat,String lng){
        new AlertDialog.Builder(MainActivity_sun.this)
                .setTitle(R.string.app_about)    //标题设置 string中预先定义app_about
                .setMessage(R.string.app_about_msg)
                .setPositiveButton(
                        R.string.str_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //add location
                                add(lat,lng);
                            }
                        }

                )
                .show();
    }

    public void add(String lat,String lng){
        SQLiteDatabase db = new MySQLiteHelper(this).getWritableDatabase();
        db.execSQL("insert into "+MySQLiteHelper.TABLE_NAME_SEARCH+"(lat,lng) values(?,?)",
                new Object[]{lat,lng});
    }
}