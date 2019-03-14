package com.zt.didi;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
/**
 * Created by wangpeng on 2018/02/01.
 */
public class GenPDF {
    static String tOutputPDFPath = "";
    // 利用模板生成pdf
    public static void pdfout(Map<String,Object> o) {
        Map<String,String> datemap = (Map<String,String>)o.get("datemap");
//        datemap.put("empty"," ");
        // 模板路径
        String templatePath = "didi_template.pdf";
        // 生成的新文件路径
        tOutputPDFPath = datemap.get("root_path")+":/滴滴出行行程报销单_"+(datemap.get("date_from"))+".pdf";
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
            for(String key : datemap.keySet()){
                String value = datemap.get(key);
                form.setField(key,value);
            }
//            //图片类的内容处理
//            Map<String,String> imgmap = (Map<String,String>)o.get("imgmap");
//            for(String key : imgmap.keySet()) {
//                String value = imgmap.get(key);
//                String imgpath = value;
//                int pageNo = form.getFieldPositions(key).get(0).page;
//                Rectangle signRect = form.getFieldPositions(key).get(0).position;
//                float x = signRect.getLeft();
//                float y = signRect.getBottom();
//                //根据路径读取图片
//                Image image = Image.getInstance(imgpath);
//                //获取图片页面
//                PdfContentByte under = stamper.getOverContent(pageNo);
//                //图片大小自适应
//                image.scaleToFit(signRect.getWidth(), signRect.getHeight());
//                //添加图片
//                image.setAbsolutePosition(x, y);
//                under.addImage(image);
//            }
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
        map.put("root_path","D");
        map.put("apply_date","2019-01-01");
        map.put("date_from","2019-01-02");
        map.put("date_to","2019-01-02");
        map.put("phone","18518760178");
        map.put("count","1");
        map.put("amount","111.11");
        map.put("time","01-01 21:11");
        map.put("week","周四");
        map.put("city","北京市");
        map.put("address_from","国防科技园");
        map.put("address_to","天骄骏园");
        map.put("miles","37");

//        Map<String,String> map2 = new HashMap();
//        map2.put("img","c:/50336.jpg");

        Map<String,Object> o=new HashMap();
        o.put("datemap",map);
//        o.put("imgmap",map2);
        pdfout(o);
    }
}