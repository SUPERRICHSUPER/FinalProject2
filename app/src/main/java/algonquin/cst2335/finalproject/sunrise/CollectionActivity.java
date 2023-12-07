package algonquin.cst2335.finalproject.sunrise;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.R;

public class CollectionActivity extends AppCompatActivity {
    CollectionAdapter adapter;
    private RecyclerView rv;
    List<SearchInfo> list=new ArrayList<>();
    String resultss="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        initView();
    }

    private void initView() {
        rv = (RecyclerView) findViewById(R.id.rv);
        list=showAll();
        adapter=new CollectionAdapter(this,list);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new CollectionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                show(position);
            }
        });
        adapter.setOnItemLongClickListener(new CollectionAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                SearchInfo searchInfo = list.get(position);

                serach(searchInfo.getLat(),searchInfo.getLng());
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
                            resultss=sunIfo.toString();
                            showInfo(resultss);


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
    public List<SearchInfo> showAll( ){
        List<SearchInfo> mlist =new ArrayList<>();
        SQLiteDatabase db = new MySQLiteHelper(this).getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from "+ MySQLiteHelper.TABLE_NAME_SEARCH,null);
        while(cursor.moveToNext()){
            @SuppressLint("Range")
            String lat = cursor.getString(cursor.getColumnIndex("lat"));
            @SuppressLint("Range") String lng=cursor.getString(cursor.getColumnIndex("lng"));
            SearchInfo searchInfo=new SearchInfo(lat,lng);
            mlist.add(searchInfo);
            Log.i(TAG, "showAll: -------"+searchInfo.toString());
        }
        cursor.close();
        db.close();
        return mlist;
    }
    public void show(int position){
                        SearchInfo searchInfo = list.get(position);
                new AlertDialog.Builder(this)
                        .setTitle(R.string.app_about)
                        .setMessage(R.string.delete)
                        .setPositiveButton(
                                R.string.str_ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //delete location
                                        delete(searchInfo.getLat(),searchInfo.getLng());
                                        list.remove(position);
                                        adapter.notifyItemChanged(position);
                                        Toast.makeText(getApplicationContext(), "Delete Successful!", Toast.LENGTH_SHORT).show();

                                    }
                                }

                        )

                        .show();
    }
    public void showInfo(String s){
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_about)
                .setMessage(s)
                .setPositiveButton(
                        R.string.str_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }

                )
                .show();
    }
    public void delete(String lat,String lng){
// 获取可写数据库
        SQLiteDatabase db = new MySQLiteHelper(this).getWritableDatabase();

// 删除符合条件的数据
        String tableName = "search";
        String whereClause = "lat = ? AND lng =?";
        String[] whereArgs = new String[]{lat,lng}; // 请替换为你要删除的任务的标识符

// 执行删除操作
       int i= db.delete(tableName, whereClause, whereArgs);
        Log.i(TAG, "delete: ----------"+i);

    }
}