package pl.nordea.synchro.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionFormatter {
	public static String printError(Throwable ex){
		StringWriter errors= new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}
}
