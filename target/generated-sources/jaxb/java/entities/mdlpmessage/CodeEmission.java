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
 * Регистрация в ИС МДЛП сведений об эмиссии (информация от СУЗ)
 * 
 * <p>Java class for code_emission complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="code_emission"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="subject_id" type="{}system_subject_type"/&gt;
 *         &lt;element name="operation_date" type="{}datetimeoffset"/&gt;
 *         &lt;element name="oms_id" type="{}guid_type" minOccurs="0"/&gt;
 *         &lt;element name="oms_order_id" type="{}guid_type"/&gt;
 *         &lt;element name="gtin" type="{}gs1_gtin_type"/&gt;
 *         &lt;element name="signs"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="sgtin" type="{}sign_sgtin_type" maxOccurs="150000"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="action_id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" fixed="10300" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "code_emission", propOrder = {
    "subjectId",
    "operationDate",
    "omsId",
    "omsOrderId",
    "gtin",
    "signs"
})
public class CodeEmission {

    @XmlElement(name = "subject_id", required = true)
    protected String subjectId;
    @XmlElement(name = "operation_date", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar operationDate;
    @XmlElement(name = "oms_id")
    protected String omsId;
    @XmlElement(name = "oms_order_id", required = true)
    protected String omsOrderId;
    @XmlElement(required = true)
    protected String gtin;
    @XmlElement(required = true)
    protected CodeEmission.Signs signs;
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
     * Gets the value of the omsId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmsId() {
        return omsId;
    }

    /**
     * Sets the value of the omsId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmsId(String value) {
        this.omsId = value;
    }

    /**
     * Gets the value of the omsOrderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOmsOrderId() {
        return omsOrderId;
    }

    /**
     * Sets the value of the omsOrderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOmsOrderId(String value) {
        this.omsOrderId = value;
    }

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
     * Gets the value of the signs property.
     * 
     * @return
     *     possible object is
     *     {@link CodeEmission.Signs }
     *     
     */
    public CodeEmission.Signs getSigns() {
        return signs;
    }

    /**
     * Sets the value of the signs property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeEmission.Signs }
     *     
     */
    public void setSigns(CodeEmission.Signs value) {
        this.signs = value;
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
     *         &lt;element name="sgtin" type="{}sign_sgtin_type" maxOccurs="150000"/&gt;
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
        "sgtin"
    })
    public static class Signs {

        @XmlElement(required = true)
        protected List<String> sgtin;

        /**
         * Gets the value of the sgtin property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the sgtin property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSgtin().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getSgtin() {
            if (sgtin == null) {
                sgtin = new ArrayList<String>();
            }
            return this.sgtin;
        }

    }

}
