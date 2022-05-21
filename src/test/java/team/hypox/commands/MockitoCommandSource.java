package team.hypox.commands;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import org.slf4j.Logger;

public class MockitoCommandSource extends ServerCommandSource {

	private final Logger logger;
	private String lastMessageReceived;

	public MockitoCommandSource(Logger logger) {
		super(null, null, null, null, 0, null, null, null, null);
		this.logger = logger;
	}

	@Override
	public String getName() {
		return "MOCKITO";
	}

	@Override
	public void sendFeedback(Text message, boolean broadcastToOps) {
		System.out.println(message.asString());
		lastMessageReceived = message.asString();
	}

	public String getLastMessageReceived() {
		return lastMessageReceived;
	}
}
