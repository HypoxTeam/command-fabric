package team.hypox.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.FabricCommandManager;
import co.aikar.commands.LoggerProvider;
import co.aikar.commands.Texts;
import co.aikar.commands.annotation.CommandAlias;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFabricCommand implements LoggerProvider {

	void test() {
		CommandDispatcher<ServerCommandSource> dispatcher = new CommandDispatcher<>();

		FabricCommandManager commandManager = new FabricCommandManager(dispatcher, this);
		commandManager.registerCommand(new TestCommandExample());
	}

	@CommandAlias("test")
	class TestCommandExample extends BaseCommand {
		public void testCommand(PlayerEntity entity) {
			entity.sendMessage(Texts.from("hello"), true);
		}
	}

	@Override
	public Logger invoke() {
		return LoggerFactory.getLogger("TEST");
	}
}
