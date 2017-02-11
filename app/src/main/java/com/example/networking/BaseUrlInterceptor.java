package com.example.networking;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * An interceptor that allows runtime changes to the URL hostname. Usually used in combination with MockWebServer.
 */
public final class BaseUrlInterceptor implements Interceptor {

    @Nullable private volatile String host;

    private final String realBaseUrl;

    public BaseUrlInterceptor(String realBaseUrl) {
        this.realBaseUrl = realBaseUrl;
    }

    public void setBaseUrl(String host) {
        this.host = host;
    }

    public void resetBaseUrl() {
        this.host = realBaseUrl;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (host != null && !realBaseUrl.equals(host)) {
            @Nullable HttpUrl newUrl = HttpUrl.parse(host);
            request = request.newBuilder()
                    .url(newUrl)
                    .build();
        }
        return chain.proceed(request);
    }

}
