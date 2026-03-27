package dev.ultreon.mods.xinexlib.registrar;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class FabricRegistrySupplier<R extends T, T> implements RegistrySupplier<R, T> {
    private final Registrar<T> registry;
    private final ResourceKey<R> key;
    protected R value;

    protected FabricRegistrySupplier(Registrar<T> registrar, ResourceKey<R> key) {
        this.registry = registrar;
        this.key = key;
    }

    @Override
    public boolean areComponentsBound() {
        return value != null;
    }

    @Override
    public @NonNull DataComponentMap components() {
        return DataComponentMap.EMPTY;
    }

    @Override
    public Optional<R> asOptional() {
        return Optional.ofNullable(value);
    }

    @Override
    public Identifier getId() {
        return key.identifier();
    }

    @Override
    public Registry<T> registry() {
        return registry.registry();
    }

    @Override
    public @NotNull R value() {
        return asOptional().orElseThrow(() -> new IllegalStateException("Value " + getId() + " in registry " + registry().key().identifier() + " is not bound!"));
    }

    @Override
    public boolean isBound() {
        return true;
    }

    @Override
    public boolean is(@NonNull Identifier location) {
        return this.key.identifier().equals(location);
    }

    @Override
    public boolean is(@NonNull ResourceKey<T> resourceKey) {
        return this.key.equals(resourceKey);
    }

    @Override
    public boolean is(Predicate<ResourceKey<T>> predicate) {
        return predicate.test((ResourceKey<T>) this.key);
    }

    @Override
    public boolean is(@NonNull TagKey<T> tagKey) {
        return false;
    }

    @Override
    public boolean is(@NonNull Holder<T> holder) {
        return false;
    }

    @Override
    public @NonNull Stream<TagKey<T>> tags() {
        return Stream.empty();
    }

    @Override
    public @NonNull Either<ResourceKey<T>, T> unwrap() {
        return Either.right(value);
    }

    @Override
    public @NonNull Optional<ResourceKey<T>> unwrapKey() {
        return Optional.of((ResourceKey<T>) key);
    }

    @Override
    public @NotNull Kind kind() {
        return Kind.DIRECT;
    }

    @Override
    public boolean canSerializeIn(@NonNull HolderOwner<T> owner) {
        return owner instanceof Registry;
    }

    protected abstract void register();
}
