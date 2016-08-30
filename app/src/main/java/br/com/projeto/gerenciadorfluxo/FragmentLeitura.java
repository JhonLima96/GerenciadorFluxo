package br.com.projeto.gerenciadorfluxo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentLeitura.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentLeitura#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLeitura extends Fragment implements View.OnClickListener, ZXingScannerView.ResultHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    private OnFragmentInteractionListener mListener;
    private ZXingScannerView mScannerView;

    private TextView txtScan;
    private EditText edtValorQR;
    private Button btnScanQR;

    public FragmentLeitura() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment FragmentLeitura.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLeitura newInstance(String param1) {
        FragmentLeitura fragment = new FragmentLeitura();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_leitura, container, false);
        // Inflate the layout for this fragment
        mScannerView = new ZXingScannerView(getActivity());
        txtScan = (TextView) view.findViewById(R.id.txtScan);
        edtValorQR = (EditText) view.findViewById(R.id.edtValorQR);
        btnScanQR = (Button) view.findViewById(R.id.btnScanQR);
        btnScanQR.setOnClickListener(this);


            edtValorQR.setText(mParam1);



        return view;
     }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        SimpleScannerFragment simpleScannerFragment = new SimpleScannerFragment();
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.relative_layout_for_fragment, simpleScannerFragment
        ).commit();
    }

    @Override
    public void handleResult(Result result) {
        Log.v("Handle Result", result.getText());
        Toast.makeText(getActivity(), result.getText(), Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(FragmentLeitura.this);
            }
        }, 2000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
