
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

        
            package mobileconnect;
        
            /**
            *  ExtensionMapper class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ExtensionMapper{

          public static Object getTypeObject(String namespaceURI,
                                                       String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws Exception{

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MSS_ProfileReqType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MSS_ProfileReqType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MSS_SignatureRespType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MSS_SignatureRespType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2001/04/xmlenc#".equals(namespaceURI) &&
                  "EncryptedDataType".equals(typeName)){
                   
                            return  org.w3.www._2001._04.xmlenc.EncryptedDataType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MessagingModeType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MessagingModeType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2001/04/xmlenc#".equals(namespaceURI) &&
                  "EncryptionPropertyType".equals(typeName)){
                   
                            return  org.w3.www._2001._04.xmlenc.EncryptionPropertyType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2001/04/xmlenc#".equals(namespaceURI) &&
                  "ReferenceList_type0".equals(typeName)){
                   
                            return  org.w3.www._2001._04.xmlenc.ReferenceList_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2003/05/soap-envelope".equals(namespaceURI) &&
                  "Body".equals(typeName)){
                   
                            return  org.w3.www._2003._05.soap_envelope.Body.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "PGPDataType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.PGPDataType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MatchingMSSPCertificates_type0".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MatchingMSSPCertificates_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "SignatureType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.SignatureType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2003/05/soap-envelope".equals(namespaceURI) &&
                  "UpgradeType".equals(typeName)){
                   
                            return  org.w3.www._2003._05.soap_envelope.UpgradeType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "AP_Info_type0".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.AP_Info_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2003/05/soap-envelope".equals(namespaceURI) &&
                  "faultcode".equals(typeName)){
                   
                            return  org.w3.www._2003._05.soap_envelope.Faultcode.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2003/05/soap-envelope".equals(namespaceURI) &&
                  "detail".equals(typeName)){
                   
                            return  org.w3.www._2003._05.soap_envelope.Detail.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "Certificates_type0".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.Certificates_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MeshMemberType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MeshMemberType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "SignaturePropertiesType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.SignaturePropertiesType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "DataType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.DataType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "X509IssuerSerialType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.X509IssuerSerialType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "DSAKeyValueType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.DSAKeyValueType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "KeyValueType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.KeyValueType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2003/05/soap-envelope".equals(namespaceURI) &&
                  "faultcodeEnum".equals(typeName)){
                   
                            return  org.w3.www._2003._05.soap_envelope.FaultcodeEnum.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "StatusCodeType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.StatusCodeType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MSS_ReceiptReqType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MSS_ReceiptReqType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MessageAbstractType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MessageAbstractType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MSS_HandshakeRespType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MSS_HandshakeRespType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2001/04/xmlenc#".equals(namespaceURI) &&
                  "ReferenceType".equals(typeName)){
                   
                            return  org.w3.www._2001._04.xmlenc.ReferenceType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "CryptoBinary".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.CryptoBinary.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "SPKIDataType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.SPKIDataType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "SignatureType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.SignatureType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "AdditionalServices_type0".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.AdditionalServices_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "CanonicalizationMethodType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.CanonicalizationMethodType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2001/04/xmlenc#".equals(namespaceURI) &&
                  "EncryptionMethodType".equals(typeName)){
                   
                            return  org.w3.www._2001._04.xmlenc.EncryptionMethodType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2001/04/xmlenc#".equals(namespaceURI) &&
                  "AgreementMethodType".equals(typeName)){
                   
                            return  org.w3.www._2001._04.xmlenc.AgreementMethodType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "DigestAlgAndValueType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2001/04/xmlenc#".equals(namespaceURI) &&
                  "CipherReferenceType".equals(typeName)){
                   
                            return  org.w3.www._2001._04.xmlenc.CipherReferenceType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "X509DataType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.X509DataType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2003/05/soap-envelope".equals(namespaceURI) &&
                  "reasontext".equals(typeName)){
                   
                            return  org.w3.www._2003._05.soap_envelope.Reasontext.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2001/04/xmlenc#".equals(namespaceURI) &&
                  "TransformsType".equals(typeName)){
                   
                            return  org.w3.www._2001._04.xmlenc.TransformsType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "StatusDetailType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.StatusDetailType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MSS_ReceiptRespType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MSS_ReceiptRespType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2003/05/soap-envelope".equals(namespaceURI) &&
                  "SupportedEnvType".equals(typeName)){
                   
                            return  org.w3.www._2003._05.soap_envelope.SupportedEnvType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "KeyReferenceType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.KeyReferenceType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MSS_HandshakeReqType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MSS_HandshakeReqType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2003/05/soap-envelope".equals(namespaceURI) &&
                  "subcode".equals(typeName)){
                   
                            return  org.w3.www._2003._05.soap_envelope.Subcode.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MatchingAPCertificates_type0".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MatchingAPCertificates_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2003/05/soap-envelope".equals(namespaceURI) &&
                  "NotUnderstoodType".equals(typeName)){
                   
                            return  org.w3.www._2003._05.soap_envelope.NotUnderstoodType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "RSAKeyValueType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.RSAKeyValueType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MSS_SignatureReqType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MSS_SignatureReqType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/XML/1998/namespace".equals(namespaceURI) &&
                  "lang_type0".equals(typeName)){
                   
                            return  org.w3.www.xml._1998.namespace.Lang_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "SignedInfoType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.SignedInfoType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "mssURIType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MssURIType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "SignatureProfileComparisonType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.SignatureProfileComparisonType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "SignatureMethodType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.SignatureMethodType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "SignaturePropertyType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.SignaturePropertyType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "RetrievalMethodType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.RetrievalMethodType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "TransformType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.TransformType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "AdditionalServiceType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.AdditionalServiceType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "DigestValueType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.DigestValueType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2001/04/xmlenc#".equals(namespaceURI) &&
                  "EncryptedKeyType".equals(typeName)){
                   
                            return  org.w3.www._2001._04.xmlenc.EncryptedKeyType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2003/05/soap-envelope".equals(namespaceURI) &&
                  "Fault".equals(typeName)){
                   
                            return  org.w3.www._2003._05.soap_envelope.Fault.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "ReferenceType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.ReferenceType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "SecureMethods_type0".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.SecureMethods_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "SecureMethods_type1".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.SecureMethods_type1.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "KeyInfoType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.KeyInfoType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "StatusType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.StatusType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "DigestMethodType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.DigestMethodType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2003/05/soap-envelope".equals(namespaceURI) &&
                  "Envelope".equals(typeName)){
                   
                            return  org.w3.www._2003._05.soap_envelope.Envelope.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MSS_ProfileRespType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MSS_ProfileRespType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "SignatureValueType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.SignatureValueType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "ManifestType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.ManifestType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MobileUserType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MobileUserType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "HMACOutputLengthType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.HMACOutputLengthType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2003/05/soap-envelope".equals(namespaceURI) &&
                  "Header".equals(typeName)){
                   
                            return  org.w3.www._2003._05.soap_envelope.Header.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MSS_RegistrationRespType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MSS_RegistrationRespType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "SignatureAlgList_type0".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.SignatureAlgList_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2001/04/xmlenc#".equals(namespaceURI) &&
                  "EncryptionPropertiesType".equals(typeName)){
                   
                            return  org.w3.www._2001._04.xmlenc.EncryptionPropertiesType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "TransformsType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.TransformsType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2001/04/xmlenc#".equals(namespaceURI) &&
                  "CipherDataType".equals(typeName)){
                   
                            return  org.w3.www._2001._04.xmlenc.CipherDataType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2001/04/xmlenc#".equals(namespaceURI) &&
                  "EncryptedType".equals(typeName)){
                   
                            return  org.w3.www._2001._04.xmlenc.EncryptedType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2003/05/soap-envelope".equals(namespaceURI) &&
                  "faultreason".equals(typeName)){
                   
                            return  org.w3.www._2003._05.soap_envelope.Faultreason.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2001/04/xmlenc#".equals(namespaceURI) &&
                  "KeySizeType".equals(typeName)){
                   
                            return  org.w3.www._2001._04.xmlenc.KeySizeType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MSS_StatusReqType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MSS_StatusReqType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MSS_RegistrationReqType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MSS_RegistrationReqType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MatchingSigAlgList_type0".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MatchingSigAlgList_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MSSP_Info_type0".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MSSP_Info_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "MSS_StatusRespType".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.MSS_StatusRespType.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://uri.etsi.org/TS102204/v1.1.2#".equals(namespaceURI) &&
                  "RootCAs_type0".equals(typeName)){
                   
                            return  org.etsi.uri.ts102204.v1_1_2.RootCAs_type0.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://www.w3.org/2000/09/xmldsig#".equals(namespaceURI) &&
                  "ObjectType".equals(typeName)){
                   
                            return  org.w3.www._2000._09.xmldsig.ObjectType.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    