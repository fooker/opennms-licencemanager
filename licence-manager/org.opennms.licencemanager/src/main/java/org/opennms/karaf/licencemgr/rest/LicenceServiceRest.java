package org.opennms.karaf.licencemgr.rest;


import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.opennms.karaf.licencemgr.LicenceService;
import org.opennms.karaf.licencemgr.metadata.Licence;
import org.opennms.karaf.licencemgr.metadata.jaxb.ErrorMessage;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceEntry;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceList;
import org.opennms.karaf.licencemgr.metadata.jaxb.ReplyMessage;


/**
 * ReST interface to access licence manager
 * 
 * @author Craig Gallen cgallen@opennms.org
 *
 */
@Path("/licence-mgr")
public class LicenceServiceRest {

	@GET
	@Path("/addlicence")
	@Produces(MediaType.APPLICATION_XML)
	public Response addLicence(@QueryParam("licence") String licence){

		LicenceService licenceService= ServiceLoader.getLicenceService();
		if (licenceService == null) throw new RuntimeException("ServiceLoader.getLicenceService() cannot be null.");

		try{
			if (licence == null) throw new RuntimeException("Licence cannot be null.");
			licenceService.addLicence(licence);
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to add licence", null, exception)).build();
		}

		ReplyMessage reply= new ReplyMessage();
        String productId = Licence.getUnverifiedMetadata(licence).getProductId();
        reply.setReplyComment("Successfully added licence instance for productId="+productId);
        reply.setProductId(productId);
		
		return Response
				.status(200).entity(reply).build();

	}

	@GET
	@Path("/removelicence")
	@Produces(MediaType.APPLICATION_XML)
	public Response removeLicence(@QueryParam("productId") String productId){

		LicenceService licenceService= ServiceLoader.getLicenceService();
		if (licenceService == null)	throw new RuntimeException("ServiceLoader.getLicenceService() cannot be null.");
		
		Boolean removed=null;
		try{
			if (productId == null) throw new RuntimeException("productId cannot be null.");
			removed = licenceService.removeLicence(productId);
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to remove licence", null, exception)).build();
		}

		ReplyMessage reply= new ReplyMessage();
		if (removed) {
			reply.setReplyComment("Licence successfully removed for productId="+productId);
		} else {
			reply.setReplyComment("Licence not found to remove for productId="+productId);
		}
		
		return Response
				.status(200).entity(reply).build();

	}
	
	@GET
	@Path("/getlicence")
	@Produces(MediaType.APPLICATION_XML)
	public Response getLicence(@QueryParam("productId") String productId){

		LicenceService licenceService= ServiceLoader.getLicenceService();
		if (licenceService == null)	throw new RuntimeException("ServiceLoader.getLicenceService() cannot be null.");
		
		String licence=null;
		try{
			if (productId == null) throw new RuntimeException("productId cannot be null.");
			licence = licenceService.getLicence(productId);
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "cannot get licence", null, exception)).build();
		}

		ReplyMessage reply= new ReplyMessage();
		if (licence==null) {
			reply.setReplyComment("Licence not found for productId for productId="+productId);
			reply.setLicence(null);
		} else {
			reply.setReplyComment("Licence Specification found for productId="+productId);
			reply.setLicence(licence);
		}
		
		return Response
				.status(200).entity(reply).build();

	}

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_XML)
	public Response getLicenceMap(){

		LicenceService licenceService= ServiceLoader.getLicenceService();
		if (licenceService == null)	throw new RuntimeException("ServiceLoader.getLicenceService() cannot be null.");

		Map<String, String> licenceMap=null;
		try{
			licenceMap = licenceService.getLicenceMap();
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "cannot get licence map", null, exception)).build();
		}

		LicenceList licenceListResponse= new LicenceList();
		
		for (Entry<String, String> entry:licenceMap.entrySet()){
			LicenceEntry licenceEntry = new LicenceEntry();
			licenceEntry.setProductId(entry.getKey());
			licenceEntry.setLicenceStr(entry.getValue());
			licenceListResponse.getLicenceList().add(licenceEntry);
		}
		
		return Response
				.status(200).entity(licenceListResponse).build();

	}


	@GET
	@Path("/clearlicences")
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteLicences(@QueryParam("confirm") String confirm){

		LicenceService licenceService= ServiceLoader.getLicenceService();
		if (licenceService == null)	throw new RuntimeException("ServiceLoader.getLicenceService() cannot be null.");

		try{
			if (!"true".equals(confirm)) throw new IllegalArgumentException("Will only delete licences if paramater confirm=true");
			licenceService.deleteLicences();
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "cannot delete licences", null, exception)).build();
		}

		ReplyMessage reply= new ReplyMessage();
        reply.setReplyComment("All Licences removed");
		
		return Response
				.status(200).entity(reply).build();

	}



	@GET
	@Path("getsystemid")
	@Produces(MediaType.APPLICATION_XML)
	public Response getSystemId(){

		LicenceService licenceService= ServiceLoader.getLicenceService();
		if (licenceService == null)	throw new RuntimeException("ServiceLoader.getLicenceService() cannot be null.");

		String systemId=null;
		try{
			systemId = licenceService.getSystemId();
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "cannot get systemId", null, exception)).build();
		}

		ReplyMessage reply= new ReplyMessage();
        reply.setReplyComment("Returned systemId for this system");
        reply.setSystemId(systemId);
		
		return Response
				.status(200).entity(reply).build();
	}


	@GET
	@Path("setsystemid")
	@Produces(MediaType.APPLICATION_XML)
	public Response setSystemId(@QueryParam("systemId") String systemId){

		LicenceService licenceService= ServiceLoader.getLicenceService();
		if (licenceService == null)	throw new RuntimeException("ServiceLoader.getLicenceService() cannot be null.");


		try{
			if (systemId == null) throw new RuntimeException("systemId cannot be null.");
			licenceService.setSystemId(systemId);
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "cannot set systemId", null, exception)).build();
		}

		ReplyMessage reply= new ReplyMessage();
        reply.setReplyComment("Set systemId for this system");
        reply.setSystemId(systemId);
		
		return Response
				.status(200).entity(reply).build();

	}

	@GET
	@Path("/makesystemid")
	@Produces(MediaType.APPLICATION_XML)
	public Response makeSystemInstance(){

		LicenceService licenceService= ServiceLoader.getLicenceService();
		if (licenceService == null) throw new RuntimeException("ServiceLoader.getLicenceService() cannot be null.");

		String systemId=null;
		try{
			systemId= licenceService.makeSystemInstance();
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "cannot make system instance", null, exception)).build();
		}

		ReplyMessage reply= new ReplyMessage();
        reply.setReplyComment("New systemId created and installed for this system");
        reply.setSystemId(systemId);
		
		return Response
				.status(200).entity(reply).build();

	}

	@GET
	@Path("/checksumforstring")
	@Produces(MediaType.APPLICATION_XML)
	public Response checksumForString(@QueryParam("string") String string){
		
		LicenceService licenceService= ServiceLoader.getLicenceService();
		if (licenceService == null) throw new RuntimeException("ServiceLoader.getLicenceService() cannot be null.");

		String checksum=null;
		try{
			if (string == null) throw new RuntimeException("string cannot be null.");
			checksum= licenceService.checksumForString(string);
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "cannot generate checksum for string", null, exception)).build();
		}

		ReplyMessage reply= new ReplyMessage();
        reply.setReplyComment("New checksum added to string");
        reply.setChecksum(checksum);
		
		return Response
				.status(200).entity(reply).build();

	}

}
