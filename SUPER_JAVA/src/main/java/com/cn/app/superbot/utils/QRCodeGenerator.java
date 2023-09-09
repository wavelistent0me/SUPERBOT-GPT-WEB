package com.cn.app.superbot.utils;

import com.alipay.api.internal.util.codec.Base64;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * The type Qr code generator.
 *
 * @author 欧渐风.
 * @email 2074055628 @qq.com.
 */
public class QRCodeGenerator {


    /**
     * Generate qr code string.
     *
     * @param url the url
     * @return the string
     * @throws WriterException the writer exception
     * @throws IOException     the io exception
     */
    public static String generateQRCode(final String url) throws WriterException, IOException {
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 400, 400, hints);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return "data:image/png;base64," + Base64.encodeBase64String(bytes);
    }


}
