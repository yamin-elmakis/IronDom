package IronDome.Utils;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class IronDomeFormatter extends Formatter{

	private final int BUFFER_SIZE = 264;
	@Override
	public String format(LogRecord record) {
		StringBuffer buffer = new StringBuffer(BUFFER_SIZE);
		
		buffer.append(new Date().toLocaleString());
		buffer.append("\n");
		buffer.append(record.getMessage());
		buffer.append("\n-------------------------------------\n");
		return buffer.toString();
	}

}
