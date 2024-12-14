package dev.ultreon.mods.xinexlib.registrar;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ForgeRegistrySupplier<R extends T, T> implements RegistrySupplier<R, T> {
    private final RegistryObject<R> registryObject;
    private final ResourceKey<R> key;
    private final Registrar<T> registrar;

    public ForgeRegistrySupplier(RegistryObject<R> registryObject, ResourceKey<R> key, Registrar<T> registrar) {
        this.registryObject = registryObject;
        this.key = key;
        this.registrar = registrar;
    }

    @Override
    public @NotNull R value() {
        return get();
    }

    @Override
    public boolean isBound() {
        return registryObject.isPresent();
    }

    @Override
    public boolean is(@NotNull ResourceLocation location) {
        return key.location().equals(location);
    }

    @Override
    public boolean is(@NotNull ResourceKey<T> key) {
        return this.key.equals(key);
    }

    @Override
    public boolean is(@NotNull Predicate<ResourceKey<T>> predicate) {
        return predicate.test((ResourceKey<T>) key);
    }

    @Override
    public boolean is(@NotNull TagKey<T> tagKey) {
        return registryObject.getHolder().orElseThrow().is((TagKey<R>) tagKey);
    }

    @Override
    public boolean is(@NotNull Holder<T> holder) {
        return registryObject.getHolder().orElseThrow().is((ResourceKey<R>) holder);
    }

    @Override
    public Stream<TagKey<T>> tags() {
        return (Stream) registryObject.getHolder().orElseThrow().tags();
    }

    @Override
    public Either<ResourceKey<T>, T> unwrap() {
        if (registryObject.isPresent()) {
            return Either.right(registryObject.get());
        }
        return Either.left((ResourceKey<T>) key);
    }

    @Override
    public Optional<ResourceKey<T>> unwrapKey() {
        return Optional.of((ResourceKey<T>) key);
    }

    @Override
    public @NotNull Kind kind() {
        return Kind.REFERENCE;
    }

    @Override
    public boolean canSerializeIn(@NotNull HolderOwner<T> holderOwner) {
        return registryObject.getHolder().orElseThrow().canSerializeIn((HolderOwner<R>) holderOwner);
    }

    @Override
    public Optional<R> asOptional() {
        return registryObject.isPresent() ? Optional.of(registryObject.get()) : Optional.empty();
    }

    @Override
    public ResourceLocation getId() {
        return key.location();
    }

    @Override
    public Registry<T> registry() {
        return registrar.registry();
    }
}
