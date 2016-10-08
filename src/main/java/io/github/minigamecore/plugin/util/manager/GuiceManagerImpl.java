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
