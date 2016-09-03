package br.com.projeto.gerenciadorfluxo;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class SimpleScannerFragment extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    public static final String TXT_VALOR_QR = "TXT_VALOR_QR";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());
        return mScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }


    @Override
    public void handleResult(Result result) {
        String txtValorQR = result.getText();

        try{
            Toast.makeText(getActivity(), "Contents = " + txtValorQR +
                    ", Format = " + result.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();

            FragmentLeitura fragmentLeitura = new FragmentLeitura().newInstance(txtValorQR);
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction().
                    replace(R.id.relative_layout_for_fragment, fragmentLeitura
                    ).commit();
        /*

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(SimpleScannerFragment.this);
            }
        }, 2000); */

        }catch (Exception e){

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }


}
