package ExpressionWithGUI;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created on 2016/4/24.
 */
public class Expression {
    //中缀表达式
    private String fixExpression;
    //后缀表达式
    private String postfixExpression;
    //创建栈
    private Stack<Character> stack;
    //符号栈
    private ArrayList<Character> symbolStack;

    public Expression(String fixExpression) {
        this.fixExpression = fixExpression;
        postfixExpression = "";
        stack = new Stack<>();
        symbolStack = new ArrayList<>();
    }

    public String getPostfixExpression(DefaultTableModel defaultTableModel){
        //变换表达式
        changeFixExpressionToPostfixExpression(defaultTableModel);
        return postfixExpression;
    }

    /**
     * 将中缀变换为后缀
     * @param defaultTableModel
     */
    private void changeFixExpressionToPostfixExpression(DefaultTableModel defaultTableModel){
        postfixExpression = "";
        StringBuffer stringBuffer = new StringBuffer();
        char c = ' ',last;

        defaultTableModel.setRowCount(0);

        for(int i=0;i<fixExpression.length();i++){
            Object[] objects = new Object[5];
            c = fixExpression.charAt(i);

            stringBuffer.append("当前字符："+c);
            objects[0] = i+1;
            objects[1] = c;

            if(i+1<fixExpression.length()){
                stringBuffer.append(" 输入区："+fixExpression.substring(i+1));
                objects[2] = fixExpression.substring(i+1);
            }

            //字符，继续读取
            if(Character.isLetter(c)||Character.isDigit(c)){
                postfixExpression += c;
            }
            //运算符，分支判断，将运算符压栈
            else{
                char top;
                if(stack.size()==0){
                    stack.push(c);
                    if(!Character.isLetter(c)){
                        symbolStack.add(c);

                        //将符号站所有符号读出
                        String sym = "";
                        for(int m=0;m<symbolStack.size();m++){
                            sym+=symbolStack.get(m);
                        }

                        defaultTableModel.addRow(new Object[]{defaultTableModel.getRowCount()+1,c,fixExpression.substring(i+1),sym,postfixExpression});
                        stringBuffer.append("\n");
                    }
                    continue;
                }
                switch (c){
                    case '+':
                    case '-':
                        top = stack.peek();
                        if(top=='*'||top=='/'){
                            last = stack.pop();
                            symbolStack.remove(symbolStack.size()-1);
                            postfixExpression += last;
                            stack.push(c);
                            symbolStack.add(c);
                        }else{
                            stack.push(c);
                            symbolStack.add(c);
                        }
                        break;
                    case '*':
                    case '/':
                        top = stack.peek();
                        if(top == '*'||top == '/'){
                            last = stack.pop();
                            symbolStack.remove(symbolStack.size()-1);
                            postfixExpression += last;
                            stack.push(c);
                            symbolStack.add(c);
                        }else{
                            stack.push(c);
                            symbolStack.add(c);
                        }
                        break;
                    case '(':
                        stack.push(c);
                        symbolStack.add(c);
                        break;
                    case ')':
                        top = stack.pop();
                        while(top!='('){
                            postfixExpression += top;
                            top = stack.pop();
                            symbolStack.remove(symbolStack.size()-1);
                        }
                        break;
                }
            }
            stringBuffer.append(" 符号栈：");

            //获取字符串
            String str = "";
            for(int n=0;n<symbolStack.size();n++){
                stringBuffer.append(symbolStack.get(n));
                str += symbolStack.get(n);
            }
            objects[3] = str;
            stringBuffer.append(" 输出区："+postfixExpression+"\n");
            objects[4] =postfixExpression;

            defaultTableModel.addRow(objects);

        }

        //将栈内剩余的数字或字符弹栈，加到表达式末尾
        while(stack.size()!=0){
            last = stack.pop();
            postfixExpression += last;
            stringBuffer.append("当前字符： 输入区： 符号栈： 输出区："+postfixExpression+"\n");
            defaultTableModel.addRow(new Object[]{defaultTableModel.getRowCount()+1,"","","",postfixExpression});
        }

        System.out.println(stringBuffer);
    }
}
