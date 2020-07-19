package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.sps.data.Form;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet responsible for listing comments. */
@WebServlet("/get-form")
public class GetForm extends HttpServlet {
    private long id;
    private long timestamp;
    private String title;
    private String ownerName;
    private String ownerEmail;
    private String description;
    private String sessionUrl;
    private String expiryDate;
    private String capacity;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Form").addSort("timestamp", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    String formTitle = request.getParameter("title");
    for (Entity entity : results.asIterable()) {
        String title = (String) entity.getProperty("title"); 
        if(title.equals(formTitle)){
            id = entity.getKey().getId();
            timestamp = (long) entity.getProperty("timestamp");
            title = (String) entity.getProperty("title");
            ownerName = (String) entity.getProperty("ownerName");
            ownerEmail = (String) entity.getProperty("ownerEmail");
            description = (String)entity.getProperty("description");
            capacity = entity.getProperty("capacity").toString();
            expiryDate = (String)entity.getProperty("expiryDate");
            sessionUrl = (String)entity.getProperty("sessionUrl");
        }
    }
    Form form = new Form(id, timestamp, title, ownerName, ownerEmail, description, Integer.valueOf(capacity), expiryDate, sessionUrl);
    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(form));
    }
}