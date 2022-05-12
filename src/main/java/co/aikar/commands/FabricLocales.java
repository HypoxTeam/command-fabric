package co.aikar.commands;

public class FabricLocales extends Locales {

	private final FabricCommandManager manager;

	public FabricLocales(FabricCommandManager manager) {
		super(manager);
		this.manager = manager;
	}

	@Override
	public void loadLanguages() {
		super.loadLanguages();
	}
}
