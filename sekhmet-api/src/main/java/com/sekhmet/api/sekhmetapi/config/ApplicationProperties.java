package com.sekhmet.api.sekhmetapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Sekhmet Api.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Getter
@Setter
public class ApplicationProperties {
    public final static String SPRING_PROFILE_DEVELOPMENT = "dev";
    /** Constant <code>SPRING_PROFILE_TEST="test"</code> */
    public final static String SPRING_PROFILE_TEST = "test";
    /** Constant <code>SPRING_PROFILE_PRODUCTION="prod"</code> */
    public final static String SPRING_PROFILE_PRODUCTION = "prod";
    private final S3Properties s3 = new S3Properties();
    private final TwilioProperties twilio = new TwilioProperties();
    private String env;
    @Getter
    @Setter
    public static class S3Properties {

        private String region;
        private String endpoint;
        private String bucket;
    }

    @Getter
    @Setter
    public static class TwilioProperties {

        private String accountSid;
        private String authToken;
        private String verifySid;
        private String apiSecret;
        private String apiSid;
        private String conversationSid;
        private String channelAdminSid;
        private String channelUserSid;
    }
}
