package com.zhour.aynctask;


import com.zhour.models.Model;

/**
 * Created by Shankar on 3/7/2017.
 */

public interface IAsyncCaller {
    void onComplete(Model model);
}
