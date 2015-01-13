package org.opennms.karaf.licencemgr.rest;

import org.opennms.karaf.licencemgr.LicenceService;
import org.opennms.karaf.licencepub.LicencePublisher;
import org.opennms.karaf.productpub.ProductPublisher;
import org.opennms.karaf.productpub.ProductRegister;

/** used to statically pass service references to Jersey ReST classes
 * 
 * @author cgallen
 *
 */
public class ServiceLoader {

	private static LicenceService licenceService= null;

	private static LicencePublisher licencePublisher= null;

	private static ProductPublisher productPublisher= null;

	private static ProductRegister productRegister= null;


	public ServiceLoader(){
		super();
	}

	public ServiceLoader(LicenceService licenceService,
			LicencePublisher licencePublisher, 
			ProductPublisher productPublisher, 
			ProductRegister productRegister){
		super();

		setLicenceService(licenceService);
		setLicencePublisher(licencePublisher);
		setProductPublisher(productPublisher);
		setProductRegister(productRegister);
	}

	/**
	 * @return the licenceService
	 */
	public static synchronized LicenceService getLicenceService() {
		return licenceService;
	}

	/**
	 * @param licenceService the licenceService to set
	 */
	public static synchronized void setLicenceService(LicenceService licenceService) {
		ServiceLoader.licenceService = licenceService;
	}

	/**
	 * @return the licencePublisher
	 */
	public static synchronized LicencePublisher getLicencePublisher() {
		return licencePublisher;
	}

	/**
	 * @param licencePublisher the licencePublisher to set
	 */
	public static synchronized void setLicencePublisher(LicencePublisher licencePublisher) {
		ServiceLoader.licencePublisher = licencePublisher;
	}

	/**
	 * @return the productPublisher
	 */
	public static synchronized ProductPublisher getProductPublisher() {
		return productPublisher;
	}

	/**
	 * @param productPublisher the productPublisher to set
	 */
	public static synchronized void setProductPublisher(ProductPublisher productPublisher) {
		ServiceLoader.productPublisher = productPublisher;
	}

	/**
	 * @return the productRegister
	 */
	public static synchronized ProductRegister getProductRegister() {
		return productRegister;
	}

	/**
	 * @param productRegister the productRegister to set
	 */
	public static synchronized void setProductRegister(ProductRegister productRegister) {
		ServiceLoader.productRegister = productRegister;
	}

}
