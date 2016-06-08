package EvaluationOfExpression;

/**
 * Created by jackstrom on 2016/4/24.
 */
public class ExpressionClient {
    public static void main(String[] args){
//        String param = "2+(3+2+1)*5";
        String param = "2*(3+2)-2*3";
//        String param = "a*(b+a)-a*c";
        Expression expression = new Expression(param);

        //将中缀表达式转换成后缀表达式
        String postFixExpression = expression.getPostfixExpression();
        System.out.println(param+"转化成逆波兰表达式:"+postFixExpression);

        //计算表达式的结果值
        CalculateValue calculater = new CalculateValue(postFixExpression);
        System.out.println(param+"="+calculater.calculate());
    }
}
