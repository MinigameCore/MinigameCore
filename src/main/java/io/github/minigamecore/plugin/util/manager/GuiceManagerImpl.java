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

package io.github.minigamecore.plugin.util.manager;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;
import io.github.minigamecore.api.util.manager.GuiceManager;

import javax.annotation.Nonnull;

/**
 * .
 */
@Singleton
public final class GuiceManagerImpl implements GuiceManager {

    private Injector injector;

    @Nonnull
    @Override public Injector getInjector() {
        return injector;
    }

    @Override
    public void registerChildInjector(@Nonnull Module module) {
        checkNotNull(module, "module");
        injector = injector.createChildInjector(module);
    }

    @Override
    public void registerChildInjector(@Nonnull Module[] modules) {
        checkNotNull(modules, "modules");
        injector = injector.createChildInjector(modules);
    }

    /*
     * Special case for first time initialization.
     */
    public void setInjector(@Nonnull Injector injector) {
        this.injector = injector;
    }

}
