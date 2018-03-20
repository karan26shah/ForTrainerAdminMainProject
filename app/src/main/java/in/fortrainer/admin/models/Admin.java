
package in.fortrainer.admin.models;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import in.fortrainer.admin.utilities.AppStorageManager;

public class Admin {
    private static final String KEY_ADMIN = "key_admin";

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile_number")
    @Expose
    private Object mobileNumber;
    @SerializedName("auth_key")
    @Expose
    private String authKey;
    @SerializedName("is_admin")
    @Expose
    private Boolean isAdmin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Object mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public static boolean isUserPreferenceStored(Context context) {
        if (isLoggedIn(context)) {
            // String storedPref = getCurrentUser(context).getCourseOfferingIds();
            //return storedPref != null && storedPref.length() > 0;
        } else {
            return false;
        }
        return false;
        }

    public static boolean isLoggedIn(Context context) {
        try {
            if (AppStorageManager.getSharedStoredString(context, KEY_ADMIN) != null) {
                Admin admin = new Gson().fromJson(AppStorageManager.getSharedStoredString(context, KEY_ADMIN), Admin.class);
                if (admin != null && admin.getId() != 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    @Nullable
    public static Admin getCurrentUser(Context context) {
        try {
            return new Gson().fromJson(AppStorageManager.getSharedStoredString(context, KEY_ADMIN), Admin.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void login(Context context){
        saveAsCurrentAdmin(context);
    }

    public static void eraseCurrentUserData(Context context) {
        AppStorageManager.removeEntry(context, KEY_ADMIN);
       // setNotificationAllowed(context, true);
    }
    public void saveAsCurrentAdmin(Context context) {
        AppStorageManager.setSharedStoreString(context, KEY_ADMIN, new Gson().toJson(this));
    }
}
