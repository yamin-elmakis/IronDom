package IronDome.Utils;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class TzoukEitanLogFilter implements Filter {

	private Object filtered;
	
	public TzoukEitanLogFilter(Object filtered) {
		this.filtered = filtered;
	}

	@Override
	public boolean isLoggable(LogRecord record) {
		if (record.getParameters() != null) {
			Object[] parameters = record.getParameters();
			for (Object object : parameters) 
				if (filtered == object)
					return true;
		}
		return false;
	}
}
