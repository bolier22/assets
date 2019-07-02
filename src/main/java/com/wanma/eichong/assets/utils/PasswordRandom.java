package com.wanma.eichong.assets.utils;

import java.util.Random;


public class PasswordRandom {

	//生成随机数字和字母 
    public String getStringRandom(int length) {  

        String val = "";  
        Random random = new Random();        
        //length为几位密码 
        for(int i = 0; i < length; i++) {          
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
            	
            	//输出是大写字母还是小写字母  
//              int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                //输出小写字母  
                int temp = 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    }  
    
//     //生产的密码包括位数 
//    public static void  main(String[] args) {  
//    	PasswordRandom test = new PasswordRandom();  
//        System.out.println("密码:'"+test.getStringRandom(6)+"'");  
//    }  

}
