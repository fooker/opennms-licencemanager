package org.opennms.karaf.licencepub;

import java.util.Map;

import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceSpecification;

public interface LicencePublisher {

	/**
	 * Adds a new licence specification to the licence publisher. 
	 * Looks for the productId in the licence specification and adds an entry
	 * in the licence table under that productId. Replaces any previous licence entry
	 * @param licenceSpec LicenceSpecification to be added to licence publisher
	 */
	public void addLicenceSpec(LicenceSpecification licenceSpec);
	
	/**
	 * removes the entry for productId from the licenceSpecMap
	 * @param productId
	 */
	public boolean removeLicenceSpec(String productId );

	/**
	 * @param productId
	 * @return  the LicenceSpecification stored for productId 
	 * returns null if no LicenceSpecification found for productId 
	 */
	public LicenceSpecification getLicenceSpec(String productId);

	/**
	 * @return a copy of the map of the LicenceSpecifications ordered by productId
	 */
	public Map<String, LicenceSpecification> getLicenceSpecMap();
	
	/**
	 * deletes all values of the licenceSpecMap
	 */
	public void deleteLicenceSpecifications();
	
	/**
	 * Creates an encoded String instance of a licence from the LicenceSecification 
	 * corresponding to the productId in the supplied createLicenceMetadata
	 * throws IllegalArgumentException if the correspondingLicenceSecification cannot be found
	 * or the names of options or licencee are different from the specification
	 * @Param createLicenceMetadata this should be created from a copy of the LicenceMetadata in the LicenceSpecfication
	 * i.e. it must contain the productId and the options must correspond to the
	 * options in the LicenceSpecification
	 */
	public String createLicenceInstanceStr(LicenceMetadata licenceMetadata);
	
	/**
	 * Creates an encoded String instance of a licence from the supplied licenceMetadata in xml form
	 * using same criteria as in createLicenceInstance(licenceMetadata)
	 * 
	 */
	public String createLicenceInstanceStr(String licenceMetadataXml);

	
}
