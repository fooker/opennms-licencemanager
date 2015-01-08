package org.opennms.karaf.licencemgr.metadata;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="product")
@XmlAccessorType(XmlAccessType.NONE)
public class ProductMetadata {

	//productId is expected to contain the concatenated <groupId>/<artifactId>/<version>
	//such that mvn:<groupId>/<artifactId>/<version> will resolve an object
	String productId=null;
	
	//product name - plain text name of product- usually maven <name> field
	String productName=null;
	
	// description of product usually maven <description>
	String productDescription=null;
	
	// URL for more product details usually matches maven <url> field
	String productUrl=null;
	
	// Organization which created the product. Usually matchers the maven <organization> field
	String organization=null;

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	@XmlElement(name="productId")
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	@XmlElement(name="productName")
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the productMetadata
	 */
	public String getProductDescription() {
		return productDescription;
	}

	/**
	 * @param productMetadata the productMetadata to set
	 */
	@XmlElement(name="productDescription")
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	/**
	 * @return the productUrl
	 */
	public String getProductUrl() {
		return productUrl;
	}

	/**
	 * @param productUrl the productUrl to set
	 */
	@XmlElement(name="productUrl")
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	@XmlElement(name="organization")
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	/**
	 * @return XML encoded version of ProductMetadata
	 */
	public String toXml(){

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(org.opennms.karaf.licencemgr.metadata.ObjectFactory.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter stringWriter = new StringWriter();
			jaxbMarshaller.marshal(this,stringWriter);
			return stringWriter.toString();
		} catch (JAXBException e) {
			throw new RuntimeException("Problem marshalling LicenceMetadata:",e);
		}
	}

	/**
	 * load this object with data from xml string
	 * @parm XML encoded version of LicenceMetadata
	 */
	public void fromXml(String xmlStr){

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(org.opennms.karaf.licencemgr.metadata.ObjectFactory.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xmlStr);
			ProductMetadata productMetadata= (ProductMetadata) jaxbUnmarshaller.unmarshal(reader);
			this.productId=productMetadata.productId;
			this.productDescription=productMetadata.productDescription;
			this.organization=productMetadata.organization;
			this.productName=productMetadata.productName;
			this.productUrl=productMetadata.productUrl;
		} catch (JAXBException e) {
			throw new RuntimeException("Problem unmarshalling LicenceMetadata:",e);
		}
	}

	/**
	 * @return Hex encoded string of XML version of ProductMetadata
	 * @throws UnsupportedEncodingException
	 */
	public String toHexString(){
		try {
			String xmlStr = toXml();
			byte[] array = xmlStr.getBytes("UTF-8");
			return DatatypeConverter.printHexBinary(array);
		} catch ( Exception e) {
			throw new RuntimeException("problem converting ProductMetadata to hexString:",e);
		}
	}

	/**
	 * imports licence metadata from hex encoded version of XML metadata
	 * @param hexString
	 * 
	 */
	public void fromHexString(String hexString){
		try {
			byte[] array = DatatypeConverter.parseHexBinary(hexString);
			String xmlStr = new String(array, "UTF-8");
			this.fromXml(xmlStr);
		} catch ( Exception e) {
			throw new RuntimeException("problem importing ProductMetadata from hexString:",e);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((organization == null) ? 0 : organization.hashCode());
		result = prime
				* result
				+ ((productDescription == null) ? 0 : productDescription
						.hashCode());
		result = prime * result
				+ ((productId == null) ? 0 : productId.hashCode());
		result = prime * result
				+ ((productName == null) ? 0 : productName.hashCode());
		result = prime * result
				+ ((productUrl == null) ? 0 : productUrl.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductMetadata other = (ProductMetadata) obj;
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization))
			return false;
		if (productDescription == null) {
			if (other.productDescription != null)
				return false;
		} else if (!productDescription.equals(other.productDescription))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		if (productUrl == null) {
			if (other.productUrl != null)
				return false;
		} else if (!productUrl.equals(other.productUrl))
			return false;
		return true;
	}


}
