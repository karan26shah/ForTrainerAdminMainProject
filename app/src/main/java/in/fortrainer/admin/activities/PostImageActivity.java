package in.fortrainer.admin.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;


//import in.fortrainer.admin.Manifest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import in.fortrainer.admin.R;
import in.fortrainer.admin.models.CreateSharedImage;
import in.fortrainer.admin.models.SharedImage;
import in.fortrainer.admin.models.Video;
import in.fortrainer.admin.utilities.RetrofitHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Bitmap.CompressFormat.PNG;
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static in.fortrainer.admin.utilities.EECMultiDexApplication.context;

public class PostImageActivity extends AppCompatActivity {

    Button button;
    Button button1;
    public static final int CAMERA_REQUEST_FO_PROFILE_PIC = 32;
    private static final int PICK_IMAGE_REQUEST = 2;
    Bitmap tempBitmap = null;
    private ImageView imageView;
    private File tempImageFile;
    private byte[] bitmapdata;
    ByteArrayOutputStream bos;
    CreateSharedImage createSharedImage;
    SharedImage sharedImage;
    EditText et_title;
    EditText et_des;
    private String uploadUrl;
    URL myURL = null;
    String sid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_image);
        button = findViewById(R.id.bt_iu);
        imageView = findViewById(R.id.iv_pv);
        bindViews();
        setupClickListener();
        init();
    }

    private void bindViews() {
        button1 = findViewById(R.id.bt_post);
        et_title = findViewById(R.id.et_title);
        et_des = findViewById(R.id.et_des);
    }

    private void setupClickListener() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(PostImageActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    launchTakePhoto();
                } else if (items[item].equals("Choose from Library")) {
                    launchPicChooser();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void launchPicChooser() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
        galleryIntent.setType("image/*");
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent);
        chooser.putExtra(Intent.EXTRA_TITLE, "Choose from");
        Intent[] intentArray = {cameraIntent};
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        startActivityForResult(chooser, PostImageActivity.PICK_IMAGE_REQUEST);

    }

    private void launchTakePhoto() {
        if (ContextCompat.checkSelfPermission(PostImageActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PostImageActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_REQUEST_FO_PROFILE_PIC);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, PICK_IMAGE_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PostImageActivity.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data.getData() != null) {
                try {

                    InputStream stream = this
                            .getContentResolver().openInputStream(data.getData());
                    tempBitmap = BitmapFactory.decodeStream(stream);

                    stream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                tempBitmap = (Bitmap) data.getExtras().get("data");
            }

            if (tempBitmap != null) {
                tempImageFile = new File(this.getCacheDir(), "tempImage");
                FileOutputStream fos = null;
                try {
                    tempImageFile.createNewFile();
                    //Convert bitmap to byte array
                    bos = new ByteArrayOutputStream();
                    //trail-code
                    int originalWidth = tempBitmap.getWidth();
                    int originalHeight = tempBitmap.getHeight();
                    int finalDim;
                    int dx = 0, dy = 0;
                    if (originalHeight < originalWidth) {
                        finalDim = originalHeight;
                        dx = (originalWidth - finalDim) / 2;
                    } else {
                        finalDim = originalWidth;
                        dy = (originalHeight - finalDim) / 2;
                    }

                    //tempBitmap = Bitmap.createBitmap(tempBitmap, dx, dy, finalDim, finalDim);
                    //tempBitmap = Bitmap.createScaledBitmap(tempBitmap, 350, 350, true);
                    tempBitmap.compress(Bitmap.CompressFormat.JPEG, 50 /*ignored for PNG*/,bos);

                    bitmapdata = bos.toByteArray();
                    Bitmap compressedBitmap = BitmapFactory.decodeByteArray(bitmapdata,0,bitmapdata.length);
                    imageView.setImageBitmap(compressedBitmap);
                    //write the bytes in file
                    fos = new FileOutputStream(tempImageFile);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void init() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedImageId();
            }
        });
    }

    private void SharedImageId() {
        Call<JsonObject> SICall = RetrofitHelper.getRetrofitService(context).CreateSharedImageId("Post", "image_id", String.valueOf(tempImageFile.getName()));
        SICall.enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    createSharedImage = new Gson().fromJson(jsonObject, new TypeToken<CreateSharedImage>() {
                    }.getType());
                    uploadUrl = createSharedImage.getUrl().toString();
                     sid= createSharedImage.getId().toString();
                    startUserImageUpload(uploadUrl);

                } else {
                    Toast.makeText(PostImageActivity.this, "please try again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(PostImageActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void startUserImageUpload(String uploadUrl) {
        long sizeOfFile = tempImageFile.length();
        String uploadURL = uploadUrl;
        if (tempImageFile.length() < 2097152) { //2097152 byte= 2
            try {
                 myURL = new URL(uploadURL);
                UploadImage uploadImage = new UploadImage();
                uploadImage.execute(tempImageFile);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(PostImageActivity.this, "fail", Toast.LENGTH_LONG).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class UploadImage extends AsyncTask<File, Integer, String> {

        @Override
        protected String doInBackground(File... file) {
            String result = "negative";
            try {
                //  Bitmap bm = BitmapFactory.decodeFile(String.valueOf(file));
                HttpsURLConnection connection = (HttpsURLConnection) myURL.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/octet-stream"); // Very important ! It won't work without adding this!
                OutputStream output = connection.getOutputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                output.write(bitmapdata);
                output.flush();
                DoImageUpload();
                if (connection.getResponseCode() == 200) {
                    result = "positive";
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

    }

    private void DoImageUpload() {
        Call<JsonObject> ImageuploadedCall = RetrofitHelper.getRetrofitService(context).ImageUploaded(createSharedImage.getId(), String.valueOf(tempImageFile.getName()));
        ImageuploadedCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(PostImageActivity.this, "ImageUploaded Successfully", Toast.LENGTH_SHORT).show();
                    uploadImagePost();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(PostImageActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadImagePost() {
        Call<JsonObject> uploadImagePostCall = RetrofitHelper.getRetrofitService(context).CreateImagePost(et_title.getText().toString(),et_des.getText().toString(),sid);
        uploadImagePostCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PostImageActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    launchActivity();
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(PostImageActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void launchActivity() {
        Intent intent = new Intent(PostImageActivity.this, PostActivity.class);
        startActivity(intent);
        finish();

    }
}






