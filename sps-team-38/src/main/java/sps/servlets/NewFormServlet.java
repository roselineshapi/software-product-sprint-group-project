package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import java.util.Map;
import java.util.List;
import java.io.PrintWriter;
import com.google.gson.Gson;
import com.google.sps.data.Form;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for creating new forms. */
@WebServlet("/new-form")
public class NewFormServlet extends HttpServlet {
   private long tmpId = 123;

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String title = request.getParameter("title");
    String ownerName = request.getParameter("ownerName");
    String ownerEmail = request.getParameter("ownerEmail");
    String description = request.getParameter("description");
    int capacity = Integer.parseInt(request.getParameter("capacity"));
    String expiryDate = request.getParameter("expiryDate");
    String sessionUrl = request.getParameter("sessionUrl");
    long timestamp = System.currentTimeMillis();

    Entity formEntity = new Entity("Form");
    formEntity.setProperty("title", title);
    formEntity.setProperty("ownerName", ownerName);
    formEntity.setProperty("ownerEmail", ownerEmail);
    formEntity.setProperty("description", description);
    formEntity.setProperty("capacity", capacity);
    formEntity.setProperty("expiryDate", expiryDate);
    formEntity.setProperty("sessionUrl", sessionUrl);
    formEntity.setProperty("timestamp", timestamp);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(formEntity);

    Form form = new Form(tmpId, timestamp, title, ownerName, ownerEmail, description, capacity, expiryDate, sessionUrl);
    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(form));
  }
}