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
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ForgeRegistrySupplier<R extends T, T> implements IRegistrySupplier<R, T> {
    private final RegistryObject<R> registryObject;
    private final ResourceKey<R> key;
    private final IRegistrar<T> registrar;

    public ForgeRegistrySupplier(RegistryObject<R> registryObject, ResourceKey<R> key, IRegistrar<T> registrar) {
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
    public boolean is(@NotNull ResourceKey<R> key) {
        return this.key.equals(key);
    }

    @Override
    public boolean is(@NotNull Predicate<ResourceKey<R>> predicate) {
        return predicate.test(key);
    }

    @Override
    public boolean is(@NotNull TagKey<R> tagKey) {
        return registryObject.getHolder().orElseThrow().is(tagKey);
    }

    @Override
    public boolean is(@NotNull Holder<R> holder) {
        return registryObject.getHolder().orElseThrow().is(holder);
    }

    @Override
    public @NotNull Stream<TagKey<R>> tags() {
        return registryObject.getHolder().orElseThrow().tags();
    }

    @Override
    public @NotNull Either<ResourceKey<R>, R> unwrap() {
        if (registryObject.isPresent()) {
            return Either.right(registryObject.get());
        }
        return Either.left(key);
    }

    @Override
    public @NotNull Optional<ResourceKey<R>> unwrapKey() {
        return Optional.of(key);
    }

    @Override
    public @NotNull Kind kind() {
        return Kind.REFERENCE;
    }

    @Override
    public boolean canSerializeIn(@NotNull HolderOwner<R> holderOwner) {
        return registryObject.getHolder().orElseThrow().canSerializeIn(holderOwner);
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
