package com.example.javapractice;
import java.util.*;

public class MyClass {
    public static void main(String []args){
        System.out.println("Hello World");

        String s = "as,ag,ga,ge.ji,jo,jg,gl.ajfi, lji.laid.";
       // s = s.replaceAll(".","$");
        List<String> myList = new ArrayList<String>(Arrays.asList(s.split("\\.")));

        System.out.println("List Size : "+s.split("\\.").length);
        for (int i = 0; i < myList.size(); i++) {
            System.out.println(myList.get(i));
        }
    }
}
