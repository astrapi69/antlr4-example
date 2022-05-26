package io.github.astrapi69;

import io.github.astrapi69.antlr.generated.LogLexer;
import io.github.astrapi69.antlr.generated.LogParser;
import io.github.astrapi69.antlr.log.LogListener;
import io.github.astrapi69.antlr.log.model.LogEntry;
import io.github.astrapi69.antlr.log.model.LogLevel;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LogParserUnitTest
{

	@Test
	public void testTokinizeLogMessages() throws Exception
	{
		StringBuilder sb = new StringBuilder();
		sb.append("2022-May-25 15:21:21 INFO you are /** foo bar */ entering a method"
			+ System.lineSeparator())
			.append("2022-May-25 15:21:24 ERROR An unexpected /** foo bar */error occured"
				+ System.lineSeparator());
		LogLexer serverLogLexer = new LogLexer(CharStreams.fromString(sb.toString()));
		CommonTokenStream tokens = new CommonTokenStream(serverLogLexer);
		LogParser logParser = new LogParser(tokens);
		ParseTreeWalker walker = new ParseTreeWalker();
		LogListener logWalker = new LogListener();
		walker.walk(logWalker, logParser.log());

		assertThat(logWalker.getEntries().size(), is(2));
		LogEntry error = logWalker.getEntries().get(1);
		assertThat(error.getLevel(), is(LogLevel.ERROR));
		assertThat(error.getMessage(), is("An unexpected error occured"));
		assertThat(error.getTimestamp(), is(LocalDateTime.of(2022, 5, 25, 15, 21, 24)));
	}
}
