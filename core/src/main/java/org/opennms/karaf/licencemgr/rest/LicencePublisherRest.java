/*
 * Copyright 2014 OpenNMS Group Inc., Entimoss ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.opennms.karaf.licencemgr.rest;

import java.util.Collection;
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
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceMetadataList;
import org.opennms.karaf.licencemgr.metadata.jaxb.LicenceSpecList;
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
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to add licence specification", null, exception)).build();
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
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to remove licence specification", null, exception)).build();
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
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to get licence specification", null, exception)).build();
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
	
	/**
	 * @return licence metadata spec (not the full license spec)
	 */
	@GET
	@Path("/getlicencemetadataspec")
	@Produces(MediaType.APPLICATION_XML)
	public Response getLicenceMetadata(@QueryParam("productId") String productId){

		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		LicenceSpecification licenceSpec=null;
		try{
			if (productId == null) throw new RuntimeException("productId cannot be null.");
			licenceSpec = licencePublisher.getLicenceSpec(productId);
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to get licence specification", null, exception)).build();
		}
		
		ReplyMessage reply= new ReplyMessage();
		if (licenceSpec==null) {
			reply.setReplyComment("Licence Metadata Spec not found for productId="+productId);
			reply.setLicenceMetadataSpec(null);
		} else {
			reply.setReplyComment("Licence Metadata Spec found for productId="+productId);
			reply.setLicenceMetadataSpec(licenceSpec.getLicenceMetadataSpec());
		}
		
		return Response
				.status(200).entity(reply).build();
	}
	
	@GET
	@Path("/listspecs")
	@Produces(MediaType.APPLICATION_XML)
	public Response getLicenceSpecList(){

		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		Map<String, LicenceSpecification> lcnceSpecMap=null;
		try{
			lcnceSpecMap = licencePublisher.getLicenceSpecMap();
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to get licence specification map", null, exception)).build();
		}
		
		LicenceSpecList licenceSpecList= new LicenceSpecList();
		licenceSpecList.getLicenceSpecList().addAll(lcnceSpecMap.values());
		
		return Response
				.status(200).entity(licenceSpecList).build();

	}

	
	/**
	 * @return list of licence metadata specs (not the full license spec)
	 */
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_XML)
	public Response getLicenceMetadataList(){

		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		Map<String, LicenceSpecification> lcnceSpecMap=null;
		try{
			lcnceSpecMap = licencePublisher.getLicenceSpecMap();
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to get licence specification map", null, exception)).build();
		}
		
		LicenceMetadataList licenceMetadataList= new LicenceMetadataList();
		
		Collection<LicenceSpecification> licenceSpecs = lcnceSpecMap.values();
		for(LicenceSpecification lspec:licenceSpecs){
			LicenceMetadata licenceMetadataSpec = lspec.getLicenceMetadataSpec();
			licenceMetadataList.getLicenceMetadataList().add(licenceMetadataSpec);
		}
		
		return Response
				.status(200).entity(licenceMetadataList).build();

	}
	
	
	@GET
	@Path("/clearlicencespecs")
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteLicenceSpecifications(@QueryParam("confirm") String confirm){

		LicencePublisher licencePublisher= ServiceLoader.getLicencePublisher();
		if (licencePublisher == null) throw new RuntimeException("ServiceLoader.getLicencePublisher() cannot be null.");

		try{
			if (!"true".equals(confirm)) throw new IllegalArgumentException("Will only delete specs if paramater confirm=true");
			licencePublisher.deleteLicenceSpecifications();
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to delete licence specifications", null, exception)).build();
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
		} catch (Exception exception){
			//return status 400 Error
			return Response.status(400).entity(new ErrorMessage(400, 0, "Unable to create licence instance", null, exception)).build();
		}

		ReplyMessage reply= new ReplyMessage();
        reply.setReplyComment("Successfully created licence instance");
        reply.setLicence(licenceInstanceStr);
		
		return Response
				.status(200).entity(reply).build();

	}
	
}
