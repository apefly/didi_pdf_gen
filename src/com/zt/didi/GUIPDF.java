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
  static String sepChar = ",";

  Map<String,String> didiInfoMap = new HashMap();

  String travelInfoKey = "";

  String travel_type = "";
  String travel_date = "";
  String amount = "";
  String time = "";
  String weekday = "";
  String city = "";
  String address_from = "";
  String address_to = "";
  String miles = "";

  String root_path = "";
  String apply_date = "";
  String phone = "";


  //    JLabel label_remark = new JLabel("（请使用【退出】按钮，不要使用右上角的【X】，否则无法关闭JAVA进程）");
    JPanel buttonHeadPanel = new JPanel();
    JButton btn_Reset = new JButton("重置");
    JButton btn_SaveAsTemp = new JButton("存为样板");
    JButton btn_LoadTemp = new JButton("从样板载入");

    JPanel inputTravelInfopanel = new JPanel(new GridLayout(9,2));
    JLabel label_travel_type = new JLabel("乘车类型(eg: 快车,专车,顺风车)");
    JTextField input_travel_type = new JTextField(10);
    JLabel label_travel_date = new JLabel("行程时间 (eg: yyyy-mm-dd)");
    JTextField input_travel_date = new JTextField(10);
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

    JPanel buttonAddPanel = new JPanel();
    JButton btn_Add = new JButton("添加行程");

    JPanel showTravelInfoPanel = new JPanel();
  JTextArea text_TravelInfo = new JTextArea("");
//    JTextArea text_TravelInfo = new JTextArea("...1111111111111111111111111111111111111111111111111111");

    JPanel inputCommonInfopanel = new JPanel(new GridLayout(3,2));
    JLabel lable_root_path = new JLabel("根目录，不可为C盘下位置 (eg: D:)");
    JTextField input_root_path = new JTextField(1);
    JLabel label_apply_date = new JLabel("申请日期 (eg: yyyy-mm-dd)");
    JTextField input_apply_date = new JTextField(10);
    JLabel label_phone = new JLabel("联系电话 (eg: 1xxxxxxxxxx)");
    JTextField input_phone = new JTextField(10);

    JPanel buttonGenPanel = new JPanel();
    JButton btn_Gen = new JButton("生成PDF");

    JPanel showMessagePanel = new JPanel();
    JTextArea text_result = new JTextArea("");

    public PDFInterface(){

        buttonHeadPanel.add(btn_Reset);
        buttonHeadPanel.add(btn_SaveAsTemp);
        buttonHeadPanel.add(btn_LoadTemp);
        this.add(buttonHeadPanel);


        input_travel_type.setText("快车");
        inputTravelInfopanel.add(input_travel_type);
        inputTravelInfopanel.add(label_travel_type);
        inputTravelInfopanel.add(input_travel_date);
        inputTravelInfopanel.add(label_travel_date);
        inputTravelInfopanel.add(input_time);
        inputTravelInfopanel.add(label_time);
        inputTravelInfopanel.add(input_weekday);
        inputTravelInfopanel.add(label_weekday);
        inputTravelInfopanel.add(input_city);
        inputTravelInfopanel.add(label_city);
        inputTravelInfopanel.add(input_address_from);
        inputTravelInfopanel.add(label_address_from);
        inputTravelInfopanel.add(input_address_to);
        inputTravelInfopanel.add(label_address_to);
        inputTravelInfopanel.add(input_miles);
        inputTravelInfopanel.add(label_miles);
        inputTravelInfopanel.add(input_amount);
        inputTravelInfopanel.add(label_amount);
        this.add(inputTravelInfopanel);

        buttonAddPanel.add(btn_Add);
        this.add(buttonAddPanel);

        showTravelInfoPanel.add(text_TravelInfo);
        this.add(showTravelInfoPanel);

        input_root_path.setText("D:");
        inputCommonInfopanel.add(input_root_path);
        inputCommonInfopanel.add(lable_root_path);
        inputCommonInfopanel.add(input_apply_date);
        inputCommonInfopanel.add(label_apply_date);
        inputCommonInfopanel.add(input_phone);
        inputCommonInfopanel.add(label_phone);
        this.add(inputCommonInfopanel);

        buttonGenPanel.add(btn_Gen);
        this.add(buttonGenPanel);

        showMessagePanel.add(text_result);
        this.add(showMessagePanel);

        btn_Reset.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e){
//                inputField.setText(inputField.getText()+"7");
                input_travel_type.setText("快车");
                input_travel_date.setText("");
                input_time.setText("");
                input_weekday.setText("");
                input_city.setText("");
                input_address_from.setText("");
                input_address_to.setText("");
                input_miles.setText("");
                input_amount.setText("");

                text_TravelInfo.setText("");

                input_root_path.setText("D:");
                input_apply_date.setText("");
                input_phone.setText("");

                text_result.setText("");

            }
        });

        btn_SaveAsTemp.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e){
                try {
                    File file = new File("personal_template.txt");
                    if(file.exists()){
                        FileWriter fw = new FileWriter(file,false);
                        BufferedWriter bw = new BufferedWriter(fw);

                      if(!"".equals(input_travel_type.getText())){bw.write("input_travel_type::::"+input_travel_type.getText()+"\r\n");}
                      if(!"".equals(input_travel_date.getText())){bw.write("input_travel_date::::"+input_travel_date.getText()+"\r\n");}
                      if(!"".equals(input_time.getText())){bw.write("input_time::::"+input_time.getText()+"\r\n");}
                      if(!"".equals(input_weekday.getText())){bw.write("input_weekday::::"+input_weekday.getText()+"\r\n");}
                      if(!"".equals(input_city.getText())){bw.write("input_city::::"+input_city.getText()+"\r\n");}
                      if(!"".equals(input_address_from.getText())){bw.write("input_address_from::::"+input_address_from.getText()+"\r\n");}
                      if(!"".equals(input_address_to.getText())){bw.write("input_address_to::::"+input_address_to.getText()+"\r\n");}
                      if(!"".equals(input_miles.getText())){bw.write("input_miles::::"+input_miles.getText()+"\r\n");}
                      if(!"".equals(input_amount.getText())){bw.write("input_amount::::"+input_amount.getText()+"\r\n");}

                        if(!"".equals(input_root_path.getText())){bw.write("input_root_path::::"+input_root_path.getText()+"\r\n");}
                        if(!"".equals(input_apply_date.getText())){bw.write("input_apply_date::::"+input_apply_date.getText()+"\r\n");}
                        if(!"".equals(input_phone.getText())){bw.write("input_phone::::"+input_phone.getText()+"\r\n");}

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
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                        StringBuffer sb = new StringBuffer();
                        String text = null;
                        String[] rowInfo = new String[2];
                        while((text = bufferedReader.readLine()) != null){
                            sb.append(text);
                            System.out.println(text);
                            if(text!=null && text.indexOf("::::")>=0){
                                rowInfo = text.split("::::");

                              if("input_travel_type".equals(rowInfo[0])){input_travel_type.setText(rowInfo[1]);}
                              else if("input_travel_date".equals(rowInfo[0])){input_travel_date.setText(rowInfo[1]);}
                              else if("input_time".equals(rowInfo[0])){input_time.setText(rowInfo[1]);}
                              else if("input_weekday".equals(rowInfo[0])){input_weekday.setText(rowInfo[1]);}
                              else if("input_city".equals(rowInfo[0])){input_city.setText(rowInfo[1]);}
                              else if("input_address_from".equals(rowInfo[0])){input_address_from.setText(rowInfo[1]);}
                              else if("input_address_to".equals(rowInfo[0])){input_address_to.setText(rowInfo[1]);}
                              else if("input_miles".equals(rowInfo[0])){input_miles.setText(rowInfo[1]);}
                              else if("input_amount".equals(rowInfo[0])){input_amount.setText(rowInfo[1]);}
                                else if("input_root_path".equals(rowInfo[0])){input_root_path.setText(rowInfo[1]);}
                                else if("input_apply_date".equals(rowInfo[0])){input_apply_date.setText(rowInfo[1]);}
                                else if("input_phone".equals(rowInfo[0])){input_phone.setText(rowInfo[1]);}
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

        btn_Add.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e){
              travel_type = input_travel_type.getText();
              travel_date = input_travel_date.getText();
              time = input_time.getText();
              weekday = input_weekday.getText();
              city = input_city.getText();
              address_from = input_address_from.getText();
              address_to = input_address_to.getText();
              miles = input_miles.getText();
              amount = input_amount.getText();

              travelInfoKey = new StringBuffer("")
                  .append(travel_type).append(sepChar)
                  .append(travel_date).append(sepChar)
                  .append(time).append(sepChar)
                  .append(weekday).append(sepChar)
                  .append(city).append(sepChar)
                  .append(address_from).append(sepChar)
                  .append(address_to).append(sepChar)
                  .append(miles).append(sepChar)
                  .append(amount).append(sepChar)
                  .append("\r\n")
                  .toString();

              text_TravelInfo.append(travelInfoKey);
            }
        });

        btn_Gen.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e){
              root_path = input_root_path.getText();
              apply_date = input_apply_date.getText();
              phone = input_phone.getText();

              travel_date = input_travel_date.getText();
              amount = input_amount.getText();
              time = input_time.getText();

              didiInfoMap.put("root_path", root_path);
              didiInfoMap.put("apply_date", apply_date);
              didiInfoMap.put("phone", phone);
              didiInfoMap.put("text_TravelInfo", text_TravelInfo.getText());

              didiInfoMap.put("img_path","template/blank.jpg");

              GenPDF.pdfPrint(didiInfoMap);
            }
        });

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("滴滴 o(*￣︶￣*)o 生成器");
        this.setResizable(false);
        this.setSize(490,600);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.setVisible(true);
    }

}
