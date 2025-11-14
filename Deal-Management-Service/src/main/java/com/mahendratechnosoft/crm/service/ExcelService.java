package com.mahendratechnosoft.crm.service;




import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mahendratechnosoft.crm.dto.AttendanceEmployeeDTO;
import com.mahendratechnosoft.crm.entity.ActivityLogs;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.Leads;
import com.mahendratechnosoft.crm.repository.ActivityLogsRepository;
import com.mahendratechnosoft.crm.repository.LeadRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExcelService {
	
	@Autowired
	private  LeadRepository leadRepository;

	@Autowired
	private ActivityLogsRepository activityLogsRepository;
	
	public void generateExcel(HttpServletResponse response) throws Exception {

	    XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = workbook.createSheet("SampleData");

	    // ======= HEADER STYLE =======
	    CellStyle headerStyle = workbook.createCellStyle();
	    Font font = workbook.createFont();

	    font.setBold(true);
	    font.setFontHeightInPoints((short) 12);
	    headerStyle.setFont(font);

	    // Background color
	    headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	    // Border
	    headerStyle.setBorderBottom(BorderStyle.THIN);
	    headerStyle.setBorderTop(BorderStyle.THIN);
	    headerStyle.setBorderLeft(BorderStyle.THIN);
	    headerStyle.setBorderRight(BorderStyle.THIN);

	    // Alignment
	    headerStyle.setAlignment(HorizontalAlignment.CENTER);
	    headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

	    // ======= HEADER ROW =======
	    Row header = sheet.createRow(0);

	    String[] headers = {
	            "Client Name", "Company Name", "Email", "Mobile", "Phone",
	            "Source", "Website", "Industry", "Street", "Country",
	            "State", "City", "Zip Code", "Description"
	    };

	    for (int i = 0; i < headers.length; i++) {
	        Cell cell = header.createCell(i);
	        cell.setCellValue(headers[i]);
	        cell.setCellStyle(headerStyle);
	    }

	    // Auto-size all columns
	    for (int i = 0; i < headers.length; i++) {
	        sheet.autoSizeColumn(i);
	    }

	    // ======= RESPONSE SETTINGS =======
	    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	    response.setHeader("Content-Disposition", "attachment; filename=sample.xlsx");

	    ServletOutputStream outputStream = response.getOutputStream();
	    workbook.write(outputStream);
	    workbook.close();
	    outputStream.close();
	}
	
	public List<Leads> importLeadsFromExcel(MultipartFile file,Object loginUser) throws Exception {

		String adminId = null;
		String employeeId = null;
		String createdBy=null;
		if (loginUser instanceof Admin admin) {
			adminId = admin.getAdminId();
			createdBy=admin.getCompanyName();
		} else if (loginUser instanceof Employee employee) {
			employeeId = employee.getEmployeeId();
			adminId=employee.getAdmin().getAdminId();
			createdBy=employee.getName();
		}
		
	    List<Leads> leadsList = new ArrayList<>();
        List<ActivityLogs> activityList=new ArrayList<>();
	    XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
	    XSSFSheet sheet = workbook.getSheetAt(0);

	    int rows = sheet.getPhysicalNumberOfRows();

	    // Skip header → Start from row 1
	    for (int i = 1; i < rows; i++) {

	        Row row = sheet.getRow(i);
	        if (row == null) continue;

	        Leads lead = new Leads();

	        lead.setClientName(getCellValue(row.getCell(0)));
	        lead.setCompanyName(getCellValue(row.getCell(1)));
	        lead.setEmail(getCellValue(row.getCell(2)));
	        lead.setMobileNumber(getCellValue(row.getCell(3)));
	        lead.setPhoneNumber(getCellValue(row.getCell(4)));
	        lead.setSource(getCellValue(row.getCell(5)));
	        lead.setWebsite(getCellValue(row.getCell(6)));
	        lead.setIndustry(getCellValue(row.getCell(7)));
	        lead.setStreet(getCellValue(row.getCell(8)));
	        lead.setCountry(getCellValue(row.getCell(9)));
	        lead.setState(getCellValue(row.getCell(10)));
	        lead.setCity(getCellValue(row.getCell(11)));
	        lead.setZipCode(getCellValue(row.getCell(12)));
	        lead.setDescription(getCellValue(row.getCell(13)));
            lead.setAdminId(adminId);
            lead.setEmployeeId(employeeId);
            lead.setCreatedDate(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDateTime());
            lead.setUpdatedDate(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDateTime());
            lead.setStatus("New Lead");
	        leadsList.add(lead);
	        
	        ActivityLogs activity=new ActivityLogs();
			activity.setAdminId(lead.getAdminId());
			activity.setCreatedBy(createdBy);
			activity.setCreatedDateTime(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDateTime());
			activity.setDescription("Lead Imported");
			activity.setModuleType("lead");
			activity.setModuleId(lead.getId());
			
			activityList.add(activity);
	    }
	    
	 // 1️⃣ Save all leads first (IDs will be generated here)
	    List<Leads> savedLeads = leadRepository.saveAll(leadsList);


	    for (Leads l : savedLeads) {

	        ActivityLogs activity = new ActivityLogs();
	        activity.setAdminId(l.getAdminId());
	        activity.setCreatedBy(createdBy);
	        activity.setCreatedDateTime(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toLocalDateTime());
	        activity.setDescription("Lead Imported");
	        activity.setModuleType("lead");
	        activity.setModuleId(l.getId());  // now ID is NOT null

	        activityList.add(activity);
	    }

	    // 3️⃣ Save activity logs
	    activityLogsRepository.saveAll(activityList);
	    
	    
		

	    workbook.close();

	    
	    return savedLeads;
	    
	}

	private String getCellValue(Cell cell) {
	    if (cell == null) return "";

	    switch (cell.getCellType()) {
	        case STRING:
	            return cell.getStringCellValue().trim();
	        case NUMERIC:
	            if (DateUtil.isCellDateFormatted(cell)) {
	                return cell.getLocalDateTimeCellValue().toString();
	            }
	            return String.valueOf((long) cell.getNumericCellValue());
	        case BOOLEAN:
	            return String.valueOf(cell.getBooleanCellValue());
	        case BLANK:
	            return "";
	        default:
	            return "";
	    }
	}


}
