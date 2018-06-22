package vn.tiki.android.androidhometest.mvp.model.remote.api;

public interface DataCallback<T> {
    void onResult(T data);
}
