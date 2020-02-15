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
 * Регистрация в ИС МДЛП сведений о продаже лекарственного препарата в рамках розничной торговли с использованием ККТ
 * 
 * <p>Java class for retail_sell_kkt complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="retail_sell_kkt"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="subject_id" type="{}subject_any_type"/&gt;
 *         &lt;element name="inn" type="{}inn_type"/&gt;
 *         &lt;element name="sell_details"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="receipt" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="type" type="{}receipt_type_enum"/&gt;
 *                             &lt;element name="operation_date" type="{}datetimeoffset"/&gt;
 *                             &lt;element name="prescription" minOccurs="0"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="doc_date" type="{}date_type" minOccurs="0"/&gt;
 *                                       &lt;element name="doc_series" type="{}document_number_200_type" minOccurs="0"/&gt;
 *                                       &lt;element name="doc_num" type="{}document_number_200_type" minOccurs="0"/&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="items"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;sequence&gt;
 *                                       &lt;element name="item" maxOccurs="unbounded"&gt;
 *                                         &lt;complexType&gt;
 *                                           &lt;complexContent&gt;
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                               &lt;sequence&gt;
 *                                                 &lt;element name="sgtin" type="{}sign_sgtin_type"/&gt;
 *                                                 &lt;element name="cost" type="{}price_kkt_type"/&gt;
 *                                                 &lt;element name="vat_value" type="{}price_kkt_type"/&gt;
 *                                                 &lt;element name="discount" type="{}price_kkt_type" minOccurs="0"/&gt;
 *                                                 &lt;element name="sold_part" type="{}part_type" minOccurs="0"/&gt;
 *                                               &lt;/sequence&gt;
 *                                             &lt;/restriction&gt;
 *                                           &lt;/complexContent&gt;
 *                                         &lt;/complexType&gt;
 *                                       &lt;/element&gt;
 *                                     &lt;/sequence&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
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
 *       &lt;attribute name="action_id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" fixed="10511" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "retail_sell_kkt", propOrder = {
    "subjectId",
    "inn",
    "sellDetails"
})
public class RetailSellKkt {

    @XmlElement(name = "subject_id", required = true)
    protected String subjectId;
    @XmlElement(required = true)
    protected String inn;
    @XmlElement(name = "sell_details", required = true)
    protected RetailSellKkt.SellDetails sellDetails;
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
     * Gets the value of the inn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInn() {
        return inn;
    }

    /**
     * Sets the value of the inn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInn(String value) {
        this.inn = value;
    }

    /**
     * Gets the value of the sellDetails property.
     * 
     * @return
     *     possible object is
     *     {@link RetailSellKkt.SellDetails }
     *     
     */
    public RetailSellKkt.SellDetails getSellDetails() {
        return sellDetails;
    }

    /**
     * Sets the value of the sellDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link RetailSellKkt.SellDetails }
     *     
     */
    public void setSellDetails(RetailSellKkt.SellDetails value) {
        this.sellDetails = value;
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
     *         &lt;element name="receipt" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="type" type="{}receipt_type_enum"/&gt;
     *                   &lt;element name="operation_date" type="{}datetimeoffset"/&gt;
     *                   &lt;element name="prescription" minOccurs="0"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="doc_date" type="{}date_type" minOccurs="0"/&gt;
     *                             &lt;element name="doc_series" type="{}document_number_200_type" minOccurs="0"/&gt;
     *                             &lt;element name="doc_num" type="{}document_number_200_type" minOccurs="0"/&gt;
     *                           &lt;/sequence&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="items"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;sequence&gt;
     *                             &lt;element name="item" maxOccurs="unbounded"&gt;
     *                               &lt;complexType&gt;
     *                                 &lt;complexContent&gt;
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                                     &lt;sequence&gt;
     *                                       &lt;element name="sgtin" type="{}sign_sgtin_type"/&gt;
     *                                       &lt;element name="cost" type="{}price_kkt_type"/&gt;
     *                                       &lt;element name="vat_value" type="{}price_kkt_type"/&gt;
     *                                       &lt;element name="discount" type="{}price_kkt_type" minOccurs="0"/&gt;
     *                                       &lt;element name="sold_part" type="{}part_type" minOccurs="0"/&gt;
     *                                     &lt;/sequence&gt;
     *                                   &lt;/restriction&gt;
     *                                 &lt;/complexContent&gt;
     *                               &lt;/complexType&gt;
     *                             &lt;/element&gt;
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
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "receipt"
    })
    public static class SellDetails {

        @XmlElement(required = true)
        protected List<RetailSellKkt.SellDetails.Receipt> receipt;

        /**
         * Gets the value of the receipt property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the receipt property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getReceipt().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RetailSellKkt.SellDetails.Receipt }
         * 
         * 
         */
        public List<RetailSellKkt.SellDetails.Receipt> getReceipt() {
            if (receipt == null) {
                receipt = new ArrayList<RetailSellKkt.SellDetails.Receipt>();
            }
            return this.receipt;
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
         *         &lt;element name="type" type="{}receipt_type_enum"/&gt;
         *         &lt;element name="operation_date" type="{}datetimeoffset"/&gt;
         *         &lt;element name="prescription" minOccurs="0"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="doc_date" type="{}date_type" minOccurs="0"/&gt;
         *                   &lt;element name="doc_series" type="{}document_number_200_type" minOccurs="0"/&gt;
         *                   &lt;element name="doc_num" type="{}document_number_200_type" minOccurs="0"/&gt;
         *                 &lt;/sequence&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="items"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;sequence&gt;
         *                   &lt;element name="item" maxOccurs="unbounded"&gt;
         *                     &lt;complexType&gt;
         *                       &lt;complexContent&gt;
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                           &lt;sequence&gt;
         *                             &lt;element name="sgtin" type="{}sign_sgtin_type"/&gt;
         *                             &lt;element name="cost" type="{}price_kkt_type"/&gt;
         *                             &lt;element name="vat_value" type="{}price_kkt_type"/&gt;
         *                             &lt;element name="discount" type="{}price_kkt_type" minOccurs="0"/&gt;
         *                             &lt;element name="sold_part" type="{}part_type" minOccurs="0"/&gt;
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
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "type",
            "operationDate",
            "prescription",
            "items"
        })
        public static class Receipt {

            protected int type;
            @XmlElement(name = "operation_date", required = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar operationDate;
            protected RetailSellKkt.SellDetails.Receipt.Prescription prescription;
            @XmlElement(required = true)
            protected RetailSellKkt.SellDetails.Receipt.Items items;

            /**
             * Gets the value of the type property.
             * 
             */
            public int getType() {
                return type;
            }

            /**
             * Sets the value of the type property.
             * 
             */
            public void setType(int value) {
                this.type = value;
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
             * Gets the value of the prescription property.
             * 
             * @return
             *     possible object is
             *     {@link RetailSellKkt.SellDetails.Receipt.Prescription }
             *     
             */
            public RetailSellKkt.SellDetails.Receipt.Prescription getPrescription() {
                return prescription;
            }

            /**
             * Sets the value of the prescription property.
             * 
             * @param value
             *     allowed object is
             *     {@link RetailSellKkt.SellDetails.Receipt.Prescription }
             *     
             */
            public void setPrescription(RetailSellKkt.SellDetails.Receipt.Prescription value) {
                this.prescription = value;
            }

            /**
             * Gets the value of the items property.
             * 
             * @return
             *     possible object is
             *     {@link RetailSellKkt.SellDetails.Receipt.Items }
             *     
             */
            public RetailSellKkt.SellDetails.Receipt.Items getItems() {
                return items;
            }

            /**
             * Sets the value of the items property.
             * 
             * @param value
             *     allowed object is
             *     {@link RetailSellKkt.SellDetails.Receipt.Items }
             *     
             */
            public void setItems(RetailSellKkt.SellDetails.Receipt.Items value) {
                this.items = value;
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
             *         &lt;element name="item" maxOccurs="unbounded"&gt;
             *           &lt;complexType&gt;
             *             &lt;complexContent&gt;
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
             *                 &lt;sequence&gt;
             *                   &lt;element name="sgtin" type="{}sign_sgtin_type"/&gt;
             *                   &lt;element name="cost" type="{}price_kkt_type"/&gt;
             *                   &lt;element name="vat_value" type="{}price_kkt_type"/&gt;
             *                   &lt;element name="discount" type="{}price_kkt_type" minOccurs="0"/&gt;
             *                   &lt;element name="sold_part" type="{}part_type" minOccurs="0"/&gt;
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
                "item"
            })
            public static class Items {

                @XmlElement(required = true)
                protected List<RetailSellKkt.SellDetails.Receipt.Items.Item> item;

                /**
                 * Gets the value of the item property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the item property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getItem().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link RetailSellKkt.SellDetails.Receipt.Items.Item }
                 * 
                 * 
                 */
                public List<RetailSellKkt.SellDetails.Receipt.Items.Item> getItem() {
                    if (item == null) {
                        item = new ArrayList<RetailSellKkt.SellDetails.Receipt.Items.Item>();
                    }
                    return this.item;
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
                 *         &lt;element name="sgtin" type="{}sign_sgtin_type"/&gt;
                 *         &lt;element name="cost" type="{}price_kkt_type"/&gt;
                 *         &lt;element name="vat_value" type="{}price_kkt_type"/&gt;
                 *         &lt;element name="discount" type="{}price_kkt_type" minOccurs="0"/&gt;
                 *         &lt;element name="sold_part" type="{}part_type" minOccurs="0"/&gt;
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
                    "cost",
                    "vatValue",
                    "discount",
                    "soldPart"
                })
                public static class Item {

                    @XmlElement(required = true)
                    protected String sgtin;
                    @XmlElement(required = true)
                    protected BigDecimal cost;
                    @XmlElement(name = "vat_value", required = true)
                    protected BigDecimal vatValue;
                    protected BigDecimal discount;
                    @XmlElement(name = "sold_part")
                    protected String soldPart;

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
                     * Gets the value of the discount property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getDiscount() {
                        return discount;
                    }

                    /**
                     * Sets the value of the discount property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setDiscount(BigDecimal value) {
                        this.discount = value;
                    }

                    /**
                     * Gets the value of the soldPart property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getSoldPart() {
                        return soldPart;
                    }

                    /**
                     * Sets the value of the soldPart property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setSoldPart(String value) {
                        this.soldPart = value;
                    }

                }

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
             *         &lt;element name="doc_date" type="{}date_type" minOccurs="0"/&gt;
             *         &lt;element name="doc_series" type="{}document_number_200_type" minOccurs="0"/&gt;
             *         &lt;element name="doc_num" type="{}document_number_200_type" minOccurs="0"/&gt;
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
                "docDate",
                "docSeries",
                "docNum"
            })
            public static class Prescription {

                @XmlElement(name = "doc_date")
                protected String docDate;
                @XmlElement(name = "doc_series")
                protected String docSeries;
                @XmlElement(name = "doc_num")
                protected String docNum;

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
                 * Gets the value of the docSeries property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getDocSeries() {
                    return docSeries;
                }

                /**
                 * Sets the value of the docSeries property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setDocSeries(String value) {
                    this.docSeries = value;
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

            }

        }

    }

}
