package com.redhat.xpaas.swa.rest;

import static javax.ws.rs.core.Response.status;

import com.redhat.xpaas.swa.model.Person;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Stateless
public class PersonRepository {

	@PersistenceContext
	private EntityManager em;

	public Response create(Person entity) {
		em.persist(entity);
		return Response.created(UriBuilder.fromResource(PersonEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
	}

	public Response findById(Long id) {
		TypedQuery<Person> findByIdQuery = em.createQuery("SELECT p FROM Person p WHERE p.id = :entityId", Person.class);
		findByIdQuery.setParameter("entityId", id);
		Person entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException e) {
			return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for ID: " + id).build();
		}
		return Response.ok(entity).build();
	}

	public Response deleteById(Long id) {
		Person entity = em.find(Person.class, id);
		if (entity == null) {
			return status(Response.Status.NOT_FOUND).build();
		}
		em.remove(entity);
		return Response.noContent().build();
	}

	public Response getAll() {
		TypedQuery<Person> findByIdQuery = em.createQuery("SELECT p FROM Person p", Person.class);
		List<Person> entities;
		entities = findByIdQuery.getResultList();
		if (entities.isEmpty()) {
			return Response.status(Response.Status.NOT_FOUND).entity("No entities found.").build();
		}
		return Response.ok(entities).build();
	}

}
