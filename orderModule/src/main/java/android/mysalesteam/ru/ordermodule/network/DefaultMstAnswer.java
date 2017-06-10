package android.mysalesteam.ru.ordermodule.network;

import android.mysalesteam.ru.ordermodule.KitSettings;
import android.mysalesteam.ru.ordermodule.utils.GuavaSubstitute;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Alexey Nekrasov on 17.11.16.
 */

public class DefaultMstAnswer implements Parcelable {
    protected int code;
    protected String status;
    protected String[] errors;
    private String current_time;

    protected DefaultMstAnswer() {

    }

    public DefaultMstAnswer(int code, String status, String[] errors) {
        this.code = code;
        this.status = status;
        this.errors = errors;
    }

    public DefaultMstAnswer(DefaultMstAnswer defaultMstAnswer) {
        code = defaultMstAnswer.getCode();
        status = defaultMstAnswer.getStatus();
        errors = defaultMstAnswer.getErrors();
    }

    protected DefaultMstAnswer(Parcel in) {
        code = in.readInt();
        status = in.readString();
        errors = in.createStringArray();
        current_time = in.readString();
    }

    public static final Creator<DefaultMstAnswer> CREATOR = new Creator<DefaultMstAnswer>() {
        @Override
        public DefaultMstAnswer createFromParcel(Parcel in) {
            return new DefaultMstAnswer(in);
        }

        @Override
        public DefaultMstAnswer[] newArray(int size) {
            return new DefaultMstAnswer[size];
        }
    };

    public static <T extends DefaultMstAnswer> T fromJson(String jsonString, Class<T> clazz) {
        if (GuavaSubstitute.isNullOrEmpty(jsonString)) {
            return null;
        } else {
            return KitSettings.getGson().fromJson(jsonString, clazz);
        }
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return GuavaSubstitute.nullToEmpty(status);
    }

    public String[] getErrors() {
        if (errors == null) {
            return new String[] { getStatus() };
        }
        return errors;
    }

    public boolean isOk() {
        return code == 200;
    }

    public String getServerTime() {
        return current_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(status);
        dest.writeStringArray(errors);
        dest.writeString(current_time);
    }
}
