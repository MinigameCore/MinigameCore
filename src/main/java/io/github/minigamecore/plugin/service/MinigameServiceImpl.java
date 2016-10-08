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

package io.github.minigamecore.plugin.service;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.github.minigamecore.api.MinigameService;
import io.github.minigamecore.api.util.config.ConfigurationManager;
import io.github.minigamecore.api.util.manager.GuiceManager;
import io.github.minigamecore.plugin.util.logger.MinigameCoreLogger;
import org.slf4j.Logger;

import javax.annotation.Nonnull;

/*
 * The implementation for MinigameService.
 */
@Singleton
public final class MinigameServiceImpl implements MinigameService {

    private Injector injector;
    private final ConfigurationManager configManager;
    private final GuiceManager guiceManager;
    private final Logger logger = new MinigameCoreLogger("MinigameService");

    @Inject
    private MinigameServiceImpl(ConfigurationManager configManager, GuiceManager guiceManager) {
        this.configManager = configManager;
        this.guiceManager = guiceManager;
    }

    @Nonnull
    @Override
    public ConfigurationManager getConfigurationManager() {
        return configManager;
    }

    @Nonnull
    @Override
    public GuiceManager getGuiceManager() {
        return guiceManager;
    }


}
