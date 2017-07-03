package core;

import java.math.BigDecimal;
import java.util.ListResourceBundle;

public class Another_es_CO extends ListResourceBundle{

	@Override
	protected Object[][] getContents() {
		
		return new Object[][] {
			{ "hello", "Hablate" },
			{ "open", "El Zoo esta abierto" },
			
			{"object", new BigDecimal("1.3") }
			
		
		};
	}
	
	
}