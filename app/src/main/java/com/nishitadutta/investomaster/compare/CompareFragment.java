package com.nishitadutta.investomaster.compare;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nishitadutta.investomaster.R;
import com.nishitadutta.investomaster.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CompareFragment extends Fragment {
    @BindView(R.id.sym1)
    TextInputEditText sym1;

    @BindView(R.id.sym2)
    TextInputEditText sym2;

    @BindView(R.id.btn_apply)
    Button btnApply;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_compare, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }
    @OnClick(R.id.btn_apply)
    public void compareTwo(){
        String sym_1=sym1.getText().toString();
        String sym_2=sym2.getText().toString();

        Intent intent=new Intent(getContext(),CompareActivity.class);
        intent.putExtra(AppConstants.RED_SYM,sym_1);
        intent.putExtra(AppConstants.BLUE_SYM, sym_2);
        getContext().startActivity(intent);
    }
}
