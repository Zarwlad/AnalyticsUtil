//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.09 at 06:23:10 PM MSK 
//


package java.entities.mdlpmessage;

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
 * Регистрация в ИС МДЛП сведений о вводе лекарственных препаратов в оборот на территории Российской Федерации
 * 
 * <p>Java class for eeu_release complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eeu_release"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="subject_id" type="{}subject_id_type"/&gt;
 *         &lt;element name="operation_date" type="{}datetimeoffset"/&gt;
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
 *                                                   &lt;element name="release_info" type="{}release_info_type"/&gt;
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
 *                             &lt;element name="release_info" type="{}release_info_type"/&gt;
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
 *       &lt;attribute name="action_id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" fixed="363" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eeu_release", propOrder = {
    "subjectId",
    "operationDate",
    "orderDetails"
})
public class EeuRelease {

    @XmlElement(name = "subject_id", required = true)
    protected String subjectId;
    @XmlElement(name = "operation_date", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar operationDate;
    @XmlElement(name = "order_details", required = true)
    protected EeuRelease.OrderDetails orderDetails;
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
     * Gets the value of the orderDetails property.
     * 
     * @return
     *     possible object is
     *     {@link EeuRelease.OrderDetails }
     *     
     */
    public EeuRelease.OrderDetails getOrderDetails() {
        return orderDetails;
    }

    /**
     * Sets the value of the orderDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link EeuRelease.OrderDetails }
     *     
     */
    public void setOrderDetails(EeuRelease.OrderDetails value) {
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
     *                                         &lt;element name="release_info" type="{}release_info_type"/&gt;
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
     *                   &lt;element name="release_info" type="{}release_info_type"/&gt;
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
        protected List<EeuRelease.OrderDetails.Union> union;

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
         * {@link EeuRelease.OrderDetails.Union }
         * 
         * 
         */
        public List<EeuRelease.OrderDetails.Union> getUnion() {
            if (union == null) {
                union = new ArrayList<EeuRelease.OrderDetails.Union>();
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
         *                               &lt;element name="release_info" type="{}release_info_type"/&gt;
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
         *         &lt;element name="release_info" type="{}release_info_type"/&gt;
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
            "releaseInfo"
        })
        public static class Union {

            protected String sgtin;
            @XmlElement(name = "sscc_detail")
            protected EeuRelease.OrderDetails.Union.SsccDetail ssccDetail;
            @XmlElement(name = "release_info", required = true)
            protected ReleaseInfoType releaseInfo;

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
             *     {@link EeuRelease.OrderDetails.Union.SsccDetail }
             *     
             */
            public EeuRelease.OrderDetails.Union.SsccDetail getSsccDetail() {
                return ssccDetail;
            }

            /**
             * Sets the value of the ssccDetail property.
             * 
             * @param value
             *     allowed object is
             *     {@link EeuRelease.OrderDetails.Union.SsccDetail }
             *     
             */
            public void setSsccDetail(EeuRelease.OrderDetails.Union.SsccDetail value) {
                this.ssccDetail = value;
            }

            /**
             * Gets the value of the releaseInfo property.
             * 
             * @return
             *     possible object is
             *     {@link ReleaseInfoType }
             *     
             */
            public ReleaseInfoType getReleaseInfo() {
                return releaseInfo;
            }

            /**
             * Sets the value of the releaseInfo property.
             * 
             * @param value
             *     allowed object is
             *     {@link ReleaseInfoType }
             *     
             */
            public void setReleaseInfo(ReleaseInfoType value) {
                this.releaseInfo = value;
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
             *                   &lt;element name="release_info" type="{}release_info_type"/&gt;
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
                protected List<EeuRelease.OrderDetails.Union.SsccDetail.Detail> detail;

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
                 * {@link EeuRelease.OrderDetails.Union.SsccDetail.Detail }
                 * 
                 * 
                 */
                public List<EeuRelease.OrderDetails.Union.SsccDetail.Detail> getDetail() {
                    if (detail == null) {
                        detail = new ArrayList<EeuRelease.OrderDetails.Union.SsccDetail.Detail>();
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
                 *         &lt;element name="release_info" type="{}release_info_type"/&gt;
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
                    "releaseInfo"
                })
                public static class Detail {

                    @XmlElement(required = true)
                    protected String gtin;
                    @XmlElement(name = "series_number", required = true)
                    protected String seriesNumber;
                    @XmlElement(name = "release_info", required = true)
                    protected ReleaseInfoType releaseInfo;

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
                     * Gets the value of the releaseInfo property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link ReleaseInfoType }
                     *     
                     */
                    public ReleaseInfoType getReleaseInfo() {
                        return releaseInfo;
                    }

                    /**
                     * Sets the value of the releaseInfo property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link ReleaseInfoType }
                     *     
                     */
                    public void setReleaseInfo(ReleaseInfoType value) {
                        this.releaseInfo = value;
                    }

                }

            }

        }

    }

}
