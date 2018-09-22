package io.github.wesmartin17.shopifychallenge2019;

import org.json.JSONObject;

/**
 * Created by WM on 2017-12-27.
 */

public interface ServerCallback<T> {

    void onSuccess(T response);

    void onSuccess(JSONObject response);
}
