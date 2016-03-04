
/**
 * MSS_SignatureReqType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package dialog.org.etsi.uri.ts102204.v1_1_2;
            

            /**
            *  MSS_SignatureReqType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class MSS_SignatureReqType extends dialog.org.etsi.uri.ts102204.v1_1_2.MessageAbstractType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = MSS_SignatureReqType
                Namespace URI = http://uri.etsi.org/TS102204/v1.1.2#
                Namespace Prefix = ns5
                */
            

                        /**
                        * field for MobileUser
                        */

                        
                                    protected dialog.org.etsi.uri.ts102204.v1_1_2.MobileUserType localMobileUser ;
                                

                           /**
                           * Auto generated getter method
                           * @return org.etsi.uri.ts102204.v1_1_2.MobileUserType
                           */
                           public  dialog.org.etsi.uri.ts102204.v1_1_2.MobileUserType getMobileUser(){
                               return localMobileUser;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MobileUser
                               */
                               public void setMobileUser(dialog.org.etsi.uri.ts102204.v1_1_2.MobileUserType param){
                            
                                            this.localMobileUser=param;
                                    

                               }
                            

                        /**
                        * field for DataToBeSigned
                        */

                        
                                    protected dialog.org.etsi.uri.ts102204.v1_1_2.DataType localDataToBeSigned ;
                                

                           /**
                           * Auto generated getter method
                           * @return org.etsi.uri.ts102204.v1_1_2.DataType
                           */
                           public  dialog.org.etsi.uri.ts102204.v1_1_2.DataType getDataToBeSigned(){
                               return localDataToBeSigned;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DataToBeSigned
                               */
                               public void setDataToBeSigned(dialog.org.etsi.uri.ts102204.v1_1_2.DataType param){
                            
                                            this.localDataToBeSigned=param;
                                    

                               }
                            

                        /**
                        * field for DataToBeDisplayed
                        */

                        
                                    protected dialog.org.etsi.uri.ts102204.v1_1_2.DataType localDataToBeDisplayed ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localDataToBeDisplayedTracker = false ;

                           public boolean isDataToBeDisplayedSpecified(){
                               return localDataToBeDisplayedTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.etsi.uri.ts102204.v1_1_2.DataType
                           */
                           public  dialog.org.etsi.uri.ts102204.v1_1_2.DataType getDataToBeDisplayed(){
                               return localDataToBeDisplayed;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param DataToBeDisplayed
                               */
                               public void setDataToBeDisplayed(dialog.org.etsi.uri.ts102204.v1_1_2.DataType param){
                            localDataToBeDisplayedTracker = param != null;
                                   
                                            this.localDataToBeDisplayed=param;
                                    

                               }
                            

                        /**
                        * field for SignatureProfile
                        */

                        
                                    protected dialog.org.etsi.uri.ts102204.v1_1_2.MssURIType localSignatureProfile ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSignatureProfileTracker = false ;

                           public boolean isSignatureProfileSpecified(){
                               return localSignatureProfileTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.etsi.uri.ts102204.v1_1_2.MssURIType
                           */
                           public  dialog.org.etsi.uri.ts102204.v1_1_2.MssURIType getSignatureProfile(){
                               return localSignatureProfile;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SignatureProfile
                               */
                               public void setSignatureProfile(dialog.org.etsi.uri.ts102204.v1_1_2.MssURIType param){
                            localSignatureProfileTracker = param != null;
                                   
                                            this.localSignatureProfile=param;
                                    

                               }
                            

                        /**
                        * field for AdditionalServices
                        */

                        
                                    protected dialog.org.etsi.uri.ts102204.v1_1_2.AdditionalServices_type0 localAdditionalServices ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAdditionalServicesTracker = false ;

                           public boolean isAdditionalServicesSpecified(){
                               return localAdditionalServicesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.etsi.uri.ts102204.v1_1_2.AdditionalServices_type0
                           */
                           public  dialog.org.etsi.uri.ts102204.v1_1_2.AdditionalServices_type0 getAdditionalServices(){
                               return localAdditionalServices;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AdditionalServices
                               */
                               public void setAdditionalServices(dialog.org.etsi.uri.ts102204.v1_1_2.AdditionalServices_type0 param){
                            localAdditionalServicesTracker = param != null;
                                   
                                            this.localAdditionalServices=param;
                                    

                               }
                            

                        /**
                        * field for MSS_Format
                        */

                        
                                    protected dialog.org.etsi.uri.ts102204.v1_1_2.MssURIType localMSS_Format ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMSS_FormatTracker = false ;

                           public boolean isMSS_FormatSpecified(){
                               return localMSS_FormatTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.etsi.uri.ts102204.v1_1_2.MssURIType
                           */
                           public  dialog.org.etsi.uri.ts102204.v1_1_2.MssURIType getMSS_Format(){
                               return localMSS_Format;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MSS_Format
                               */
                               public void setMSS_Format(dialog.org.etsi.uri.ts102204.v1_1_2.MssURIType param){
                            localMSS_FormatTracker = param != null;
                                   
                                            this.localMSS_Format=param;
                                    

                               }
                            

                        /**
                        * field for KeyReference
                        */

                        
                                    protected dialog.org.etsi.uri.ts102204.v1_1_2.KeyReferenceType localKeyReference ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localKeyReferenceTracker = false ;

                           public boolean isKeyReferenceSpecified(){
                               return localKeyReferenceTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.etsi.uri.ts102204.v1_1_2.KeyReferenceType
                           */
                           public  dialog.org.etsi.uri.ts102204.v1_1_2.KeyReferenceType getKeyReference(){
                               return localKeyReference;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param KeyReference
                               */
                               public void setKeyReference(dialog.org.etsi.uri.ts102204.v1_1_2.KeyReferenceType param){
                            localKeyReferenceTracker = param != null;
                                   
                                            this.localKeyReference=param;
                                    

                               }
                            

                        /**
                        * field for SignatureProfileComparison
                        */

                        
                                    protected dialog.org.etsi.uri.ts102204.v1_1_2.SignatureProfileComparisonType localSignatureProfileComparison ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSignatureProfileComparisonTracker = false ;

                           public boolean isSignatureProfileComparisonSpecified(){
                               return localSignatureProfileComparisonTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.etsi.uri.ts102204.v1_1_2.SignatureProfileComparisonType
                           */
                           public  dialog.org.etsi.uri.ts102204.v1_1_2.SignatureProfileComparisonType getSignatureProfileComparison(){
                               return localSignatureProfileComparison;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SignatureProfileComparison
                               */
                               public void setSignatureProfileComparison(dialog.org.etsi.uri.ts102204.v1_1_2.SignatureProfileComparisonType param){
                            localSignatureProfileComparisonTracker = param != null;
                                   
                                            this.localSignatureProfileComparison=param;
                                    

                               }
                            

                        /**
                        * field for ValidityDate
                        * This was an Attribute!
                        */

                        
                                    protected java.util.Calendar localValidityDate ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getValidityDate(){
                               return localValidityDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ValidityDate
                               */
                               public void setValidityDate(java.util.Calendar param){
                            
                                            this.localValidityDate=param;
                                    

                               }
                            

                        /**
                        * field for TimeOut
                        * This was an Attribute!
                        */

                        
                                    protected org.apache.axis2.databinding.types.PositiveInteger localTimeOut ;
                                

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.PositiveInteger
                           */
                           public  org.apache.axis2.databinding.types.PositiveInteger getTimeOut(){
                               return localTimeOut;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TimeOut
                               */
                               public void setTimeOut(org.apache.axis2.databinding.types.PositiveInteger param){
                            
                                            this.localTimeOut=param;
                                    

                               }
                            

                        /**
                        * field for MessagingMode
                        * This was an Attribute!
                        */

                        
                                    protected dialog.org.etsi.uri.ts102204.v1_1_2.MessagingModeType localMessagingMode ;
                                

                           /**
                           * Auto generated getter method
                           * @return org.etsi.uri.ts102204.v1_1_2.MessagingModeType
                           */
                           public  dialog.org.etsi.uri.ts102204.v1_1_2.MessagingModeType getMessagingMode(){
                               return localMessagingMode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MessagingMode
                               */
                               public void setMessagingMode(dialog.org.etsi.uri.ts102204.v1_1_2.MessagingModeType param){
                            
                                            this.localMessagingMode=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://uri.etsi.org/TS102204/v1.1.2#");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":MSS_SignatureReqType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "MSS_SignatureReqType",
                           xmlWriter);
                   }

               
                                            if (localMajorVersion != null){
                                        
                                                writeAttribute("",
                                                         "MajorVersion",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMajorVersion), xmlWriter);

                                            
                                      }
                                    
                                      else {
                                          throw new org.apache.axis2.databinding.ADBException("required attribute localMajorVersion is null");
                                      }
                                    
                                            if (localMinorVersion != null){
                                        
                                                writeAttribute("",
                                                         "MinorVersion",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMinorVersion), xmlWriter);

                                            
                                      }
                                    
                                      else {
                                          throw new org.apache.axis2.databinding.ADBException("required attribute localMinorVersion is null");
                                      }
                                    
                                            if (localValidityDate != null){
                                        
                                                writeAttribute("",
                                                         "ValidityDate",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localValidityDate), xmlWriter);

                                            
                                      }
                                    
                                            if (localTimeOut != null){
                                        
                                                writeAttribute("",
                                                         "TimeOut",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTimeOut), xmlWriter);

                                            
                                      }
                                    
                                    
                                    if (localMessagingMode != null){
                                        writeAttribute("",
                                           "MessagingMode",
                                           localMessagingMode.toString(), xmlWriter);
                                    }
                                    
                                      else {
                                          throw new org.apache.axis2.databinding.ADBException("required attribute localMessagingMode is null");
                                      }
                                    
                                            if (localAP_Info==null){
                                                 throw new org.apache.axis2.databinding.ADBException("AP_Info cannot be null!!");
                                            }
                                           localAP_Info.serialize(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","AP_Info"),
                                               xmlWriter);
                                        
                                            if (localMSSP_Info==null){
                                                 throw new org.apache.axis2.databinding.ADBException("MSSP_Info cannot be null!!");
                                            }
                                           localMSSP_Info.serialize(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","MSSP_Info"),
                                               xmlWriter);
                                        
                                            if (localMobileUser==null){
                                                 throw new org.apache.axis2.databinding.ADBException("MobileUser cannot be null!!");
                                            }
                                           localMobileUser.serialize(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","MobileUser"),
                                               xmlWriter);
                                        
                                            if (localDataToBeSigned==null){
                                                 throw new org.apache.axis2.databinding.ADBException("DataToBeSigned cannot be null!!");
                                            }
                                           localDataToBeSigned.serialize(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","DataToBeSigned"),
                                               xmlWriter);
                                         if (localDataToBeDisplayedTracker){
                                            if (localDataToBeDisplayed==null){
                                                 throw new org.apache.axis2.databinding.ADBException("DataToBeDisplayed cannot be null!!");
                                            }
                                           localDataToBeDisplayed.serialize(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","DataToBeDisplayed"),
                                               xmlWriter);
                                        } if (localSignatureProfileTracker){
                                            if (localSignatureProfile==null){
                                                 throw new org.apache.axis2.databinding.ADBException("SignatureProfile cannot be null!!");
                                            }
                                           localSignatureProfile.serialize(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","SignatureProfile"),
                                               xmlWriter);
                                        } if (localAdditionalServicesTracker){
                                            if (localAdditionalServices==null){
                                                 throw new org.apache.axis2.databinding.ADBException("AdditionalServices cannot be null!!");
                                            }
                                           localAdditionalServices.serialize(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","AdditionalServices"),
                                               xmlWriter);
                                        } if (localMSS_FormatTracker){
                                            if (localMSS_Format==null){
                                                 throw new org.apache.axis2.databinding.ADBException("MSS_Format cannot be null!!");
                                            }
                                           localMSS_Format.serialize(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","MSS_Format"),
                                               xmlWriter);
                                        } if (localKeyReferenceTracker){
                                            if (localKeyReference==null){
                                                 throw new org.apache.axis2.databinding.ADBException("KeyReference cannot be null!!");
                                            }
                                           localKeyReference.serialize(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","KeyReference"),
                                               xmlWriter);
                                        } if (localSignatureProfileComparisonTracker){
                                            if (localSignatureProfileComparison==null){
                                                 throw new org.apache.axis2.databinding.ADBException("SignatureProfileComparison cannot be null!!");
                                            }
                                           localSignatureProfileComparison.serialize(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","SignatureProfileComparison"),
                                               xmlWriter);
                                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://uri.etsi.org/TS102204/v1.1.2#")){
                return "ns5";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                    attribList.add(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema-instance","type"));
                    attribList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","MSS_SignatureReqType"));
                
                            elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                      "AP_Info"));
                            
                            
                                    if (localAP_Info==null){
                                         throw new org.apache.axis2.databinding.ADBException("AP_Info cannot be null!!");
                                    }
                                    elementList.add(localAP_Info);
                                
                            elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                      "MSSP_Info"));
                            
                            
                                    if (localMSSP_Info==null){
                                         throw new org.apache.axis2.databinding.ADBException("MSSP_Info cannot be null!!");
                                    }
                                    elementList.add(localMSSP_Info);
                                
                            elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                      "MobileUser"));
                            
                            
                                    if (localMobileUser==null){
                                         throw new org.apache.axis2.databinding.ADBException("MobileUser cannot be null!!");
                                    }
                                    elementList.add(localMobileUser);
                                
                            elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                      "DataToBeSigned"));
                            
                            
                                    if (localDataToBeSigned==null){
                                         throw new org.apache.axis2.databinding.ADBException("DataToBeSigned cannot be null!!");
                                    }
                                    elementList.add(localDataToBeSigned);
                                 if (localDataToBeDisplayedTracker){
                            elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                      "DataToBeDisplayed"));
                            
                            
                                    if (localDataToBeDisplayed==null){
                                         throw new org.apache.axis2.databinding.ADBException("DataToBeDisplayed cannot be null!!");
                                    }
                                    elementList.add(localDataToBeDisplayed);
                                } if (localSignatureProfileTracker){
                            elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                      "SignatureProfile"));
                            
                            
                                    if (localSignatureProfile==null){
                                         throw new org.apache.axis2.databinding.ADBException("SignatureProfile cannot be null!!");
                                    }
                                    elementList.add(localSignatureProfile);
                                } if (localAdditionalServicesTracker){
                            elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                      "AdditionalServices"));
                            
                            
                                    if (localAdditionalServices==null){
                                         throw new org.apache.axis2.databinding.ADBException("AdditionalServices cannot be null!!");
                                    }
                                    elementList.add(localAdditionalServices);
                                } if (localMSS_FormatTracker){
                            elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                      "MSS_Format"));
                            
                            
                                    if (localMSS_Format==null){
                                         throw new org.apache.axis2.databinding.ADBException("MSS_Format cannot be null!!");
                                    }
                                    elementList.add(localMSS_Format);
                                } if (localKeyReferenceTracker){
                            elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                      "KeyReference"));
                            
                            
                                    if (localKeyReference==null){
                                         throw new org.apache.axis2.databinding.ADBException("KeyReference cannot be null!!");
                                    }
                                    elementList.add(localKeyReference);
                                } if (localSignatureProfileComparisonTracker){
                            elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                      "SignatureProfileComparison"));
                            
                            
                                    if (localSignatureProfileComparison==null){
                                         throw new org.apache.axis2.databinding.ADBException("SignatureProfileComparison cannot be null!!");
                                    }
                                    elementList.add(localSignatureProfileComparison);
                                }
                            attribList.add(
                            new javax.xml.namespace.QName("","MajorVersion"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMajorVersion));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","MinorVersion"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMinorVersion));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","ValidityDate"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localValidityDate));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","TimeOut"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTimeOut));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","MessagingMode"));
                            
                                      attribList.add(localMessagingMode.toString());
                                

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static MSS_SignatureReqType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            MSS_SignatureReqType object =
                new MSS_SignatureReqType();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"MSS_SignatureReqType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (MSS_SignatureReqType)dialog.es.telefonica.mobileconnect.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    // handle attribute "MajorVersion"
                    java.lang.String tempAttribMajorVersion =
                        
                                reader.getAttributeValue(null,"MajorVersion");
                            
                   if (tempAttribMajorVersion!=null){
                         java.lang.String content = tempAttribMajorVersion;
                        
                                                 object.setMajorVersion(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(tempAttribMajorVersion));
                                            
                    } else {
                       
                               throw new org.apache.axis2.databinding.ADBException("Required attribute MajorVersion is missing");
                           
                    }
                    handledAttributes.add("MajorVersion");
                    
                    // handle attribute "MinorVersion"
                    java.lang.String tempAttribMinorVersion =
                        
                                reader.getAttributeValue(null,"MinorVersion");
                            
                   if (tempAttribMinorVersion!=null){
                         java.lang.String content = tempAttribMinorVersion;
                        
                                                 object.setMinorVersion(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInteger(tempAttribMinorVersion));
                                            
                    } else {
                       
                               throw new org.apache.axis2.databinding.ADBException("Required attribute MinorVersion is missing");
                           
                    }
                    handledAttributes.add("MinorVersion");
                    
                    // handle attribute "ValidityDate"
                    java.lang.String tempAttribValidityDate =
                        
                                reader.getAttributeValue(null,"ValidityDate");
                            
                   if (tempAttribValidityDate!=null){
                         java.lang.String content = tempAttribValidityDate;
                        
                                                 object.setValidityDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(tempAttribValidityDate));
                                            
                    } else {
                       
                    }
                    handledAttributes.add("ValidityDate");
                    
                    // handle attribute "TimeOut"
                    java.lang.String tempAttribTimeOut =
                        
                                reader.getAttributeValue(null,"TimeOut");
                            
                   if (tempAttribTimeOut!=null){
                         java.lang.String content = tempAttribTimeOut;
                        
                                                 object.setTimeOut(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToPositiveInteger(tempAttribTimeOut));
                                            
                    } else {
                       
                    }
                    handledAttributes.add("TimeOut");
                    
                    // handle attribute "MessagingMode"
                    java.lang.String tempAttribMessagingMode =
                        
                                reader.getAttributeValue(null,"MessagingMode");
                            
                   if (tempAttribMessagingMode!=null){
                         java.lang.String content = tempAttribMessagingMode;
                        
                                                  object.setMessagingMode(
                                                        dialog.org.etsi.uri.ts102204.v1_1_2.MessagingModeType.Factory.fromString(reader,tempAttribMessagingMode));
                                            
                    } else {
                       
                               throw new org.apache.axis2.databinding.ADBException("Required attribute MessagingMode is missing");
                           
                    }
                    handledAttributes.add("MessagingMode");
                    
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","AP_Info").equals(reader.getName())){
                                
                                                object.setAP_Info(dialog.org.etsi.uri.ts102204.v1_1_2.AP_Info_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","MSSP_Info").equals(reader.getName())){
                                
                                                object.setMSSP_Info(dialog.org.etsi.uri.ts102204.v1_1_2.MSSP_Info_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","MobileUser").equals(reader.getName())){
                                
                                                object.setMobileUser(dialog.org.etsi.uri.ts102204.v1_1_2.MobileUserType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","DataToBeSigned").equals(reader.getName())){
                                
                                                object.setDataToBeSigned(dialog.org.etsi.uri.ts102204.v1_1_2.DataType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","DataToBeDisplayed").equals(reader.getName())){
                                
                                                object.setDataToBeDisplayed(dialog.org.etsi.uri.ts102204.v1_1_2.DataType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","SignatureProfile").equals(reader.getName())){
                                
                                                object.setSignatureProfile(dialog.org.etsi.uri.ts102204.v1_1_2.MssURIType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","AdditionalServices").equals(reader.getName())){
                                
                                                object.setAdditionalServices(dialog.org.etsi.uri.ts102204.v1_1_2.AdditionalServices_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","MSS_Format").equals(reader.getName())){
                                
                                                object.setMSS_Format(dialog.org.etsi.uri.ts102204.v1_1_2.MssURIType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","KeyReference").equals(reader.getName())){
                                
                                                object.setKeyReference(dialog.org.etsi.uri.ts102204.v1_1_2.KeyReferenceType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","SignatureProfileComparison").equals(reader.getName())){
                                
                                                object.setSignatureProfileComparison(dialog.org.etsi.uri.ts102204.v1_1_2.SignatureProfileComparisonType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    