package pl.nordea.synchro.controller;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org. 	springframework.web.servlet.ModelAndView;

import pl.nordea.synchro.core.ISynchroActions;
import pl.nordea.synchro.utils.AppProps;
import pl.nordea.synchro.utils.IAppPropsService;
import pl.nordea.synchro.utils.IDirectoryXmlService;
import pl.nordea.synchro.utils.SynchroPropsService;
import pl.nordea.synchro.utils.model.enties.SynchroProperties;

@Controller
public class SynchroMainPage {
	private static final Logger logger= LoggerFactory.getLogger(SynchroMainPage.class);
	@Autowired
	private SynchroPropsService synchroPropsService;
	@Autowired
	private IAppPropsService appProps;
	
	@Autowired
	private FileValidator fileValidator;
	@Autowired
	private IDirectoryXmlService dirService;
	@Autowired
	ISynchroActions synchroActions;

	@RequestMapping(value="/config", method=RequestMethod.GET)
	public String setupForm(Map<String, Object> map) {
		logger.info("::: SETUP_FORM ::: ");
		SynchroProperties prop = new SynchroProperties();
		map.put("prop", prop);
		map.put("propList", synchroPropsService.getAllSynchroProperties(AppProps.SYNCHRO_CONFIG));
		//ModelAndView mav = new ModelAndView();
		
		return "index";
	}
	
	@RequestMapping(value="/config", method=RequestMethod.POST)
	public String doActions(@ModelAttribute SynchroProperties prop, BindingResult result, @RequestParam String action, Map<String, Object> map) {
		SynchroProperties propResult = new SynchroProperties();
		logger.debug("::: DO_ACTIONS : "+action +" :::");
		if (action.equals("add")) {	
			synchroPropsService.add(prop);
			propResult=new SynchroProperties();
			logger.debug("::: add : "+action +" :::");
			//propResult=prop;
		}else if (action.equals("edit")) {
			synchroPropsService.edit(prop);
			propResult=prop;
			logger.debug("::: edit : "+action +" :::");
		}else if (action.equals("delete")) {
			synchroPropsService.delete(prop.getId());
			propResult=new SynchroProperties();
		}else if (action.equals("search")) {
			SynchroProperties searchedProps= synchroPropsService.getSynchroProperties(prop.getId());
			
		/*	if(searchedProps.getValue()!=null && searchedProps.getName().contains("passw")) {
				logger.info("GET Value before decrypt:"+searchedProps.getValue());
				searchedProps.setValue(decrypt(searchedProps.getValue()));
				logger.info("GET Value after decrypt:"+searchedProps.getValue());
			}*/
			
			propResult=searchedProps!=null?searchedProps:new SynchroProperties();  ;
		}else if (action.equals("run")) {
			synchroActions.startSynchronisationProcess();
		}
		map.put("prop", propResult);
		map.put("propList", synchroPropsService.getAllSynchroProperties(AppProps.SYNCHRO_CONFIG));
		try {
			appProps.loadProperties();
		}catch (Exception e) {
			logger.error("::Exception:"+e.getMessage());
		}
		return "index";
	}
	
	@RequestMapping(value="/runSynchro")
	public String manualRunSynchro(@RequestParam("countryId") Integer countryId, Map<String, Object> map ) {
		SynchroProperties propResult = new SynchroProperties();
		logger.debug("::: RUN_SYNCHRO ::::");
			synchroActions.startSynchronisationProcess(countryId.intValue());
		map.put("prop", propResult);
		map.put("propList", synchroPropsService.getAllSynchroProperties(AppProps.SYNCHRO_CONFIG));
		return "index";
	}
	
	
	
	@RequestMapping("/fileUploadForm")  
	 public ModelAndView getUploadForm(@ModelAttribute("uploadedFile") UploadedFile uploadedFile,BindingResult result) {  
		logger.info("             getUploadForm  START");
	  return new ModelAndView("uploadForm");  
	 }  
	  
	@RequestMapping("/fileUpload")
	public ModelAndView fileUploaded(
			@ModelAttribute("uploadedFile") UploadedFile uploadedFile,
			BindingResult result) {
		logger.info("             fileUploaded  START");
		MultipartFile file = uploadedFile.getFile();
		fileValidator.validate(uploadedFile, result);

		String fileName = file.getOriginalFilename();

		if (result.hasErrors()) {
			return new ModelAndView("index");
		}

		try {
			file.getInputStream();
			dirService.insertDirectoryConfig(file);

		} catch (IOException e) {
			e.printStackTrace();
			return new ModelAndView("index");
		}

		return new ModelAndView("showFile", "message", fileName);
	}
}
