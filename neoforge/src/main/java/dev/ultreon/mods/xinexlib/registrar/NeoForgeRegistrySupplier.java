package dev.ultreon.mods.xinexlib.registrar;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class NeoForgeRegistrySupplier<R extends T, T> implements RegistrySupplier<R, T> {
    private final DeferredHolder<T, R> holder;
    private final Registrar<T> registrar;
    private final Identifier id;

    public NeoForgeRegistrySupplier(DeferredHolder<T, R> holder, Registrar<T> registrar, Identifier id) {
        this.holder = holder;
        this.registrar = registrar;
        this.id = id;
    }

    @Override
    public Optional<R> asOptional() {
        return holder.asOptional();
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public Registry<T> registry() {
        return registrar.registry();
    }

    @Override
    public @NotNull T value() {
        return holder.value();
    }

    @Override
    public boolean isBound() {
        return holder.isBound();
    }

    @Override
    public boolean areComponentsBound() {
        return holder.areComponentsBound();
    }

    @Override
    public boolean is(@NotNull Identifier Identifier) {
        return holder.is(Identifier);
    }

    @Override
    public boolean is(@NotNull ResourceKey<T> resourceKey) {
        return holder.is(resourceKey);
    }

    @Override
    public boolean is(@NotNull TagKey<T> tagKey) {
        return holder.is(tagKey);
    }

    @Override
    @Deprecated
    public boolean is(@NotNull Holder<T> holder) {
        return this.holder.is(holder);
    }

    @Override
    public @NotNull Stream<TagKey<T>> tags() {
        return holder.tags();
    }

    @Override
    public @NonNull DataComponentMap components() {
        return holder.components();
    }

    @Override
    public @NotNull Either<ResourceKey<T>, T> unwrap() {
        return Either.right(value());
    }

    @Override
    public @NotNull Optional<ResourceKey<T>> unwrapKey() {
        return Optional.empty();
    }

    @Override
    public @NotNull Kind kind() {
        return holder.kind();
    }

    @Override
    public boolean canSerializeIn(@NotNull HolderOwner<T> holderOwner) {
        return holder.canSerializeIn(holderOwner);
    }

    @Override
    public boolean is(@NotNull Predicate<ResourceKey<T>> predicate) {
        return holder.is(predicate);
    }
}
