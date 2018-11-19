package com.leo.core.net;

import android.os.Handler;
import android.os.Looper;

import com.leo.core.iapi.inter.IProgressListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

public class MRequestBody extends RequestBody implements IProgressListener {

    private RequestBody mRequestBody;
    private IProgressListener mListener;
    private Handler handler;

    MRequestBody(RequestBody requestBody, IProgressListener listener) {
        this.mRequestBody = requestBody;
        this.mListener = listener;
        this.handler = new Handler(Looper.getMainLooper());
    }

    public RequestBody getRequestBody() {
        return mRequestBody;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() {
        try {
            return mRequestBody.contentLength();
        } catch (IOException ignored) {
        }
        return 0;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (sink instanceof Buffer) {
            mRequestBody.writeTo(sink);
        } else {
            BufferedSink bufferedSink = Okio.buffer(new ForwardingSink(sink) {
                private long sum = 0;

                @Override
                public void write(Buffer source, long byteCount) throws IOException {
                    super.write(source, byteCount);
                    handler.post(() -> onLoading(sum += byteCount, contentLength()));
                }
            });
            mRequestBody.writeTo(bufferedSink);//写入
            bufferedSink.flush();//必须调用flush，否则最后一部分数据可能不会被写入
        }
    }

    @Override
    public void onLoading(long progress, long total) {
        if (mListener != null) {
            mListener.onLoading(progress, total);
        }
    }

}