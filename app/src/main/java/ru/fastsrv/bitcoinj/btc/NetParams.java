package ru.fastsrv.bitcoinj.btc;

import org.bitcoinj.core.NetworkParameters;

public class NetParams {

    private NetworkParameters params;

    public NetworkParameters getParams(String net){

        if (net.equals("production")) {
            System.out.println("Prod");
            params = NetworkParameters.fromID("org.bitcoin.production");
            return params;
        } else if (net.equals("testnet")){
                System.out.println("Test");
                params = NetworkParameters.fromID("org.bitcoin.test");
                return params;
        }
        return params;
    }

}
