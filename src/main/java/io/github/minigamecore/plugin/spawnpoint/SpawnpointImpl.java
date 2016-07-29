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

package io.github.minigamecore.plugin.spawnpoint;

import static com.google.common.base.Preconditions.checkNotNull;

import io.github.minigamecore.api.spawnpoint.ImmutableSpawnpoint;
import io.github.minigamecore.api.spawnpoint.Spawnpoint;
import io.github.minigamecore.api.spawnpoint.spawnpointtype.SpawnpointType;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.world.extent.Extent;

import java.util.Collection;
import java.util.UUID;

import javax.annotation.Nonnull;

/*
 * Implementatation of
 */
public class SpawnpointImpl<E extends Extent> implements Spawnpoint<E> {

    private boolean active;
    private SpawnpointType spawnpointType;
    private Collection<Team> teams;
    private Transform<E> transform;
    private UUID uuid;

    SpawnpointImpl(boolean active, SpawnpointType spawnpointType, Collection<Team> teams, Transform<E> transform, UUID uuid) {
        this.active = active;
        this.spawnpointType = spawnpointType;
        this.teams = teams;
        this.transform = transform;
        this.uuid = uuid;
    }

    @Nonnull
    @Override
    public ImmutableSpawnpoint asImmutable() {
        return new ImmutableSpawnpointImpl(active, spawnpointType, teams, transform, uuid);
    }

    @Nonnull
    @Override
    public SpawnpointType getSpawnpointType() {
        return spawnpointType;
    }

    @Nonnull
    @Override
    public Collection<Team> getTeams() {
        return teams;
    }

    @Nonnull
    @Override
    public Transform<E> getTransform() {
        return transform;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        checkNotNull(active, "active");

        this.active = active;
    }

    @Override
    public void setSpawnpointType(@Nonnull SpawnpointType spawnpointType) {
        checkNotNull(spawnpointType, "spawnpoint type");

        this.spawnpointType = spawnpointType;
    }

    @Override
    public void setTeams(@Nonnull Collection<Team> teams) {
        checkNotNull(teams, "teams");

        this.teams = teams;
    }

    @Override
    public void setTransform(Transform<E> transform) {
        checkNotNull(transform, "transform");

        this.transform = transform;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

}
