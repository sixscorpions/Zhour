package com.zhour.parser;

import com.zhour.models.ComplaintModel;
import com.zhour.models.Model;

/**
 * Created by madhu on 03-Aug-17.
 */

public class ComplaintParser implements Parser<Model> {


    @Override
    public Model parse(String s) {
        ComplaintModel complaintModel = new ComplaintModel();

        return complaintModel;
    }
}
