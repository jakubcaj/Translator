package com.translator.Impl;

import com.translator.Util.NumericUtil;
import com.translator.dto.Row;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by Kuba on 09.05.2017.
 */
public class RowImpl {
    Stack<Row> rowStack = new Stack<>();
    HashMap<String, String> variableMap = new HashMap<>();


    public void Add(Row row) {

        switch (row.getStatement()) {
            case "push": {
                rowStack.push(row);
                break;
            }
            case "write":
            case "print": {
                for (int i = 0; i < Integer.parseInt(row.getValues()); i++) {
                    System.out.println(rowStack.pop().getValues());
                }
            }
            case "load": {
//                Row temp = rowStack.stream().filter(p -> p.getValues().equals(row.getValues())).findAny().orElse(null);
//                if(temp != null){
//                    rowStack.push(temp);
//                }
                rowStack.push(new Row(row.getStatement(), variableMap.get(row.getValues())));
                break;
            }
            case "save": {
                variableMap.put(row.getVariableValue(), rowStack.pop().getValues());
                break;
            }
            case "add": {
                Row first = rowStack.pop();
                Row second = rowStack.pop();
                if (first.getValues().charAt(0) == 'F' || second.getValues().charAt(0) == 'F') {
                    rowStack.add(new Row(row.getStatement(), "F" +(NumericUtil.getFloat(first.getValues()) + NumericUtil.getFloat(second.getValues()))));
                }else{
                    rowStack.add(new Row(row.getStatement(), "I" + (NumericUtil.getInt(first.getValues()) + NumericUtil.getInt(second.getValues()))));
                }
                break;
            }
            case "sub": {
                Row first = rowStack.pop();
                Row second = rowStack.pop();
                if (first.getValues().charAt(0) == 'F' || second.getValues().charAt(0) == 'F') {
                    rowStack.add(new Row(row.getStatement(), "F" +(NumericUtil.getFloat(first.getValues()) - NumericUtil.getFloat(second.getValues()))));
                }else{
                    rowStack.add(new Row(row.getStatement(), "I" + (NumericUtil.getInt(first.getValues()) - NumericUtil.getInt(second.getValues()))));
                }
                break;
            }
            case "mul": {
                Row first = rowStack.pop();
                Row second = rowStack.pop();
                if (first.getValues().charAt(0) == 'F' || second.getValues().charAt(0) == 'F') {
                    rowStack.add(new Row(row.getStatement(), "F" +(NumericUtil.getFloat(first.getValues()) * NumericUtil.getFloat(second.getValues()))));
                }else{
                    rowStack.add(new Row(row.getStatement(), "I" + (NumericUtil.getInt(first.getValues()) * NumericUtil.getInt(second.getValues()))));
                }
                break;
            }
            case "div": {
                Row first = rowStack.pop();
                Row second = rowStack.pop();
                if (first.getValues().charAt(0) == 'F' || second.getValues().charAt(0) == 'F') {
                    rowStack.add(new Row(row.getStatement(), "F" +(NumericUtil.getFloat(first.getValues()) / NumericUtil.getFloat(second.getValues()))));
                }else{
                    rowStack.add(new Row(row.getStatement(), "I" + (NumericUtil.getInt(first.getValues()) / NumericUtil.getInt(second.getValues()))));
                }
                break;
            }
            case "mod": {
                Row first = rowStack.pop();
                Row second = rowStack.pop();
                rowStack.add(new Row(row.getStatement(), "F" +(NumericUtil.getFloat(first.getValues()) % NumericUtil.getFloat(second.getValues()))));
                break;
            }
            case "uminus": {
                Row first = rowStack.pop();
                rowStack.push(new Row(first.getStatement(), first.getValues().charAt(0) + "-" + first.getValues().substring(1,first.getValues().length()-1)));
                break;
            }
            case "concat": {

            }


        }
        this.rowStack.push(row);
    }

    public void parse() {
        while (!rowStack.empty()) {
            //TODO
        }
    }
}
