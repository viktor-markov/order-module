package android.mysalesteam.ru.ordermodule.network;

import android.mysalesteam.ru.ordermodule.utils.GuavaSubstitute;

import java.io.IOException;


/**
 * Created by Alexey Nekrasov on 17.11.16.
 */

public abstract class MyOrderServerException extends IOException {
    public abstract <T extends DefaultMstAnswer> T getDefaultMstAnswer(Class<T> clazz);

    public DefaultMstAnswer getDefaultMstAnswer() {
        return getDefaultMstAnswer(DefaultMstAnswer.class);
    }

    private MyOrderServerException(String mstError) {
        super(mstError);
    }

    public static IOException build(String mstError, int statusCode, String reasonPhrase) {
        if (GuavaSubstitute.isNullOrEmpty(mstError)) {
            return new MyOrderServerUnhandledException(statusCode, reasonPhrase);
        } else {
            return new MyOrderHandledException(mstError);
        }
    }

    private static class MyOrderHandledException extends MyOrderServerException {
        MyOrderHandledException(String mstError) {
            super(mstError);
        }

        @Override
        public <T extends DefaultMstAnswer> T getDefaultMstAnswer(Class<T> clazz) {
            return DefaultMstAnswer.fromJson(getMessage(), clazz);
        }
    }

    private static class MyOrderServerUnhandledException extends MyOrderServerException {
        private final int statusCode;
        private final String reasonPhrase;

        private MyOrderServerUnhandledException(int statusCode, String reasonPhrase) {
            super(statusCode + " : " + reasonPhrase);
            this.statusCode = statusCode;
            this.reasonPhrase = reasonPhrase;
        }

        @Override
        public <T extends DefaultMstAnswer> T getDefaultMstAnswer(Class<T> clazz) {
            try {
                T res = clazz.newInstance();
                res.code = statusCode;
                res.status = reasonPhrase;
                res.errors = new String[]{reasonPhrase};
                return res;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
