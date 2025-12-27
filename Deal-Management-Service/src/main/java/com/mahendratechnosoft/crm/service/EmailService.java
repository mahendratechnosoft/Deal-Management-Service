	package com.mahendratechnosoft.crm.service;
	
	import java.util.List;
	import java.util.Properties;
	import java.util.Set;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.mail.SimpleMailMessage;
	import org.springframework.mail.javamail.JavaMailSender;
	import org.springframework.mail.javamail.JavaMailSenderImpl;
	import org.springframework.stereotype.Service;
	
	import com.mahendratechnosoft.crm.dto.EmployeeInfo;
	import com.mahendratechnosoft.crm.entity.EmailConfiguration;
	import com.mahendratechnosoft.crm.repository.EmailConfigurationRepository;
	import com.mahendratechnosoft.crm.repository.EmployeeRepository;
	
	@Service
	public class EmailService {
	
	    @Autowired
	    private EmailConfigurationRepository emailConfigurationRepository;
	
	    @Autowired
	    private EmployeeRepository employeeRepository;
	    
		public EmailConfiguration updateEmailConfiguration(EmailConfiguration request) {
			return emailConfigurationRepository.save(request);
		}
		
		public EmailConfiguration getEmailConfiguration(String adminId) {
			return emailConfigurationRepository.findByAdminId(adminId);
		}
	
	    // Build sender every time using DB values
	    private JavaMailSender buildMailSender(EmailConfiguration config) {
	
	        JavaMailSenderImpl sender = new JavaMailSenderImpl();
	        Properties props = sender.getJavaMailProperties();
	
	        switch (config.getActiveHost().toUpperCase()) {
	
	            case "GMAIL":
	                sender.setHost("smtp.gmail.com");
	                sender.setPort(587);
	                sender.setUsername(config.getGmail());
	                sender.setPassword(config.getGmailPassword());
	                props.put("mail.smtp.starttls.enable", "true");
	                break;
	
	            case "HOSTINGER":
	                sender.setHost("smtp.hostinger.com");
	                sender.setPort(587);
	                sender.setUsername(config.getHostingerMail());
	                sender.setPassword(config.getHostingerPassword());
	                props.put("mail.smtp.starttls.enable", "true");
	                break;
	
	            case "OUTLOOK":
	                sender.setHost("smtp.office365.com");
	                sender.setPort(587);
	                // sender.setUsername(config.getOutlookMail());
	                // sender.setPassword(config.getOutlookPassword());
	                props.put("mail.smtp.starttls.enable", "true");
	                break;
	
	            case "ZOHO":
	                sender.setHost("smtp.zoho.com");
	                sender.setPort(587);
	                // sender.setUsername(config.getZohoMail());
	                // sender.setPassword(config.getZohoPassword());
	                props.put("mail.smtp.starttls.enable", "true");
	                break;
	
	            default:
	                throw new RuntimeException("Unsupported email host: " + config.getActiveHost());
	        }
	
	        props.put("mail.transport.protocol", "smtp");
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.debug", "true");
	
	        return sender;
	    }
	    
	    
	
	    public void sendSimpleEmail(String adminId, Set<String> employeeIds,
	                                String subject, String text) {
	         
	        EmailConfiguration config = emailConfigurationRepository.findByAdminId(adminId);
	
	        if (config == null) {
	            throw new RuntimeException("Email configuration not found");
	        }
	
	        JavaMailSender mailSender = buildMailSender(config);
	
	        List<EmployeeInfo> employeeInfos =
	                employeeRepository.findInEmployeeId(employeeIds);
	
	        for (EmployeeInfo info : employeeInfos) {
	
	            SimpleMailMessage message = new SimpleMailMessage();
	            message.setTo(info.getLoginEmail());
	            message.setSubject(subject);
	            message.setText(text);
	
	            // choose From email based on active host
	            switch (config.getActiveHost().toUpperCase()) {
	                case "GMAIL":
	                    message.setFrom(config.getGmail());
	                    break;
	                case "HOSTINGER":
	                    message.setFrom(config.getHostingerMail());
	                    break;
	                default:
	                    message.setFrom(config.getGmail()); // fallback
	            }
	
	            mailSender.send(message);
	        }
	    }
	    
	    
	    public void sendForgotPasswordEmail(String toEmail, String subject, String text) {

	        // Load active config (or set fallback system email)
			System.err.println("forgot password");
			EmailConfiguration config = new EmailConfiguration();
			config.setActiveHost("GMAIL");
			config.setGmail("kartikdemo5@gmail.com");
			config.setGmailPassword("pwko sqri nerl yrxk");

	        JavaMailSender mailSender = buildMailSender(config);

	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(toEmail);
	        message.setSubject(subject);
	        message.setText(text);

	        // choose From email based on active host
	        switch (config.getActiveHost().toUpperCase()) {
	            case "GMAIL":
	                message.setFrom(config.getGmail());
	                break;

	            case "HOSTINGER":
	                message.setFrom(config.getHostingerMail());
	                break;

	            default:
	                message.setFrom(config.getGmail());
	        }

	        // send (always OUTSIDE switch)
	        mailSender.send(message);
	    }

	}
