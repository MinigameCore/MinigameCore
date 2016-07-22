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

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;
import io.github.minigamecore.api.MinigameService;
import io.github.minigamecore.plugin.MinigameCore;
import org.slf4j.Logger;

import javax.annotation.Nonnull;

/*
 * The implementation for MinigameService.
 */
@Singleton // Only one instance should be available
public final class MinigameServiceImpl implements MinigameService {

    private final Logger logger; // TODO Will work on this when we get some configuration stuff done
    private Injector injector;

    @SuppressWarnings("deprecation")
    @Inject
    private MinigameServiceImpl(MinigameCore plugin) {
        logger = plugin.getLogger();
        injector = plugin.getDefaultInjector();
    }

    @Nonnull
    @Override
    public Injector getInjector() {
        return injector;
    }

    @Override
    public void registerChildInjector(@Nonnull Module module) {
        checkNotNull(module, "module");

        injector = injector.createChildInjector(module);
    }

    @Override
    public void registerChildInjector(Module[] modules) {
        checkNotNull(modules, "module");

        injector = injector.createChildInjector(modules);
    }

}
