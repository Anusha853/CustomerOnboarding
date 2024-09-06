package com.bootcamp.customer.Onboarding.Service;

import com.bootcamp.customer.Onboarding.constants.emailConstants;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private MailjetClient mailjetClient;

    public void sendEmail(String to, String userName) {
        // Create JSON object for email
        JSONObject email = new JSONObject();
        email.put("From", new JSONObject().put("Email",emailConstants.EMAIL_FROM));
        email.put("To", new JSONArray().put(new JSONObject().put("Email", to)));
        email.put("Subject", emailConstants.WELCOME_EMAIL_SUBJECT + userName);
        email.put("TextPart", emailConstants.WELCOME_EMAIL_BODY);

        MailjetRequest request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray().put(email));

        try {
            MailjetResponse response = mailjetClient.post(request);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed to send email. Status code: " + response.getStatus());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error sending email", e);
        }
    }


}
