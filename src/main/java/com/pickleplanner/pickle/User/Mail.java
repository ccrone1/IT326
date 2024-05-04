@Configuration
public class MailConfig {
    
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // Configure mail sender properties
        return mailSender;
    }
}