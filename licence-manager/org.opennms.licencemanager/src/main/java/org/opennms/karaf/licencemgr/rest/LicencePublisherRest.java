package org.opennms.karaf.licencemgr.rest;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.opennms.karaf.licencemgr.metadata.jaxb.ErrorMessage;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceMetadata;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceSpecMap;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceSpecification;
import org.opennms.karaf.licencemgr.metadata.jaxb.ReplyMessage;
import org.opennms.karaf.licencepub.LicencePublisher;

@Path("/licence-pub")
public class LicencePublisherRest {
	
	@POST
	@Path("/addlicencespec")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response addLicenceSpec(LicenceSpecification licenceSpec){

		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		try{
			if (licenceSpec == null) throw new RuntimeException("licenceSpec cannot be null.");
			licencePublisher.addLicenceSpec(licenceSpec);
		} catch (Exception e){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to add licence specification", null, e.getMessage())).build();
		}

		ReplyMessage reply= new ReplyMessage();
		reply.setReplyComment("Licence Specification successfully added");
		
		return Response
				.status(200).entity(reply).build();

	}
	
	
	@GET
	@Path("/removelicencespec")
	@Produces(MediaType.APPLICATION_XML)
	public Response removeLicenceSpec(@QueryParam("productId") String productId){

		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		Boolean removed = null;
		try{
			if (productId == null) throw new RuntimeException("productId cannot be null.");
			removed = licencePublisher.removeLicenceSpec(productId);
		} catch (Exception e){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to remove licence specification", null, e.getMessage())).build();
		}

		ReplyMessage reply= new ReplyMessage();
		if (removed) {
			reply.setReplyComment("Licence Specification successfully removed for productId="+productId);
		} else {
			reply.setReplyComment("Licence Specification not found to remove for productId="+productId);
		}
		
		return Response
				.status(200).entity(reply).build();

	}

	
	@GET
	@Path("/getlicencespec")
	@Produces(MediaType.APPLICATION_XML)
	public Response getLicenceSpec(@QueryParam("productId") String productId){

		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		LicenceSpecification licenceSpec=null;
		try{
			if (productId == null) throw new RuntimeException("productId cannot be null.");
			licenceSpec = licencePublisher.getLicenceSpec(productId);
		} catch (Exception e){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to get licence specification", null, e.getMessage())).build();
		}
		
		ReplyMessage reply= new ReplyMessage();
		if (licenceSpec==null) {
			reply.setReplyComment("Licence Specification not found for productId="+productId);
			reply.setLicenceSpec(null);
		} else {
			reply.setReplyComment("Licence Specification found for productId="+productId);
			reply.setLicenceSpec(licenceSpec);
		}
		
		return Response
				.status(200).entity(reply).build();

	}
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_XML)
	public Response getLicenceSpecMap(){

		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		Map<String, LicenceSpecification> lcnceSpecMap=null;
		try{
			lcnceSpecMap = licencePublisher.getLicenceSpecMap();
		} catch (Exception e){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to get licence specification map", null, e.getMessage())).build();
		}
		
		LicenceSpecMap licenceSpecMap= new LicenceSpecMap();
		licenceSpecMap.getLicenceSpecMap().putAll(lcnceSpecMap);
		
		return Response
				.status(200).entity(licenceSpecMap).build();

	}
	
	
	@GET
	@Path("/clearlicencespecs")
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteLicenceSpecifications(){

		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		try{
			licencePublisher.deleteLicenceSpecifications();
		} catch (Exception e){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to delete licence specifications", null, e.getMessage())).build();
		}

		ReplyMessage reply= new ReplyMessage();
        reply.setReplyComment("All Licence Specifications removed");
		
		return Response
				.status(200).entity(reply).build();
	}
	
	
	@POST
	@Path("/createlicence")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response createLicenceInstanceStr(LicenceMetadata licenceMetadata){

		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		String licenceInstanceStr=null;
		try{
			if (licenceMetadata == null) throw new RuntimeException("licenceMetadata cannot be null.");
			licenceInstanceStr = licencePublisher.createLicenceInstanceStr(licenceMetadata);
		} catch (Exception e){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to create licence instance", null, e.getMessage())).build();
		}

		ReplyMessage reply= new ReplyMessage();
        reply.setReplyComment("Successfully created licence instance");
        reply.setLicence(licenceInstanceStr);
		
		return Response
				.status(200).entity(reply).build();

	}
	
}
