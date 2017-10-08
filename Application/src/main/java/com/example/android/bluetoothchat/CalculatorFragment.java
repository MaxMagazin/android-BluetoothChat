package com.example.android.bluetoothchat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Maxim on 05.10.2017.
 */

public class CalculatorFragment extends Fragment {

    private static final String ADD = "+";
    private static final String SUB = "-";
    private static final String MUL = "*";
    private static final String DIV = "/";


    private EditText mResultEditText;

    private Button mNumber0;
    private Button mNumber1;
    private Button mNumber2;
    private Button mNumber3;
    private Button mNumber4;
    private Button mNumber5;
    private Button mNumber6;
    private Button mNumber7;
    private Button mNumber8;
    private Button mNumber9;
    private Button mPoint;

    private Button mOperationAdd;
    private Button mOperationDiv;
    private Button mOperationMul;
    private Button mOperationSub;
    private Button mOperationEqual;

    private Button mOperationMemoryAdd;
    private Button mOperationClean;
    private Button mOperationClearEntry;
    private Button mOperationChangeSign;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_calculator_fragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mResultEditText = (EditText) view.findViewById(R.id.calc_out);

        mNumber0 = (Button) view.findViewById(R.id.number_0);
        mNumber1 = (Button) view.findViewById(R.id.number_1);
        mNumber2 = (Button) view.findViewById(R.id.number_2);
        mNumber3 = (Button) view.findViewById(R.id.number_3);
        mNumber4 = (Button) view.findViewById(R.id.number_4);
        mNumber5 = (Button) view.findViewById(R.id.number_5);
        mNumber6 = (Button) view.findViewById(R.id.number_6);
        mNumber7 = (Button) view.findViewById(R.id.number_7);
        mNumber8 = (Button) view.findViewById(R.id.number_8);
        mNumber9 = (Button) view.findViewById(R.id.number_9);
        mPoint = (Button) view.findViewById(R.id.point);

        mNumber0.setOnClickListener(mNumbersAndPointClickListener);
        mNumber1.setOnClickListener(mNumbersAndPointClickListener);
        mNumber2.setOnClickListener(mNumbersAndPointClickListener);
        mNumber3.setOnClickListener(mNumbersAndPointClickListener);
        mNumber4.setOnClickListener(mNumbersAndPointClickListener);
        mNumber5.setOnClickListener(mNumbersAndPointClickListener);
        mNumber6.setOnClickListener(mNumbersAndPointClickListener);
        mNumber7.setOnClickListener(mNumbersAndPointClickListener);
        mNumber8.setOnClickListener(mNumbersAndPointClickListener);
        mNumber9.setOnClickListener(mNumbersAndPointClickListener);
        mPoint.setOnClickListener(mNumbersAndPointClickListener);

        mOperationAdd = (Button) view.findViewById(R.id.operation_add);
        mOperationDiv = (Button) view.findViewById(R.id.operation_div);
        mOperationMul = (Button) view.findViewById(R.id.operation_mul);
        mOperationSub = (Button) view.findViewById(R.id.operation_sub);
        mOperationEqual = (Button) view.findViewById(R.id.operation_equal);

        mOperationAdd.setOnClickListener(mOperationOrderClickListener);
        mOperationDiv.setOnClickListener(mOperationOrderClickListener);
        mOperationMul.setOnClickListener(mOperationOrderClickListener);
        mOperationSub.setOnClickListener(mOperationOrderClickListener);
        mOperationEqual.setOnClickListener(mOperationOrderClickListener);

        mOperationMemoryAdd = (Button) view.findViewById(R.id.operation_memory_add);
        mOperationClean = (Button) view.findViewById(R.id.operation_clean);
        mOperationClearEntry = (Button) view.findViewById(R.id.operation_clear_entry);
        mOperationChangeSign = (Button) view.findViewById(R.id.operation_change_sign);
    }

    private View.OnClickListener mNumbersAndPointClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String currentValue = mResultEditText.getText().toString();

            String charStr = null;
            switch (v.getId()) {
                case R.id.number_0:
                    if (!TextUtils.isEmpty(currentValue)) {
                        charStr = "0";
                    }
                    break;
                case R.id.number_1: charStr = "1"; break;
                case R.id.number_2: charStr = "2"; break;
                case R.id.number_3: charStr = "3"; break;
                case R.id.number_4: charStr = "4"; break;
                case R.id.number_5: charStr = "5"; break;
                case R.id.number_6: charStr = "6"; break;
                case R.id.number_7: charStr = "7"; break;
                case R.id.number_8: charStr = "8"; break;
                case R.id.number_9: charStr = "9"; break;
                case R.id.point:

                    if (!currentValue.contains(".") && !currentValue.isEmpty()) {
                        charStr = ".";
                    }
                    break;
            }
            if (!TextUtils.isEmpty(charStr)) {
                String result = mResultEditText.getText() + charStr;
                mResultEditText.setText(result);
            }
        }
    };

    private View.OnClickListener mOperationOrderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            String currentValue = mResultEditText.getText().toString();
//
//            boolean hasOperationInside = currentValue.contains(ADD) || currentValue.contains(SUB)
//                    || currentValue.contains(MUL) || currentValue.contains(DIV);
//
//            if (hasOperationInside) {
//                calculateAndSetResult();
//            }
//
//            String charStr = null;
//            switch (v.getId()) {
//                case R.id.operation_equal: charStr = "4"; break;
//
//
//                case R.id.operation_add:
//                    if (!TextUtils.isEmpty(currentValue)) {
//                        charStr = "0";
//                    }
//                    break;
//                case R.id.operation_sub: charStr = "1"; break;
//                case R.id.operation_mul: charStr = "2"; break;
//                case R.id.operation_div: charStr = "3"; break;
//
//                    if (!currentValue.contains(".") && !currentValue.isEmpty()) {
//                        charStr = ".";
//                    }
//                    break;
//            }
        }
    };
}
