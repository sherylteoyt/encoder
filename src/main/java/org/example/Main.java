package org.example;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    //assign variable for input text
    static String encodedString = "";
    //characters provided in exercise
    static char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '(', ')', '*', '+',
            ',', '-', '.', '/'};
    static char[] original = chars.clone();
    static char offsetChar;
    static int offnum = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String string = "HELLO wORLd@[]";

        System.out.println("Enter Offset: ");
        String str = scanner.next();
        scanner.close();

        char in = str.charAt(0);

        int offset = 0;
        for (int i = 0; i < 44; i++){
            if (in == chars[i]){
                offset = i;
            }
        }

        offsetChar = chars[offset];
        offnum = offset;
        Main object = new Main();
        System.out.println("Encoded String: " + in + object.encode(string));
        System.out.println("Decoded String: " + object.decode(encodedString));
    }

    public String decode(String encodedString){
        char[] encodedArray = encodedString.toCharArray();
        int[] position = new int[encodedString.length()];

        int pos = 0;
        String tocheck = "";
        int flag = 0;

        for (int i = 0; i < encodedArray.length; i++){
            for (int k = 0; k < chars.length; k++){
                if (encodedArray[i] == chars[k]){
                    flag += 1;
                    tocheck = tocheck + chars[k + offnum];
                    break;
                }
                if (encodedArray[i] == ' '){
                    tocheck = tocheck + " ";
                    break;
                }
            }

            if(flag == 0) {
                tocheck = tocheck + encodedArray[i];
            }

            flag = 0;
        }

        return tocheck;

    }

    public String encode (String plaintext){
        char[] input = plaintext.toCharArray();
        int space = 0;
        for (int i = 0; i< input.length; i++){
            if (input[i] == ' '){
                space++;
            }
        }

        //to get offset table based on offset value
        for (int j = 0; j < offnum; j++) {
            char temp = chars[chars.length - 1];
            int arr_length = chars.length;
            int last = arr_length - 1;

            for (int i = arr_length - 2; i >= 0; i--){
                chars[last] = chars[i];
                last--;
            }
            chars[0] = temp;
        }

        String t = "";
        int[] position = new int[input.length];
        int pos = 0;
        int g = 0;

        //flag to check whether element of String is present in table or not
        int flag = 0;
        for (int k = 0; k < input.length; k++) {
            for (int i = 0; i < original.length; i++){
                if (input[k] == original[i]){
                    flag = 1;
                    position[pos] = i;
                    pos++;
                    break;
                }

            }
            if (flag == 0 && input[k] != ' '){
                position[pos] = (int) input[k];
                pos++;
            }

            flag = 0;
            if(input[k] == ' '){
                position[pos] = 0;
                pos++;
            }
        }

        String ret = "";

        for (int i = 0; i < position.length; i++) {
            if(position[i] == 0){
                ret = ret + " ";
            }

            if(position[i] != 0 && position[i] >= 0 && position[i] <= 43){
                ret = ret + chars[position[i]];
            }

            if (position[i] >= 58 && position[i] <= 64) {
                char single = (char) position[i];
                ret = ret + single;
            }

            if(position[i] >= 91 && position[i] <= 126){
                char single = (char) position[i];
                ret = ret + single;
            }

        }
        encodedString = encodedString + ret;
        return ret;
    }
}
