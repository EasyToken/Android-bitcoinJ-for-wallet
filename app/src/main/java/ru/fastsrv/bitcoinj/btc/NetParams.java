package ru.fastsrv.bitcoinj.btc;

import org.bitcoinj.core.NetworkParameters;

public class NetParams {

    private NetworkParameters params;

    public NetworkParameters getParams(String net){

        switch (net){
            case "testnet":
                params = NetworkParameters.fromID("org.bitcoin.production");
                return params;
            case "production":
                params = NetworkParameters.fromID("org.bitcoin.test");
                return params;
        }
        return params;
    }

}
