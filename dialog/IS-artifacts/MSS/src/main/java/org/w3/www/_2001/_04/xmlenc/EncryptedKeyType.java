
/**
 * EncryptedKeyType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v10  Built on : Sep 04, 2013 (02:03:08 UTC)
 */

            
                package org.w3.www._2001._04.xmlenc;
            

            /**
            *  EncryptedKeyType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class EncryptedKeyType extends org.w3.www._2001._04.xmlenc.EncryptedType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = EncryptedKeyType
                Namespace URI = http://www.w3.org/2001/04/xmlenc#
                Namespace Prefix = ns2
                */
            

                        /**
                        * field for ReferenceList
                        */

                        
                                    protected org.w3.www._2001._04.xmlenc.ReferenceList_type0 localReferenceList ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localReferenceListTracker = false ;

                           public boolean isReferenceListSpecified(){
                               return localReferenceListTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.w3.www._2001._04.xmlenc.ReferenceList_type0
                           */
                           public  org.w3.www._2001._04.xmlenc.ReferenceList_type0 getReferenceList(){
                               return localReferenceList;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ReferenceList
                               */
                               public void setReferenceList(org.w3.www._2001._04.xmlenc.ReferenceList_type0 param){
                            localReferenceListTracker = param != null;
                                   
                                            this.localReferenceList=param;
                                    

                               }
                            

                        /**
                        * field for CarriedKeyName
                        */

                        
                                    protected java.lang.String localCarriedKeyName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCarriedKeyNameTracker = false ;

                           public boolean isCarriedKeyNameSpecified(){
                               return localCarriedKeyNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCarriedKeyName(){
                               return localCarriedKeyName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CarriedKeyName
                               */
                               public void setCarriedKeyName(java.lang.String param){
                            localCarriedKeyNameTracker = param != null;
                                   
                                            this.localCarriedKeyName=param;
                                    

                               }
                            

                        /**
                        * field for Recipient
                        * This was an Attribute!
                        */

                        
                                    protected java.lang.String localRecipient ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getRecipient(){
                               return localRecipient;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Recipient
                               */
                               public void setRecipient(java.lang.String param){
                            
                                            this.localRecipient=param;
                                    

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
                

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.w3.org/2001/04/xmlenc#");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":EncryptedKeyType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "EncryptedKeyType",
                           xmlWriter);
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
                                    
                                            if (localRecipient != null){
                                        
                                                writeAttribute("",
                                                         "Recipient",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRecipient), xmlWriter);

                                            
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
                                        } if (localReferenceListTracker){
                                            if (localReferenceList==null){
                                                 throw new org.apache.axis2.databinding.ADBException("ReferenceList cannot be null!!");
                                            }
                                           localReferenceList.serialize(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#","ReferenceList"),
                                               xmlWriter);
                                        } if (localCarriedKeyNameTracker){
                                    namespace = "http://www.w3.org/2001/04/xmlenc#";
                                    writeStartElement(null, namespace, "CarriedKeyName", xmlWriter);
                             

                                          if (localCarriedKeyName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("CarriedKeyName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCarriedKeyName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
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
                    attribList.add(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#","EncryptedKeyType"));
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
                                } if (localReferenceListTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#",
                                                                      "ReferenceList"));
                            
                            
                                    if (localReferenceList==null){
                                         throw new org.apache.axis2.databinding.ADBException("ReferenceList cannot be null!!");
                                    }
                                    elementList.add(localReferenceList);
                                } if (localCarriedKeyNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#",
                                                                      "CarriedKeyName"));
                                 
                                        if (localCarriedKeyName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCarriedKeyName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("CarriedKeyName cannot be null!!");
                                        }
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
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","Recipient"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRecipient));
                                

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
        public static EncryptedKeyType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            EncryptedKeyType object =
                new EncryptedKeyType();

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
                    
                            if (!"EncryptedKeyType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (EncryptedKeyType)org.w3.www.xml._1998.namespace.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

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
                    
                    // handle attribute "Recipient"
                    java.lang.String tempAttribRecipient =
                        
                                reader.getAttributeValue(null,"Recipient");
                            
                   if (tempAttribRecipient!=null){
                         java.lang.String content = tempAttribRecipient;
                        
                                                 object.setRecipient(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(tempAttribRecipient));
                                            
                    } else {
                       
                    }
                    handledAttributes.add("Recipient");
                    
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#","EncryptionMethod").equals(reader.getName())){
                                
                                                object.setEncryptionMethod(org.w3.www._2001._04.xmlenc.EncryptionMethodType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#","KeyInfo").equals(reader.getName())){
                                
                                                object.setKeyInfo(org.w3.www._2000._09.xmldsig.KeyInfoType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#","CipherData").equals(reader.getName())){
                                
                                                object.setCipherData(org.w3.www._2001._04.xmlenc.CipherDataType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#","EncryptionProperties").equals(reader.getName())){
                                
                                                object.setEncryptionProperties(org.w3.www._2001._04.xmlenc.EncryptionPropertiesType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#","ReferenceList").equals(reader.getName())){
                                
                                                object.setReferenceList(org.w3.www._2001._04.xmlenc.ReferenceList_type0.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#","CarriedKeyName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CarriedKeyName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCarriedKeyName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
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
           
    