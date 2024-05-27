package com.gbf.kukuru;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class te {
    public static void main(String[] args) {
        List<Integer> list=new ArrayList<>();
        List<Integer>list1=new ArrayList<>();
        Random random=new Random();
        for (int i=0;i<20;i++){
            list.add(random.nextInt(100));
        }
//        System.out.println(list);
//        Collections.sort(list);
//        System.out.println(list);
//        list1=list.subList(6,16);
//        System.out.println("AAA:"+list);
//        System.out.println("AAA:"+list1);
//        list1.set(0,99);
//        System.out.println("BBB:"+list);
//        System.out.println("BBB:"+list1);
//
//        System.out.println("000");

        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);
        list1=list.subList(6,16);
        Collections.reverse(list1);
        System.out.println(list1);
        list.subList(6,16).clear();
        System.out.println(list);
        list.addAll(6,list1);
        System.out.println(list);
    }
}
