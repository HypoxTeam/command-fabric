package team.hypox.commands;

import co.aikar.commands.FabricCommandManager;
import co.aikar.commands.LoggerProvider;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFabric implements LoggerProvider {

	@Test
	public void onTest() throws CommandSyntaxException {
		CommandDispatcher<ServerCommandSource> dispatcher = new CommandDispatcher<>();
		FabricCommandManager commandManager = new FabricCommandManager(dispatcher, this);
		commandManager.getCommandContexts()
				.registerContext(ArgTest.class, ctx -> ArgTest.valueOf(ctx.popFirstArg().toUpperCase()));
		commandManager.registerCommand(new TestFabricCommand());

		MockitoCommandSource source = new MockitoCommandSource(invoke());
		dispatcher.execute("test fabric", source);
		dispatcher.execute("test help", source);
		dispatcher.execute("test asdd", source);
		dispatcher.execute("test one two", source);
		dispatcher.execute("test colorize XD", source);

		Assertions.assertSame("HOLAAAA", source.getLastMessageReceived());
	}

	@Override
	public Logger invoke() {
		return LoggerFactory.getLogger("Test-Logger");
	}
}
