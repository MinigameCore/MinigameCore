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

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.ImmutableList.copyOf;

import com.google.common.base.Objects;
import io.github.minigamecore.api.spawnpoint.ImmutableSpawnpoint;
import io.github.minigamecore.api.spawnpoint.Spawnpoint;
import io.github.minigamecore.api.spawnpoint.spawnpointtype.SpawnpointType;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.world.extent.Extent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.annotation.Nonnull;

/*
 * Implementation of ImmutableSpawnpoint.
 */
public class ImmutableSpawnpointImpl implements ImmutableSpawnpoint {

    private final UUID uuid;
    private final boolean active;
    private final SpawnpointType spawnpointType;
    private final Collection<Team> teams;
    private final Transform<? extends Extent> transform;

    ImmutableSpawnpointImpl(boolean active, SpawnpointType spawnpointType, Collection<Team> teams,
            Transform<? extends Extent> transform, UUID uuid) {
        this.active = active;
        this.spawnpointType = spawnpointType;
        this.teams = teams;
        this.transform = transform;
        this.uuid = uuid;
    }


    @Nonnull
    @Override
    public Transform<? extends Extent> getTransform() {
        return new Transform<>(transform.getLocation(), transform.getRotation(), transform.getScale());
    }

    @Nonnull
    @Override
    public SpawnpointType getSpawnpointType() {
        return spawnpointType;
    }

    @Nonnull
    @Override
    public Collection<Team> getTeams() {
        return copyOf(teams);
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(active, spawnpointType, teams, transform, uuid);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("active", active)
                .add("spawnpointType", spawnpointType)
                .add("teams", teams)
                .add("transform", transform)
                .add("uuid", uuid)
                .toString();
    }

    @Nonnull
    @Override
    public Spawnpoint<? extends Extent> asMutable() {
        return new SpawnpointImpl<>(active, spawnpointType, teams, transform, uuid);
    }

    /*
     * Implementation of ImmutableSpawnpoint.Builder.
     */
    public static class BuilderImpl implements Builder {

        private boolean active = false;
        private SpawnpointType spawnpointType = null;
        private Transform<? extends Extent> transform = null;
        private UUID uuid = UUID.randomUUID();
        private Collection<Team> teams = new ArrayList<>();

        @Override
        public BuilderImpl active(boolean active) {
            checkNotNull(active, "active");

            this.active = active;

            return this;
        }

        @Override
        public ImmutableSpawnpoint build() throws IllegalArgumentException {
            try {
                checkNotNull(active, "active");
                checkNotNull(spawnpointType, "spawnpoint");
                checkNotNull(teams, "teams");
                checkNotNull(transform, "transform");
                checkNotNull(uuid, "uuid");
            } catch (NullPointerException e) {
                throw new IllegalArgumentException(e);
            }

            return new ImmutableSpawnpointImpl(active, spawnpointType, teams, transform, uuid);
        }

        @Override
        public BuilderImpl spawnpointType(@Nonnull SpawnpointType spawnpointType) {
            checkNotNull(spawnpointType, "spawnpoint type");

            this.spawnpointType = spawnpointType;

            return this;
        }

        @Override
        public BuilderImpl teams(@Nonnull Collection<Team> teams) {
            checkNotNull(teams, "teams");

            this.teams = teams;

            return this;
        }

        @Override
        public <E extends Extent> BuilderImpl transform(@Nonnull Transform<E> transform) {
            checkNotNull(transform, "transform");

            this.transform = transform;

            return this;
        }

        /*
         * Only needed for deserializing.
         */
        public BuilderImpl uuid(@Nonnull UUID uuid) {
            checkNotNull(uuid, "uuid");

            this.uuid = uuid;

            return this;
        }

        @Override
        public BuilderImpl from(ImmutableSpawnpoint value) {
            checkNotNull(value, "immutable spawnpoint");

            active(value.isActive());
            spawnpointType(value.getSpawnpointType());
            teams(value.getTeams());
            transform(value.getTransform());
            uuid(value.getUniqueId());

            return this;
        }

        @Override
        public BuilderImpl reset() {

            active = false;
            spawnpointType = null;
            teams.clear();
            transform = null;
            uuid = UUID.randomUUID();

            return this;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(active, spawnpointType, transform);
        }

        @Override
        public String toString() {
            return toStringHelper(this)
                    .add("active", active)
                    .add("spawnpointType", spawnpointType)
                    .add("teams", teams)
                    .add("transform", transform)
                    .add("uuid", uuid)
                    .toString();
        }

    }

}
