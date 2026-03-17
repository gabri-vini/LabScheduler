package com.xaviervinicius.labschedule.configs;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailgunConfigs {
    @Bean
    MailgunMessagesApi api(@Value("${application.apis.mailgun.key}") String key){
        return MailgunClient.config(key)
                .createAsyncApi(MailgunMessagesApi.class);
    }
}
