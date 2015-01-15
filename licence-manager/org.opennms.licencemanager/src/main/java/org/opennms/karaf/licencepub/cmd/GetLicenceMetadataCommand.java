package org.opennms.karaf.licencepub.cmd;

import org.apache.felix.gogo.commands.Argument;
import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceSpecification;
import org.opennms.karaf.licencepub.LicencePublisher;

@Command(scope = "licence-pub", name = "getlicencespec", description="Gets the licence metadata for installed licence specification for a given product id")
public class GetLicenceMetadataCommand extends OsgiCommandSupport {

	private LicencePublisher _licencePublisher;

	public LicencePublisher getLicencePublisher() {
		return _licencePublisher;
	}

	public void setLicencePublisher(LicencePublisher _licencePublisher) {
		this._licencePublisher = _licencePublisher;
	}


	@Argument(index = 0, name = "productId", description = "productId for which to find specification metadata", required = true, multiValued = false)
	String productId = null;

	@Override
	protected Object doExecute() throws Exception {
		try{
			LicenceSpecification licenceSpecification = getLicencePublisher().getLicenceSpec(productId);
			if (licenceSpecification==null){
				System.out.println("licence specification not installed for productId='"+productId+"'");
			} else {
				LicenceMetadata licenceMetadata = licenceSpecification.getLicenceMetadataSpec();
				String metadatastr = (licenceMetadata==null) ? "null" : licenceMetadata.toXml();
				System.out.println("  productId='"+productId+"'\n"
						+ "      licenceMetadataSpec='"+metadatastr+"'\n");
			}
		} catch (Exception e) {
			System.out.println("Error getting licence specification. Exception="+e);
		}
		return null;
	}
}