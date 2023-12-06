package algonquin.cst2335.finalproject.dict;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;
import algonquin.cst2335.finalproject.databinding.ActivityMainDictBinding;
import algonquin.cst2335.finalproject.dict.data.AppDatabase;
import algonquin.cst2335.finalproject.dict.data.ItemDao;


/**
 * This page is the first page of the application.
 *
 * @author qizhang
 * @version 1.0
 */
public class MainActivity_dict extends AppCompatActivity {
    protected String word;
    protected RequestQueue queue = null;

    private List<WordHistory> wordHistoryList = new ArrayList<>();


    private WordHistoryAdapter adapter; // 成员变量
    private AppDatabase db;
    private ItemDao itemDao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity_dict", "onCreate: Starting");
        queue = Volley.newRequestQueue(this);
        ActivityMainDictBinding binding = ActivityMainDictBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        loadWordHistory();

        //---------------------------------------------------------------------------------
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        itemDao = db.itemDao();


        //---------------------------------------------------------------------------------

        adapter = new WordHistoryAdapter(wordHistoryList, new WordHistoryAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(WordHistory wordHistory) {
                Log.d("AdapterClick", "Clicked on: 001" + wordHistory.getWord());
                // 处理点击事件，显示 Fragment
                WordDetailFragment fragment = WordDetailFragment.newInstance(wordHistory.getWord(), wordHistory.getDefinition());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit();
                // 显示 Fragment 容器并隐藏 RecyclerView
                findViewById(R.id.fragmentContainer).setVisibility(View.VISIBLE);

            }
        });


        RecyclerView recyclerView = binding.myRecyclerView; // 确保ID正确
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //---------------------------------------------------------------------------------

        binding.getForecast.setOnClickListener(click -> {
            word = binding.editText.getText().toString();

            String stringURL = null;
            try {
                stringURL = "https://api.dictionaryapi.dev/api/v2/entries/en/" + URLEncoder.encode(word, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            Log.d("URL", "Request URL: " + stringURL); // 添加日志输出以检查URL
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, stringURL, null,


                    //---------------------------------------------------------------------------------
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                JSONObject firstEntry = response.getJSONObject(0);
                                String word = firstEntry.getString("word");
                                String phonetic = firstEntry.optString("phonetic", "No phonetic available");
                                ArrayList<String> allDefinitions = new ArrayList<>(); // 用于存储所有定义的列表

                                JSONArray meaningsArray = firstEntry.getJSONArray("meanings"); // 获取词义数组
                                for (int i = 0; i < meaningsArray.length(); i++) {
                                    JSONObject meaning = meaningsArray.getJSONObject(i);
                                    String partOfSpeech = meaning.getString("partOfSpeech"); // 获取词性

                                    JSONArray definitionsArray = meaning.getJSONArray("definitions"); // 获取定义数组
                                    for (int j = 0; j < definitionsArray.length(); j++) {
                                        JSONObject definition = definitionsArray.getJSONObject(j);
                                        String definitionText = definition.getString("definition"); // 获取定义文本

                                        String definitionWithPartOfSpeech = partOfSpeech + ": " + definitionText; // 修正为只添加定义文本
                                        allDefinitions.add(definitionWithPartOfSpeech); // 添加到定义列表
                                    }
                                }
                                StringBuilder definitionsText = new StringBuilder();
                                for (String definition : allDefinitions) {
                                    definitionsText.append(definition).append("\n\n"); // 将每个定义添加到文本中，并用换行符分隔
                                }


                                //---------------------------------------------------------------------------------
                                runOnUiThread(() -> {
                                    binding.temp.setText("Word: " + word);
                                    binding.temp.setVisibility(View.VISIBLE);
                                    binding.maxTemp.setText("Phonetic: " + phonetic);
                                    binding.maxTemp.setVisibility(View.VISIBLE);
                                    binding.minTemp.setText(definitionsText.toString());
                                    binding.minTemp.setVisibility(View.VISIBLE);


                                    if (!isWordInHistory(word)) {
                                        WordHistory wordHistory = new WordHistory(word, definitionsText.toString());
                                        wordHistoryList.add(wordHistory);
                                        saveWordHistory(); // 保存历史记录
                                        adapter.notifyDataSetChanged(); // 更新适配器
                                    }
                                    // 解析和加载逻辑...
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                                // 显示错误消息
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // 处理错误
                            error.printStackTrace();
                        }
                    });

            queue.add(request);
        });

    }

    //    ---------------------------------------------------------------------------------
    private boolean isWordInHistory(String word) {
        for (WordHistory history : wordHistoryList) {
            if (history.getWord().equalsIgnoreCase(word)) {
                return true; // 如果找到相同的单词，返回 true
            }
        }
        return false; // 如果没有找到，返回 false
    }


    //menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu); // 替换为您的菜单文件名
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_1) {
            showDeleteConfirmationDialog();
            return true;
        } else if (id == R.id.item_2) {
            showAppInfo();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        CharSequence[] words = new CharSequence[wordHistoryList.size()];
        for (int i = 0; i < wordHistoryList.size(); i++) {
            words[i] = wordHistoryList.get(i).getWord();
        }

        final int[] checkedItem = {-1}; // 使用数组来存储选中的项

        new AlertDialog.Builder(this)
                .setTitle("Delete Word History")
                .setSingleChoiceItems(words, checkedItem[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedItem[0] = which; // 更新选中项
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (checkedItem[0] != -1) {
                            // 临时保存被删除的历史记录
                            WordHistory deletedWordHistory = wordHistoryList.get(checkedItem[0]);
                            wordHistoryList.remove(checkedItem[0]);
                            adapter.notifyDataSetChanged();

                            // 显示 Snackbar 用于撤销操作
                            Snackbar.make(findViewById(R.id.myRecyclerView), "Word history deleted", Snackbar.LENGTH_LONG)
                                    .setAction("UNDO", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // 撤销删除操作
                                            wordHistoryList.add(checkedItem[0], deletedWordHistory);
                                            adapter.notifyDataSetChanged();
                                        }
                                    })
                                    .addCallback(new Snackbar.Callback() {
                                        @Override
                                        public void onDismissed(Snackbar snackbar, int event) {
                                            if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                                                // 用户没有选择撤销，显示删除成功 Toast
                                                Toast.makeText(MainActivity_dict.this, "Deletion successful", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    })
                                    .show();
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }


    private void showAppInfo() {
        new AlertDialog.Builder(this)
                .setTitle("detailed")
                .setMessage("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx。")
                // 可以在这里添加更多关于应用的详细信息
                .setPositiveButton("ok", null)
                .show();
    }

    // ---------------------------------------------------------------
    private void saveWordHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences("WordHistory", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        JSONArray historyArray = new JSONArray();

        // 遍历历史记录列表，将每个记录转换为 JSONObject 并添加到 JSONArray 中
        for (WordHistory wordHistory : wordHistoryList) {
            try {
                JSONObject historyObject = new JSONObject();
                historyObject.put("word", wordHistory.getWord());
                historyObject.put("definition", wordHistory.getDefinition());
                historyArray.put(historyObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // 将 JSONArray 转换为字符串，并保存到 SharedPreferences
        editor.putString("history", historyArray.toString());
        editor.apply();
    }


    //    -----------------------------------------------------------------
    private void loadWordHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences("WordHistory", MODE_PRIVATE);
        String savedHistoryJson = sharedPreferences.getString("history", "");

        if (!savedHistoryJson.isEmpty()) {
            try {
                JSONArray historyArray = new JSONArray(savedHistoryJson);
                wordHistoryList.clear(); // 清空现有的历史记录列表

                for (int i = 0; i < historyArray.length(); i++) {
                    JSONObject historyObject = historyArray.getJSONObject(i);
                    String word = historyObject.getString("word");
                    String definition = historyObject.getString("definition");
                    wordHistoryList.add(new WordHistory(word, definition));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    // 加载数据的方法



}