package com.zhour.asynctasknew;

import org.json.JSONException;

/**
 * Created by madhu on 15-Nov-17.
 */

public interface JSONResult {

    void successJSONResult(int code, Object result) throws JSONException;

    void failedJSONResult(int code);
}
