package IronDome.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class TzoukEitanConsoleFormatter extends Formatter{

	private final int BUFFER_SIZE = 264;
	
	@Override
	public String format(LogRecord record) {
        LocalDateTime now = LocalDateTime.now();
        
        StringBuffer buffer = new StringBuffer(BUFFER_SIZE);
		buffer.append(now.format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm:ss")));
		buffer.append(": ");
		buffer.append(record.getMessage());
		buffer.append("\n-------------------------------------\n");
		return buffer.toString();
	}

}
