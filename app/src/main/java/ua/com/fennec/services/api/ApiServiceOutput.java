package ua.com.fennec.services.api;

import androidx.annotation.Nullable;

public interface ApiServiceOutput<T> {
    @Nullable
    public void onResponse(T response);
}
