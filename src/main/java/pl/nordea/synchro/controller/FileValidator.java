package pl.nordea.synchro.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;  
import org.springframework.validation.Validator;  

@Component
@Scope("prototype")
public class FileValidator implements Validator {  
  
 @Override  
 public boolean supports(Class<?> arg0) {  
  // TODO Auto-generated method stub  
  return false;  
 }  
  
 @Override  
 public void validate(Object uploadedFile, Errors errors) {  
  
  UploadedFile file = (UploadedFile) uploadedFile;  
  
  if (file!=null && file.getFile().getSize() == 0) {  
   errors.rejectValue("file", "uploadForm.salectFile",  
     "Please select a file!");  
  }  
 }
}  
