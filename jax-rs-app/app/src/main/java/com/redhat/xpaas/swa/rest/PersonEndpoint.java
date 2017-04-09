/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.xpaas.swa.rest;

import com.redhat.xpaas.swa.model.Person;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/people")
public class PersonEndpoint {

	@Inject
	private PersonRepository pr;

	@GET
	@Produces({"application/json"})
	public Response getAll() {
		return pr.getAll();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces({"application/json"})
	public Response findById(@PathParam("id") Long id) {
		return pr.findById(id);
	}

	@POST
	@Consumes("application/json")
	public Response create(Person entity) {
		return pr.create(entity);
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		return pr.deleteById(id);
	}

}
