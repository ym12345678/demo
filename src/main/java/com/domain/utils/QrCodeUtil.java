package com.domain.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * 自动生成二维码的工具类
 *
 * @author Administrator
 */

public class QrCodeUtil {

    private QrCodeUtil(){}



    public static String createPaymentQrCode(String URL, OutputStream stream) {
        try {
            Map<EncodeHintType, String> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(URL, BarcodeFormat.QR_CODE, 260, 260, hints);
            writeToStream(bitMatrix, "jpg", stream);
            //System.out.println("搞定：" + file);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private static int width = 300;              //二维码宽度
    private static int height = 300;             //二维码高度
    private static int onColor = 0xFF000000;     //前景色
    private static int offColor = 0xFFFFFFFF;    //背景色
    private static int margin = 1;               //白边大小，取值范围0~4
    private static ErrorCorrectionLevel level = ErrorCorrectionLevel.L;  //二维码容错率


    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }


    private static BufferedImage logoMatrix(BufferedImage matrixImage) throws IOException {
        //读取二维码图片，并构建绘图对象
        Graphics2D g2 = matrixImage.createGraphics();
        int matrixWidth = matrixImage.getWidth();
        int matrixHeigh = matrixImage.getHeight();
        // 读取Logo图片
        BufferedImage logo = ImageIO.read(new File("E:\\APP.jpg"));
        //开始绘制图片
        g2.drawImage(logo, matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, null);
        //绘制
        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);// 设置笔画对象
        //指定弧度的圆角矩形
        RoundRectangle2D.Float round =
                new RoundRectangle2D.Float(matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, 20, 20);
        g2.setColor(Color.white);
        g2.draw(round);
        // 绘制圆弧矩形
        //设置logo 有一道灰色边框
        BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke2);// 设置笔画对象
        RoundRectangle2D.Float round2 =
                new RoundRectangle2D.Float(matrixWidth / 5 * 2 + 2, matrixHeigh / 5 * 2 + 2, matrixWidth / 5 - 4, matrixHeigh / 5 - 4, 20, 20);
        g2.setColor(new Color(128, 128, 128));
        g2.draw(round2);// 绘制圆弧矩形
        g2.dispose();
        matrixImage.flush();
        return matrixImage;
    }

    /**
     * 生成带logo的二维码图片
     * @param txt          //二维码内容
     * @param stream      //二维码保存绝对物理路径
     * @throws Exception
     */
    public static void generateLoginQRImage(String txt, OutputStream stream) throws Exception{
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, level);
        hints.put(EncodeHintType.MARGIN, margin);  //设置白边
        BitMatrix bitMatrix = new MultiFormatWriter().encode(txt, BarcodeFormat.QR_CODE, width, height, hints);
        writeLoginQrToStream(bitMatrix, "jpg", stream);
    }
    /**
     *
     * @param matrix 二维码矩阵相关
     * @param format 二维码图片格式
     * @param stream 二维码图片文件
     * @throws IOException
     */
    public static void writeLoginQrToStream(BitMatrix matrix,String format,OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        Graphics2D gs = image.createGraphics();

        int ratioWidth = image.getWidth()*2/10;
        int ratioHeight = image.getHeight()*2/10;
        //载入logo
        Image img = ImageIO.read(new File("E:\\APP.jpg"));
        int logoWidth = img.getWidth(null)>ratioWidth?ratioWidth:img.getWidth(null);
        int logoHeight = img.getHeight(null)>ratioHeight?ratioHeight:img.getHeight(null);

        int x = (image.getWidth() - logoWidth) / 2;
        int y = (image.getHeight() - logoHeight) / 2;

        gs.drawImage(img, x, y, logoWidth, logoHeight, null);
        gs.setColor(Color.black);
        gs.setBackground(Color.WHITE);
        gs.dispose();
        img.flush();
        if(!ImageIO.write(image, format, stream)){
            throw new IOException("Could not write an image of format " + format + " to " + stream);
        }
    }



}
