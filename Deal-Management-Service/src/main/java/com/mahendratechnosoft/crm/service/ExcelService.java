package com.mahendratechnosoft.crm.service;





import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mahendratechnosoft.crm.entity.ActivityLogs;
import com.mahendratechnosoft.crm.entity.Admin;
import com.mahendratechnosoft.crm.entity.Attendance;
import com.mahendratechnosoft.crm.entity.Employee;
import com.mahendratechnosoft.crm.entity.Leads;
import com.mahendratechnosoft.crm.repository.ActivityLogsRepository;
import com.mahendratechnosoft.crm.repository.AttendanceRepository;
import com.mahendratechnosoft.crm.repository.EmployeeRepository;
import com.mahendratechnosoft.crm.repository.LeadRepository;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExcelService {
	
	@Autowired
	private  LeadRepository leadRepository;

	@Autowired
	private ActivityLogsRepository activityLogsRepository;
	
	
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	
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
	
	public void generateLeadExcel(HttpServletResponse response, List<Leads> leadsList) throws Exception {

	    XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = workbook.createSheet("Leads");

	    CellStyle headerStyle = workbook.createCellStyle();
	    XSSFFont headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    headerFont.setFontHeight(12);
	    headerStyle.setFont(headerFont);
	    headerStyle.setAlignment(HorizontalAlignment.CENTER);
	    headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    setBorder(headerStyle); 

	    CellStyle dataStyle = workbook.createCellStyle();
	    setBorder(dataStyle);


	    CellStyle totalStyle = workbook.createCellStyle();
	    totalStyle.setFont(headerFont);
	    setBorder(totalStyle);


	    String[] headers = {
	        "Client Name", "Company Name", "Email", "Primary Number", "Secondary Number",
	        "Status", "Source", "Industry","Street","City", "State", "Country", "Zip Code", 
	        "Created Date", "Follow Up", "Website"
	    };


	    Row headerRow = sheet.createRow(0);
	    for (int i = 0; i < headers.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(headers[i]);
	        cell.setCellStyle(headerStyle);
	    }

	    int rowCount = 1;

	    for (Leads lead : leadsList) {
	        Row row = sheet.createRow(rowCount++);
	        
	        String createdDateStr = (lead.getCreatedDate() != null) ? lead.getCreatedDate().format(dtf) : "";
	        String followUpStr = (lead.getFollowUp() != null) ? lead.getFollowUp().format(dtf) : "";

	        createCell(row, 0, lead.getClientName(), dataStyle);
	        createCell(row, 1, lead.getCompanyName(), dataStyle);
	        createCell(row, 2, lead.getEmail(), dataStyle);
	        createCell(row, 3, lead.getMobileNumber(), dataStyle);
	        createCell(row, 4, lead.getPhoneNumber(), dataStyle);
	        createCell(row, 5, lead.getStatus(), dataStyle);
	        createCell(row, 6, lead.getSource(), dataStyle);
	        createCell(row, 7, lead.getIndustry(), dataStyle);
	        createCell(row, 8, lead.getStreet(), dataStyle);
	        createCell(row, 9, lead.getCity(), dataStyle);
	        createCell(row, 10, lead.getState(), dataStyle);
	        createCell(row, 11, lead.getCountry(), dataStyle);
	        createCell(row, 12, lead.getZipCode(), dataStyle);
	        createCell(row, 13, createdDateStr, dataStyle);
	        createCell(row, 14, followUpStr, dataStyle);
	        createCell(row, 15, lead.getWebsite(), dataStyle);
	    }

	    
	    for (int i = 0; i < headers.length; i++) {
	        sheet.autoSizeColumn(i);
	    }

	    response.setContentType("application/octet-stream");
	    String headerKey = "Content-Disposition";
	    String headerValue = "attachment; filename=leads_export_" + System.currentTimeMillis() + ".xlsx";
	    response.setHeader(headerKey, headerValue);

	    ServletOutputStream outputStream = response.getOutputStream();
	    workbook.write(outputStream);
	    workbook.close();
	    outputStream.close();
	}

	private void setBorder(CellStyle style) {
	    style.setBorderBottom(BorderStyle.THIN);
	    style.setBorderTop(BorderStyle.THIN);
	    style.setBorderRight(BorderStyle.THIN);
	    style.setBorderLeft(BorderStyle.THIN);
	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
	    Cell cell = row.createCell(columnCount);
	    
	    if (value instanceof Double) {
	        cell.setCellValue((Double) value);
	    } else if (value instanceof Integer) {
	        cell.setCellValue((Integer) value);
	    } else if (value instanceof Boolean) {
	        cell.setCellValue((Boolean) value);
	    } else {
	        cell.setCellValue(value != null ? value.toString() : "");
	    }

	    cell.setCellStyle(style);
	}

	public ByteArrayInputStream exportAttendanceExcel(Admin admin, String fromDate, String toDate, String monthName) throws Exception {

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    LocalDate start = LocalDate.parse(fromDate, formatter);
	    LocalDate end = LocalDate.parse(toDate, formatter);

	    List<Employee> employees = employeeRepository.findByAdmin(admin);

	    List<Attendance> attendanceList = attendanceRepository
	            .findAttendanceBetweenDates(admin.getAdminId(), start, end);

	    // employeeId → date → present
	    Map<String, Map<LocalDate, Boolean>> attendanceMap = new HashMap<>();
	    for (Attendance att : attendanceList) {
	        LocalDate attDate = Instant.ofEpochMilli(att.getTimeStamp())
	                .atZone(ZoneId.systemDefault())
	                .toLocalDate();
	        attendanceMap.computeIfAbsent(att.getEmployeeId(), k -> new HashMap<>())
	                .put(attDate, true);
	    }

	    XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = workbook.createSheet("Attendance Report");

	    // ----------------- FONT COLORS -----------------
	    Font greenFont = workbook.createFont();
	    greenFont.setBold(true);
	    greenFont.setColor(IndexedColors.GREEN.getIndex());
	    CellStyle presentStyle = workbook.createCellStyle();
	    presentStyle.setFont(greenFont);
	    presentStyle.setBorderBottom(BorderStyle.THIN);
	    presentStyle.setBorderTop(BorderStyle.THIN);
	    presentStyle.setBorderLeft(BorderStyle.THIN);
	    presentStyle.setBorderRight(BorderStyle.THIN);
	    presentStyle.setAlignment(HorizontalAlignment.CENTER);

	    Font redFont = workbook.createFont();
	    redFont.setColor(IndexedColors.RED.getIndex());
	    CellStyle absentStyle = workbook.createCellStyle();
	    absentStyle.setFont(redFont);
	    absentStyle.setBorderBottom(BorderStyle.THIN);
	    absentStyle.setBorderTop(BorderStyle.THIN);
	    absentStyle.setBorderLeft(BorderStyle.THIN);
	    absentStyle.setBorderRight(BorderStyle.THIN);
	    absentStyle.setAlignment(HorizontalAlignment.CENTER);

	    Font headerFont = workbook.createFont();
	    headerFont.setBold(true);
	    CellStyle headerStyle = workbook.createCellStyle();
	    headerStyle.setFont(headerFont);
	    headerStyle.setAlignment(HorizontalAlignment.CENTER);
	    headerStyle.setBorderBottom(BorderStyle.THIN);
	    headerStyle.setBorderTop(BorderStyle.THIN);
	    headerStyle.setBorderLeft(BorderStyle.THIN);
	    headerStyle.setBorderRight(BorderStyle.THIN);

	    // ----------------- MONTH & YEAR HEADER -----------------
	    Row monthRow = sheet.createRow(0);
	    Cell monthCell = monthRow.createCell(0);
	    monthCell.setCellValue(monthName + " " + start.getYear());
	    monthCell.setCellStyle(headerStyle);

	    int totalDays = (int) (end.toEpochDay() - start.toEpochDay() + 1);
	    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, totalDays + 2));

	    // ----------------- DAY HEADER ROW -----------------
	    Row headerRow = sheet.createRow(1);
	    headerRow.createCell(0).setCellValue("Employee Name");
	    headerRow.getCell(0).setCellStyle(headerStyle);

	    LocalDate current = start;
	    int col = 1;
	    while (!current.isAfter(end)) {
	        Cell cell = headerRow.createCell(col);
	        // Show day abbreviation: Mon, Tue, Wed...
	        String dayWithDate = current.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + current.getDayOfMonth();
	        cell.setCellValue(dayWithDate);
	        cell.setCellStyle(headerStyle);
	        col++;
	        current = current.plusDays(1);
	    }

	    // Total Days & Total Present
	    headerRow.createCell(col).setCellValue("Total Days");
	    headerRow.getCell(col).setCellStyle(headerStyle);
	    headerRow.createCell(col + 1).setCellValue("Total Present");
	    headerRow.getCell(col + 1).setCellStyle(headerStyle);

	    // ----------------- EMPLOYEE ROWS -----------------
	    int rowIndex = 2;
	    for (Employee emp : employees) {
	        Row row = sheet.createRow(rowIndex);
	        Cell nameCell = row.createCell(0);
	        nameCell.setCellValue(emp.getName());
	        nameCell.setCellStyle(headerStyle);

	        LocalDate datePointer = start;
	        int colIndex = 1;
	        Map<LocalDate, Boolean> empDates = attendanceMap.getOrDefault(emp.getEmployeeId(), new HashMap<>());
	        int presentCount = 0;

	        while (!datePointer.isAfter(end)) {
	            boolean present = empDates.containsKey(datePointer);
	            Cell cell = row.createCell(colIndex);
	            cell.setCellValue(present ? "P" : "AB");
	            cell.setCellStyle(present ? presentStyle : absentStyle);
	            if (present) presentCount++;
	            datePointer = datePointer.plusDays(1);
	            colIndex++;
	        }

	        // Total Days
	        Cell totalDaysCell = row.createCell(colIndex);
	        totalDaysCell.setCellValue(totalDays);
	        totalDaysCell.setCellStyle(headerStyle);

	        // Total Present
	        Cell presentCell = row.createCell(colIndex + 1);
	        presentCell.setCellValue(presentCount);
	        presentCell.setCellStyle(headerStyle);

	        rowIndex++;
	    }

	    // ----------------- FREEZE PANES -----------------
	    // Freeze first two rows (month + day header) and first column
	    sheet.createFreezePane(1, 2);

	    // Auto-size columns
	    for (int i = 0; i <= col + 1; i++) {
	        sheet.autoSizeColumn(i);
	    }

	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    workbook.write(out);
	    workbook.close();

	    return new ByteArrayInputStream(out.toByteArray());
	}




}
