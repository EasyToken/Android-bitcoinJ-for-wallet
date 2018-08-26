package ru.fastsrv.bitcoinj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Map;

import ru.fastsrv.bitcoinj.btc.CreateBtcWallet;
import ru.fastsrv.bitcoinj.btc.ReadBtcWallet;

/**
 *
 * @author Dmitry Markelov
 * Telegram group: https://t.me/joinchat/D62dXAwO6kkm8hjlJTR9VA
 *
 * If you have any questions, I will answer the telegram
 *
 *
 *
 */

public class MainActivity extends AppCompatActivity {

    File dataDir;
    File keyDir;

    TextView tv_address, tv_balance;
    EditText et_seedcode, et_receiveaddress, et_btcammount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_balance = (TextView) findViewById(R.id.tv_balance);

        dataDir = this.getExternalFilesDir("/bitcoin/keystore/");
        keyDir = new File(dataDir.getAbsolutePath());
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_create:
                System.out.println("Creating");
                CreateWallet();
                break;
            case R.id.btn_recovery:
                break;
            case R.id.btn_btctransaction:
                break;
            default:
                break;
        }
    }

    public void CreateWallet(){

        /**
         * Checking if there are wallet
         */
        File[] listfiles = keyDir.listFiles();
        if (listfiles.length == 0 ) {
            // Если в директории нет файла кошелька, добавляем кошелек
            /**
             * ir not, create wallet
             */
            CreateBtcWallet createBtcWallet = new CreateBtcWallet(keyDir);
            messageToast(createBtcWallet.Bip39());
        } else {

            /**
             * If the wallet is present, loading the data
             */
            ReadBtcWallet readBtcWallet = new ReadBtcWallet(keyDir);
            Map<String,String> result = readBtcWallet.ReadWallet();
            tv_address.setText(result.get("address"));
            tv_balance.setText(result.get("balance"));
            messageToast(result.get("seedcode"));
        }
    }

    private void messageToast(String message){
        Toast toast = Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG);
        toast.show();
    }
}
