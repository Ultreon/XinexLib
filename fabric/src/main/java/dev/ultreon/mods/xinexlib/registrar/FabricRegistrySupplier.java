package dev.ultreon.mods.xinexlib.registrar;

import com.mojang.datafixers.util.Either;
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
    public boolean is(ResourceKey<T> resourceKey) {
        return this.key.equals(resourceKey);
    }

    @Override
    public boolean is(Predicate<ResourceKey<T>> predicate) {
        return predicate.test((ResourceKey<T>) this.key);
    }

    @Override
    public boolean is(TagKey<T> tagKey) {
        return false;
    }

    @Override
    public boolean is(Holder<T> holder) {
        return false;
    }

    @Override
    public Stream<TagKey<T>> tags() {
        return Stream.empty();
    }

    @Override
    public Either<ResourceKey<T>, T> unwrap() {
        return Either.right(value);
    }

    @Override
    public Optional<ResourceKey<T>> unwrapKey() {
        return Optional.of((ResourceKey<T>) key);
    }

    @Override
    public @NotNull Kind kind() {
        return Kind.DIRECT;
    }

    @Override
    public boolean canSerializeIn(HolderOwner<T> owner) {
        return owner instanceof Registry;
    }

    protected abstract void register();
}
