
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

/** Servlet responsible for listing all forms */
@WebServlet("/list-all-forms")
public class ListAllForms extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String orgEmail = request.getParameter("email");
    Query query = new Query("Form").addSort("timestamp", SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Form> forms = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
        String ownerEmail = (String) entity.getProperty("ownerEmail");
        if(ownerEmail.equals(orgEmail)){
            long id = entity.getKey().getId();
            long timestamp = (long) entity.getProperty("timestamp");
            String title = (String) entity.getProperty("title");
            String ownerName = (String) entity.getProperty("ownerName");
            String  description = (String) entity.getProperty("description");
            String sessionUrl = (String) entity.getProperty("sessionUrl");
            String expiryDate = (String) entity.getProperty("expiryDate");
            String capacity = entity.getProperty("capacity").toString();
            
            Form form = new Form(id, timestamp, title, ownerName, ownerEmail, description, Integer.valueOf(capacity), expiryDate, sessionUrl);
            forms.add(form);
        }
    }
    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(forms));
  }

}
