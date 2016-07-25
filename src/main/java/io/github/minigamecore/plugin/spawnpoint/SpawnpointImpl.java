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
