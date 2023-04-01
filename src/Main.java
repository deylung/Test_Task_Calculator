/**
 * @author Sergey Mihaylov
 *
 */


import java.util.Scanner;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args){
        try {
            System.out.println("Калькулятор для выражений вида: \"Число1 Операция Число2\", через пробел. Допускаются числа от 1 до 10 или от I до X включительно. Операции: + - * /");
            System.out.print("Введите выражение: ");
            Scanner scanner = new Scanner(System.in);
            String calcString = scanner.nextLine();
            String calc = calc(calcString);
            System.out.println("Ответ: " + calc);
        }
        catch(CalcException e){

        }
    }
    public static int calcOperation(int num1, String operator, int num2){
        int result;
        switch (operator) {
            case "+":
                result = num1+num2;
                break;
            case "-":
                result = num1-num2;
                break;
            case "*":
                result = num1*num2;
                break;
            case "/":
                result = num1/num2;
                break;
            default:
                throw new AssertionError();
        }
        return result;
    }
    public static String calc(String input) throws CalcException{
        int num1, num2;
        String operator;
        boolean isRomanInput; // ---Признак, что числа римские
        Parse parse = new Parse();
        if (input.length()< 5){
            throw new CalcException("throws Exception //т.к. в строке отсутствует пробел между оператором");
        }
        //---разбиваем исходное выражение String по разделителю " "
        List<String> inputItems = Arrays.asList(input.split(" "));
        if (inputItems.size()==1){
            throw new CalcException("throws Exception //т.к. строка не является математической операцией");
        }
        //---проверка, что создалось 3 элемента: число1, оператор, число2, иначе исключение
        if (inputItems.size()!=3){
            throw new CalcException("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }


        //--- проверка оператора, должен быть: + - * /
        if (parse.checkOperator(inputItems.get(1))){
            operator = inputItems.get(1);
        } else {
            throw new CalcException("throws Exception //т.к. оператор '" + inputItems.get(1) + "' не корректен, должен быть: + - * / ");
        }



        //---проверка чисел, должны быть оба арабские или оба римские
        if (parse.isNumeric(inputItems.get(0)) && parse.isNumeric(inputItems.get(2))){      //---проверяем, что оба числа арабские
            num1 = Integer.parseInt(inputItems.get(0));
            num2 = Integer.parseInt(inputItems.get(2));
            isRomanInput = false;
        } else if (parse.isRoman(inputItems.get(0)) && parse.isRoman(inputItems.get(2))){   //---проверяем, что оба числа римские
            num1 = parse.romeToArabConvert(inputItems.get(0));
            num2 = parse.romeToArabConvert(inputItems.get(2));
            isRomanInput = true;
        } else {    //--- числа не соответствуют
            throw new CalcException("throws Exception //т.к. используются одновременно разные системы счисления ");
        }

        //---проверка чисел, должны быть от 1 до 10 включительно
        if (!(num1>=1 && num1<=10)){
            throw new CalcException("throws Exception //т.к. число #1 должно быть от 1 до 10 или от I до X включительно");
        }

        if (!(num2>=1 && num2<=10)){
            throw new CalcException("throws Exception //т.к. число #2 должно быть от 1 до 10 или от I до X включительно");
        }

        //--- получаем результат
        int res = calcOperation(num1, operator, num2);

        //--- если числа римские, то конвертируем и возвращаем результат
        if (isRomanInput){
            if (res<0){
                throw new CalcException("throws Exception //т.к. в римской системе нет отрицательных чисел");
            }
            return parse.arabToRomeConvert(Math.abs(res));
        }

        //--- возвращаем ответ - арабское число
        return String.valueOf(res);
    }


}
