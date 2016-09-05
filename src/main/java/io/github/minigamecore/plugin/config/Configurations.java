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
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static org.spongepowered.api.Sponge.getAssetManager;
import static org.spongepowered.api.Sponge.getPluginManager;

import io.github.minigamecore.api.util.config.Configuration;
import io.github.minigamecore.plugin.MinigameCore;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Util class for handling all the MinigameCore specific configurations.
 */
public final class Configurations {

    private static Map<String, Configuration> configMap = new HashMap<>();

    private Configurations() {
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static void register() {
        MinigameCore plugin = (MinigameCore) getPluginManager().getPlugin("minigamecore").get().getInstance().get();

        addConfig("global", new Configuration(plugin.getConfigDir().resolve("global.conf"), getAssetManager().getAsset(plugin, "config/global.conf")
                .get()));
    }

    private static void addConfig(String key, Configuration config) {
        checkNotNull(key, "key");
        checkNotNull(config, "configuration");

        configMap.put(key, config);
    }

    public static Optional<Configuration> get(String key) {
        return configMap.containsKey(key) ? of(configMap.get(key)) : empty();
    }

    public static List<Configuration> getAll() {
        return configMap.entrySet().stream().map(Map.Entry::getValue).collect(toList());
    }

    // Special case for global.conf.
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static void loadGlobal(Logger logger) {
        try {
            get("global").get().load();
        } catch (IOException e) {
            logger.error("Failed to load global configuration. This is going to be a problem!", e);
        }
    }

    public static void saveGlobal(Logger logger) {
        try {
            Configurations.get("global").get().save();
        } catch (IOException e) {
            logger.error("Failed to save global configuration.", e);
        }
    }

}
