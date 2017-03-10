package com.example.vladislav.androidstudy.tasksfortests;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class VkComJavaProblems {

    public boolean isBase2Number(int number) {
        /**
         * Определить, является ли число, введеное пользователем, бинарным
         * (степенью двойки, иными словами).
         */
        String str = Integer.toBinaryString(number);
        if (str.indexOf('1') == str.lastIndexOf('1')) {
            return true;
        }
        return false;
    }

    public int[] isPerfectNumber(int a, int b) {
        /**
         * https://ru.wikipedia.org/wiki/%D0%A1%D0%BE%D0%B2%D0%B5%D1%80%D1%88%D0%B5%D0%BD%D0%BD%D0%BE%D0%B5_%D1%87%D0%B8%D1%81%D0%BB%D0%BE
         *
         * Совершенное число. #java Понятие совершенного числа. Совершенное
         * число - число, которое есть сумма все делителей данного числа, кроме
         * самого числа (например, 6 = 1 + 2 + 3). Пользователь вводит начало
         * диапазона A и его конец Б, а увидеть он желает все совершенные числа,
         * принадлежащие диапазону. Пользователь средней сообразительности,
         * поэтому если в данном диапазоне совершенных чисел нет, об этом не
         * помешает сообщить.
         */

        List<Integer> lstNumbers = new ArrayList<Integer>();
        int[] arrNumbers;

        int sum = 0;
        boolean presentPerfect = false;
        for (int number = a; number < b; number++) {
            for (int j = number - 1; j > 0; j--) {
                if ((double) number / j == number / j) {
                    sum += j;
                }
            }
            if (sum == number) {
                presentPerfect = true;
                lstNumbers.add(number);
            }
            sum = 0;
        }

        arrNumbers = new int[lstNumbers.size()];
        for (int i = 0; i < lstNumbers.size(); i++) {
            arrNumbers[i] = lstNumbers.get(i);
        }

        return arrNumbers;
    }

    public String[] removeWord(String[] strs, String word) {
        /**
         * Удаление слова. #java Дан массив строк. Удалите из текста все
         * вхождения заданного слова. И выведите обновленный текст. Пустые
         * строки не выводите. Если в результате удаления весь текст состоит из
         * пустых строк - вывести, что выводить нечего.
         */

        String str;
        String[] strsResult;
        List<String> strList = new ArrayList<>();

        for (int i = 0; i < strs.length; i++) {
            str = strs[i].replace(word, "");
            if (!str.isEmpty()) {
                strList.add(str);
            }
        }

        strsResult = new String[strList.size()];
        int i = 0;
        for (String strElement : strList) {
            strsResult[i] = strElement;
            System.out.println(strsResult[i] + " ");
            i++;
        }

        return strsResult;
    }

    public int[] fragmentNumber(long number) {

        double divResult = 0;
        List<Integer> lstNumbers = new ArrayList<>();
        int[] arrNumbers;

        for (int i = 2; i < Integer.MAX_VALUE; i++) {
            divResult = (double) number / i;

            if (divResult == Math.round(divResult)) {
                lstNumbers.add(i);
                number = (long) divResult;
                i--;
            }
            if (divResult == 1) {
                break;
            }
        }

        arrNumbers = new int[lstNumbers.size()];
        for (int i = 0; i < lstNumbers.size(); i++) {
            arrNumbers[i] = lstNumbers.get(i);
        }
        return arrNumbers;

    }

    // Turning number to an array of digits of int type.
    public int[] numberToDigitsArray(int number) {

        String string = Integer.toString(number);
        int[] ints = new int[string.length()];
        int i = 0;
        for (char ch : string.toCharArray()) {
            ints[i] = Character.getNumericValue(ch);
            i++;
        }
        return ints;
    }

    // https://vk.com/java_problems?w=wall-60560229_1080
    public int computeRecharge(int amount) {
        // Recharge - пополнение баланса.
        /**
         * Банковский перевод #Java #Pascal #php #JavaScript
         Банк берет комиссию за перевод по принципу - с каждой сотни рублей изымается комиссия - 7 рублей. За неполную
         сотню также изымается 7 рублей. К примеру, за перевод одного рубля придется выложить 1 + 7 = 8 рублей.
         За перевод 101 рубля придется выложить уже 100 + 7 + 1 + 7 = 115 рублей

         Пользователь вводит сумму, которую хочет перевести, а программа должна определить:
         1. Максимально возможную сумму перевода (с учетом, что комиссия за перевод будет браться из переводимой суммы).
         2. Комиссию за данный перевод.

         1. Узнать сколько там сотен.
         2.

         */
        int chargePerHundred = 7;
        int charged;
        int fullCharges; // How many 100 + chargePerHundred.
        System.out.println("User tries to deposit: " + amount);
        if (amount < chargePerHundred + 1) {
            System.out.println("Amount cannot be deposited.");
            return 0;
        } else {
            fullCharges = (int) amount / (100 + chargePerHundred);
            // How much charged for full charges;
            charged = fullCharges * chargePerHundred;
            System.out.println("There are " + fullCharges + " of "
                    + (100 + chargePerHundred) + " = "
                    + (100 + chargePerHundred) * fullCharges);
            amount -= fullCharges * (100 + chargePerHundred);
            System.out.println("Leftover is: " + amount);
            if (amount <= chargePerHundred) {
                System.out.println("Leftover of " + amount + " is returned to payer.");
                System.out.println("Amount deposited: " + (fullCharges * 100));
                System.out.println("Amount charged: " + charged);
                return fullCharges * 100;
            } else {
                amount -= chargePerHundred;
                charged += chargePerHundred;
            }
            System.out.println("Amount charged: " + charged);
            System.out.println("Amount deposited: " + (fullCharges * 100 + amount));
            return fullCharges * 100 + amount;
        }

    }
}

class replaceBadWords {

    Set<String> setBadWords;
    String currentString;

    public replaceBadWords(String strInitial) {
        setBadWords = new HashSet<>();
        populateBadWords(setBadWords);
        System.out.println(strInitial);
        System.out.println(replaceBadWords2(strInitial, setBadWords));
    }

    void populateBadWords(Set setBadWords) {
        setBadWords.add("дурак");
        setBadWords.add("тварь");
        setBadWords.add("чмо");
        setBadWords.add("упырь");
        setBadWords.add("обмудок");
    }

    /**
     * Very simple solution - doesn't check if there are any surrounding
     * characters.
     *
     * @param row
     * @param badWords
     * @return
     */
    String replaceBadWords(String row, Set badWords) {
//        List<String> arrRows = new ArrayList<>();
        List<String> lstWords = Arrays.asList(row.split("[ ]"));
//        [ \\w\\s . ,]
        Iterator iterator = lstWords.iterator();
        String asterisk = "";
        String result = "";
        while (iterator.hasNext()) {
            currentString = (String) iterator.next();
//            System.out.println("|" + currentString + "|");
            if (badWords.contains(currentString.toLowerCase())) {
                for (int i = 0; i < currentString.length(); i++) {
                    asterisk += "*";
                }
                result += asterisk + " ";
                asterisk = "";
            } else {
                if (currentString.isEmpty()) {
//                    result = result.subSequence(0, result.length() - 1) + ", ";
                    result += currentString;
                } else {
                    result += currentString + " ";
                }
            }
        }
        return result;
    }

    // Correct one.
    // Strings only used.
    String replaceBadWords2(String row, Set badWords) {

        int position = 0;
        int index = 0;
        String asterisk = "";
        String result = null;
        String strBadWord;

        for (Object badWord : badWords) {
            strBadWord = (String) badWord;
            position = 0;
            index = 0;
            while (position != -1) {
                position = row.toLowerCase().indexOf(strBadWord, index);
                index = position + 1;
                if (position == -1) {
                    break;
                } else {
                    for (int i = 0; i < strBadWord.length(); i++) {
                        asterisk += "*";
                    }
//                    asterisk += " ";
                    result = row.replace(strBadWord, asterisk);
                    asterisk = "";
                    row = result;
                }
            }
        }
        return result;
    }

    // Correct one.
    // StringBuilder is used.
    String replaceBadWords3(String row, Set badWords) {

        String word;
        int position = 0;
        int index = 0;
        String asterisk = "";
        StringBuilder stringBuilder = new StringBuilder(row.toLowerCase());

        for (Iterator iterator = badWords.iterator(); iterator.hasNext(); ) {
            word = (String) iterator.next();
            position = 0;
            while (position != -1) {
                position = stringBuilder.indexOf(word, index);
                index = position + 1;
                if (position != -1) {
                    for (int i = 0; i < word.length(); i++) {
                        asterisk += "*";
                    }
                    stringBuilder.delete(position, position + word.length());
                    stringBuilder.insert(position, asterisk, 0,
                            asterisk.length());
                    position = 0;
                    asterisk = "";
                }
            }
        }
        return stringBuilder.toString();
    }
}

class BeginnersTasks2 {

    //    https://acmp.ru/?main=tasks
    String filesPath = "user/files/";

    // https://acmp.ru/?main=task&id_task=1
    void sumAandB() throws Exception {

        // Is catching an exception and rethrowing it again is ok ?

        long a = 0;
        long b = 0;
        String[] strings;

        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(filesPath + "/input.txt"));
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("File " + filesPath + "/input.txt"
                    + " does not exist.");
        }

        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(
                    new FileWriter(filesPath + "/output.txt"));
        } catch (FileNotFoundException ex) {
            throw new FileNotFoundException("File " + filesPath + "/output.txt"
                    + " does not exist.");
        }
        if (fileReader == null) {
            throw new IOException("File reader is null.");
        }
        if (bufferedWriter == null) {
            throw new IOException("File writer is null.");
        }

        try {
            // Splitting what's been read into words.
            strings = fileReader.readLine().split("[ ]");
        } catch (NullPointerException npe) {
            throw new NullPointerException("Nothing is present in a file.");
        }
//        Utils.printArr(strings);
        try {
            a = Integer.parseInt(strings[0]);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException("First summand is not a number.");
        }
        if (a > 10e9) {
            throw new NumberFormatException("First summand is more than 10e9.");
        }
        if (b > 10e9) {
            throw new NumberFormatException("Second summand is more than 10e9.");
        }
        if (strings.length < 2) {
            throw new Exception("There is no second summand.");
        }
        try {
            b = Integer.parseInt(strings[1]);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException("Second summand is not a number.");
        }
        if (strings.length > 2) {
            System.out.println("More than 2 summands present.");
        }

        BigDecimal bigDecimal = new BigDecimal(a + b);
        bufferedWriter.write(bigDecimal.toString());

        // Closing streams.
        if (fileReader != null) {
            try {
                fileReader.close();
            } catch (IOException ioe) {
                System.out.println("fileReader cannot be closed.");
            }
        }
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException ioe) {
                System.out.println("bufferedWriter cannot be closed.");
            }
        }

        System.out.println("Addition is performed successfully, check the output file.");

    }

    void multiplyAandAEndingWith5() throws Exception {

        String[] strings;

        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(new FileReader(filesPath + "/input.txt"));
        BufferedWriter bufferedWriter = null;
        bufferedWriter = new BufferedWriter(new FileWriter(filesPath + "/output.txt"));

        strings = bufferedReader.readLine().split("[ ]");

        if (strings.length > 1) {
            System.out.println("There is more than one lexem.");
        }

        if (strings.length == 0) {
            throw new Exception("No any lexem present in a file.");
        }

        int i = Integer.parseInt(strings[0]);
        if (i > 4 * 10e5) {
            throw new NumberFormatException("Number is greater than 4 * 10e5");
        }

        // If a last letter is 5,
        if (strings[0].lastIndexOf("5") == strings[0].length() - 1) {
            // Putting all the digits, except the last one.
            i = Integer.parseInt(strings[0].substring(0, strings[0].length() - 1));
            i = i * ++i;
            strings[0] = Integer.toString(i) + "25";
            bufferedWriter.write(strings[0]);
        }

        // Closing streams.
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException ioe) {
                System.out.println("fileReader cannot be closed.");
            }
        }
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException ioe) {
                System.out.println("bufferedWriter cannot be closed.");
            }
        }

    }

}