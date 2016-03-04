
/**
 * SecureMethods_type0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package dialog.org.etsi.uri.ts102204.v1_1_2;
            

            /**
            *  SecureMethods_type0 bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class SecureMethods_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = SecureMethods_type0
                Namespace URI = http://uri.etsi.org/TS102204/v1.1.2#
                Namespace Prefix = ns5
                */
            

                        /**
                        * field for MSS_Signature
                        * This was an Attribute!
                        */

                        
                                    protected boolean localMSS_Signature ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getMSS_Signature(){
                               return localMSS_Signature;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MSS_Signature
                               */
                               public void setMSS_Signature(boolean param){
                            
                                            this.localMSS_Signature=param;
                                    

                               }
                            

                        /**
                        * field for MSS_Registration
                        * This was an Attribute!
                        */

                        
                                    protected boolean localMSS_Registration ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getMSS_Registration(){
                               return localMSS_Registration;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MSS_Registration
                               */
                               public void setMSS_Registration(boolean param){
                            
                                            this.localMSS_Registration=param;
                                    

                               }
                            

                        /**
                        * field for MSS_Notification
                        * This was an Attribute!
                        */

                        
                                    protected boolean localMSS_Notification ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getMSS_Notification(){
                               return localMSS_Notification;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MSS_Notification
                               */
                               public void setMSS_Notification(boolean param){
                            
                                            this.localMSS_Notification=param;
                                    

                               }
                            

                        /**
                        * field for MSS_ProfileQuery
                        * This was an Attribute!
                        */

                        
                                    protected boolean localMSS_ProfileQuery ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getMSS_ProfileQuery(){
                               return localMSS_ProfileQuery;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MSS_ProfileQuery
                               */
                               public void setMSS_ProfileQuery(boolean param){
                            
                                            this.localMSS_ProfileQuery=param;
                                    

                               }
                            

                        /**
                        * field for MSS_Receipt
                        * This was an Attribute!
                        */

                        
                                    protected boolean localMSS_Receipt ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getMSS_Receipt(){
                               return localMSS_Receipt;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MSS_Receipt
                               */
                               public void setMSS_Receipt(boolean param){
                            
                                            this.localMSS_Receipt=param;
                                    

                               }
                            

                        /**
                        * field for MSS_Status
                        * This was an Attribute!
                        */

                        
                                    protected boolean localMSS_Status ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getMSS_Status(){
                               return localMSS_Status;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MSS_Status
                               */
                               public void setMSS_Status(boolean param){
                            
                                            this.localMSS_Status=param;
                                    

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
                           namespacePrefix+":SecureMethods_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "SecureMethods_type0",
                           xmlWriter);
                   }

               
                   }
               
                                                   if (true) {
                                               
                                                writeAttribute("",
                                                         "MSS_Signature",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMSS_Signature), xmlWriter);

                                            
                                      }
                                    
                                      else {
                                          throw new org.apache.axis2.databinding.ADBException("required attribute localMSS_Signature is null");
                                      }
                                    
                                                   if (true) {
                                               
                                                writeAttribute("",
                                                         "MSS_Registration",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMSS_Registration), xmlWriter);

                                            
                                      }
                                    
                                      else {
                                          throw new org.apache.axis2.databinding.ADBException("required attribute localMSS_Registration is null");
                                      }
                                    
                                                   if (true) {
                                               
                                                writeAttribute("",
                                                         "MSS_Notification",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMSS_Notification), xmlWriter);

                                            
                                      }
                                    
                                      else {
                                          throw new org.apache.axis2.databinding.ADBException("required attribute localMSS_Notification is null");
                                      }
                                    
                                                   if (true) {
                                               
                                                writeAttribute("",
                                                         "MSS_ProfileQuery",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMSS_ProfileQuery), xmlWriter);

                                            
                                      }
                                    
                                      else {
                                          throw new org.apache.axis2.databinding.ADBException("required attribute localMSS_ProfileQuery is null");
                                      }
                                    
                                                   if (true) {
                                               
                                                writeAttribute("",
                                                         "MSS_Receipt",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMSS_Receipt), xmlWriter);

                                            
                                      }
                                    
                                      else {
                                          throw new org.apache.axis2.databinding.ADBException("required attribute localMSS_Receipt is null");
                                      }
                                    
                                                   if (true) {
                                               
                                                writeAttribute("",
                                                         "MSS_Status",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMSS_Status), xmlWriter);

                                            
                                      }
                                    
                                      else {
                                          throw new org.apache.axis2.databinding.ADBException("required attribute localMSS_Status is null");
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

                
                            attribList.add(
                            new javax.xml.namespace.QName("","MSS_Signature"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMSS_Signature));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","MSS_Registration"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMSS_Registration));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","MSS_Notification"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMSS_Notification));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","MSS_ProfileQuery"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMSS_ProfileQuery));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","MSS_Receipt"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMSS_Receipt));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","MSS_Status"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMSS_Status));
                                

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
        public static SecureMethods_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            SecureMethods_type0 object =
                new SecureMethods_type0();

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
                    
                            if (!"SecureMethods_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (SecureMethods_type0)dialog.es.telefonica.mobileconnect.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    // handle attribute "MSS_Signature"
                    java.lang.String tempAttribMSS_Signature =
                        
                                reader.getAttributeValue(null,"MSS_Signature");
                            
                   if (tempAttribMSS_Signature!=null){
                         java.lang.String content = tempAttribMSS_Signature;
                        
                                                 object.setMSS_Signature(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(tempAttribMSS_Signature));
                                            
                    } else {
                       
                               throw new org.apache.axis2.databinding.ADBException("Required attribute MSS_Signature is missing");
                           
                    }
                    handledAttributes.add("MSS_Signature");
                    
                    // handle attribute "MSS_Registration"
                    java.lang.String tempAttribMSS_Registration =
                        
                                reader.getAttributeValue(null,"MSS_Registration");
                            
                   if (tempAttribMSS_Registration!=null){
                         java.lang.String content = tempAttribMSS_Registration;
                        
                                                 object.setMSS_Registration(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(tempAttribMSS_Registration));
                                            
                    } else {
                       
                               throw new org.apache.axis2.databinding.ADBException("Required attribute MSS_Registration is missing");
                           
                    }
                    handledAttributes.add("MSS_Registration");
                    
                    // handle attribute "MSS_Notification"
                    java.lang.String tempAttribMSS_Notification =
                        
                                reader.getAttributeValue(null,"MSS_Notification");
                            
                   if (tempAttribMSS_Notification!=null){
                         java.lang.String content = tempAttribMSS_Notification;
                        
                                                 object.setMSS_Notification(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(tempAttribMSS_Notification));
                                            
                    } else {
                       
                               throw new org.apache.axis2.databinding.ADBException("Required attribute MSS_Notification is missing");
                           
                    }
                    handledAttributes.add("MSS_Notification");
                    
                    // handle attribute "MSS_ProfileQuery"
                    java.lang.String tempAttribMSS_ProfileQuery =
                        
                                reader.getAttributeValue(null,"MSS_ProfileQuery");
                            
                   if (tempAttribMSS_ProfileQuery!=null){
                         java.lang.String content = tempAttribMSS_ProfileQuery;
                        
                                                 object.setMSS_ProfileQuery(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(tempAttribMSS_ProfileQuery));
                                            
                    } else {
                       
                               throw new org.apache.axis2.databinding.ADBException("Required attribute MSS_ProfileQuery is missing");
                           
                    }
                    handledAttributes.add("MSS_ProfileQuery");
                    
                    // handle attribute "MSS_Receipt"
                    java.lang.String tempAttribMSS_Receipt =
                        
                                reader.getAttributeValue(null,"MSS_Receipt");
                            
                   if (tempAttribMSS_Receipt!=null){
                         java.lang.String content = tempAttribMSS_Receipt;
                        
                                                 object.setMSS_Receipt(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(tempAttribMSS_Receipt));
                                            
                    } else {
                       
                               throw new org.apache.axis2.databinding.ADBException("Required attribute MSS_Receipt is missing");
                           
                    }
                    handledAttributes.add("MSS_Receipt");
                    
                    // handle attribute "MSS_Status"
                    java.lang.String tempAttribMSS_Status =
                        
                                reader.getAttributeValue(null,"MSS_Status");
                            
                   if (tempAttribMSS_Status!=null){
                         java.lang.String content = tempAttribMSS_Status;
                        
                                                 object.setMSS_Status(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(tempAttribMSS_Status));
                                            
                    } else {
                       
                               throw new org.apache.axis2.databinding.ADBException("Required attribute MSS_Status is missing");
                           
                    }
                    handledAttributes.add("MSS_Status");
                    
                    
                    reader.next();
                



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    