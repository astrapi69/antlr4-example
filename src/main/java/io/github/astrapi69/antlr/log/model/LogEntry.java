package io.github.astrapi69.antlr.log.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LogEntry
{

	LogLevel level;
	String message;
	LocalDateTime timestamp;

}
