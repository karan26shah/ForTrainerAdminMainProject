package in.fortrainer.admin.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import in.fortrainer.admin.R;

/**
 * Created by hbb20 on 5/11/16.
 */

public class AdminHelper {
    private static final String TAG = "EEC helper tag";
    static String KEY = "1234567812345678";
    public static void hideStatusBar(Activity activity) {
        try {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if either of wifi and cellular data is on or not. If device do not have connection due to turned off adapter shows message in textView
     *
     * @param context: : context of activity on which this request made
     * @return true for data adapter on
     */
    public static boolean isDataAdapterOn(Context context) {
        //Log.d("Data adapter check initiated"," ");
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
        } catch (Exception e) {
            return false;
        }
    }

    public static void toastNoInternetMessage(Context context) {
        Toast.makeText(context, "Please connect to the Internet.", Toast.LENGTH_SHORT).show();
    }


    /**
     * @param appCompatActivity
     * @param title
     * @param subTitle
     */
    public static void setupDefaultAppbar(AppCompatActivity appCompatActivity, String title, String subTitle) {
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setTitle(title);
        if (subTitle != null) {
            appCompatActivity.getSupportActionBar().setSubtitle(subTitle);
        } else {
            appCompatActivity.getSupportActionBar().setSubtitle("");
        }
    }


    /**
     * @param eecDateString in format 26-09-2016 14:00:21
     * @return
     */
    public static Calendar getCalenderForEECStringDate(String eecDateString) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:mm:ss", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(eecDateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }


    public static void openInExternalBrowser(Context context, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static void openFile(Context context, String absolutePath) {
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        File file = new File(absolutePath);
        if (file.exists()) {
            String mimeType = mimeTypeMap.getMimeTypeFromExtension(fileExt(file.toString()).substring(1));
            Intent newIntent = new Intent(android.content.Intent.ACTION_VIEW);
            newIntent.setDataAndType(Uri.fromFile(file), mimeType);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                context.startActivity(newIntent);
            } catch (android.content.ActivityNotFoundException e) {
                Toast.makeText(context, "No handler for this type of file.", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * @param url
     * @return
     */
    public static String fileExt(String url) {
        if (url.contains("?")) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf("."));
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();
        }
    }

    public static void openKeyboardForEditText(EditText editText) {
        try {
            if (editText.isEnabled()) {
                editText.requestFocus();
                editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
                editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
                editText.setSelection(editText.getText().toString().length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void forceHideKeyBoard(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void colorizeMenuIcons(Context context, Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();
            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(context.getResources().getColor(R.color.textColorPrimary), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }


    public static void openMap(Context context, float latitude, float longitude, String title) {
        String geoURI = "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + title + ")";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoURI));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * using these methods, ssb has stored encrypted values to shared prefs
     *
     * @param message
     * @return
     */
    public static String encryptSSB(String message) {
        Key aesKey = new SecretKeySpec(KEY.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(message.getBytes());
            message = Base64.encodeToString(encrypted, Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return message;
    }

    public static String decryptSSB(String message) {
        Key aesKey = new SecretKeySpec(KEY.getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            message = new String(cipher.doFinal(Base64.decode(message, Base64.NO_WRAP)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static boolean isValidEmailAddress(String email) {
        String emailRegEx = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        return email.matches(emailRegEx);
    }
}
