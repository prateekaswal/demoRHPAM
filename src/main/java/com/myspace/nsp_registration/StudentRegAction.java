package com.myspace.nsp_registration;

import org.apache.commons.lang.StringUtils;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;
import com.google.gson.Gson;
import java.util.Map;
import java.util.HashMap;
import org.json.JSONException;
import java.security.NoSuchAlgorithmException;
/**
 * This class was automatically generated by the data modeler tool.
 */

public class StudentRegAction {

	private java.lang.String fieldError;

	public String checkSQLInjection(
			com.myspace.nsp_registration.Student student) {
		System.out.println("In checkSQLInjection method :"+student.getIfscCode());
        String result = "success";
		if (student.getSchemeType()==1 || student.getSchemeType()==2) {
		    System.out.println("Scheme Type :"+student.getSchemeType());
        } else {
            this.setFieldError("Please Select Scheme Type Option");
            return "input";
        }
        
        if (student.getEidOpt().equals("1") || student.getEidOpt().equals("3")) {
            System.out.println("EID Opt :"+student.getEidOpt());
        } else {
            this.setFieldError("Please Select Identification Detail Option");
            return "input";
        }
        
        student.setAadharNumber(student.getAadhaar_number());
        System.out.println("Aadhar Number:"+student.getAadharNumber());
        student.setAadhaar_number("");
        if (student.getEidOpt().equals("1")) {
            if (student.getAadharNumber() == null) {
                student.setAadharNumber("");
            } else if (student.getAadharNumber() == "") {
                this.setFieldError("Please Enter Valid Aadhaar Number");
                return "input";
            }
            else
            {
                try
                {
                    System.out.println("Aadhar :"+student.getAadharNumber());
                    student.setAadharNumber(RSAUtil.decrypt(student.getAadharNumber(), AadhaarDecryptionKey.privateKey));
                    System.out.println("Aadhar decryption:"+student.getAadharNumber());
                }
                catch(java.lang.Exception e)
                {
                    student.setAadharNumber("");
                    this.setFieldError("Please Enter Valid Aadhaar Number");
                    return "input";
                }
            }
        }
        
        sqlInjection sqli = new sqlInjection();
        student.setStudentName(sqli.removeWhitespaceChar(student.getStudentName()));
        student.setBankAcccountNO(sqli.removeWhitespaceChar(student.getBankAcccountNO()));
        student.setIfscCode(sqli.removeWhitespaceChar(student.getIfscCode()));
        student.setBankName(sqli.removeWhitespaceChar(student.getBankName()));
        DateUtil dtutl=new DateUtil();
        student.setNameAsInAadhhar(student.getStudentName());
        
        if (student.getStateDomicile() < 1 || student.getStateDomicile() > 36) {
            this.setFieldError("Please Select Domicile State ?");
            return "input";
        } else if ((student.getScholarshipCategory() == -1)) {
            this.setFieldError("Please Select Scholarship Category ?");
            return "input";
        } else if (sqli.isEmpty(student.getStudentName())) {
            this.setFieldError("Please Enter Name of Student ?");
            return "input";
        } else if (!sqli.handleSqlStudentName(student.getStudentName())) {
            this.setFieldError("Special characters are not allowed");
            return "input";
        } else if (sqli.isEmpty(student.getDateOfBirth()) || !sqli.handleSql(student.getDateOfBirth())) {
            this.setFieldError("Please Enter Date of Birth ?");
            return "input";
        } else if(!dtutl.validate(student.getDateOfBirth())) {
            this.setFieldError("Invalid Date of Birth ?");
            return "input"; 
        } else if (!sqli.validateDOB(student.getDateOfBirth())) {
            this.setFieldError("Invalid Date of Birth ?");
            return "input";
        } else if (student.getGender().equals("-1") || sqli.isEmpty(student.getGender()) || !sqli.handleSql(student.getGender())) {
            this.setFieldError("Please Select Gender ?");
            return "input";
        } else if (student.getEidOpt().equals("-1") || sqli.isEmpty(student.getEidOpt()) || !sqli.handleSql(student.getEidOpt())) {
            this.setFieldError("Please Select Identity Proof ?");
            return "input";
        }
        
        if (student.getEidOpt().equals("1")) {
            if (sqli.isEmpty(student.getNameAsInAadhhar())) {
                this.setFieldError("Please Enter Name As in Aadhaar");
                return "input";
            } else if (!sqli.handleSqlNameAsInAadhaar(student.getNameAsInAadhhar())) {
                this.setFieldError("Special Character are not allowed in Name As in Aadhaar");
                return "input";
            }
            if (student.getAadharNumber() == "" || student.getAadharNumber().length() != 12) {
                this.setFieldError("Please Enter Valid Aadhaar Number ?");
                return "input";
            }   
        }
        
        if (student.getEidOpt().equals("3") || student.getEidOpt().equals("1")) {
            if (sqli.isEmpty(student.getIfscCode())) {
                this.setFieldError("Please Enter IFSC Code");
                return "input";
            } else if (!sqli.handleSql(student.getIfscCode())) {
                this.setFieldError("Special Character are not allowed in IFSC Code");
                return "input";
            } else if (student.getIfscCode().length() != 11 || student.getIfscCode().charAt(4) != '0') {
                this.setFieldError("Invalid IFSC Code");
                return "input";
            } else if (sqli.isEmpty(student.getBankAcccountNO())) {
                this.setFieldError("Please Enter Bank A/C Number");
                return "input";
            } else if (!StringUtils.isAlphanumeric(student.getIfscCode())) {
                this.setFieldError("Invalid ifsc code");
                return "input";
            }
            
            if (student.getBankAcccountNO().length() > 20) {
                this.setFieldError("Maximum 16 Digit Bank A/C Number is Allowed");
                return "input";
            } else if (!StringUtils.isAlphanumeric(student.getBankAcccountNO())) {
                this.setFieldError("Invalid Account Number");
                return "input";
            } else if (!sqli.handleSql(student.getBankAcccountNO())) {
                this.setFieldError("Special Character are not allowed in Bank Account Number");
                return "input";
            } else if (sqli.isEmpty(student.getBankName())) {
                this.setFieldError("Please Enter Bank A/C Number");
                return "input";
            }
            
            if (student.getBankName().length() > 110) {
                this.setFieldError("Exceeding Max. Bank Name Length Limit 110 Characters");
                return "input";
            }    
            
            if (!sqli.handleSql(student.getBankName())) {
                this.setFieldError("Special Character are not allowed in Bank Name");
                return "input";
            }
            
            if (!sqli.handleSql(student.getBankAcccountNO())) {
                this.setFieldError("Special Characters are not allowed in Bank A/C Number");
                return "input";
            }
            
            //document type,size etc validation 
            if (student.getEidOpt().equals("3")) {
                try {
                    MagicMatch match = null;
                    
                    if (sqli.isEmpty(student.getBankProofDocFileName())) {
                        this.setFieldError("Please Select Passbook Scan Copy to upload");
                        return "input";
                    }
                   
                    if (student.getBankProofDocFileName().length() > 0) {    
                        if(student.getBankProofDocFileName().length()>50) {
                            this.setFieldError("Passbook Scan Copy file name should not exceed 50 characters");
                            return "input";
                        }
                        
                        if (student.getBankProofDoc().length() < 204800) {
                            
                            if (student.getBankProofDocContentType().equalsIgnoreCase("application/pdf") || student.getBankProofDocContentType().equalsIgnoreCase("image/jpeg")|| student.getBankProofDocContentType().equalsIgnoreCase("image/jpg")) {

                                if (FilenameUtils.getExtension(student.getBankProofDocFileName()).equalsIgnoreCase("pdf") || FilenameUtils.getExtension(student.getBankProofDocFileName()).equalsIgnoreCase("jpeg")|| FilenameUtils.getExtension(student.getBankProofDocFileName()).equalsIgnoreCase("jpg")) {

                                } else {
                                    this.setFieldError("Invalid file type only JPEG & PDF allowed!!!");
                                    return "input";
                                }
                            } else {
                                this.setFieldError("Invalid file type only JPEG & PDF allowed!!!");
                                return "input";
                            }
                            //System.out.println("Bank Proof Doc :"+student.getBankProofDoc());
                            match = Magic.getMagicMatch(student.getBankProofDoc(), false);
                            if (match.getMimeType().equalsIgnoreCase("application/pdf") || match.getMimeType().equalsIgnoreCase("image/jpeg")|| match.getMimeType().equalsIgnoreCase("image/jpg")) {
                            } else {
                                this.setFieldError("Invalid file type only JPEG & PDF allowed!!!");
                                return "input";
                            }
                        } else {
                            this.setFieldError("Maximum 200kb file size allowed to upload!!!");
                            return "input";
                        }
                    } else {
                        this.setFieldError("Please Select Passbook Scan Copy to upload");
                        return "input";
                    }
                } catch (java.lang.Exception e) {
                    this.setFieldError("Please try again (Only .pdf and .jpeg file type allowed)");
                    return "input";
                }
                //end of document type,size etc validation
                
                // eid validation
                if (sqli.isEmpty(student.getEid()) || student.getEid().length() != 16) {
                    student.setEid("");
                } else if (!sqli.handleSql(student.getEid())) {
                    this.setFieldError("Special Character are not allowed in Aadhaar Enrolment Number");
                    return "input";
                } else if (sqli.isEmpty(student.getEiddateTime()) || student.getEiddateTime().length() != 19) {
                    student.setEiddateTime("");
                } else if (!sqli.handleSqlDateTime(student.getEiddateTime())) {
                    this.setFieldError("Special Characters are not allowed in Enrolment ID");
                    return "input";
                } else if (!sqli.isEmpty(student.getEid())) {
                    if (!sqli.isEmpty(student.getEiddateTime())) {
                        if (sqli.isEmpty(student.getEidProofDocFileName())) {
                            this.setFieldError("if you are filling Aadhaar Enrolment Id(EID) then uploading of EID receipt copy is mandatory");
                            return "input";
                        }
                    }
                }
                //end of eid validation
                
                try {
                    MagicMatch match = null;
                    if (!sqli.isEmpty(student.getEidProofDocFileName())) {
                        if (student.getEidProofDocFileName().length() > 0) {
                                                        
                            if (sqli.isEmpty(student.getEid()) || student.getEid().length() != 16) {
                                this.setFieldError("Aadhaar Enrolment Number is mandatory if You are uploading Aadhaar Enrolment Receipt Copy");
                                return "input";

                            } else if (sqli.isEmpty(student.getEiddateTime()) || student.getEiddateTime().length() != 19) {
                                this.setFieldError("Aadhaar Enrolment Number is mandatory if You are uploading Aadhaar Enrolment Receipt Copy");
                                return "input";
                            }
                            if (student.getEidProofDoc().length() < 204800) {
                            
                                    if (student.getEidProofDocContentType().equalsIgnoreCase("application/pdf") || student.getEidProofDocContentType().equalsIgnoreCase("image/jpeg")|| student.getEidProofDocContentType().equalsIgnoreCase("image/jpg")) {

                                    if (FilenameUtils.getExtension(student.getEidProofDocFileName()).equalsIgnoreCase("pdf") || FilenameUtils.getExtension(student.getEidProofDocFileName()).equalsIgnoreCase("jpeg")|| FilenameUtils.getExtension(student.getEidProofDocFileName()).equalsIgnoreCase("jpg")) {

                                    } else {
                                        this.setFieldError("Invalid file type only JPEG & PDF allowed!!!");
                                        return "input";
                                    }
                                } else {
                                    this.setFieldError("Invalid file type only JPEG & PDF allowed!!!");
                                    return "input";
                                }
                                //System.out.println("EID Proof Doc :"+student.getEidProofDoc());
                                match = Magic.getMagicMatch(student.getEidProofDoc(), false);
                                if (match.getMimeType().equalsIgnoreCase("application/pdf") || match.getMimeType().equalsIgnoreCase("image/jpeg")|| match.getMimeType().equalsIgnoreCase("image/jpg")) {
                                } else {
                                    this.setFieldError("Invalid file type only JPEG & PDF allowed!!!");
                                    return "input";
                                }
                            } else {
                                this.setFieldError("Maximum 200kb file size allowed to upload!!!");
                                return "input";
                            }
                        }

                    } else {
//                   setFieldError("Please Select Passbook Scan Copy to upload");
//                       return "input"; 
                    }
                } catch (Exception e) {
                    this.setFieldError("Please try again (Only .pdf and .jpeg file type allowed)");
                    return "input";
                }
            }    
        }
        
        if (!sqli.checkMobile(String.valueOf(student.getMobileNumber()))) {
            this.setFieldError("Please Enter 10 digit Mobile Number?");
            return "input";
        } else if (student.getMobileNumber() == 0 || String.valueOf(student.getMobileNumber()).length() != 10) {
            this.setFieldError("Please Enter 10 digit valid Mobile Number?");
            return "input";
        } else if (!sqli.checkmail(student.getEmailAddress())) {
            this.setFieldError("Please Enter Correct Email Address?");
            return "input";
        }
        return result;
	}
	
	public String verifyNameInAadhar(com.myspace.nsp_registration.Student student, String verifyStatus) {
	    
	    JSONObject objectAadhar = null;
	    try{
	        objectAadhar = new JSONObject(verifyStatus);
	    } catch (JSONException e) { 
	        e.printStackTrace();
	    }
	    String aadhaarVerifStat = "";
        String res1 = null;
        String res2 = null;
        String res3 = null;
        
        Map<String,String> aadharMap = new Gson().fromJson(objectAadhar.toString(), HashMap.class);
        aadhaarVerifStat = aadharMap.get("status");
        
        System.out.println("Aadhar Status :"+aadhaarVerifStat);
        String[] resultResponse = aadhaarVerifStat.split("\\$", 5);
        res1 = resultResponse[0];
        res2 = resultResponse[1];
        res3 = resultResponse[2];
        if (res1.equalsIgnoreCase("Y")){
            student.setAadhaar_token_no(resultResponse[3]);
            student.setAadhaar_registration_mode(resultResponse[4]);
        }
        
        if (res1.equalsIgnoreCase("N")) {
                if (res3.equalsIgnoreCase("Identity data Mismatch")) {
                    this.setFieldError("Applicant Name does not match with Name mentioned in Aadhaar Number.");
                } else if (res3.equalsIgnoreCase("100")) {
                    this.setFieldError("Please check aadhaar number/name. Data provided doesnot match.");
                } else if (res3.equalsIgnoreCase("997")) {
                    this.setFieldError("Aadhaar Suspended. Your Aadhaar number status is not active. Kindly contact UIDAI");
                } else if (res3.equalsIgnoreCase("996")) {
                    this.setFieldError("Aadhaar Cancelled.");
                } else if (res3.equalsIgnoreCase("998")) {
                    this.setFieldError("Invalid Aadhaar Number 0r Non Availability of Aadhaar data");
                } else {
                    this.setFieldError("Error(UIDAI)  : error " + res3);
                }
                return "input";
        }
	    return "success";
	}
	
    public String verifyAadharInDB(String apiResponse) {
	    JSONObject objectAadhar = null;
	    try{
	        objectAadhar = new JSONObject(apiResponse);
	    } catch (JSONException e) { 
	        e.printStackTrace();
	    }
	    
	    String result = "";
	    Map<String,String> aadharMap = new Gson().fromJson(objectAadhar.toString(), HashMap.class);
        String res = aadharMap.get("errorResponse");
        
        if(res.equals("Aadhar not found")){
            result = "success";
        } else {
            this.setFieldError(res);
            result = "input";
        }
        
        return result;
	}
	
	public String checkDuplicate(String apiResponse) {
	    JSONObject objectDuplicate = null;
	    try{
	        objectDuplicate = new JSONObject(apiResponse);
	    } catch (JSONException e) { 
	        e.printStackTrace();
	    }
	    
	    String result = "";
	    Map<String,String> map = new Gson().fromJson(objectDuplicate.toString(), HashMap.class);
        String res = map.get("errorResponse");
        
        if(res.equals("Bank Account not found")){
            result = "success";
        } else {
            this.setFieldError(res);
            result = "input";
        }
        
        return result;
	}
	
	public void generateRegistrationID(com.myspace.nsp_registration.Student student, String counter, String shortName, String currentSession){
	    String studentRegistrationId = "";
	    
	    if (student.getSchemeType() == 1) {
            studentRegistrationId = shortName.toUpperCase() + currentSession + String.format("%09d",java.lang.Long.parseLong(counter));
        } else {
            studentRegistrationId = "IN" + shortName.toUpperCase() + String.valueOf(currentSession).substring(2,6) + String.format("%09d",java.lang.Long.parseLong(counter));
        }
        System.out.println("Student Registration ID :"+studentRegistrationId);
        student.setStudentRegId(studentRegistrationId);
	}
	
	public String submitRegistrationDocJson(com.myspace.nsp_registration.Student student, String counter, String shortName, String currentSession, String ip, String docUploadQuery) {
	    Map<String,String> map = new HashMap<>();
	    
        String studentRegistrationId = student.getStudentRegId();
        System.out.println("Student Registration ID :"+studentRegistrationId);
        
        String tab_name = "";
        String query = "";
        if (student.getEidOpt().equals("3")) {
            	student.setAadhaar_registration_mode("");
            	map.put("docUpload",docUploadQuery);
        }
        
        if (student.getSchemeType() == 1) {
            tab_name = "data_applicant_registration_details";
        } else {
            tab_name = "data_applicant_registration_details_nsig";
        }
        
        try{
		query = "INSERT INTO " + tab_name + "(application_id, fresh_renewal,domicile_state_id, applicant_name, aadhaar_no, gender, date_of_birth,email_id, mobile_no, application_level,created_by,hash_dob,pre_post_matric,permanent_state_id,ip_address,application_status,bank_name,bank_account_no,branch_ifsc_code) VALUES('"+studentRegistrationId+"','F',"+student.getStateDomicile()+",'"+student.getStudentName()+"',aadhaar_mask('"+student.getAadharNumber()+"',12,4),'"+student.getGender()+"',to_date('"+student.getDateOfBirth()+"','dd/mm/yyyy'),'"+student.getEmailAddress()+"',"+student.getMobileNumber()+",-1,'"+studentRegistrationId+"','"+encript_passwoard.SHA512_MD5(student.getDateOfBirth())+"',"+student.getScholarshipCategory()+","+student.getStateDomicile()+",'"+ip+"','SU','"+student.getBankName()+"','"+student.getBankAcccountNO()+"','"+student.getIfscCode()+"')";
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        map.put("submitRegistration",query);
        
        if (student.getSchemeType() == 1) {
            tab_name = "data_applicant_registration_warb";
        } else {
            tab_name = "data_applicant_registration_warb_nsig";
        }
                
        query ="insert into " + tab_name + "(application_id) values ('"+studentRegistrationId+"')";
        map.put("submitWarb",query);
        
        if (student.getSchemeType() == 1) {
            tab_name = "data_applicant_qualifications";
        } else {
            tab_name = "data_applicant_qualifications_nsig";
        }
                
        query ="insert into " + tab_name + "(application_id) values ('"+studentRegistrationId+"')";
        map.put("submitQualification",query);
        
        if (student.getSchemeType() == 1) {
           tab_name = "data_applicant_applied_schemes";
        } else {
            tab_name = "data_applicant_applied_schemes_nsig";
        }
                
        query ="insert into " + tab_name + "(application_id,scheme_id) values ('"+studentRegistrationId+"',0)";
        map.put("submitScheme",query);
        
        if (student.getEidOpt().equals("1")) {
            if (student.getSchemeType() == 1) {
                tab_name = "data_applicant_agency_verifications";
            } else {
                tab_name = "data_applicant_agency_verifications_nsig";
            }
            
            query ="insert into " + tab_name + "(application_id,applicant_name_in_aadhaar,aadhaar_verif_status,npci_aadhaar_no) values ('"+studentRegistrationId+"','"+student.getNameAsInAadhhar()+"','0','"+student.getAadharNumber()+"')";
            map.put("submitEid",query);
        } else if (student.getEidOpt().equals("2")) {
            if (student.getSchemeType() == 1) {
                tab_name = "data_applicant_eid";
            } else {
                tab_name = "data_applicant_eid_nsig";
            }
            
            query ="insert into " + tab_name + "(application_id,eid_no, eid_date_time, doc_upload_date) values ('"+studentRegistrationId+"','"+student.getEid()+"','"+student.getEiddateTime()+"',now())";
            map.put("submitEid",query);
        } else if (student.getEidOpt().equals("3")) {
            map.put("submitEid",docUploadQuery);
        }
        
        if (student.getEidOpt().equals("3")) {
            if (student.getSchemeType() == 1) {
                tab_name = "data_applicant_agency_verifications";
            } else {
                tab_name = "data_applicant_agency_verifications_nsig";
            }
                    
            query ="insert into " + tab_name + "(application_id) values ('"+studentRegistrationId+"')";
            map.put("submitAgency",query);
        }
        
        if (student.getSchemeType() == 1) {
            tab_name = "date_applicant_mobileno_verification";
        } else {
            tab_name = "date_applicant_mobileno_verification_nsig";
        }
        
        query ="insert into " + tab_name + "(is_validated_mobileno,mobile_no,application_id) values ('N','"+student.getMobileNumber()+"','"+studentRegistrationId+"')";
        map.put("submitMobile",query);
        JSONObject json = new JSONObject(map);
        
        return json.toString();
	}
	
	public String submitRegistrationJson(com.myspace.nsp_registration.Student student, String counter, String shortName, String currentSession, String ip) {
	    Map<String,String> map = new HashMap<>();
	    
        String studentRegistrationId = student.getStudentRegId();
        System.out.println("Student Registration ID :"+studentRegistrationId);
        
        String tab_name = "";
        String query = "";
        
        if (student.getSchemeType() == 1) {
            tab_name = "data_applicant_registration_details";
        } else {
            tab_name = "data_applicant_registration_details_nsig";
        }
        
        try{
            query = "INSERT INTO " + tab_name + "(application_id, fresh_renewal,domicile_state_id, applicant_name, aadhaar_no, gender, date_of_birth,email_id, mobile_no, application_level,created_by,hash_dob,pre_post_matric,permanent_state_id,ip_address,application_status,bank_name,enc_aadhaar,aadhaar_virtual_id,aadhaar_registration_mode,aadhaar_token_no,bank_account_no,branch_ifsc_code) VALUES('"+studentRegistrationId+"','F',"+student.getStateDomicile()+",'"+student.getStudentName()+"',aadhaar_mask('"+student.getAadharNumber()+"',12,4),'"+student.getGender()+"',to_date('"+student.getDateOfBirth()+"','dd/mm/yyyy'),'"+student.getEmailAddress()+"',"+student.getMobileNumber()+",-1,'"+studentRegistrationId+"','"+encript_passwoard.SHA512_MD5(student.getDateOfBirth())+"',"+student.getScholarshipCategory()+","+student.getStateDomicile()+",'"+ip+"','SU','"+student.getBankName()+"',sha512_md5('"+student.getAadharNumber()+"'),'"+student.getAadhaar_virtual_id()+"','"+student.getAadhaar_registration_mode()+"','"+student.getAadhaar_token_no()+"','"+student.getBankAcccountNO()+"','"+student.getIfscCode()+"')";
        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        map.put("submitRegistration",query);
        
        if (student.getSchemeType() == 1) {
            tab_name = "data_applicant_registration_warb";
        } else {
            tab_name = "data_applicant_registration_warb_nsig";
        }
                
        query ="insert into " + tab_name + "(application_id) values ('"+studentRegistrationId+"')";
        map.put("submitWarb",query);
        
        if (student.getSchemeType() == 1) {
            tab_name = "data_applicant_qualifications";
        } else {
            tab_name = "data_applicant_qualifications_nsig";
        }
                
        query ="insert into " + tab_name + "(application_id) values ('"+studentRegistrationId+"')";
        map.put("submitQualification",query);
        
        if (student.getSchemeType() == 1) {
           tab_name = "data_applicant_applied_schemes";
        } else {
            tab_name = "data_applicant_applied_schemes_nsig";
        }
                
        query ="insert into " + tab_name + "(application_id,scheme_id) values ('"+studentRegistrationId+"',0)";
        map.put("submitScheme",query);
        
        if (student.getEidOpt().equals("1")) {
            if (student.getSchemeType() == 1) {
                tab_name = "data_applicant_agency_verifications";
            } else {
                tab_name = "data_applicant_agency_verifications_nsig";
            }
            
            query ="insert into " + tab_name + "(application_id,applicant_name_in_aadhaar,aadhaar_verif_status,npci_aadhaar_no) values ('"+studentRegistrationId+"','"+student.getNameAsInAadhhar()+"','0','"+student.getAadharNumber()+"')";
            map.put("submitEid",query);
        }
        
        if (student.getSchemeType() == 1) {
            tab_name = "date_applicant_mobileno_verification";
        } else {
            tab_name = "date_applicant_mobileno_verification_nsig";
        }
        
        query ="insert into " + tab_name + "(is_validated_mobileno,mobile_no,application_id) values ('N','"+student.getMobileNumber()+"','"+studentRegistrationId+"')";
        map.put("submitMobile",query);
        JSONObject json = new JSONObject(map);
        
        return json.toString();
	}
	
	public String uploadFile(com.myspace.nsp_registration.Student student, String currentSession){
	    String studentRegistrationId = student.getStudentRegId();
	    fileUploadUtility fileup = new fileUploadUtility();
	    String stateshortName = studentRegistrationId.substring(0, 2);
        String destPath = "/home/bikash/nsp/bankProofUpload_"+currentSession+"/".concat(stateshortName);
        String studentidupload = studentRegistrationId.concat("_bank");
        String fileextension = "";
        
        if (student.getBankProofDocContentType().equalsIgnoreCase("application/pdf")) {
            fileextension = ".pdf";
        } else if (student.getBankProofDocContentType().equalsIgnoreCase("application/jpg")) {
            fileextension = ".jpg";
        } else {
            fileextension = ".jpeg";
        }
        
        String tab_name = "";
        String query = "";
        if (student.getSchemeType() == 1) {
            tab_name = "data_applicant_eid";
        } else {
            tab_name = "data_applicant_eid_nsig";
        }
            
        String[] uploadStatus = fileup.fileUploadToDirecotry(student.getBankProofDoc(), destPath, fileextension, studentidupload, stateshortName);
        if (uploadStatus[0].equals("true")) {    
            query ="insert into " + tab_name + "(application_id, eid_no, eid_date_time, doc_upload_date,doc_file_type,doc_path,original_name,identity_type) values ('"+studentRegistrationId+"','"+student.getIfscCode()+"','"+student.getBankAcccountNO()+"',now(),'"+student.getBankProofDocContentType()+"','"+uploadStatus[1]+"','"+student.getBankProofDocFileName()+"','"+student.getEidOpt()+"')";
        }
        
        if (!sqlInjection.isEmptyCheck(student.getEid()) && !sqlInjection.isEmptyCheck(student.getEiddateTime())) {
            if (student.getEidProofDocFileName() != null) {
                if (student.getEidProofDocFileName().length() > 0) {
                    String destPath_eid = "/home/bikash/nsp/eidProofUpload_"+currentSession+"/".concat(stateshortName);
                    String studentidupload_eid = studentRegistrationId.concat("_eid");
                    String fileextension_eid = "";
                    
                    if (student.getEidProofDocContentType().equalsIgnoreCase("application/pdf")) {
                        fileextension_eid = ".pdf";
                    } else if (student.getEidProofDocContentType().equalsIgnoreCase("application/jpg")) {
                        fileextension_eid = ".jpg";
                    } else {
                        fileextension_eid = ".jpeg";
                    }
                    
                    String[] uploadStatus_eid = fileup.fileUploadToDirecotry(student.getEidProofDoc(), destPath_eid, fileextension_eid, studentidupload_eid, stateshortName);
                    if (uploadStatus_eid[0].equals("true")) {
                        query ="insert into " + tab_name + "(application_id, eid_no, eid_date_time, doc_upload_date,doc_file_type,doc_path,original_name,identity_type) values ('"+studentRegistrationId+"','"+student.getEid()+"','"+student.getEiddateTime()+"',now(),'"+student.getEidProofDocContentType()+"','"+uploadStatus_eid[1]+"','"+student.getEidProofDocFileName()+"','2')";
                    }
                }
            }
        }
        return query;	    
	}
	public java.lang.String getFieldError() {
		return this.fieldError;
	}

	public void setFieldError(java.lang.String fieldError) {
		this.fieldError = fieldError;
	}

	public StudentRegAction() {
	}

	public StudentRegAction(java.lang.String fieldError) {
		this.fieldError = fieldError;
	}

}
