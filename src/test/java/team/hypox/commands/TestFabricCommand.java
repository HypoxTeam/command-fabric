package team.hypox.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.Texts;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import net.minecraft.server.command.ServerCommandSource;

@CommandAlias("test")
public class TestFabricCommand extends BaseCommand {

	@Default
	@HelpCommand
	public void help() {
		System.out.println("Help!");
	}

	@Subcommand("one two")
	public void testSubCommandDouble(ServerCommandSource sender) {
		System.out.println("two subcommands");
	}

	@Subcommand("fabric")
	public void testSubCommand(ServerCommandSource sender) {
		sender.sendFeedback(Texts.from("HOLAAAA"), false);
	}

}
