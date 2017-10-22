package com.example.android.bluetoothchat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.BaseInputConnection;
import android.widget.Button;
import android.widget.EditText;

/**
 * Base ever calculator
 *
 * Created by Maxim Yastremsky on 05.10.2017.
 */
public class CalculatorFragment extends Fragment {

    private static final String ADD = "+";
    private static final String SUB = "-";
    private static final String MUL = "*";
    private static final String DIV = "/";

    private EditText mResultEditText;

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
        return inflater.inflate(R.layout.calculator_fragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mResultEditText = (EditText) view.findViewById(R.id.calc_out);

        view.findViewById(R.id.number_0).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_1).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_2).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_3).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_4).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_5).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_6).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_7).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_8).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.number_9).setOnClickListener(mNumbersAndPointClickListener);
        view.findViewById(R.id.point).setOnClickListener(mNumbersAndPointClickListener);

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

            KeyEvent keyEvent = null;

            switch (v.getId()) {
                case R.id.point:
                    if (!currentValue.contains(",") && !currentValue.isEmpty()) {
                        keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_COMMA);
                    }
                    break;
                case R.id.number_0:
                    if (!TextUtils.isEmpty(currentValue)) {
                        keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_0);
                    }
                    break;
                case R.id.number_1: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1); break;
                case R.id.number_2: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_2); break;
                case R.id.number_3: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_3); break;
                case R.id.number_4: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_4); break;
                case R.id.number_5: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_5); break;
                case R.id.number_6: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_6); break;
                case R.id.number_7: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_7); break;
                case R.id.number_8: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_8); break;
                case R.id.number_9: keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_9); break;
            }

            if (keyEvent != null) {
                BaseInputConnection inputConnection = new BaseInputConnection(mResultEditText, true);
                inputConnection.sendKeyEvent(keyEvent);
            }
        }
    };

    private View.OnClickListener mOperationOrderClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String currentValue = mResultEditText.getText().toString();

            boolean hasOperationInside = currentValue.contains(ADD) || currentValue.contains(SUB)
                    || currentValue.contains(MUL) || currentValue.contains(DIV);

            if (hasOperationInside) {
                calculateAndSetResult();
            }

            String charStr = null;
            switch (v.getId()) {
                case R.id.operation_equal:
                    break;


                case R.id.operation_add:
                    break;
                case R.id.operation_sub:
                    break;
                case R.id.operation_mul:
                    break;
                case R.id.operation_div:
                    break;
            }
        }

        private void calculateAndSetResult() {

        }
    };


}
