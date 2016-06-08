package ExpressionWithGUI;

import java.text.DecimalFormat;
import java.util.Stack;

/**
 * Created by jackstrom on 2016/4/24.
 */
public class CalculateValue {
    private String expression;
    private Stack<Double> stack;
    static DecimalFormat decimalFormat = new DecimalFormat("#.0000");

    public CalculateValue(String expression) {
        this.expression = expression;
        stack = new Stack<>();
    }

    /**
     * 计算表达式结果
     * @return 返回运算结果
     */
    public double calculate(){
        double retVal = 0;

        try{
            for(int i=0;i<expression.length();i++){
                char ch = expression.charAt(i);
                String num = "";
                if(Character.isDigit(ch)){
                    num += ch;
                    char nextCh = expression.charAt(i+1);
                    while(Character.isDigit(nextCh)||nextCh=='.'){
                        num+=nextCh;
                        i++;
                        nextCh = expression.charAt(i+1);
                    }
                    stack.push(Double.valueOf(num));
                }else if(ch!=' '){
                    //从栈中获取操作数
                    double secondNum = stack.pop();
                    double firstNum = stack.pop();
                    //根据不同运算符计算结果
                    switch(ch){
                        case '+':
                            stack.push(firstNum+secondNum);
                            break;
                        case '-':
                            stack.push(firstNum-secondNum);
                            break;
                        case '*':
                            stack.push(firstNum*secondNum);
                            break;
                        case '/':
                            stack.push(firstNum/secondNum);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            //弹出结果值
            retVal = stack.pop();
        }
        return retVal;
    }

    public static void main(String[] args){
        CalculateValue calculateValue = new CalculateValue("1.1 2.2+");
        System.out.println(decimalFormat.format(calculateValue.calculate()));
    }
}
