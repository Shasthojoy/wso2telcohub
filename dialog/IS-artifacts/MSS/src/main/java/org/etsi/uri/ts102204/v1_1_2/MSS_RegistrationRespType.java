
/**
 * MSS_RegistrationRespType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v10  Built on : Sep 04, 2013 (02:03:08 UTC)
 */

            
                package org.etsi.uri.ts102204.v1_1_2;
            

            /**
            *  MSS_RegistrationRespType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class MSS_RegistrationRespType extends org.etsi.uri.ts102204.v1_1_2.MessageAbstractType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = MSS_RegistrationRespType
                Namespace URI = http://uri.etsi.org/TS102204/v1.1.2#
                Namespace Prefix = ns4
                */
            

                        /**
                        * field for Status
                        */

                        
                                    protected org.etsi.uri.ts102204.v1_1_2.StatusType localStatus ;
                                

                           /**
                           * Auto generated getter method
                           * @return org.etsi.uri.ts102204.v1_1_2.StatusType
                           */
                           public  org.etsi.uri.ts102204.v1_1_2.StatusType getStatus(){
                               return localStatus;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Status
                               */
                               public void setStatus(org.etsi.uri.ts102204.v1_1_2.StatusType param){
                            
                                            this.localStatus=param;
                                    

                               }
                            

                        /**
                        * field for EncryptedData
                        */

                        
                                    protected org.w3.www._2001._04.xmlenc.EncryptedType localEncryptedData ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEncryptedDataTracker = false ;

                           public boolean isEncryptedDataSpecified(){
                               return localEncryptedDataTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.w3.www._2001._04.xmlenc.EncryptedType
                           */
                           public  org.w3.www._2001._04.xmlenc.EncryptedType getEncryptedData(){
                               return localEncryptedData;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EncryptedData
                               */
                               public void setEncryptedData(org.w3.www._2001._04.xmlenc.EncryptedType param){
                            localEncryptedDataTracker = param != null;
                                   
                                            this.localEncryptedData=param;
                                    

                               }
                            

                        /**
                        * field for CertificateURI
                        */

                        
                                    protected org.apache.axis2.databinding.types.URI localCertificateURI ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCertificateURITracker = false ;

                           public boolean isCertificateURISpecified(){
                               return localCertificateURITracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.URI
                           */
                           public  org.apache.axis2.databinding.types.URI getCertificateURI(){
                               return localCertificateURI;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CertificateURI
                               */
                               public void setCertificateURI(org.apache.axis2.databinding.types.URI param){
                            localCertificateURITracker = param != null;
                                   
                                            this.localCertificateURI=param;
                                    

                               }
                            

                        /**
                        * field for X509Certificate
                        */

                        
                                    protected javax.activation.DataHandler localX509Certificate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localX509CertificateTracker = false ;

                           public boolean isX509CertificateSpecified(){
                               return localX509CertificateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return javax.activation.DataHandler
                           */
                           public  javax.activation.DataHandler getX509Certificate(){
                               return localX509Certificate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param X509Certificate
                               */
                               public void setX509Certificate(javax.activation.DataHandler param){
                            localX509CertificateTracker = param != null;
                                   
                                            this.localX509Certificate=param;
                                    

                               }
                            

                        /**
                        * field for PublicKey
                        */

                        
                                    protected javax.activation.DataHandler localPublicKey ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPublicKeyTracker = false ;

                           public boolean isPublicKeySpecified(){
                               return localPublicKeyTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return javax.activation.DataHandler
                           */
                           public  javax.activation.DataHandler getPublicKey(){
                               return localPublicKey;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PublicKey
                               */
                               public void setPublicKey(javax.activation.DataHandler param){
                            localPublicKeyTracker = param != null;
                                   
                                            this.localPublicKey=param;
                                    

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
                           namespacePrefix+":MSS_RegistrationRespType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "MSS_RegistrationRespType",
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
                                        
                                            if (localStatus==null){
                                                 throw new org.apache.axis2.databinding.ADBException("Status cannot be null!!");
                                            }
                                           localStatus.serialize(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","Status"),
                                               xmlWriter);
                                         if (localEncryptedDataTracker){
                                            if (localEncryptedData==null){
                                                 throw new org.apache.axis2.databinding.ADBException("EncryptedData cannot be null!!");
                                            }
                                           localEncryptedData.serialize(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","EncryptedData"),
                                               xmlWriter);
                                        } if (localCertificateURITracker){
                                    namespace = "http://uri.etsi.org/TS102204/v1.1.2#";
                                    writeStartElement(null, namespace, "CertificateURI", xmlWriter);
                             

                                          if (localCertificateURI==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("CertificateURI cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCertificateURI));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localX509CertificateTracker){
                                    namespace = "http://uri.etsi.org/TS102204/v1.1.2#";
                                    writeStartElement(null, namespace, "X509Certificate", xmlWriter);
                             
                                        
                                    if (localX509Certificate!=null)  {
                                       try {
                                           org.apache.axiom.util.stax.XMLStreamWriterUtils.writeDataHandler(xmlWriter, localX509Certificate, null, true);
                                       } catch (java.io.IOException ex) {
                                           throw new javax.xml.stream.XMLStreamException("Unable to read data handler for X509Certificate", ex);
                                       }
                                    } else {
                                         
                                    }
                                 
                                   xmlWriter.writeEndElement();
                             } if (localPublicKeyTracker){
                                    namespace = "http://uri.etsi.org/TS102204/v1.1.2#";
                                    writeStartElement(null, namespace, "PublicKey", xmlWriter);
                             
                                        
                                    if (localPublicKey!=null)  {
                                       try {
                                           org.apache.axiom.util.stax.XMLStreamWriterUtils.writeDataHandler(xmlWriter, localPublicKey, null, true);
                                       } catch (java.io.IOException ex) {
                                           throw new javax.xml.stream.XMLStreamException("Unable to read data handler for PublicKey", ex);
                                       }
                                    } else {
                                         
                                    }
                                 
                                   xmlWriter.writeEndElement();
                             }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://uri.etsi.org/TS102204/v1.1.2#")){
                return "ns4";
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
                while (xmlWriter.getNamespaceContext().getNamespaceURI(prefix) != null) {
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
                    attribList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","MSS_RegistrationRespType"));
                
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
                                                                      "Status"));
                            
                            
                                    if (localStatus==null){
                                         throw new org.apache.axis2.databinding.ADBException("Status cannot be null!!");
                                    }
                                    elementList.add(localStatus);
                                 if (localEncryptedDataTracker){
                            elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                      "EncryptedData"));
                            
                            
                                    if (localEncryptedData==null){
                                         throw new org.apache.axis2.databinding.ADBException("EncryptedData cannot be null!!");
                                    }
                                    elementList.add(localEncryptedData);
                                } if (localCertificateURITracker){
                                      elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                      "CertificateURI"));
                                 
                                        if (localCertificateURI != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCertificateURI));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("CertificateURI cannot be null!!");
                                        }
                                    } if (localX509CertificateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                        "X509Certificate"));
                                
                            elementList.add(localX509Certificate);
                        } if (localPublicKeyTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                        "PublicKey"));
                                
                            elementList.add(localPublicKey);
                        }
                            attribList.add(
                            new javax.xml.namespace.QName("","MajorVersion"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMajorVersion));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","MinorVersion"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMinorVersion));
                                

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
        public static MSS_RegistrationRespType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            MSS_RegistrationRespType object =
                new MSS_RegistrationRespType();

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
                    
                            if (!"MSS_RegistrationRespType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (MSS_RegistrationRespType)org.w3.www.xml._1998.namespace.ExtensionMapper.getTypeObject(
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
                    
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","AP_Info").equals(reader.getName())){
                                
                                                object.setAP_Info(org.etsi.uri.ts102204.v1_1_2.AP_Info_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","MSSP_Info").equals(reader.getName())){
                                
                                                object.setMSSP_Info(org.etsi.uri.ts102204.v1_1_2.MSSP_Info_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","Status").equals(reader.getName())){
                                
                                                object.setStatus(org.etsi.uri.ts102204.v1_1_2.StatusType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","EncryptedData").equals(reader.getName())){
                                
                                                object.setEncryptedData(org.w3.www._2001._04.xmlenc.EncryptedType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","CertificateURI").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CertificateURI" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCertificateURI(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToAnyURI(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","X509Certificate").equals(reader.getName())){
                                
                                            object.setX509Certificate(org.apache.axiom.util.stax.XMLStreamReaderUtils.getDataHandlerFromElement(reader));
                                      
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","PublicKey").equals(reader.getName())){
                                
                                            object.setPublicKey(org.apache.axiom.util.stax.XMLStreamReaderUtils.getDataHandlerFromElement(reader));
                                      
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
           
    