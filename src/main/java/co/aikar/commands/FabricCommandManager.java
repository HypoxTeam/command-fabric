package co.aikar.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FabricCommandManager extends CommandManager<
		ServerCommandSource,
		FabricCommandIssuer,
		Formatting,
		FabricMessageFormatter,
		FabricCommandExecutionContext,
		FabricConditionContext
		> {

	private FabricCommandContexts contexts;
	private FabricCommandCompletions completions;
	private FabricLocales locales;

	protected final Map<String, FabricRootCommand> registeredCommands = new HashMap<>();
	private final CommandDispatcher<ServerCommandSource> dispatcher;
	private final Logger logger;

	public FabricCommandManager(CommandDispatcher<ServerCommandSource> dispatcher, LoggerProvider loggerProvider) {
		this.dispatcher = dispatcher;
		this.logger = loggerProvider.invoke();
	}

	@Override
	public CommandContexts<FabricCommandExecutionContext> getCommandContexts() {
		if (contexts == null) {
			contexts = new FabricCommandContexts(this);
		}

		return contexts;
	}

	@Override
	public CommandCompletions<FabricCommandCompletionContext> getCommandCompletions() {
		if (this.completions == null) {
			this.completions = new FabricCommandCompletions(this);
		}

		return completions;
	}

	@Override
	public void registerCommand(BaseCommand command) {
		command.onRegister(this);

		dispatcher.register(
				CommandAdapter.from(command)
		);

		for (Entry<String, RootCommand> entry : command.registeredCommands.entrySet()) {
			FabricRootCommand fabricCommand = (FabricRootCommand) entry.getValue();

			if (!fabricCommand.isRegistered) {
				fabricCommand.isRegistered = true;
				registeredCommands.put(entry.getKey(), fabricCommand);
			}
		}
	}

	@Override
	public boolean hasRegisteredCommands() {
		return !registeredCommands.isEmpty();
	}

	@Override
	public boolean isCommandIssuer(Class<?> type) {
		return ServerCommandSource.class.isAssignableFrom(type);
	}

	@Override
	public FabricCommandIssuer getCommandIssuer(Object issuer) {
		if (!(issuer instanceof ServerCommandSource)) {
			throw new IllegalArgumentException(issuer.getClass().getName() + " is not a Command Issuer.");
		}

		return new FabricCommandIssuer(this, (ServerCommandSource) issuer);
	}

	@Override
	public RootCommand createRootCommand(String cmd) {
		return new FabricRootCommand(this, cmd);
	}

	@Override
	public Locales getLocales() {
		if (this.locales == null) {
			this.locales = new FabricLocales(this);
			this.locales.loadLanguages();
		}

		return locales;
	}

	@Override
	public FabricCommandExecutionContext createCommandContext(RegisteredCommand command, CommandParameter parameter, CommandIssuer sender, List<String> args, int i, Map<String, Object> passedArgs) {
		return new FabricCommandExecutionContext(command, parameter, (FabricCommandIssuer) sender, args, i, passedArgs);
	}

	@Override
	public FabricCommandCompletionContext createCompletionContext(RegisteredCommand command, CommandIssuer sender, String input, String config, String[] args) {
		return new FabricCommandCompletionContext(command, (FabricCommandIssuer) sender, input, config, args);
	}

	@Override
	public void log(LogLevel level, String message, Throwable throwable) {
		if (throwable != null) {
			logger.error(message, throwable);
		} else {
			logger.info(message, level);
		}
	}

	@Override
	public FabricConditionContext createConditionContext(CommandIssuer issuer, String config) {
		return new FabricConditionContext((FabricCommandIssuer) issuer, config);
	}

	@Override
	public Collection<RootCommand> getRegisteredRootCommands() {
		return Collections.unmodifiableCollection(registeredCommands.values());
	}

	@Override
	public String getCommandPrefix(CommandIssuer issuer) {
		return issuer.isPlayer() ? "/" : "";
	}
}
