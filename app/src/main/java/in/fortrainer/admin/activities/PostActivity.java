package in.fortrainer.admin.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import in.fortrainer.admin.R;
import in.fortrainer.admin.adapters.PostAdpater;
import in.fortrainer.admin.models.AppPost;
import in.fortrainer.admin.models.Event;
import in.fortrainer.admin.utilities.AdminHelper;
import in.fortrainer.admin.utilities.CommonRecyclerItem;
import in.fortrainer.admin.utilities.CommonRecyclerScreen;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class PostActivity extends AppCompatActivity   {

    List<AppPost> appPosts;
    PostAdpater postAdpater;
    CommonRecyclerScreen crs;
    Button button;
    LinearLayout linearLayout;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;

    FloatingActionButton floatingActionButton;
    RelativeLayout relativeLayout,relativeLayout1;
    ImageView imageView1;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        //android.app.ActionBar actionBar = getActionBar();
        //actionBar.setTitle("Post");
        button = findViewById(R.id.bt_nointernet);
        linearLayout = findViewById(R.id.nointernet);
        //linearLayout1 = findViewById(R.id.relative_header);
        //linearLayout2 = findViewById(R.id.rl_home_text);
       // relativeLayout = findViewById(R.id.rl_icon_holder);
        //relativeLayout1 = findViewById(R.id.rl_homeText);
        //imageView1 = findViewById(R.id.imageView_buttonUp);
        setScreen();
        floatingActionButton = findViewById(R.id.bt_newpost);
        /*registerForContextMenu(floatingActionButton);*/
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });
      /*  relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/
    }

   /* @Override
    public void onBackPressed() {
        Intent intent = new Intent(PostActivity.this,HomeActivity.class);
        startActivity(intent);
       // super.onBackPressed();
    }*/
   /* @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);
        menu.setHeaderTitle("Select Post Type");
        getMenuInflater().inflate(R.menu.menu_main, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                startActivity(new Intent(this, PostImageActivity.class));
                return true;
            case R.id.item2:
                startActivity(new Intent(this, PostVideoActivity.class));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }*/
   /* public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_main);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                startActivity(new Intent(this, PostImageActivity.class));
                return true;
            case R.id.item2:
                startActivity(new Intent(this, PostVideoActivity.class));
                return true;
            default:
                return false;
        }
    }*/

    private void selectImage() {
        final CharSequence[] items = {"Image", "Video",
                };
        AlertDialog.Builder builder = new AlertDialog.Builder(PostActivity.this);
        builder.setTitle("Select Post Type");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Image")) {
                    startActivity(new Intent(PostActivity.this, PostImageActivity.class));
                } else if (items[item].equals("Video")) {
                    startActivity(new Intent(PostActivity.this, PostVideoActivity.class));
                }
            }
        });
        builder.show();
    }

    private void setScreen() {
        crs = CommonRecyclerScreen.setupWithActivity(this);
        postAdpater = new PostAdpater(this, crs.recyclerItems);
        crs.setLayoutManager(new LinearLayoutManager(this));
        crs.attachAdapter(postAdpater);
        getPosts();
    }

    private void getPosts() {
        if (AdminHelper.isDataAdapterOn(context)) {
            crs.setScreen(CommonRecyclerScreen.ScreenMode.LOADING);
            Call<JsonObject> eventListCall = RetrofitHelper.getRetrofitService(context).getPostlist();
            eventListCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        JsonObject jsonObject = response.body();
                        appPosts = new Gson().fromJson(jsonObject.getAsJsonArray("app_posts"), new TypeToken<List<AppPost>>() {
                        }.getType());

                        if (appPosts.size() == 0) {
                            CommonRecyclerItem commonRecyclerItem = new CommonRecyclerItem(CommonRecyclerItem.ItemType.CARD_ACK, "No Posts yet", this);
                            crs.recyclerItems.add(commonRecyclerItem);
                        } else {
                            crs.recyclerItems.addAll(CommonRecyclerItem.generate(CommonRecyclerItem.ItemType.POSTS, appPosts, this));
                        }
                        postAdpater.notifyDataSetChanged();
                        crs.setScreen(CommonRecyclerScreen.ScreenMode.DONE);

                    } else {
                        Toast.makeText(PostActivity.this, "please try again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(PostActivity.this, "failed", Toast.LENGTH_SHORT).show();

                }
            }

            );
        }
        else
        {
            Toast.makeText(PostActivity.this, "check your internet connection", Toast.LENGTH_SHORT).show();
            linearLayout.setVisibility(View.VISIBLE);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linearLayout.setVisibility(View.GONE);
                    getPosts();

                }

            });

        }


    }
}

