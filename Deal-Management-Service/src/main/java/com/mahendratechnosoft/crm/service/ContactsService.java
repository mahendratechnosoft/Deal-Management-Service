package com.mahendratechnosoft.crm.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.dto.ContactDto;
import com.mahendratechnosoft.crm.entity.Contacts;
import com.mahendratechnosoft.crm.entity.User;
import com.mahendratechnosoft.crm.repository.ContactsRepository;
import com.mahendratechnosoft.crm.repository.UserRepository;

@Service
public class ContactsService {

	@Autowired
	private ContactsRepository contactsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	public ResponseEntity<?> createContact(ContactDto request) {

		try {
			Contacts contacts = request.getContacts();
			
			if(request.getLoginEmail() != null && !request.getLoginEmail().isEmpty() &&
					request.getPassword() != null && !request.getPassword().isEmpty()) {
					
					if (userRepository.existsByLoginEmail(request.getLoginEmail())) {
			            throw new RuntimeException("Error: Email is already in use!");
			        }
					
					User newCustomerUser = new User();
					newCustomerUser.setLoginEmail(request.getLoginEmail());
					newCustomerUser.setPassword(passwordEncoder.encode(request.getPassword()));
					newCustomerUser.setRole("ROLE_CONTACT");
					newCustomerUser.setExpiryDate(LocalDateTime.now().plusYears(1));
					
					User savedUser = userRepository.save(newCustomerUser);
					contacts.setUserId(savedUser.getUserId());
				}
			
			return ResponseEntity.ok(contactsRepository.save(contacts));

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
		}

	}

	public ResponseEntity<?> updateContact(ContactDto request) {

		try {
			Contacts contacts = request.getContacts();
			if(request.isActive()) {
				if(request.getLoginEmail() != null && !request.getLoginEmail().isEmpty()) {
					
					String userId = contacts.getUserId();
					
					if(userId != null && !userId.isBlank()) {
						User userCustomer = userRepository.findById(userId)
						.orElseThrow(()-> new RuntimeException("user with not found with id :" + userId));
						
						if (userRepository.existsByLoginEmailAndUserIdNot(request.getLoginEmail(),userCustomer.getUserId())) {
				            throw new RuntimeException("Error: Email is already in use!");
				        }
						
						userCustomer.setLoginEmail(request.getLoginEmail());
						if(request.getPassword()!=null && !request.getPassword().isBlank()) {
							userCustomer.setPassword(passwordEncoder.encode(request.getPassword()));
						}
						userCustomer.setActive(true);
						userRepository.save(userCustomer);
					} else {
						if (userRepository.existsByLoginEmail(request.getLoginEmail())) {
				            throw new RuntimeException("Error: Email is already in use!");
				        }
						
						User newCustomerUser = new User();
						newCustomerUser.setLoginEmail(request.getLoginEmail());
						newCustomerUser.setPassword(passwordEncoder.encode(request.getPassword()));
						newCustomerUser.setRole("ROLE_CONTACT");
						newCustomerUser.setExpiryDate(LocalDateTime.now().plusYears(1));
						
						User savedUser = userRepository.save(newCustomerUser);
						contacts.setUserId(savedUser.getUserId());
					}
				}
			}else {
				String userId = contacts.getUserId();
				if(userId != null && !userId.isBlank()) {
					User user = userRepository.findById(userId)
					.orElseThrow(()-> new RuntimeException("user with not found with id :" + userId));
					user.setActive(false);
					userRepository.save(user);
				}
			}
			
			
			
			Contacts contact = contactsRepository.save(contacts);
			return ResponseEntity.ok(contact);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
		}

	}

	public ResponseEntity<?> getContacts(String customerId, String search) {

		try {

			List<Contacts> contactsList = contactsRepository
					.findByCustomerIdAndNameContainingIgnoreCaseOrderByIdDesc(customerId, search);
			return ResponseEntity.ok(contactsList);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
		}
	}

	public ResponseEntity<?> deleteContacts(String contactId) {

		try {

			contactsRepository.deleteById(contactId);

			return ResponseEntity.ok("Contact Deleted");

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
		}

	}
	
	public ResponseEntity<?> getContactById(String contactId) {

		try {
			Contacts contacts = contactsRepository.findById(contactId)
			.orElseThrow(()->new RuntimeException("Contact with not found for id :"+ contactId));
			ContactDto contactDto = new ContactDto();
			contactDto.setContacts(contacts);
			if(contacts.getUserId() != null && ! contacts.getUserId().isBlank()) {
				User user = userRepository.findById(contacts.getUserId())
						.orElseThrow(()->new RuntimeException("User with not found for id :"+ contacts.getCustomerId()));
				contactDto.setLoginEmail(user.getLoginEmail());
				contactDto.setActive(user.isActive());
			}
			return ResponseEntity.ok(contactDto);

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
		}

	}

}
