package android.mysalesteam.ru.ordermodule.network;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by viktormarkov on 09.02.17.
 */

public class DefaultResponseWrapper implements Parcelable {

    public static final int SOME_ERROR = 0;
    public static final int PERMISSION_ERROR = 1;

    boolean isSuccess;
    int errorCode;

    public DefaultResponseWrapper() {
    }

    public DefaultResponseWrapper(Parcel in) {
        isSuccess = in.readByte() != 0;
        errorCode = in.readInt();

        parcelToResponse(in);
    }


    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isSuccess ? 1 : 0));
        dest.writeInt(errorCode);

        responseToParcel(dest, flags);
    }

    public void responseToParcel(Parcel dest, int flags) {

    }

    public void parcelToResponse(Parcel in) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DefaultResponseWrapper> CREATOR = new Creator<DefaultResponseWrapper>() {
        @Override
        public DefaultResponseWrapper createFromParcel(Parcel in) {
            return new DefaultResponseWrapper(in);
        }

        @Override
        public DefaultResponseWrapper[] newArray(int size) {
            return new DefaultResponseWrapper[size];
        }
    };
}
