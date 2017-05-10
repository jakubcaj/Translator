package com.translator.dto;

/**
 * Created by Kuba on 09.05.2017.
 */
public class Row {
    private String statement;
    private String values;
    private String variableValue;

    public String getVariableValue() {
        return variableValue;
    }

    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }

    public Row(String statement) {
        this.statement = statement;
        this.values = "";
        this.variableValue = "";
    }

    public Row(String statement, String value) {
        this.statement = statement;
        this.values = value;
    }

    public Row(String statement, String values, String variableName) {
        this.statement = statement;
        this.values = values;
        this.variableValue = variableName;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getValues() {
        return values == null ? " " : values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String toString() {
        return this.statement + " " + this.values + " " + this.variableValue;
    }
}
