package com.mahendratechnosoft.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mahendratechnosoft.crm.entity.Contacts;
import com.mahendratechnosoft.crm.repository.ContactsRepository;

@Service
public class ContactsService {

	@Autowired
	private ContactsRepository contactsRepository;

	public ResponseEntity<?> createContact(Contacts contacts) {

		try {
			contacts.setStatus(true);
			return ResponseEntity.ok(contactsRepository.save(contacts));

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error  " + e.getMessage());
		}

	}

	public ResponseEntity<?> updateContact(Contacts contacts) {

		try {

			return ResponseEntity.ok(contactsRepository.save(contacts));

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

}
