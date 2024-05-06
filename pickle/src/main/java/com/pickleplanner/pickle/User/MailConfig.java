// I was running into a bunch of errors with attempting to email and this helped resolve them somehow

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // Configure mail sender properties
        return mailSender;
    }
}