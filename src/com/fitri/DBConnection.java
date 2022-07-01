package com.fitri;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public Connection getConnection(){
        try{

            String unicode="useSSL=false&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8";
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/dataset_PBO?"+unicode, "haris", "180900");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            System.out.println("couldn't connect!");
            throw new RuntimeException(ex);
        }
    }
}

