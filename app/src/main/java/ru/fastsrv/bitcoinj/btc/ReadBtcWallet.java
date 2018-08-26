package ru.fastsrv.bitcoinj.btc;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadBtcWallet implements Interface {

    private File keyDir;

    public ReadBtcWallet(File k){
        keyDir = k;
    }

    @Override
    public NetworkParameters getNetParams(String net) {
        NetworkParameters netParams = new NetParams().getParams(net);
        return netParams;
    }

    public Map<String, String> ReadWallet(){
        NetworkParameters params = getNetParams(net);
        Wallet wallet = new Wallet(params);

        try {
            wallet = Wallet.loadFromFile(getWalletFile());
        } catch (UnreadableWalletException e) {
            e.printStackTrace();
        }

        DeterministicKeyChain chain = wallet.getActiveKeyChain();
        List<ChildNumber> keyPath = HDUtils.parsePath(keypath);
        DeterministicKey key = chain.getKeyByPath(keyPath, true);

        System.out.println(key.toAddress(params));
        System.out.println(wallet.getBalance().toString());

        String seedCode=chain.getMnemonicCode().toString();
        seedCode=seedCode.replace(",","").replace("[","").replace("]","");
        System.out.println(seedCode);

        Map<String,String> result = new HashMap<>();
        result.put("address",key.toAddress(params).toString());
        result.put("balance",wallet.getBalance().toString());
        result.put("seedcode",seedCode);

        return result;
    }

    private File getWalletFile(){
        /**
         * Get list files in directory
         */
        File[] listfiles = keyDir.listFiles();
        File file = new File(String.valueOf(listfiles[0]));
        System.out.println("Load Wallet File "+ file);
        return file;
    }

}