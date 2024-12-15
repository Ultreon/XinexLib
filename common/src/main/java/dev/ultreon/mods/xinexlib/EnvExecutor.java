package dev.ultreon.mods.xinexlib;

import dev.ultreon.mods.xinexlib.platform.XinexPlatform;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class EnvExecutor {
    public static void runInEnv(Env env, Supplier<Runnable> runnable) {
        if (XinexPlatform.getEnv() == env) runnable.get().run();
    }

    public static void runInEnv(Env env, Supplier<Runnable> runnable, Runnable fallback) {
        if (XinexPlatform.getEnv() == env) runnable.get().run();
        else fallback.run();
    }

    public static <T> void runInEnvSpecific(Supplier<Runnable> client, Supplier<Runnable> server) {
        if (XinexPlatform.getEnv() == Env.CLIENT) client.get().run();
        else server.get().run();
    }

    public static <T> T getInEnv(Env env, Supplier<Supplier<T>> supplier, Supplier<T> fallback) {
        if (XinexPlatform.getEnv() == env) return supplier.get().get();
        return fallback.get();
    }

    public static <T> T getInEnv(Env env, Supplier<Supplier<T>> supplier) {
        return getInEnv(env, supplier, () -> null);
    }

    public static <T> T getInEnvSpecific(Supplier<Supplier<T>> client, Supplier<Supplier<T>> server) {
        if (XinexPlatform.getEnv() == Env.CLIENT) return client.get().get();
        return server.get().get();
    }

    public static <T> T callInEnv(Env env, Supplier<Callable<T>> supplier) throws Exception {
        if (XinexPlatform.getEnv() == env) return supplier.get().call();
        return null;
    }

    public static <T> T callInEnv(Env env, Supplier<Callable<T>> supplier, Callable<T> fallback) throws Exception {
        if (XinexPlatform.getEnv() == env) return supplier.get().call();
        return fallback.call();
    }

    public static <T> T callInEnvSpecific(Supplier<Callable<T>> client, Supplier<Callable<T>> server) throws Exception {
        if (XinexPlatform.getEnv() == Env.CLIENT) return client.get().call();
        return server.get().call();
    }
}
