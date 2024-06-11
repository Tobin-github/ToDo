//package top.tobin.todo.utils;
//
//import org.bouncycastle.asn1.ASN1InputStream;
//import org.bouncycastle.asn1.ASN1ObjectIdentifier;
//import org.bouncycastle.asn1.ASN1OctetString;
//import org.bouncycastle.asn1.ASN1Primitive;
//import org.bouncycastle.asn1.ASN1Sequence;
//import org.bouncycastle.asn1.ASN1TaggedObject;
//import org.bouncycastle.asn1.x509.Extension;
//import org.bouncycastle.cert.ocsp.BasicOCSPResp;
//import org.bouncycastle.cert.ocsp.CertificateStatus;
//import org.bouncycastle.cert.ocsp.OCSPException;
//import org.bouncycastle.cert.ocsp.OCSPResp;
//import org.bouncycastle.cert.ocsp.RevokedStatus;
//import org.bouncycastle.cert.ocsp.SingleResp;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.operator.OperatorException;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.security.cert.CRLException;
//import java.security.cert.CertificateException;
//import java.security.cert.CertificateFactory;
//import java.security.cert.X509Certificate;
//
//public class OcspHelper {
//
//    public static void main(String[] args) throws CertificateException, IOException, CRLException {
//        String rootCert = "MIIFuDCCBKCgAwIBAgIQche7n/HhSQ+guIZEOjNvGzANBgkqhkiG9w0BAQsFADAzMQswCQYDVQQGEwJDTjERMA8GA1UECgwIVW5pVHJ1c3QxETAPBgNVBAMMCFNIRUNBIEcyMB4XDTIzMDIwNTE3MDQ0MVoXDTIzMDUwNjE1NTk1OVowTzELMAkGA1UEBhMCQ04xGzAZBgNVBAoMEueUteWtkOetvueroOWJjeWPsDEjMCEGA1UEAwwa5rWL6K+VMDEwMTExMUA4NCoqKioqKioqMjMwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCHJ8088uY9p4XD77TrLu3h0tB5O4Xp32hW4zNttjHmhoXai7wngFooNXDzGwj9JnM99VE+qjQzkr8Th9pyIYeTk5eLTMmmMEo/p42uiHyGbTtn8B2g3wmPTApu2S4+MAkNbvPD5VDEfMb+1e/7oN39NTZv1mBoENpst6nwEdgQ7jla4ueOcKOWWmFT+3lGzx3tWm8lkqSnryaSjNtMynMK25PxiXLFV8dQ4Wk7DigQpveP+GH8ltTHoZqpZMcXy5qgUWeZSfNYQ1i3JajjKBdSzflrKBi3HeeQBzPSk6M12B6EE5usl1zCABXl6o29IREEy9d/zmmScyLtff+oYw19AgMBAAGjggKqMIICpjAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwfQYIKwYBBQUHAQEEcTBvMDgGCCsGAQUFBzABhixodHRwOi8vb2NzcDMuc2hlY2EuY29tL29jc3Avc2hlY2Evc2hlY2Eub2NzcDAzBggrBgEFBQcwAoYnaHR0cDovL2xkYXAyLnNoZWNhLmNvbS9yb290L3NoZWNhZzIuZGVyMB8GA1UdIwQYMBaAFFaI3uMYQ4K3cqQm60SpYtCHxKwmMB0GA1UdDgQWBBS8nQ//lcMctfZGZ1AnPCwwRPZM6TALBgNVHQ8EBAMCBsAwgYYGBiqBHAHFOAR8MHowSQYIKoEcAcU4gRAEPWxkYXA6Ly9sZGFwMi5zaGVjYS5jb20vb3U9c2hlY2EgY2VydGlmaWNhdGUgY2hhaW4sbz1zaGVjYS5jb20wEQYIKoEcAcU4gRMEBTY2MDU1MBoGCCqBHAHFOIEUBA5RVDg0OTAyMzc0MDkyMzAJBgNVHRMEAjAAMEIGA1UdIAQ7MDkwNwYJKoEcAYbvOoEVMCowKAYIKwYBBQUHAgEWHGh0dHA6Ly93d3cuc2hlY2EuY29tL3BvbGljeS8wgeAGA1UdHwSB2DCB1TA3oDWgM4YxaHR0cDovL2xkYXAyLnNoZWNhLmNvbS9DQTIwMDExL1JBOTAzMS9DUkw1MTkxLmNybDCBmaCBlqCBk4aBkGxkYXA6Ly9sZGFwMi5zaGVjYS5jb206Mzg5L2NuPUNSTDUxOTEuY3JsLG91PVJBOTAzMSxvdT1DQTIwMDExLG91PWNybCxvPVVuaVRydXN0P2NlcnRpZmljYXRlUmV2b2NhdGlvbkxpc3Q/YmFzZT9vYmplY3RDbGFzcz1jUkxEaXN0cmlidXRpb25Qb2ludDANBgkqhkiG9w0BAQsFAAOCAQEAjQRnIYRE9SH4+leOjO9oUt++qhfefVzaZXdGQgxiUzIXv14vo9mls0COjz0YXoruEe6olh6X6rrdmaKrYw0iq2CJ3D1GkrFCutjX2P3r97Irale8w5J8hJ6dybd/rFZFZuTfYm7yWJLEcF+pAZXedGObwe4fOjS0J/A6KXqGsrdB/fJwvfHH5UIIWW3OihTr1TLEEuun/3oDbGdBDTud2+6tbiEN9daFV92TSko2DRQ/CisJoq5SCmI/dYZlAqeyr4jlLWHFfVUpHKuvpn/lHtYCU0FsGyu9ixs4/YdBw/QIDj5oES9yf/FFDzfTnS8twa8rRJKWdUKKa9sYEeJtVQ==";
//        CertificateFactory cf = CertificateFactory.getInstance("X.509", new BouncyCastleProvider());
//        X509Certificate certificate = (X509Certificate) cf
//                .generateCertificate(new ByteArrayInputStream(Base64Utils.decode(rootCert)));
//        byte[] crlDistributionPointsExtension = certificate.getExtensionValue("1.3.6.1.5.5.7.1.1");
//        ASN1InputStream aIn = new ASN1InputStream(new ByteArrayInputStream(crlDistributionPointsExtension));
//        ASN1OctetString octs = (ASN1OctetString) aIn.readObject();
//        aIn = new ASN1InputStream(new ByteArrayInputStream(octs.getOctets()));
//        ASN1Primitive asn1Primitive = aIn.readObject();
//        ASN1Sequence AccessDescriptions = (ASN1Sequence) asn1Primitive;
//        for (int i = 0; i < AccessDescriptions.size(); i++) {
//            ASN1Sequence AccessDescription = (ASN1Sequence) AccessDescriptions.getObjectAt(i);
//            if (AccessDescription.size() != 2) {
//                continue;
//            } else if (AccessDescription.getObjectAt(0) instanceof ASN1ObjectIdentifier) {
//                ASN1ObjectIdentifier id = (ASN1ObjectIdentifier) AccessDescription.getObjectAt(0);
//                if (SecurityIDs.ID_OCSP.equals(id.getId())) {
//                    ASN1Primitive description = (ASN1Primitive) AccessDescription.getObjectAt(1);
//                    String AccessLocation = getStringFromGeneralName(description);
//                    // 区别于itext源码，不获取ldap协议地址
//                    if (AccessLocation.startsWith("ldap")) {
//                        continue;
//                    }
//                    if (AccessLocation == null) {
//                        System.out.println("");
//                    } else {
//                        System.out.println(AccessLocation);
//                    }
//                }
//            }
//        }
//    }
//
//    private static String getStringFromGeneralName(ASN1Primitive names) throws IOException {
//        ASN1TaggedObject taggedObject = (ASN1TaggedObject) names ;
//        return new String(ASN1OctetString.getInstance(taggedObject, false).getOctets(), "ISO-8859-1");
//    }
//
//
//    private void  checkOcsp() throws Exception{
//        Extension.authorityInfoAccess.getId();
//
//        public static void main(String[] args) throws CertificateException, IOException, CRLException {
//            String rootCert = "MIIFuDCCBKCgAwIBAgIQche7n/HhSQ+guIZEOjNvGzANBgkqhkiG9w0BAQsFADAzMQswCQYDVQQGEwJDTjERMA8GA1UECgwIVW5pVHJ1c3QxETAPBgNVBAMMCFNIRUNBIEcyMB4XDTIzMDIwNTE3MDQ0MVoXDTIzMDUwNjE1NTk1OVowTzELMAkGA1UEBhMCQ04xGzAZBgNVBAoMEueUteWtkOetvueroOWJjeWPsDEjMCEGA1UEAwwa5rWL6K+VMDEwMTExMUA4NCoqKioqKioqMjMwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCHJ8088uY9p4XD77TrLu3h0tB5O4Xp32hW4zNttjHmhoXai7wngFooNXDzGwj9JnM99VE+qjQzkr8Th9pyIYeTk5eLTMmmMEo/p42uiHyGbTtn8B2g3wmPTApu2S4+MAkNbvPD5VDEfMb+1e/7oN39NTZv1mBoENpst6nwEdgQ7jla4ueOcKOWWmFT+3lGzx3tWm8lkqSnryaSjNtMynMK25PxiXLFV8dQ4Wk7DigQpveP+GH8ltTHoZqpZMcXy5qgUWeZSfNYQ1i3JajjKBdSzflrKBi3HeeQBzPSk6M12B6EE5usl1zCABXl6o29IREEy9d/zmmScyLtff+oYw19AgMBAAGjggKqMIICpjAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwfQYIKwYBBQUHAQEEcTBvMDgGCCsGAQUFBzABhixodHRwOi8vb2NzcDMuc2hlY2EuY29tL29jc3Avc2hlY2Evc2hlY2Eub2NzcDAzBggrBgEFBQcwAoYnaHR0cDovL2xkYXAyLnNoZWNhLmNvbS9yb290L3NoZWNhZzIuZGVyMB8GA1UdIwQYMBaAFFaI3uMYQ4K3cqQm60SpYtCHxKwmMB0GA1UdDgQWBBS8nQ//lcMctfZGZ1AnPCwwRPZM6TALBgNVHQ8EBAMCBsAwgYYGBiqBHAHFOAR8MHowSQYIKoEcAcU4gRAEPWxkYXA6Ly9sZGFwMi5zaGVjYS5jb20vb3U9c2hlY2EgY2VydGlmaWNhdGUgY2hhaW4sbz1zaGVjYS5jb20wEQYIKoEcAcU4gRMEBTY2MDU1MBoGCCqBHAHFOIEUBA5RVDg0OTAyMzc0MDkyMzAJBgNVHRMEAjAAMEIGA1UdIAQ7MDkwNwYJKoEcAYbvOoEVMCowKAYIKwYBBQUHAgEWHGh0dHA6Ly93d3cuc2hlY2EuY29tL3BvbGljeS8wgeAGA1UdHwSB2DCB1TA3oDWgM4YxaHR0cDovL2xkYXAyLnNoZWNhLmNvbS9DQTIwMDExL1JBOTAzMS9DUkw1MTkxLmNybDCBmaCBlqCBk4aBkGxkYXA6Ly9sZGFwMi5zaGVjYS5jb206Mzg5L2NuPUNSTDUxOTEuY3JsLG91PVJBOTAzMSxvdT1DQTIwMDExLG91PWNybCxvPVVuaVRydXN0P2NlcnRpZmljYXRlUmV2b2NhdGlvbkxpc3Q/YmFzZT9vYmplY3RDbGFzcz1jUkxEaXN0cmlidXRpb25Qb2ludDANBgkqhkiG9w0BAQsFAAOCAQEAjQRnIYRE9SH4+leOjO9oUt++qhfefVzaZXdGQgxiUzIXv14vo9mls0COjz0YXoruEe6olh6X6rrdmaKrYw0iq2CJ3D1GkrFCutjX2P3r97Irale8w5J8hJ6dybd/rFZFZuTfYm7yWJLEcF+pAZXedGObwe4fOjS0J/A6KXqGsrdB/fJwvfHH5UIIWW3OihTr1TLEEuun/3oDbGdBDTud2+6tbiEN9daFV92TSko2DRQ/CisJoq5SCmI/dYZlAqeyr4jlLWHFfVUpHKuvpn/lHtYCU0FsGyu9ixs4/YdBw/QIDj5oES9yf/FFDzfTnS8twa8rRJKWdUKKa9sYEeJtVQ==";
//            CertificateFactory cf = CertificateFactory.getInstance("X.509", new BouncyCastleProvider());
//            X509Certificate certificate = (X509Certificate) cf
//                    .generateCertificate(new ByteArrayInputStream(Base64Utils.decode(rootCert)));
//            byte[] crlDistributionPointsExtension = certificate.getExtensionValue("1.3.6.1.5.5.7.1.1");
//            ASN1InputStream aIn = new ASN1InputStream(new ByteArrayInputStream(crlDistributionPointsExtension));
//            ASN1OctetString octs = (ASN1OctetString) aIn.readObject();
//            aIn = new ASN1InputStream(new ByteArrayInputStream(octs.getOctets()));
//            ASN1Primitive asn1Primitive = aIn.readObject();
//            ASN1Sequence AccessDescriptions = (ASN1Sequence) asn1Primitive;
//            for (int i = 0; i < AccessDescriptions.size(); i++) {
//                ASN1Sequence AccessDescription = (ASN1Sequence) AccessDescriptions.getObjectAt(i);
//                if (AccessDescription.size() != 2) {
//                    continue;
//                } else if (AccessDescription.getObjectAt(0) instanceof ASN1ObjectIdentifier) {
//                    ASN1ObjectIdentifier id = (ASN1ObjectIdentifier) AccessDescription.getObjectAt(0);
//                    if (SecurityIDs.ID_OCSP.equals(id.getId())) {
//                        ASN1Primitive description = (ASN1Primitive) AccessDescription.getObjectAt(1);
//                        String AccessLocation = getStringFromGeneralName(description);
//                        // 区别于itext源码，不获取ldap协议地址
//                        if (AccessLocation.startsWith("ldap")) {
//                            continue;
//                        }
//                        if (AccessLocation == null) {
//                            System.out.println("");
//                        } else {
//                            System.out.println(AccessLocation);
//                        }
//                    }
//                }
//            }
//        }
//
//        private static String getStringFromGeneralName(ASN1Primitive names) throws IOException {
//            ASN1TaggedObject taggedObject = (ASN1TaggedObject) names ;
//            return new String(ASN1OctetString.getInstance(taggedObject, false).getOctets(), "ISO-8859-1");
//        }
//
//    }
//
//    public static void main(String[] args) throws GeneralSecurityException, IOException, OperatorException, OCSPException {
//        String verifyCert = "MIIFuDCCBKCgAwIBAgIQche7n/HhSQ+guIZEOjNvGzANBgkqhkiG9w0BAQsFADAzMQswCQYDVQQGEwJDTjERMA8GA1UECgwIVW5pVHJ1c3QxETAPBgNVBAMMCFNIRUNBIEcyMB4XDTIzMDIwNTE3MDQ0MVoXDTIzMDUwNjE1NTk1OVowTzELMAkGA1UEBhMCQ04xGzAZBgNVBAoMEueUteWtkOetvueroOWJjeWPsDEjMCEGA1UEAwwa5rWL6K+VMDEwMTExMUA4NCoqKioqKioqMjMwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCHJ8088uY9p4XD77TrLu3h0tB5O4Xp32hW4zNttjHmhoXai7wngFooNXDzGwj9JnM99VE+qjQzkr8Th9pyIYeTk5eLTMmmMEo/p42uiHyGbTtn8B2g3wmPTApu2S4+MAkNbvPD5VDEfMb+1e/7oN39NTZv1mBoENpst6nwEdgQ7jla4ueOcKOWWmFT+3lGzx3tWm8lkqSnryaSjNtMynMK25PxiXLFV8dQ4Wk7DigQpveP+GH8ltTHoZqpZMcXy5qgUWeZSfNYQ1i3JajjKBdSzflrKBi3HeeQBzPSk6M12B6EE5usl1zCABXl6o29IREEy9d/zmmScyLtff+oYw19AgMBAAGjggKqMIICpjAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwfQYIKwYBBQUHAQEEcTBvMDgGCCsGAQUFBzABhixodHRwOi8vb2NzcDMuc2hlY2EuY29tL29jc3Avc2hlY2Evc2hlY2Eub2NzcDAzBggrBgEFBQcwAoYnaHR0cDovL2xkYXAyLnNoZWNhLmNvbS9yb290L3NoZWNhZzIuZGVyMB8GA1UdIwQYMBaAFFaI3uMYQ4K3cqQm60SpYtCHxKwmMB0GA1UdDgQWBBS8nQ//lcMctfZGZ1AnPCwwRPZM6TALBgNVHQ8EBAMCBsAwgYYGBiqBHAHFOAR8MHowSQYIKoEcAcU4gRAEPWxkYXA6Ly9sZGFwMi5zaGVjYS5jb20vb3U9c2hlY2EgY2VydGlmaWNhdGUgY2hhaW4sbz1zaGVjYS5jb20wEQYIKoEcAcU4gRMEBTY2MDU1MBoGCCqBHAHFOIEUBA5RVDg0OTAyMzc0MDkyMzAJBgNVHRMEAjAAMEIGA1UdIAQ7MDkwNwYJKoEcAYbvOoEVMCowKAYIKwYBBQUHAgEWHGh0dHA6Ly93d3cuc2hlY2EuY29tL3BvbGljeS8wgeAGA1UdHwSB2DCB1TA3oDWgM4YxaHR0cDovL2xkYXAyLnNoZWNhLmNvbS9DQTIwMDExL1JBOTAzMS9DUkw1MTkxLmNybDCBmaCBlqCBk4aBkGxkYXA6Ly9sZGFwMi5zaGVjYS5jb206Mzg5L2NuPUNSTDUxOTEuY3JsLG91PVJBOTAzMSxvdT1DQTIwMDExLG91PWNybCxvPVVuaVRydXN0P2NlcnRpZmljYXRlUmV2b2NhdGlvbkxpc3Q/YmFzZT9vYmplY3RDbGFzcz1jUkxEaXN0cmlidXRpb25Qb2ludDANBgkqhkiG9w0BAQsFAAOCAQEAjQRnIYRE9SH4+leOjO9oUt++qhfefVzaZXdGQgxiUzIXv14vo9mls0COjz0YXoruEe6olh6X6rrdmaKrYw0iq2CJ3D1GkrFCutjX2P3r97Irale8w5J8hJ6dybd/rFZFZuTfYm7yWJLEcF+pAZXedGObwe4fOjS0J/A6KXqGsrdB/fJwvfHH5UIIWW3OihTr1TLEEuun/3oDbGdBDTud2+6tbiEN9daFV92TSko2DRQ/CisJoq5SCmI/dYZlAqeyr4jlLWHFfVUpHKuvpn/lHtYCU0FsGyu9ixs4/YdBw/QIDj5oES9yf/FFDzfTnS8twa8rRJKWdUKKa9sYEeJtVQ==";
//        CertificateFactory cf = CertificateFactory.getInstance("X.509", new BouncyCastleProvider());
//        X509Certificate certificate = (X509Certificate) cf
//                .generateCertificate(new ByteArrayInputStream(Base64Utils.decode(verifyCert)));
//        String rootCert = "MIIEGDCCAwCgAwIBAgIQag48Y/PJFceE2VmIFXZ9qDANBgkqhkiG9w0BAQsFADAzMQswCQYDVQQGEwJDTjERMA8GA1UECgwIVW5pVHJ1c3QxETAPBgNVBAMMCFVDQSBSb290MB4XDTE1MDMxMzAwMDAwMFoXDTI5MTIzMTAwMDAwMFowMzELMAkGA1UEBhMCQ04xETAPBgNVBAoMCFVuaVRydXN0MREwDwYDVQQDDAhTSEVDQSBHMjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAN6IIHj7qZIYrz466zxTGCPURSI6GZqbFCTtxBrPnTE5SKtCIyqRNwj/+3A9f/cCyWYHptLeGeY80WORDGuZBBHiVTEsIHSnXZvfqCnQf9KmAisVOOTIGCPWJvCRnfMWLdAcENNaZDIxpkc31ZejALBNPHJDDhxmt6PqyvdX5/cF6gkXO2OOzCa/EF5+x9LwWUKAGR/b+x5j5vt637AQjNmt5Xym63sQdwEaAHqTuPCbcwl+Y1eKXmWuFUXcMk+JdbOhXmjqbOIhup5yrx+hyXc+dtRBJzuSEpvC7WkXLJInR2dqb+Bc2ReJd6zM1deM1MPRmqdJQKEDyT7lEXST53UCAwEAAaOCASYwggEiMEEGA1UdIAQ6MDgwNgYIKoEchu86gRUwKjAoBggrBgEFBQcCARYcaHR0cDovL3d3dy5zaGVjYS5jb20vcG9saWN5LzAOBgNVHQ8BAf8EBAMCAQYwDwYDVR0TAQH/BAUwAwEB/zBDBggrBgEFBQcBAQQ3MDUwMwYIKwYBBQUHMAKGJ2h0dHA6Ly9sZGFwMi5zaGVjYS5jb20vcm9vdC91Y2Fyb290LmRlcjAdBgNVHQ4EFgQUVoje4xhDgrdypCbrRKli0IfErCYwHwYDVR0jBBgwFoAU2x8182tM/0IxZJvNu1oeHUgQt+4wNwYDVR0fBDAwLjAsoCqgKIYmaHR0cDovL2xkYXAyLnNoZWNhLmNvbS9yb290L3VjYXN1Yi5jcmwwDQYJKoZIhvcNAQELBQADggEBAHj7LkurkcVg8NqoXTg8skl96hhVN06jx2OuiEUFda8NVOfxvaCIc7Ep009/CHZnFUaQO3DYZejpTPAMlsJa18luc12xrOhLZxP4ht2TY+UfcskrjiyrrczJ+95dXT+ChYcGtDGfYXFKDOrgsxekEahSIs+fS/H4LA3Y3z8SeK4tFRigaWZQWV2kW+YNRAtXPoNYUPbPCq3UP4dLtm35DHPvdn/h1iVo5/GU+P02F+SBd6J4AO+wcVw5izs6LRXNRnfgSERM7vP8WLt+lX14umZXJPMPh+WoAH9WU6KnXFwLxpltCayueWsLOzDsX6sUbLcp/vPPrkA20CzeMerxY9E=";
//        X509Certificate rootCertificate = (X509Certificate) cf
//                .generateCertificate(new ByteArrayInputStream(Base64Utils.decode(rootCert)));
//
//        byte[] authorityInfoAccesses = certificate.getExtensionValue(Extension.authorityInfoAccess.getId());
//        ASN1InputStream aIn = new ASN1InputStream(new ByteArrayInputStream(authorityInfoAccesses));
//        ASN1OctetString octs = (ASN1OctetString) aIn.readObject();
//        aIn = new ASN1InputStream(new ByteArrayInputStream(octs.getOctets()));
//        ASN1Primitive asn1Primitive = aIn.readObject();
//        ASN1Sequence AccessDescriptions = (ASN1Sequence) asn1Primitive;
//        String AccessLocation = null;
//        for (int i = 0; i < AccessDescriptions.size(); i++) {
//            ASN1Sequence AccessDescription = (ASN1Sequence) AccessDescriptions.getObjectAt(i);
//            if (AccessDescription.size() != 2) {
//                continue;
//            } else if (AccessDescription.getObjectAt(0) instanceof ASN1ObjectIdentifier) {
//                ASN1ObjectIdentifier id = (ASN1ObjectIdentifier) AccessDescription.getObjectAt(0);
//                if (SecurityIDs.ID_OCSP.equals(id.getId())) {
//                    ASN1Primitive description = (ASN1Primitive) AccessDescription.getObjectAt(1);
//                    AccessLocation = getStringFromGeneralName(description);
//                    // 区别于itext源码，不获取ldap协议地址
//                    if (AccessLocation.startsWith("ldap")) {
//                        continue;
//                    }
//                    if (AccessLocation == null) {
//                        System.out.println("");
//                    } else {
//                        System.out.println(AccessLocation);
//                    }
//                }
//            }
//        }
//        OCSPResp ocspResponse = CertRevokeVerifyUtil.getOcspResponse(certificate, rootCertificate, AccessLocation);
//        System.out.println(ocspResponse);
//        BasicOCSPResp basicResponse = null;  //获取到BasicOCSPResp
//        try {
//            basicResponse = (BasicOCSPResp) ocspResponse.getResponseObject();
//        } catch (Exception e) {
//            LogUtil.INSTANCE.i("Exception: " + e.getMessage());
//            System.out.println("吊销校验异常");
//        }
//
//        if (basicResponse != null) {
//            SingleResp[] responses = basicResponse.getResponses();
//            if (responses.length == 1) {
//                SingleResp resp = responses[0];
//                Object status = resp.getCertStatus();
//                if (status == CertificateStatus.GOOD) {
//                    System.out.println("证书可以正常使用");
//                } else if (status instanceof RevokedStatus) {
//                    System.out.println("该证书已吊销");
//                } else {
//                    LogUtil.INSTANCE.i("ocsp校验结果:" + "ocsp.status.is.unknown");
//                    System.out.println("吊销校验异常");
//                }
//            }
//        }
//    }
//
//}
