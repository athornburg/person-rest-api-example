package com.alex.thornburg.web.rest.model;

public class Testing{

    public static void main(String[] args){
        System.out.println(answer(42));
    }
    public static int answer(int n) {
        return getFirstPalindrome(n);

    }

    public static boolean isPalindrome(String n){
        char[] input = n.toCharArray();
        int i1=0;
        int i2 = input.length - 1;
        while(i2>i1){
            if(input[i1]!=input[i2]){
                return false;
            }
            i1++;
            i2--;
        }
        return true;
    }

    public static int getFirstPalindrome(int n){
        int bases = 2;
        while(!isPalindrome(convertToBase(n,bases))){
            bases++;
        }
        return bases;
    }

    public static String convertToBase(int n,int base){
        return Integer.toString(Integer.parseInt(String.valueOf(n),10),base);
    }
}