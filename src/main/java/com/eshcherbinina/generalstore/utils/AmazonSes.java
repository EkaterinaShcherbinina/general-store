package com.eshcherbinina.generalstore.utils;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;

public class AmazonSes {
    final String emailFrom = "kshcherbinina26@gmail.com";
    final String textBody = "Hello dear customer! "
            + "We got a request to reset your password. If it was not you, please ignore this email,"
            + "otherwise follow the link below to set a password."
            + " http://localhost:8080/reset-password?token=$tokenValue"
            + " Thank you!";
    final String resetPasswordSubject = "Reset Password Request";

    public boolean sendPasswordResetRequest(String email, String token)
    {
        boolean returnValue = false;
        AmazonSimpleEmailService client =
                AmazonSimpleEmailServiceClientBuilder.standard()
                        .withRegion(Regions.US_EAST_1).build();

        String text = textBody.replace("$tokenValue", token);
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(
                        new Destination().withToAddresses(email) )
                .withMessage(new Message()
                        .withBody(new Body()
                                .withText(new Content()
                                        .withCharset("UTF-8").withData(text)))
                        .withSubject(new Content()
                                .withCharset("UTF-8").withData(resetPasswordSubject)))
                .withSource(emailFrom);

        SendEmailResult result = client.sendEmail(request);
        if(result != null && (result.getMessageId() != null && !result.getMessageId().isEmpty()))
            returnValue = true;

        return returnValue;
    }
}
