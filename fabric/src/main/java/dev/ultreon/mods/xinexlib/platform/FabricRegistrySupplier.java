package dev.ultreon.mods.xinexlib.platform;

import com.mojang.datafixers.util.Either;
import dev.ultreon.mods.xinexlib.platform.services.IRegistrar;
import dev.ultreon.mods.xinexlib.platform.services.IRegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class FabricRegistrySupplier<R extends T, T> implements IRegistrySupplier<R, T> {
    private final IRegistrar<T> registry;
    private final ResourceKey<R> key;
    protected R value;

    protected FabricRegistrySupplier(IRegistrar<T> registrar, ResourceKey<R> key) {
        this.registry = registrar;
        this.key = key;
    }

    @Override
    public Optional<R> asOptional() {
        return Optional.ofNullable(value);
    }

    @Override
    public ResourceLocation getId() {
        return key.location();
    }

    @Override
    public Registry<T> registry() {
        return registry.registry();
    }

    @Override
    public @NotNull R value() {
        return asOptional().orElseThrow(() -> new IllegalStateException("Value " + getId() + " in registry " + registry().key().location() + " is not bound!"));
    }

    @Override
    public boolean isBound() {
        return true;
    }

    @Override
    public boolean is(ResourceLocation location) {
        return this.key.location().equals(location);
    }

    @Override
    public boolean is(ResourceKey<R> resourceKey) {
        return this.key.equals(resourceKey);
    }

    @Override
    public boolean is(Predicate<ResourceKey<R>> predicate) {
        return predicate.test(this.key);
    }

    @Override
    public boolean is(TagKey<R> tagKey) {
        return false;
    }

    @Override
    public boolean is(Holder<R> holder) {
        return false;
    }

    @Override
    public @NotNull Stream<TagKey<R>> tags() {
        return Stream.empty();
    }

    @Override
    public @NotNull Either<ResourceKey<R>, R> unwrap() {
        return Either.right(value);
    }

    @Override
    public Optional<ResourceKey<R>> unwrapKey() {
        return Optional.of(key);
    }

    @Override
    public @NotNull Kind kind() {
        return Kind.DIRECT;
    }

    @Override
    public boolean canSerializeIn(HolderOwner<R> owner) {
        return owner instanceof Registry;
    }

    protected abstract void register();
}
