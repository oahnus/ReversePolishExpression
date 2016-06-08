package EvaluationOfExpression;

import java.util.Stack;

/**
 * Created by jackstrom on 2016/4/24.
 */
public class Expression {
    //中缀表达式
    private String fixExpression;
    //后缀表达式
    private String postfixExpression;
    //创建栈
    private Stack<Character> stack;

    public Expression(String fixExpression) {
        this.fixExpression = fixExpression;
        postfixExpression = "";
        stack = new Stack<>();
    }

    public String getPostfixExpression(){
        //变换表达式
        changeFixExpressionToPostfixExpression();
        return postfixExpression;
    }

    /**
     * 将中缀变换为后缀
     */
    private void changeFixExpressionToPostfixExpression(){
        postfixExpression = "";
        char c = ' ';
        for(int i=0;i<fixExpression.length();i++){
            c = fixExpression.charAt(i);
//            //数字字符，继续读取
            if(Character.isDigit(c)){
                postfixExpression += c;
            }
            //运算符，分支判断，将运算符压栈
            else{
                char top;
                if(stack.size()==0){
                    stack.push(c);
                    continue;
                }
                switch (c){
                    case '+':
                    case '-':
                        top = stack.peek();
                        if(top=='*'||top=='/'){
                            postfixExpression += stack.pop();
                            stack.push(c);
                        }else{
                            stack.push(c);
                        }
                        break;
                    case '*':
                    case '/':
                        top = stack.peek();
                        if(top == '*'||top == '/'){
                            postfixExpression += stack.pop();
                            stack.push(c);
                        }else{
                            stack.push(c);
                        }
                        break;
                    case '(':
                        stack.push(c);
                        break;
                    case ')':
                        top = stack.pop();
                        while(top!='('){
                            postfixExpression += top;
                            top = stack.pop();
                        }
                        break;
                }
            }
        }
        //将栈内剩余的数字或字符弹栈，加到表达式末尾
        while(stack.size()!=0){
            postfixExpression += stack.pop();
        }
    }
}
