/*
 * This file is part of MinigameCore, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2016 - 2016 MinigameCore <http://minigamecore.github.io>
 * Copyright (c) Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.minigamecore.plugin;

import static io.github.minigamecore.plugin.config.Configurations.getAll;
import static org.spongepowered.api.Sponge.getServiceManager;
import static org.spongepowered.api.event.Order.EARLY;
import static org.spongepowered.api.event.Order.LATE;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import io.github.minigamecore.api.MinigameService;
import io.github.minigamecore.api.util.config.ConfigurationManager;
import io.github.minigamecore.plugin.config.ConfigModule;
import io.github.minigamecore.plugin.config.ConfigurationManagerImpl;
import io.github.minigamecore.plugin.config.Configurations;
import io.github.minigamecore.plugin.service.MinigameServiceImpl;
import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.nio.file.Path;

/*
 * The main class for MinigameCore.
 */
@Plugin(authors = {"Flibio", "KingGoesGaming"}, id = "minigamecore", url = "http://minigamecore.github.io/")
public final class MinigameCore {

    private final Path configDir;
    private Injector defaultInjector;
    private final Logger logger;
    private final PluginContainer pluginContainer;

    @Inject
    private MinigameCore(@ConfigDir(sharedRoot = false) Path configDir, Injector defaultInjector, Logger logger, PluginContainer pluginContainer) {
        this.configDir = configDir;
        this.defaultInjector = defaultInjector;
        this.logger = logger;
        this.pluginContainer = pluginContainer;
    }

    @Listener(order = EARLY)
    public void onPreInitializationEarly(final GamePreInitializationEvent event) {
        getLogger().info("Starting " + getPluginContainer().getId());

        Configurations.register();

        // Setup minigameservice
        Module module = binder -> {
            binder.bind(ConfigurationManager.class).to(ConfigurationManagerImpl.class);
            binder.bind(MinigameService.class).to(MinigameServiceImpl.class);
            binder.install(new ConfigModule());
        };

        defaultInjector = defaultInjector.createChildInjector(module);
        getServiceManager().setProvider(this, MinigameService.class, defaultInjector.getInstance(MinigameService.class));

        getAll().forEach((configuration -> defaultInjector.getInstance(ConfigurationManager.class).register(this, configuration)));

        ((ConfigurationManagerImpl) defaultInjector.getInstance(ConfigurationManager.class)).loadAllConfigurations();
    }

    @Listener(order = LATE)
    public void onPreInitLate(final GamePreInitializationEvent event) {
        ((ConfigurationManagerImpl) defaultInjector.getInstance(ConfigurationManager.class)).saveAllConfigurations();
    }

    @Listener(order = LATE)
    public void onStoppingLate(final GameStoppingEvent event) {
        ((ConfigurationManagerImpl) getServiceManager().provideUnchecked(MinigameService.class).getConfigurationManager()).saveAllConfigurations();
    }

    // TODO move this to another class later
    @Listener
    public void onReload(final GameReloadEvent event) {
        ((ConfigurationManagerImpl) defaultInjector.getInstance(ConfigurationManager.class)).loadAllConfigurations();
    }

    public Path getConfigDir() {
        return configDir;
    }

    public Logger getLogger() {
        return logger;
    }

    public PluginContainer getPluginContainer() {
        return pluginContainer;
    }

}
