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

package io.github.minigamecore.plugin.spawnpoint.spawnpointtype.player;

import static java.util.Collections.singletonList;
import static org.spongepowered.api.entity.EntityTypes.PLAYER;

import io.github.minigamecore.api.spawnpoint.spawnpointtype.SpawnpointType;
import org.spongepowered.api.entity.EntityType;

import java.util.Collection;

import javax.annotation.Nonnull;

/**
 *
 */
abstract class PlayerSpawnpointType implements SpawnpointType {

    private Collection<EntityType> entityTypes = singletonList(PLAYER);
    protected final String id;
    protected final String name;

    protected PlayerSpawnpointType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Nonnull
    @Override
    public final Collection<EntityType> getApplicableEntityTypes() {
        return entityTypes;
    }

    @Override
    public final String getId() {
        return id;
    }

    @Override
    public final String getName() {
        return name;
    }
}
