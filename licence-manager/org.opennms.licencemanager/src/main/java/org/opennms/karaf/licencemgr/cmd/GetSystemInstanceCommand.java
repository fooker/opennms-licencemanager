package org.opennms.karaf.licencemgr.cmd;

import org.apache.felix.gogo.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opennms.karaf.licencemgr.LicenceService;


@Command(scope = "licence-mgr", name = "getsystemid", description="Get System Instance ID installed for licence manager")
public class GetSystemInstanceCommand extends OsgiCommandSupport {

	private LicenceService _licenceService;

	public LicenceService getLicenceService() {
		return _licenceService;
	}

	public void setLicenceService( LicenceService licenceService) {
		_licenceService = licenceService;
	} 

	@Override
	protected Object doExecute() throws Exception {
		try{
			System.out.println("Licence System Instance ID='"+getLicenceService().getSystemId()+"'");
		} catch (Exception e) {
			System.out.println("Error getting System Instance ID. Exception="+e);
		}

		return null;
	}
}