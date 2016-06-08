package ExpressionWithGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jackstrom on 2016/5/31.
 */
public class MainFrame extends JFrame{

    //显示结果用的表格
    private JTable table;
    private DefaultTableModel defaultTableModel;
    //表达式输入文本框
    private JTextField textField,paramsField;
    //显示运算结果的标签
    private JLabel result,input,param;

    private JButton button;

    private CalculateValue calculateValue;

    private Expression expression;

    private Object[] columnNames = new Object[]{"步骤","当前符号","输入区","符号栈","输出区"};

    //保存逆波兰表达式
    private String resultExpression = "";

    private JCheckBox checkBox;

    MainFrame(){
        table = new JTable();
        defaultTableModel = (DefaultTableModel) table.getModel();

        button = new JButton("转换");
        textField = new JTextField();
        result = new JLabel();
        input = new JLabel("表达式:");
        param = new JLabel("参数值");
        paramsField = new JTextField();

//        checkBox = new JCheckBox("无参数");
    }

    public void launch(){
        setting();
        this.getContentPane().add(table.getTableHeader());
        this.getContentPane().add(input);
        this.getContentPane().add(textField);
        this.getContentPane().add(button);
        this.getContentPane().add(result);
        this.getContentPane().add(table);
        this.getContentPane().add(param);
        this.getContentPane().add(paramsField);
//        this.getContentPane().add(checkBox);

        addListener();
    }

    private void addListener() {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                if(input.equals("")||input==null){
                    result.setText("表达式为空");
                    return;
                }else{
                    expression = new Expression(input);
                    resultExpression = expression.getPostfixExpression(defaultTableModel);

                    //计算表达式结果值
                    double retVal = calculate();
                    result.setText(textField.getText()+"="+retVal);
                }
            }
        });


    }

    public double calculate(){

        String srcExpression = resultExpression;
//        String params =
        String[] params = paramsField.getText().split(";");
        for(int i=0;i<params.length;i++){
            String[] letterValue = params[i].split("=");
            srcExpression = srcExpression.replace(letterValue[0],letterValue[1]+" ");
        }

System.out.println(srcExpression);

        calculateValue = new CalculateValue(srcExpression);
        return calculateValue.calculate();
    }

    public void setting(){
        setSize(800,600);
        setLayout(null);
        setLocation(200,100);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        table.setRowHeight(30);
        defaultTableModel.addColumn("步骤");
        defaultTableModel.addColumn("当前符号");
        defaultTableModel.addColumn("输入区");
        defaultTableModel.addColumn("运算符号区");
        defaultTableModel.addColumn("输出区");

        input.setBounds(30,20,60,40);
        textField.setBounds(100,20,300,40);
        button.setBounds(500,20,100,40);
        result.setBounds(530,70,200,40);

        param.setBounds(30,70,60,40);
        paramsField.setBounds(100,70,300,40);

//        checkBox.setBounds(420,70,100,40);

        table.setBounds(30,150,740,400);
        table.getTableHeader().setBounds(30,120,740,30);
    }

    public static void main(String[] args){
        MainFrame mainFrame = new MainFrame();
        mainFrame.launch();
    }
}
