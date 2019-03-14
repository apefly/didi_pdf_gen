package com.zt.didi;

import java.io.*;
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

    JLabel lable_root_path = new JLabel("根目录，不可为C盘下位置 (eg: D:)");
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
    JButton button1 = new JButton("重置");
    JButton btn_SaveAsTemp = new JButton("存为样板");
    JButton btn_LoadTemp = new JButton("从样板载入");

    JTextArea text_result = new JTextArea("...");

    public PDFInterface(){

//        JPanel resultPanel = new JPanel();
//        resultPanel.add(text_result);
//        this.add(resultPanel);

        JPanel buttonpanel = new JPanel();
        buttonpanel.add(button);
        buttonpanel.add(button1);
        buttonpanel.add(btn_SaveAsTemp);
        buttonpanel.add(btn_LoadTemp);
        this.add(buttonpanel);

        JPanel inputpanel = new JPanel(new GridLayout(12,2));

        input_root_path.setText("D:");
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
                input_root_path.setText("D:");
                input_apply_date.setText("");
                input_travel_date.setText("");
                input_phone.setText("");
                input_amount.setText("");
                input_time.setText("");
                input_weekday.setText("");
                input_city.setText("北京市");
                input_address_from.setText("北京理工大学中关村国防科技园");
                input_address_to.setText("");
                input_miles.setText("");
            }
        });

        btn_SaveAsTemp.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e){
                try {
                    File file = new File("personal_template.txt");
                    if(file.exists()){
                        FileWriter fw = new FileWriter(file,false);
                        BufferedWriter bw = new BufferedWriter(fw);

                        if(!"".equals(input_root_path.getText())){bw.write("input_root_path::::"+input_root_path.getText()+"\r\n");}
                        if(!"".equals(input_apply_date.getText())){bw.write("input_apply_date::::"+input_apply_date.getText()+"\r\n");}
                        if(!"".equals(input_travel_date.getText())){bw.write("input_travel_date::::"+input_travel_date.getText()+"\r\n");}
                        if(!"".equals(input_phone.getText())){bw.write("input_phone::::"+input_phone.getText()+"\r\n");}
                        if(!"".equals(input_amount.getText())){bw.write("input_amount::::"+input_amount.getText()+"\r\n");}
                        if(!"".equals(input_time.getText())){bw.write("input_time::::"+input_time.getText()+"\r\n");}
                        if(!"".equals(input_weekday.getText())){bw.write("input_weekday::::"+input_weekday.getText()+"\r\n");}
                        if(!"".equals(input_city.getText())){bw.write("input_city::::"+input_city.getText()+"\r\n");}
                        if(!"".equals(input_address_from.getText())){bw.write("input_address_from::::"+input_address_from.getText()+"\r\n");}
                        if(!"".equals(input_address_to.getText())){bw.write("input_address_to::::"+input_address_to.getText()+"\r\n");}
                        if(!"".equals(input_miles.getText())){bw.write("input_miles::::"+input_miles.getText()+"\r\n");}
                        if(!"".equals(input_apply_date.getText())){bw.write("input_apply_date::::"+input_apply_date.getText()+"\r\n");}

                        bw.close(); fw.close();
                        System.out.println("test1 done!");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    // TODO: handle exception
                }
            }
        });

        btn_LoadTemp.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e){
                File personalTemp = new File("personal_template.txt");
                if(personalTemp.isFile() && personalTemp.exists()){
                    try {
                        FileInputStream fileInputStream = new FileInputStream(personalTemp);
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK");
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                        StringBuffer sb = new StringBuffer();
                        String text = null;
                        String[] rowInfo = new String[2];
                        while((text = bufferedReader.readLine()) != null){
                            sb.append(text);
                            System.out.println(text);
                            if(text!=null && text.indexOf("::::")>=0){
                                rowInfo = text.split("::::");
                                if("input_root_path".equals(rowInfo[0])){input_root_path.setText(rowInfo[1]);}
                                else if("input_apply_date".equals(rowInfo[0])){input_apply_date.setText(rowInfo[1]);}
                                else if("input_travel_date".equals(rowInfo[0])){input_travel_date.setText(rowInfo[1]);}
                                else if("input_phone".equals(rowInfo[0])){input_phone.setText(rowInfo[1]);}
                                else if("input_amount".equals(rowInfo[0])){input_amount.setText(rowInfo[1]);}
                                else if("input_time".equals(rowInfo[0])){input_time.setText(rowInfo[1]);}
                                else if("input_weekday".equals(rowInfo[0])){input_weekday.setText(rowInfo[1]);}
                                else if("input_city".equals(rowInfo[0])){input_city.setText(rowInfo[1]);}
                                else if("input_address_from".equals(rowInfo[0])){input_address_from.setText(rowInfo[1]);}
                                else if("input_address_to".equals(rowInfo[0])){input_address_to.setText(rowInfo[1]);}
                                else if("input_miles".equals(rowInfo[0])){input_miles.setText(rowInfo[1]);}
                            }
                        }
                        if(sb==null || "".equals(sb.toString())){
                            text_result.setText("未设定过模板");
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        // TODO: handle exception
                    }
                }
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
