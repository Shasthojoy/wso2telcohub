
/**
 * KeyInfoTypeChoice_type0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v10  Built on : Sep 04, 2013 (02:03:08 UTC)
 */

            
                package org.w3.www._2000._09.xmldsig;
            

            /**
            *  KeyInfoTypeChoice_type0 bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class KeyInfoTypeChoice_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = KeyInfoTypeChoice_type0
                Namespace URI = http://www.w3.org/2000/09/xmldsig#
                Namespace Prefix = ns3
                */
            
            /** Whenever a new property is set ensure all others are unset
             *  There can be only one choice and the last one wins
             */
            private void clearAllSettingTrackers() {
            
                   localKeyNameTracker = false;
                
                   localPGPDataTracker = false;
                
                   localRetrievalMethodTracker = false;
                
                   localSPKIDataTracker = false;
                
                   localKeyValueTracker = false;
                
                   localMgmtDataTracker = false;
                
                   localX509DataTracker = false;
                
                   localExtraElementTracker = false;
                
            }
        

                        /**
                        * field for KeyName
                        */

                        
                                    protected java.lang.String localKeyName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localKeyNameTracker = false ;

                           public boolean isKeyNameSpecified(){
                               return localKeyNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getKeyName(){
                               return localKeyName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param KeyName
                               */
                               public void setKeyName(java.lang.String param){
                            
                                clearAllSettingTrackers();
                            localKeyNameTracker = param != null;
                                   
                                            this.localKeyName=param;
                                    

                               }
                            

                        /**
                        * field for PGPData
                        */

                        
                                    protected org.w3.www._2000._09.xmldsig.PGPDataType localPGPData ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPGPDataTracker = false ;

                           public boolean isPGPDataSpecified(){
                               return localPGPDataTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.w3.www._2000._09.xmldsig.PGPDataType
                           */
                           public  org.w3.www._2000._09.xmldsig.PGPDataType getPGPData(){
                               return localPGPData;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PGPData
                               */
                               public void setPGPData(org.w3.www._2000._09.xmldsig.PGPDataType param){
                            
                                clearAllSettingTrackers();
                            localPGPDataTracker = param != null;
                                   
                                            this.localPGPData=param;
                                    

                               }
                            

                        /**
                        * field for RetrievalMethod
                        */

                        
                                    protected org.w3.www._2000._09.xmldsig.RetrievalMethodType localRetrievalMethod ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRetrievalMethodTracker = false ;

                           public boolean isRetrievalMethodSpecified(){
                               return localRetrievalMethodTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.w3.www._2000._09.xmldsig.RetrievalMethodType
                           */
                           public  org.w3.www._2000._09.xmldsig.RetrievalMethodType getRetrievalMethod(){
                               return localRetrievalMethod;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RetrievalMethod
                               */
                               public void setRetrievalMethod(org.w3.www._2000._09.xmldsig.RetrievalMethodType param){
                            
                                clearAllSettingTrackers();
                            localRetrievalMethodTracker = param != null;
                                   
                                            this.localRetrievalMethod=param;
                                    

                               }
                            

                        /**
                        * field for SPKIData
                        */

                        
                                    protected org.w3.www._2000._09.xmldsig.SPKIDataType localSPKIData ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSPKIDataTracker = false ;

                           public boolean isSPKIDataSpecified(){
                               return localSPKIDataTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.w3.www._2000._09.xmldsig.SPKIDataType
                           */
                           public  org.w3.www._2000._09.xmldsig.SPKIDataType getSPKIData(){
                               return localSPKIData;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SPKIData
                               */
                               public void setSPKIData(org.w3.www._2000._09.xmldsig.SPKIDataType param){
                            
                                clearAllSettingTrackers();
                            localSPKIDataTracker = param != null;
                                   
                                            this.localSPKIData=param;
                                    

                               }
                            

                        /**
                        * field for KeyValue
                        */

                        
                                    protected org.w3.www._2000._09.xmldsig.KeyValueType localKeyValue ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localKeyValueTracker = false ;

                           public boolean isKeyValueSpecified(){
                               return localKeyValueTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.w3.www._2000._09.xmldsig.KeyValueType
                           */
                           public  org.w3.www._2000._09.xmldsig.KeyValueType getKeyValue(){
                               return localKeyValue;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param KeyValue
                               */
                               public void setKeyValue(org.w3.www._2000._09.xmldsig.KeyValueType param){
                            
                                clearAllSettingTrackers();
                            localKeyValueTracker = param != null;
                                   
                                            this.localKeyValue=param;
                                    

                               }
                            

                        /**
                        * field for MgmtData
                        */

                        
                                    protected java.lang.String localMgmtData ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMgmtDataTracker = false ;

                           public boolean isMgmtDataSpecified(){
                               return localMgmtDataTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMgmtData(){
                               return localMgmtData;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MgmtData
                               */
                               public void setMgmtData(java.lang.String param){
                            
                                clearAllSettingTrackers();
                            localMgmtDataTracker = param != null;
                                   
                                            this.localMgmtData=param;
                                    

                               }
                            

                        /**
                        * field for X509Data
                        */

                        
                                    protected org.w3.www._2000._09.xmldsig.X509DataType localX509Data ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localX509DataTracker = false ;

                           public boolean isX509DataSpecified(){
                               return localX509DataTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.w3.www._2000._09.xmldsig.X509DataType
                           */
                           public  org.w3.www._2000._09.xmldsig.X509DataType getX509Data(){
                               return localX509Data;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param X509Data
                               */
                               public void setX509Data(org.w3.www._2000._09.xmldsig.X509DataType param){
                            
                                clearAllSettingTrackers();
                            localX509DataTracker = param != null;
                                   
                                            this.localX509Data=param;
                                    

                               }
                            

                        /**
                        * field for ExtraElement
                        */

                        
                                    protected org.apache.axiom.om.OMElement localExtraElement ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localExtraElementTracker = false ;

                           public boolean isExtraElementSpecified(){
                               return localExtraElementTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.apache.axiom.om.OMElement
                           */
                           public  org.apache.axiom.om.OMElement getExtraElement(){
                               return localExtraElement;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ExtraElement
                               */
                               public void setExtraElement(org.apache.axiom.om.OMElement param){
                            
                                clearAllSettingTrackers();
                            localExtraElementTracker = param != null;
                                   
                                            this.localExtraElement=param;
                                    

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
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.w3.org/2000/09/xmldsig#");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":KeyInfoTypeChoice_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "KeyInfoTypeChoice_type0",
                           xmlWriter);
                   }

               
                   }
                if (localKeyNameTracker){
                                    namespace = "http://www.w3.org/2000/09/xmldsig#";
                                    writeStartElement(null, namespace, "KeyName", xmlWriter);
                             

                                          if (localKeyName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("KeyName cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localKeyName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPGPDataTracker){
                                            if (localPGPData==null){
                                                 throw new org.apache.axis2.databinding.ADBException("PGPData cannot be null!!");
                                            }
                                           localPGPData.serialize(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#","PGPData"),
                                               xmlWriter);
                                        } if (localRetrievalMethodTracker){
                                            if (localRetrievalMethod==null){
                                                 throw new org.apache.axis2.databinding.ADBException("RetrievalMethod cannot be null!!");
                                            }
                                           localRetrievalMethod.serialize(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#","RetrievalMethod"),
                                               xmlWriter);
                                        } if (localSPKIDataTracker){
                                            if (localSPKIData==null){
                                                 throw new org.apache.axis2.databinding.ADBException("SPKIData cannot be null!!");
                                            }
                                           localSPKIData.serialize(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#","SPKIData"),
                                               xmlWriter);
                                        } if (localKeyValueTracker){
                                            if (localKeyValue==null){
                                                 throw new org.apache.axis2.databinding.ADBException("KeyValue cannot be null!!");
                                            }
                                           localKeyValue.serialize(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#","KeyValue"),
                                               xmlWriter);
                                        } if (localMgmtDataTracker){
                                    namespace = "http://www.w3.org/2000/09/xmldsig#";
                                    writeStartElement(null, namespace, "MgmtData", xmlWriter);
                             

                                          if (localMgmtData==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("MgmtData cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMgmtData);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localX509DataTracker){
                                            if (localX509Data==null){
                                                 throw new org.apache.axis2.databinding.ADBException("X509Data cannot be null!!");
                                            }
                                           localX509Data.serialize(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#","X509Data"),
                                               xmlWriter);
                                        } if (localExtraElementTracker){
                            
                            if (localExtraElement != null) {
                                localExtraElement.serialize(xmlWriter);
                            } else {
                               throw new org.apache.axis2.databinding.ADBException("extraElement cannot be null!!");
                            }
                        }

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.w3.org/2000/09/xmldsig#")){
                return "ns3";
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

                 if (localKeyNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#",
                                                                      "KeyName"));
                                 
                                        if (localKeyName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localKeyName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("KeyName cannot be null!!");
                                        }
                                    } if (localPGPDataTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#",
                                                                      "PGPData"));
                            
                            
                                    if (localPGPData==null){
                                         throw new org.apache.axis2.databinding.ADBException("PGPData cannot be null!!");
                                    }
                                    elementList.add(localPGPData);
                                } if (localRetrievalMethodTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#",
                                                                      "RetrievalMethod"));
                            
                            
                                    if (localRetrievalMethod==null){
                                         throw new org.apache.axis2.databinding.ADBException("RetrievalMethod cannot be null!!");
                                    }
                                    elementList.add(localRetrievalMethod);
                                } if (localSPKIDataTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#",
                                                                      "SPKIData"));
                            
                            
                                    if (localSPKIData==null){
                                         throw new org.apache.axis2.databinding.ADBException("SPKIData cannot be null!!");
                                    }
                                    elementList.add(localSPKIData);
                                } if (localKeyValueTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#",
                                                                      "KeyValue"));
                            
                            
                                    if (localKeyValue==null){
                                         throw new org.apache.axis2.databinding.ADBException("KeyValue cannot be null!!");
                                    }
                                    elementList.add(localKeyValue);
                                } if (localMgmtDataTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#",
                                                                      "MgmtData"));
                                 
                                        if (localMgmtData != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMgmtData));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("MgmtData cannot be null!!");
                                        }
                                    } if (localX509DataTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#",
                                                                      "X509Data"));
                            
                            
                                    if (localX509Data==null){
                                         throw new org.apache.axis2.databinding.ADBException("X509Data cannot be null!!");
                                    }
                                    elementList.add(localX509Data);
                                } if (localExtraElementTracker){
                            if (localExtraElement != null){
                                elementList.add(org.apache.axis2.databinding.utils.Constants.OM_ELEMENT_KEY);
                                elementList.add(localExtraElement);
                            } else {
                               throw new org.apache.axis2.databinding.ADBException("extraElement cannot be null!!");
                            }
                        }

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
        public static KeyInfoTypeChoice_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            KeyInfoTypeChoice_type0 object =
                new KeyInfoTypeChoice_type0();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#","KeyName").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"KeyName" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setKeyName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                        else
                                    
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#","PGPData").equals(reader.getName())){
                                
                                                object.setPGPData(org.w3.www._2000._09.xmldsig.PGPDataType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                        else
                                    
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#","RetrievalMethod").equals(reader.getName())){
                                
                                                object.setRetrievalMethod(org.w3.www._2000._09.xmldsig.RetrievalMethodType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                        else
                                    
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#","SPKIData").equals(reader.getName())){
                                
                                                object.setSPKIData(org.w3.www._2000._09.xmldsig.SPKIDataType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                        else
                                    
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#","KeyValue").equals(reader.getName())){
                                
                                                object.setKeyValue(org.w3.www._2000._09.xmldsig.KeyValueType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                        else
                                    
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#","MgmtData").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"MgmtData" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMgmtData(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                        else
                                    
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.w3.org/2000/09/xmldsig#","X509Data").equals(reader.getName())){
                                
                                                object.setX509Data(org.w3.www._2000._09.xmldsig.X509DataType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                        else
                                    
                                   if (reader.isStartElement()){
                                
                                     
                                    
                                     //use the QName from the parser as the name for the builder
                                     javax.xml.namespace.QName startQname8 = reader.getName();

                                     // We need to wrap the reader so that it produces a fake START_DOCUMENT event
                                     // this is needed by the builder classes
                                     org.apache.axis2.databinding.utils.NamedStaxOMBuilder builder8 =
                                         new org.apache.axis2.databinding.utils.NamedStaxOMBuilder(
                                             new org.apache.axis2.util.StreamWrapper(reader),startQname8);
                                     object.setExtraElement(builder8.getOMElement());
                                       
                                         reader.next();
                                     
                              }  // End of if for expected property start element
                                



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    