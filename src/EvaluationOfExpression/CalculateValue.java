package EvaluationOfExpression;

import java.util.Stack;

/**
 * Created by jackstrom on 2016/4/24.
 */
public class CalculateValue {
    private String expression;
    private Stack<Integer> stack;

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
                if(Character.isDigit(ch)){
                    stack.push(Integer.valueOf(ch-48));
                }else{
                    //从栈中获取操作数
                    int secondNum = stack.pop();
                    int firstNum = stack.pop();
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
}
