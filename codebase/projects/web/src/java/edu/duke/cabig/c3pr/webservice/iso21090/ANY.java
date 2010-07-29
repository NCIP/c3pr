
package edu.duke.cabig.c3pr.webservice.iso21090;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import edu.duke.cabig.c3pr.webservice.subjectmanagement.COLLSUBJECT;


/**
 * <p>Java class for ANY complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ANY">
 *   &lt;complexContent>
 *     &lt;extension base="{uri:iso.org:21090}HXIT">
 *       &lt;attribute name="nullFlavor" type="{uri:iso.org:21090}NullFlavor" />
 *       &lt;attribute name="flavorId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="updateMode" type="{uri:iso.org:21090}UpdateMode" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ANY")
@XmlSeeAlso({
    COLLSUBJECT.class,
    SLISTTSDate.class,
    NPPDTSDateFull.class,
    SLISTTSBirth.class,
    SLISTTSDateTime.class,
    NPPDMO.class,
    NPPDCDCE.class,
    NPPDCDCV.class,
    UVPSC.class,
    NPPDINT.class,
    NPPDREAL.class,
    SLISTREAL.class,
    UVPEDSignature.class,
    NPPDTSDate.class,
    UVPST.class,
    SLISTMO.class,
    UVPTELPerson.class,
    UVPTS.class,
    UVPBLNonNull.class,
    NPPDTSBirth.class,
    UVPEDDoc.class,
    UVPTELPhone.class,
    NPPDSC.class,
    NPPDTSDateTime.class,
    UVPMO.class,
    AD.class,
    NPPDSTNT.class,
    NPPDPQ.class,
    NPPDEDDocInline.class,
    BL.class,
    GLISTTSDateFull.class,
    NPPDTELPerson.class,
    NPPDTELUrl.class,
    CS.class,
    EN.class,
    SLISTPQ.class,
    LISTSCNT.class,
    UVPPQ.class,
    LISTBLNonNull.class,
    LISTEDDocInline.class,
    UVPTELUrl.class,
    UVPINT.class,
    GLISTRTO.class,
    NPPDEN.class,
    UVPSTNT.class,
    NPPDED.class,
    COLLEDText.class,
    UVPREAL.class,
    NPPDPQTime.class,
    NPPDEDDocRef.class,
    UVPTSBirth.class,
    GLISTCO.class,
    NPPDCO.class,
    NPPDCS.class,
    NPPDRTO.class,
    GLISTTSDateTimeFull.class,
    NPPDCD.class,
    NPPDTELEmail.class,
    NPPDEDImage.class,
    II.class,
    COLLTSDateTime.class,
    LISTEDDocRef.class,
    NPPDII.class,
    LISTTELPhone.class,
    UVPTELEmail.class,
    LISTTELUrl.class,
    GLISTINT.class,
    UVPPQTime.class,
    SLISTTSDateFull.class,
    UVPRTO.class,
    SLISTPQTime.class,
    NPPDCDCENone.class,
    GLISTINTPositive.class,
    UVPEDDocInline.class,
    COLLST.class,
    NPPDTEL.class,
    NPPDENON.class,
    UVPBL.class,
    UVPTSDate.class,
    NPPDINTPositive.class,
    COLLTS.class,
    UVPEDText.class,
    NPPDSCNT.class,
    UVPAD.class,
    COLLEDNarrative.class,
    LISTCDCENone.class,
    UVPCS.class,
    NPPDEDNarrative.class,
    COLLINTPositive.class,
    GLISTPQTime.class,
    UVPENTN.class,
    GLISTTSBirth.class,
    UVPTSDateTime.class,
    NPPDTELPhone.class,
    GLISTMO.class,
    COLLSC.class,
    UVPCD.class,
    COLLRTO.class,
    COLLEDImage.class,
    UVPCO.class,
    GLISTTSDate.class,
    LISTTELEmail.class,
    NPPDTS.class,
    NPPDAD.class,
    LISTMO.class,
    SLISTINT.class,
    NPPDINTNonNeg.class,
    UVPCDCE.class,
    GLISTTSDateTime.class,
    NPPDST.class,
    COLLPQ.class,
    COLLINTNonNeg.class,
    QSETREAL.class,
    NPPDEDSignature.class,
    UVPCDCV.class,
    NPPDBL.class,
    UVPTSDateFull.class,
    COLLTELPhone.class,
    COLLMO.class,
    UVPSCNT.class,
    ST.class,
    NPPDTSDateTimeFull.class,
    LISTII.class,
    UVPINTPositive.class,
    SLISTINTPositive.class,
    COLLPQTime.class,
    GLISTREAL.class,
    SLISTCO.class,
    LISTEDNarrative.class,
    COLLTELEmail.class,
    LISTSC.class,
    QSETPQ.class,
    COLLENTN.class,
    QSETTS.class,
    UVPII.class,
    COLLTSDateFull.class,
    UVPTEL.class,
    LISTTSDateTimeFull.class,
    COLLII.class,
    LISTAD.class,
    LISTENPN.class,
    NPPDEDDoc.class,
    LISTTEL.class,
    LISTRTO.class,
    COLLEDDoc.class,
    LISTST.class,
    LISTEDImage.class,
    LISTREAL.class,
    QSETPQTime.class,
    LISTINT.class,
    LISTTSDateFull.class,
    COLLAD.class,
    COLLTSDate.class,
    SLISTINTNonNeg.class,
    SLISTTS.class,
    LISTPQTime.class,
    COLLCDCENone.class,
    LISTSTNT.class,
    LISTENTN.class,
    SLISTRTO.class,
    GLISTTS.class,
    UVPTSDateTimeFull.class,
    LISTTS.class,
    UVPEDImage.class,
    LISTTELPerson.class,
    UVPINTNonNeg.class,
    CD.class,
    UVPEDDocRef.class,
    NPPDENTN.class,
    NPPDBLNonNull.class,
    QSETMO.class,
    COLLTSBirth.class,
    UVPCDCENone.class,
    QSETINT.class,
    ED.class,
    COLLSCNT.class,
    COLLENPN.class,
    COLLTEL.class,
    GLISTINTNonNeg.class,
    QSETTSDate.class,
    LISTEDSignature.class,
    LISTED.class,
    LISTINTPositive.class,
    UVPEN.class,
    LISTEN.class,
    COLLINT.class,
    COLLEDSignature.class,
    LISTEDText.class,
    COLLEDDocInline.class,
    SLISTTSDateTimeFull.class,
    UVPED.class,
    NPPDEDText.class,
    LISTCDCV.class,
    COLLREAL.class,
    LISTTSBirth.class,
    COLLED.class,
    LISTENON.class,
    LISTBL.class,
    QSETRTO.class,
    QTY.class,
    COLLSTNT.class,
    LISTTSDateTime.class,
    QSETTSDateTime.class,
    COLLEN.class,
    LISTCDCE.class,
    UVPENPN.class,
    COLLTELUrl.class,
    COLLCO.class,
    LISTINTNonNeg.class,
    QSETTSBirth.class,
    COLLBLNonNull.class,
    COLLEDDocRef.class,
    GLISTPQ.class,
    LISTPQ.class,
    COLLCS.class,
    UVPEDNarrative.class,
    LISTCD.class,
    LISTEDDoc.class,
    COLLCDCE.class,
    LISTCO.class,
    COLLCD.class,
    LISTCS.class,
    COLLTELPerson.class,
    COLLENON.class,
    LISTTSDate.class,
    URL.class,
    QSETINTPositive.class,
    COLLBL.class,
    NPPDENPN.class,
    UVPENON.class,
    COLLTSDateTimeFull.class,
    COLLCDCV.class,
    QSETTSDateTimeFull.class,
    QSETCO.class,
    QSETTSDateFull.class,
    QSETINTNonNeg.class
})
public abstract class ANY
    extends HXIT
{

    @XmlAttribute
    protected NullFlavor nullFlavor;
    @XmlAttribute
    protected String flavorId;
    @XmlAttribute
    protected UpdateMode updateMode;

    public ANY() {
		// TODO Auto-generated constructor stub
	}
    
    
    public ANY(NullFlavor nullFlavor) {
		super();
		this.nullFlavor = nullFlavor;
	}


	/**
     * Gets the value of the nullFlavor property.
     * 
     * @return
     *     possible object is
     *     {@link NullFlavor }
     *     
     */
    public NullFlavor getNullFlavor() {
        return nullFlavor;
    }

    /**
     * Sets the value of the nullFlavor property.
     * 
     * @param value
     *     allowed object is
     *     {@link NullFlavor }
     *     
     */
    public void setNullFlavor(NullFlavor value) {
        this.nullFlavor = value;
    }

    /**
     * Gets the value of the flavorId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlavorId() {
        return flavorId;
    }

    /**
     * Sets the value of the flavorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlavorId(String value) {
        this.flavorId = value;
    }

    /**
     * Gets the value of the updateMode property.
     * 
     * @return
     *     possible object is
     *     {@link UpdateMode }
     *     
     */
    public UpdateMode getUpdateMode() {
        return updateMode;
    }

    /**
     * Sets the value of the updateMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpdateMode }
     *     
     */
    public void setUpdateMode(UpdateMode value) {
        this.updateMode = value;
    }

}
