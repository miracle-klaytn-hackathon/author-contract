package com.github.miracle.klaytn.hackathon.contracts.impl.klaytn;

import com.klaytn.caver.Caver;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.function.Supplier;

@Singleton
public class CaverProvider implements Supplier<Caver> {

    private final Caver caver;

    public CaverProvider(@ConfigProperty(name = "RPC_URL") String rpcURL) {
        this.caver = new Caver(rpcURL);
    }

    @Override
    public Caver get() {
        return caver;
    }

}
