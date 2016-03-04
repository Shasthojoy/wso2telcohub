
/**
 * EncryptedType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package dialog.org.w3.www._2001._04.xmlenc;
            

            /**
            *  EncryptedType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public abstract class EncryptedType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = EncryptedType
                Namespace URI = http://www.w3.org/2001/04/xmlenc#
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for EncryptionMethod
                        */

                        
                                    protected dialog.org.w3.www._2001._04.xmlenc.EncryptionMethodType localEncryptionMethod ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEncryptionMethodTracker = false ;

                           public boolean isEncryptionMethodSpecified(){
                               return localEncryptionMethodTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.w3.www._2001._04.xmlenc.EncryptionMethodType
                           */
                           public  dialog.org.w3.www._2001._04.xmlenc.EncryptionMethodType getEncryptionMethod(){
                               return localEncryptionMethod;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EncryptionMethod
                               */
                               public void setEncryptionMethod(dialog.org.w3.www._2001._04.xmlenc.EncryptionMethodType param){
                            localEncryptionMethodTracker = param != null;
                                   
                                            this.localEncryptionMethod=param;
                                    

                               }
                            

                        /**
                        * field for KeyInfo
                        */

                        
                                    protected dialog.org.w3.www._2000._09.xmldsig.KeyInfoType localKeyInfo ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localKeyInfoTracker = false ;

                           public boolean isKeyInfoSpecified(){
                               return localKeyInfoTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.w3.www._2000._09.xmldsig.KeyInfoType
                           */
                           public  dialog.org.w3.www._2000._09.xmldsig.KeyInfoType getKeyInfo(){
                               return localKeyInfo;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param KeyInfo
                               */
                               public void setKeyInfo(dialog.org.w3.www._2000._09.xmldsig.KeyInfoType param){
                            localKeyInfoTracker = param != null;
                                   
                                            this.localKeyInfo=param;
                                    

                               }
                            

                        /**
                        * field for CipherData
                        */

                        
                                    protected dialog.org.w3.www._2001._04.xmlenc.CipherDataType localCipherData ;
                                

                           /**
                           * Auto generated getter method
                           * @return org.w3.www._2001._04.xmlenc.CipherDataType
                           */
                           public  dialog.org.w3.www._2001._04.xmlenc.CipherDataType getCipherData(){
                               return localCipherData;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CipherData
                               */
                               public void setCipherData(dialog.org.w3.www._2001._04.xmlenc.CipherDataType param){
                            
                                            this.localCipherData=param;
                                    

                               }
                            

                        /**
                        * field for EncryptionProperties
                        */

                        
                                    protected dialog.org.w3.www._2001._04.xmlenc.EncryptionPropertiesType localEncryptionProperties ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localEncryptionPropertiesTracker = false ;

                           public boolean isEncryptionPropertiesSpecified(){
                               return localEncryptionPropertiesTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.w3.www._2001._04.xmlenc.EncryptionPropertiesType
                           */
                           public  dialog.org.w3.www._2001._04.xmlenc.EncryptionPropertiesType getEncryptionProperties(){
                               return localEncryptionProperties;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EncryptionProperties
                               */
                               public void setEncryptionProperties(dialog.org.w3.www._2001._04.xmlenc.EncryptionPropertiesType param){
                            localEncryptionPropertiesTracker = param != null;
                                   
                                            this.localEncryptionProperties=param;
                                    

                               }
                            

                        /**
                        * field for Id
                        * This was an Attribute!
                        */

                        
                                    protected org.apache.axis2.databinding.types.Id localId ;
                                

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.Id
                           */
                           public  org.apache.axis2.databinding.types.Id getId(){
                               return localId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Id
                               */
                               public void setId(org.apache.axis2.databinding.types.Id param){
                            
                                            this.localId=param;
                                    

                               }
                            

                        /**
                        * field for Type
                        * This was an Attribute!
                        */

                        
                                    protected org.apache.axis2.databinding.types.URI localType ;
                                

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.URI
                           */
                           public  org.apache.axis2.databinding.types.URI getType(){
                               return localType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Type
                               */
                               public void setType(org.apache.axis2.databinding.types.URI param){
                            
                                            this.localType=param;
                                    

                               }
                            

                        /**
                        * field for MimeType
                        * This was an Attribute!
                        */

                        
                                    protected java.lang.String localMimeType ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMimeType(){
                               return localMimeType;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MimeType
                               */
                               public void setMimeType(java.lang.String param){
                            
                                            this.localMimeType=param;
                                    

                               }
                            

                        /**
                        * field for Encoding
                        * This was an Attribute!
                        */

                        
                                    protected org.apache.axis2.databinding.types.URI localEncoding ;
                                

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.URI
                           */
                           public  org.apache.axis2.databinding.types.URI getEncoding(){
                               return localEncoding;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Encoding
                               */
                               public void setEncoding(org.apache.axis2.databinding.types.URI param){
                            
                                            this.localEncoding=param;
                                    

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
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.w3.org/2001/04/xmlenc#");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":EncryptedType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "EncryptedType",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localId != null){
                                        
                                                writeAttribute("",
                                                         "Id",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId), xmlWriter);

                                            
                                      }
                                    
                                            if (localType != null){
                                        
                                                writeAttribute("",
                                                         "Type",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localType), xmlWriter);

                                            
                                      }
                                    
                                            if (localMimeType != null){
                                        
                                                writeAttribute("",
                                                         "MimeType",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMimeType), xmlWriter);

                                            
                                      }
                                    
                                            if (localEncoding != null){
                                        
                                                writeAttribute("",
                                                         "Encoding",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEncoding), xmlWriter);

                                            
                                      }
                                     if (localEncryptionMethodTracker){
                                            if (localEncryptionMethod==null){
                                                 throw new org.apache.axis2.databinding.ADBException("EncryptionMethod cannot be null!!");
                                            }
                                           localEncryptionMethod.serialize(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#","EncryptionMethod"),
                                               xmlWriter);
                                        } if (localKeyInfoTracker){
                                            if (localKeyInfo==null){
                                                 throw new org.apache.axis2.databinding.ADBException("KeyInfo cannot be null!!");
                                            }
                                           localKeyInfo.serialize(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#","KeyInfo"),
                                               xmlWriter);
                                        }
                                            if (localCipherData==null){
                                                 throw new org.apache.axis2.databinding.ADBException("CipherData cannot be null!!");
                                            }
                                           localCipherData.serialize(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#","CipherData"),
                                               xmlWriter);
                                         if (localEncryptionPropertiesTracker){
                                            if (localEncryptionProperties==null){
                                                 throw new org.apache.axis2.databinding.ADBException("EncryptionProperties cannot be null!!");
                                            }
                                           localEncryptionProperties.serialize(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#","EncryptionProperties"),
                                               xmlWriter);
                                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.w3.org/2001/04/xmlenc#")){
                return "ns2";
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

                 if (localEncryptionMethodTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#",
                                                                      "EncryptionMethod"));
                            
                            
                                    if (localEncryptionMethod==null){
                                         throw new org.apache.axis2.databinding.ADBException("EncryptionMethod cannot be null!!");
                                    }
                                    elementList.add(localEncryptionMethod);
                                } if (localKeyInfoTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#",
                                                                      "KeyInfo"));
                            
                            
                                    if (localKeyInfo==null){
                                         throw new org.apache.axis2.databinding.ADBException("KeyInfo cannot be null!!");
                                    }
                                    elementList.add(localKeyInfo);
                                }
                            elementList.add(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#",
                                                                      "CipherData"));
                            
                            
                                    if (localCipherData==null){
                                         throw new org.apache.axis2.databinding.ADBException("CipherData cannot be null!!");
                                    }
                                    elementList.add(localCipherData);
                                 if (localEncryptionPropertiesTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#",
                                                                      "EncryptionProperties"));
                            
                            
                                    if (localEncryptionProperties==null){
                                         throw new org.apache.axis2.databinding.ADBException("EncryptionProperties cannot be null!!");
                                    }
                                    elementList.add(localEncryptionProperties);
                                }
                            attribList.add(
                            new javax.xml.namespace.QName("","Id"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","Type"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localType));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","MimeType"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMimeType));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","Encoding"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEncoding));
                                

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
        public static EncryptedType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            EncryptedType object =
                null;

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
                    
                            if (!"EncryptedType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (EncryptedType)dialog.es.telefonica.mobileconnect.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        
                        throw new org.apache.axis2.databinding.ADBException("The an abstract class can not be instantiated !!!");
                    

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    // handle attribute "Id"
                    java.lang.String tempAttribId =
                        
                                reader.getAttributeValue(null,"Id");
                            
                   if (tempAttribId!=null){
                         java.lang.String content = tempAttribId;
                        
                                                 object.setId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToID(tempAttribId));
                                            
                    } else {
                       
                    }
                    handledAttributes.add("Id");
                    
                    // handle attribute "Type"
                    java.lang.String tempAttribType =
                        
                                reader.getAttributeValue(null,"Type");
                            
                   if (tempAttribType!=null){
                         java.lang.String content = tempAttribType;
                        
                                                 object.setType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToAnyURI(tempAttribType));
                                            
                    } else {
                       
                    }
                    handledAttributes.add("Type");
                    
                    // handle attribute "MimeType"
                    java.lang.String tempAttribMimeType =
                        
                                reader.getAttributeValue(null,"MimeType");
                            
                   if (tempAttribMimeType!=null){
                         java.lang.String content = tempAttribMimeType;
                        
                                                 object.setMimeType(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(tempAttribMimeType));
                                            
                    } else {
                       
                    }
                    handledAttributes.add("MimeType");
                    
                    // handle attribute "Encoding"
                    java.lang.String tempAttribEncoding =
                        
                                reader.getAttributeValue(null,"Encoding");
                            
                   if (tempAttribEncoding!=null){
                         java.lang.String content = tempAttribEncoding;
                        
                                                 object.setEncoding(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToAnyURI(tempAttribEncoding));
                                            
                    } else {
                       
                    }
                    handledAttributes.add("Encoding");
                    
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#","EncryptionMethod").equals(reader.getName())){
                                
                                                object.setEncryptionMethod(dialog.org.w3.www._2001._04.xmlenc.EncryptionMethodType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#","KeyInfo").equals(reader.getName())){
                                
                                                object.setKeyInfo(dialog.org.w3.www._2000._09.xmldsig.KeyInfoType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#","CipherData").equals(reader.getName())){
                                
                                                object.setCipherData(dialog.org.w3.www._2001._04.xmlenc.CipherDataType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#","EncryptionProperties").equals(reader.getName())){
                                
                                                object.setEncryptionProperties(dialog.org.w3.www._2001._04.xmlenc.EncryptionPropertiesType.Factory.parse(reader));
                                              
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
           
    