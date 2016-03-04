
/**
 * KeyReferenceType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v10  Built on : Sep 04, 2013 (02:03:08 UTC)
 */

            
                package org.etsi.uri.ts102204.v1_1_2;
            

            /**
            *  KeyReferenceType bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class KeyReferenceType
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = KeyReferenceType
                Namespace URI = http://uri.etsi.org/TS102204/v1.1.2#
                Namespace Prefix = ns4
                */
            

                        /**
                        * field for CertificateURL
                        * This was an Array!
                        */

                        
                                    protected org.apache.axis2.databinding.types.URI[] localCertificateURL ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCertificateURLTracker = false ;

                           public boolean isCertificateURLSpecified(){
                               return localCertificateURLTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.URI[]
                           */
                           public  org.apache.axis2.databinding.types.URI[] getCertificateURL(){
                               return localCertificateURL;
                           }

                           
                        


                               
                              /**
                               * validate the array for CertificateURL
                               */
                              protected void validateCertificateURL(org.apache.axis2.databinding.types.URI[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param CertificateURL
                              */
                              public void setCertificateURL(org.apache.axis2.databinding.types.URI[] param){
                              
                                   validateCertificateURL(param);

                               localCertificateURLTracker = param != null;
                                      
                                      this.localCertificateURL=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param org.apache.axis2.databinding.types.URI
                             */
                             public void addCertificateURL(org.apache.axis2.databinding.types.URI param){
                                   if (localCertificateURL == null){
                                   localCertificateURL = new org.apache.axis2.databinding.types.URI[]{};
                                   }

                            
                                 //update the setting tracker
                                localCertificateURLTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localCertificateURL);
                               list.add(param);
                               this.localCertificateURL =
                             (org.apache.axis2.databinding.types.URI[])list.toArray(
                            new org.apache.axis2.databinding.types.URI[list.size()]);

                             }
                             

                        /**
                        * field for CertificateIssuerDN
                        * This was an Array!
                        */

                        
                                    protected java.lang.String[] localCertificateIssuerDN ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localCertificateIssuerDNTracker = false ;

                           public boolean isCertificateIssuerDNSpecified(){
                               return localCertificateIssuerDNTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String[]
                           */
                           public  java.lang.String[] getCertificateIssuerDN(){
                               return localCertificateIssuerDN;
                           }

                           
                        


                               
                              /**
                               * validate the array for CertificateIssuerDN
                               */
                              protected void validateCertificateIssuerDN(java.lang.String[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param CertificateIssuerDN
                              */
                              public void setCertificateIssuerDN(java.lang.String[] param){
                              
                                   validateCertificateIssuerDN(param);

                               localCertificateIssuerDNTracker = param != null;
                                      
                                      this.localCertificateIssuerDN=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param java.lang.String
                             */
                             public void addCertificateIssuerDN(java.lang.String param){
                                   if (localCertificateIssuerDN == null){
                                   localCertificateIssuerDN = new java.lang.String[]{};
                                   }

                            
                                 //update the setting tracker
                                localCertificateIssuerDNTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localCertificateIssuerDN);
                               list.add(param);
                               this.localCertificateIssuerDN =
                             (java.lang.String[])list.toArray(
                            new java.lang.String[list.size()]);

                             }
                             

                        /**
                        * field for HashOfUsersPublicKey
                        * This was an Array!
                        */

                        
                                    protected org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[] localHashOfUsersPublicKey ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHashOfUsersPublicKeyTracker = false ;

                           public boolean isHashOfUsersPublicKeySpecified(){
                               return localHashOfUsersPublicKeyTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[]
                           */
                           public  org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[] getHashOfUsersPublicKey(){
                               return localHashOfUsersPublicKey;
                           }

                           
                        


                               
                              /**
                               * validate the array for HashOfUsersPublicKey
                               */
                              protected void validateHashOfUsersPublicKey(org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param HashOfUsersPublicKey
                              */
                              public void setHashOfUsersPublicKey(org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[] param){
                              
                                   validateHashOfUsersPublicKey(param);

                               localHashOfUsersPublicKeyTracker = param != null;
                                      
                                      this.localHashOfUsersPublicKey=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType
                             */
                             public void addHashOfUsersPublicKey(org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType param){
                                   if (localHashOfUsersPublicKey == null){
                                   localHashOfUsersPublicKey = new org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[]{};
                                   }

                            
                                 //update the setting tracker
                                localHashOfUsersPublicKeyTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localHashOfUsersPublicKey);
                               list.add(param);
                               this.localHashOfUsersPublicKey =
                             (org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[])list.toArray(
                            new org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[list.size()]);

                             }
                             

                        /**
                        * field for HashOfCAPublicKey
                        * This was an Array!
                        */

                        
                                    protected org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[] localHashOfCAPublicKey ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localHashOfCAPublicKeyTracker = false ;

                           public boolean isHashOfCAPublicKeySpecified(){
                               return localHashOfCAPublicKeyTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[]
                           */
                           public  org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[] getHashOfCAPublicKey(){
                               return localHashOfCAPublicKey;
                           }

                           
                        


                               
                              /**
                               * validate the array for HashOfCAPublicKey
                               */
                              protected void validateHashOfCAPublicKey(org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[] param){
                             
                              }


                             /**
                              * Auto generated setter method
                              * @param param HashOfCAPublicKey
                              */
                              public void setHashOfCAPublicKey(org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[] param){
                              
                                   validateHashOfCAPublicKey(param);

                               localHashOfCAPublicKeyTracker = param != null;
                                      
                                      this.localHashOfCAPublicKey=param;
                              }

                               
                             
                             /**
                             * Auto generated add method for the array for convenience
                             * @param param org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType
                             */
                             public void addHashOfCAPublicKey(org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType param){
                                   if (localHashOfCAPublicKey == null){
                                   localHashOfCAPublicKey = new org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[]{};
                                   }

                            
                                 //update the setting tracker
                                localHashOfCAPublicKeyTracker = true;
                            

                               java.util.List list =
                            org.apache.axis2.databinding.utils.ConverterUtil.toList(localHashOfCAPublicKey);
                               list.add(param);
                               this.localHashOfCAPublicKey =
                             (org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[])list.toArray(
                            new org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[list.size()]);

                             }
                             

                        /**
                        * field for ExtraElement
                        */

                        
                                    protected org.apache.axiom.om.OMElement localExtraElement ;
                                

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
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://uri.etsi.org/TS102204/v1.1.2#");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":KeyReferenceType",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "KeyReferenceType",
                           xmlWriter);
                   }

               
                   }
                if (localCertificateURLTracker){
                             if (localCertificateURL!=null) {
                                   namespace = "http://uri.etsi.org/TS102204/v1.1.2#";
                                   for (int i = 0;i < localCertificateURL.length;i++){
                                        
                                            if (localCertificateURL[i] != null){
                                        
                                                writeStartElement(null, namespace, "CertificateURL", xmlWriter);

                                            
                                                        xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCertificateURL[i]));
                                                    
                                                xmlWriter.writeEndElement();
                                              
                                                } else {
                                                   
                                                           // we have to do nothing since minOccurs is zero
                                                       
                                                }

                                   }
                             } else {
                                 
                                         throw new org.apache.axis2.databinding.ADBException("CertificateURL cannot be null!!");
                                    
                             }

                        } if (localCertificateIssuerDNTracker){
                             if (localCertificateIssuerDN!=null) {
                                   namespace = "http://uri.etsi.org/TS102204/v1.1.2#";
                                   for (int i = 0;i < localCertificateIssuerDN.length;i++){
                                        
                                            if (localCertificateIssuerDN[i] != null){
                                        
                                                writeStartElement(null, namespace, "CertificateIssuerDN", xmlWriter);

                                            
                                                        xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCertificateIssuerDN[i]));
                                                    
                                                xmlWriter.writeEndElement();
                                              
                                                } else {
                                                   
                                                           // we have to do nothing since minOccurs is zero
                                                       
                                                }

                                   }
                             } else {
                                 
                                         throw new org.apache.axis2.databinding.ADBException("CertificateIssuerDN cannot be null!!");
                                    
                             }

                        } if (localHashOfUsersPublicKeyTracker){
                                       if (localHashOfUsersPublicKey!=null){
                                            for (int i = 0;i < localHashOfUsersPublicKey.length;i++){
                                                if (localHashOfUsersPublicKey[i] != null){
                                                 localHashOfUsersPublicKey[i].serialize(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","HashOfUsersPublicKey"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("HashOfUsersPublicKey cannot be null!!");
                                        
                                    }
                                 } if (localHashOfCAPublicKeyTracker){
                                       if (localHashOfCAPublicKey!=null){
                                            for (int i = 0;i < localHashOfCAPublicKey.length;i++){
                                                if (localHashOfCAPublicKey[i] != null){
                                                 localHashOfCAPublicKey[i].serialize(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","HashOfCAPublicKey"),
                                                           xmlWriter);
                                                } else {
                                                   
                                                        // we don't have to do any thing since minOccures is zero
                                                    
                                                }

                                            }
                                     } else {
                                        
                                               throw new org.apache.axis2.databinding.ADBException("HashOfCAPublicKey cannot be null!!");
                                        
                                    }
                                 }
                            
                            if (localExtraElement != null) {
                                localExtraElement.serialize(xmlWriter);
                            } else {
                               throw new org.apache.axis2.databinding.ADBException("extraElement cannot be null!!");
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

                 if (localCertificateURLTracker){
                            if (localCertificateURL!=null){
                                  for (int i = 0;i < localCertificateURL.length;i++){
                                      
                                         if (localCertificateURL[i] != null){
                                          elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                              "CertificateURL"));
                                          elementList.add(
                                          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCertificateURL[i]));
                                          } else {
                                             
                                                    // have to do nothing
                                                
                                          }
                                      

                                  }
                            } else {
                              
                                    throw new org.apache.axis2.databinding.ADBException("CertificateURL cannot be null!!");
                                
                            }

                        } if (localCertificateIssuerDNTracker){
                            if (localCertificateIssuerDN!=null){
                                  for (int i = 0;i < localCertificateIssuerDN.length;i++){
                                      
                                         if (localCertificateIssuerDN[i] != null){
                                          elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                              "CertificateIssuerDN"));
                                          elementList.add(
                                          org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCertificateIssuerDN[i]));
                                          } else {
                                             
                                                    // have to do nothing
                                                
                                          }
                                      

                                  }
                            } else {
                              
                                    throw new org.apache.axis2.databinding.ADBException("CertificateIssuerDN cannot be null!!");
                                
                            }

                        } if (localHashOfUsersPublicKeyTracker){
                             if (localHashOfUsersPublicKey!=null) {
                                 for (int i = 0;i < localHashOfUsersPublicKey.length;i++){

                                    if (localHashOfUsersPublicKey[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                          "HashOfUsersPublicKey"));
                                         elementList.add(localHashOfUsersPublicKey[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("HashOfUsersPublicKey cannot be null!!");
                                    
                             }

                        } if (localHashOfCAPublicKeyTracker){
                             if (localHashOfCAPublicKey!=null) {
                                 for (int i = 0;i < localHashOfCAPublicKey.length;i++){

                                    if (localHashOfCAPublicKey[i] != null){
                                         elementList.add(new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#",
                                                                          "HashOfCAPublicKey"));
                                         elementList.add(localHashOfCAPublicKey[i]);
                                    } else {
                                        
                                                // nothing to do
                                            
                                    }

                                 }
                             } else {
                                 
                                        throw new org.apache.axis2.databinding.ADBException("HashOfCAPublicKey cannot be null!!");
                                    
                             }

                        }
                            if (localExtraElement != null){
                                elementList.add(org.apache.axis2.databinding.utils.Constants.OM_ELEMENT_KEY);
                                elementList.add(localExtraElement);
                            } else {
                               throw new org.apache.axis2.databinding.ADBException("extraElement cannot be null!!");
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
        public static KeyReferenceType parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            KeyReferenceType object =
                new KeyReferenceType();

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
                    
                            if (!"KeyReferenceType".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (KeyReferenceType)org.w3.www.xml._1998.namespace.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                        java.util.ArrayList list1 = new java.util.ArrayList();
                    
                        java.util.ArrayList list2 = new java.util.ArrayList();
                    
                        java.util.ArrayList list3 = new java.util.ArrayList();
                    
                        java.util.ArrayList list4 = new java.util.ArrayList();
                    
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","CertificateURL").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list1.add(reader.getElementText());
                                            
                                            //loop until we find a start element that is not part of this array
                                            boolean loopDone1 = false;
                                            while(!loopDone1){
                                                // Ensure we are at the EndElement
                                                while (!reader.isEndElement()){
                                                    reader.next();
                                                }
                                                // Step out of this element
                                                reader.next();
                                                // Step to next element event.
                                                while (!reader.isStartElement() && !reader.isEndElement())
                                                    reader.next();
                                                if (reader.isEndElement()){
                                                    //two continuous end elements means we are exiting the xml structure
                                                    loopDone1 = true;
                                                } else {
                                                    if (new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","CertificateURL").equals(reader.getName())){
                                                         list1.add(reader.getElementText());
                                                        
                                                    }else{
                                                        loopDone1 = true;
                                                    }
                                                }
                                            }
                                            // call the converter utility  to convert and set the array
                                            
                                            object.setCertificateURL((org.apache.axis2.databinding.types.URI[])
                                                org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                            org.apache.axis2.databinding.types.URI.class,list1));
                                                
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","CertificateIssuerDN").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list2.add(reader.getElementText());
                                            
                                            //loop until we find a start element that is not part of this array
                                            boolean loopDone2 = false;
                                            while(!loopDone2){
                                                // Ensure we are at the EndElement
                                                while (!reader.isEndElement()){
                                                    reader.next();
                                                }
                                                // Step out of this element
                                                reader.next();
                                                // Step to next element event.
                                                while (!reader.isStartElement() && !reader.isEndElement())
                                                    reader.next();
                                                if (reader.isEndElement()){
                                                    //two continuous end elements means we are exiting the xml structure
                                                    loopDone2 = true;
                                                } else {
                                                    if (new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","CertificateIssuerDN").equals(reader.getName())){
                                                         list2.add(reader.getElementText());
                                                        
                                                    }else{
                                                        loopDone2 = true;
                                                    }
                                                }
                                            }
                                            // call the converter utility  to convert and set the array
                                            
                                                    object.setCertificateIssuerDN((java.lang.String[])
                                                        list2.toArray(new java.lang.String[list2.size()]));
                                                
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","HashOfUsersPublicKey").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list3.add(org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone3 = false;
                                                        while(!loopDone3){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone3 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","HashOfUsersPublicKey").equals(reader.getName())){
                                                                    list3.add(org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone3 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setHashOfUsersPublicKey((org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType.class,
                                                                list3));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","HashOfCAPublicKey").equals(reader.getName())){
                                
                                    
                                    
                                    // Process the array and step past its final element's end.
                                    list4.add(org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType.Factory.parse(reader));
                                                                
                                                        //loop until we find a start element that is not part of this array
                                                        boolean loopDone4 = false;
                                                        while(!loopDone4){
                                                            // We should be at the end element, but make sure
                                                            while (!reader.isEndElement())
                                                                reader.next();
                                                            // Step out of this element
                                                            reader.next();
                                                            // Step to next element event.
                                                            while (!reader.isStartElement() && !reader.isEndElement())
                                                                reader.next();
                                                            if (reader.isEndElement()){
                                                                //two continuous end elements means we are exiting the xml structure
                                                                loopDone4 = true;
                                                            } else {
                                                                if (new javax.xml.namespace.QName("http://uri.etsi.org/TS102204/v1.1.2#","HashOfCAPublicKey").equals(reader.getName())){
                                                                    list4.add(org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType.Factory.parse(reader));
                                                                        
                                                                }else{
                                                                    loopDone4 = true;
                                                                }
                                                            }
                                                        }
                                                        // call the converter utility  to convert and set the array
                                                        
                                                        object.setHashOfCAPublicKey((org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType[])
                                                            org.apache.axis2.databinding.utils.ConverterUtil.convertToArray(
                                                                org.etsi.uri.ts102204.v1_1_2.DigestAlgAndValueType.class,
                                                                list4));
                                                            
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                   if (reader.isStartElement()){
                                
                                     
                                    
                                     //use the QName from the parser as the name for the builder
                                     javax.xml.namespace.QName startQname5 = reader.getName();

                                     // We need to wrap the reader so that it produces a fake START_DOCUMENT event
                                     // this is needed by the builder classes
                                     org.apache.axis2.databinding.utils.NamedStaxOMBuilder builder5 =
                                         new org.apache.axis2.databinding.utils.NamedStaxOMBuilder(
                                             new org.apache.axis2.util.StreamWrapper(reader),startQname5);
                                     object.setExtraElement(builder5.getOMElement());
                                       
                                         reader.next();
                                     
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
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
           
    