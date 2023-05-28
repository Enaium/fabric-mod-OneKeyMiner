package cn.enaium.onekeyminer;

import cn.enaium.onekeyminer.command.ScreenCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

/**
 * @author Enaium
 */
public class ClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ScreenCommand.register(dispatcher);
        });
    }
}
