package ru.fastsrv.bitcoinj.btc;

import org.bitcoinj.core.NetworkParameters;

public interface Interface {
    
    long creationtime = 1409478661L;

    String keypath = "44H/0H/0H/0/0";
    
    NetworkParameters getNetParams(String net);
}
