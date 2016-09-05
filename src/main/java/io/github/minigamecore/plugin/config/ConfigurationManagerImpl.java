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

package io.github.minigamecore.plugin.config;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.spongepowered.api.Sponge.getPluginManager;

import io.github.minigamecore.api.util.config.Configuration;
import io.github.minigamecore.api.util.config.ConfigurationManager;
import io.github.minigamecore.plugin.util.logger.MinigameCoreLogger;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ConfigurationManagerImpl implements ConfigurationManager {

    private final Logger logger = new MinigameCoreLogger("ConfigurationManager");
    private final Map<Object, List<Configuration>> configMap = new HashMap<>();

    @Override
    public void register(final Object plugin, final Configuration config) {
        checkNotNull(plugin, "plugin");
        checkNotNull(config, "configuration");

        getPluginManager().fromInstance(plugin).ifPresent(pluginContainer ->  {
            if (configMap.containsKey(plugin)) {
                configMap.get(plugin).add(config);
            } else {
                List<Configuration> c = new ArrayList<>();
                c.add(config);
                configMap.put(plugin, c);
            }
        });
    }

    @Override
    public void load(final Object plugin) {
        checkNotNull(plugin, "plugin");

        if (configMap.containsKey(plugin)) {
            configMap.get(plugin).forEach((config -> {
                try {
                    config.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        }
    }

    @Override
    public void save(final Object plugin) {
        checkNotNull(plugin, "plugin");

        if (configMap.containsKey(plugin)) {
            configMap.get(plugin).forEach(config -> {
                try {
                    config.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Loads all configurations.
     */
    public void loadAllConfigurations() {
        configMap.forEach((plugin, configs) -> configs.forEach(config -> {
            try {
                config.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    /**
     * Saves all configurations.
     */
    public void saveAllConfigurations() {
        configMap.forEach((plugin, configs) -> configs.forEach(config -> {
            try {
                config.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

}
