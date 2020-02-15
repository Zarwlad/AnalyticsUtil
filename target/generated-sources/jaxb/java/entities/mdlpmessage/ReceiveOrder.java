//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.09 at 06:23:10 PM MSK 
//


package java.entities.mdlpmessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Регистрация ИС МДЛП сведений о приемке лекарственных препаратов на склад получателя
 * 
 * <p>Java class for receive_order complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="receive_order"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="subject_id" type="{}subject_id_type"/&gt;
 *         &lt;element name="shipper_id" type="{}subject_id_type"/&gt;
 *         &lt;element name="operation_date" type="{}datetimeoffset"/&gt;
 *         &lt;element name="doc_num" type="{}document_number_200_type"/&gt;
 *         &lt;element name="doc_date" type="{}date_type"/&gt;
 *         &lt;element name="receive_type" type="{}receive_type_enum"/&gt;
 *         &lt;element name="source" type="{}source_type"/&gt;
 *         &lt;element name="contract_type" type="{}contract_type_enum"/&gt;
 *         &lt;element name="contract_num" type="{}document_number_200_type" minOccurs="0"/&gt;
 *         &lt;element name="order_details"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="union" maxOccurs="25000"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;choice&gt;
 *                               &lt;element name="sgtin" type="{}sign_sgtin_type"/&gt;
 *                               &lt;element name="sscc_detail"&gt;
 *                                 &lt;complexType&gt;
 *                                   &lt;complexContent&gt;
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                       &lt;sequence&gt;
 *                                         &lt;element name="sscc" type="{}sscc_type"/&gt;
 *                                         &lt;element name="detail" maxOccurs="100" minOccurs="0"&gt;
 *                                           &lt;complexType&gt;
 *                                             &lt;complexContent&gt;
 *                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                                 &lt;sequence&gt;
 *                                                   &lt;element name="gtin" type="{}gs1_gtin_type"/&gt;
 *                                                   &lt;element name="series_number" type="{}series_number_type"/&gt;
 *                                                   &lt;element name="cost" type="{}price_type"/&gt;
 *                                                   &lt;element name="vat_value" type="{}price_type"/&gt;
 *                                                 &lt;/sequence&gt;
 *                                               &lt;/restriction&gt;
 *                                             &lt;/complexContent&gt;
 *                                           &lt;/complexType&gt;
 *                                         &lt;/element&gt;
 *                                       &lt;/sequence&gt;
 *                                     &lt;/restriction&gt;
 *                                   &lt;/complexContent&gt;
 *                                 &lt;/complexType&gt;
 *                               &lt;/element&gt;
 *                             &lt;/choice&gt;
 *                             &lt;element name="cost" type="{}price_type"/&gt;
 *                             &lt;element name="vat_value" type="{}price_type"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="action_id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" fixed="416" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "receive_order", propOrder = {
    "subjectId",
    "shipperId",
    "operationDate",
    "docNum",
    "docDate",
    "receiveType",
    "source",
    "contractType",
    "contractNum",
    "orderDetails"
})
public class ReceiveOrder {

    @XmlElement(name = "subject_id", required = true)
    protected String subjectId;
    @XmlElement(name = "shipper_id", required = true)
    protected String shipperId;
    @XmlElement(name = "operation_date", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar operationDate;
    @XmlElement(name = "doc_num", required = true)
    protected String docNum;
    @XmlElement(name = "doc_date", required = true)
    protected String docDate;
    @XmlElement(name = "receive_type")
    protected int receiveType;
    protected int source;
    @XmlElement(name = "contract_type")
    protected int contractType;
    @XmlElement(name = "contract_num")
    protected String contractNum;
    @XmlElement(name = "order_details", required = true)
    protected ReceiveOrder.OrderDetails orderDetails;
    @XmlAttribute(name = "action_id", required = true)
    protected int actionId;

    /**
     * Gets the value of the subjectId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * Sets the value of the subjectId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubjectId(String value) {
        this.subjectId = value;
    }

    /**
     * Gets the value of the shipperId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipperId() {
        return shipperId;
    }

    /**
     * Sets the value of the shipperId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipperId(String value) {
        this.shipperId = value;
    }

    /**
     * Gets the value of the operationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOperationDate() {
        return operationDate;
    }

    /**
     * Sets the value of the operationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOperationDate(XMLGregorianCalendar value) {
        this.operationDate = value;
    }

    /**
     * Gets the value of the docNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocNum() {
        return docNum;
    }

    /**
     * Sets the value of the docNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocNum(String value) {
        this.docNum = value;
    }

    /**
     * Gets the value of the docDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocDate() {
        return docDate;
    }

    /**
     * Sets the value of the docDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocDate(String value) {
        this.docDate = value;
    }

    /**
     * Gets the value of the receiveType property.
     * 
     */
    public int getReceiveType() {
        return receiveType;
    }

    /**
     * Sets the value of the receiveType property.
     * 
     */
    public void setReceiveType(int value) {
        this.receiveType = value;
    }

    /**
     * Gets the value of the source property.
     * 
     */
    public int getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     */
    public void setSource(int value) {
        this.source = value;
    }

    /**
     * Gets the value of the contractType property.
     * 
     */
    public int getContractType() {
        return contractType;
    }

    /**
     * Sets the value of the contractType property.
     * 
     */
    public void setContractType(int value) {
        this.contractType = value;
    }

    /**
     * Gets the value of the contractNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContractNum() {
        return contractNum;
    }

    /**
     * Sets the value of the contractNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContractNum(String value) {
        this.contractNum = value;
    }

    /**
     * Gets the value of the orderDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ReceiveOrder.OrderDetails }
     *     
     */
    public ReceiveOrder.OrderDetails getOrderDetails() {
        return orderDetails;
    }

    /**
     * Sets the value of the orderDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceiveOrder.OrderDetails }
     *     
     */
    public void setOrderDetails(ReceiveOrder.OrderDetails value) {
        this.orderDetails = value;
    }

    /**
     * Gets the value of the actionId property.
     * 
     */
    public int getActionId() {
        return actionId;
    }

    /**
     * Sets the value of the actionId property.
     * 
     */
    public void setActionId(int value) {
        this.actionId = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="union" maxOccurs="25000"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;choice&gt;
     *                     &lt;element name="sgtin" type="{}sign_sgtin_type"/&gt;
     *                     &lt;element name="sscc_detail"&gt;
     *                       &lt;complexType&gt;
     *                         &lt;complexContent&gt;
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                             &lt;sequence&gt;
     *                               &lt;element name="sscc" type="{}sscc_type"/&gt;
     *                               &lt;element name="detail" maxOccurs="100" minOccurs="0"&gt;
     *                                 &lt;complexType&gt;
     *                                   &lt;complexContent&gt;
     *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                       &lt;sequence&gt;
     *                                         &lt;element name="gtin" type="{}gs1_gtin_type"/&gt;
     *                                         &lt;element name="series_number" type="{}series_number_type"/&gt;
     *                                         &lt;element name="cost" type="{}price_type"/&gt;
     *                                         &lt;element name="vat_value" type="{}price_type"/&gt;
     *                                       &lt;/sequence&gt;
     *                                     &lt;/restriction&gt;
     *                                   &lt;/complexContent&gt;
     *                                 &lt;/complexType&gt;
     *                               &lt;/element&gt;
     *                             &lt;/sequence&gt;
     *                           &lt;/restriction&gt;
     *                         &lt;/complexContent&gt;
     *                       &lt;/complexType&gt;
     *                     &lt;/element&gt;
     *                   &lt;/choice&gt;
     *                   &lt;element name="cost" type="{}price_type"/&gt;
     *                   &lt;element name="vat_value" type="{}price_type"/&gt;
     *                 &lt;/sequence&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "union"
    })
    public static class OrderDetails {

        @XmlElement(required = true)
        protected List<ReceiveOrder.OrderDetails.Union> union;

        /**
         * Gets the value of the union property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the union property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getUnion().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ReceiveOrder.OrderDetails.Union }
         * 
         * 
         */
        public List<ReceiveOrder.OrderDetails.Union> getUnion() {
            if (union == null) {
                union = new ArrayList<ReceiveOrder.OrderDetails.Union>();
            }
            return this.union;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;sequence&gt;
         *         &lt;choice&gt;
         *           &lt;element name="sgtin" type="{}sign_sgtin_type"/&gt;
         *           &lt;element name="sscc_detail"&gt;
         *             &lt;complexType&gt;
         *               &lt;complexContent&gt;
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                   &lt;sequence&gt;
         *                     &lt;element name="sscc" type="{}sscc_type"/&gt;
         *                     &lt;element name="detail" maxOccurs="100" minOccurs="0"&gt;
         *                       &lt;complexType&gt;
         *                         &lt;complexContent&gt;
         *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                             &lt;sequence&gt;
         *                               &lt;element name="gtin" type="{}gs1_gtin_type"/&gt;
         *                               &lt;element name="series_number" type="{}series_number_type"/&gt;
         *                               &lt;element name="cost" type="{}price_type"/&gt;
         *                               &lt;element name="vat_value" type="{}price_type"/&gt;
         *                             &lt;/sequence&gt;
         *                           &lt;/restriction&gt;
         *                         &lt;/complexContent&gt;
         *                       &lt;/complexType&gt;
         *                     &lt;/element&gt;
         *                   &lt;/sequence&gt;
         *                 &lt;/restriction&gt;
         *               &lt;/complexContent&gt;
         *             &lt;/complexType&gt;
         *           &lt;/element&gt;
         *         &lt;/choice&gt;
         *         &lt;element name="cost" type="{}price_type"/&gt;
         *         &lt;element name="vat_value" type="{}price_type"/&gt;
         *       &lt;/sequence&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "sgtin",
            "ssccDetail",
            "cost",
            "vatValue"
        })
        public static class Union {

            protected String sgtin;
            @XmlElement(name = "sscc_detail")
            protected ReceiveOrder.OrderDetails.Union.SsccDetail ssccDetail;
            @XmlElement(required = true)
            protected BigDecimal cost;
            @XmlElement(name = "vat_value", required = true)
            protected BigDecimal vatValue;

            /**
             * Gets the value of the sgtin property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSgtin() {
                return sgtin;
            }

            /**
             * Sets the value of the sgtin property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSgtin(String value) {
                this.sgtin = value;
            }

            /**
             * Gets the value of the ssccDetail property.
             * 
             * @return
             *     possible object is
             *     {@link ReceiveOrder.OrderDetails.Union.SsccDetail }
             *     
             */
            public ReceiveOrder.OrderDetails.Union.SsccDetail getSsccDetail() {
                return ssccDetail;
            }

            /**
             * Sets the value of the ssccDetail property.
             * 
             * @param value
             *     allowed object is
             *     {@link ReceiveOrder.OrderDetails.Union.SsccDetail }
             *     
             */
            public void setSsccDetail(ReceiveOrder.OrderDetails.Union.SsccDetail value) {
                this.ssccDetail = value;
            }

            /**
             * Gets the value of the cost property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getCost() {
                return cost;
            }

            /**
             * Sets the value of the cost property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setCost(BigDecimal value) {
                this.cost = value;
            }

            /**
             * Gets the value of the vatValue property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getVatValue() {
                return vatValue;
            }

            /**
             * Sets the value of the vatValue property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setVatValue(BigDecimal value) {
                this.vatValue = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType&gt;
             *   &lt;complexContent&gt;
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *       &lt;sequence&gt;
             *         &lt;element name="sscc" type="{}sscc_type"/&gt;
             *         &lt;element name="detail" maxOccurs="100" minOccurs="0"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="gtin" type="{}gs1_gtin_type"/&gt;
             *                   &lt;element name="series_number" type="{}series_number_type"/&gt;
             *                   &lt;element name="cost" type="{}price_type"/&gt;
             *                   &lt;element name="vat_value" type="{}price_type"/&gt;
             *                 &lt;/sequence&gt;
             *               &lt;/restriction&gt;
             *             &lt;/complexContent&gt;
             *           &lt;/complexType&gt;
             *         &lt;/element&gt;
             *       &lt;/sequence&gt;
             *     &lt;/restriction&gt;
             *   &lt;/complexContent&gt;
             * &lt;/complexType&gt;
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "sscc",
                "detail"
            })
            public static class SsccDetail {

                @XmlElement(required = true)
                protected String sscc;
                protected List<ReceiveOrder.OrderDetails.Union.SsccDetail.Detail> detail;

                /**
                 * Gets the value of the sscc property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSscc() {
                    return sscc;
                }

                /**
                 * Sets the value of the sscc property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSscc(String value) {
                    this.sscc = value;
                }

                /**
                 * Gets the value of the detail property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the detail property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getDetail().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link ReceiveOrder.OrderDetails.Union.SsccDetail.Detail }
                 * 
                 * 
                 */
                public List<ReceiveOrder.OrderDetails.Union.SsccDetail.Detail> getDetail() {
                    if (detail == null) {
                        detail = new ArrayList<ReceiveOrder.OrderDetails.Union.SsccDetail.Detail>();
                    }
                    return this.detail;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType&gt;
                 *   &lt;complexContent&gt;
                 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
                 *       &lt;sequence&gt;
                 *         &lt;element name="gtin" type="{}gs1_gtin_type"/&gt;
                 *         &lt;element name="series_number" type="{}series_number_type"/&gt;
                 *         &lt;element name="cost" type="{}price_type"/&gt;
                 *         &lt;element name="vat_value" type="{}price_type"/&gt;
                 *       &lt;/sequence&gt;
                 *     &lt;/restriction&gt;
                 *   &lt;/complexContent&gt;
                 * &lt;/complexType&gt;
                 * </pre>
                 * 
                 * 
                 */
                @XmlAccessorType(XmlAccessType.FIELD)
                @XmlType(name = "", propOrder = {
                    "gtin",
                    "seriesNumber",
                    "cost",
                    "vatValue"
                })
                public static class Detail {

                    @XmlElement(required = true)
                    protected String gtin;
                    @XmlElement(name = "series_number", required = true)
                    protected String seriesNumber;
                    @XmlElement(required = true)
                    protected BigDecimal cost;
                    @XmlElement(name = "vat_value", required = true)
                    protected BigDecimal vatValue;

                    /**
                     * Gets the value of the gtin property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getGtin() {
                        return gtin;
                    }

                    /**
                     * Sets the value of the gtin property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setGtin(String value) {
                        this.gtin = value;
                    }

                    /**
                     * Gets the value of the seriesNumber property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSeriesNumber() {
                        return seriesNumber;
                    }

                    /**
                     * Sets the value of the seriesNumber property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSeriesNumber(String value) {
                        this.seriesNumber = value;
                    }

                    /**
                     * Gets the value of the cost property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getCost() {
                        return cost;
                    }

                    /**
                     * Sets the value of the cost property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setCost(BigDecimal value) {
                        this.cost = value;
                    }

                    /**
                     * Gets the value of the vatValue property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getVatValue() {
                        return vatValue;
                    }

                    /**
                     * Sets the value of the vatValue property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setVatValue(BigDecimal value) {
                        this.vatValue = value;
                    }

                }

            }

        }

    }

}
