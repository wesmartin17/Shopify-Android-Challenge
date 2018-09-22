package io.github.wesmartin17.shopifychallenge2019;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * RestManager handles all volley requests to a given API using GET or POST.
 *
 * Responses for given keys are returned
 *
 */
public class RestManager {

    private static RestManager mRestManager;
    private RequestQueue mRequestQueue;

    /**
     * Constructor to instantiate RestManager
     *
     * @param context context of the class requesting the RestManager instance
     */
    private RestManager(Context context) {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Passes on the instance of RestManager or creates it if there isn't one
     *
     * @param context context of class that is calling Restmanager
     * @return instance of RestManager
     */
    public static synchronized RestManager getInstance(Context context) {
        if (mRestManager == null)
            mRestManager = new RestManager(context);
        return mRestManager;
    }

    /**
     * Calls connectToRest using GET
     *
     * @param url      see connectToRest.url
     * @param keys     see connectToRest.keys
     * @param callback see connectToRest.callback
     */
    public void getJSONObjectFromRest(String url, final ServerCallback callback) {
        connectToRestObject(Request.Method.GET, url, new JSONObject(), callback);
    }

    /**
     * Calls connectToRestArray using GET
     *
     * @param url      see connectToRest.url
     * @param callback see connectToRest.callback
     */
    public void getJSONArrayFromRest(String url, final ServerCallback callback) {
        connectToRestArray(Request.Method.GET, url, callback);
    }


    /**
     * Sends a URL to the API using POST, passes any desired values to ServerCallback
     * <p>
     * If String [] keys is null, the whole JSON response object will be returned to the callback
     *
     * @param method     either GET or POST, depending on function this is called from
     * @param url        url where the keys are located
     * @param jsonObject sends a JSON object to the server.  Set as null if not applicable.
     * @param keys       all keys needed to be retreived from Rest API
     */
    private void connectToRestObject(int method, String url, JSONObject jsonObject, final ServerCallback callback) {
        final JsonObjectRequest request = new JsonObjectRequest(method, url, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                callback.onSuccess(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ERROR",error.toString());
                callback.onSuccess(null);
            }
        });
        int socketTimeout = 5000;//30 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        mRequestQueue.add(request);
    }

    /**
     * Sends a URL to the API using POST, passes any desired values to ServerCallback
     * <p>
     * If String [] keys is null, the whole JSON response object will be returned to the callback
     *
     * @param method either GET or POST, depending on function this is called from
     * @param url    url where the keys are located
     */
    private void connectToRestArray(int method, String url, final ServerCallback callback) {

        final JsonArrayRequest request = new JsonArrayRequest(method, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onSuccess(null);
            }
        });
        int socketTimeout = 30000;//30 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        mRequestQueue.add(request);
    }
}