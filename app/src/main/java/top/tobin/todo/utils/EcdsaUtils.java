package top.tobin.todo.utils;

import android.util.Log;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EcdsaUtils {

    public static final String EC_ALGORITHM = "EC";
    //摘要算法
    public static final String SIGNATURE = "SHA256withECDSA";
    //公钥
//    public static final String publickKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEwReijPArmd6xzhfQpOMQpDulr2+u4fN6b0DWSQwd7mTCLCdqGrYK04qb4QSN5Yg6e3jH5YnC5RGsAPKlFBVJ3A==";

    public static final String publickKey = "-----BEGIN CERTIFICATE-----\n" +
            "MIIDQDCCAuegAwIBAgINAIZRwQwPjNFMWV9aETAKBggqhkjOPQQDAjCBpzELMAkG\n" +
            "A1UEBhMCQ04xETAPBgNVBAgTCFpoZWppYW5nMREwDwYDVQQHEwhIYW5nemhvdTEm\n" +
            "MCQGA1UEChMdR2VlbHkgQXV0b21vYmlsZSBIb2xkaW5ncyBMdGQxGzAZBgNVBAsT\n" +
            "EkdlZWx5IFRydXN0IENlbnRlcjEtMCsGA1UEAxMkRXh0ZXJuYWwgU2VydmljZXMg\n" +
            "SXNzdWluZyBRQSBDQSAtIENOMCIYDzIwMjAwODIwMDgzMDM1WhgPMjAzMDA4MjAw\n" +
            "ODMwMzVaMIHoMQswCQYDVQQGEwJDTjEVMBMGCgmSJomT8ixkARkWBUdlZWx5MR0w\n" +
            "GwYKCZImiZPyLGQBGRYNQ29ubmVjdGVkIENhcjERMA8GA1UECBMIWmhlSmlhbmcx\n" +
            "ETAPBgNVBAcTCEhhbmdaaG91MS4wLAYDVQQKEyVaaGVqaWFuZyBHZWVseSBIb2xk\n" +
            "aW5nIEdyb3VwIENvLiwgTHRkMSYwJAYDVQQLEx1HZWVseSBBdXRvbW9iaWxlIEhv\n" +
            "bGRpbmdzIEx0ZDElMCMGA1UEAxMcR2VlbHkgT1RBIFNpZ25pbmcgU2VydmljZSBR\n" +
            "QTBZMBMGByqGSM49AgEGCCqGSM49AwEHA0IABIJp8znKIY0b9P8NriOV4I2IDNeO\n" +
            "cR5kohufud+yz3W+cwQaqp0QXzva7eeP08lDsbPa9aNBiIRL0wQxploWPfWjgbAw\n" +
            "ga0wCQYDVR0TBAIwADAdBgNVHQ4EFgQUY/MCVTX6Y+8yuXkAb6k2AJENhHUwFgYD\n" +
            "VR0lAQH/BAwwCgYIKwYBBQUHAwMwOwYIKwYBBQUHAQEELzAtMCsGCCsGAQUFBzAB\n" +
            "hh9odHRwOi8vb2NzcC50ZXN0LmdlZWx5LmNvbToyNTYwMB8GA1UdIwQYMBaAFDXv\n" +
            "HktGA8Rz45RMEj90blWm/RIfMAsGA1UdDwQEAwID+DAKBggqhkjOPQQDAgNHADBE\n" +
            "AiBz/u9Auah52yv0mz8bBLMbCyRtCYc7gKz0UTPb7ZOiTwIgUahCijEmtZRiyoxw\n" +
            "vPlBBQDK48Lt/rkMrRAwrkNJ4OQ=\n" +
            "-----END CERTIFICATE-----";
    //文件加签后数据
    public static final String fileSign = "3046022100EA09D0747B662A97954E2361E7800BEC296C72572EC0A76A3E72A32C3DF54DE7022100BEC32622B4B266FA9929230CF1847B39B7952F19E737760D8C51F6E7822ECB09";

    public static final int BLOCK_SIZE = 1024 *4;

    /**
     * 验签
     * @param publicKey 公钥文本
     * @param filePath VBF文件路径
     * @param dataSiag vbf加签后数据
     * @return
     */
    public static boolean eccVerify(String publicKey,String filePath,String dataSiag){
        Security.removeProvider("BC");
        Security.addProvider(new BouncyCastleProvider());
        try {
            InputStream fis = new FileInputStream(filePath);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream certStream = new ByteArrayInputStream(publicKey.getBytes());
            X509Certificate cert = (X509Certificate) cf.generateCertificate(certStream);
            PublicKey key = cert.getPublicKey();
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
//            KeyFactory keyFactory = KeyFactory.getInstance(EC_ALGORITHM,"BC");
//            PublicKey key = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(SIGNATURE,"BC");
            signature.initVerify(key);
            byte[] buffer = new byte[BLOCK_SIZE];
            int read = fis.read(buffer,0,BLOCK_SIZE);
            while (read > -1){
                signature.update(buffer,0,read);
                read = fis.read(buffer,0,BLOCK_SIZE);
            }
            fis.close();
            return signature.verify(Hex.decode(dataSiag));
        } catch (Exception e) {
            Log.i("TAG","SHA256 eccVerify exception:"+e.toString());
        }
        return false;
    }

}
