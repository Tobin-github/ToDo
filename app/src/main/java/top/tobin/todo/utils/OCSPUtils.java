package top.tobin.todo.utils;

import android.util.Log;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.ocsp.BasicOCSPResponse;
import org.bouncycastle.asn1.ocsp.CertID;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.asn1.ocsp.OCSPRequest;
import org.bouncycastle.asn1.ocsp.OCSPResponse;
import org.bouncycastle.asn1.ocsp.OCSPResponseStatus;
import org.bouncycastle.asn1.ocsp.SingleResponse;
import org.bouncycastle.asn1.ocsp.TBSRequest;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.cert.ocsp.BasicOCSPResp;
import org.bouncycastle.cert.ocsp.CertificateID;
import org.bouncycastle.cert.ocsp.CertificateStatus;
import org.bouncycastle.cert.ocsp.OCSPException;
import org.bouncycastle.cert.ocsp.OCSPResp;
import org.bouncycastle.cert.ocsp.RevokedStatus;
import org.bouncycastle.cert.ocsp.SingleResp;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.cert.CRLException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;


import javax.security.auth.x500.X500Principal;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import top.tobin.common.utils.LogUtil;

public class OCSPUtils {
    public static final String certStr = "-----BEGIN CERTIFICATE-----\n" +
            "MIIDPjCCAuOgAwIBAgINAPBrqLP6QyUnDUCaHTAKBggqhkjOPQQDAjCBpzELMAkG\n" +
            "A1UEBhMCQ04xETAPBgNVBAgTCFpoZWppYW5nMREwDwYDVQQHEwhIYW5nemhvdTEm\n" +
            "MCQGA1UEChMdR2VlbHkgQXV0b21vYmlsZSBIb2xkaW5ncyBMdGQxGzAZBgNVBAsT\n" +
            "EkdlZWx5IFRydXN0IENlbnRlcjEtMCsGA1UEAxMkRXh0ZXJuYWwgU2VydmljZXMg\n" +
            "SXNzdWluZyBRQSBDQSAtIENOMB4XDTIzMDYzMDA1MjgyOFoXDTMzMDYzMDA1Mjgy\n" +
            "OFowgd4xCzAJBgNVBAYTAkNOMREwDwYDVQQIDAhaaGVqaWFuZzERMA8GA1UEBwwI\n" +
            "SGFuZ3pob3UxJjAkBgNVBAoMHUdlZWx5IEF1dG9tb2JpbGUgSG9sZGluZ3MgTHRk\n" +
            "MRswGQYDVQQLDBJHZWVseSBUcnVzdCBDZW50ZXIxHTAbBgoJkiaJk/IsZAEZFg1D\n" +
            "b25uZWN0ZWQgQ2FyMRUwEwYKCZImiZPyLGQBGRYFR2VlbHkxLjAsBgNVBAMMJVRl\n" +
            "c3QgRm9yIEdlZWx5IE9UQSBTaWduaW5nIFNlcnZpY2UgUUEwWTATBgcqhkjOPQIB\n" +
            "BggqhkjOPQMBBwNCAARuIZB4CpkIzFFg2BhsgTg9Y34SEi2lOqmM6HTWaNQJu2ey\n" +
            "dWifnN9765bZWkJK++u1P4YqM2KgTpBrpjui/Veho4G6MIG3MAkGA1UdEwQCMAAw\n" +
            "HQYDVR0OBBYEFNEKX07zIZ5x5vz5AN/bcOvUH1PHMA4GA1UdDwEB/wQEAwIDqDAd\n" +
            "BgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUHAwIwOwYIKwYBBQUHAQEELzAtMCsG\n" +
            "CCsGAQUFBzABhh9odHRwOi8vb2NzcC50ZXN0LmdlZWx5LmNvbToyNTYwMB8GA1Ud\n" +
            "IwQYMBaAFDXvHktGA8Rz45RMEj90blWm/RIfMAoGCCqGSM49BAMCA0kAMEYCIQDj\n" +
            "ON91VHN00bgVNo8d9+PrVVFFjB+hq6NiBGBzVZeVJgIhAPCRM8ZSJrzlCyp07rxm\n" +
            "bl10usQIPGwybC+6Cud4jEYm\n" +
            "-----END CERTIFICATE-----";
    private static final String urlStr = "http://pki-ocsp.geely.com:2560";
    private static final String urlStrTest = "http://ocsp.test.geely.com:2560";

    public static void testeee() throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream certStream = new ByteArrayInputStream(certStr.getBytes());
        X509Certificate certificate = (X509Certificate) cf.generateCertificate(certStream);

        byte[] crlDistributionPointsExtension = certificate.getExtensionValue(Extension.authorityInfoAccess.getId());
        ASN1InputStream aIn = new ASN1InputStream(new ByteArrayInputStream(crlDistributionPointsExtension));
        ASN1OctetString octs = (ASN1OctetString) aIn.readObject();
        aIn = new ASN1InputStream(new ByteArrayInputStream(octs.getOctets()));
        ASN1Primitive asn1Primitive = aIn.readObject();
        ASN1Sequence AccessDescriptions = (ASN1Sequence) asn1Primitive;
        for (int i = 0; i < AccessDescriptions.size(); i++) {
            ASN1Sequence AccessDescription = (ASN1Sequence) AccessDescriptions.getObjectAt(i);
            if (AccessDescription.getObjectAt(0) instanceof ASN1ObjectIdentifier) {
                ASN1ObjectIdentifier id = (ASN1ObjectIdentifier) AccessDescription.getObjectAt(0);
                ASN1Primitive description = (ASN1Primitive) AccessDescription.getObjectAt(1);

                ASN1TaggedObject taggedObject = (ASN1TaggedObject) description;
                String accessLocation = new String(ASN1OctetString.getInstance(taggedObject, false).getOctets(), "ISO-8859-1");

                // 区别于itext源码，不获取ldap协议地址
                if (accessLocation.startsWith("ldap")) {
                    continue;
                }

                LogUtil.INSTANCE.e("AccessLocation url: " + accessLocation);
            }
        }
    }

    public static boolean ocspfunc() {
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream certStream = new ByteArrayInputStream(certStr.getBytes());
            X509Certificate cert = (X509Certificate) cf.generateCertificate(certStream);

            byte[] ocspRequest = generateOCSPRequest(cert);

            HttpLoggingInterceptor dd = new HttpLoggingInterceptor();
            dd.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    //日志拦截器会影响文件上传进度监听
//                    .addInterceptor(dd)
                    .build();


            Request request = new Request.Builder()
                    .url(urlStrTest)
                    .post(RequestBody.create(MediaType.parse("application/ocsp-request"), ocspRequest))
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    byte[] ocspResponse = responseBody.bytes();
                    return processOCSPResponse(ocspResponse);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static byte[] generateOCSPRequest(X509Certificate certificate) throws CertificateEncodingException {
        byte[] encode = null;
        BigInteger serialNumber = certificate.getSerialNumber();
        ASN1Integer serialNumberAsn = new ASN1Integer(serialNumber);
        X500Principal principal = certificate.getIssuerX500Principal();
        PublicKey publicKey = certificate.getPublicKey();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] issuerNameHash = digest.digest(principal.getEncoded());
            byte[] serialNumberHash = digest.digest(publicKey.getEncoded());
            CertID certID = new CertID(new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1),
                    new DEROctetString(issuerNameHash), new DEROctetString(serialNumberHash),
                    serialNumberAsn);
            ASN1EncodableVector v = new ASN1EncodableVector();
            v.add(certID);

            DERSequence request = new DERSequence(v);
            org.bouncycastle.asn1.ocsp.Request re = org.bouncycastle.asn1.ocsp.Request.getInstance(request);

            ASN1EncodableVector v1 = new ASN1EncodableVector();
            v1.add(re);

            DERSequence requestList = new DERSequence(v1);
            TBSRequest tbsRequest = new TBSRequest(null, requestList, (Extensions) null);
            OCSPRequest ocspRequest = new OCSPRequest(tbsRequest, null);
            try {
                encode = ocspRequest.toASN1Primitive().getEncoded();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return encode;
    }

    private static boolean processOCSPResponse(byte[] bytes) throws Exception {

        ASN1Sequence asn1Sequence = null;
        ByteArrayInputStream inputStream = null;
        inputStream = new ByteArrayInputStream(bytes);
        ASN1InputStream asn1InputStream = new ASN1InputStream(inputStream);
        ASN1Primitive asn1Primitive = null;
        try {
            asn1Primitive = asn1InputStream.readObject();

            if (asn1Primitive instanceof ASN1Sequence) {
                asn1Sequence = (ASN1Sequence) asn1Primitive;
            }
            if (asn1Sequence != null) {
                OCSPResponse ocspResp = OCSPResponse.getInstance(asn1Sequence);
                int status = ocspResp.getResponseStatus().getValue().intValue();
                Log.e("TAG", "status: " + status);
                if (status == OCSPResponseStatus.SUCCESSFUL) {
                    ASN1ObjectIdentifier responseType = ocspResp.getResponseBytes().getResponseType();
                    DEROctetString responseOctets = (DEROctetString) ocspResp.getResponseBytes().getResponse();
                    if (responseType.equals(OCSPObjectIdentifiers.id_pkix_ocsp_basic)) {
                        BasicOCSPResponse basicOCSPResp = BasicOCSPResponse.getInstance(responseOctets.getOctets());
                        SingleResponse res = SingleResponse.getInstance(basicOCSPResp.getTbsResponseData().getResponses().getObjectAt(0));
                        Log.e("TAG", "getTagNo: " + res.getCertStatus().getTagNo());
                        if (res.getCertStatus().getTagNo() == 0) { //good
                            return true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
