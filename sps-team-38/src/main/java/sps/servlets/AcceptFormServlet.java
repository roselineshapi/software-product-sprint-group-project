package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
// [START simple_email_includes]
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
// [END simple_email_includes]


@WebServlet("/accept-form")
@SuppressWarnings("serial")
public class AcceptFormServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String title = request.getParameter("title");
    String ownerEmail = request.getParameter("ownerEmail");
    String volunteerEmail = request.getParameter("email");
    response.getWriter().print("Sending simple email.");
    sendSimpleMail(title, ownerEmail, volunteerEmail);
    
    response.sendRedirect("/");
  }



  private void sendSimpleMail(String title, String ownerEmail, String volunteerEmail) {
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);

    try {
      Message msg = new MimeMessage(session);
      Message msg2 = new MimeMessage(session);
      //TODO: Create Web App Email Address and swith replyEmailString to that address
      String replyEmailString = "achlesinger1@sps-program.com";
      InternetAddress[] replyAddress = InternetAddress.parse(replyEmailString);
      msg.setFrom(new InternetAddress("matches@volunteer-web-app.appspotmail.com", "Volunteer Web App Team"));
      msg.addRecipient(Message.RecipientType.TO, new InternetAddress(ownerEmail, "Volunteer Requester"));
      msg.setSubject("A Volunteer Has Accepted Your Listing!");
      msg.setReplyTo(replyAddress);
      msg.setText("Congrats! A volunteer has accepted your listing titled, " + title + "." + "\n Please refer to your listing details on the site to review event details.");
      Transport.send(msg);
      msg2.setFrom(new InternetAddress("matches@volunteer-web-app.appspotmail.com", "Volunteer Web App Team"));
      msg2.addRecipient(Message.RecipientType.TO, new InternetAddress(volunteerEmail, "Volunteer"));
      msg2.setSubject("Your Volunteering Event is Confirmed!");
      msg2.setReplyTo(replyAddress);
      msg2.setText("Congrats! Your volunteering event with the listings title " + title + " is offically confirmed." + "\n Please review the event information on the site before the event start time.");
      Transport.send(msg2);
    } catch (AddressException e) {
      // ...
    } catch (MessagingException e) {
      // ...
    } catch (UnsupportedEncodingException e) {
      // ...
    }

  }
}
