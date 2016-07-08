/**
 * This file is part of MinigameCore, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2016 - 2016 MinigameCore
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
package io.github.minigamecore.plugin.internal.impl.service;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import io.github.minigamecore.api.MinigameService;
import io.github.minigamecore.plugin.MinigameCore;

import javax.annotation.Nonnull;

/**
 * The implementation for {@link MinigameService}
 */
@Singleton
public final class MinigameServiceImpl implements MinigameService {

    private final MinigameCore plugin;
    private Injector injector;

    @SuppressWarnings("deprecation")
    @Inject
    private MinigameServiceImpl(MinigameCore plugin) {
        this.plugin = plugin;
        this.injector = plugin.getDefaultInjector();
    }

    @Nonnull
    @Override
    public Injector getInjector() {
        return injector;
    }

    public void provideMapping(Injector injector) {
        this.injector = injector;
    }

}
