
/**
 * AP_Info_type0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1-wso2v10  Built on : Sep 04, 2013 (02:03:08 UTC)
 */

            
                package org.etsi.uri.ts102204.v1_1_2;
            

            /**
            *  AP_Info_type0 bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class AP_Info_type0
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = AP_Info_type0
                Namespace URI = http://uri.etsi.org/TS102204/v1.1.2#
                Namespace Prefix = ns4
                */
            

                        /**
                        * field for AP_ID
                        * This was an Attribute!
                        */

                        
                                    protected org.apache.axis2.databinding.types.URI localAP_ID ;
                                

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.URI
                           */
                           public  org.apache.axis2.databinding.types.URI getAP_ID(){
                               return localAP_ID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AP_ID
                               */
                               public void setAP_ID(org.apache.axis2.databinding.types.URI param){
                            
                                            this.localAP_ID=param;
                                    

                               }
                            

                        /**
                        * field for AP_TransID
                        * This was an Attribute!
                        */

                        
                                    protected org.apache.axis2.databinding.types.NCName localAP_TransID ;
                                

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.NCName
                           */
                           public  org.apache.axis2.databinding.types.NCName getAP_TransID(){
                               return localAP_TransID;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AP_TransID
                               */
                               public void setAP_TransID(org.apache.axis2.databinding.types.NCName param){
                            
                                            this.localAP_TransID=param;
                                    

                               }
                            

                        /**
                        * field for AP_PWD
                        * This was an Attribute!
                        */

                        
                                    protected java.lang.String localAP_PWD ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAP_PWD(){
                               return localAP_PWD;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AP_PWD
                               */
                               public void setAP_PWD(java.lang.String param){
                            
                                            this.localAP_PWD=param;
                                    

                               }
                            

                        /**
                        * field for Instant
                        * This was an Attribute!
                        */

                        
                                    protected java.util.Calendar localInstant ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getInstant(){
                               return localInstant;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Instant
                               */
                               public void setInstant(java.util.Calendar param){
                            
                                            this.localInstant=param;
                                    

                               }
                            

                        /**
                        * field for AP_URL
                        * This was an Attribute!
                        */

                        
                                    protected org.apache.axis2.databinding.types.URI localAP_URL ;
                                

                           /**
                           * Auto generated getter method
                           * @return org.apache.axis2.databinding.types.URI
                           */
                           public  org.apache.axis2.databinding.types.URI getAP_URL(){
                               return localAP_URL;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AP_URL
                               */
                               public void setAP_URL(org.apache.axis2.databinding.types.URI param){
                            
                                            this.localAP_URL=param;
                                    

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
                           namespacePrefix+":AP_Info_type0",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "AP_Info_type0",
                           xmlWriter);
                   }

               
                   }
               
                                            if (localAP_ID != null){
                                        
                                                writeAttribute("",
                                                         "AP_ID",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAP_ID), xmlWriter);

                                            
                                      }
                                    
                                      else {
                                          throw new org.apache.axis2.databinding.ADBException("required attribute localAP_ID is null");
                                      }
                                    
                                            if (localAP_TransID != null){
                                        
                                                writeAttribute("",
                                                         "AP_TransID",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAP_TransID), xmlWriter);

                                            
                                      }
                                    
                                      else {
                                          throw new org.apache.axis2.databinding.ADBException("required attribute localAP_TransID is null");
                                      }
                                    
                                            if (localAP_PWD != null){
                                        
                                                writeAttribute("",
                                                         "AP_PWD",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAP_PWD), xmlWriter);

                                            
                                      }
                                    
                                      else {
                                          throw new org.apache.axis2.databinding.ADBException("required attribute localAP_PWD is null");
                                      }
                                    
                                            if (localInstant != null){
                                        
                                                writeAttribute("",
                                                         "Instant",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInstant), xmlWriter);

                                            
                                      }
                                    
                                      else {
                                          throw new org.apache.axis2.databinding.ADBException("required attribute localInstant is null");
                                      }
                                    
                                            if (localAP_URL != null){
                                        
                                                writeAttribute("",
                                                         "AP_URL",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAP_URL), xmlWriter);

                                            
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

                
                            attribList.add(
                            new javax.xml.namespace.QName("","AP_ID"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAP_ID));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","AP_TransID"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAP_TransID));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","AP_PWD"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAP_PWD));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","Instant"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localInstant));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","AP_URL"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAP_URL));
                                

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
        public static AP_Info_type0 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            AP_Info_type0 object =
                new AP_Info_type0();

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
                    
                            if (!"AP_Info_type0".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (AP_Info_type0)org.w3.www.xml._1998.namespace.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    // handle attribute "AP_ID"
                    java.lang.String tempAttribAP_ID =
                        
                                reader.getAttributeValue(null,"AP_ID");
                            
                   if (tempAttribAP_ID!=null){
                         java.lang.String content = tempAttribAP_ID;
                        
                                                 object.setAP_ID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToAnyURI(tempAttribAP_ID));
                                            
                    } else {
                       
                               throw new org.apache.axis2.databinding.ADBException("Required attribute AP_ID is missing");
                           
                    }
                    handledAttributes.add("AP_ID");
                    
                    // handle attribute "AP_TransID"
                    java.lang.String tempAttribAP_TransID =
                        
                                reader.getAttributeValue(null,"AP_TransID");
                            
                   if (tempAttribAP_TransID!=null){
                         java.lang.String content = tempAttribAP_TransID;
                        
                                                 object.setAP_TransID(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToNCName(tempAttribAP_TransID));
                                            
                    } else {
                       
                               throw new org.apache.axis2.databinding.ADBException("Required attribute AP_TransID is missing");
                           
                    }
                    handledAttributes.add("AP_TransID");
                    
                    // handle attribute "AP_PWD"
                    java.lang.String tempAttribAP_PWD =
                        
                                reader.getAttributeValue(null,"AP_PWD");
                            
                   if (tempAttribAP_PWD!=null){
                         java.lang.String content = tempAttribAP_PWD;
                        
                                                 object.setAP_PWD(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(tempAttribAP_PWD));
                                            
                    } else {
                       
                              // throw new org.apache.axis2.databinding.ADBException("Required attribute AP_PWD is missing");
                           
                    }
                    handledAttributes.add("AP_PWD");
                    
                    // handle attribute "Instant"
                    java.lang.String tempAttribInstant =
                        
                                reader.getAttributeValue(null,"Instant");
                            
                   if (tempAttribInstant!=null){
                         java.lang.String content = tempAttribInstant;
                        
                                                 object.setInstant(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(tempAttribInstant));
                                            
                    } else {
                       
                               throw new org.apache.axis2.databinding.ADBException("Required attribute Instant is missing");
                           
                    }
                    handledAttributes.add("Instant");
                    
                    // handle attribute "AP_URL"
                    java.lang.String tempAttribAP_URL =
                        
                                reader.getAttributeValue(null,"AP_URL");
                            
                   if (tempAttribAP_URL!=null){
                         java.lang.String content = tempAttribAP_URL;
                        
                                                 object.setAP_URL(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToAnyURI(tempAttribAP_URL));
                                            
                    } else {
                       
                    }
                    handledAttributes.add("AP_URL");
                    
                    
                    reader.next();
                  
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
           
    