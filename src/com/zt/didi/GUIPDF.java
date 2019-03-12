package com.zt.didi;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GUIPDF {
    public static void main(String[] args){
        new  PDFInterface();
    }
}

class PDFInterface extends JFrame {
//    JLabel label_remark = new JLabel("（请使用【退出】按钮，不要使用右上角的【X】，否则无法关闭JAVA进程）");

    JLabel lable_root_path = new JLabel("生成盘符，不可为C (eg: D)");
    JTextField input_root_path = new JTextField(1);
    JLabel label_apply_date = new JLabel("申请日期 (eg: yyyy-mm-dd)");
    JTextField input_apply_date = new JTextField(10);
    JLabel label_travel_date = new JLabel("行程时间 (eg: yyyy-mm-dd)");
    JTextField input_travel_date = new JTextField(10);
    JLabel label_phone = new JLabel("联系电话 (eg: 1xxxxxxxxxx)");
    JTextField input_phone = new JTextField(10);
    JLabel label_amount = new JLabel("行程金额 (eg: xxx.xx)");
    JTextField input_amount = new JTextField(10);
    JLabel label_time = new JLabel("出行时间 (eg: hh:mm)");
    JTextField input_time = new JTextField(10);
    JLabel label_weekday = new JLabel("出行周几 (eg: 周x)");
    JTextField input_weekday = new JTextField(10);
    JLabel label_city = new JLabel("出行城市 (eg: xx市)");
    JTextField input_city = new JTextField(10);
    JLabel label_address_from = new JLabel("行程起点 (eg: 参照软件)");
    JTextField input_address_from = new JTextField(20);
    JLabel label_address_to = new JLabel("行程终点 (eg: 参照软件)");
    JTextField input_address_to = new JTextField(20);
    JLabel label_miles = new JLabel(" 里  程  (eg: 参照软件xx.x)");
    JTextField input_miles = new JTextField(10);

    JButton button = new JButton("生成PDF");
    JButton button1 = new JButton("测试值");

    JTextArea text_result = new JTextArea("...");

    public PDFInterface(){

//        JPanel resultPanel = new JPanel();
//        resultPanel.add(text_result);
//        this.add(resultPanel);

        JPanel buttonpanel = new JPanel();
        buttonpanel.add(button);
        buttonpanel.add(button1);
        this.add(buttonpanel);

        JPanel inputpanel = new JPanel(new GridLayout(12,2));

        input_root_path.setText("D");
        inputpanel.add(input_root_path);
        inputpanel.add(lable_root_path);
        inputpanel.add(input_apply_date);
        inputpanel.add(label_apply_date);
        inputpanel.add(input_travel_date);
        inputpanel.add(label_travel_date);
        inputpanel.add(input_phone);
        inputpanel.add(label_phone);
        inputpanel.add(input_amount);
        inputpanel.add(label_amount);
        inputpanel.add(input_time);
        inputpanel.add(label_time);
        inputpanel.add(input_weekday);
        inputpanel.add(label_weekday);
        inputpanel.add(input_city);
        inputpanel.add(label_city);
        inputpanel.add(input_address_from);
        inputpanel.add(label_address_from);
        inputpanel.add(input_address_to);
        inputpanel.add(label_address_to);
        inputpanel.add(input_miles);
        inputpanel.add(label_miles);
        this.add(inputpanel);

        JPanel remarkPanel = new JPanel();
        remarkPanel.add(text_result);
        this.add(remarkPanel);

        button.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e){
//                inputField.setText(inputField.getText()+"7");
                Map<String,String> map = new HashMap();
                map.put("root_path",input_root_path.getText());
                map.put("apply_date",input_apply_date.getText());
                String travel_date = input_travel_date.getText();
                map.put("date_from",travel_date);
                map.put("date_to",input_travel_date.getText());
                map.put("phone",input_phone.getText());
                map.put("count","1");
                map.put("amount",input_amount.getText());

                String time = input_time.getText();
                if(travel_date.length()==10){
                    time = travel_date.substring(5) + " " + time;
                }
                map.put("time",time);
                map.put("week",input_weekday.getText());
                map.put("city",input_city.getText());
                map.put("address_from",input_address_from.getText());
                map.put("address_to",input_address_to.getText());
                map.put("miles",input_miles.getText());

                boolean isValid = true;
                Iterator iter = map.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Object val = entry.getValue();
                    if(val==null || "".equals(String.valueOf(val))){
                        isValid = false;
                        continue;
                    }
                }

                if(isValid){
                    Map<String,Object> o=new HashMap();
                    o.put("datemap",map);
                    GenPDF.pdfout(o);
                    text_result.setText("生成成功,产生文件路径在：\r\n"+GenPDF.getOutPutPath());
                }else{
                    text_result.setText("生成失败,录入项不可为空");
                }
            }
        });

        button1.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e){
                input_apply_date.setText("2019-03-12");
                input_travel_date.setText("2019-02-27");
                input_phone.setText("13812345678");
                input_amount.setText("123.45");
                input_time.setText("21:35");
                input_weekday.setText("周三");
                input_city.setText("北京市");
                input_address_from.setText("北京理工大学中关村国防科技园");
                input_address_to.setText("回龙观流星花园三区 - 西门");
                input_miles.setText("27.1");
            }
        });

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("滴滴 o(*￣︶￣*)o 生成器");
        this.setResizable(false);
        this.setSize(490,400);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.setVisible(true);
    }

}
