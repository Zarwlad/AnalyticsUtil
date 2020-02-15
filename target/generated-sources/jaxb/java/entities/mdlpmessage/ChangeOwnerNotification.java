//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.09 at 06:23:10 PM MSK 
//


package java.entities.mdlpmessage;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Уведомление нового собственника о смене собственника лекарственных препаратов
 * 
 * <p>Java class for change_owner_notification complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="change_owner_notification"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="subject_id" type="{}system_subject_type"/&gt;
 *         &lt;element name="receiver_id" type="{}system_subject_type"/&gt;
 *         &lt;element name="operation_date" type="{}datetimeoffset"/&gt;
 *         &lt;element name="doc_num" type="{}document_number_200_type"/&gt;
 *         &lt;element name="doc_date" type="{}date_type"/&gt;
 *         &lt;element name="order_details"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;choice maxOccurs="25000"&gt;
 *                   &lt;element name="sgtin" type="{}sign_sgtin_type"/&gt;
 *                   &lt;element name="sscc" type="{}sscc_type"/&gt;
 *                 &lt;/choice&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="action_id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" fixed="609" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "change_owner_notification", propOrder = {
    "subjectId",
    "receiverId",
    "operationDate",
    "docNum",
    "docDate",
    "orderDetails"
})
public class ChangeOwnerNotification {

    @XmlElement(name = "subject_id", required = true)
    protected String subjectId;
    @XmlElement(name = "receiver_id", required = true)
    protected String receiverId;
    @XmlElement(name = "operation_date", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar operationDate;
    @XmlElement(name = "doc_num", required = true)
    protected String docNum;
    @XmlElement(name = "doc_date", required = true)
    protected String docDate;
    @XmlElement(name = "order_details", required = true)
    protected ChangeOwnerNotification.OrderDetails orderDetails;
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
     * Gets the value of the receiverId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiverId() {
        return receiverId;
    }

    /**
     * Sets the value of the receiverId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiverId(String value) {
        this.receiverId = value;
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
     * Gets the value of the orderDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ChangeOwnerNotification.OrderDetails }
     *     
     */
    public ChangeOwnerNotification.OrderDetails getOrderDetails() {
        return orderDetails;
    }

    /**
     * Sets the value of the orderDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangeOwnerNotification.OrderDetails }
     *     
     */
    public void setOrderDetails(ChangeOwnerNotification.OrderDetails value) {
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
     *       &lt;choice maxOccurs="25000"&gt;
     *         &lt;element name="sgtin" type="{}sign_sgtin_type"/&gt;
     *         &lt;element name="sscc" type="{}sscc_type"/&gt;
     *       &lt;/choice&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "sgtinOrSscc"
    })
    public static class OrderDetails {

        @XmlElementRefs({
            @XmlElementRef(name = "sgtin", type = JAXBElement.class, required = false),
            @XmlElementRef(name = "sscc", type = JAXBElement.class, required = false)
        })
        protected List<JAXBElement<String>> sgtinOrSscc;

        /**
         * Gets the value of the sgtinOrSscc property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the sgtinOrSscc property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSgtinOrSscc().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link JAXBElement }{@code <}{@link String }{@code >}
         * {@link JAXBElement }{@code <}{@link String }{@code >}
         * 
         * 
         */
        public List<JAXBElement<String>> getSgtinOrSscc() {
            if (sgtinOrSscc == null) {
                sgtinOrSscc = new ArrayList<JAXBElement<String>>();
            }
            return this.sgtinOrSscc;
        }

    }

}
