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
