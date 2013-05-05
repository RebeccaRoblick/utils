
package net.sf.ahtutils.xml.mail;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://ahtutils.aht-group.com/mail}from"/>
 *         &lt;element ref="{http://ahtutils.aht-group.com/mail}to"/>
 *         &lt;element ref="{http://ahtutils.aht-group.com/mail}cc"/>
 *         &lt;element ref="{http://ahtutils.aht-group.com/mail}bcc"/>
 *         &lt;element name="subject">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "from",
    "to",
    "cc",
    "bcc",
    "subject"
})
@XmlRootElement(name = "header")
public class Header
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected From from;
    @XmlElement(required = true)
    protected To to;
    @XmlElement(required = true)
    protected Cc cc;
    @XmlElement(required = true)
    protected Bcc bcc;
    @XmlElement(namespace = "", required = true)
    protected String subject;

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link From }
     *     
     */
    public From getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link From }
     *     
     */
    public void setFrom(From value) {
        this.from = value;
    }

    public boolean isSetFrom() {
        return (this.from!= null);
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link To }
     *     
     */
    public To getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link To }
     *     
     */
    public void setTo(To value) {
        this.to = value;
    }

    public boolean isSetTo() {
        return (this.to!= null);
    }

    /**
     * Gets the value of the cc property.
     * 
     * @return
     *     possible object is
     *     {@link Cc }
     *     
     */
    public Cc getCc() {
        return cc;
    }

    /**
     * Sets the value of the cc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cc }
     *     
     */
    public void setCc(Cc value) {
        this.cc = value;
    }

    public boolean isSetCc() {
        return (this.cc!= null);
    }

    /**
     * Gets the value of the bcc property.
     * 
     * @return
     *     possible object is
     *     {@link Bcc }
     *     
     */
    public Bcc getBcc() {
        return bcc;
    }

    /**
     * Sets the value of the bcc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Bcc }
     *     
     */
    public void setBcc(Bcc value) {
        this.bcc = value;
    }

    public boolean isSetBcc() {
        return (this.bcc!= null);
    }

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubject(String value) {
        this.subject = value;
    }

    public boolean isSetSubject() {
        return (this.subject!= null);
    }

}
