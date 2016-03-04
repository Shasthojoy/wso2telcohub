
/**
 * MobileUserType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v10  Built on : Sep 04, 2013 (02:03:08 UTC)
 */

            
                package org.etsi.uri.ts102204.v1_1_2;
            

            /**
            *  MobileUserType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class MobileUserType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = MobileUserType
                Namespace URI = http://uri.etsi.org/TS102204/v1.1.2#
                Namespace Prefix = ns4
                */
            

                        /**
                        * field for IdentityIssuer
                        */

                        
                                    protected org.etsi.uri.ts102204.v1_1_2.MeshMemberType localIdentityIssuer ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIdentityIssuerTracker = false ;

                           public boolean isIdentityIssuerSpecified(){
                               return localIdentityIssuerTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.etsi.uri.ts102204.v1_1_2.MeshMemberType
                           */
                           public  org.etsi.uri.ts102204.v1_1_2.MeshMemberType getIdentityIssuer(){
                               return localIdentityIssuer;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IdentityIssuer
                               */
                               public void setIdentityIssuer(org.etsi.uri.ts102204.v1_1_2.MeshMemberType param){
                            localIdentityIssuerTracker = param != null;
                                   
                                            this.localIdentityIssuer=param;
                                    

                               }
                            

                        /**
                        * field for UserIdentifier
                        */

                        
                                    protected java.lang.String localUserIdentifier ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localUserIdentifierTracker = false ;

                           public boolean isUserIdentifierSpecified(){
                               return localUserIdentifierTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getUserIdentifier(){
                               return localUserIdentifier;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param UserIdentifier
                               */
                               public void setUserIdentifier(java.lang.String param){
                            localUserIdentifierTracker = param != null;
                                   
                                            this.localUserIdentifier=param;
                                    

                               }
                            

                        /**
                        * field for HomeMSSP
                        */

                        
                                    protected org.etsi.uri.ts102204.v1_1_2.MeshMemberType localHomeMSSP ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHomeMSSPTracker = false ;

                           public boolean isHomeMSSPSpecified(){
                               return localHomeMSSPTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.etsi.uri.ts102204.v1_1_2.MeshMemberType
                           */
                           public  org.etsi.uri.ts102204.v1_1_2.MeshMemberType getHomeMSSP(){
                               return localHomeMSSP;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param HomeMSSP
                               */
                               public void setHomeMSSP(org.etsi.uri.ts102204.v1_1_2.MeshMemberType param){
                            localHomeMSSPTracker = param != null;
                                   
                                            this.localHomeMSSP=param;
                                    

                               }
                            

                        /**
                        * field for MSISDN
                        */

                        
                                    protected java.lang.String localMSISDN ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMSISDNTracker = false ;

                           public boolean isMSISDNSpecified(){
                               return localMSISDNTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMSISDN(){
                               return localMSISDN;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MSISDN
                               */
                               public void setMSISDN(java.lang.String param){
                            localMSISDNTracker = param != null;
                                   
                                            this.localMSISDN=param;
                                    

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
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://uri.etsi.org/TS102204/v1.1.2#");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":MobileUserType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "MobileUserType",
                           xmlWriter);
                   }

               
                   }
                if (localIdentityIssuerTracker){
                                            if (localIdentityIssuer==null){
                                                 throw new org.apache.axis2.databinding.ADBException("IdentityIssuer cannot be null!!");
                                            }
                                           localIdentityIssuer.serialize(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","IdentityIssuer"),
                                               xmlWriter);
                                        } if (localUserIdentifierTracker){
                                    namespace = "http://uri.etsi.org/TS102204/v1.1.2#";
                                    writeStartElement(null, namespace, "UserIdentifier", xmlWriter);
                             

                                          if (localUserIdentifier==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("UserIdentifier cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localUserIdentifier);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localHomeMSSPTracker){
                                            if (localHomeMSSP==null){
                                                 throw new org.apache.axis2.databinding.ADBException("HomeMSSP cannot be null!!");
                                            }
                                           localHomeMSSP.serialize(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","HomeMSSP"),
                                               xmlWriter);
                                        } if (localMSISDNTracker){
                                    namespace = "http://uri.etsi.org/TS102204/v1.1.2#";
                                    writeStartElement(null, namespace, "MSISDN", xmlWriter);
                             

                                          if (localMSISDN==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("MSISDN cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMSISDN);
                                            
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

                 if (localIdentityIssuerTracker){
                            elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                      "IdentityIssuer"));
                            
                            
                                    if (localIdentityIssuer==null){
                                         throw new org.apache.axis2.databinding.ADBException("IdentityIssuer cannot be null!!");
                                    }
                                    elementList.add(localIdentityIssuer);
                                } if (localUserIdentifierTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                      "UserIdentifier"));
                                 
                                        if (localUserIdentifier != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localUserIdentifier));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("UserIdentifier cannot be null!!");
                                        }
                                    } if (localHomeMSSPTracker){
                            elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                      "HomeMSSP"));
                            
                            
                                    if (localHomeMSSP==null){
                                         throw new org.apache.axis2.databinding.ADBException("HomeMSSP cannot be null!!");
                                    }
                                    elementList.add(localHomeMSSP);
                                } if (localMSISDNTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                      "MSISDN"));
                                 
                                        if (localMSISDN != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMSISDN));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("MSISDN cannot be null!!");
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
        public static MobileUserType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            MobileUserType object =
                new MobileUserType();

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
                    
                            if (!"MobileUserType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (MobileUserType)org.w3.www.xml._1998.namespace.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","IdentityIssuer").equals(reader.getName())){
                                
                                                object.setIdentityIssuer(org.etsi.uri.ts102204.v1_1_2.MeshMemberType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","UserIdentifier").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"UserIdentifier" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setUserIdentifier(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","HomeMSSP").equals(reader.getName())){
                                
                                                object.setHomeMSSP(org.etsi.uri.ts102204.v1_1_2.MeshMemberType.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","MSISDN").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"MSISDN" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMSISDN(
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
           
    