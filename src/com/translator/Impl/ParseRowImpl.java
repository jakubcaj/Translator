package com.translator.Impl;

import com.translator.dto.Row;


/**
 * Created by Kuba on 09.05.2017
 */
public class ParseRowImpl {

    public static Row getRow(String row){
        String[] rowArray = row.split(" ",1);
        if(rowArray.length > 1) {
            if(rowArray[0].equals("save")){
                return new Row(rowArray[0], null, rowArray[1]);
            }
            return new Row(rowArray[0], rowArray[1]);
        }else{
            return new Row(rowArray[0]);
        }
    }
}
