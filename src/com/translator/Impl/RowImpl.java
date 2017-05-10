package com.translator.Impl;

import com.translator.Util.NumericUtil;
import com.translator.Util.StringUtil;
import com.translator.dto.Row;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class RowImpl {
    private Stack<Row> rowStack = new Stack<>();
    private Stack<Row> origRowStack = new Stack<>();
    private HashMap<String, String> variableMap = new HashMap<>();
    private Row ignoreCase;


    public void Add(Row row) {
        if (ignoreCase == null || (row.getValues().equals(ignoreCase.getValues()) && row.getStatement().equals(ignoreCase.getStatement()))) {
            switch (row.getStatement()) {
                case "push": {
                    rowStack.push(row);
                    origRowStack.push(row);
                    break;
                }
                case "write":
                case "print": {
                    Stack<String> tempStack = new Stack<>();
                    for (int i = 0; i < Integer.parseInt(row.getValues()); i++) {
                        tempStack.push(this.rowStack.pop().getValues());
                    }
                    //tempStack.stream().forEach(System.out::println);
                    do {
                        String tmp = tempStack.pop();
                        System.out.print(tmp.substring(1, tmp.length()).replace("\"", ""));
                    } while (!tempStack.empty());
                    System.out.println();
                    //System.out.println();
                    origRowStack.push(row);
                    break;
                }
                case "load": {
//                Row temp = rowStack.stream().filter(p -> p.getValues().equals(row.getValues())).findAny().orElse(null);
//                if(temp != null){
//                    rowStack.push(temp);
//                }
                    origRowStack.push(row);
                    rowStack.push(new Row(row.getStatement(), variableMap.get(row.getValues())));
                    break;
                }
                case "save": {
                    Row trow = new Row("load", rowStack.pop().getValues());
                    variableMap.put(row.getVariableValue(), trow.getValues());
                    rowStack.push(trow);
                    origRowStack.push(row);
                    break;
                }
                case "add": {
                    Row first = rowStack.pop();
                    Row second = rowStack.pop();
                    if (first.getValues().charAt(0) == 'F' || second.getValues().charAt(0) == 'F') {
                        rowStack.add(new Row("add", "F" + (NumericUtil.getFloat(second.getValues()) + NumericUtil.getFloat(first.getValues()))));
                    } else {
                        rowStack.add(new Row("add", "I" + (NumericUtil.getInt(second.getValues()) + NumericUtil.getInt(first.getValues()))));
                    }
                    origRowStack.push(row);
                    break;
                }
                case "sub": {
                    Row first = rowStack.pop();
                    Row second = rowStack.pop();
                    if (first.getValues().charAt(0) == 'F' || second.getValues().charAt(0) == 'F') {
                        rowStack.add(new Row(row.getStatement(), "F" + (NumericUtil.getFloat(first.getValues()) - NumericUtil.getFloat(second.getValues()))));
                    } else {
                        rowStack.add(new Row(row.getStatement(), "I" + (NumericUtil.getInt(first.getValues()) - NumericUtil.getInt(second.getValues()))));
                    }
                    origRowStack.push(row);
                    break;
                }
                case "mul": {
                    Row first = rowStack.pop();
                    Row second = rowStack.pop();
                    if (first.getValues().charAt(0) == 'F' || second.getValues().charAt(0) == 'F') {
                        rowStack.add(new Row("mul", "F" + (NumericUtil.getFloat(first.getValues()) * NumericUtil.getFloat(second.getValues()))));
                    } else {
                        rowStack.add(new Row("mul", "I" + (NumericUtil.getInt(first.getValues()) * NumericUtil.getInt(second.getValues()))));
                    }
                    origRowStack.push(row);
                    break;
                }
                case "div": {
                    Row first = rowStack.pop();
                    Row second = rowStack.pop();
                    if (first.getValues().charAt(0) == 'F' || second.getValues().charAt(0) == 'F') {
                        rowStack.add(new Row(row.getStatement(), "F" + (NumericUtil.getFloat(second.getValues()) / NumericUtil.getFloat(first.getValues()))));
                    } else {
                        rowStack.add(new Row(row.getStatement(), "I" + (NumericUtil.getInt(second.getValues()) / NumericUtil.getInt(first.getValues()))));
                    }
                    origRowStack.push(row);
                    break;
                }
                case "mod": {
                    Row first = rowStack.pop();
                    Row second = rowStack.pop();
                    rowStack.add(new Row(row.getStatement(), "F" + (NumericUtil.getFloat(second.getValues()) % NumericUtil.getFloat(first.getValues()))));
                    origRowStack.push(row);
                    break;
                }
                case "uminus": {
                    Row first = rowStack.pop();
                    rowStack.push(new Row(first.getStatement(), first.getValues().charAt(0) + "-" + first.getValues().substring(1, first.getValues().length())));
                    origRowStack.push(row);
                    break;
                }
                case "concat": {
                    Row first = rowStack.pop();
                    Row second = rowStack.pop();
                    rowStack.push(new Row(row.getStatement(), "S" + StringUtil.getString(second.getValues()) + StringUtil.getString(first.getValues())));
                    origRowStack.push(row);
                    break;
                }
                case "read": {
                    System.out.println();
                    Scanner scanner = new Scanner(System.in);
                    String type = "";
                    switch (row.getValues()) {
                        case "INT":
                            type = "I";
                            break;
                        case "FLOAT":
                            type = "F";
                            break;
                        case "STRING":
                            type = "S";
                            break;
                        case "BOOLEAN":
                            type = "B";
                            break;
                    }
                    rowStack.push(new Row("push", type + scanner.nextLine()));
                    origRowStack.push(row);
                    break;
                }
                case "gt": {
                    Row first = rowStack.pop();
                    Row second = rowStack.pop();
                    rowStack.push(new Row("push", NumericUtil.getFloat(second.getValues()) > NumericUtil.getFloat(first.getValues()) ? "Btrue" : "Bfalse"));
                    origRowStack.push(row);
                    break;
                }
                case "not": {
                    Row trow = rowStack.pop();
                    rowStack.push(new Row("push", trow.getValues().substring(1, trow.getValues().length()).equals("true") ? "Bfalse" : "Btrue"));
                    origRowStack.push(row);
                    break;
                }
                case "fjmp": {
                    Row trow = rowStack.pop();
                    if (trow.getValues().substring(1, trow.getValues().length()).equals("false")) {
                        ignoreCase = new Row("label", row.getValues());
                    }
                    origRowStack.push(row);
                    break;
                }
                case "label": {
                    if (ignoreCase != null && ignoreCase.getValues().equals(row.getValues())) {
                        ignoreCase = null;
                    }
                    rowStack.push(row);
                    origRowStack.push(row);
                    break;
                }
                case "jmp": {
                    Stack<Row> tmpStack = new Stack<>();
                    tmpStack.push(row);
                    Row tmpRow = origRowStack.pop();
                    while (!tmpRow.getValues().equals(row.getValues()) && !tmpRow.getStatement().equals("label")) {
                        tmpStack.push(tmpRow);
                        tmpRow = origRowStack.pop();
                    }

                    origRowStack.push(tmpRow);
                    while (!tmpStack.empty()) {
                        Row tmp2 = tmpStack.pop();
                        //origRowStack.push(tmp2);
                        this.Add(tmp2);
                    }
                    origRowStack.push(row);
                    break;
                }


            }
        }
        //this.rowStack.push(row);
    }

}
