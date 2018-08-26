package ru.fastsrv.bitcoinj.btc;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;

public class CreateBtcWallet implements Interface {

    private static final SecureRandom secureRandom = new SecureRandom();
    private File keyDir;

    public CreateBtcWallet(File k){
        keyDir = k;
    }

    @Override
    public NetworkParameters getNetParams(String net) {
        NetworkParameters netParams = new NetParams().getParams(net);
        return netParams;
    }

    public String Bip39(){
        byte[] initialEntropy = new byte[16];
        secureRandom.nextBytes(initialEntropy);

        /**
         * Network Parametrs
         */
        NetworkParameters params = getNetParams(net);


        /**
         * Seed
         */
        DeterministicSeed seed = new DeterministicSeed(initialEntropy,"",creationtime);

        /**
         * KeyChain
         */
        DeterministicKeyChain keyChain = DeterministicKeyChain.builder().seed(seed).build();

        /**
         * keyPath
         */
        List<ChildNumber> keyPath = HDUtils.parsePath(keypath);

        DeterministicKey key = keyChain.getKeyByPath(keyPath, true);
        List<ECKey> ecKeys = (List<ECKey>) Collections.singletonList(key.decompress());

        /**
         * seed Code
         */
        String seedCode=seed.getMnemonicCode().toString();
        seedCode=seedCode.replace(",","").replace("[","").replace("]","");

        /**
         * Get Address
         */
        Address addressFromKey = key.toAddress(params);

        /**
         * Wallet
         */
        Wallet wallet = new Wallet(params);

        /**
         * Create wallet of Keys
         */
        wallet.fromKeys(params, ecKeys);

        /**
         * Add Chain to Wallet
         */
        wallet.addAndActivateHDChain(keyChain);

        /**
         * Output in log
         */
        System.out.println("Wallet Address: "+ addressFromKey);
        System.out.println("Seed Code: "+seedCode);

        // Save Wallet to file
        String fileName = addressFromKey.toString()+".dat";
        saveWallet(wallet, fileName);

        return seedCode;
    }

    public void saveWallet(Wallet wallet, String fileName){

        System.out.println("FileName: "+keyDir.getAbsolutePath());
        try {
            File file = new File(keyDir+"/"+fileName);
            wallet.saveToFile(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
