package ru.fastsrv.bitcoinj.btc;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class RecoveryBtcWallet implements Interface {

    private File keyDir;

    public RecoveryBtcWallet(File k){
        keyDir = k;
    }

    @Override
    public NetworkParameters getNetParams(String net) {
        NetworkParameters netParams = new NetParams().getParams(net);
        return netParams;
    }

    public String recoveryWallet(String seedCode){
        System.out.println("Recovery");
        NetworkParameters params = getNetParams(net);

        try {
            DeterministicSeed seed = new DeterministicSeed(seedCode, null, "", creationtime);

            DeterministicKeyChain keyChain = DeterministicKeyChain.builder().seed(seed).build();
            List<ChildNumber> keyPath = HDUtils.parsePath("44H/0H/0H/0/0");
            DeterministicKey key = keyChain.getKeyByPath(keyPath, true);
            List<ECKey> ecKeys = (List<ECKey>) Collections.singletonList(key.decompress());

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

            /**
             * Save Wallet to file
             */
            String fileName = addressFromKey.toString()+".dat";
            saveWallet(wallet, fileName);

            return addressFromKey.toString();
        } catch (UnreadableWalletException e) {
            e.printStackTrace();
        }
        return null;
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
