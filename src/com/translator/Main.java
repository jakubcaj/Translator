package com.translator;

import com.translator.Impl.ParseRowImpl;
import com.translator.Impl.RowImpl;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("output.out"));
            String row;
            RowImpl resultSet = new RowImpl();

            while ((row = br.readLine()) != null) {
                resultSet.Add(ParseRowImpl.getRow(row));
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

        }
    }
}
