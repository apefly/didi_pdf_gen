package com.zt.didi;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
/**
 * Created by wangpeng on 2018/02/01.
 */
public class GenPDF {
    static String tOutputPDFPath = "";
    static String sepChar = ",";

    // 利用模板生成pdf
    public static void pdfPrint(Map<String,String> o) {
        String root_path = o.get("root_path");
        String apply_date = o.get("apply_date");
        String phone = o.get("phone");
        String text_TravelInfo = o.get("text_TravelInfo");
        String img_Path = o.get("img_path");

        String[] travelInfos = text_TravelInfo.replace("\r\n","\n").split("\n");

        int rows = travelInfos.length;

        String date_from = "";
        String date_to = "";
        BigDecimal total_Amount = new BigDecimal("0");

        // 模板路径
        String templatePath = "template/didi_template.pdf";
        // 生成的新文件路径
        tOutputPDFPath = root_path+"/滴滴出行行程报销单_"+apply_date+".pdf";
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            BaseFont bf = BaseFont.createFont("msyh.ttc,1" , BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font FontChinese = new Font(bf, 10, Font.NORMAL);

            out = new FileOutputStream(tOutputPDFPath);// 输出流
            reader = new PdfReader(templatePath);// 读取pdf模板
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();
            //文字类的内容处理
            form.addSubstitutionFont(bf);

            for(int i=1; i<=rows; i++){
              String curTravelInfo = travelInfos[i-1];
              String[] curTravelMsg = curTravelInfo.split(sepChar);
              int t_index=0;
              form.setField("num"+i, String.valueOf(i));
              form.setField("type"+i, curTravelMsg[t_index++]);
              form.setField("time"+i, curTravelMsg[t_index++].substring(5,10) + " " + curTravelMsg[t_index++]);
              form.setField("week"+i, curTravelMsg[t_index++]);
              form.setField("city"+i, curTravelMsg[t_index++]);
              form.setField("address_from"+i, curTravelMsg[t_index++]);
              form.setField("address_to"+i, curTravelMsg[t_index++]);
              form.setField("miles"+i, curTravelMsg[t_index++]);
              form.setField("amount"+i, curTravelMsg[t_index++]);
              if(i==1){
                  date_from = curTravelMsg[1];
                  date_to = curTravelMsg[1];
              }else{
                  if(date_from.compareTo(curTravelMsg[1])>0){date_from = curTravelMsg[1];}
                  if(date_to.compareTo(curTravelMsg[1])<0){date_to = curTravelMsg[1];}
              }
              total_Amount = total_Amount.add(BigDecimal.valueOf(Double.valueOf(curTravelMsg[8])));
            }
            form.setField("apply_date", apply_date);
            form.setField("date_from", date_from);
            form.setField("date_to", date_to);
            form.setField("phone", phone);
            form.setField("count", String.valueOf(rows));
            form.setField("total_amount", String.format("%.2f", total_Amount.doubleValue()));

            //图片类的内容处理
            int pageNo = form.getFieldPositions("img").get(0).page;
            Rectangle signRect = form.getFieldPositions("img").get(0).position;
            float x = signRect.getLeft();
            float y = signRect.getBottom();
            float wide = signRect.getWidth();
            float height = signRect.getHeight();
//          System.out.println(x);
//          System.out.println(y);
//          System.out.println(signRect.getWidth());
//          System.out.println(signRect.getHeight());
            //根据路径读取图片
            Image image = Image.getInstance(img_Path);
            //获取图片页面
            PdfContentByte under = stamper.getOverContent(pageNo);
            //图片大小自适应
//            image.scaleAbsolute(signRect.getWidth(), signRect.getHeight());
            image.scaleAbsolute(signRect.getWidth(), -signRect.getHeight()*(10-rows));
//            image.scaleToFit(signRect.getWidth(), signRect.getHeight());
            //添加图片
//            image.setAbsolutePosition(x, y);
            float buffer_y = 0;
            if(rows==5 || rows==7){buffer_y = -1;}
            image.setAbsolutePosition(x, y - (rows - 2) * height + buffer_y);
            under.addImage(image);

            stamper.setFormFlattening(true);// 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
            stamper.close();
            Document doc = new Document();
//            Font font = new Font(bf, 32);
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();

        } catch (IOException e) {
            System.out.println(e);
        } catch (DocumentException e) {
            System.out.println(e);
        }
    }

    public static String getOutPutPath(){
        return tOutputPDFPath;
    }

    public static void main(String[] args) {

        //上面数字  carlibri 29
        //下面数字  carlibri 26
        //下面中文  微软雅黑 22
        Map<String,String> map = new HashMap();
        map.put("root_path","D:");
        map.put("apply_date","2019-01-01");
        map.put("phone","18518760178");

        String textInfo = "快车,2019-01-01,20:15,周一,北京市,北京理工大学中关村国防科技园,天骄骏园,28.1,85.12"+"\r\n"
            +"快车,2019-01-02,20:16,周二,北京市,北京理工大学中关村国防科技园,天骄骏园,28.1,85.12"+"\r\n"
            +"快车,2019-01-03,20:17,周三,北京市,北京理工大学中关村国防科技园,天骄骏园,28.1,85.12"+"\r\n"
            +"快车,2019-01-04,20:18,周四,北京市,北京理工大学中关村国防科技园,天骄骏园,28.1,85.12"+"\r\n"
            +"快车,2019-01-05,20:19,周五,北京市,北京理工大学中关村国防科技园,天骄骏园,28.1,85.12"+"\r\n"
//            +"快车,2019-01-06,20:20,周六,北京市,北京理工大学中关村国防科技园,天骄骏园,28.1,85.12"+"\r\n"
//            +"快车,2019-01-07,20:21,周日,北京市,北京理工大学中关村国防科技园,天骄骏园,28.1,85.12"+"\r\n"
//            +"快车,2019-01-08,20:22,周一,北京市,北京理工大学中关村国防科技园,天骄骏园,28.1,85.12"+"\r\n"
//            +"快车,2019-01-09,20:23,周二,北京市,北京理工大学中关村国防科技园,天骄骏园,28.1,85.12"+"\r\n"
            + "";
        map.put("text_TravelInfo",textInfo);

//        Map<String,String> map2 = new HashMap();
//        map2.put("img","template/blank.jpg");

//        o.put("imgmap",map2);
        map.put("img_path","template/blank.jpg");

        pdfPrint(map);
    }
}